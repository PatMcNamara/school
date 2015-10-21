str1 = raw_input("First string: ")
str2 = raw_input("Second string: ")
a = 0
for i in range(len(str1) - 1):
	for x in range(len(str2) - 1):
		if str2[x:x+2] == str1[i:i+2]:
			if " " in str2[x:x+2]:
				break
			else:
				a += 1
print a, "sequences in the strings"
