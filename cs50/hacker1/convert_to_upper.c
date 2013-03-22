/*************************************************************
*
* convert_to_upper.c
*
* Computer Science 50
* Pset1 - Hacker
* Graham Schmidt
*
* Convert a lowercase letter to uppercase NOT using bitwise operations
*
**************************************************************/

#include <cs50.h>
#include <stdio.h>

int main(void)
{
   char c = GetChar(); 
   printf("%c\n", (char) ((int) c - 32));

   return 0;
}
