//----------------------------------------------------------------
//	squares.s
//
//	This assembly language program displays a numerical table 
//	showing the squares of the first twenty positive integers
//	with 'add' and 'inc' as its only arithmetical operations. 
//
//	   assemble-and-link using: $ gcc squares.s -o squares
//
//	programmer: ALLAN CRUSE
//	written on: 24 JAN 2006
//----------------------------------------------------------------

	.equ		MAX, 20		# total number of lines 

	.section	.data
head:	.string		"\n     Table of Squares and Cubes\n\n"
body:	.string		"       %2d       %3d     %4d	    \n"
foot:	.string		"\n"		# newline format-string

	.section	.bss
arg:	.space		4		# to hold current number
sqr:	.space		4		# to hold current square
cub:	.space		4		# to hold current cube

	.section	.text
main:	# print the table's title
	pushl		$head		# address of format-string
	call		printf		# call the runtime library
	addl		$4, %esp	# discard the one argument

	# program loop, to print table's body
	movl		$0, cub		# initial value for cube
	movl		$0, sqr		# initial value for square
	movl		$0, arg		# initial value for number
again:	# compute next square
	movl		arg, %eax	# fetch the current number
	addl		%eax, %eax	# compute twice its value
	incl		%eax		# add one to that result
	addl		%eax, sqr	# and add to prior square

	# compute next cube
	movl		$0, %eax	# fetch the current number
	movl		$0, %ecx	# reset counter
squar:	# calculates arg^2
	cmpl		%ecx, arg	# does counter equal number?
	je		endsqr		# yes, jump to endsqr
	addl		arg, %eax	# add number to accumulator
	incl		%ecx		# add one to counter
	jmp		squar		# check square again
endsqr:	movl		%eax, %ebx	# set number^2 equal to ebx
	addl		%eax, %eax 
	addl		%ebx, %eax	# triple number^2
	addl		arg, %eax
	addl		arg, %eax
	addl		arg, %eax	# add 3arg to that
	incl		%eax		# add one to result
	addl		%eax, cub	# and add to previous cube
	# and compute next number
	incl		arg		# increment current number
	# print next line of the table
	pushl		cub		# numerical argument #3
	pushl		sqr		# numerical argument #2
	pushl		arg		# numerical argument #1
	pushl		$body		# format-string argument
	call		printf		# call the runtime library
	addl		$16, %esp	# discard the 4 arguments
	# check the loop's exit-condition
	cmpl		$MAX, arg	# does arg equal maximum?
	je		finis		# yes, jump ahead to finish
	jmp		again		# else display another line

finis:	# print a blank bottom line, and return to the shell 	
	pushl		$foot		# format-string argument
	call		printf		# call the runtime library
	addl		$4, %esp	# now discard the argument

	movl		$0, %eax	# exit-code goes into %eax
	ret				# return control to caller

	.global		main		# make entry-point visible
