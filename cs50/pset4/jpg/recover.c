/****************************************************************************
 * recover.c
 *
 * Computer Science 50
 * Problem Set 4
 *
 * Graham Schmidt
 *
 * Recovers JPEGs from a forensic image.
 ***************************************************************************/

#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>

#define CF_FILE "card.raw"
#define READ_BYTES 512

typedef uint8_t  BYTE;
int write_jpg(int file_number, FILE* fp);

int main(void)
{
    // open data file
    FILE* fp = fopen(CF_FILE, "r");    
    if (fp == NULL) {
        printf("Could not open %s.\n", CF_FILE);
        return 1;
    }

    // initialize file_number counter
    int file_number = 0;

    // read 512 bytes at a time until EOF
    BYTE bytes[READ_BYTES];
    while ( fread(&bytes, READ_BYTES, 1, fp) == 1 ) {
        
        // stop if start of JPG pattern detected
        if (
         (  bytes[0] == 0xff
         && bytes[1] == 0xd8
         && bytes[2] == 0xff)
         && (
            bytes[3] == 0xe0
         || bytes[3] == 0xe1
         )
        ) {
            // backup last BYTE read in fp
            fseek(fp, sizeof(bytes) * -1, SEEK_CUR);

            write_jpg(file_number, fp);

            // increment file for next iteration
            file_number++;
        }
    }

    // finished with file
    fclose(fp);

    return 0;
}

/**
 * write_jpg
 *
 * Open a new JPG file, and write the recovered data until a
 * new JPG file is encountered
 */
int
write_jpg(int file_number, FILE* fp)
{
    // open new file for writing
    char newfile[8];
    sprintf(newfile, "%03d.jpg", file_number);
    FILE *new = fopen(newfile, "w");
    if (new == NULL) {
        printf("Could not create %s\n", newfile);
        return 1;
    }

    // reuse buffer
    BYTE buffer[READ_BYTES];

    // read/write start of JPG file
    fread(&buffer, READ_BYTES, 1, fp);
    fwrite(&buffer, READ_BYTES, 1, new);

    // read until EOF
    while ( fread(&buffer, READ_BYTES, 1, fp) == 1 ) {

        // stop writing if new JPG encountered
        if (
         (  buffer[0] == 0xff
         && buffer[1] == 0xd8
         && buffer[2] == 0xff)
         && (
            buffer[3] == 0xe0
         || buffer[3] == 0xe1
         )
        ) {
            // detected start of JPG, go back last BYTE read
            fseek(fp, sizeof(buffer) * -1, SEEK_CUR);
            break;
        }

        // write JPG data
        fwrite(&buffer, READ_BYTES, 1, new);
    }

    fclose(new);

    return 0;
}
