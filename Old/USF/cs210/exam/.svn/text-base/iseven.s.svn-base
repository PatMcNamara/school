#iseven
#Pat McNamara

	.equ	sys_EXIT, 1
	.equ	sys_WRITE, 4
	.equ	dev_STDOUT, 1

	.section	.data
arg:	.long	0
num:	.ascii	""
numlen:	.int	.-num

evenstr:.ascii	" is an even number\n"
evenlen:.int	.-evenstr

nevenstr:.ascii	" is not an even number\n"
nevenlen:.int	.-nevenstr

nargs:	.ascii	"You didn't give a number\n"
nargslen:.int	.-nargs

	.section	.text
_start:
	cmp	$1, (%esp)
	je	noargs
	mov	8(%esp), %eax
	movl	%eax, arg
	movl	$2, %ebx
	
again:	divl	%ebx
	cmp	$1, %edx
	je	noteven
	cmp	$1, %eax
	jg	again
	je	noteven
	jmp	even
noteven:
	call	loadarg
	lea	num, %ecx
	mov	numlen, %edx
	call	print
	lea	nevenstr, %ecx
	mov	nevenlen, %edx
	call	print
	jmp	quit
even:
	call	loadarg
	lea	num, %ecx
	mov	numlen, %edx
	call	print
	lea	evenstr, %ecx
	mov	evenlen, %eax
	call	print
	jmp	quit

noargs:	lea	nargs, %ecx
	mov	nargslen, %edx
	call	print

quit:	mov	$sys_EXIT, %eax
	mov	$0, %ebx
	int	$0x80

loadarg:
	mov	arg, %eax
	lea	num, %edi
	call	eax2asc
	ret

print:	mov	$sys_WRITE, %eax
	mov	$dev_STDOUT, %ebx
	int	$0x80
	ret

	.global	_start
	.end
