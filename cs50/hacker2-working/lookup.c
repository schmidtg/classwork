/*************************************************************
*
* lookup.c
*
* Computer Science 50
* Pset2 - Hacker
* Graham Schmidt
*
* Check the /usr/share/dict/words file if it contains a word 
* provided by the user
*
**************************************************************/

#include <stdio.h>
#include <cs50.h>
#include <string.h>

int main(int argc, char *argv[])
{
    // Sanity check
    if ( argc != 2 ) {
        printf("Usage: %s word_to_check\n", argv[0]);

    // we have valid input
    } else {
        // open file
        FILE *file =  fopen( "/usr/share/dict/words", "r");

        // check if file is valid
        if ( file == 0 ) {
            printf("Could not open file\n");

        // Open dict file, read each line
        } else {

            // store each line in buffer
            char line[128];
            int match = 0;
            while ( fgets(line, sizeof line, file) != NULL ) {

                // remove newline character
                line[strlen(line) - 1] = '\0';

                // compare word on line to user input
                // exit immediately if found
                if ( strcmp(line, argv[1]) == 0 ) {
                    match = 1;
                    break;
                }
            }
            fclose(file);

            // success message
            if ( match == 1 ) {
                printf("YES\n");
            } else {
                printf("NO\n");
            }
        }
    }
}
