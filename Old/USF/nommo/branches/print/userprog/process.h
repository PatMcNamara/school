#ifndef USERPROG_PROCESS_H
#define USERPROG_PROCESS_H

#include <list.h>
#include "threads/thread.h"
#include "threads/synch.h"

/* Holds data used by parent to keep track of child's execution status. */
struct exit_struct
  {
    tid_t tid;                /* Child's tid. */
    int value;                /* Child's exit status. */
    bool loaded;              /* Did child successfully load? */
    struct semaphore running, /* Is upped when child finishes loading. */
                     parent;  /* Is upped when child exits. */
    struct list_elem elem;
  };

/* A file descriptor */
struct fd_elem
{
   int fd;
   struct file *file;
};

void process_init (struct thread *);
tid_t process_execute (const char *file_name);
int process_wait (tid_t);
void process_exit (void);
void process_activate (void);

#endif /* userprog/process.h */
