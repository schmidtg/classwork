#include <stdio.h>

int main(int argc, char* argv[])
{
    if (argc != 3)
    {
        printf("Usage: cp source destination\n");
        return 1;
    }

    // TODO: copy the contents of argv[1] to argv[2]
    // input file
    FILE *in = fopen(argv[1], "r");
    if (in == NULL) {
        printf("%s: %s: No such file or directory", argv[0], argv[1]);
        return 1;
    }

    // output file
    FILE *out = fopen(argv[2], "w");
    if (out == NULL) {
        printf("%s: %s: No such file or directory", argv[0], argv[2]);
        return 1;
    }

    int c;
    while ( (c = fgetc(in)) != EOF ) {
        fputc(c, out);
    }

    return 0;
}

