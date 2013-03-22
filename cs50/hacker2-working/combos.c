#define _XOPEN_SOURCE
#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <cs50.h>
#include <string.h>
#include <stdbool.h>

// Functions
void bruteForce(char *ciphertext, string salt);

int main(int argc, char *argv[])
{
    // Sanity check
    if ( argc != 2 ) {
        printf("Usage: %s password\n", argv[0]);
        return 1;
    }

    // store the salt
    char salt[3];
    salt[0] = argv[1][0];
    salt[1] = argv[1][1];
    salt[2] = '\0';

    bruteForce(argv[1], salt);
    return 0;
}

/**
 * Use a series of for-loops to try all possible combinations
 * of printable ASCII characters for each placeholder
 */
void bruteForce(char *ciphertext, string salt)
{
    // 95 printable characters of ASCII
    string ascii = " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}";

    // initialize variables
    int combos = strlen(ascii);
    bool found = false;
    char word[combos + 1];
    char *crypt_word = "";
    unsigned long count = 0;
    string found_word = "";

    printf("Checking...\n");
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
        printf("FOUND! %s in %lu iterations\n", found_word, count);
    } else {
        printf("NOT FOUND in %lu iterations\n", count);
    }
}
