#include <cs50.h>
#include <stdio.h>

int 
main(void)
{
    
    printf("C: ");
    int c =  GetInt();
    float temp = c * 9.0/5.0 + 32.0;
    printf("F: %.1f\n", temp);
}
