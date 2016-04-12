#include <stdio.h>
#include "typecheck.h"
#include "context.h"
#include "y.tab.h"

char *id_name(int); // from ada.l

void print_error(char *msg) {
	printf("Incorrect type: %s\n", msg); //TODO add in line numbers.
} 

/* Bitwise AND (&) is used throught. This is to prevent short circuting with logical AND (&&). */
int check_expr(tree t) {
	switch (t->kind) {
		case Plus:
		case Minus:
		case Mul:
		case Div:
			if( check_expr(t->first) == Integer & check_expr(t->second) == Integer ) {
				return Integer;
			} else {
				print_error("Arithmetic operators can only be used on two integers.");//TODO make these messages actually mean something
				return 0; // add this to tokens list
			}
		case Uplus:
		case Uminus:
			if( check_expr(t->first) == Integer ) {
				return Integer;
			} else {
				print_error("Unary operators can only be used on integers.");
				return 0;
			}
		case Or:
		case And:
		case Xor:
		case Not:
			if( check_expr(t->first) == Boolean & check_expr(t->second) == Boolean ) {
				return Boolean;
			} else {
				print_error("Logical operators can only be used on two booleans.");
				return 0;
			}
		case Eq:
		case Neq:
			if( check_expr(t->first) == Boolean & check_expr(t->second) == Boolean ) {
				return Boolean;
			} // ELSE FALL THROUGH
		case Gth:
		case Geq:
		case Lth:
		case Leq:
			if( check_expr(t->first) == Integer & check_expr(t->second) == Integer ) {
				return Boolean;
			} else {
				print_error("Bad comparison.");
				return 0;
			}
		case Iconst:
			return Integer;
		case Ident:
			//return get_type(t->value);
			return t->value;
		default:
			print_error("Token not type checked.");
			return 0;
	}
}

void check_stmt(tree t) {//TODO read and ignore the procedure and procedure ident
	for( ; t != NULL; t = t->next ) {
		switch (t->kind) {
			case If:
			case Elseif:
				if( check_expr(t->first) == Boolean ) {
					check_stmt(t->second);
					break;
				} else {
					print_error("Invalid IF statement.");
				}
				break;
			case Assign:
				if(get_type(id_name(t->first)) != check_expr(t->second)) {
					print_error("Invalid assignment statement.");
				}
				break;
			case Declare:
				//TODO there are multiple declares possible in a single tree.
				declare_var(id_name(t->first->value), t->value);
				break;
			default:
				print_error("Token not type checked.");
				break;
		}
	}
}