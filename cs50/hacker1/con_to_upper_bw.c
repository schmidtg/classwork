/*************************************************************
*
* con_to_upper_bw.c
*
* Computer Science 50
* Pset1 - Hacker
* Graham Schmidt
*
* Convert a lowercase letter to uppercase using bitwise operations
*
**************************************************************/

#include <cs50.h>
#include <stdio.h>

int main(void)
{
   char c = GetChar(); 
   
   // 00000001 << 3, left=shifts 3 places

   // Convert 'A' (65) to 'a' (97)
   // a 01100001
   // A 01000001
   printf("%c\n", c & 223);

   return 0;
}
