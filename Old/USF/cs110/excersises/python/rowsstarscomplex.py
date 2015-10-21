rows = input("Number of rows: ")

for i in range(rows):
	for i in range (rows - i):
		print "",
	x = 1
	while x <= rows - i:
		print "*",
		x += 1
	print
