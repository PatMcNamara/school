#include <stdio.h>
#include <stdlib.h>
#include <stddef.h>
#include "lex_yacc.h"
#include "typecheck.h"

extern FILE *yyin;
tree root;

int main (int argc, char **argv) {
	if ((yyin = fopen (argv[1], "r")) == 0L) {
		fprintf (stderr, "%s: Can't open Input File %s\n", argv[0], argv[1]); 
		exit(1);
	}

	yyparse();

	if(root != NULL) {// If there was a syntax error, the tree will be null.
		//printTree (root);
		//printf("\n");
		check(root);
	} else {
		printf("Parsing failed. Exiting.\n");
	}
}