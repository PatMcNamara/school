
all: test_mem listtest

LIBDIR=../lib
KERNELDIR=$(LIBDIR)/kernel
SOURCES=memalloc.c $(KERNELDIR)/list.c
SOURCES1=test_mem.c $(SOURCES)
SOURCES2=listtest.c $(KERNELDIR)/list.c $(LIBDIR)/string.c
SOURCES3=my_test_mem.c $(SOURCES)

all: test_mem listtest my_test_mem

test_mem: $(SOURCES1)
	$(CC) -m32 -g -Wall -I$(LIBDIR) -I$(KERNELDIR) -o $@ $(SOURCES1) -lpthread

listtest: $(SOURCES2)
	$(CC) -m32 -g -Wall -I$(LIBDIR) -I$(KERNELDIR) -o $@ $(SOURCES2) -lpthread

my_test_mem: $(SOURCES3)
	$(CC) -m32 -g -Wall -I$(LIBDIR) -I$(KERNELDIR) -o $@ $(SOURCES3) -lpthread

clean:
	/bin/rm -f test_mem listtest my_test_mem *.o
