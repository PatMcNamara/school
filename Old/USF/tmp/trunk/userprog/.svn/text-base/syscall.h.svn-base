#ifndef USERPROG_SYSCALL_H
#define USERPROG_SYSCALL_H

#include "threads/synch.h"

struct lock fs_lock;

void syscall_init (void);
int syscall_read(void);
int syscall_write(void);
int syscall_open(const char *);
void syscall_close(int);
void syscall_seek(void);

#endif /* userprog/syscall.h */
