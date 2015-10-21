def check(change, bill):
	num = 0
	while change >= bill:
		change -= bill
		num +=1
	printnum(bill, num)
	return change

def printnum(denom, num):
	if num == 0:
		return
	if denom == 1000:
		denom = "ten"
	if denom == 500:
		denom = "five"
	if denom == 100:
		denom = "one"
	if denom == 25:
		denom = "quarter"
	if denom == 10:
		denom = "dime"
	if denom == 5:
		denom = "nickle"
	if denom == 1:
		denom = "pennie"
	if num > 1 or num == 0:
		print num, str(denom) + "s"
	else:
		print num, str(denom)

change = float(input("Change: "))
change *= 100

change = check(change, 1000)
change = check(change, 500) 
change = check(change, 100)
change = check(change, 25)
change = check(change, 10)
change = check(change, 5)
change = check(change, 1)
