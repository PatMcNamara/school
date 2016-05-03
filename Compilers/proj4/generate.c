#include <stddef.h>
#include <stdio.h>
#include "tree.h"
#include "generate.h"
#include "y.tab.h"
#include "context.h"
#include "out.h"
char *id_name(int); // from ada.l

int find_addr(tree t) {
	
}

void gen_expr(tree root) {
	switch(root->kind) {
		case Iconst:;
			//push_code("PUSHL ", 1)
			char s[20];
			sprintf(s, "PUSHL %d ", root->value);
			put_code(s, 2);
			return;
		case Ident:
			
		default:
			printf("Not implemented in gen_expr.");
			printTree(root);
			printf("\n");
	}
}

void generate(tree root) {
	//printf("Entered generate\n");
	
	for(tree t = root; t != NULL; t = t->next) {
		switch(t->kind) {
			case Procedure:
				printTree(t);
				print(".DATA\n");
				generate(t->second);
				print("\n.CODE\n0: "); // Assumes there is only one procedure in the program
				generate(t->third);
				print("HALT .ENTRY 0\n"); // Assumes program starts at 0:
				
				end_scope();
				continue;
			case Declaration:
				if(t->second->kind == Array) {
					for(tree cur = t->first; cur != NULL; cur = cur->next) {
						declare_array(id_name(cur->value), t->second->second->kind, t->second->first->first->value, t->second->first->second->value);
					}
				} else {
					//printf("Declaring var.\n");
					for(tree cur = t->first; cur != NULL; cur = cur->next) {
						declare_var(id_name(cur->value), t->second->kind);
					}
				}
				//printf("In declare.\n");
				continue;
			case Assign:
				//printf("In assign.\n");
				gen_expr(t->second);//generate?
				find_addr(t->first);
				put_code("PUT ", 1);
				continue;
			default:
				printf("Not implemented in generate.");
				printTree(t);
				printf("\n");
		}
	}
}