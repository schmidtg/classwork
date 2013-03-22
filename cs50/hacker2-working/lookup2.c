#include <stdio.h>
#include <stdlib.h>
#include <cs50.h>
#include <string.h>
#include <stdbool.h>

#define DICT "/usr/share/dict/words"

int main(int argc, char *argv[])
{
    if (argc != 2) {
        printf("Wrong usage.\n");
        exit(1);
    }

    FILE* dictionary = fopen(DICT, "r");
    if (dictionary == NULL) {
        printf("Could not find file.\n");
        exit(1);
    }

    char buffer[128];

    bool found = false;
    while( fscanf(dictionary, "%127s", buffer) == 1) {

        // see if buffer matches argument
        if (strcmp(buffer, argv[1]) == 0) {
            found = true;
            break;
        }
    }
    fclose(dictionary);

    if (found) {
        printf("FOUND: %s\n", argv[1]);
    } else {
        printf("NOT FOUND\n");
    }
}
