#------------------------------------------------------------------------------
#	expo.s
#
#	Implements expo function used in exponent.cpp.  Calculates n^k using
#	recusion.	
#		implements C++ funtion: int expo(int n, int k)
#		assemble using: as expo.s -o expo.o
#
#	programmer: Patrick McNamara
#	written on: 19 MAR, 2007
#------------------------------------------------------------------------------

	.global	expo		# entry-point public

	# The second argument is going to be above the first
	.equ	N, 8
	.equ	K, 12

	.section	.text
expo:
	push	%ebp		# preserve previous base pointer
	movl	%esp, %ebp	# setup new base pointer
	push	%edx		# save register contents

	cmpl	$0, K(%ebp)	# base case?
	jne	recur		# no, recurse

	movl	$1, %eax	# yes, set return value to 1
	jmp	expox		# exit

recur:
	movl	K(%ebp), %eax	# get k
	dec	%eax		# decrease k by 1
	push	%eax		# make k first arg in next recursion

	movl	N(%ebp), %edx	# get n
	push	%edx		# make n second arg in next recursion

	call	expo		# call recursive function
	add	$8, %esp	# take 2 arguments off the stack

	mull	N(%ebp)		# n * n^(k-1)
expox:
	pop	%edx		# recover reg components
	pop	%ebp		# recover base pointer
	ret			# return to caller

	.end
