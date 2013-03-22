/*************************************************************
*
* crack.c
*
* Computer Science 50
* Pset2 - Hacker
* Graham Schmidt
*
* Crack a series of passwords encrypted with C's  DES-based
* 'crypt'. Uses a dictionary attack first, then attempts brute-force
*
**************************************************************/

#define _XOPEN_SOURCE
#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <cs50.h>
#include <string.h>
#include <stdbool.h>

// declarations
void bruteForce(char *ciphertext, string salt);
string generateDictionary(int length, string dict_output, string dict_input);

int main(int argc, char *argv[])
{
    // Sanity check
    if ( argc != 2 ) {
        printf("Usage: %s password\n", argv[0]);
        return 1;
    }

    // we have valid input
    string dictionary = "words_62.txt";
    /* string dictionary = generateDictionary(8, "filter_output.txt", "/usr/share/dict/words"); */

    // open file
    FILE *file =  fopen( dictionary, "r");

    // check if file is valid
    if ( file == 0 ) {
        printf("Could not open file '%s'\n", dictionary);
        return 1;
    } 

    // store the salt
    char salt[3];
    salt[0] = argv[1][0];
    salt[1] = argv[1][1];
    salt[2] = '\0';

    // store each line in buffer
    char buffer[128];
    bool found = false;
    while ( fscanf(file, "%127s", buffer) == 1 ) {

        // apply crypto
        char *crypt_word = crypt(buffer, salt);

        // compare word on line to user input
        // exit immediately if found
        if ( strcmp(crypt_word, argv[1]) == 0 ) {
            found = true;
            break;
        }
    }
    fclose(file);

    // success message
    if ( found ) {
        printf("%s\n", buffer);

    // not found in dictionary, try brute-force
    } else {
        bruteForce(argv[1], salt);
    }

    return 0;
}

/**
 * Use a series of for-loops to try all possible combinations
 * of printable ASCII characters for each placeholder
 *
 * Limitations/considerations
 *
 * [95] * [95] * [95] * [95] * [95] * [95] * [95] * [95]
 * 95 ^ 8 = 6634204312890625 combinations
 * would take about 8.41 years at 25,000,000 cracks per second
 *
 * Although this solution is definitely not optimal, it is complete.
 * It checks through every possible character for each placeholder.
 *
 * My apologies for not abstracting this into functions. It definitely
 * lends itself to a repetitive pattern, however, I'm not as comfortable
 * with C just yet, so expressing myself is limited and less elegant for now.
 *
 */
void bruteForce(char *ciphertext, string salt)
{
    // 95 printable characters of ASCII
    string ascii = " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}";

    // initialize variables
    int combos = strlen(ascii);
    bool found = false;
    char word[9];
    char *crypt_word = "";
    unsigned long count = 0;
    string found_word = "";

    // go through all ascii characters - first place
    for (int i = 0; i < combos; i++) {
        count++;

        // one letter words
        word[0] = ascii[i];
        word[1] = '\0';

        // apply crypto
        crypt_word = crypt(word, salt);
        if ( strcmp(crypt_word, ciphertext) == 0 ) {
            found = true;
            found_word = word;
            break;
        }

        // go through all ascii characters - second place
        for (int j = 0; j < combos; j++) {
            count++;

            // two letter combos
            word[0] = ascii[i];
            word[1] = ascii[j];
            word[2] = '\0';

            // apply crypto
            crypt_word = crypt(word, salt);
            if ( strcmp(crypt_word, ciphertext) == 0 ) {
                found = true;
                found_word = word;
                break;
            }

            // go through all ascii characters - third place
            for (int k = 0; k < combos; k++) {
                count++;

                // three letter combos
                word[0] = ascii[i];
                word[1] = ascii[j];
                word[2] = ascii[k];
                word[3] = '\0';

                // apply crypto
                crypt_word = crypt(word, salt);
                if ( strcmp(crypt_word, ciphertext) == 0 ) {
                    found = true;
                    found_word = word;
                    break;
                }

                // go through all ascii characters - 4th place
                for (int l = 0; l < combos; l++) {
                    count++;

                    // four letter words
                    word[0] = ascii[i];
                    word[1] = ascii[j];
                    word[2] = ascii[k];
                    word[3] = ascii[l];
                    word[4] = '\0';

                    // apply crypto
                    crypt_word = crypt(word, salt);
                    if ( strcmp(crypt_word, ciphertext) == 0 ) {
                        found = true;
                        found_word = word;
                        break;
                    }

                    // go through all ascii characters - 5th place
                    for (int m = 0; m < combos; m++) {
                        count++;

                        // five letter words
                        word[0] = ascii[i];
                        word[1] = ascii[j];
                        word[2] = ascii[k];
                        word[3] = ascii[l];
                        word[4] = ascii[m];
                        word[5] = '\0';

                        // apply crypto
                        crypt_word = crypt(word, salt);
                        if ( strcmp(crypt_word, ciphertext) == 0 ) {
                            found = true;
                            found_word = word;
                            break;
                        }

                        // go through all ascii characters - sixth place
                        for (int n = 0; n < combos; n++) {
                            count++;

                            // six letter words
                            word[0] = ascii[i];
                            word[1] = ascii[j];
                            word[2] = ascii[k];
                            word[3] = ascii[l];
                            word[4] = ascii[m];
                            word[5] = ascii[n];
                            word[6] = '\0';

                            // apply crypto
                            crypt_word = crypt(word, salt);
                            if ( strcmp(crypt_word, ciphertext) == 0 ) {
                                found = true;
                                found_word = word;
                                break;
                            }

                            // go through all ascii characters - seventh place
                            for (int o = 0; o < combos; o++) {
                                count++;

                                // seven letter words
                                word[0] = ascii[i];
                                word[1] = ascii[j];
                                word[2] = ascii[k];
                                word[3] = ascii[l];
                                word[4] = ascii[m];
                                word[5] = ascii[n];
                                word[6] = ascii[o];
                                word[7] = '\0';

                                // apply crypto
                                crypt_word = crypt(word, salt);
                                if ( strcmp(crypt_word, ciphertext) == 0 ) {
                                    found = true;
                                    found_word = word;
                                    break;
                                }

                                // go through all ascii characters - eighth place
                                for (int p = 0; p < combos; p++) {
                                    count++;

                                    // eight letter words
                                    word[0] = ascii[i];
                                    word[1] = ascii[j];
                                    word[2] = ascii[k];
                                    word[3] = ascii[l];
                                    word[4] = ascii[m];
                                    word[5] = ascii[n];
                                    word[6] = ascii[o];
                                    word[7] = ascii[p];
                                    word[8] = '\0';

                                    // apply crypto
                                    crypt_word = crypt(word, salt);
                                    if ( strcmp(crypt_word, ciphertext) == 0 ) {
                                        found = true;
                                        found_word = word;
                                        break;
                                    }
                                    printf("%s\n", word);
                                    printf("%s\n\n", crypt_word);
                                }
                                // break out of nested loop
                                if (found) {
                                    break;
                                }
                            }
                            if (found) {
                                break;
                            }
                        }
                        if (found) {
                            break;
                        }
                    }
                    if (found) {
                        break;
                    }
                }
                if (found) {
                    break;
                }
            }
            if (found) {
                break;
            }
        }
        if (found) {
            break;
        }
    }

    // success message
    if ( found ) {
        printf("%s\n", found_word);
    }
}

/**
 * Parse the dict/words file and save only those
 * words 8 characters or less
 */
string generateDictionary(int length, string dict_output, string dict_input)
{
    // default length
    if ( length == 0 ) {
        length = 8;
    }

    // default filename
    if ( dict_output == NULL 
      || strlen(dict_output) == 0
    ) {
        dict_output = "filtered_dict.txt";
    }

    // default dictionary path
    if ( dict_input == NULL 
      || strlen(dict_input) == 0
    ) {
        dict_input = "/usr/share/dict/words";
    }

    // Open dict file, read each line
    FILE *file =  fopen( dict_input, "r");

    // Exit if not valid
    if ( file == 0 ) {
        printf("Could not open file %s\n", dict_input);
        exit(1);
    }

    // create output stream to store 8-char words
    FILE *out;
    out = fopen( dict_output, "w" );
            
    // store each line in buffer
    char buffer[128];
    while ( fscanf(file, "%127s", buffer) == 1 ) {

        // save 8 character words to output file
        if ( strlen(buffer) <= length ) {
            if( out != NULL ) {
                fprintf( out, "%s\n", buffer );              
            }
        }
    }
    fclose(out);
    fclose(file);

    // Provide filename of saved dictionary output
    return dict_output;
}

