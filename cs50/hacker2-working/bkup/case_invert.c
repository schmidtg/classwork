/**
 * Invert a sentence, skip non-alphabetic characters
 */

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

// Check each character
// for conversion
void mySpaCeTeXt(string s)
{

    int length = strlen(s);
    int prevSpace = 0;
    int start = 0;
    for (int i = 0; i < length; i++) {

        // Only keep track of alphas
        // from start, alternate case for all other letters

        // Convert first letter to upper
        if ( i == 0 ) {
            toUpper(s[i]);
            start++;

        } else {

            // upperase new word
            if (prevSpace == 1
                && s[i] != ' '
            ) {
                if ( isLower(s[i]) ) {
                    toUpper(s[i]);
                } else {
                    putchar(s[i]);
                }
                prevSpace = 0;
                // reset new start new word
                start = 0;

            } else {

                // handle word after space
                if ( s[i] == ' ' ) {
                    prevSpace = 1;
                    putchar(s[i]);

                } else {
                    /* printf("\nstart %d\n", start); */

                    // alternating characters
                    if ( start % 2 == 0 ) {
                        /* printf("\nstart %% 2%d\n", start % 2); */
                        if ( isUpper(s[i]) ) {
                            toLower(s[i]);
                        } else if ( isLower(s[i]) ) {
                            toUpper(s[i]);
                        } else {
                            putchar(s[i]);
                        }
                    } else {
                        putchar(s[i]);
                    }
                    start++;
                }
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

