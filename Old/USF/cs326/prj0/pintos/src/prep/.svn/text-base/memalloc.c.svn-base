/* memalloc.c */

#include<stdio.h>
#include<unistd.h>
#include<pthread.h>
#include"memalloc.h"

extern void exit(int);

struct free_block *find_first_fit(size_t);
struct free_block *find_best_fit(size_t);

void coalesce_next(struct free_block *);

bool less_func(const struct list_elem *, const struct list_elem *, void *);

struct free_block *get_block(struct list_elem *);
struct free_block *next_block(struct free_block *);
struct free_block *prev_block(struct free_block *);

struct list *free_blocks; /* List of free blocks */
pthread_mutex_t lock; /* Lock controles access to free blocks list */
bool first_fit = true; /* Control which allocation stratigy to use */

#define FB_SIZE sizeof(struct free_block)

/* Initialize memory allocator to use 'length' 
   bytes of memory at 'base'. */
void mem_init(uint8_t *base, size_t length) {
	struct free_block *b = (struct free_block *) base;
	
	if((int)(free_blocks = sbrk(sizeof(struct list))) == -1) {
		printf("Could not allocate space for list");
		exit(1);
	}
	
	list_init(free_blocks);
	b->length = length;
	list_push_front(free_blocks, &b->elem);
	pthread_mutex_init(&lock, NULL);
}

/* Allocate 'length' bytes of memory. */
void * mem_alloc(size_t length) {
	struct free_block *b;
	struct used_block *u;

	length += sizeof(struct used_block);
	if(length < FB_SIZE)
		length = FB_SIZE;
	
	pthread_mutex_lock(&lock);
	
	if(first_fit)
		b = find_first_fit(length);
	else
		b = find_best_fit(length);
	
	if(b == NULL){
		pthread_mutex_unlock(&lock);
		return NULL;
	}
	if(b->length - FB_SIZE < length) {
		length = b->length;
		list_remove(&b->elem);
	}
	
	b->length -= length;
	u = (struct used_block *) ((void *) b + b->length);
	u->length = length;
	
	pthread_mutex_unlock(&lock);
	return (void *) &u->data;
}

void use_first_fit(bool fit) {
	pthread_mutex_lock(&lock);
	first_fit = fit;
	pthread_mutex_unlock(&lock);
}

struct free_block *find_first_fit(size_t length) {
	struct list_elem *e;
	
	for (e = list_begin(free_blocks); e != list_end(free_blocks); e = list_next(e)) {
		if(get_block(e)->length >= length)
			return get_block(e);
	}
	return NULL;
}

struct free_block *find_best_fit(size_t length) {
	struct list_elem *e;
	struct free_block *best = NULL;
	
	for (e = list_begin(free_blocks); e != list_end(free_blocks); e = list_next(e)) {
		int e_length = get_block(e)->length;
		
		if(e_length >= length && (best == NULL || e_length < best->length)) {
			best = get_block(e);
			if(e_length == length) { break; }
		}
	}
	return best;
}

/* Free memory pointed to by 'ptr'. */
void mem_free(void *ptr) { 
	struct free_block *new_block = (struct free_block *) (ptr - sizeof(struct used_block));

	pthread_mutex_lock(&lock);
	list_insert_ordered(free_blocks, &new_block->elem, less_func, NULL);
	
	coalesce_next(new_block);
	coalesce_next(prev_block(new_block));
	pthread_mutex_unlock(&lock);
}

void coalesce_next(struct free_block *b) {
	if((void *)b + b->length == next_block(b)) {
		b->length += next_block(b)->length;
		list_remove(&next_block(b)->elem);
	}
}

bool less_func(const struct list_elem *a, const struct list_elem *b, void *aux) {
	return a < b;
}

/* Return the number of elements in the free list. */
size_t mem_sizeof_free_list(void) {
	return list_size(free_blocks);
}

/* Dump the free list.  Implementation of this method is optional. 
 * Prints first lowest adress last, not thread-safe
 */
void mem_dump_free_list(void) {
	if(mem_sizeof_free_list() == 0) {
		printf("Free list is empty\n");
	} else {
		struct list_elem *e;
		
		for (e = list_rbegin(free_blocks); e != list_rend(free_blocks); 
			 e = list_prev(e)) {
			printf("%p, %i\n", get_block(e), get_block(e)->length);
		}
	}
	printf("\n");
}

struct free_block *get_block(struct list_elem *e) {
	return list_entry(e, struct free_block, elem);
}
struct free_block *next_block(struct free_block *b) {
	return get_block(list_next(&b->elem));
}
struct free_block *prev_block(struct free_block *b) {
	return get_block(list_prev(&b->elem));
}
