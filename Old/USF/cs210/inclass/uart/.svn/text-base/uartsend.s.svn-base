//------------------------------------------------------------------
//	testuart.s                       
//
//	This program will test the Serial UART's 'loopback' mode by 
//	transmitting a succession of bytes from an asciz-string and
//	reading each one back, displaying each character on screen.
//
//		to assemble:  $ as testuart.s -o testuart.o
//		and to link:  $ ld testuart.o -o testuart
//
//	Note that Linux application-programs normally are prevented
//	by the processor from directly accessing I/O ports, but the
//	operating system can 'reprogram' the processor to eliminate
//	this restriction, something a student can accomplish in our 
//	classroom by executing a special command (installed by Alex
//	Fedosov, our System Administrator), like this: 
//
//				$ iopl3
//
//	programmer: ALLAN CRUSE 
//	written on: 04 APR 2007 
//------------------------------------------------------------------

	# manifest constants 
	.equ	sys_EXIT, 1 
	.equ	sys_READ, 3
	.equ	sys_WRITE, 4
	.equ	STDIN, 0
	.equ	STDOUT, 1
	.equ	UART, 0x03F8
	.equ	LINE_CONTROL, UART+3
	.equ	DIVISOR_LATCH, UART+0
	.equ	MODEM_CONTROL, UART+4
	.equ	LINE_STATUS, UART+5
	.equ	TX_DATA, UART+0
	.equ	RX_DATA, UART+0

	.section	.data 
msg:	.space	40
msglen:	.int	.-msg
inch:	.byte	0

	.section	.text 
_start:	
	mov	$sys_READ, %eax
	mov	$STDIN, %ebx
	lea	msg, %ecx
	mov	msglen, %edx
	int	$0x80
	mov	$0, msg(msglen)

	# configure the UART's operational parameters
	mov	$LINE_CONTROL, %dx
	mov	$0x80, %al		# access Divisor-Latch 
	out	%al, %dx

	mov	$DIVISOR_LATCH, %dx
	mov	$0x0001, %ax		# set baudrate: 115200
	out	%ax, %dx

	mov	$LINE_CONTROL, %dx
	mov	$0x03, %al		# set data-format: 8-N-1
	out	%al, %dx

	# write each message-character, read it back, then display it
	xor	%esi, %esi		# initialize array-index
again:
	# wait until the UART's Transmit Holding Register is empty
	mov	$LINE_STATUS, %dx
spin1:	in	%dx, %al
	test	$0x20, %al
	jz	spin1	

	# write the next message-character to the UART's TX_DATA register
	mov	msg(%esi), %al
	mov	$TX_DATA, %dx
	out	%al, %dx

	# wait until the UART's has received a new byte of data
	mov	$LINE_STATUS, %dx
spin2:	in	%dx, %al
	test	$0x01, %al
	jz	spin2	

	# read the new character from the UART's RX_DATA register
	mov	$RX_DATA, %dx
	in	%dx, %al
	mov	%al, inch

	# show the received character on the screen
	mov	$sys_WRITE, %eax
	mov	$STDOUT, %ebx
	lea	inch, %ecx
	mov	$1, %edx
	int	$0x80

	# increment the array-index, and check for string's null-byte
	inc	%esi
	cmpb	$0, msg(%esi)
	jne	again
	
	# terminate this program 
	mov	$sys_EXIT, %eax 
	xor	%ebx, %ebx 
	int	$0x80 

	.global	_start 
	.end
 
