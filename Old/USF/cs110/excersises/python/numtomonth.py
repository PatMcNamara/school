def getmonth(number):
	if number == 1:
		return "January"
	elif number == 2:
		return "February"
	elif number == 3:
		return "March"
	elif number == 4:
		return "April"
	elif number == 5:
		return "May"
	elif number == 6:
		return "June"
	elif number == 7:
		return "July"
	elif number == 8:
		return "August"
	elif number == 9:
		return "September"
	elif number == 10:
		return "October"
	elif number == 11:
		return "November"
	elif number == 12:
		return "December"
	else:
		number = input("Not a month, re-enter: ")
		month = getmonth(number)
		return month

number = input("What is the number? ")
#number = 0
#while number <= 13:
#	print number
month = getmonth(number)
print month
#	number += 1
