#include "userprog/syscall.h"
#include <stdio.h>
#include <stdint.h>
#include <syscall-nr.h>
#include "threads/interrupt.h"
#include "threads/thread.h"
#include "threads/init.h"
#include "threads/synch.h"
#include "threads/vaddr.h"
#include "threads/malloc.h"
#include "userprog/process.h"
#include "userprog/pagedir.h"
#include "filesys/filesys.h"
#include "filesys/file.h"
#include "devices/input.h"

#define MAX_ARGS 3

static void syscall_handler (struct intr_frame *);

/* Syscall helper functions. */
static tid_t syscall_exec(const char *);
static int syscall_open(const char *);
static int syscall_read(int, void *, unsigned);
static int syscall_write(int, void *, unsigned);

static struct fd_elem *fd_to_elem(int fd, struct hash_elem * (*hash_func) (struct hash *, struct hash_elem *));
static struct file *fd_to_file(int fd, struct hash_elem * (*hash_func) (struct hash *, struct hash_elem *));
static fd_t allocate_fd(void);

/* Gets and validates the next arg off the stack. */
static void get_args(void **, void **, int);
static void *next_arg(void **);

/* Validates user supplied buffer (kills the thread if validation fails). */
static void validate_buffer(void *, size_t);
static void validate_str(char *);

static struct lock fd_lock;

void
syscall_init (void)
{
  intr_register_int (0x30, 3, INTR_ON, syscall_handler, "syscall");
  lock_init(&fs_lock);
  lock_init(&fd_lock);
}

/* Handles syscalls.  Gets the syscall arguments off the stack (including the
   syscall number) then (either directly or with the aid of helper functions)
   executes the system call and places any return value into the eax register.
   Arguments should be parsed as soon as possible and the offset_lock should
   be held for the least possible amount of time. */
static void
syscall_handler (struct intr_frame *f)
{
  int sys_call;
  uint32_t ret_val = -1;
  void *args[MAX_ARGS], *offset;//TODO should array really be a void *?

  offset = f->esp;
  sys_call = (int) next_arg(&offset);

  switch (sys_call) {
  case SYS_HALT:
    power_off();
    NOT_REACHED ();

  case SYS_EXIT:
    get_args(&offset, args, 1);
    thread_current()->exit_controler->value = (int) args[0];
    thread_exit();
    NOT_REACHED ();

  case SYS_EXEC:
    get_args(&offset, args, 1);
    validate_str((char *) args[0]);
    ret_val = syscall_exec((const char *) args[0]);
    break;

  case SYS_WAIT:
    get_args(&offset, args, 1);
    ret_val = process_wait((tid_t) args[0]);
    break;

  case SYS_CREATE:
    get_args(&offset, args, 2);
    validate_str((char *) args[0]);

    lock_acquire(&fs_lock);
    ret_val = filesys_create((const char *) args[0], (unsigned) args[1]);
    lock_release(&fs_lock);
    break;

  case SYS_REMOVE:
    get_args(&offset, args, 1);
    validate_str((char *) args[0]);

    lock_acquire(&fs_lock);
    ret_val = filesys_remove((const char *) args[0]);
    lock_release(&fs_lock);
    break;

  case SYS_OPEN:
    get_args(&offset, args, 1);
    validate_str((char *) args[0]);
    ret_val = syscall_open((const char *) args[0]);
    break;

  case SYS_FILESIZE:
    get_args(&offset, args, 1);

    lock_acquire(&fs_lock);
    ret_val = file_length(fd_to_file((int) args[0], hash_find));
    lock_release(&fs_lock);
    break;

  case SYS_READ:
    get_args(&offset, args, 3);
    validate_buffer(args[1], (size_t) args[2]);
    ret_val = syscall_read((int) args[0], args[1], (unsigned) args[2]);
    break;

  case SYS_WRITE:
    get_args(&offset, args, 3);
    validate_buffer(args[1], (size_t) args[2]);
    ret_val = syscall_write((int) args[0], args[1], (unsigned) args[2]);
    break;

  case SYS_SEEK:
    get_args(&offset, args, 2);

    lock_acquire(&fs_lock);
    file_seek(fd_to_file((int) args[0], hash_find), (unsigned) args[1]);
    lock_release(&fs_lock);
    break;

  case SYS_TELL:
    get_args(&offset, args, 1);

    lock_acquire(&fs_lock);
    ret_val = file_tell(fd_to_file((int) args[0], hash_find));
    lock_release(&fs_lock);
    break;

  case SYS_CLOSE:
    get_args(&offset, args, 1);
    syscall_close((int) args[0]);
    break;

  default:
    /* Invalid system call number. */
    ret_val = -1;
    break;
  }
  /* Put return value into eax register and return to normal execution. */
  f->eax = ret_val;
}

/* Exec's a process with the given command line arguments.
   Returns tid of exec'd thread or TID_ERROR if exec failed. */
static tid_t syscall_exec(const char *cmd_line) {
  tid_t tid = process_execute(cmd_line);

  /* If a thread was created, make sure it was able to successfully load. */
  if(tid != TID_ERROR){
    struct exit_struct e, *exit;

    /* Get exit_struct of created child. */
    e.tid = tid;
    exit = hash_entry(hash_find(&thread_current()->children, &e.elem), struct exit_struct, elem);

    /* Wait for child to finish loading. */
    sema_down(&exit->running);
    /* If loading failed, free child's exit_struct. */
    if(!exit->loaded) {
      process_wait(tid);
      tid = TID_ERROR;
    }
  }
  return tid;
}

/* Reads a file into specified buffer and returns number of chars read or -1. */
static int syscall_read(int fd, void *buf, unsigned size)
{
  int ret_val;
  struct file *f;

  if(fd == STDIN_FILENO) { /* Read from stdin. */
    char *c = buf;
    buf += size;
    while((void *) c < buf)
      *c++ = input_getc();
    ret_val = size;

  } else { /* Read from file */
    f = fd_to_file(fd, hash_find);
    if(f != NULL){ /* If fd is valid, read from file. */
      lock_acquire(&fs_lock);
      ret_val = file_read(f, buf, size);
      lock_release(&fs_lock);
    } else { /* If fd is invalid, return -1. */
      ret_val = -1;
    }
  }

  return ret_val;
}

/* Writes a file into specified buffer and returns number of chars written or -1. */
static int syscall_write(int fd, void *buf, unsigned size){
  int ret_val;
  struct file *f;

  if (fd == STDOUT_FILENO){ /* Write to stdout. */
    putbuf(buf, size);
    ret_val = size;

  } else { /* Write from file */
    f = fd_to_file(fd, hash_find);
    if (f != NULL) {/* If fd is valid, write to file. */
      lock_acquire(&fs_lock);
      ret_val = file_write(f, buf, size);
      lock_release(&fs_lock);
    } else { /* If fd is invalid, return -1 */
      ret_val = -1;
    }
  }

  return ret_val;
}

/* Closes a file. */
void syscall_close(int fd) {
  /* Get the fd_elem and delete it from hash table. */
  struct fd_elem *elem = fd_to_elem(fd, hash_delete);

  /* If the fd was valid, close the file and free the fd_elem. */
  if(elem != NULL){
    lock_acquire(&fs_lock);
    file_close(elem->file);
    lock_release(&fs_lock);
    free(elem);
  }
}

/* Open specified file and return fd. */
static int syscall_open(const char *file_name){
  int fd;
  struct fd_elem *elem;
  struct file *f;

  /* Open file */
  lock_acquire(&fs_lock);
  f = filesys_open(file_name);
  lock_release(&fs_lock);

  /* Check that file was opened. */
  if(f != NULL){
    elem = malloc(sizeof(struct fd_elem));
    elem->fd = fd = allocate_fd();
    elem->file = f;
    hash_insert(&thread_current()->fd_table, &elem->hash_elem);
  } else {
    fd = -1;
  }

  return fd;
}

static struct fd_elem *fd_to_elem(int fd, struct hash_elem * (*hash_func) (struct hash *, struct hash_elem *)) {
  struct fd_elem f;
  struct hash_elem *hash;
  f.fd = fd;
  hash = (*hash_func) (&thread_current()->fd_table, &f.hash_elem);
  return (hash == NULL) ? NULL : hash_entry(hash, struct fd_elem, hash_elem);
}

static struct file *fd_to_file(int fd, struct hash_elem * (*hash_func) (struct hash *, struct hash_elem *)) {
  struct fd_elem *f = fd_to_elem(fd, hash_func);
  return (f == NULL) ? NULL : f->file;
}

/* Helper functions for validating user pointers. */
static void validate_byte(void *);
static void validate_word(void *);
static void validate_range(void *, void *);
//static void validate_str(char *);

static void get_args(void **esp, void *args[], int num_args) {
  ASSERT(num_args <= MAX_ARGS);

  for(int i = 0; i < num_args; i++)
    args[i] = next_arg(esp);
}

/* Pops the next argument off the stack and checks that it is a valid pointer. */
static void *next_arg(void **esp) {
  void *ret_val, *offset = *esp;

  validate_word(offset);
  ret_val = (void *) (*(uint32_t *) offset);
  *esp += sizeof(uint32_t);
  return ret_val;
}

/* Validates a single byte.  If the byte isn't a valid virtual memory
   location, the program will be terminated an with exit status of -1.
   This function shouldn't be called directly but instead one should
   use the other validate functions (which in turn call this function). */
static void validate_byte(void *ptr) {
  if(!is_user_vaddr(ptr) || pagedir_get_page(thread_current()->pagedir, ptr) == NULL){
    thread_exit();
  }
}

/* Validates all pointers between start and end in a few page table
   inspections as possible. Same notes as validate_byte(). */
static void validate_range(void *start, void *end) {
  int i;
  for(i = pg_no(end) - pg_no(start); i >= 0; i--)
    validate_byte(start + (PGSIZE * i));
}

/* Validates if a user supplied buffer is valid.  This is the validation
   function that should be called with a few exceptions as it uses the other
   functions as helper functions to assist with the validation. */
static void validate_buffer(void *ptr, size_t size) {
  validate_range(ptr, ptr + size);
}

/* Validates any word sized object.  Called by next_arg to validate words
   it pulls off the stack. */
static void validate_word(void *ptr) {
  validate_range(ptr, ptr + sizeof(void *));
}

/* Validates an entire string.  It's main job it to help next_str() with
   validation. */
static void validate_str(char *str) {
  do {
    validate_byte(str);
  } while (*str++ != '\0');
}

/*  */
static fd_t allocate_fd() {
  static fd_t next_fd = 2;
  fd_t fd;

  lock_acquire(&fd_lock);
  fd = next_fd++;
  lock_release(&fd_lock);

  return fd;
}
