#include <cs50.h>
#include <stdio.h>

int
main(void)
{
    int n,d;
    do
    {
        printf("non-negative numerator: ");
        n = GetInt();
    }
    while (n < 0);

    do
    {
        printf("positive denominator: ");
        d = GetInt();
    }
    while (d < 0);

    float percent = (float) n / (float) d * 100;
    printf("%.2f%%\n", percent);
}
