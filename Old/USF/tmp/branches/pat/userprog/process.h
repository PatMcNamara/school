#ifndef USERPROG_PROCESS_H
#define USERPROG_PROCESS_H

#include <list.h>
#include "threads/thread.h"
#include "threads/synch.h"
#include "filesys/file.h"

struct exit_struct
  {
    tid_t tid;
    int value;
    bool loaded;
    struct semaphore parent, running;
    struct list_elem elem;
  };

tid_t process_execute (const char *file_name);
void process_init (struct thread *);
int process_wait (tid_t);
void process_exit (void);
//void process_terminate(void);
void process_activate (void);

#endif /* userprog/process.h */
