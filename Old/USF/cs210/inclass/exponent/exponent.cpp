//-----------------------------------------------------------------
//	exponent.cpp
//
//
//-----------------------------------------------------------------

#include <stdio.h>

extern "C"
int expo( int n, int k );
/*
{
	if ( k == 0 ) return 1;
	else return  n * expo( n, k-1 );
}
*/

int main( void )
{
	int	n = 3;
	
	for (int k = 0; k <= 5; k++)
		printf( "  %d     %d   \n", k, expo( n, k ) );
	printf( "\n" );
}
	




