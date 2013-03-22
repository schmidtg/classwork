/*
 * StringNode.java
 *
 * Author:          Computer Science E-119
 * Modified by:     Graham Schmidt, schmidtg@gmail.com
 */

import java.io.*;
import java.util.*;

/**
 * A class for representing a string using a linked list.  Each
 * character of the string is stored in a separate node.  
 *
 * This class represents one node of the linked list.  The string as a
 * whole is represented by storing a reference to the first node in
 * the linked list.  The methods in this class are static methods that
 * take a reference to a string linked-list as a parameter.  This
 * approach allows us to use recursion to write many of the methods.
 */
public class StringNode {
    private char ch;
    private StringNode next;

    /**
     * Constructor
     */
    public StringNode(char c, StringNode n) {
        ch = c;
        next = n;
    }

    /**
     * getNode - private helper method that returns a reference to
     * node i in the given linked-list string.  If the string is too
     * short, returns null.
     */
    private static StringNode getNode(StringNode str, int i) {
        StringNode trav = str;
        int ref = 0;

        // Sanity check
        if (i < 0 || str == null)
            return null;

        while (trav != null) {
            if (ref == i) {
                return trav;
            }

            ref++;
            trav = trav.next;
        }

        return null;
    }

    /*****************************************************
     * Public methods (in alphabetical order)
     *****************************************************/

    /**
     * charAt - returns the character at the specified index of the
     * specified linked-list string, where the first character has
     * index 0.  If the index i is < 0 or i > length - 1, the method
     * will end up throwing an IllegalArgumentException.
     */
    public static char charAt(StringNode str, int i) {
        if (str == null)
            throw new IllegalArgumentException("the string is empty");
        
        StringNode node = getNode(str, i);

        if (node != null) 
            return node.ch;     
        else
            throw new IllegalArgumentException("invalid index: " + i);
    }

    /**
     * compareAlpha - compares two linked-list strings to determine
     * which comes first alphabetically (i.e., according  to the ordering 
     * used for words in a dictionary). 
     * 
     * It returns:
     *    1 if str1 comes first alphabetically
     *    2 if str2 comes first alphabetically
     *    0 if str1 and str2 represent the same string
     * 
     * The empty string comes before any non-empty string, 
     * and the prefix of a string comes before the string
     * itself (e.g., "be" comes before "become").
     */
    public static int compareAlpha(StringNode str1, StringNode str2) {
        if (str1 == null && str2 == null)
            return 0;
        else if (str1 == null)
            return 1;
        else if (str2 == null)
            return 2;

        StringNode trav1 = str1;
        StringNode trav2 = str2;

        while (trav1 != null || trav2 != null) {

            // Compare if str1 comes first
            if (trav1.ch < trav2.ch) {
                return 1;
            }
            // Compare if str2 comes first
            if (trav2.ch < trav1.ch) {
                return 2;
            }

            // Advance the pointers
            trav1 = trav1.next;
            trav2 = trav2.next;
        }

        // str1 and str2 represent the same string
        return 0;
    }
    
    /**
     * concat - returns the concatenation of two linked-list strings
     */
    public static StringNode concat(StringNode str1, StringNode str2) {
        StringNode cat;

        if (str1 == null)
            return copy(str2);
        else if (str2 == null)
            return copy(str1);
        else {

            StringNode trav = str1;

            // Create copy of str1
            cat = new StringNode(trav.ch, trav.next);
            trav = trav.next;

            // Create iterator node for copied linked-list
            StringNode travCopy = cat;

            // Create copy of next node in str, then link that into the copy of str
            while (trav != null) {
                travCopy.next = new StringNode(trav.ch, null);

                trav = trav.next;
                travCopy = travCopy.next;
            }

            // Copy str2 into next list
            StringNode cat2 = copy(str2);

            // Set last node of our concat string equal to the copy of str2
            travCopy.next = cat2;
        }

        return cat;
    }

    /**
     * convert - converts a standard Java String object to a linked-list
     * string and returns a reference to the linked-list string
     */
    public static StringNode convert(String s) {
        if (s.length() == 0)
            return null;

        StringNode firstNode = new StringNode(s.charAt(0), null);
        StringNode prevNode = firstNode;
        StringNode nextNode;

        for (int i = 1; i < s.length(); i++) {
            nextNode = new StringNode(s.charAt(i), null);
            prevNode.next = nextNode;
            prevNode = nextNode;
        }

        return firstNode;
    }
    
    /**
     * copy - returns a copy of the given linked-list string
     */
    public static StringNode copy(StringNode str) {
        if (str == null)
            return null;

        StringNode trav = str;

        // Keep track of beginning of next string
        StringNode copyFirst = new StringNode(trav.ch, trav.next);
        trav = trav.next;

        // Create iterator node for copied linked-list
        StringNode travCopy = copyFirst;

        // Create copy of next node in str, then link that into the Copy of str
        while (trav != null) {
            travCopy.next = new StringNode(trav.ch, null);

            trav = trav.next;
            travCopy = travCopy.next;
        }

        return copyFirst;
    }

    /**
     * deleteChar - deletes character i in the given linked-list string and
     * returns a reference to the resulting linked-list string
     */
    public static StringNode deleteChar(StringNode str, int i) {
        if (str == null)
            throw new IllegalArgumentException("string is empty");
        else if (i < 0) 
            throw new IllegalArgumentException("invalid index: " + i);
        else if (i == 0) 
            str = str.next;
        else {
            StringNode prevNode = getNode(str, i-1);
            if (prevNode != null && prevNode.next != null) 
                prevNode.next = prevNode.next.next;
            else
                throw new IllegalArgumentException("invalid index: " + i);
        }

        return str;
    }

    /**
     * indexOf - returns the position of the first occurrence of
     * character ch in the given linked-list string.  If there is
     * none, returns -1.
     */
    public static int indexOf(StringNode str, char ch) {

        // Emptry string
        if(str == null) {
            return -1;
        }

        StringNode trav = str;
        int pos = 0;
        while (trav != null && trav.ch != ch) {
            pos++;

            // Exit loop if at end
            if (trav == null) {
                return -1;
            }

            trav = trav.next;
        }

        // Loop would have stopped if trav.ch == ch, return the position
        return pos;
    }

    /**
     * insertChar - inserts the character ch before the character
     * currently in position i of the specified linked-list string.
     * Returns a reference to the resulting linked-list string.
     */
    public static StringNode insertChar(StringNode str, int i, char ch) {
        StringNode newNode, prevNode;

        if (i < 0) 
            throw new IllegalArgumentException("invalid index: " + i);
        else if (i == 0) {
            newNode = new StringNode(ch, str);
            str = newNode;
        } else {
            prevNode = getNode(str, i-1);
            if (prevNode != null) {
                newNode = new StringNode(ch, prevNode.next);
                prevNode.next = newNode;
            } else 
                throw new IllegalArgumentException("invalid index: " + i);
        }

        return str;
    }

    /**
     * Return the character with the largest character code in the string
     */
    public static char largestChar(StringNode str)
    {
        // Base case
        if (str == null)
            return '\0';

        // Recurse through to end of list, keep returning
        // each character and compare it to the current one
        char largest_char = largestChar(str.next);

        if (str.ch > largest_char)
            return str.ch;
        else
            return largest_char;
    }

    /**
     * insertSorted - inserts character ch in the correct position
     * in a sorted list of characters (i.e., a sorted linked-list string)
     * and returns a reference to the resulting list.
     */
    public static StringNode insertSorted(StringNode str, char ch) {
        StringNode newNode, trail, trav;

        // Find where the character belongs.
        trail = null;
        trav = str;
        while (trav != null && trav.ch < ch) {
            trail = trav;
            trav = trav.next;
        }

        // Create and insert the new node.
        newNode = new StringNode(ch, trav);
        if (trail == null) {
            // We never advanced the prev and trav references, so
            // newNode goes at the start of the list.
            str = newNode;
        } else 
            trail.next = newNode;

        return str;
    }

    /**
     * length - recursively determines the number of characters in the
     * linked-list string to which str refers
     */
    public static int length(StringNode str) {
        StringNode trav = str;
        int numLength = 0;

        while (trav != null) {
            numLength++;
            trav = trav.next;
        }

        return numLength;
    }

    /**
     * numOccurrences - find the number of occurrences of the character
     * ch in the linked list to which str refers
     */
    public static int numOccurrences(StringNode str, char ch) {
        if (str == null)
            return 0;
     
        int numOccur = numOccurrences(str.next, ch);
        if (str.ch == ch)
            numOccur++;
        
        return numOccur;
    }

    /**
     * print - recursively writes the specified linked-list string to System.out
     */
    public static void print(StringNode str) {
        StringNode trav = str;

        while (trav != null) {
            // Traverse list, print contents along the way
            System.out.print(trav.ch);
            trav = trav.next;
        }
    }

    /**
     * printEveryOther - print every other character in the string represented by str to System.out
     */
    public static void printEveryOther(StringNode str) {

        // Base case, end of string
        if (str == null) {
            return;
        }

        System.out.print(str.ch);

        // Do not skip next item if near end of string
        if (str.next == null) {
            printEveryOther(str.next);

        // Skip next item
        } else {
            printEveryOther(str.next.next);
        }
    }

    /**
     * startsWith - Determine if the string starts with a specified parameter prefix
     */
    public static boolean startsWith(StringNode str, StringNode prefix) {

        // Special case, in case both strings are null
        if (str == null && prefix != null)
            return false;

        // Got to end of either string, the prefix matches
        if (str == null || prefix == null)
            return true;

        // Compare current chars and continue until they're not equal
        return (str.ch == prefix.ch
                ? startsWith(str.next, prefix.next)
                : false
        );
    }

    /**
     * lastIndexOf - Find and return the index of the last occurence of the character 'ch' in the string 'str'
     * or -1 if 'ch' does not appear in 'str'
     */
    public static int lastIndexOf(StringNode str, char ch) {
        // Found, or reached end of word
        if (str == null) {
            return -1;
        }

        // Continue to end
        int index = lastIndexOf(str.next, ch);

        // Each return, compare the current ch to ch, if found start counting from that position
        return (index == -1
                ? (str.ch == ch ? 0 : -1)
                : index + 1);
    }

    /**
     * read - reads a string from an input stream and returns a
     * reference to a linked list containing the characters in the string
     */
    public static StringNode read(InputStream in) throws IOException {
        StringNode str; 
        char ch = (char)in.read();

        if (ch == '\n')    // base case
            str = null;         
        else
            str = new StringNode(ch, read(in));
    
        return str;
    }
    
    /*
     * substring - creates a new linked list that represents the substring 
     * of str that begins with the character at index start and has
     * length (end - start). It thus has the same behavior as the
     * substring method in the String class.
     * 
     * Throws an exception if start < 0, end < start, or 
     * if end > the length of the string.
     * 
     * Note that our method does NOT need to call the length()
     * method to determine if end > the length, and neither
     * should your revised method.
     */
    public static StringNode substring(StringNode str, int start, int end) {
        // Check for invalid parameters. 
        if (start < 0 || end < start)
            throw new IndexOutOfBoundsException();
        
        if (start == 0 && end == 0)   // base case
            return null;
        else if (str == null)         // end > length
            throw new IndexOutOfBoundsException();

        // Reference to beginning of incoming string
        StringNode strFrom = getNode(str, start);
        int str_length = end - start;

        //Create new StringNode to return
        StringNode newStr;

        newStr = new StringNode(strFrom.ch, null);
        strFrom = strFrom.next;

        // Point to first node of string we're returning
        StringNode newStrIt = newStr;

        for (int i = 0; i < str_length; i++) {

            // Last node
            if (i == str_length - 1) {
                newStr.next = new StringNode(strFrom.ch, null);

            } else {
                newStr.next = new StringNode(strFrom.ch, null);

                // Increment iterators
                strFrom = strFrom.next;
                newStr = newStr.next;
            }
        }

        return newStrIt;
    }
    
    /*
     * toString - creates and returns the Java string that
     * the current StringNode represents.  Note that this
     * method -- unlike the others -- is a non-static method.
     * Thus, it will not work for empty strings, since they
     * are represented by a value of null, and we can't use
     * null to invoke this method.
     */
    public String toString() {
        String str = "";
        StringNode trav = this;   // start trav on the current node
            
        while (trav != null) {
            str = str + trav.ch;
            trav = trav.next;
        }
         
        return str;
    }
    
    /**
     * toUpperCase - converts all of the characters in the specified
     * linked-list string to upper case.  Modifies the list itself,
     * rather than creating a new list.
     */
    public static void toUpperCase(StringNode str) {        
        StringNode trav = str; 
        while (trav != null) {
            trav.ch = Character.toUpperCase(trav.ch); 
            trav = trav.next;
        }
    } 
              
    public static void main(String[] args) throws IOException {
        StringNode copy, str, str1, str2, str3;
        String line;
        int n;
        int l;
        char ch;


        // Test: 2a - printEveryOther()
        System.out.println("Testing: 2a.");
        str = convert("method frame");
        System.out.print("Str: ");
        print(str);
        System.out.println("");
        System.out.print("Print every other: ");
        printEveryOther(str);

        // Test: 2b - largestChar()
        System.out.println("");
        System.out.println("");
        System.out.println("Testing: 2b.");
        str = convert("method frame");
        System.out.print("Str: ");
        print(str);
        System.out.println("");
        char large = largestChar(str);
        System.out.println("Largest char: " + large);

        // Test: 2c - startsWith()
        System.out.println("");
        System.out.println("Testing: 2c.");
        str1 = convert("recursion");
        str2 = convert("recur");
        str3 = convert("recurse");

        System.out.print("Check if str1 '" + str1.toString() + "' starts with str2 '" + str2.toString() + "': ");
        if(startsWith(str1, str2)) {
            System.out.print("YES");
        } else {
            System.out.print("NO");
        }

        System.out.println("");
        System.out.print("Check if str1 '" + str1.toString() + "' starts with str3 '" + str3.toString() + "': ");
        if(startsWith(str1, str3)) {
            System.out.print("YES");
        } else {
            System.out.print("NO");
        }

        // Test: 2d - lastIndexOf()
        System.out.println("");
        System.out.println("");
        System.out.println("Testing: 2d.");
        System.out.println("Last index of 'e' in '" + str3.toString() + "' is " + lastIndexOf(str3, 'e') + " and should be 6");
        System.out.println("Last index of 'r' in '" + str3.toString() + "' is " + lastIndexOf(str3, 'r') + " and should be 4");
        System.out.println("Last index of 'l' in '" + str3.toString() + "' is " + lastIndexOf(str3, 'l') + " and should be -1");


        // convert, print, and toUpperCase
        System.out.println("");
        System.out.println("TESTING - ITERATIVE REWRITES.");
        str = StringNode.convert("fine");
        System.out.print("Here's a string: "); 
        StringNode.print(str);
        System.out.println();
        System.out.print("Here it is in upper-case letters: "); 
        StringNode.toUpperCase(str);
        StringNode.print(str);
        System.out.println();
        System.out.println();

        Scanner in = new Scanner(System.in);
        
        // read, toString, and length.
        System.out.print("Type a string: ");
        String s = in.nextLine();
        str1 = StringNode.convert(s);
        System.out.print("Your string is: "); 
        System.out.println(str1);        // implicit toString call
        System.out.println("\nIts length is " + StringNode.length(str1) +
            " characters.");

        // charAt
        n = -1;
        l = -1;
        while (n < 0) {
            System.out.print("\nWhat # character to get (>= 0)? ");
            n = in.nextInt();
            in.nextLine();
        }
        try {
            ch = StringNode.charAt(str1, n);
            System.out.println("That character is " + ch);
        } catch (IllegalArgumentException e) {
            System.out.println("The string is too short.");
        }
    
        // indexOf
        System.out.print("\nWhat character to search for? ");
        line = in.nextLine();
        n = StringNode.indexOf(str1, line.charAt(0));
        if (n == -1)
            System.out.println("Not in the string.");
        else
            System.out.println("The index of that character is: " + n);

        
        // substring
        System.out.print("\nstart index for substring? ");
        int start = in.nextInt();
        in.nextLine();
        System.out.print("\nend index for substring? ");
        int end = in.nextInt();
        in.nextLine();
        System.out.println("substring = " + StringNode.substring(str1, start, end));
        
        // deleteChar and copy
        n = -1;
        while (n < 0) {
            System.out.print("\nWhat # character to delete (>= 0)? ");
            n = in.nextInt();
            in.nextLine();
        }
        copy = StringNode.copy(str1);
        try {
            str1 = StringNode.deleteChar(str1, n);
            StringNode.print(str1);
        } catch (IllegalArgumentException e) {
            System.out.println("The string is too short.");
        }
        System.out.print("\nUnchanged copy: ");
        StringNode.print(copy);
        System.out.println();

        // insertChar
        n = -1;
        while (n < 0) {
            System.out.print("\nWhat # character to insert before (>= 0)? ");
            n = in.nextInt();
            in.nextLine();
        }
        System.out.print("What character to insert? ");
        line = in.nextLine();
        try {
            str1 = StringNode.insertChar(str1, n, line.charAt(0));
            StringNode.print(str1);
            System.out.println();
        } catch (IllegalArgumentException e) {
            System.out.println("The string is too short.");
        }
        
        System.out.print("\nType another string: ");
        s = in.nextLine();
        str2 = StringNode.convert(s);
        System.out.println("Mine: Its length is " + StringNode.length(str2) +
            " characters.");

        // compareAlpha
        System.out.print("\ncomparing " + str1 + " and " + str2 + " gives: ");
        System.out.println(StringNode.compareAlpha(str1, str2));
        
        // concat
        System.out.print("\nconcatenation = ");
        StringNode.print(StringNode.concat(str1, str2));
        System.out.println();

        // insertSorted
        System.out.print(
            "\nType a string of characters in alphabetical order: ");
        str3 = StringNode.read(System.in);
        System.out.print("What character to insert in order? ");
        line = in.nextLine();
        str3 = StringNode.insertSorted(str3, line.charAt(0));
        StringNode.print(str3);
        System.out.println();
    }
}
