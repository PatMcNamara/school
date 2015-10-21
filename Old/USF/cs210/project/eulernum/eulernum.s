//----------------------------------------------------------------
//	eulernum.s
//	
//	Calculates Euler's number (e) and displays the first 4
//	digits of the value.
//	
//		to assemble:  $ as eulernum.s -o eulernum.o
//		and to link:  $ ld eulernum.o -o eulernum
//	
//	programmer: Patrick McNamara
//	date begun: 01 MAY 2007
//----------------------------------------------------------------

	# manifest constants
	.equ	sys_EXIT, 1		# system-call ID-number
	.equ	N_TERMS, 9		# number of iterations

	.section	.data
n:	.int	1			# iteration count
result:	.single	0.0

	.section	.text
_start:	# initialize and setup FPU
	finit				# initialize coprocessor
	fld1				# initial total is one for 1/0!
	fld1				# initial factorial value
	mov	$N_TERMS, %ecx		# setup the loop-counter
	
nxtint:	# calculate e
	fild	n			# push count on to ST
	fmulp				# calc next factorial and pop
	fld1				# push 1 on ST to set up for 1/n!
	fdiv	%st(1)			# calc 1/n! and...
	faddp	%st(2)			# ...add result to running total
	incl	n			# increment counter
	loop	nxtint			# next iteration

	# get e from FPU and prepair to print
	fincstp				# increment st to point to result
	fst	result			# get result from ST
	fwait				# synchronize processors

	mov	result, %eax		# move results into eax

//----------------------------------------------------------------
//	Modified version of flt2asc.s
//
//	This function displays the single-precision floating-point
//	value in eax as a signed decimal fraction rounded to 4 places.
//
//	programmer: ALLAN CRUSE
//	written on: 27 MAR 2006 
//	revised on: 28 MAR 2006 -- reorganized algorithm steps 
//----------------------------------------------------------------

	.equ	PLACES, 4		# count of decimal places
	.equ	BIAS, 127		# single-precision's bias

	.section	.data
ten:	.long	10			# radix of decimal-system
sign:	.ascii	"+-"			# list of sign characters
msg:	.ascii	"\n      e = "		# output string's legend
buf:	.ascii	"        \n\n"		# output-string's digits
len:	.int	. - msg			# length of output string

	.section	.text
flt2asc:
	# prepare to format the character-sequence in 'buf' 	
	lea	buf, %edi		# point RDI to output buffer

	# extract the mantissa and save it in register EBP
	mov	$0x00800000, %ebp	# setup 2**23 as a divisor
	xor	%edx, %edx		# extend EAX to a quadword
	div	%ebp			# remainder is the mantissa
	or	%edx, %ebp		# if leading 1 is prepended

	# separate the sign-bit from the biased exponent 	
	mov	$0x100, %ebx		# setup 2**8 as a divisor
	xor	%edx, %edx		# extend EAX to a quadword
	div	%ebx			# remainder is biased expo
	sub	$BIAS, %edx		# so adjust the remainder	

	# output the sign-character
	mov	sign(%eax), %al		# fetch the sign-character
	mov	%al, (%edi)		# store the sign-character
	inc	%edi			# advance buffer pointer

	# next we need to multiply the mantissa by 10**PLACES, then
	# divide the (quadword) product by 2**(23-expo) and finally
	# round our quotient upward in case 2*remainder >= divisor.
	
	# prepare 2**(23-expo) in RBX using logical left-shifts 
	mov	$1, %ebx		# setup 2**0 in EBX
	mov	$23, %ecx		# setup loop-count in ECX
	sub	%edx, %ecx		# minus unbiased exponent 
	js	exit			# needs nonnegative count  
	test	$~31, %ecx		# does count surpass 31?
	jnz	exit			# yes, out-of-range shift  
	shl	%cl, %ebx		# else EBX = 2**(31-expo)
	
	# prepare 10**PLACES in EAX using multiplications by ten
	mov	$1, %eax		# setup 10**0 in EAX
	mov	$PLACES, %ecx		# setup loop-count in ECX
mul10:	mull	ten			# multiply EAX by ten
	loop	mul10			# according to loop-count

	# do the multiplication, then the division, then rounding
	mul	%ebp			# 10**PLACES times mantissa
	div	%ebx			# divided by 2**(23-expo)
	add	%edx, %edx		# double the remainder
	sub	%ebx, %edx		# subtract the divisor
	cmc				# flip the 'borrow' bit
	adc	$0, %eax		# add bit to quotient

	# convert EAX to decimal with at least 1+PLACES digits
	xor	%ecx, %ecx		# initial digit-count
nxdivx:	xor	%edx, %edx		# extend EAX to octaword
	divl	ten			# divide by the radix
	push	%edx			# push the remainder
	inc	%ecx			# count this digit
	or	%eax, %eax		# quotient is zero?
	jnz	nxdivx			# no, divide again
	cmp	$PLACES, %ecx		# enough digits yet?
	jbe	nxdivx			# no, divide again
nxdgtx:	pop	%edx			# recover remainder
	or	$'0', %dl		# convert to numeral
	mov	%dl, (%edi)		# put digit in buffer
	inc	%edi			# advance the pointer
	loop	nxdgtx			# again if more digits

	# shift a decimal-point leftward past PLACES digits
	movb	$'.', (%edi)		# append decimal-point
	mov	$PLACES, %ecx		# setup shift counter	
nxrol:	dec	%edi			# back up the pointer
	rolw	$8, (%edi)		# swap character bytes
	loop	nxrol			# again if more places

	# display the signed decimal number
	mov	$4, %eax		# service-ID for 'write'
	mov	$1, %ebx		# device-ID for screen
	lea	msg, %ecx		# message's address
	mov	len, %edx		# message's length
	int	$0x80			# invoke Linux service
	
exit:	# terminate this program
	mov	$sys_EXIT, %eax		# system-call ID-number
	xor	%ebx, %ebx		# return 0 as exit-code
	int	$0x80			# invoke kernel service

	.global	_start			# make entry-point public
	.end				# no more to be assembled
