/****************************************************************************
 * copy.c
 *
 * Computer Science 50
 * Problem Set 4
 *
 * Copies a BMP piece by piece, just because.
 ***************************************************************************/
       
#include <stdio.h>
#include <stdlib.h>

#include "bmp.h"

int main(int argc, char* argv[])
{
    // ensure proper usage
    if (argc != 4)
    {
        printf("Usage: resize n infile outfile\n");
        return 1;
    }

    // store resize factor
    int n = atoi(argv[1]);
    if ( n < 1
      || n > 100
    ) {
        printf("Please enter a factor between 1 and 100\n");
        return 2;
    }

    // remember filenames
    char* infile = argv[2];
    char* outfile = argv[3];

    // open input file 
    FILE* inptr = fopen(infile, "r");
    if (inptr == NULL)
    {
        printf("Could not open %s.\n", infile);
        return 3;
    }

    // open output file
    FILE* outptr = fopen(outfile, "w");
    if (outptr == NULL)
    {
        fclose(inptr);
        fprintf(stderr, "Could not create %s.\n", outfile);
        return 4;
    }

    // read infile's BITMAPFILEHEADER
    BITMAPFILEHEADER bf;
    fread(&bf, sizeof(BITMAPFILEHEADER), 1, inptr);

    // read infile's BITMAPINFOHEADER
    BITMAPINFOHEADER bi;
    fread(&bi, sizeof(BITMAPINFOHEADER), 1, inptr);

    // ensure infile is (likely) a 24-bit uncompressed BMP 4.0
    if (bf.bfType != 0x4d42 || bf.bfOffBits != 54 || bi.biSize != 40 || 
        bi.biBitCount != 24 || bi.biCompression != 0)
    {
        fclose(outptr);
        fclose(inptr);
        fprintf(stderr, "Unsupported file format.\n");
        return 5;
    }

    // store infile width and height
    int width_in = bi.biWidth;
    int height_in = bi.biHeight;

    // update relevant image info
    bi.biWidth = bi.biWidth * n;
    bi.biHeight = bi.biHeight * n;
    int rowSize = (int) ((bi.biBitCount * bi.biWidth + 31) / 32) * 4;
    bi.biSizeImage = rowSize * abs(bi.biHeight);

    // update relevant header info
    bf.bfSize = 54 + bi.biSizeImage;

    // write outfile's BITMAPFILEHEADER
    fwrite(&bf, sizeof(BITMAPFILEHEADER), 1, outptr);

    // write outfile's BITMAPINFOHEADER
    fwrite(&bi, sizeof(BITMAPINFOHEADER), 1, outptr);

    // determine padding for scanlines
    int padding_in =  (4 - (width_in * sizeof(RGBTRIPLE)) % 4) % 4;
    int padding_out =  (4 - (bi.biWidth * sizeof(RGBTRIPLE)) % 4) % 4;

    // iterate over infile's scanlines
    int seek = 0;
    for (int i = 0, biHeight = abs(height_in); i < biHeight; i++)
    {
        for (int h = 0; h < n; h++) {

            // go back in file to rewrite the pixels from inptr
            fseek(inptr, seek, SEEK_CUR);

            // reset seek
            if (seek < 0)
                seek = 0;

            // iterate over pixels in scanline
            for (int j = 0; j < width_in; j++)
            {
                // temporary storage
                RGBTRIPLE triple;

                // read RGB triple from infile
                fread(&triple, sizeof(RGBTRIPLE), 1, inptr);

                // write RGB triple to outfile n times
                for (int w = 0; w < n; w++) {
                    fwrite(&triple, sizeof(RGBTRIPLE), 1, outptr);
                }

                seek -= sizeof(RGBTRIPLE);
            }

            // skip over padding, if any (adjust for n)
            fseek(inptr, padding_in, SEEK_CUR);
            seek -= padding_in;

            // then add it back (to demonstrate how)
            for (int k = 0; k < padding_out; k++)
                fputc(0x00, outptr);
        }

        // reset for next scanline
        seek = 0;
    }

    // close infile
    fclose(inptr);

    // close outfile
    fclose(outptr);

    // that's all folks
    return 0;
}
