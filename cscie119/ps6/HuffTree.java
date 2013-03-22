/*
 * HuffTree.java
 * (borrowed logic from LinkedTree.java)
 *
 * Computer Science E-119
 *
 * Modifications and additions by:
 *     name: Graham Schmidt
 *     username: gschmidt
 */

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.*;
import java.util.List;


/**
 * HuffTree - a class that represents a binary tree containing data
 * items with integer keys.  If the nodes are inserted using the
 * insert method, the result will be a binary search tree.
 */
public class HuffTree {

    // An inner class for the nodes in the tree
    private class Node {
        private int key;         // the key field
        private Object data;     // the rest of the data item
        private Node left;       // reference to the left child/subtree
        private Node right;      // reference to the right child/subtree

        private Node next;      // referent to the next LLList node

        private Node(int key, Object data, Node left, Node right, Node next){
            this.key = key;
            this.data = data;
            this.left = left;
            this.right = right;
            this.next = next;
        }
        
        private Node(int key, Object data) {
            this(key, data, null, null, null);
        }

        private boolean isLeaf() {
            return (left == null && right == null);
        }
    }
    
    // Root of the tree as a whole
    private Node root;

    // LinkedList variables
    private Node front;
    private Node head;
    private Node rear;
    private Node current;

    private HashMap<Object, String> codes;
    private HashMap<String, Integer> unsorted_map;
    private LinkedHashMap<String, Integer> sorted_map = new LinkedHashMap<String, Integer>();

    private int total_bits_to_decode;


    /**
     * Constructor - initialize private variables
     */
    public HuffTree() {
        current = root = front = rear = null;
        head = new Node(0, "");
        codes = new HashMap<Object, String>();
        unsorted_map = new HashMap<String, Integer>();
        sorted_map = new LinkedHashMap<String, Integer>();
        total_bits_to_decode = 0;
    }

    /**
     * Build the [character, frequency] map using a HashMap
     * @param c - String key to increment frequency
     */
    public void buildCharFreqMap(String c) {
        // Check if Character exists in unsorted HashMap, increment its frequency
        if (unsorted_map.containsKey(c)) {
            Integer freq = unsorted_map.get(c);
            freq++;
            unsorted_map.put(c, freq);

        // Character not found in Hash, set initial value
        } else {
            unsorted_map.put(c, 1);
        }
    }

    /**
     * Build the <character, frequency> map using a HashMap
     * @param c - Character key to increment frequency
     * @param freq - Number of times this character appears in text
     */
    public void buildCharFreqMap(Character c, int freq) {
        String c_s = "" + c;
        sorted_map.put(c_s, freq);
    }

    /**
     * Sort the char-freq hashmap according to the frequencies (values)
     */
    private void sortCharFreqMap() {

        List<Integer> mapValues = new ArrayList<Integer>(unsorted_map.values());
        List<String> mapKeys = new ArrayList<String>(unsorted_map.keySet());

        Collections.sort(mapValues);
        Collections.sort(mapKeys);

        Iterator valueIt = mapValues.iterator();
        while (valueIt.hasNext()) {
            Object val = valueIt.next();
            Iterator keyIt = mapKeys.iterator();

            while (keyIt.hasNext()) {
                Object key = keyIt.next();
                String comp1 = unsorted_map.get(key).toString();
                String comp2 = val.toString();

                if (comp1.equals(comp2)){
                    unsorted_map.remove(key);
                    mapKeys.remove(key);
                    sorted_map.put((String)key, (Integer)val);
                    break;
                }
            }
        }
    }

    /**
     * Takes the Char-freq hashmap, sorts it, and converts all the key-value pairs
     * to HNodes, then adds them to a TreeSet
     */
    public void addNodesToTree() {
        // Sort the char-freq hashmap
        sortCharFreqMap();

        // Iterate through the c-f hashmap,
        // and convert each key-value pair to an HNode
        Iterator iterator = sorted_map.keySet().iterator();
        while (iterator.hasNext()) {
            Object c = iterator.next();
            Integer freq = sorted_map.get(c);

            // Add Node to the HuffTree
            addToEnd(freq, c);
        }
    }

    /**
     * Construct the HashMap which stores the associated bitcodes
     * @param n - Node being traversed
     * @param s - String that keeps track of the bitcodes traversed
     */
    private void createBitCodes(Node n, String s) {
        // Traverse the HuffTree
        // At each leaf, write the bits for that leaf to a char-bits table
        if(!n.isLeaf()) {
            createBitCodes(n.left, s + "0");
            createBitCodes(n.right, s + "1");
        } else {
            this.codes.put(n.data, s);
        }
    }

    /**
     * Get the bitcode associated with a character
     * @param c - lookup character
     * @return - String of the bitcode
     */
    public String getCode(String c) {
        return codes.get(c);
    }

    /**
     * Write the char-freq map (including its size and total num of bits to decompress)
     * to an output filestream
     * @param out - the object outputstream
     */
    public void writeHeader(ObjectOutputStream out) {
        try {
            // Write size of char-freq map
            out.writeInt(sorted_map.size());

            // Iterate through the c-f hashmap,
            // and convert each key-value pair to an HNode
            Iterator iterator = sorted_map.keySet().iterator();

            total_bits_to_decode = 0;
            while (iterator.hasNext()) {
                Object c = iterator.next();
                Integer freq = sorted_map.get(c);

                // Cast to primitive data type
                String c_int = c.toString();
                char[] c_int2 = c_int.toCharArray();

                // Compute the number of bits to process for decoding
                int total_bits_for_freq = freq * codes.get(c_int).length();
                total_bits_to_decode += total_bits_for_freq;

                // Write char-freqs to file
                out.writeInt(c_int2[0]);
                out.writeInt(freq);
            }
            out.writeInt(total_bits_to_decode);
        } catch (IOException e) {
            System.out.println("Can't read from file.");
            System.exit(1);
        }
    }

    /**
     * Return number of bits needed to decode
     * @return - int
     */
    public int getTotalBitsToDecode() {
        return total_bits_to_decode;
    }

    /**
     * createHuffTree - scan through existing HuffTree, combining the lowest frequency nodes
     * and merging into one node, adding that back into the List, until eventually there's
     * just one remaining Node which will be the root
     */
    public void buildHuffTree() {
        // Base case
        if(isDone()) {
            // Create the bitcodes
            String s = "";
            createBitCodes(root, s);
            return;
        }

        // Remove the two smallest nodes, and insert the merged node
        // back into the list
        combineTwoNodes();

        // Recursively call createHuffTree again
        buildHuffTree();
    }

    /**
     * followBit - Used when decoding. It traverses the
     * HuffmanTree based on the bit provided
     * keeping track of where the tree was last traversed from
     * or if it needs to traverse from the root.
     * @param bit - int 1 or 0
     * @return - Object of data if the Node followed was a leaf
     */
    public Object followBit(int bit) {

        Node n;

        // Set the current point to the root on fresh traversal
        if (current == null) {
            n = root;
        } else {
            n = current;
        }

        // Go left or right depending on incoming bit
        if (bit == 1) {
            n = n.right;
        } else {
            n = n.left;
        }

        // Update the current point to the existing Node
        current = n;

        // Print out leaf node
        if (n.isLeaf()) {
            current = null; // Reset current node
            return n.data;
        }

        // Return empty string if not a leaf node
        return "";

    }

    /**
     * addToEnd - adds the specified item at the rear of the ordered queue.
     * Always returns true, because the linked list is never full.
     *
     * @param freq - the frequency for this Node
     * @param data - the data item for this Node (character)
     */
    private void addToEnd(int freq, Object data) {
        Node newNode = new Node(freq, data);

        if (isEmpty()) {
            front = rear = newNode;
            head.next = front;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
    }

    /**
     * addInOrder - adds the specified item at the rear of the ordered queue.
     * Always returns true, because the linked list is never full.
     *
     * @param n - the merged node to add into the sorted Huffman LinkedList
     */
    private void addInOrder(Node n) {

        // Start before Front to be able to insert BEFORE node
        Node trav1 = head;
        Node trav2 = front;
        while (trav2 != null) {
            if (n.key <= trav2.key) {
                break;
            }
            trav1 = trav1.next;
            trav2 = trav2.next;
        }

        // Special Case: Insert into empty list
        if (front == null) {
            root = front = rear = n;
            head.next = front;

        // Insert at beginning of list if existing front key is
        // greater than or equal to new merged key
        } else if (head.next.key >= n.key) {
            n.next = front;
            root = front = n;
            head.next = front;

        // Insert new Node n BEFORE trav
        } else {
            n.next = trav1.next;
            trav1.next = n;
        }

        // Update rear Node when there's 2 or less nodes
        if (trav2 == null) {
            rear = n;
        }
    }

    /**
     * Remove the smallest node in the list
     * @return Node - the item removed
     */
    private Node removeSmallest() {

        if (isEmpty())
            return null;

        Node removed = front;

        // Remove the first item since it will always be the smallest
        if (front == rear)       // removing the only item
            front = root = rear = null;
        else {
            front = front.next;

            // Maintain root equal to the front node
            root = front;
            head.next = front;
        }

        return removed;
    }

    /**
     * Remove the two smallest Nodes (by frequency) in the List and merged them into
     * one Node, and add that back into the List
     */
    private void combineTwoNodes() {
        Node n1 = this.removeSmallest();
        Node n2 = this.removeSmallest();

        if (n1 != null && n2 != null) {
            int mergedFreq = n1.key + n2.key;

            Node merged = new Node(mergedFreq, "");

            merged.left = n1;
            merged.right = n2;

            // Add the merged node to this list
            this.addInOrder(merged);
        }
    }

    /**
     * isEmpty - returns true if the queue is empty, and false otherwise
     * @return - true/false if HuffmanTree is empty
     */
    private boolean isEmpty() {
        return (front == null);
    }

    /**
     * isDone - returns true if the queue has just one remaining root node
     * @return - true/false if HuffmanTree has one node remaining
     */
    private boolean isDone() {
        return (front == rear);
    }

}


