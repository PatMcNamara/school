x = input("First number: ")
y = input("Second number: ")
z = input("Third number: ")

if x > y and x > z: 
	print x, "is larger then", y, "and", z
elif y > x and y > z:
	print y, "is larger then", x, "and", z
elif z > x and z > y:
	print z, "is larger then", x, "and", y
else:
	print x, y, "and", z, "are equal."
