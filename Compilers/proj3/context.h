#include <stdbool.h>

void new_scope();
void end_scope();
void declare_array(char*, int);
void declare_var(char*, int);
int get_type(char*);
bool is_array(char*);