//--------------------------------------------------------------
//	demo1.c
//
//	This very simple application program merely adds two
//	specified integers and prints out the resulting sum.
//	It is written using the C programming language, just
//	to illustrate the distinction between a "high-level"
//	language and a "low-level" language (like assembly).
//	This demo should be compared with 'demo2.s' which is    
//	a "low-level" version of this very same computation.
//
//	      compile using:  $ gcc demo1.c -o demo1
//	      execute using:  $ ./demo1
//
//	programmer: ALLAN CRUSE
//	written on: 22 JAN 2006
//--------------------------------------------------------------

#include <stdlib.h>		// for exit()
#include <stdio.h>		// for sprintf() and write()

#define device_id  1		// standard output device-file

int	x = 4, y = 5;		// initialized integer variables
int	z, n;			// uninitialized integer variables
char	buf[80];		// uninitialized character array

int main()
{
	z = x + y;		// assignment statement

	// call to external function
	n = sprintf( buf, "%d + %d = %d \n", x, y, z );

	// system-call to OS kernel
	write( device_id, &buf, n ); 

	// terminate with return-code 
	exit( 0 );
}	

