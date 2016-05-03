#include <stdio.h>
#include "out.h"

extern FILE *output;
int LC = 0;
//bool in_data_section = false;

// Puts the designated string into the output file without updating LC
void print(char *str) {
	printf("putting %s into file.\n", str);
	fprintf(output, "%s", str);
}

// Puts the designated string into the output file and updates LC by size.
void put_code(char *opcode, int size) {
	print(opcode);
	LC += size;
}