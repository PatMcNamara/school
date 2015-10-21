//-------------------------------------------------------------------
//	squares.cpp
//
//	This is a prototype (written in the high-level C++ language)  
//	for a program we intend to rewrite using assembler language.
//	It will display a numerical table showing the squares of the
//	first twenty positive integers without using multiplication. 
//
//	(The reason we have taken time to build this initial version
//	of our computation is to make sure we have identified all of
//	the various data-elements and functional algorithms that may
//	be needed in a final assembly language version of the task.) 
//
//	    compile-and-link using:  g++ squares.cpp -o squares
//
//	programmer: ALLAN CRUSE
//	written on: 24 JAN 2006
//-------------------------------------------------------------------

#include <stdio.h>	// for printf() 

#define MAX  20		// number of lines in table's body

int	arg, val;	// uninitialized integer variables

int main( void )
{
	// print the table's title
	printf( "\n     Table of Squares     \n\n" );

	// program loop, to print the table's body
	val = 0;	// initialize the 'val' variable
	arg = 0;	// initialize the 'arg' variable
	do	{
		// compute the next values for our two variables
		val += arg + arg + 1;	// compute next 'val'
		++arg;			// compute next 'arg'
		// print the next line in the table's body
		printf( "       %2d       %3d     \n", arg, val );
		}
	while ( arg != MAX );	// check loop-exit condition
	
	// print a blank bottom line
	printf( "\n" ); 

	// return control to the shell (with exit-code zero)
	return	0;
}
