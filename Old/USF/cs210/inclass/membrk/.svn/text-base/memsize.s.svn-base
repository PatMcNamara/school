/* 
 * 	Finds total amount of memory that you can allocate (how much space
 *	is available on the heap)
 */

	.equ	sys_BRK, 45
	.equ	sys_WRITE, 4
	.equ	sys_EXIT, 1
	.equ	STDOUT, 1

	.section	.data
brk1:	.long	0
mem:	.long	0
value:	.long	0x10000000
buf:	.ascii	"xxxxxxxx \n"
buflen:	.int	. - buf

	.section	.text
_start:
	xor	%ebx, %ebx
	mov	$sys_BRK, %eax
	int	$0x80

	mov	%eax, brk1

loop:	mov	mem, %ebx
	add	brk1, %ebx
	add	value, %ebx

	mov	$sys_BRK, %eax
	int	$0x80

	cmp	%eax, %ebx
	jne	shift

	mov	value, %eax
	add	%eax, mem

	jmp	loop

shift:	cmpl	$1, value
	je	endloop
	
	shrl	$1, value
	jmp	loop

endloop:
	mov	mem, %eax
	lea	buf, %edi
	call	eax2hex
	
	mov	$sys_WRITE, %eax
	mov	$STDOUT, %ebx
	lea	buf, %ecx
	mov	buflen, %edx
	int	$0x80

	mov	$sys_EXIT, %eax
	mov	$0, %ebx
	int	$0x80

	.section	.data
hex:	.ascii	"0123456789ABCDEF"

	.section	.text
eax2hex:
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
