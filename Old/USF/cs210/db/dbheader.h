//----------------------------------------------------------------
//	myheader.h
//
//	programmer: ALLAN CRUSE
//	date begun: 03 AUG 2004
//	revised on: 08 AUG 2004
//	revised on: 16 AUG 2004 -- to incorporate SNAPSHOT struct
//	revised on: 20 AUG 2004 -- to add 'dmpaddr' to SNAPSHOT
//	revised on: 21 AUG 2004 -- to rearrange SNAPSHOT fields 
//----------------------------------------------------------------

#define MY_PORT 0x9753

#define ILINES	14

#define MAX_CMDTAIL 512
#define MAX_CMDARGS  16

#define MAX_MESSAGE 1500

//-----------------------------------------------------------
// For communication between 'mymodule.c' and 'myserver.cpp'
//-----------------------------------------------------------
#define DB_TRACEME	0
#define DB_PEEKTEXT	1
#define DB_PEEKDATA	2
#define DB_PEEKUSER	3
#define DB_POKETEXT	4
#define DB_POKEDATA	5
#define DB_POKEUSER	6
#define DB_CONTINUE	7
#define DB_KILLTASK	8
#define DB_SINGLESTEP	9
#define DB_GETREGS	12
#define DB_SETREGS	13
#define DB_GETFPREGS	14
#define DB_SETFPREGS	15
#define DB_ATTACH	16
#define DB_DETACH	17
#define DB_GETDBREG	31
#define DB_SETDBREG	32
#define DB_GETALLREGS	33

#define MYPAGESIZE	256
#define MYPANESIZE	32
#define DB_GETPAGE	40
#define DB_GETPANE	41
#define DB_SETGENREGS	42

#define DR_TRAP0 (1<<0)
#define DR_TRAP1 (1<<1)
#define DR_TRAP2 (1<<2)
#define DR_TRAP3 (1<<3)
#define DR_SSTEP (1<<14)
#define TRAP_FLAG (1<<8)
#define RESUME_FLAG (1<<16)
#define RPL_MASK (3<<0)

typedef struct	{
		unsigned long	dr0, dr1, dr2, dr3, dr6, dr7;
		unsigned long	cr0, cr2, cr3, cr4, eip, eflags;
		unsigned long	eax, ebx, ecx, edx, esp, ebp, esi, edi;
		unsigned short	cs, ds, es, fs, gs, ss, ldtr, tssr;
		unsigned long long  gdtr, idtr;
		} CPUREGS;

typedef struct { int *err, cmd, pid; unsigned long addr, data; } MYREQ;

typedef struct 	{
		CPUREGS		cpu;
		unsigned char	stak[ 32 ], code[ 256 ], data[ 32 ];
		unsigned long	effaddr, dmpaddr, taskpid, taskptr;
		unsigned char	memused, addrssz, segname, ilength;	
		unsigned char	usecode, usestak, command, islegal;
		} SNAPSHOT;

