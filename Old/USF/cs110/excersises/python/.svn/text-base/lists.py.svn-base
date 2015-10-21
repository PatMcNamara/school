inputs = []
string = raw_input("Enter string: ")
while "quit" != string.lower():
	inputs.append(string)
	string = raw_input("Enter another string: ")
a = 0
for x in range(len(inputs)):
	keep = raw_input("Do you want to keep " + str(inputs[x - a]) + "? ")
	if keep == "yes":
		continue
	if keep == "no":
		del inputs[x - a]
		a += 1
print inputs
