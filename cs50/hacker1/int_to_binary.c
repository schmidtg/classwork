/*************************************************************
* int_to_binary.c
*
* Computer Science 50
* Pset1 - Hacker
* Graham Schmidt
*
* Convert an integer to binary
*
**************************************************************/

#include <cs50.h>
#include <stdio.h>
#include <math.h>

int check(int orig, int i, double multiple_2);

int main(void)
{
    int num;
    do
    {
        printf("Integer to convert to binary: ");
        num = GetInt();
    }
    while (num < 0);

    printf("num: %d\n", num);

    // Start a very high number, most likely greater
    // than the number provided to incrementally
    // determine each place value in the binary sequence
    int remain = num;
    for (int i = 50; i >= 0; i--) {
        remain = check(num, remain, pow(2, i));
    }

    printf("\n");

    return 0;
}

/*
 * Takes the original number, a decrementing integer
 * and a power of 2. Incrementally returns the remainder
 * as we get closer to the 0 digit's place.
 */
int check(int orig, int i, double multiple_2)
{
    // skip leading zeroes
    if (orig < multiple_2) {
        return i;
    }

    // alternate 1s/0s
    if (i >= multiple_2) {
        printf("1");
    } else {
        printf("0");
        return i;
    }

    return i % (int) multiple_2;
}
