#include <cs50.h>
#include <stdio.h>
#include <string.h>
#define SIZE 8

bool search(int needle, int haystack[], int size);
void print_r(int arr[], int size);

int main(void)
{
    int numbers[SIZE] = { 4, 15, 16, 50, 8, 23, 42, 108 };
    printf("> ");
    int n = GetInt();

    printf("before\n");
    print_r(haystack, size);

    printf("selection sort.\n");
    for (int i = 0; i < SIZE; i++) {
        int min = i;

        // find lowest value
        for (int j = i + 1; j < SIZE; j++) {
            if ( numbers[j] < numbers[min] )
                min = j;
        }

        // swap
        if ( numbers[min] != numbers[i] ) {
            int temp = numbers[min];
            numbers[min] = numbers[i];
            numbers[i] = temp;
        }
    }
    printf("after\n");
    print_r(haystack, size);

    if (search(n, numbers, SIZE))
        printf("YES\n");

    return 0;
}

bool search(int needle, int haystack[], int size)
{
    // TODO: return true iff needle is in haystack, using binary search


    // use binary search
    /*
    while length of list > 0
        look at middle of list
        if number found, return true
        else if number is too high, only consider
        left half of list
        else if number is too low, only consider
        right half of list
    return false
    */
    int length = size; // store copy
    while (length > 0) {
        int check_num = haystack[length / 2];

        if (check_num == needle)
            return true;

        if (check_num > needle)
            search(needle, haystack, )
        length--;
    }


    return true;
}

void print_r(int arr[], int size)
{
    printf("print_r.\n");

    for (int i = 0; i < size; i++) {
        printf("%d\n", arr[i]);
    }
}
