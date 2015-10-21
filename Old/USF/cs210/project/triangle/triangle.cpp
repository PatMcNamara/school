//-----------------------------------------------------------------
//	triangle.cpp
//
//	This program displays some upper rows of the famous PASCAL 
//	TRIANGLE of binomial coefficients, where the array-entries
//	bin(n,k) are the values of a recursively-defined function.
//
//	  compile-and-link using: $ g++ triangle.cpp -o triangle
//
//	programmer: ALLAN CRUSE
//	written on: 18 MAR 2007
//      modified by: PATRICK MCNAMARA
//-----------------------------------------------------------------

#include <stdio.h>

#define N_MAX	12

extern "C" int bin( int n, int k );

int main( void )
{
	printf( "\n\n  THE PASCAL TRIANGLE  \n" );
	for (int i = 0; i <= N_MAX; i++)
		{
		printf( "\n" );
		for (int j = 0; j <= i; j++)				       
			printf( " %4d", bin( i, j ) ); 
		}
	printf( "\n\n" );
}
