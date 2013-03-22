#include <stdio.h>
#include <stdlib.h>

int main(void)
{
    printf("int %d\n", sizeof(int));
    printf("int* %d\n", sizeof(int*));
    printf("char %d\n", sizeof(char));
    printf("char* %d\n", sizeof(char*));
    printf("long long %d\n", sizeof(long long));
    printf("long long* %d\n", sizeof(long long*));
}
