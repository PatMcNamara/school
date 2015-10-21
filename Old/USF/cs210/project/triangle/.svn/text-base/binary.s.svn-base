#------------------------------------------------------------------------------
#	binary.s
#
#	This program recursively calculates the values in a pascal triangle.
#	The calculated values are actualy printed by the triangle.ccp program.
#
#		$ as binary.s -o binary.o
#		$ g++ triangle.cpp triangle.o -o triangle
#
#	Programmed by: Patrick McNamara
#------------------------------------------------------------------------------


	.global	bin		# make label visible to cpp program

	.equ	K, 12		# location of var K in relation to esp
	.equ	N, 8		# location of var N in relation to esp

	.section	.text
bin:
        push    %ebp            # preserve previous base pointer
        movl    %esp, %ebp      # setup new base pointer
        push    %edx		# save register contents

	mov	K(%ebp), %eax

	# check first base case (n<0 or k<0 or k>n)
        cmpl    $0, N(%ebp)	# n < 0?
        jl	ret0		# yes, return 0
	cmpl	$0, %eax	# k < 0?
	jl	ret0		# yes, return 0
	cmpl	N(%ebp), %eax	# k > n?
	jg	ret0		# yes, return 0

	# check second base case (k=0 or k=n)
	cmpl	$0, %eax	# j = 0?
	je	ret1		# yes, return 1
	cmpl	%eax, N(%ebp)	# k = n?
	je	ret1		# yes, return 1

	jmp	recur		# recursive call

ret0:
	mov	$0, %eax	# set return value to 0
	jmp	binx		# exit

ret1:
	movl    $1, %eax        # set return value to 1
        jmp     binx		# exit

recur:	# return  bin( n-1, k ) + bin( n-1, k-1 )
	push	%eax		# sets up k param

	mov	N(%ebp), %eax	# sets n param
	dec	%eax		# n-1
	push	%eax		# puts n-1 onto stack
	call	bin		# bin( n-1, k )
	mov	%eax, %edx	# saves return value in edx
	
	decl	4(%esp)		# k-1
	call	bin		# bin( n-1, k-1 )
	add	%edx, %eax	# puts value of recursive function into eax

	add	$8, %esp	# takes recursive params off stack

binx:
	pop	%edx		# restore edx
	pop	%ebp		# restore previous base pointer
	ret			# return control to caller

	.end
