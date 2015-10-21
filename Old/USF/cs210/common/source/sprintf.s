//----------------------------------------------------------------
//	sprintf.s
//
//	Here we have written our own (simplified) implementation 
//	for the customary 'sprintf()' library-function:
//
//		 int sprintf( char *dst, char *fmt, ... );
//	
//	By the end of this course you will be able to understand 
//	this code -- but you are unlikely to understand it now.
//
//	     assemble using:  $ as sprintf.s -o sprintf.o
//
//	programmer: ALLAN CRUSE
//	written on: 23 JAN 2005
//----------------------------------------------------------------

	.section	.text
numeral: .ascii	  "0123456789ABCDEF"	# our translation-table
sprintf:
	pushl	%ebp			# preserve frame-pointer
	movl	%esp, %ebp		# setup local stack-frame
	subl	$4, %esp		# create space for radix 
	pushal				# preserve cpu registers
	
	movl	8(%ebp), %edi		# dst parameter into EDI
	movl	12(%ebp), %esi		# fmt parameter into ESI
	movl	$0, %ecx		# initial argument-index

	cld				# use forward processing
again:
	cmpb	$0, (%esi)		# test: null-terminator?
	je	finish			# yes, we are finished

	cmpb	$'%', (%esi)		# test: format-escape?
	je	escape			# yes, insert numerals

	movsb				# else copy the character
	jmp	again			# and go back for another

finish:	subl	8(%ebp), %edi		# compute output length
	movl	%edi, 8(%ebp)		# and save it on stack
	jmp	return			# then exit this function 

escape:	incl	%esi			# skip past escape-code
	lodsb				# and fetch escape-type

	cmpb	$'d', %al		# wanted decimal-format?
	movl	$10, -4(%ebp)		# yes, use 10 as the radix
	je	do_tx			# convert number to string

	cmpb	$'X', %al		# wanted hexadecimal-format?
	movl	$16, -4(%ebp)		# yes, use 16 as the radix
	je	do_tx			# convert number to string

	jmp	errorx			# otherwise return error

do_tx:
	movl	$numeral, %ebx		# point EBX to numeral-list 
	movl	16(%ebp,%ecx,4), %eax	# get next argument in EAX
	incl	%ecx			# and advance argument-index

	pushl	%ecx			# preserve argument-index
	xorl	%ecx, %ecx		# initialize digit-counter
nxdiv:	
	xorl	%edx, %edx		# extend dividend to quadword
	divl	-4(%ebp)		# divide by selected radix
	push	%edx			# push remainder onto stack
	incl	%ecx			# and increment digit-count
	orl	%eax, %eax		# test: quotient was zero?
	jnz	nxdiv			# no, another digit needed
nxdgt:
	popl	%eax			# saved remainder into EAX
	xlat	%cs:(%ebx)		# convert number to numeral 
	stosb				# store numeral in buffer
	loop	nxdgt			# go get the next remainder

	popl	%ecx			# recover the argument-index
	jmp	again			# and resume copying format

errorx:	movl	$-1, 8(%ebp)		# store error-indicator
return:	popal				# restore saved registers
	movl	8(%ebp), %eax		# copy return-value to EAX
	movl	%ebp, %esp		# discard temporary storage 
	popl	%ebp			# restore saved frame-pointer
	ret				# return control to caller

	.global	sprintf			# make entry-point public
	.end				# ignore everything beyond

