/*************************************************************
*
* eight.c
*
* Computer Science 50
* Pset2 - Hacker
* Graham Schmidt
*
* Parses the /usr/shared/dict/words file for all words 8 chars
* or less and stores them in local file
*
**************************************************************/

#include <stdio.h>
#include <cs50.h>
#include <string.h>

int main(int argc, char *argv[])
{
    // open file
    FILE *file =  fopen( "/usr/share/dict/words", "r");

    // check if file is valid
    if ( file == 0 ) {
        printf("Could not open file\n");

    // Open dict file, read each line
    } else {

        // create output stream
        FILE *out;
        out = fopen( "eightc_words.txt", "w" );
                
        // store each line in buffer
        char line[256];
        while ( fgets(line, sizeof line, file) != NULL ) {

            // remove newline character
            line[strlen(line) - 1] = '\0';

            // save 8 character words to output file
            if ( strlen(line) <= 8 ) {
                if( out != NULL ) {
                    fprintf( out, "%s\n", line );              
                }
            }
        }
        fclose(out);
        fclose(file);
    }
}
