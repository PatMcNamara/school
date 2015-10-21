import sys

#makes student class
class student:

	#name, 4 projects, 3 exams, participation
	def __init__(self, line, count):
		
		#IMPORTANT!!!!!! you MUST have python version 2.5 in order to use the partition method
		#School computers have version 2.4.3 installed

		if len(line[i]) != 8:
			print "ERROR -- Malformed text file"
			print "Problem occurred on line", count
			sys.exit()
		for i in range(len(line)):
			temp = line[i].partition(":")
			x = temp[0]
			z = temp[2]
			for t in z:
				if z == ":":
					print "ERROR -- Malformed text file"
					print "Two colons not seperated by a space"
					sys.exit()
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
			else:
				print "ERORR -- Malformed text file"
				print "Problem occurred on line", count
				sys.exit()
		self.examave = 0
		self.projave = 0
		self.finalgrade = 0

	#prints student final grade
	def printstud(self):
		#you could change the formating here
		output.write("Name: " + str(self.name) + "  Final grade: " + str(self.finalgrade) + "\n")

	#change students information
	def edit(self, batch):
		if batch != 1
			print "1. Name:", self.name
			print "2. Project 1:", self.proj1
			print "3. Project 2:", self.proj2
			print "4. Project 3:", self.proj3
			print "5. Project 4:", self.proj4
			print "6. Test 1:", self.test1
			print "7. Test 2:", self.test2
			print "8. Test 3:", self.test3
			print "9. Participation", self.part

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

	#adds a student
	def addstudent(self, line):
		self.students.append(student(line))

	#calcualtes average grade
	def grade(self):
		for i in self.students:
			grade = i.grades()
			self.ave += grade
			if grade > self.high:
				self.high = grade
			if grade < self.low:
				self.low = grade
		self.ave /= len(self.students)

	#sends student info to outfile
	def output(self):
		for y in self.students:
			y.printstud()
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
	selection = 1
	while selection != 0:
		print "1. Add student"
		print "2. Change grades"
		print "3. Delete student"
		print "4. Calculate final grades and quit"
		print "0. Quit"
		try:
			selection = input("Input selection: ")
			if -1 > selection > 5:
				raise SyntaxError
		except SyntaxError:
			print "Not a valid entry -- retry"
			continue
		if selection = 1:
			#creates blank object then sends user to change all of the values
			gradebook.addstudent("",0,0,0,0,0,0,0,0)
			gradebook.
			try:
				name = raw_input("Students name: ")
			except SyntaxError:
				while 1:
					try:
						raw_input("Students name can not have numbers in it -- enter again: ")
						break
					except SyntaxError:
						continue

	#calculates each persons average scores for test and projects
	studentDB.grade()
	studentDB.output()
	gradebook.close()
	output.close()

except SystemExit:
	gradebook.close()
	output.close()
