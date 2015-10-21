import sys

#makes student class
class student:

	#name, 4 projects, 3 exams, participation
	def __init__(self, line, count):
		
		#IMPORTANT!!!!!! you MUST have python version 2.5 in order to use the partition method
		#School computers have version 2.4.3 installed

		#makes sure there are only 9 indexes per line
		if len(line) != 9:
			print "ERROR -- Malformed text file"
			print "Problem occurred on line", count
			sys.exit()
		#splits each object into 3 parts, the part before the colon, the colon and the part after
		for i in range(len(line)):
			temp = line[i].partition(":")
			#gets part before and part after colon
			x = temp[0]
			z = temp[2]
			#checks each character after colon to make sure there are no more colons
			for t in z:
				if z == ":":
					print "ERROR -- Malformed text file on line", count
					print "Two colons not seperated by a space"
					sys.exit()
			#compares x to each possible x
			if x.lower() == "name":
				self.name = z
			elif x.lower() == "proj1":
				self.proj1 = int(z)
			elif x.lower() == "proj2":
				self.proj2 = int(z)
			elif x.lower() == "proj3":
				self.proj3 = int(z)
			elif x.lower() == "proj4":
				self.proj4 = int(z)
			elif x.lower() == "exam1":
				self.exam1 = int(z)
			elif x.lower() == "exam2":
				self.exam2 = int(z)
			elif x.lower() == "exam3":
				self.exam3 = int(z)
			elif x.lower() == "part":
				self.part = int(z)
			#if x is none of the above, raise error
			else:
				print "ERORR -- Malformed text file"
				print "Problem occurred on line", count
				sys.exit()
		#blank variables until average is calculated
		self.examave = 0
		self.projave = 0
		self.finalgrade = 0

	#prints student final grade
	def printstud(self):
		#you could change the formating here
		output.write("Name: " + str(self.name) + "  Final grade: " + str(self.finalgrade) + "\n")

	#change students information
	def edit(self, batch):
		selection = 10
		#selection == 0 escapes from loop
		while selection != 0:
			#menu, the menu is not used when it is a new person
			if batch != 1:
				print "1. Name:", self.name
				print "2. Project 1:", self.proj1
				print "3. Project 2:", self.proj2
				print "4. Project 3:", self.proj3
				print "5. Project 4:", self.proj4
				print "6. Test 1:", self.exam1
				print "7. Test 2:", self.exam2
				print "8. Test 3:", self.exam3
				print "9. Participation", self.part
				print "0. Back to main menu"
				while 1:
					try:
						selection = input("Which grade do you want to change: ")
						if 0 > selection or selection > 9:
							raise NameError
						break
					except NameError:
						print "Invalid selection"

			#edits name
			if selection == 1 or batch == 1:
				while 1:
					try:
						self.name = raw_input("Enter name: ")
						break
					except NameError:
						print "Name can not contain numbers"
			#edits a project score
			if 1 < selection < 6 or batch == 1:
				#if you are not batch editing, gives you the proper project to edit
				if batch != 1:
					b = selection - 1
				else:
					b = 1
				while b < 5:
					try:
						proj = input("Enter score on project #" + str(b) + ": ")
						if 0 > proj or proj > 100:
							raise NameError
						if b == 1:
							self.proj1 = proj
						if b == 2:
							self.proj2 = proj
						if b == 3:
							self.proj3 = proj
						if b == 4:
							self.proj4 = proj
						if batch != 1:
							break
						else:
							b += 1
					except NameError:
						print "Invalid entry"

			#used for editing test number
			if 5 < selection < 9 or batch == 1:
				if batch != 1:
					t = selection -5
				else:
					t = 1
				while t < 4:
					try:
						test = input("Score on test #" + str(t) + ": ")
						if test < 0 or test > 100:
							raise NameError
						if t == 1:
							self.exam1 = test
						elif t == 2:
							self.exam2 = test
						else:
							self.exam3 = test
						if batch != 1:
							break
						else:
							t +=1
					except NameError:
						print "Invalid entry"

			#edits participation grade
			if selection == 9 or batch == 1:
				while 1:
					try:
						part = input("Enter participation grade: ")
						if 0 > part or 100 < part:
							raise NameError
						self.part = part
						break
					except NameError:
						print "Invalid entry"
			#if this is batch editing, break
			if batch == 1:
				break

	#returnes name of student
	def getname(self):
		return self.name

	#displays the student information
	def printstud(self):
		print "Name:", self.name, "Exam 1:", self.exam1, "Exam 2:", self.exam2,
		print "Exam 3:", self.exam3, "Project 1:", self.proj1, "Project 2:", self.proj2,
		print "Project 3:", self.proj3, "Project 4:", self.proj4, "Participation:", self.part

	#calcualate grades
	def grades(self):
		self.projave = (self.proj1 + self.proj2 + self.proj3 + self.proj4)/4
		self.examave = (self.exam1 + self.exam2 + self.exam3)/3
		self.finalgrade = (.45 * self.projave) + (.45 * self.examave) + (.10 * self.part)
		return self.finalgrade

#studentDB
class studentDB:

	#stores list of student objects an high, low and ave class scores
	def __init__(self):
		self.students = []
		self.high = 0 #high score
		self.low = 100 #low score
		self.ave = 0 #average

	#adds a student from a text file
	def addstudent(self, line, count):
		self.students.append(student(line, count))

	#edits a student entry
	def editstudent(self, new):
		#this is only used after creating a student within the program
		if new == 1:
			self.students[len(self.students) - 1].edit(1) #edits last student in list
		#selects students then goes to student.edit
		else:
			#displays each student
			for i in range(len(self.students)):
				print str(i+1) + ":", self.students[i].getname()
			try:
				s = (input("Student you want to change: ") - 1)
				#checks for inputs of that could not be in list and triggers error
				if s < 0 or s > i:
					raise NameError
				self.students[s].edit(0)
			#error if input is string
			except NameError:
				print "Invalid entry"

	#deletes a student
	def delstudent(self):
		#prints list of names
		for i in range(len(self.students)):
			print str(i+1) + ":", self.students[i].getname()
		#gets input, checks for invalid input then deletes the selected index
		try:
			s = (input("Student you want to delete: ") - 1)
			if s < 0 or s > i:
				raise NameError
			del self.students[s]
		except NameError:
			print "Invalid entry"

	#calcualtes average grade
	def grade(self):
		for i in self.students:
			#gets grade of each student
			grade = i.grades()
			#adds grade to running total
			self.ave += grade
			#checks if it is the highest grade
			if grade > self.high:
				self.high = grade
			#checks if it is lowest grade
			if grade < self.low:
				self.low = grade
		#devides running total by total number of students
		self.ave /= len(self.students)

	#displays student info
	def display(self):
		#gets each student and prints info
		for d in self.students:
			d.printstud()

	#sends class grade info to outfile
	def output(self):
		output.write("\nHighest score in class: " + str(self.high) + "  Lowest score in class: " + str(self.low) + "  Class average: " + str(self.ave))


#loads gradebook and output files
try:
	gradebook = open("gradebook.txt","r")
except IOError:
	print "No gradebook.txt file"
	sys.exit()
output = open("grades.txt", "w")

#catches any sys.exit() and closes open files before quiting.
try:
	#creates blank DB object
	studentDB = studentDB()
	#takes each line of grade book (each student)
	count = 1
	while 1:
 		i = gradebook.readline()
		if not i:
			break
		studentinfo = i.split()  #creates list with each word being differant item
		studentDB.addstudent(studentinfo, count) #adds a new student to the database
		count += 1
	#Menu
	selection = 10
	while selection != 5:
		print "1. Add student"
		print "2. Change grades"
		print "3. Delete student"
		print "4. Display list of students"
		print "5. Calculate final grades and quit"
		try:
			selection = input("Input selection: ")
			if 0 > selection or selection > 6:
				raise NameError
		except NameError:
			print "Not a valid entry -- retry"
			continue
		if selection == 1:
			#creates blank object then sends user to change all of the values
			studentDB.addstudent(["name:0","exam1:0","exam2:0","exam3:0","proj1:0","proj1:0","proj2:0","proj3:0","part:0"], 0)
			studentDB.editstudent(1)
		if selection == 2:
			studentDB.editstudent(0)
		if selection == 3:
			studentDB.delstudent()
		if selection == 4:
			studentDB.display()

	#calculates each persons average scores for test and projects
	studentDB.grade()
	#outputs grade to text file
	studentDB.output()
	gradebook.close()
	output.close()

except SystemExit:
	gradebook.close()
	output.close()
