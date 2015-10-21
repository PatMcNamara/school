def overtime(pay, time):
	extrapay = time * (pay * 1.5)
	return extrapay

def time(pay, time):
	if time - 40 > 0:
		extrapay = overtime(pay, (time - 40))
		normalpay = 40 * pay
		return extrapay + normalpay
	return time * pay

pay = input("Base pay per hour: ")
hour = input("Hours worked: ")

totalpay = time(pay, hour)

print "You made", totalpay
