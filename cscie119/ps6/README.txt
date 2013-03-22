Overview of using Huff, Puff and Huffman
Graham Schmidt
Harvard ID: 30825489
gschmidt@harvard.nice.edu, schmidtg@gmail.com

Modified Classes
- Huff 
- Puff
- Huffman

Provided Classes
- BitReader
- BitWriter
- Code


Huff Class
@uses Huffman
@uses BitWriter
@uses Code

This class takes an uncompressed, incoming filestream, and encodes it to a file after having converted 
the stream into a HuffmanTree object. The HuffmanTree class does the majority of "heavy-lifting" 
in terms of building and storing the char-freq map, building a Huffman Tree, and providing
methods to instantiate all of these processes from the Huff object.

The Huff class's main purpose is to provide an I/O solution to compressing a data stream.

Puff Class
@uses Huffman
@uses BitReader
@uses Code

This class takes an incoming, compressed filestream and decompresses the file by writing it to
an uncompressed output file. It handles many of the I/O operations that interact directly
with the processes of the HuffmanTree class. The main method is 'decode' which is a wrapper
that reads an inputstream, builds a HuffmanTree object, interprets each bit in the compressed
file and writes the appropriate characters to an output file.


HuffmanTree Class
The HuffmanTree class handles all of the processes that support creating, and traversing
through a Huffman tree. It has a number of private variables that make up it's unique data structure,
whic is a hybrid data structure of a LinkedList, Queue, and LinkedTree. I uses a Hashmap to efficiently
store the access the <character, frequency> pairs based on the file I/O.

I use a number of private methods and helpers that are called internally in the class when the
public methods are called from outside the class. This helps to encapsulate specific logic in
the Huffman Tree since much of it gets used in both the Huff and Puff classes. I tried to minimize
the amount of code/logic duplication in all classes.

EFFICIENCY DISCUSSION
I tried to keep the implementation of the Huffman tree encoding/decoding as efficient as possible.
First off I chose to use the built-in Hashmap as a data structure to store my character-frequency pairs.
The Hashmap provides an efficiency of O(1) constant time for inserting, removing and accessing the char-freq
pairs. When processing a large file, this makes a significant improvement over using an alternate solution 
such as a simple array or linked-list to store the char-freq pairs.

The Huffman Tree itself is setup as a linked-list/queue so new char-freq pairs can be added as new nodes
in O(1) time. Because I wanted to keep the frequencies in ascending order, I needed 'next' pointers between
the nodes (like a linked-list) and both a front and rear pointers (like queues) to be able to initially insert 
all of the nodes when initially building the tree in constant time. Adding new nodes to the end of the list 
(the addtoEnd() method) has O(1) efficiency, while inserting new merged Nodes into the sorted linked-list 
(the addInOrder() method) is O(n) efficient since it needs to traverse from the beginning each time.

The building of the Huffman Tree itself is done via a recursive method 'buildHuffmanTree()'. Since the
nodes are kept in sorted order, I'm able to recursively remove the two nodes at the beginning of the
list each time, combine their frequencies into a new node, and add this node back into the list in
sorted order. This method is O(n) efficiency since it involves inserting new merged nodes in
sorted order. Once the 'buildHuffmanTree()' method has finished, I'm able to traverse the Huffman Tree to 
both create and read bitcodes for each character. Traversing this tree is O(logn) since we're doing a 
simple binary search each time a code is looked up.

Overall the time efficiency of my solution is O(n). The creation of the compressed file depends on the size
of the file. The slowest part is inserting new merged nodes into a sorted list, but this part is required
in order to build the Huffman tree accurately.
