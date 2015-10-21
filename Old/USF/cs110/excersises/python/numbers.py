rows = input("Number a number: ")
rows += 1

for i in range(rows):
	x = 0
	while x <= i :
		print x,
		x += 1
	print

for i in range(rows):
	n = 0
	while n < rows - (i + 1):
		print n,
		n += 1
	print
