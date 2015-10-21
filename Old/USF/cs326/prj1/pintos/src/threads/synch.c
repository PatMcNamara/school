/* This file is derived from source code for the Nachos
   instructional operating system.  The Nachos copyright notice
   is reproduced in full below. */

/* Copyright (c) 1992-1996 The Regents of the University of California.
   All rights reserved.

   Permission to use, copy, modify, and distribute this software
   and its documentation for any purpose, without fee, and
   without written agreement is hereby granted, provided that the
   above copyright notice and the following two paragraphs appear
   in all copies of this software.

   IN NO EVENT SHALL THE UNIVERSITY OF CALIFORNIA BE LIABLE TO
   ANY PARTY FOR DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR
   CONSEQUENTIAL DAMAGES ARISING OUT OF THE USE OF THIS SOFTWARE
   AND ITS DOCUMENTATION, EVEN IF THE UNIVERSITY OF CALIFORNIA
   HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

   THE UNIVERSITY OF CALIFORNIA SPECIFICALLY DISCLAIMS ANY
   WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
   WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
   PURPOSE.  THE SOFTWARE PROVIDED HEREUNDER IS ON AN "AS IS"
   BASIS, AND THE UNIVERSITY OF CALIFORNIA HAS NO OBLIGATION TO
   PROVIDE MAINTENANCE, SUPPORT, UPDATES, ENHANCEMENTS, OR
   MODIFICATIONS.
*/

#include "threads/synch.h"
#include <stdio.h>
#include <string.h>
#include "threads/interrupt.h"
#include "threads/thread.h"

//extern bool proc_greater_priority(const struct list_elem *, const struct list_elem *, void *);
//bool sema_elem_greater_priority(const struct list_elem *, const struct list_elem *, void *);

int original_priority;

/* Initializes semaphore SEMA to VALUE.  A semaphore is a
   nonnegative integer along with two atomic operators for
   manipulating it:

   - down or "P": wait for the value to become positive, then
     decrement it.

   - up or "V": increment the value (and wake up one waiting
     thread, if any). */
void
sema_init (struct semaphore *sema, unsigned value) 
{
  ASSERT (sema != NULL);

  sema->value = value;
  list_init (&sema->waiters);
}

/* Down or "P" operation on a semaphore.  Waits for SEMA's value
   to become positive and then atomically decrements it.

   This function may sleep, so it must not be called within an
   interrupt handler.  This function may be called with
   interrupts disabled, but if it sleeps then the next scheduled
   thread will probably turn interrupts back on. */
void
sema_down (struct semaphore *sema) 
{
  enum intr_level old_level;

  ASSERT (sema != NULL);
  ASSERT (!intr_context ());

  old_level = intr_disable ();
  while (sema->value == 0) 
    {
      //list_push_back (&sema->waiters, &thread_current ()->elem);
      list_insert_ordered(&sema->waiters, &thread_current()->elem, proc_greater_priority, NULL);
      //TODO could use a normal insert at head and use of max fnc in list might be better
      thread_block ();
    }
  sema->value--;
  intr_set_level (old_level);
}

/* Down or "P" operation on a semaphore, but only if the
   semaphore is not already 0.  Returns true if the semaphore is
   decremented, false otherwise.

   This function may be called from an interrupt handler. */
bool
sema_try_down (struct semaphore *sema) 
{
  enum intr_level old_level;
  bool success;

  ASSERT (sema != NULL);

  old_level = intr_disable ();
  if (sema->value > 0) 
    {
      sema->value--;
      success = true; 
    }
  else
    success = false;
  intr_set_level (old_level);

  return success;
}

/* Up or "V" operation on a semaphore.  Increments SEMA's value
   and wakes up one thread of those waiting for SEMA, if any.

   This function may be called from an interrupt handler. */
void
sema_up (struct semaphore *sema) 
{
  enum intr_level old_level; 

  ASSERT (sema != NULL);

  old_level = intr_disable ();
  if (!list_empty (&sema->waiters)) 
    thread_unblock (list_entry (list_pop_front (&sema->waiters),
                                struct thread, elem));
  sema->value++;
  intr_set_level (old_level);
  
  thread_yield();
}

static void sema_test_helper (void *sema_);

/* Self-test for semaphores that makes control "ping-pong"
   between a pair of threads.  Insert calls to printf() to see
   what's going on. */
void
sema_self_test (void) 
{
  struct semaphore sema[2];
  int i;

  printf ("Testing semaphores...");
  sema_init (&sema[0], 0);
  sema_init (&sema[1], 0);
  thread_create ("sema-test", PRI_DEFAULT, sema_test_helper, &sema);
  for (i = 0; i < 10; i++) 
    {
      sema_up (&sema[0]);
      sema_down (&sema[1]);
    }
  printf ("done.\n");
}

/* Thread function used by sema_self_test(). */
static void
sema_test_helper (void *sema_) 
{
  struct semaphore *sema = sema_;
  int i;

  for (i = 0; i < 10; i++) 
    {
      sema_down (&sema[0]);
      sema_up (&sema[1]);
    }
}

/* Initializes LOCK.  A lock can be held by at most a single
   thread at any given time.  Our locks are not "recursive", that
   is, it is an error for the thread currently holding a lock to
   try to acquire that lock.

   A lock is a specialization of a semaphore with an initial
   value of 1.  The difference between a lock and such a
   semaphore is twofold.  First, a semaphore can have a value
   greater than 1, but a lock can only be owned by a single
   thread at a time.  Second, a semaphore does not have an owner,
   meaning that one thread can "down" the semaphore and then
   another one "up" it, but with a lock the same thread must both
   acquire and release it.  When these restrictions prove
   onerous, it's a good sign that a semaphore should be used,
   instead of a lock. */
void
lock_init (struct lock *lock)
{
  ASSERT (lock != NULL);

  lock->holder = NULL;
  sema_init (&lock->semaphore, 1);
  lock->priority = -1;
  lock->original_priority = -1;
}

/* Acquires LOCK, sleeping until it becomes available if
   necessary.  The lock must not already be held by the current
   thread.

   This function may sleep, so it must not be called within an
   interrupt handler.  This function may be called with
   interrupts disabled, but interrupts will be turned back on if
   we need to sleep. */
void
lock_acquire (struct lock *lock)
{ 
  int priority;

  ASSERT (lock != NULL);
  ASSERT (!intr_context ());
  ASSERT (!lock_held_by_current_thread (lock));
  
  enum intr_level old_level; 
  old_level = intr_disable ();//TODO 

  // TODO what happens if this thread has the same priority as another thread waiting in a lock but the other lock free's first
  if(!sema_try_down(&lock->semaphore)){
    struct thread *highest_priority_waiter = list_entry(list_begin(&lock->semaphore.waiters), struct thread, elem);
    struct thread *current_holder = lock->holder;
    int priority_before_donation = current_holder->donated_priority;
    
    priority = highest_priority_waiter->priority;
    
    // If your greater then the highest priority thread in the list
      // Check if your of greater priority then the lockholder's donated priority
        //Check if your of greater priority then the lockholder
          // yes, set lockholders prority equal to yours
        // endif
        // yes, set lockholders donated priority to yours
      //endif
    // else, you are of equal priority to the lockholder, even if you are blocked, you must still have the lock
    
    if(thread_current()-> priority > priority) { //TODO the only time this will be false is if this thread has the same priority as the highest waiter
      if(current_holder->donated_priority < thread_current->priority)
        if(current_holder->priority < thread_current()->priority){
          current_holder->priority = thread_current()->priority;
        }
      current_holder->donated_priority = thread_current->priority;
      }
      
      //TODO check if thread is blocked //TODO anytime you set someone's priority you need to make sure that you resort what ever list they are one
    }
    
    sema_down (&lock->semaphore);
    current_holder->priority = priority;
  }
  intr_set_level (old_level);
  lock->holder = thread_current ();
}

/*bool lock_less_priority (const struct list_elem *a, const struct list_elem *b, void *aux){
  struct lock a_lock = list_entry(a, struct lock, elem);
  struct lock b_lock = list_entry(b, struct lock, elem);
  
  if(a->priority = 
}*/

/* Tries to acquires LOCK and returns true if successful or false
   on failure.  The lock must not already be held by the current
   thread.

   This function will not sleep, so it may be called within an
   interrupt handler. */
bool
lock_try_acquire (struct lock *lock)
{
  bool success;

  ASSERT (lock != NULL);
  ASSERT (!lock_held_by_current_thread (lock));

  success = sema_try_down (&lock->semaphore);
  if (success) {
    lock->holder = thread_current ();
  }
  return success;
}

/* Releases LOCK, which must be owned by the current thread.

   An interrupt handler cannot acquire a lock, so it does not
   make sense to try to release a lock within an interrupt
   handler. *///TODO this is not correct.  If an interrupt got a lock from lock_try_acquire then it would need to call release
void
lock_release (struct lock *lock) 
{
  ASSERT (lock != NULL);
  ASSERT (lock_held_by_current_thread (lock));

  lock->holder = NULL;
  sema_up (&lock->semaphore);
  thread_yield();
}

/* Returns true if the current thread holds LOCK, false
   otherwise.  (Note that testing whether some other thread holds
   a lock would be racy.) */
bool
lock_held_by_current_thread (const struct lock *lock) 
{
  ASSERT (lock != NULL);

  return lock->holder == thread_current ();
}

/* One semaphore in a list. */
struct semaphore_elem 
  {
    struct list_elem elem;              /* List element. */
    struct semaphore semaphore;         /* This semaphore. */
  };

/* Initializes condition variable COND.  A condition variable
   allows one piece of code to signal a condition and cooperating
   code to receive the signal and act upon it. */
void
cond_init (struct condition *cond)
{
  ASSERT (cond != NULL);

  list_init (&cond->waiters);
}

/* Atomically releases LOCK and waits for COND to be signaled by
   some other piece of code.  After COND is signaled, LOCK is
   reacquired before returning.  LOCK must be held before calling
   this function.

   The monitor implemented by this function is "Mesa" style, not
   "Hoare" style, that is, sending and receiving a signal are not
   an atomic operation.  Thus, typically the caller must recheck
   the condition after the wait completes and, if necessary, wait
   again.

   A given condition variable is associated with only a single
   lock, but one lock may be associated with any number of
   condition variables.  That is, there is a one-to-many mapping
   from locks to condition variables.

   This function may sleep, so it must not be called within an
   interrupt handler.  This function may be called with
   interrupts disabled, but interrupts will be turned back on if
   we need to sleep. */
void
cond_wait (struct condition *cond, struct lock *lock) 
{
  struct semaphore_elem waiter;

  ASSERT (cond != NULL);
  ASSERT (lock != NULL);
  ASSERT (!intr_context ());
  ASSERT (lock_held_by_current_thread (lock));
  
  sema_init (&waiter.semaphore, 0);
  
  struct semaphore_elem *sema_elem;
  int priority = thread_get_priority();
  struct list_elem *e;

  for (e = list_begin(&cond->waiters); e != list_end(&cond->waiters); e = list_next(e)) {
    sema_elem = list_entry(e, struct semaphore_elem, elem);
    struct list *waiters = &sema_elem->semaphore.waiters;
//    printf("my priority = %d, current semaphore waiter priority = %d, size = %d, location = %p\n", priority, list_entry(list_front(waiters), struct thread, elem)->priority, list_size(waiters), list_front(waiters));
    if(priority > list_entry(list_front(waiters), struct thread, elem)->priority)
      break;
  }
  list_insert(e, &waiter.elem);
/*  for(e = list_begin(waiters); e != list_end(waiters); e = list_next(e))
    if(priority > list_entry(e, struct thread, elem)->priority)
      break;
  list_insert(e, &waiter.elem);*/
  
  
//  list_push_back (&cond->waiters, &waiter.elem);
//  list_insert_ordered (&cond->waiters, &waiter.elem, sema_elem_greater_priority, NULL);
//  printf("elem_inserted, thread's head is at %p\n", thread_current());
  lock_release (lock);
  sema_down (&waiter.semaphore);
  lock_acquire (lock);
}

/* If any threads are waiting on COND (protected by LOCK), then
   this function signals one of them to wake up from its wait.
   LOCK must be held before calling this function.

   An interrupt handler cannot acquire a lock, so it does not
   make sense to try to signal a condition variable within an
   interrupt handler. */
void
cond_signal (struct condition *cond, struct lock *lock UNUSED) 
{
  ASSERT (cond != NULL);
  ASSERT (lock != NULL);
  ASSERT (!intr_context ());
  ASSERT (lock_held_by_current_thread (lock));

  if (!list_empty (&cond->waiters)) 
    sema_up (&list_entry (list_pop_front (&cond->waiters),
                          struct semaphore_elem, elem)->semaphore);
}

/* Wakes up all threads, if any, waiting on COND (protected by
   LOCK).  LOCK must be held before calling this function.

   An interrupt handler cannot acquire a lock, so it does not
   make sense to try to signal a condition variable within an
   interrupt handler. */
void
cond_broadcast (struct condition *cond, struct lock *lock) 
{
  ASSERT (cond != NULL);
  ASSERT (lock != NULL);

  while (!list_empty (&cond->waiters))
    cond_signal (cond, lock);
}

/*bool sema_elem_greater_priority(const struct list_elem *a, const struct list_elem *b, void *aux UNUSED) {
  struct semaphore_elem sema_elem1;
  struct semaphore_elem sema_elem2 = *list_entry(b, struct semaphore_elem, elem);
  
  sema_elem1 = *list_entry(a, struct semaphore_elem, elem);
  
  printf("%p\n", &sema_elem1);
  
  struct semaphore *a_sema = &sema_elem1->semaphore;
  struct semaphore *b_sema = &sema_elem2->semaphore;

  struct semaphore *a_sema;
  struct semaphore *b_sema;
  
  a_sema = &list_entry(a, struct semaphore_elem, elem)->semaphore;
  b_sema = &list_entry(b, struct semaphore_elem, elem)->semaphore;
  
  ASSERT (!list_empty(&a_sema->waiters));
  ASSERT (!list_empty(&b_sema->waiters));
  
  bool tmp = proc_greater_priority(list_front(&a_sema->waiters), list_front(&b_sema->waiters), NULL);
  
  struct thread *a_thread = list_entry(list_head(&a_sema->waiters), struct thread, elem);
  struct thread *b_thread = list_entry(list_head(&b_sema->waiters), struct thread, elem);
 
  printf("a: tid = %d, priority = %d\tb: tid = %d, priority = %d\t",
         a_thread->tid, a_thread->priority, b_thread->tid, b_thread->priority);
  
  if(tmp)
    printf("true\n");
  else
    printf("false\n");
  
  return tmp;
}*/
