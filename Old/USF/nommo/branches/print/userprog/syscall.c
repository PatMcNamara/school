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

//TODO static methods

static void syscall_handler (struct intr_frame *);

/* Syscall helper functions. */
static tid_t syscall_exec(const char *);
static int syscall_open(const char *);
static int syscall_read(int, void *, unsigned);
static int syscall_write(int, void *, unsigned);

/* Gets and validates the next arg off the stack. */
static void *next_arg(void);
static char *next_str(void);

/* Validates user supplied buffer (kills the thread if validation fails). */
static void validate_buffer(void *ptr, size_t size);

/* Location of next 4 byte arg (used by next_arg and nxt_str). */
static void *offset;
/* Lock that controls access to offset (and by extension the stack args). */
static struct lock offset_lock;

void
syscall_init (void)
{
  intr_register_int (0x30, 3, INTR_ON, syscall_handler, "syscall");
  lock_init(&fs_lock);
  lock_init(&offset_lock);
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
  void *arg0, *arg1, *arg2;
  struct fd_elem **fdt = thread_current()->open_files;

  /* Acquire lock before manipulating offset */
  lock_acquire(&offset_lock);
  offset = f->esp;
  sys_call = (int) next_arg();

  switch (sys_call) {
  case SYS_HALT:
    lock_release(&offset_lock);
    power_off();
    NOT_REACHED ();

  case SYS_EXIT:
    arg0 = next_arg();/* exit status */
    lock_release(&offset_lock);

    thread_current()->exit_controler->value = (int) arg0;
    thread_exit();
    NOT_REACHED ();

  case SYS_EXEC:
    arg0 = next_str(); /* executable name */
    lock_release(&offset_lock);

    ret_val = syscall_exec((const char *) arg0);
    break;

  case SYS_WAIT:
    arg0 = next_arg(); /* tid */
    lock_release(&offset_lock);

    ret_val = process_wait((tid_t) arg0);
    break;

  case SYS_CREATE:
    arg0 = next_str(); /* File name */
    arg1 = next_arg(); /* Size */
    lock_release(&offset_lock);

    lock_acquire(&fs_lock);
    ret_val = filesys_create((const char *) arg0, (unsigned) arg1);
    lock_release(&fs_lock);
    break;

  case SYS_REMOVE:
    arg0 = next_str();/* file name */
    lock_release(&offset_lock);

    lock_acquire(&fs_lock);
    ret_val = filesys_remove((const char *) arg0);
    lock_release(&fs_lock);
    break;

  case SYS_OPEN:
    arg0 = next_str(); /* file name */
    lock_release(&offset_lock);

    ret_val = syscall_open((const char *) arg0);
    break;

  case SYS_FILESIZE:
    arg0 = next_arg(); /* fd */
    lock_release(&offset_lock);

    lock_acquire(&fs_lock);
    ret_val = file_length(fdt[(int) arg0]->file);
    lock_release(&fs_lock);
    break;

  case SYS_READ:
    arg0 = next_arg(); /* fd */
    arg1 = next_arg(); /* buffer */
    arg2 = next_arg(); /* size */
    lock_release(&offset_lock);

    ret_val = syscall_read((int) arg0, arg1, (unsigned) arg2);
    break;

  case SYS_WRITE:
    arg0 = next_arg(); /* fd */
    arg1 = next_arg(); /* buffer */
    arg2 = next_arg(); /* size */
    lock_release(&offset_lock);

    ret_val = syscall_write((int) arg0, arg1, (unsigned) arg2);
    break;

  case SYS_SEEK:
    arg0 = next_arg(); /* fd */
    arg1 = next_arg(); /* position */
    lock_release(&offset_lock);

    lock_acquire(&fs_lock);
    file_seek(thread_current()->open_files[(int) arg0]->file, (unsigned) arg1);
    lock_release(&fs_lock);
    break;

  case SYS_TELL:
    arg0 = next_arg(); /* fd */
    lock_release(&offset_lock);

    lock_acquire(&fs_lock);
    ret_val = file_tell(fdt[(int) arg0]->file);
    lock_release(&fs_lock);
    break;

  case SYS_CLOSE:
    arg0 = next_arg(); /* fd */
    lock_release(&offset_lock);

    syscall_close((int) arg0);
    break;

  default:
    /* Invalid system call number. */
    lock_release(&offset_lock);
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
    struct list_elem *e;
    struct exit_struct *exit;

    /* Get exit_struct of created child. */
    for(e = list_begin(&thread_current()->children); e != list_end(&thread_current()->children); e = list_next(e)) {
      if(tid == list_entry(e, struct exit_struct, elem)->tid){
        exit = list_entry(e, struct exit_struct, elem);
        break;
      }
    }

    /* Wait for child to finish loading. */
    sema_down(&exit->running);
    /* If loading failed, free child's exit_struct. */
    if(!exit->loaded)
      tid = process_wait(tid); /* This line will always set tid = TID_ERROR. */
  }
  return tid;
}

/* Reads a file into specified buffer and returns number of chars read or -1. */
static int syscall_read(int fd, void *buf, unsigned size)
{
  int ret_val;
  struct fd_elem **fdt = thread_current()->open_files;

  /* Verify user supplied buffer is valid. */
  validate_buffer(buf, size);

  if(fd == STDIN_FILENO) { /* Read from stdin. */
    char *c = buf;
    while((void *) c < buf + size)
      *c++ = input_getc();
    ret_val = size;

  } else if(fd >= 2 && fd < 128 && fdt[fd] != NULL) {
    /* If fd is valid, read from file. */
    lock_acquire(&fs_lock);
    ret_val = file_read(fdt[fd]->file, buf, size);
    lock_release(&fs_lock);

  } else /* fd not valid. */
    ret_val = -1;

  return ret_val;
}

/* Writes a file into specified buffer and returns number of chars written or -1. */
static int syscall_write(int fd, void *buf, unsigned size){
  int ret_val;
  struct fd_elem **fdt = thread_current()->open_files;

  /* Verify user supplied buffer is valid. */
  validate_buffer(buf, size);

  if (fd == STDOUT_FILENO){ /* Write to stdout. */
    putbuf(buf, size);
    ret_val = size;

  } else if (fd >= 2 && fd < 128 && fdt[fd] != NULL){
    /* If fd is valid, read from file. */
    lock_acquire(&fs_lock);
    ret_val = file_write(fdt[fd]->file, buf, size);
    lock_release(&fs_lock);

  } else /* fd not valid. */
    ret_val = -1;

  return ret_val;
}

/* Closes a file. */
void syscall_close(int fd) {
  struct fd_elem *fde;

  /* Check fd is valid. */
  if(fd < 2 || fd >= 128)
    return;

  fde = thread_current()->open_files[fd];

  /* If fd is open, close it and free its resources. */
  if(fde != NULL) {
    lock_acquire(&fs_lock);
    file_close(fde->file);
    lock_release(&fs_lock);
    thread_current()->open_files[fd] = NULL;
    free(fde);
  }
}

/* Open specified file and return fd. */
static int syscall_open(const char *file_name){
  int fd;
  struct fd_elem *fd_elem, **fdt = thread_current()->open_files;
  struct file *f;

  /* Get first available fd and ensure it's valid. */
  for(fd = 2; fdt[fd] != NULL && fd < 128; fd++);
  if(fd == 128)
    return -1;

  /* Open file */
  lock_acquire(&fs_lock);
  f = filesys_open(file_name);
  lock_release(&fs_lock);

  /* Check that file was opened. */
  if(f == NULL)
    return -1;

  /* Create new fd_elem and add it to the file table. */
  fd_elem = (struct fd_elem *) malloc(sizeof(struct fd_elem));
  fd_elem->fd = fd;
  fd_elem->file = f;
  fdt[fd] = fd_elem;

  return fd;
}

/* Helper functions for validating user pointers. */
static void validate_byte(void *);
static void validate_word(void *);
static void validate_range(void *, void *);
static void validate_str(char *);

/* Pops the next argument off the stack and checks that it is a valid pointer. */
static void *next_arg() {
  ASSERT(lock_held_by_current_thread(&offset_lock));

  validate_word(offset);
  void *ret_val = *(uint32_t *) offset;
  offset += sizeof(uint32_t);
  return ret_val;
}

/* Pops the next argument off the stack and checks it is a valid string. */
static char *next_str() {
  ASSERT(lock_held_by_current_thread(&offset_lock));

  char *s = (char *) next_arg();
  validate_str(s);
  return s;
}

/* Validates a single byte.  If the byte isn't a valid virtual memory
   location, the program will be terminated an with exit status of -1.
   This function shouldn't be called directly but instead one should
   use the other validate functions (which in turn call this function). */
static void validate_byte(void *ptr) {
  if(!is_user_vaddr(ptr) || pagedir_get_page(thread_current()->pagedir, ptr) == NULL){
    if(lock_held_by_current_thread(&offset_lock))
      lock_release(&offset_lock);
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

/* Validates all pointers within the given buffer.  This is the validation
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
