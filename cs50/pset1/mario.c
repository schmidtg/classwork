/*************************************************************
*
* mario.c
*
* Computer Science 50
* Pset1
* Graham Schmidt
*
* Print a Mario Bros. half-pyramid to the screen.
*
**************************************************************/

#include <cs50.h>
#include <stdio.h>

int main(void)
{
    // Ensure height is between 0 and 23
    int height;
    do
    {
        printf("Height: ");
        height = GetInt();
    }
    while (height < 0 || height > 23);

    // Defaults for height and hashes
    int hashes = 2;
    int spaces = height - 1;
    for(int i = 0; i < height; i++) {

        // Print spaces
        for(int j = 0; j < spaces; j++) {
            printf(" ");
        }

        // Print hashes
        for(int j = 0; j < hashes; j++) {
            printf("#");
        }

        // Print newline
        printf("\n");
        
        // Adjust spaces and hashes for next iteration
        spaces--;
        hashes++;
    }
}
