/*************************************************************
*
* myspace.c
*
* Computer Science 50
* Pset2 - Hacker
* Graham Schmidt
*
* Invert a sentence, skip non-alphabetic characters
*
**************************************************************/

#include <cs50.h>
#include <stdio.h>
#include <string.h>

void mySpaCeTeXt(string s);
bool isUpper(int ascii);
bool isLower(int ascii);
void toUpper(int c);
void toLower(int c);

int main (void)
{
    printf("Enter a sentence: ");
    string s = GetString();

    mySpaCeTeXt(s);
    return 0;
}

// Check string of characters
// and convert to mySpaceText
void mySpaCeTeXt(string s)
{

    int length = strlen(s);
    int start = 0;
    for (int i = 0; i < length; i++) {


        // Convert first letter to upper
        if ( i == 0 ) {
            toUpper(s[i]);
            start++;

        } else {

            // keep track of alphas
            // alternate case for all other letters
            if ( isUpper(s[i]) 
              || isLower(s[i])
            ) {

                // alternate case inversion
                if ( start % 2 == 0 ) {
                    if ( isUpper(s[i]) ) {
                        toLower(s[i]);
                    } else if ( isLower(s[i]) ) {
                        toUpper(s[i]);
                    }

                // skip conversion
                } else {
                    putchar(s[i]);
                }

                start++;

            // print non-alpha characters
            } else {
                putchar(s[i]);
            }
        }
    }
    printf("\n");
}

/*
 * Check character for uppercase
 */
bool isUpper(int ascii)
{
    // c >= 'A' && c <= 'B'
    return ((int) ascii > 64
        && (int) ascii < 91
    );
}

/*
 * Check for lowercase
 */
bool isLower(int ascii)
{
    return ((int) ascii > 96
        && (int) ascii < 123 
    );
}

/*
 * Print character as uppercase
 */
void toUpper(int c)
{
    putchar(c & 223);
}

/*
 * Print character as lowercase
 */
void toLower(int c)
{
    putchar(c | 32);
}

