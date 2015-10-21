# Solve.sh
# Run with ./Solve.sh (be sure file has proper permissions)
if [ "$1" = "--help" -o -z "$1" ]; then
   echo "         Usage: ./Solve.sh directory"
   echo
   echo "         Place this file in the same location as the Solver.java and Test.jar file, then run it passing in the directory of the boards to be tested as the arguement."
   echo
   exit 1
fi
test "Solver.java" -nt "Solver.class" && echo "Compiling" && javac Solver.java

test -f .tmp && mv .tmp .tmp.back
touch .tmp

if [ -f $1 ]; then
   result=`/usr/bin/time -f "%E" java -ea Solver $1 $1.goal 2> .tmp | java -jar Test.jar -gui $1 $1.goal`
   if [ -z "`grep "Exception" < .tmp`" ]; then
      echo $result
      echo "Total time: `cat .tmp`"
   else
      echo "`grep "Exception" < .tmp`"
   fi
elif [ ! -d "$1" ]; then
   echo "$1 does not exist or is not a directory"
   exit=0
else
   dir="${1%/}/"
   for x in `ls -I *.goal $dir`
   do
      x="$dir$x"
      test -d $x && echo "Skipping directory $x" && continue
      test ! -f "$x.goal" && echo "Skipping $x as it does not have a goal file" && continue
      echo "Running $x:"
      result=`/usr/bin/time -f "%E" java -ea Solver $x $x.goal 2> .tmp | java -jar Test.jar $x $x.goal`
      
      if [ -z "`grep "Exception" < .tmp`" ]; then
         echo "$result took `tail -n 1 .tmp`"
      else
         echo "`grep "Exception" < .tmp`"
      fi
      
      if [ "$result" = "Success" ]; then
         success="$success $x"
      else
         fail="$fail $x"
      fi
   done
   test ! -z "$success" && echo "Successful tests:$success"
   test ! -z "$fail" && echo "Failed tests:$fail"
fi
rm .tmp
test -e .tmp.back && mv .tmp.back .tmp
test -z $exit && exit=0
exit $exit
