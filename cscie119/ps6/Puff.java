/* 
 * Puff.java
 *
 * A program that decompresses a file that was compressed using 
 * Huffman encoding.
 *
 * Graham Schmidt, schmidtg@gmail.com
 * December 9, 2011
 */ 

import java.io.*;

public class Puff {

    /* Put any methods that you add here. */
    private HuffTree htree;

    /**
     * Constructor for new Huff object
     */
    public Puff() {
        htree = new HuffTree();
    }

    /**
     * Decode - Wrapper that calls the order of Huffman decoding functions
     * @param in - incoming object stream
     * @param out - outgoing stream
     * @param reader - read bytes of the incoming file
     */
    public void decode(ObjectInputStream in, FileWriter out, BitReader reader) {
        // Process the header that was created by the encoder
        /*
         Header Format: [int] - header length
                        {[c][f], [c][f], ...} - pairs of characters with num of freqs in doc
                        [num of bits to process] - determined in encoding to tell Puff to
                                                   stop processing bits
         */
        try {
            int header_length = in.readInt();

            // Read the char-freq pairs to build the char-freq hash
            for (int i = 0; i < header_length; i++) {
                // Read char-freq
                int c = in.readInt();
                // Cast the read integer to its equivalent character key
                char ch = (char) c;
                int f = in.readInt();

                // Add char-freq to map
                htree.buildCharFreqMap(ch, f);
            }

            // Add the Nodes to the tree, and actually build the Huffman Tree
            htree.addNodesToTree();
            htree.buildHuffTree();

            // Get number of bits to process from input file
            int total_bits_to_process = in.readInt();

            // Read file again. Grabbing a byte at a time and processing all of those bits
            // Stop processing if we reach the end-of-file (EOF) or once we've read the
            // number of bits we're suppose to process
            int bits_read = 0;
            while (true) {
                int b;
                // Check if EOF
                if ((b = reader.getBit()) == -1) {
                    break;
                }
                Object o = htree.followBit(b);
                bits_read++;
                // Only write leaf nodes
                if (!o.equals("")) {
                    char[] to_write = o.toString().toCharArray();
                    out.write(to_write[0]);
                }
                // Exit if we've read the total number of bits to process
                if (bits_read == total_bits_to_process) {
                    break;
                }
            }

            System.out.println("Total num of bits processed: " + bits_read);

        } catch (IOException e) {
            System.out.println("Can't read from file.");
            System.exit(1);
        }

        return;
    }

    /** 
     * main method for decompression.  Takes command line arguments. 
     * To use, type: java Puff input-file-name output-file-name 
     * at the command-line prompt.
     * @param args - Command line arguments
     * @throws IOException - Throw IO exception if problem reading/writing from file
     */
    public static void main(String[] args) throws IOException {
        ObjectInputStream in = null;      // reads in the compressed file
        FileWriter out = null;            // writes out the decompressed file

        // Check for the file names on the command line.
        if (args.length != 2) {
            System.out.println("Usage: java Puff <in fname> <out fname>");
            System.exit(1);
        }

        // Open the input file.
        try {
            in = new ObjectInputStream(new FileInputStream(args[0]));
        } catch (FileNotFoundException e) {
            System.out.println("Can't open file " + args[0]);
            System.exit(1);
        }

        // Open the output file.
        try {
            out = new FileWriter(args[1]);
        } catch (FileNotFoundException e) {
            System.out.println("Can't open file " + args[1]);
            System.exit(1);
        }
    
        // Create a BitReader that is able to read the compressed file.
        BitReader reader = new BitReader(in);

        /****** Add your code here. ******/
        Puff decompress = new Puff();
        decompress.decode(in, out, reader);

        /* Leave these lines at the end of the method. */
        in.close();
        out.close();
    }
}
