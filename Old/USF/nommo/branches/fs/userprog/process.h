#ifndef USERPROG_PROCESS_H
#define USERPROG_PROCESS_H

#include <list.h>
#include <hash.h>
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
    struct hash_elem elem;
  };

typedef int fd_t;
/* A file descriptor */
struct fd_elem
{
   fd_t fd;
   struct file *file;
   struct hash_elem hash_elem;
};

void process_init (struct thread *);
tid_t process_execute (const char *file_name);
int process_wait (tid_t);
void process_exit (void);
void process_activate (void);

hash_hash_func tid_hash;
hash_less_func tid_less;

#endif /* userprog/process.h */
