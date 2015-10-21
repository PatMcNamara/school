#ask for project scores
project1 = input("Percent on first project: ")
project2 = input("Percent on second project: ")
project3 = input("Percent on third project: ")

#ask for exam scores
exam1 = input("Percent on first exam: ")
exam2 = input("Percent on second exam: ")
exam3 = input("Percent on third exam: ")

#ask for lab score
lab = input("Lab Percent: ")

#calculate final score
final = (lab*.2)+(project1*.1)+(project2*.1)+(project3*.1)+(exam1*.1)+(exam2*.1)+(exam3*.15)

#calculate needed grade
a = (90-final)/.15
b = (80-final)/.15 
c = (70-final)/.15
d = (60-final)/.15

#display Percent
print "To get an A: ", a
print "To get a B: ", b
print "To get a C: ", c
print "To get a D: ", d
