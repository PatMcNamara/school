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
head:	.string		"\n     Table of Squares\n\n"
body:	.string		"       %2d       %3d     \n"
foot:	.string		"\n"		# newline format-string

	.section	.bss
arg:	.space		4		# to hold current number
val:	.space		4		# to hold current square

	.section	.text
main:	# print the table's title
	pushl		$head		# address of format-string
	call		printf		# call the runtime library
	addl		$4, %esp	# discard the one argument

	# program loop, to print table's body
	movl		$0, val		# initial value for square
	movl		$0, arg		# initial value for number
again:	# compute the next square 
	movl		arg, %eax	# fetch the current number
	addl		%eax, %eax	# compute twice its value
	incl		%eax		# add one to that result
	addl		%eax, val	# and add to prior square
	# and compute next number
	incl		arg		# increment current number
	# print next line of the table
	pushl		val		# numerical argument #2
	pushl		arg		# numerical argument #1
	pushl		$body		# format-string argument
	call		printf		# call the runtime library
	addl		$12, %esp	# discard the 3 arguments
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
