        .section        .data
hex:    .ascii  "0123456789ABCDEF"

        .section        .text
rax2hex:
	push	%rax
	push	%rbx
	push	%rcx
	push	%rdx
	push	%rdi

        mov     $16, %rcx
nxnby:
        rol     $4, %rax
        mov     %al, %bl
        and     $0xF, %rbx
        mov     hex(%rbx), %dl
        mov     %dl, (%rdi)
        inc     %rdi
        loop    nxnby

	pop	%rdi
	pop	%rdx
	pop	%rcx
	pop	%rbx
	pop	%rax
	ret

        .equ    sys_EXIT, 1
        .equ    sys_WRITE, 4
        .equ    dev_STDOUT, 1

	.section	.data
names:	.ascii	" R15 R14 R13 R12 R11 R1O  R9  R8"
	.ascii	" RDI RSI RBP RSP RBX RDX RCX RAX"
nelts:	.quad	(. - names)/4
buf:	.ascii	" nnn=xxxxxxxxxxxxxxxx \r\n"
len:	.quad	. - buf

        .section        .text
_start:
	push	%rax
	push	%rcx
	push	%rdx
	push	%rbx
	push	%rsp
	push	%rbp
	push	%rsi
	push	%rdi
	push	%r8
	push	%r9
	push	%r10
	push	%r11
	push	%r12
	push	%r13
	push	%r14
	push	%r15

	# now display the eight general-purpose register-values
	xor	%rsi, %rsi		# initialize array-index
nxelt:	
	# put element-name in the output-buffer
	mov	names(, %rsi, 4), %rax	# get element's name
	mov	%rax, buf		# put name into buffer

	# put element-value in the output-buffer
	mov	(%rsp, %rsi, 8), %rax	# get element's value
	lea	buf+5, %rdi		# point to value field
	call	rax2hex			# convert number to hex

	# draw buffer's contents on the screen
	mov	$sys_WRITE, %rax	# ID-number for 'write'
	mov	$dev_STDOUT, %rbx	# ID-number for display
	lea	buf, %rcx		# address of the string
	mov	len, %rdx		# length of the string
	int	$0x80			# invoke kernel service

	# increment array-index for next loop-iteration
	inc	%rsi			# increment array-index
	cmp	nelts, %rsi		# check: beyond bounds?
	jb	nxelt			# no, show next element

	# terminate this program
	mov	$sys_EXIT, %rax		# ID-number for 'exit'
	xor	%rbx, %rbx		# use zero as exit-code
	int	$0x80			# invoke kernel service

	.global	_start			# make entry-point public
	.end				# no more to be assembled
