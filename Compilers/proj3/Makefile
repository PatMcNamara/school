CC = gcc -g
LEX = flex
YACC = bison -y
OBJS = main.o lex.yy.o context.o typecheck.o y.tab.o tree.o

all: ada

ada: $(OBJS)
	$(CC) -o ada $(OBJS)

scantest: lex.yy.c scantest.c
	$(CC) -o scantest scantest.c

lex.yy.o : lex.yy.c 
lex.yy.c: ada.l
	$(LEX) ada.l

y.tab.o : y.tab.c tree.o
y.tab.c : ada.y
	$(YACC) ada.y
y.tab.h : ada.y
	$(YACC) -d ada.y
	
typecheck.o : typecheck.c context.c
tree.o : tree.c
main.o : main.c tree.o typecheck.o

clean:
	-@rm lex.yy.c scantest
	-@rm y.tab.c *.o
	-@rm ada
	-@rm tree.h.gch
	
allclean: clean
	-@rm y.tab.h y.output