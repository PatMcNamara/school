#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include<ctype.h>
#include<fcntl.h>
#include<unistd.h>

#include<sys/wait.h>
#include<sys/stat.h>

#define INIT_BUFF_SIZE 100

int main(int argc, char *argv[]){
	char *input,
	     *filename,
	     *arglist,
	     **args,
	     *pname,
	     *buff,
	     *tmp;
	int i, numargs, child;
	
	while(1){
		printf("& ");
		
		// get input
		buff = malloc(INIT_BUFF_SIZE);
		fgets(buff, INIT_BUFF_SIZE, stdin);
		
		// enlarge and refill buffer until all input read
		for(i=1; strlen(buff) == INIT_BUFF_SIZE * i; i++){
			buff = realloc(buff, INIT_BUFF_SIZE * (i+1));
			fgets(buff + (INIT_BUFF_SIZE*i - 1), INIT_BUFF_SIZE, stdin);
		}
		input = buff;
		
		// strip newline
		input[strlen(input)-1] = '\0';
		
		// parse program name, arguments and redirect filename
		pname = strsep(&input, " ");
		arglist = strsep(&input, ">");
		filename = strsep(&input, "\n");
		
		// if exit command, return
		if(strcmp(pname, "exit") == 0)
			return(0);
		
		// remove space from filename
		if(filename != NULL && isspace(filename[0]))
			filename = &(filename[1]);
		
		// calculate number of args and malloc space for them
		for(tmp=arglist, numargs=1; tmp != NULL && strlen(tmp) > 0; numargs++)
			tmp = strrchr(tmp+1, ' ');
		args = malloc(sizeof(char *) * (numargs+1));
		
		// fill arg array
		args[0] = pname;
		for(i=1; i<numargs; i++)
			args[i] = strsep(&arglist, " ");
		args[i] = NULL;
		
		// fork and exec process
		child = fork();
		if(child == 0){
			if(filename != NULL)
				freopen(filename, "w", stdout);
			execvp(pname, args);
			fprintf(stderr, "There was an error executing the program\n");
			exit(1);
		}
		wait(NULL);
		
		// free memory
		free(args);
		free(buff);
	}
}
