#include <stddef.h>
#include <stdlib.h>
#include <string.h>
#include "context.h"

struct STEntry {
	char *name;
	int scope;
	int pos;
	int type;
};

struct STEntry* ST[50];
struct STEntry* STStack[300];
int size = 0;
int tos = 0;
int currentScope = 0;

void push(struct STEntry *);
struct STEntry *pop();

void new_scope() {
	currentScope++;
	push(NULL);
}

void end_scope() {
	while(STStack[tos] != NULL) {//TODO there is likely an off by one error.
		struct STEntry* entry = pop();
		free(ST[entry->pos]);
		ST[entry->pos] = entry;
	}
	pop();
	currentScope--;
}

void declare_var(char* name, int type) { //TODO should this return STEntry?
	struct STEntry* e = malloc(sizeof(struct STEntry));
	e->name = name;
	e->scope = currentScope;
	e->pos = size;
	e->type = type;
	ST[size++] = e;
}

int get_type(char *name) {//TODO isn't type already stored in the tree node? should we remove the tree node and replace with this?
	struct STEntry *entry;
	for(int pos = 0; !strcmp(entry->name, name); entry = ST[++pos])
		;
	if(entry->scope > currentScope) {
		//TODO error, variable not in scope
		return 0;//TODO make a token type
	}
	return entry->type;
}

void push(struct STEntry *entry) {
	STStack[tos++] = entry;
}

struct STEntry *pop() {
	return STStack[--tos];
}


