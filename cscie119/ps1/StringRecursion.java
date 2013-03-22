/*
 * StringRecursion.java
 *
 * Author:          Graham Schmidt, schmidtg@gmail.com
 * Date modified:   September 12, 2011
 */

public class StringRecursion {

    public static void printLetters(String str) {

        // Base case
        if (str.length() == 1) {
            System.out.print(str.charAt(0));

        // Recursive case
        } else {
            System.out.print(str.charAt(0) + ", ");
            printLetters(str.substring(1));
        }

    }

    public static int indexOf(char ch, String str) {

        // Found, or reached end of word
        if (str == null || str.equals("")) {
            return -1;
        }

        // If its the 1st char
        if(ch == str.charAt(0)) {
            return 0;
        }

        // Get all the way to end, set pos to -1
        if(str.length() == 1) {
            return -1;
        }

        // Continue digging
        int pos = indexOf(ch, str.substring(1));

        // If pos has not been set to -1, then return the addition each time
        return (pos == -1
                ? -1
                : pos + 1);
    }

    public static String replace(String str, char oldChar, char newChar) {

        // Base case
        if (str == null || str.equals("")) {
            return "";
        }

        // Save each new character to a string
        String toRet = "" + str.charAt(0);

        // oldChar matches
        if (oldChar == str.charAt(0)) {
            String newC = "" + newChar;
            toRet = newC;
        }

        // Recursive case
        String rep = replace(str.substring(1), oldChar, newChar);

        // Return String
        return toRet + rep;
    }



    /* Test the StringRecursion implementation. */
    public static void main(String[] args) {
        System.out.println("2. a)");
        printLetters("Rabbit");
        System.out.println("");
        printLetters("I like to recurse!");
        System.out.println("");

        System.out.println("");
        System.out.println("2. b)");
        System.out.println("Index of b: " + indexOf('b', "Rabbit"));
        System.out.println("Index of P: " + indexOf('P', "Rabbit"));

        System.out.println("");
        System.out.println("2. c)");
        System.out.println("replace(\"base case\", 'e', 'y') returns " + replace("base case", 'e', 'y'));
        System.out.println("replace(\"base case\", 'r', 'y') returns " + replace("base case", 'r', 'y'));
    }
}