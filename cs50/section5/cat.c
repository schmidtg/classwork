#include <stdio.h>

int main(int argc, char* argv[])
{
    if (argc < 2)
    {
        printf("Usage: cat filename [filename ...]\n");
        return 1;
    }

    for (int i = 1; i < argc; i++)
    {
        // TODO: open argv[i] and print its contents to stdout

        // fopen filename[i]
        // get contents using fscanf
        // print contents using fprintf
        // close file
        
        FILE *file = fopen(argv[i], "r");

        if (file == NULL) {
            printf("%s: %s: No such file or directory\n", argv[0], argv[i]);
            return 1;
        }

        // get contents of each line
        int c;
        while ((c = fgetc(file)) != EOF) {
            printf("%c", c);
        }

    }

    return 0;
}

