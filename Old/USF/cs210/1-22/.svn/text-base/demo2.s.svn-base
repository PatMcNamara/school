//----------------------------------------------------------------
//	demo2.s
//
//	This is the same computation as in 'demo1.c' but written 
//	in the GNU/Linux assembly language for an x86 processor.
//
//	    assemble using:  $ as demo2.s -o demo2.o
//	    and link using:  $ ld demo2.o sprintf.o -o demo2
//
//	programmer: ALLAN CRUSE
//	written on: 22 JAN 2006 
//----------------------------------------------------------------

	.equ	sys_exit, 1	# mnemonic for kernel service #1
	.equ	sys_write, 4	# mnemonic for kernel service #4
	.equ	device_id, 1	# mnemonic for output device-file

	.section	.data
x:	.int	12			# integer variable: x=4
y:	.int	14			# integer variable: y=5
fmt:	.asciz	"%d + %d = %d \n"	# to specify formatting   

	.section	.bss
z:	.int	0			# integer variable: z=?
n:	.int	0			# integer variable: n=?
buf:	.space	80			# to store output chars 

	.section	.text
_start:	# perform the assignment:  z = x + y; 
	movl	x, %eax		# puts x in register EAX
	addl	y, %eax		# adds y to value in EAX
	movl	%eax, z		# save sum in location z

	# call library-function: len = sprintf( buf, fmt, x, y, z );
	pushl	z		# push argument #5 onto stack
	pushl	y		# push argument #4 onto stack
	pushl	x		# push argument #3 onto stack
	pushl	$fmt		# push argument #2 onto stack
	pushl	$buf		# push argument #1 onto stack
	call	sprintf		# perform 'external' function
	addl	$20, %esp	# discards the five arguments
	movl	%eax, n		# save EAX as our value for n

	# make Linux system-call: write( device_id, &buf, len );
	movl	$sys_write, %eax	# service-ID into EAX
	movl	$device_id, %ebx	# device-ID into EBX
	movl	$buf, %ecx		# buffer address in ECX
	movl	n, %edx			# string length in EDX
	int	$0x80			# transfer to OS kernel
	
fini:	# make Linux system-call: exit( 0 );
	movl	$sys_exit, %eax		# service-ID into EAX
	movl	$0, %ebx		# return-code into EBX
	int	$0x80			# transfer to OS kernel
	
	.global	_start		# tells linker our entry-point
	.end			# tells assembler to stop here

