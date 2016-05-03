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
	char s[40];
	switch(root->kind) {
		case Plus:
			gen_expr(root->first);
			gen_expr(root->second);
			put_code("ADDI ", 1);
			return;
		case Minus:
			gen_expr(root->first);
			gen_expr(root->second);
			put_code("SUBI ", 1);
			return;
		case Mul:
			gen_expr(root->first);
			gen_expr(root->second);
			put_code("MULI ", 1);
			return;
		case Div:
			gen_expr(root->first);
			gen_expr(root->second);
			put_code("DIVI ", 1);
			return;
			
		case And:
			gen_expr(root->first);
			gen_expr(root->second);
			put_code("ANDI ", 1);
			return;
		case Or:
			gen_expr(root->first);
			gen_expr(root->second);
			put_code("ORW ", 1);
			return;
		case Xor:
			gen_expr(root->first);
			gen_expr(root->second);
			put_code("XORW ", 1);
			return;
		case Not:
			gen_expr(root->first);
			put_code("NOTW ", 1);
			return;
			
		case Eq:
			gen_expr(root->first);
			gen_expr(root->second);
			put_code("SUBI ", 1);
			put_code("TSTEQI ", 1);
			return;
		case Neq:
			gen_expr(root->first);
			gen_expr(root->second);
			put_code("SUBI ", 1);
			put_code("TSTEQI ", 1);
			put_code("NOTW ", 1);
			return;
		case Geq:
			gen_expr(root->first);
			gen_expr(root->second);
			put_code("SUBI ", 1);
			put_code("TSTLTI ", 1);
			put_code("NOTW ", 1);
			return;
		case Gth:
			gen_expr(root->first);
			put_code("PUSHW 1 ", 2); // Subtract 1 from the second term to transform to greater then or equal to
			put_code("SUBI ", 1);
			gen_expr(root->second);
			put_code("SUBI ", 1);
			put_code("TSTLTI ", 1);
			put_code("NOTW ", 1);
			return;
		case Leq:
			gen_expr(root->first);
			put_code("PUSHW 1 ", 2); // Subtract 1 from the second term to transform to strictly less than.
			put_code("SUBI ", 1);
			gen_expr(root->second);
			put_code("SUBI ", 1);
			put_code("TSTLTI ", 1);
			return;
		case Lth:
			gen_expr(root->first);
			gen_expr(root->second);
			put_code("SUBI ", 1);
			put_code("TSTLTI ", 1);
			return;		
			
		case Iconst:
			//push_code("PUSHL ", 1)
			sprintf(s, "PUSHW %d ", root->value);
			put_code(s, 2);
			return;
		case Ident:
			sprintf(s, "PUSHW %d GETSW ", get_addr(root->value));
			put_code(s, 2);
			return;
		default:
			printf("Not implemented in gen_expr.\n");
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
						declare_array(cur->value, t->second->second->kind, t->second->first->first->value, t->second->first->second->value);
					}
				} else {
					//printf("Declaring var.\n");
					for(tree cur = t->first; cur != NULL; cur = cur->next) {
						declare_var(cur->value, t->second->kind);
					}
				}
				//printf("In declare.\n");
				continue;
			case Assign:;
				char s[20];
				sprintf(s, "PUSHW %d ", get_addr(t->first->value));
				put_code(s, 2);
				//printf("In assign.\n");
				//gen_expr(t->first);
				gen_expr(t->second);
				//find_addr(t->first);
				put_code("PUTSW ", 1);
				continue;
			default:
				printf("Not implemented in generate.");
				printTree(t);
				printf("\n");
		}
	}
}