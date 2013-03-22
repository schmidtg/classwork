/*************************************************************
*
* mario.c
*
* Computer Science 50
* Pset1 - Hacker
* Graham Schmidt
*
* Print two inverted Mario Bros. half-pyramids to the screen.
*
**************************************************************/

#include <cs50.h>
#include <stdio.h>

void print_hashes(int hashes);

int main(void)
{
    // ensure height is between 0 and 23
    int height;
    do
    {
        printf("Height: ");
        height = GetInt();
    }
    while (height < 0 || height > 23);

    // defaults for height and hashes
    int hashes = 1;
    int spaces = height - 1;
    for(int i = 0; i < height; i++) {

        // print spaces
        for(int j = 0; j < spaces; j++) {
            printf(" ");
        }

        print_hashes(hashes);

        // line separator
        printf("  ");

        print_hashes(hashes);

        // print newline
        printf("\n");
        
        // adjust spaces and hashes for next iteration
        spaces--;
        hashes++;
    }

    return 0;
}

/*
 * Print the hashes
 */
void print_hashes(int hashes)
{
    // print hashes
    for(int j = 0; j < hashes; j++) {
        printf("#");
    }
}
