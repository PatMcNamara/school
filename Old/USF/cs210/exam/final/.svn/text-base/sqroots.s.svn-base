//----------------------------------------------------------------
//	sqroots.s
//	
//	Will calculate the square-roots of all the number from 
//	1 to 9.
//	
//		to assemble:  $ as sqroots.s -o sqroots.o
//		and to link:  $ ld sqroots.o -o sqroots
//	
//	programmer: Patrick McNamara
//	date begun: 16 MAY 2007
//----------------------------------------------------------------

	# manifest constants
	.equ	sys_EXIT, 1		# system-call ID-number
	.equ	N, 9			# number of square-roots
	
	.section	.data
real:	.float	0.0			# storage for real number
count:	.int	1			# counts the number of loops		

	.section	.text
_start:	# calculates the square root
	finit				# initalize coprocessor
repeat:	fild	count			# load count in ST(0)
	fsqrt				# calculate square-root
	fstp	real			# save result
	fwait				# syncronize processors

	# slightly modified showreal.s program
	.section	.data
temp:	.int	0			# storage for its multiple
tento4:	.int	10000			# decimal-point will shift
msg:	.ascii	" The square-root of "	# legend for output string
msg2:	.ascii	"  is "			# extra space for count
buf:	.ascii	"                  \n"	# buffer for output string
len:	.int	. - msg			# length for output string

	.section	.text
	# multiply the real-number by 10**4
	finit				# initializes coprocessor
	fld	real			# load the real number
	fild	tento4			# load multiplier-factor
	fmulp				# multiply and pop st(0) 
	fistp	temp			# store st(0) as integer
	fwait				# synchronize processors
	
	# convert the 'temp' integer to a decimal digit-string 
	mov	temp, %eax		# temp into accumulator
	mov	$10, %ebx		# and radix into EDX
	mov	$5, %ecx		# setup digit counter
nxdiv:	xor	%edx, %edx		# extend EAX to quadword
	div	%ebx			# perform division by 10
	add	$'0', %dl		# remainder intto ascii
	mov	%dl, buf(%ecx)		# store numeral in buffer
	loop	nxdiv			# again for other digits

	# insert decimal-point
	movb	$'.', buf		# overwrite leading zero
	rolw	$8, buf			# swap character-positions

	# prepair the count for display
	lea	msg2, %edi		# load destination
	mov	count, %eax		# load decimal value
	call	eax2asc			# convert using other function

	# display message
	mov	$4, %eax		# 'write' system-call ID
	mov	$1, %ebx		# standard-output device
	lea	msg, %ecx		# address of message
	mov	len, %edx		# length of message
	int	$0x80			# invoke kernel service
	
	cmp	$N, count		# check if done
	je	exit			# if yes, exit
	incl	count			# if no, increment count
	jmp	repeat			# loop

exit:	# terminate this program
	mov	$sys_EXIT, %eax		# system-call ID-number
	xor	%ebx, %ebx		# return 0 as exit-code
	int	$0x80			# invoke kernel service
	
	
# eax2asc function that converts the decimal count into ascii
# string and puts it into the proper location in message 2 for
# displaying
#-----------------------------------------------------------------
eax2asc:	
#
# This procedure converts the 32-bit value found in register EAX 
# into its representation as a string of decimal numerals at EDI.
#
	pushal				# precerve CPU registers

	movl	$10, %ebx		# setup our divisor in EBX	
	xorl	%ecx, %ecx		# use ECX as digit counter
nxdivd:	# loop to generate and push the division-by-ten remainders 
	xorl	%edx, %edx		# extend dividend to qword
	divl	%ebx			# perform division by ten
	pushl	%edx			# save remainder on stack 
	incl	%ecx			# increment digit-counter 
	orl	%eax, %eax		# was the quotient zero?
	jnz	nxdivd			# no, do another division
nxdgt:	# loop to pop and store the digits in reversed order
	popl	%edx			# recover saved remainder
	addb	$'0', %dl		# convert number to ascii 
	movb	%dl, (%edi)		# place numeral in buffer
	incl	%edi			# and advance buffer-index
	loop	nxdgt			# convert another remainder

	popal				# restore saved registers
	ret				# return control to caller 
#-----------------------------------------------------------------

	.global	_start			# make entry-point public
	.end				# no more to be assembled
