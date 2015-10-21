
	.equ	stdin_ID, 0
	.equ	stdout_ID, 1
	.equ	sys_exit, 1
	.equ	sys_read, 3
	.equ	sys_write, 4

	.section	.data
msg1:	.ascii	"\nPlease enter your name: "
len1:	.int	. - msg1
msg2:	.ascii	"\nThank you, "
len2:	.int	. - msg2
buf:	.space	80
maxin:	.int	. - buf

	.section	.text
_start:	
	movl	$sys_write, %eax
	movl	$stdout_ID, %ebx
	leal	msg1, %ecx
	movl	len1, %edx
	int	$0x80

	movl	$sys_read, %eax
	movl	$stdin_ID, %ebx
	leal	buf, %ecx
	movl	maxin, %edx
	int	$0x80

	movl	$sys_write, %eax
	movl	$stdout_ID, %ebx
	leal	msg2, %ecx
	movl	len2, %edx
	int	$0x80

	movl	$sys_write, %eax
	movl	$stdout_ID, %ebx
	leal	buf, %ecx
	movl	maxin, %edx
	int	$0x80

	movl	$sys_exit, %eax
	movl	$0,%ebx
	int	$0x80

	.global	_start
	.end
	