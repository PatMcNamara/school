	.equ	sys_EXIT, 1
	.equ	sys_READ, 3
	.equ	sys_WRITE, 4
	.equ	sys_OPEN, 5
	.equ	sys_CLOSE, 6
	.equ	S_ACCESS, 00600
	.equ	O_CREAT, 0100
	.equ	O_TRUNC, 01000
	.equ	O_WRONLY, 01
	.equ	O_APPEND, 02000

	.section	.data
handle:	.int	-1
handler:.int	-1
flags:	.int	O_CREAT | O_WRONLY | O_APPEND
access:	.int	S_ACCESS
fname:	.asciz	"mycookie.txt"
buf:	.ascii	""
buflen: .int	61

oldfile:.asciz	"manual_of_surgery_ascii.txt"

	.section	.text
_start:
	mov	$sys_OPEN, %eax
	lea	fname, %ebx
	mov	flags, %ecx
	mov	access, %edx
	int	$0x80
	mov	%eax, handle
	
	mov	$sys_OPEN, %eax
	lea	open, %ebx
	mov	flags, %ecx
	mov	acces, %edx
	int	$0x80
	mov	%eax, handler
	
	mov	$sys_READ, %eax
	mov	handler, %ebx
	lea	buf, %ecx
	mov	buflen, %edx
	int	$0x80
	
		# write information to the file
	mov	$sys_WRITE, %eax
	mov	handle, %ebx
	lea	buf, %ecx
	mov	size, %edx
	int	$0x80

	mov	$sys_EXIT, %eax
	mov	$0, %ebx
	int	$0x80
	
	.global	_start
	.end
