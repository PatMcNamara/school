//-----------------------------------------------------------------
//	brkdemo.s
//
//	This program demonstrates the effect of directly invoking
//	the Linux kernel's 'brk' system-call to modify the length
//	of an application program's data-section during run-time. 
//
//	  	to assemble:  $ brkdemo.s -o brkdemo.o
//	  	and to link:  $ brkdemo.o -o brkdemo
//
//	programmer: ALLAN CRUSE
//	written on: 28 MAR 2007
//-----------------------------------------------------------------

	# manifest constants
	.equ	sys_BRK, 45
	.equ	sys_WRITE, 4
	.equ	sys_EXIT, 1
	.equ	STDOUT,	1
	.equ	MEM_SIZE, 0x100

	.section	.data
brk1:	.long	0			# stores original address
brk2:	.long	0			# stores modified address
msg1:	.ascii	"original brk-value: "	# legend for message one
buf1:	.ascii	"xxxxxxxx \n"		# buffer for message one
len1:	.int	. - msg1		# length for message one
msg2:	.ascii	"modified brk-value: "	# legend for message two
buf2:	.ascii	"xxxxxxxx \n"		# buffer for message two
len2:	.int	. - msg2		# length for message two

	.section	.text
_start:	
	# get the original value for the brk-address
	mov	$sys_BRK, %eax
	xor	%ebx, %ebx
	int	$0x80
	mov	%eax, brk1

	# format the original brk-address for display
	mov	brk1, %eax
	lea	buf1, %edi
	call	eax2hex

	# display the original brk-address
	mov	$sys_WRITE, %eax
	mov	$STDOUT, %ebx
	lea	msg1, %ecx
	mov	len1, %edx
	int	$0x80

	# set the modified value for the brk-address
	mov	$sys_BRK, %eax
	mov	brk1, %ebx
	add	MEM_SIZE, %ebx
	int	$0x80
	mov	%eax, brk2

	# format the modified brk-address for display
	mov	brk2, %eax
	lea	buf2, %edi
	call	eax2hex

	# display the modified brk-address
	mov	$sys_WRITE, %eax
	mov	$STDOUT, %ebx
	lea	msg2, %ecx
	mov	len2, %edx
	int	$0x80

	# terminate this program
	mov	$sys_EXIT, %eax
	mov	$0, %ebx
	int	$0x80

hex:	.ascii	"0123456789ABCDEF"

eax2hex: # convert the value in EAX to a hexadecimal string at EDI
	pushal
	mov	$8, %ecx
nxnyb:	rol	$4, %eax
	mov	%al, %bl
	and	$0x0F, %ebx
	mov	hex(%ebx), %dl
	mov	%dl, (%edi)
	inc	%edi
	loop	nxnyb
	popal
	ret

	.global	_start
	.end

