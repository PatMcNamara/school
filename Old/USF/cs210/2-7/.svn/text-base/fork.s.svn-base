	.section .text
_start:
nxfork:
	mov	$2,%eax
	int	$0x80
	jmp	nxfork

	.global	_start
	.end
