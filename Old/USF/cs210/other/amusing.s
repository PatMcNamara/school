	.equ	sys_EXIT, 1
	.equ	sys_WRITE, 4
	.equ	dev_STDOUT, 1

	.section 	.data
year:	.int	0
month:	.int	1
G:	.int	0
C:	.int	0
X:	.int	0
Z:	.int	0
D:	.int	0
E:	.int	0
N:	.int	0


	.section	.text
_start:
	cmpl	$2, (%esp)
	jne	noyear
	movl	8(%esp), %eax
	mov	%eax, year
	
#[Golden Number] G=(year%19)+1
	mov	$19, %ebx
	xor	%edx, %edx
	divw	%bx
	inc	%dl
	mov	%dl, G

#[Century] C=(year/100)+1
	mov	year, %ax
	mov	$100, %bx
	xor	%dx, %dx
	divw	%bx
	inc	%al
	mov	%al, C
	mov	%al, %cl

#[Corrections] X=(3C/4)-12, Z=((8C+5)/25)-5
	xor	$0xFF, %ax
	mov	$3, %bl
	mulb	%bl
	xor	$0xFF, %ax
	mov	$4, %bx
	divb	%bl
	sub	$12, %al
	mov	%al, X
	
	mov	%cx, %ax
	mov	$8, %bl
	mul	%bl
	add	$5, %ax
	mov	$25, %bl
	divb	%bl
	sub	$5, %ax
	mov	%ax, Z

#[Find Sunday] D=(3C/4)-X-10
	mov	%cx, %ax
	mov	$3, %bx
	mul	%bx
	mov	$4, %bl
	div	%bl
	sub	X, %al
	sub	$10, %al
	mov	%al, D

#[Epact] E=(11G+12+Z-X)%30
	mov	G, %eax
	mov	$11, %ebx
	mul	%ebx
	add	$12, %eax
	add	Z, %eax
	sub	X, %eax
	mov	$30, %ebx
	xor	%edx, %edx
	divl	%ebx
	mov	%dl, E
	
	#if (E=25 and G>11) or E=24 then E+1
	cmp	$24, %al
	je	add
	cmp	$24, %al
	jne	fullmoon
	cmp	$11, G
	jae	fullmoon
add:	inc	%al
	mov	%al, E

fullmoon:
#[Find full moon] N=44-E if N<21 then N=N+31
	sub	$44, %al
	mov	%al, N
	cmp	$21, %al
	jb	tosun
	add	$31, %al
	mov	%al, N

tosun:
#[Advance to Sunday] N=N+7-((D+N)%7)
	add	D, %ax
	mov	$7, %bl
	divb	%bl
	sub	$7, %ah
	add	N, %ah
	mov	%ah, N

#[Get month] if N>31 Easter is on (N-31) April, else Easter on N March
	cmp	$31, %ah
	jae	display
	sub	$31, %ah
	incb	month		#TODO month incriminted by 7 to point at table 

	.section	.data
msg1:	.ascii	"\nIn the year "
dyear:	.ascii	"xxxx"
msg2:	.ascii	" Easter will is on "
msglen:	.int	.-msg1
buf:	.ascii	""
buflen:	.int	.-buf
monstr:	.ascii	" March April "

	.section	.text
display:
	mov	%ah, %al
	xor	$0xFF, %eax
	lea	buf, %edi
	call	eax2asc

	lea	msg1, %ecx
	mov	msglen, %edx
	call	print

	lea	monstr, %ecx
	cmp	$1, month
	je	jump
	add	$6, %ecx
jump:	mov	$7, %edx
	call	print

	lea	buf, %ecx
	mov	buflen, %edx
	call	print
	jmp	exit

	.section	.data
nyear:	.ascii	"You did not enter a year\n\r"
nyearl:	.int	.-nyear

	.section	.text
noyear:	lea	nyear, %ecx
	mov	nyearl, %edx
	call	print

exit:	mov	$sys_EXIT, %eax
	xor	%ebx, %ebx
	int	$0x80

print:	push	%eax
	push	%ebx
	mov	$sys_WRITE, %eax
	mov	$dev_STDOUT, %ebx
	int	$0x80
	pop	%ebx
	pop	%eax
	ret

	.global _start
	.end
