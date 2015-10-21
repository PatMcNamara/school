#-----------------------------------------------------------------
#	easter.s
#-----------------------------------------------------------------

	.equ	sys_EXIT, 1
	.equ	sys_WRITE, 4
	.equ	dev_STDOUT, 1

	.section 	.data
year:	.int	0
month:	.int	1		# initialized to 1 for 
G:	.int	0
C:	.int	0
X:	.int	0
Z:	.int	0
D:	.int	0
E:	.int	0
N:	.int	0


	.section	.text
_start:	cmpl	$2, (%esp)
	jne	noyear
	movl	8(%esp), %esi
	call	asc2eax
	mov	%eax, year
	
#[Golden Number] G=(year%19)+1 1970=14=G
	mov	$19, %ebx
	xor	%edx, %edx
	divw	%bx
	inc	%dl
	mov	%dl, G

#[Century] C=(year/100)+1 1970=20=C
	mov	year, %ax
	mov	$100, %bx
	xor	%dx, %dx
	divw	%bx
	inc	%al
	mov	%al, C
	mov	%al, %cl

#[Corrections] X=(3C/4)-12, Z=((8C+5)/25)-5  1970=3=X  1970=1=Z
	mov	$3, %bl
	mulb	%bl
	mov	$4, %bx
	xor	%dx, %dx
	divw	%bx
	sub	$12, %al
	mov	%al, X
	
	mov	%cx, %ax
	mov	$8, %bl
	mul	%bl
	add	$5, %ax
	mov	$25, %bl
	divb	%bl
	sub	$5, %al
	mov	%al, Z

#[Find Sunday] D=(5Y/4)-X-10  1970=2449=D
	mov	year, %ax
	mov	$5, %bx
	mul	%bx
	mov	$4, %bx
	xor	%dx, %dx
	div	%bx
	sub	X, %ax
	sub	$10, %ax
	mov	%ax, D

#[Epact] E=(11G+20+Z-X)%30  1970=22=E
	mov	G, %eax
	mov	$11, %ebx
	mul	%ebx
	add	$20, %eax
	add	Z, %eax
	sub	X, %eax
	mov	$30, %ebx
	xor	%edx, %edx
	divl	%ebx
	mov	%dl, E
	
	#if (E=25 and G>11) or E=24 then E+1  1970=22=E
	cmp	$24, %dl
	je	add
	cmp	$24, %dl
	jne	fullmoon
	cmp	$11, G
	jae	fullmoon
add:	inc	%dl
	mov	%dl, E

fullmoon:
#[Find full moon] N=44-E if N<21 then N=N+30  1970=22=N
	mov	$44, %al
	sub	%dl, %al
	mov	%al, N
	cmp	$21, %al
	ja	tosun		#cary flag is set so there is no need for conditional
	add	$30, %al
	mov	%al, N

tosun:
#[Advance to Sunday] N=N+7-((D+N)%7)  1970=29=N
#	andw	$0xFF, ax
	add	D, %ax
	mov	$7, %bx
	xor	%dx, %dx
	divw	%bx
	mov	N,%bx
	add	$7, %bx
	sub	%dx, %bx
	mov	%bx, N

#[Get month] if N>31 Easter is on (N-31) April, else Easter on N March  1970=29 March
	cmp	$31, %bx
	jb	display
	sub	$31, %bx
	incb	month			#TODO month incriminted by 7 to point at table 

	.section	.data
msg:	.ascii	"\n In xxxx, Easter Sunday is on"
msglen:	.int	.-msg
monstr:	.ascii	" March April "
buf:	.space	4
blank:	.ascii	"\n\n\r"
buflen: .int	.-buf

	.section	.text
display:
	lea	buf, %edi
	mov	%bx, %ax
	call	eax2asc	

	mov	8(%esp),%ebx
	mov	(%ebx), %eax
	mov	%eax, msg+5

	lea	msg, %ecx
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

#-----------------------------------------------------------------
print:

	push	%eax
	push	%ebx
	mov	$sys_WRITE, %eax
	mov	$dev_STDOUT, %ebx
	int	$0x80
	pop	%ebx
	pop	%eax
	ret
#-----------------------------------------------------------------


#-----------------------------------------------------------------
asc2eax:	
#
# This procedure converts a string of decimal digits found at ESI
# into its numerical value, which it returns in the EAX register.
# (If that value overflows this register, the carry-flag is set.)
#
	pushal				# preserve CPU registers

	# Note: the saved image of register EAX is at 28(%esp)

	movl	$10, %ebx		# setup multiplier in EBX
	movl	$0, 28(%esp)		# image of EAX is cleared
nxasc:	# quit -- unless the next character is a valid digit  
	cmpb	$'0', (%esi)		# does char preceed '0'?
	jb	retok			# yes, end of the digits
	cmpb	$'9', (%esi)		# does char follow '9'?
	ja	retok			# yes, end of the digits
	# OK, convert digit to its 32-bit numeric value in EAX 
	movb	(%esi), %al		# copy the digit into AL
	subb	$'0', %al		# convert digit to number
	andl	$0xFF, %eax		# extend byte to 32-bits
	# add this new number to ten times the prior total  
	xchg	%eax, 28(%esp)		# swap with prior total
	mull	%ebx			# prior total times ten
	addl	%eax, 28(%esp)		# is added to digit-value
	jc	finis			# overflow? exit with CF=1
	# point ESI to the next source-character 
	incl	%esi			# else advance array-index
	jmp	nxasc			# and check the next char

retok:	clc				# clear carry-flag for exit
finis:
	popal				# restore saved registers
	ret				# return control to caller
#-----------------------------------------------------------------

#-----------------------------------------------------------------
eax2asc:	
#
# This procedure converts the 32-bit value found in register EAX 
# into its representation as a string of decimal numerals at EDI.
#
	pushal				# precerve CPU registers

	movl	$10, %ebx		# setup our divisor in EBX	
	xorl	%ecx, %ecx		# use ECX as digit counter
nxdiv:	# loop to generate and push the division-by-ten remainders 
	xorl	%edx, %edx		# extend dividend to qword
	divl	%ebx			# perform division by ten
	pushl	%edx			# save remainder on stack 
	incl	%ecx			# increment digit-counter 
	orl	%eax, %eax		# was the quotient zero?
	jnz	nxdiv			# no, do another division
nxdgt:	# loop to pop and store the digits in reversed order
	popl	%edx			# recover saved remainder
	addb	$'0', %dl		# convert number to ascii 
	movb	%dl, (%edi)		# place numeral in buffer
	incl	%edi			# and advance buffer-index
	loop	nxdgt			# convert another remainder

	popal				# restore saved registers
	ret				# return control to caller 
#-----------------------------------------------------------------


	.global _start
	.end
