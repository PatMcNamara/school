	.equ	sys_exit, 1
	.equ	sys_write, 4
	.equ	sys_iopl, 110		# system-ID for 'iopl'
	.equ	STDOUT, 1
	.equ	rtc_dow, 6
	.equ	RTC_ADDR, 0x70		# RTC's address-port 
	.equ	RTC_DATA, 0x71		# RTC's data-port
	.equ	CHAR_DAY, 11
	
	.section	.data
buf:	.ascii	"Tomorrow is "
	.space	CHAR_DAY
buflen:	.int	. - buf
daytbl:	.ascii	"Monday.   \n"
	.ascii	"Tuesday.  \n"
	.ascii	"Wednesday.\n"
	.ascii	"Thursday. \n"
	.ascii	"Friday.   \n"
	.ascii	"Saturday. \n"
	.ascii	"Sunday.   \n"
err:	.ascii	"Could not adjust privilage level\n"
esz:	.int	. - err

	.section	.text
_start:	mov	$sys_iopl, %eax		# service-ID for 'iopl'
	mov	$3, %ebx		# desired value for IOPL
	int	$0x80			# enter Linux kernel
	cmp	$-1, %eax		# did function succeed?
	jne	cont
	
	mov	$sys_write, %eax	# service-ID for 'write'
	mov	$STDOUT, %ebx
	lea	err, %ecx		# message's addrexx
	mov	esz, %edx		# message's length
	int	$0x80			# enter Linux kernel
	jmp	exit			# jump to program exit

cont:	mov	$rtc_dow, %al
	out	%al, $RTC_ADDR
	in	$RTC_DATA, %al
	cmp	$7, %al
	jne	printmonth
	mov	$0, %al
	
printmonth:
	andl	$0xF, %eax
	lea	buf, %edi
	lea	daytbl, %esi
	mov	$CHAR_DAY, %ecx
	mov	buflen, %ebx
	sub	%ecx, %ebx
	add	%ebx, %edi
	mov	$CHAR_DAY, %ebx
	mul	%ebx
	add	%eax, %esi
	cld
	rep	movsb
	
	mov	$sys_write, %eax
	mov	$STDOUT, %ebx
	lea	buf, %ecx
	mov	buflen, %edx
	int	$0x80
	
exit:	mov	$sys_exit, %eax
	xor	%ebx, %ebx
	int	$0x80
	
	.global	_start
	.end
