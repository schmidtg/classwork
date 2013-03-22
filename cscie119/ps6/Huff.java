/* 
 * Huff.java
 *
 * A program that compresses a file using Huffman encoding.
 *
 * Graham Schmidt, schmidtg@gmail.com
 * December 9, 2011
 */ 

import java.io.*;

public class Huff {

    /* Put any methods that you add here. */
    private HuffTree htree;

    /**
     * Constructor for new Huff object
     */
    public Huff() {
        htree = new HuffTree();
    }

    /**
     * Wrapper that calls the order of Huffman encoding functions
     * @param in - incoming object stream
     * @param in2 - incoming object stream
     * @param out - outgoing stream
     * @param writer - write bytes to the outgoing file
     */
    public void encode(FileReader in, FileReader in2, ObjectOutputStream out, BitWriter writer) {

        // Read from an input file, build the char-freq table, the huffman tree, and decode
        // each bit to an output file
        try {
            BufferedReader inStream = new BufferedReader(in);
            BufferedReader inStream2 = new BufferedReader(in2);

            // Read the input file and build the character-frequency map for all characters
            // in the file
            int r;
            while ((r = inStream.read()) != -1) {
                char ch = (char) r;
                String ch1 = "" + ch;
                htree.buildCharFreqMap(ch1);
            }

            // Add the Nodes to the tree, and actually build the Huffman Tree
            htree.addNodesToTree();
            htree.buildHuffTree();
            htree.writeHeader(out);
            System.out.println("Total bits to decode: " + htree.getTotalBitsToDecode());

            // Read input file again, writing each character's equivalent bitcode
            // to the compressed file
            while ((r = inStream2.read()) != -1) {
                char cht = (char) r;
                String ch2t = "" + cht;
                if (htree.getCode(ch2t) != null) {
                    char[] arr_bits = htree.getCode(ch2t).toCharArray();
                    Code c = new Code();

                    // Construct the Code object, which will get written to file
                    for(int j = 0; j < arr_bits.length; j++) {
                        if(arr_bits[j] == '1') {
                            c.addBit(1);
                        } else {
                            c.addBit(0);
                        }
                    }
                    writer.writeCode(c);
                }
            }

            // Determine number of bits to add to last byte to make it a full 8
            int num_last_byte = htree.getTotalBitsToDecode() % 8;
            int num_to_pad = 8 - num_last_byte;

            // Pad last byte with 'O's, to fill out last byte that will be read
            // Add one more extra bit to allow Puff to stop appropriately
            Code padding = new Code();
            for (int k = 0; k < num_to_pad; k++) {
                padding.addBit(0);
            }
            // Add one more bit to allow Puff to ignore
            padding.addBit(0);
            writer.writeCode(padding);

        } catch (IOException e) {
            System.out.println("Can't read from file.");
            System.exit(1);
        }

        return;
    }

    /** 
     * main method for compression.  Takes command line arguments. 
     * To use, type: java Huff input-file-name output-file-name 
     * at the command-line prompt.
     * @param args - Command line arguments
     * @throws IOException - Throw IO exception if problem reading/writing from file
     */ 
    public static void main(String[] args) throws IOException {

        FileReader in = null;               // reads in the original file
        FileReader in2 = null;               // reads in the original file
        ObjectOutputStream out = null;      // writes out the compressed file

        // Check for the file names on the command line.
        if (args.length != 2) {
            System.out.println("Usage: java Huff <in fname> <out fname>");
            System.exit(1);
        }

        // Open the input file.
        try {
            in = new FileReader(args[0]);
            in2 = new FileReader(args[0]);
        } catch (FileNotFoundException e) {
            System.out.println("Can't open file " + args[0]);
            System.exit(1);
        }

        // Open the output file.
        try {
            out = new ObjectOutputStream(new FileOutputStream(args[1]));
        } catch (FileNotFoundException e) {
            System.out.println("Can't open file " + args[1]);
            System.exit(1);
        }
    
        // Create a BitWriter that is able to write to the compressed file.
        BitWriter writer = new BitWriter(out);


        /****** Add your code below. ******/
        /* 
         * Note: after you read the input file once, you will need
         * to reopen it in order to read through the file
         * a second time.
         */

        Huff compress = new Huff();
        compress.encode(in, in2, out, writer);

        /* Leave these lines at the end of the method. */
        in.close();
        in2.close();
        out.close();
    }
}
