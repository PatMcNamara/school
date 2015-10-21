//------------------------------------------------------------------------------
//	skeleton.s
//
//	Creates template code that can be used when creating a new assembly
//	source file.
//
//		assemble using: skeleton.s -o skeleton.o
//		and link using: skeleton.o -o skeleton
//		
//	programmer: Patrick McNamara
//	written on: 4/12/2006
//
//------------------------------------------------------------------------------

	# Manifiest Constants
	.equ	sys_EXIT, 1
	.equ	sys_WRITE, 4
	.equ	sys_OPEN, 5
	.equ	sys_CLOSE, 6
	.equ	sys_BRK, 45
	.equ	STDOUT, 1
	.equ	S_ACCESS, 00666
	.equ	O_CREAT, 0100
	.equ	O_WRONLY, 01
	.equ	MAX_ARG_LEN, 12

	.section	.text
_start:	# check arguments
	cmpl	$1, (%esp)
	je	error
	mov	$MAX_ARG_LEN, %ecx	#set counter to largest file name length
	mov	8(%esp), %ebx		# set location of argument
	dec	%ecx			# begin with second letter
nxtchr:	# get lenght of the argument
	add	$1, %ebx		# get next char
	cmpb	$0, (%ebx)		# is it null?
	je	openfile		# jump out of loop
	loop	nxtchr			# check next character
	jmp	longarg			# argument was too long

	.section	.data
handle:	.int	-1
flags:	.int	O_TRUNC | O_WRONLY
access:	.int	S_ACCESS

ftype:	.asciz	".s"			# filetype
ftypel:	.int	. - ftype		# filetype len
fname:	.int	0			# pointer to location of filename
fnamel:	.int	0			# length of filename

	.section	.text
openfile:
	# alocate memory for filename
	mov	$sys_BRK, %eax
	xor	%ebx, %ebx
	int	$0x80

	mov	%eax, %ebx
	mov	%eax, fname
	sub	$MAX_ARG_LEN, %ecx
	neg	%ecx
	mov	%ecx, fnamel
	add	%ecx, %ebx
	add	$ftypel, %ebx
	mov	$sys_BRK, %eax
	int	$0x80

	mov	fname, %edi
	mov	8(%esp), %esi
	cld
	rep	movsb

	add	%ecx,%edi
	lea	ftype, %esi
	mov	ftypel, %ecx
	cld
	rep	movsb

	mov	$sys_OPEN, %eax
	mov	fname, %ebx
	mov	flags, %ecx
	mov	access, %edx
	int	$0x80
	mov	%eax, handle

	.section	.data	
brk:	.ascii	"//--------------------"
	.ascii	"----------------------"
	.ascii	"----------------------"
brklen:	.int	. - brk

	.section	.text
	lea	brk, %ecx
	mov	brklen, %edx
	call	write

	.section	.data
cmt:	.ascii	"\n//\t"
cmtlen:	.int	. - cmt

	.section	.text
	lea	cmt, %ecx
	mov	cmtlen, %edx
	call	write

	mov	fname, %ecx
	mov	fnamel, %edx
	add	ftypel, %edx
	dec	%edx
	call	write

	lea	cmt, %ecx
	mov	cmtlen, %edx
	call	write
	call	write
	call	write
	call	write

	.equ	SPACE, 32
	.section	.data
asmb:	.ascii	"\tto assemble:  $ as "
asmbln:	.int	. - asmb
obj:	.ascii	" -o "
objlen:	.int	. - obj
ofext:	.ascii	".o"			# object file extention
oflen:	.int	. - ofext

	.section	.text
	lea	asmb, %ecx
	mov	asmbln, %edx
	call	write
	
	mov	fname, %ecx
	mov	fnamel, %edx
	add	ftypel, %edx
	dec	%edx
	call	write
	
	lea	obj, %ecx
	mov	objlen, %edx
	call	write
	
	mov	fname, %ecx
	mov	fnamel, %edx
	call	write
	
	lea	ofext, %ecx
	mov	oflen, %edx
	call	write
	
	lea	cmt, %ecx
	mov	cmtlen, %edx
	call	write
	
	.section	.data
lnk:	.ascii	"\tand to link:  $ ld "
lnklen:	.int	. - lnk

	.section	.text
	lea	lnk, %ecx
	mov	lnklen, %edx
	call	write
	
	mov	fname, %ecx
	mov	fnamel, %edx
	call	write
	
	lea	ofext, %ecx
	mov	oflen, %edx
	call	write
	
	lea	obj, %ecx
	mov	objlen, %edx
	call	write
	
	mov	fname, %ecx
	mov	fnamel, %edx
	call	write
	
	lea	cmt, %ecx
	mov	cmtlen, %edx
	call	write
	call	write
	
	.section	.data
aut:	.ascii	"programmer: Patrick McNamara"
autlen:	.int	. - aut

	.section	.text
	lea	aut, %ecx
	mov	autlen, %edx
	call	write
	
	lea	cmt, %ecx
	mov	cmtlen, %edx
	call	write
	
	.section	.data
dat:	.ascii	"date begun: "
datlen:	.int	. - dat

	.section	.text
	lea	dat, %ecx
	mov	datlen, %edx
	call	write
	
	mov	handle, %eax
	call	gettime
	
	.section	.data	
newln:	.ascii	"\n"
newlnl:	.int	. - newln

	.section	.text
	lea	newln, %ecx
	mov	newlnl, %edx
	call	write
	
	lea	brk, %ecx
	mov	brklen, %edx
	call	write
	
	.section	.data
bod:	.ascii	"\n\n\n\t# manifest constants\n"
	.ascii	"\t.equ\tsys_EXIT, 1\t\t# system-call ID-number\n\n\n"
	.ascii	"\t.section\t.data\n\n\n"
	.ascii	"\t.section\t.text\n" 
	.ascii	"_start:\t\n\n\n"	
	.ascii	"\t# terminate this program\n"
	.ascii	"\tmov\t$sys_EXIT, %eax\t\t# system-call ID-number\n"
	.ascii	"\txor\t%ebx, %ebx\t\t# return 0 as exit-code\n"
	.ascii	"\tint\t$0x80\t\t\t# invoke kernel service\n\n"
	.ascii	"\t.global\t_start\t\t\t# make entry-point public\n"
	.ascii	"\t.end\t\t\t\t# no more to be assembled"
bodlen:	.int	. - bod

	.section	.text
	lea	bod, %ecx
	mov	bodlen, %edx
	call	write

	mov	$sys_CLOSE, %eax
	mov	handle, %ebx
	int	$0x80
	jmp	exit
	
	.section	.data
errstr:	.ascii	"Must have only one argument\n"
errlen:	.int	. - errstr

	.section	.text
error:	mov	$sys_WRITE, %eax
	mov	$STDOUT, %ebx
	lea	errstr, %ecx
	mov	errlen, %edx
	int	$0x80
	jmp	exit

	.section	.data
arglong:.ascii	"Filename can't be more than 12 chars\n"
allen:	.int	. - arglong
	
	.section	.text
longarg:mov	$sys_WRITE, %eax
	mov	$STDOUT, %ebx
	lea	arglong, %ecx
	mov	allen, %edx
	int	$0x80

        # terminate this program
        mov     $sys_EXIT, %eax         # system-call ID-number
        xor     %ebx, %ebx              # return 0 as exit-code
        int     $0x80                   # invoke kernel service
#------------------------------------------------------------------------------
write:
	mov	$sys_WRITE, %eax
	mov	handle, %ebx
	int	$0x80
	ret
#------------------------------------------------------------------------------

# This is the function that will display the day month and year
#------------------------------------------------------------------------------
	.equ	sys_WRITE, 4
	.equ	sys_TIME, 13
	.equ	STDOUT, 1

	.section	.data
year:	.int	1970		# initialized to epoch
time:	.int	0		# system time
day:	.int	0

	.section	.text
gettime:
	push	%ebp
	mov	%esp, %ebp
	push	%eax
	push	%ebx
	push	%ecx
	push	%edx

	# System call to get the current time
	movl	$sys_TIME, %eax
	xor	%ebx, %ebx
	int	$0x80

	sub	$28800, %eax	# minus 8 hours from time to account for time zones

	# 64-bit division to find number of days since epoch
	movl	$86400, %ebx	# divisor equals num of seconds in day
	xor	%edx, %edx	# zeros upper half of divisor
	divl	%ebx		# divide

	# 32-bit division to find number of years since epoch
	mov	$365, %ebx	# number of days in a year
	xor	%edx, %edx	# clears upper half of 32-bit divisor
	divw	%bx		# divide

	movw	%ax, %bx	# copy number of years
	add	year, %bx	# add to when epoch happened
	mov	%bx, year	# store sum as year

	# find number of days added because of leap year
	sub	$2, %ax		# first affecting leap year happened 2 years after epoch
	mov	$4, %bx		# prepair for integer division
	divb	%bl		# al is now number of leap years
	and	$0xFF, %ax	# masks remainder of division

	# what if number of days taken because of leap year cause negitive days?
	cmp	%ax, %dx	# check number of days vs number of leap days
	ja	month		# if carry and zero arn't set, result will be fine
	add	$365, %dx	# else, add a year to the day
	movw	year, %bx
	dec	%bx		# take out year that was added to the day
	mov	%bx, year
	

month:	# Find out what month it is
	sub	%ax, %dx	# take out number of leap days
	mov	%dx, day	# store differance as num of days

	# Prepair to find out what month it is
	mov	%edx, %eax	# moves num of days into eax
	xor	%ecx, %ecx	# clear count register
	mov	$1387, %ebx	# prepairs bit pattern 10101101011 (see below)
	call	isleap		# find out if it is a leap year
	jnz	nxmon		# if not, start the loop
	inc	%eax		# if yes, add 1 day (if before Feb 29th,
				#	will be taken off later)

	# loop to find how many months there are
nxmon:	mov	%eax, %edx	# save current month in edx
	inc	%ecx		# next month
	cmp	$2, %ecx	# check if month is February
	je	february

	# this part checks to see if there is 31 days in the month.
	# it does this by using the bit pattern established above,
	# this bit pattern is gotten by reversing the pattern you
	# would get if 1 represents months with 31 days and 0 represents
	# months with 30 days, while ignoring feb as this is handeled elsewhere
	ror	$1, %ebx	# sets cf to 1 if 31 days in month
	sbb	$0, %eax	# subtract cf from num of days 
	
not31:	sub	$30, %eax	# subtract 30 from num of days
	ja	nxmon		# if zf!=0 and cf!=0 loop again

endmon:	mov	%edx, %eax	# moves correct date before turning into a string

# we now have correct day, month and year

	.section	.data	
buff:	.ascii	"xx xxx xxxx"
lenght:	.int	. - buff
tmp:	.ascii	"xxxx"		#temporary storage area
montbl:	.ascii	" JAN FEB MAR APR MAY JUN JUL AUG SEP OCT NOV DEC" # month-table

	.section	.text
	# prints all the collected info
print:	# adds day of month to display buffer
	lea	buff, %edi
	call	eax2asc		# converts date in aex into ascii value for printing

	# adds month to display buffer
	mov	%ecx, %esi	# moves month into esi
	dec	%esi		# esi dec b/c since table values begin at 0 and not 1
	mov	montbl(, %esi, 4), %eax	# gets table value of month
	mov	%eax, buff+2	# adds month name to display buffer

	# adds year to display buffer
	mov	year, %eax	# moves year into eax
	lea	tmp, %edi	# loads temporary storage area
	call	eax2asc		# places ascii value of year in temp
	mov	tmp, %eax	# move year ascii value into a register
	mov	%eax, buff+7	# add year to the display buffer

	# prepair for write system call
	lea	buff, %ecx		# load display buffer
	mov	$sys_WRITE, %eax	# set system write call
	mov	handle, %ebx		# set output device
	mov	lenght, %edx		# set lenght of display
	int	$0x80			# kernal interupt

	# restore registers
	pop	%edx
	pop	%ecx
	pop	%ebx
	pop	%eax
	mov	%ebp, %esp
	pop	%ebp
	ret				# return to caller

#METHODS
february: # lets february (and leap year) be properly accounted for
	sub	$28, %eax	# start by taking off 28 days
	jbe	endmon		# zf=0 or cf=0,  check if this month is feb
	call	isleap		# is it leap year?
	jnz	nxmon		# no, continue to next month
	dec	%eax		# yes, take off an extra day
	jz	endmon		# check again if it is feb
	jmp	nxmon		# procede to next month

isleap:	# function checks if this year is a leap year
	push	%eax
	push	%edx		# save registers
	push	%ebx
	mov	year, %eax	# set up for division...
	mov	$4, %ebx	# ... by 4
	xor	%edx, %edx	# clear upper half of divisor
	divl	%ebx		# divide
	cmp	$0, %edx	# set zf if remainder is 0
	pop	%ebx
	pop	%edx		# restore register
	pop	%eax
	ret			# return to caller
	

# All of this function, with a small exception (noted below) was
# taken from base10io.s, availible on the class website, and was
# written by Professor Cruse
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

	# added by myself
	cmp	$1, %ecx		# is there one diget in the number?
	jne	nxdgt			# no, continue normal before
	pushl	$0			# yes, push a zero to make the number 2 diget
	inc	%ecx			# and add 1 to the counter
	# end addition

nxdgt:	# loop to pop and store the digits in reversed order
	popl	%edx			# recover saved remainder
	addb	$'0', %dl		# convert number to ascii 
	movb	%dl, (%edi)		# place numeral in buffer
	incl	%edi			# and advance buffer-index
	loop	nxdgt			# convert another remainder

	popal				# restore saved registers
	ret				# return control to caller 
#-----------------------------------------------------------------
        .global _start                  # make entry-point public
        .end                            # no more to be assembled
