/*************************************************************
*
* swap.c
*
* Computer Science 50
* Pset2 - Hacker
* Graham Schmidt
*
* Swaps two integers without using temp variables
*
**************************************************************/

#include <stdio.h>
#include <cs50.h>

int main(void)
{
    int x;
    do
    {
        printf("x: ");
        x = GetInt();
    } while (x < 0);

    int y;
    do
    {
        printf("y: ");
        y = GetInt();
    } while (y < 0);

    printf("x is %d\n", x);
    printf("y is %d\n", y);

    // use sequence of executing right-side first
    // store both x & y in x
    x = x + y;

    // set y to original value of x
    y = x - y;

    // x is now original value of y
    x = x - y;

    printf("x is %d\n", x);
    printf("y is %d\n", y);
}
