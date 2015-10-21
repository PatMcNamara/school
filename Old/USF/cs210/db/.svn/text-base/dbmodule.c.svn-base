//-------------------------------------------------------------------
//	dbmodule.c
//
//	Supports /proc kernel-interface for our remote debugger.
//	(dbserver.cpp, dbclient.cpp, dbheader.h, dbmodule.c)
//
//	programmer: ALLAN CRUSE
//	date begun: 08 AUG 2004
//	revised on: 10 AUG 2004 -- added several 'mytrace' commands
//	revised on: 11 AUG 2004 -- call original debug-trap handler 
//	revised on: 12 AUG 2004 -- for Linux kernel version 2.4.20.
//	revised on: 21 AUG 2004 -- added DB_SETGENREGS service-call
//	revised on: 31 AUG 2004 -- disable the 'printk' statements
//	revised on: 09 JAN 2005 -- for Linux kernel version 2.6.9
//	revised on: 20 MAR 2005 -- for Linux kernel version 2.6.10
//	revised on: 28 MAR 2005 -- removed 'dead' development code
//-------------------------------------------------------------------

#include <linux/module.h>	// for init_module() 
#include <linux/proc_fs.h>	// for create_proc_entry()
#include <linux/ptrace.h>	// for PT_DTRACE
#include <linux/pagemap.h>	// for page_cache_release()
#include <asm/uaccess.h>	// for put_user()
#include "dbheader.h"		// for CPUREGS, xtask_t 

// These two macros are added for kernel 2.6.x
#define inc_task_usage(tsk) do { atomic_inc( &(tsk)->usage ); } while(0);
#define dec_task_usage(tsk) do { atomic_dec( &(tsk)->usage ); } while(0);

#define	SUCCESS	0
#define PROC_DIR  NULL		// default directory
#define PROC_MODE 0222		// write-only access
#define MYDEBUGID 0x0ABCDEF0	// module-signature 
#define DEBUG_GATE_ID 1		// IDT gate-umber

typedef struct 	{
		struct thread_info	info;
		struct pt_regs		*reg;
		unsigned long		dbid;	
		CPUREGS			cpu;
		} xtask_t;	


static char modname[] = "dbmodule";
static unsigned short oldidtr[3], newidtr[3];
static unsigned long long *oldidt, *newidt;
static unsigned long kpage, isr_chain;


static int access_task_vm( struct task_struct *tsk, unsigned long addr, 
					void *buf, int len, int how );

static ssize_t
my_write( struct file *file, const char *buf, size_t len, loff_t *pos );

static struct file_operations 
my_fops =	{
		owner:		THIS_MODULE,
		write:		my_write,
		};


asmlinkage void debug_trap_handler( struct pt_regs *esp )
{
	struct pt_regs	*reg = esp;	
	xtask_t		*xtsk;

	//---------------------------------- 	
	// save the current processor state
	//---------------------------------- 	
	xtsk = (xtask_t*)current_thread_info();

	xtsk->dbid = MYDEBUGID;	// write debugger signature
	xtsk->reg = reg;	// save pointer to stacktop
	xtsk->cpu.eax = reg->eax;
	xtsk->cpu.ecx = reg->ecx;
	xtsk->cpu.edx = reg->edx;
	xtsk->cpu.ebx = reg->ebx;
	xtsk->cpu.ebp = reg->ebp;
	xtsk->cpu.esi = reg->esi;
	xtsk->cpu.edi = reg->edi;
	
	xtsk->cpu.ds = reg->xds;
	xtsk->cpu.es = reg->xes;
	
	xtsk->cpu.eflags = reg->eflags;
	xtsk->cpu.cs = reg->xcs;
	xtsk->cpu.eip = reg->eip;

	if ( xtsk->cpu.cs & RPL_MASK ) 
		{
		xtsk->cpu.ss = reg->xss;
		xtsk->cpu.esp = reg->esp;
		}	
	else	{
		asm(" movw %%ss, %0 " : "=m" (xtsk->cpu.ss) );
		xtsk->cpu.esp = ((unsigned long)reg) + 60;
		}
		
	asm(" movw %%fs, %0 " : "=m" (xtsk->cpu.fs) );
	asm(" movw %%gs, %0 " : "=m" (xtsk->cpu.gs) );
	
	asm(" sidt %0 " : "=m" (xtsk->cpu.idtr) );
	asm(" sgdt %0 " : "=m" (xtsk->cpu.gdtr) );
	asm(" sldt %0 " : "=m" (xtsk->cpu.ldtr) );
	asm(" str  %0 " : "=m" (xtsk->cpu.tssr) );

	xtsk->cpu.idtr &= 0x0000FFFFFFFFFFFFLL;
	xtsk->cpu.gdtr &= 0x0000FFFFFFFFFFFFLL;

	asm(" movl %%cr0, %%eax ; movl %%eax, %0 " : "=m" (xtsk->cpu.cr0) );
	asm(" movl %%cr2, %%eax ; movl %%eax, %0 " : "=m" (xtsk->cpu.cr2) );
	asm(" movl %%cr3, %%eax ; movl %%eax, %0 " : "=m" (xtsk->cpu.cr3) );
	asm(" movl %%cr4, %%eax ; movl %%eax, %0 " : "=m" (xtsk->cpu.cr4) );

	asm(" movl %%dr0, %%eax ; movl %%eax, %0 " : "=m" (xtsk->cpu.dr0) );
	asm(" movl %%dr1, %%eax ; movl %%eax, %0 " : "=m" (xtsk->cpu.dr1) );
	asm(" movl %%dr2, %%eax ; movl %%eax, %0 " : "=m" (xtsk->cpu.dr2) );
	asm(" movl %%dr3, %%eax ; movl %%eax, %0 " : "=m" (xtsk->cpu.dr3) );
	asm(" movl %%dr6, %%eax ; movl %%eax, %0 " : "=m" (xtsk->cpu.dr6) );
	asm(" movl %%dr7, %%eax ; movl %%eax, %0 " : "=m" (xtsk->cpu.dr7) );
	
	return;
}	

//------ INTERRUPT SERVICE ROUTINE ------//
asmlinkage void isr_debug( void )
{
asm("	pushl	$-1			");
asm("	pushl	%es			");
asm("	pushl	%ds			");
asm("	pushl	%eax			");
asm("	pushl	%ebp			");
asm("	pushl	%edi			");
asm("	pushl	%esi			");
asm("	pushl	%edx			");
asm("	pushl	%ecx			");
asm("	pushl	%ebx			");
//
asm("	movl	%ss, %eax		");
asm("	movl	%eax, %ds		");
asm("	movl	%eax, %es		");
//
asm("	pushl	%esp 			");
asm("	call	debug_trap_handler	");
asm("	addl	$4, %esp 		");
//
asm("	popl	%ebx			");
asm("	popl	%ecx			");
asm("	popl	%edx			");
asm("	popl	%esi			");
asm("	popl	%edi			");
asm("	popl	%ebp			");
asm("	popl	%eax			");
asm("	popl	%ds			");
asm("	popl	%es			");
asm("	addl	$4, %esp		");
asm("	jmp	*isr_chain		");
}
//---------------------------------------//

void load_IDTR( void *regimage )
{
	asm(" lidtl %0 " : : "m" (*(unsigned short*)regimage ) );
}



int init_module( void )
{
	unsigned long long	oldgate, newgate;
	struct proc_dir_entry	*entry;
	
	printk( "<1>\nInstalling \'%s\' module\n", modname );

	// allocate kernel memory for our new IDT
	kpage = get_zeroed_page( GFP_KERNEL );
	if ( !kpage ) return -ENOMEM;

	// copy the Interrupt Descriptor Table to this page
	asm(" sidtl oldidtr \n sidtl newidtr ");
	memcpy( newidtr+1, &kpage, sizeof( unsigned long ) );
	oldidt = (unsigned long long*)(*(unsigned long*)(oldidtr+1));
	newidt = (unsigned long long*)(*(unsigned long*)(newidtr+1));
	memcpy( newidt, oldidt, 256 * sizeof( unsigned long long ) );

	// initialize debug-handler's chain-address
	oldgate = oldidt[ DEBUG_GATE_ID ];
	oldgate &= 0xFFFF00000000FFFFLL;
	oldgate |= ( oldgate >> 32 );
	oldgate &= 0x00000000FFFFFFFFLL;
	isr_chain = oldgate;

	// prepare new debug-trap gate-descriptor	
	oldgate = oldidt[ DEBUG_GATE_ID ];
	oldgate &= 0x0000FFFFFFFF0000LL;
	newgate = (unsigned long)isr_debug;
	newgate &= 0x00000000FFFFFFFFLL;
	newgate |= ( newgate << 32 );
	newgate &= 0xFFFF00000000FFFFLL;
	newgate |= oldgate;
	newidt[ DEBUG_GATE_ID ] = newgate;
	
	// activate the new IDT on all processors
	load_IDTR( newidtr );
	smp_call_function( load_IDTR, newidtr, 1, 1 );
	//--------------------------------------------------
	entry = create_proc_entry( modname, PROC_MODE, PROC_DIR );	
	entry->proc_fops = &my_fops;
	return	SUCCESS;
}


void cleanup_module( void )
{
	printk( "<1>Removing \'%s\' module\n", modname );
	remove_proc_entry( modname, PROC_DIR );
	smp_call_function( load_IDTR, oldidtr, 1, 1 );
	load_IDTR( oldidtr );
	free_page( kpage );
}

static char pagebuf[ MYPAGESIZE ];

static ssize_t
my_write( struct file *file, const char *buf, size_t len, loff_t *pos )
{
	MYREQ			req;
	int			more;
	int __user		*errorp;
	unsigned long __user	*datap;
	
	struct task_struct	*child = current;
	xtask_t			*xtask;
	struct pt_regs		*reg;
	int			retval = ~SUCCESS;
	
	//--------------------------------------------
	// copy the request-structure from user-space
	//--------------------------------------------
	more = copy_from_user( &req, buf, sizeof( req ) );
	if ( more ) return -EFAULT;
	
//	printk( "<1>  \'%s\' write: ", modname );
//	printk( "cmd=%d pid=%d ", req.cmd, req.pid ); 
//	printk( "addr=%08X data=%08X \n", req.addr, req.data );

	errorp = (int __user *)req.err;
	datap = (unsigned long __user *)req.data;
	
	//-----------------------------
	// validate the 'pid' argument
	//-----------------------------
	if ( req.pid <= 1 ) 
		{
		retval = -EPERM;
		put_user( retval, errorp );
		return	len;
		}

	//------------------------------------	
	// lookup the child's task-descriptor
	//------------------------------------	
	lock_kernel();
	retval = -ESRCH;
	read_lock( &tasklist_lock );
	child = find_task_by_pid( req.pid );
	if ( child ) inc_task_usage( child );
	read_unlock( &tasklist_lock );
	if ( !child ) goto out;
	xtask = (xtask_t*)child->thread_info;
	//------------------------------------	

	//---------------------------------------
	// OK, here is our main switch-statement
	//---------------------------------------
	switch( req.cmd )
		{
		case DB_GETPANE:
		case DB_GETPAGE:
			{
			int		sz, copied;
			unsigned long	addr;

////			printk( "  GETPAGE/GETPANE: pid=%d \n", req.pid );

			memset( pagebuf, 0xFF, MYPAGESIZE );
			
			sz = (req.cmd == DB_GETPAGE) ? MYPAGESIZE : MYPANESIZE;
			addr = req.addr;
			copied = access_task_vm( child, addr, pagebuf, sz, 0 );
			
			retval = -EACCES;
			more = copy_to_user( (void*)datap, pagebuf, sz );
			if ( more ) break;
			
			retval = copied;
			break;
			}			

		case DB_SETDBREG:
			{
			unsigned long	addr;

////			printk( "  SETDBREG: pid=%d \n", req.pid );
			
			retval = -EPERM;
			addr = req.addr;
			if (( addr < 0 )||( addr > 7 )) break;
			child->thread.debugreg[ addr ] = req.data; 
			retval = SUCCESS;
			break;
			}

		case DB_GETALLREGS:
			{
			unsigned long	addr;
			int		sz;

////			printk( "  GETALLREGS: pid=%d \n", req.pid );

			addr = req.addr;
			sz = sizeof( CPUREGS );

			retval = -EACCES;
			if ( xtask->dbid != MYDEBUGID ) break;

			retval = -EIO;
			//spin_lock_irq( &xtask_lock );
			more = copy_to_user( (void*)addr, &xtask->cpu, sz );
			//spin_unlock_irq( &xtask_lock );

			if ( more ) break;
			retval = SUCCESS;

			break;
			}

		case DB_SETGENREGS:
			{
			unsigned long	addr;
			int		sz;

////			printk( "  SETGENREGS: pid=%d \n", req.pid );
			
			addr = req.addr;
			sz = sizeof( CPUREGS );
			
			retval = -EACCES;
			if ( xtask->dbid != MYDEBUGID ) break;

			retval = -EIO;
			more = copy_from_user( &xtask->cpu, (void*)addr, sz );
			if ( more ) break;

			reg = xtask->reg;
			reg->eip = xtask->cpu.eip;
			reg->eax = xtask->cpu.eax;
			reg->ebx = xtask->cpu.ebx;
			reg->ecx = xtask->cpu.ecx;
			reg->edx = xtask->cpu.edx;
			reg->ebp = xtask->cpu.ebp;
			reg->esi = xtask->cpu.esi;
			reg->edi = xtask->cpu.edi;
			reg->xds = xtask->cpu.ds;
			reg->xes = xtask->cpu.es;
			xtask->info.task->thread.fs = xtask->cpu.fs;
			xtask->info.task->thread.gs = xtask->cpu.gs;
			
			retval = SUCCESS;
			break;
			}
		}	
	
	dec_task_usage( child );			
out:	
	unlock_kernel();			
	put_user( retval, errorp );
	return	len;
}	

MODULE_LICENSE("GPL");


// This helper-function provides access to the address-space of another
// process.  It returns the number of bytes successfully transferred.
// The meaning of 'how' is: 0=read, 1=write.  This procedure follows the
// code for 'access_process_vm()' in <kernel/ptrace.c> by Linus Torvalds
// and is replicated here because that kernel function was not exported.

int access_task_vm( struct task_struct *tsk, unsigned long addr, 
					void *buf, int len, int how )
{
	struct mm_struct	*mm;
	struct vm_area_struct	*vma;
	struct page		*page;
	void			*obuf;

	obuf = buf;
	mm = get_task_mm( tsk );
	if ( !mm ) return 0;

	// ignore errors: just check how much was successfully transferred
	down_read( &mm->mmap_sem );
	while ( len )
		{
		int	bytes, ret, offset;
		void	*maddr;

		ret = get_user_pages( tsk, mm, addr, 1, how, 1, &page, &vma );
		if ( ret <= 0 ) break;

		bytes = len;
		offset = addr & (PAGE_SIZE-1);
		if ( bytes > PAGE_SIZE - offset ) bytes = PAGE_SIZE - offset;
		
		maddr = kmap( page );
		if ( how )
			{ // write
			copy_to_user_page( vma, page, addr, 
						maddr+offset, buf, bytes ); 
			set_page_dirty_lock( page );
			}
		else	{ // read
			copy_from_user_page( vma, page, addr, 
						buf, maddr+offset, bytes );
			}

		kunmap( page );
		page_cache_release( page );
		len -= bytes;
		buf += bytes;
		addr += bytes;
		}

	up_read( &mm->mmap_sem );
	mmput( mm );
	return	buf - obuf;
}	
