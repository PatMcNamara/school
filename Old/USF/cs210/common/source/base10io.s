//----------------------------------------------------------------
//	base10io.s
//
//	This file defines a pair of generally useful subroutines
//	for converting between numerical data and digit-strings. 
//	The 'eax2asc' procedure converts a number into a decimal
//	string, and the 'asc2eax' procedure converts a string of
//	decimal numerals into the numerical value it represents.
//	
//	     assemble using:  $ as base10io.s -o base10io.o
//
//	programmer: ALLAN CRUSE
//	written on: 27 FEB 2006
//----------------------------------------------------------------


	.section	.text
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

	.global	eax2asc, asc2eax	# make procedures visible
	.end				# nothing more to assemble

