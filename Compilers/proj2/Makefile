CC = gcc -g
LEX = flex
YACC = bison -y
OBJS = ada.o lex.yy.o y.tab.o tree.o

all: ada

ada: $(OBJS)
	$(CC) -o ada $(OBJS)

scantest: lex.yy.c scantest.c
	$(CC) -o scantest scantest.c

lex.yy.o : lex.yy.c 
lex.yy.c: defs.l
	$(LEX) defs.l

y.tab.o : y.tab.c tree.o
y.tab.c : defs.y
	$(YACC) defs.y
y.tab.h : defs.y
	$(YACC) -d defs.y
	
tree.o : tree.c
ada.o : ada.c tree.o

clean:
	-@rm lex.yy.c scantest
	-@rm y.tab.c *.o
	-@rm ada
	-@rm tree.h.gch
	
cleanall: clean
	-@rm y.tab.h y.output