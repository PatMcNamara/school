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

static void syscall_handler (struct intr_frame *);
int syscall_read(void);
void *next_arg(void);
char *next_str(void);
void validate_byte(void *);
void validate_word(void *);
void validate_str(char *);
tid_t exec(void);

void *offset;

void
syscall_init (void)
{
  intr_register_int (0x30, 3, INTR_ON, syscall_handler, "syscall");
}

static void
syscall_handler (struct intr_frame *f)
{
/*  printf ("system call!\n");//TODO remove
  thread_exit ();*/
  uint32_t ret_val;

  offset = f->esp;
  int sys_call = (int) next_arg();

  char *tmp; //TODO remove
  


  switch (sys_call) {
  case SYS_HALT:
    power_off();
    NOT_REACHED ();

  case SYS_EXIT:
    syscall_exit((int) next_arg());
    NOT_REACHED ();

  case SYS_EXEC:
    ret_val = exec();
    break;

  case SYS_WAIT:
    ret_val = process_wait((tid_t) next_arg());
    break;

  case SYS_CREATE:
//    printf("test\n");
//    printf("file_name: %s, size: %d\n", next_str(), next_arg());
    tmp = next_str();
    ret_val = filesys_create(tmp, next_arg());
    break;

  case SYS_REMOVE:
    ret_val = filesys_remove(next_str());
    break;

  case SYS_OPEN:
    next_str();
    break;

  case SYS_FILESIZE:
    next_arg();
    break;

  case SYS_READ:
    /* next_arg(); next_arg(); next_arg(); */
	 ret_val = syscall_read();
    break;

  case SYS_WRITE:
    //FIXME this is just a temporary function to print to console unil real call is implemented
    /*if ((int) next_arg() == STDOUT_FILENO) {
      printf("%s", (char *) next_arg());
    }*/
	 ret_val = syscall_write();
    break;

  case SYS_SEEK:

    break;

  case SYS_TELL:

    break;

  case SYS_CLOSE:

    break;

  default:
    //TODO print error message
    break;
  }
  f->eax = ret_val;
}

void *next_arg() {//TODO instead of using void *, should use something from stdint
//  printf("next: %p = %p\n", offset, *(int *)offset);
  validate_byte(offset);
  void *ret_val = *(unsigned int *) offset;
  offset += sizeof(void *);
  return ret_val;
}

char *next_str() {
  char *s = next_arg();
  validate_str(s);
  return s;
}

void validate_byte(void *ptr) {
  
  
  if(!is_user_vaddr(ptr) || pagedir_get_page(thread_current()->pagedir, ptr) == NULL)//FIXME make sure the end of the pointer isn't over a page boundery
    process_terminate();
	 
}

void validate_range(void *start, void *end) {
  for(; start <= end; start++) {
    validate_byte(start);
  }
}

/* used for validating pointers and ints */
void validate_word(void *ptr) {
  validate_range(ptr, ptr + sizeof(void *));
}

void validate_str(char *str) {
  do {
    validate_byte(str);
  } while (*str++ != NULL);
}

tid_t exec(void) {
  char *args = next_str();
  tid_t tid = process_execute(args);

  if(tid != TID_ERROR){
    struct list_elem *e;
    struct exit_struct *exit = NULL;
    // TODO this is used at least 3 times
    for(e = list_begin(&thread_current()->children); e != list_end(&thread_current()->children); e = list_next(e)) {
      if(tid == list_entry(e, struct exit_struct, elem)->tid){
        exit = list_entry(e, struct exit_struct, elem);
        break;
      }
    }
    sema_down(&exit->running);
    if(!exit->loaded) {
      /*list_remove(&exit->elem);
      free(exit);
      tid = -1;*/
      /* This will always return -1 */
      tid = process_wait(tid);
    }
  }
  return tid;
}


int syscall_read(void)
{
	int fd = (int) next_arg();
	void *buf = next_arg();
	int size = (int) next_arg();
	
   //Don't perform operation if size is zero
   if(size == 0)
     return 0;
	  
   //Read from stdin
   if(fd==0)
   {
     int i;
     char * char_buffer = buf;
     for(i=0; i < size; i++)
     {
       char_buffer[i] = input_getc();
     }
     return size;
   }
   //Perform operation onbly if the given fd is in the calling threads
   //open fd list 
   else if(thread_has_fd(fd))
   {
        return file_read(file_get_file(fd),buf,size);
   }
   return -1;
}

int syscall_write(void){
   int fd = (int) next_arg();
   void *buf = next_arg();
   int size = (int) next_arg();
	
   //write nothing
   if (size==0) return 0; 
   //write to stdout
   if (fd==STDOUT_FILENO){
      int bytes_left = size;
      while (bytes_left >0) {
         putbuf(buf,size);
         bytes_left -= size;
      }
   return size;
   } else if (thread_has_fd(fd)){
      // write to file if thread owns the file descriptor
      return file_write(file_get_file(fd),buf,size);
   }
   return -1;
}

void syscall_exit(int value) {
  struct exit_struct *ec = thread_current()->exit_controler;
  printf("%s: exit(%d)\n", thread_name(), value);
  ec->value = value;
  sema_up(&ec->parent);

  thread_exit();
}
