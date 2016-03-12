CC = gcc -g
LEX = flex

all: scantest

lex.yy.c: defs.l
	$(LEX) defs.l
	
scantest: lex.yy.c scantest.c
	$(CC) -o scantest scantest.c
	
clean:
	-@ rm lex.yy.c scantest