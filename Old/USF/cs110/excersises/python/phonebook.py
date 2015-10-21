class phonebook:
	def __init__(self):
		self.phone = []
	
	def add(self, name, number):
		self.phone.append(person(name,number))

	def remove(self,index):
		del self.phone[index]

	def printbook(self):
		for i in range(len(self.phone)):
			print str(i) + ".",
			self.phone[i].display()

	def change(self, index):
		self.phone[index].change(input("New number: "))

class person:
	def __init__(self, name, number):
		self.name = name
		self.number = number

	def display(self):
		print "Name: ", self.name, "Number: ", self.number

	def change(self, new):
		self.number = new

selection = 6
book = phonebook()
while selection != 0:
	print "1. Add a person"
	print "2. Remove a person"
	print "3. Change number"
	print "4. View entire phone book"
	print "0. Quit"
	selection = input("Enter selection: ")
	if selection == 1:
		name = raw_input("Persons name: ")
		number = input("Persons number: ")
		book.add(name, number)
		print
	elif selection == 2:
		book.printbook()
		book.remove(input("Entry you want to delete: "))
		print
	elif selection == 3:
		book.printbook()
		book.change(input("Entry you want to change: "))
		print
	elif selection == 4:
		book.printbook()
		print
