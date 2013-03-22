/*
 * ArrayBag.java
 *
 * Author:          Computer Science E-119 staff
 * Modified by:     Graham Schmidt, schmidtg@gmail.com
 * Date modified:   September 7, 2011
 */

import java.util.*;

/**
 * An implementation of a Bag ADT using an array.
 */
public class ArrayBag implements Bag {
    /**
     * The array used to store the items in the bag.
     */
    private Object[] items;

    /**
     * The number of items in the bag.
     */
    private int numItems;

    public static final int DEFAULT_MAX_SIZE = 50;

    /**
     * Default, no-arg constructor - creates a new, empty ArrayBag with
     * the default maximum size.
     */
    public ArrayBag() {
        items = new Object[DEFAULT_MAX_SIZE];
        numItems = 0;
    }

    /**
     * A constructor that creates a new, empty ArrayBag with the specified
     * maximum size.
     *
     * @param maxSize - int of specified size
     */
    public ArrayBag(int maxSize) {
        if (maxSize <= 0)
            throw new IllegalArgumentException("maxSize must be > 0");
        items = new Object[maxSize];
        numItems = 0;
    }

    /**
     * add - adds the specified item to the Bag.  Returns true on
     * success and false if there is no more room in the Bag.
     */
    public boolean add(Object item) {
        if (item == null)
            throw new IllegalArgumentException("item must be non-null");
        if (numItems == items.length)
            return false;              // no more room!
        else {
            items[numItems] = item;
            numItems++;
            return true;
        }
    }

    /**
     * remove - removes one occurrence of the specified item (if any)
     * from the Bag.  Returns true on success and false if the
     * specified item (i.e., an object equal to item) is not in the Bag.
     */
    public boolean remove(Object item) {
        for (int i = 0; i < numItems; i++) {
            if (items[i] != null && items[i].equals(item)) {
                // Shift the remaining items left by one.
                System.arraycopy(items, i+1, items, i, numItems-i-1);
                items[numItems-1] = null;

                numItems--;
                return true;
            }
        }

        return false;  // item not found
    }

    /**
     * contains - returns true if the specified item is in the Bag, and
     * false otherwise.
     */
    public boolean contains(Object item) {
        for (int i = 0; i < numItems; i++) {
            if (items[i] != null && items[i].equals(item))
                return true;
        }

        return false;
    }

    /**
     * containsAll - does this ArrayBag contain all of the items in
     * otherBag?  Returns false if otherBag is null or empty.
     */
    public boolean containsAll(Bag otherBag) {
        if (otherBag == null || otherBag.numItems() == 0)
            return false;

        Object[] otherItems = otherBag.toArray();
        for (int i = 0; i < otherItems.length; i++) {
            if (!contains(otherItems[i]))
                return false;
        }

        return true;
    }

    /**
     * numItems - returns the number of items in the Bag.
     */
    public int numItems() {
        return numItems;
    }

    /**
     * capacity - returns the maximum number of items the ArrayBag is
     * able to hold at the time of initialization
     */
    public int capacity() {
        return items.length;
    }

    /**
     * isFull - returns true if ArrayBag is full, and false otherwise
     */
    public boolean isFull() {
        return (this.numItems() == this.capacity());
    }

    /**
     * Increase capacity of current object
     */
    public void increaseCapacity(int increment) {
        if (increment < 0)
            throw new IllegalArgumentException("Must increase capacity by number greater than 0.");
        else if (increment == 0)
            return;
        else {
            // Determine new objects capacity
            int newCapacity = this.capacity() + increment;

            // Create a new ArrayBag with current size + increment
            ArrayBag b = new ArrayBag(newCapacity);

            // Add contents of current ArrayBag to new ArrayBag
            Object[] current = this.toArray();
            for (int i = 0; i < current.length; i++) {
                b.add(current[i]);
            }

            // Reset references for private fields of 'this'
            this.items = b.items;
            this.numItems = b.numItems;
        }
    }

    /**
     * removeItems
     *
     * Remove all occurences from the calling ArrayBag of the items found in
     * parameter other.
     */
    public boolean removeItems(Bag other) {

        // Convert 'other' to an array to iterate
        Object[] other_arr = other.toArray();

        // Create found boolean
        boolean found = false;

        // Check all items of 'other', save a found operation to variable
        for(int i = 0; i < other_arr.length; i++) {

            // While loop until other_arr[i] does not exist in this Bag
            while(this.contains(other_arr[i])) {
                this.remove(other_arr[i]);
                found = true;
            }
        }

        // Return false if go through all of 'others' and no items are removed
        return ((found)
                ? true
                : false
        );
    }

    /**
     * unionWith
     *
     * Return a non-duplicate ArrayBag as the union of the incoming and calling object
     */
    public Bag unionWith(Bag other) {

        // Check if either bag is empty
        if (this.numItems() == 0 && other.numItems() == 0) {
            return new ArrayBag();
        }

        // Determine the size of the new ArrayBag
        int newCapacity = this.capacity() + other.capacity();

        // Create new ArrayBag to hold unioned values
        ArrayBag b = new ArrayBag(newCapacity);

        // Convert the incoming object to an array and copy each of its items
        // that are not in the new ArrayBag
        Object[] other_arr = other.toArray();
        Object[] this_arr = this.toArray();

        // Determine which ArrayBag has more items
        int loop_count = ((other_arr.length > this_arr.length)
                            ? other_arr.length
                            : this_arr.length);
        for(int i = 0; i < loop_count; i++) {

            // Add 'this' item if it doesn't exist and will not IndexOutOfBounds
            if(i < this_arr.length) {
                if(!b.contains(this_arr[i])) {
                    b.add(this_arr[i]);
                }
            }

            // Add 'other's item if it doesn't exist and will not IndexOutOfBounds
            if(i < other_arr.length) {
                if(!b.contains(other_arr[i])) {
                    b.add(other_arr[i]);
                }
            }
        }

        return b;
    }

    /**
     * grab - returns a reference to a randomly chosen in the Bag.
     */
    public Object grab() {
        if (numItems == 0)
            throw new NoSuchElementException("the bag is empty");
        int whichOne = (int)(Math.random() * numItems);
        return items[whichOne];
    }

    /**
     * toArray - return an array containing the current contents of the bag
     */
    public Object[] toArray() {
        Object[] copy = new Object[numItems];
        System.arraycopy(items, 0, copy, 0, numItems);
        return copy;
    }

    /**
     * toString - converts this ArrayBag into a readable String object.
     * Overrides the Object version of this method.
     */
    public String toString() {
        String str = "{";

        for (int i = 0; i < numItems; i++)
            str = str + " " + items[i];
        str = str + " }";

        return str;
    }

    /* Test the ArrayBag implementation. */
    public static void main(String[] args) {

        // Create a Scanner object for user input.
        Scanner in = new Scanner(System.in);

        // Create an ArrayBag named bag1.
        System.out.print("Size of bag 1: ");
        int size = in.nextInt();
        Bag bag1 = new ArrayBag(size);
        in.nextLine();    // consume the rest of the line

        //****** Additions *****

        // Display capacity of new bag
        System.out.println("Bag 1 can hold up to " + bag1.capacity() + " items.");

        // Determine if ArrayBag is full
        if (bag1.isFull())
            System.out.println("Bag is full.");
        else
            System.out.println("Bag is NOT full.");

        // Read in strings, add them to bag1, and print out bag1.
        String itemStr;
        for (int i = 0; i < size; i++) {
            System.out.print("item " + i + ": ");
            itemStr = in.nextLine();
            bag1.add(itemStr);
        }
        System.out.println("bag 1 = " + bag1);
        System.out.println();

        // Increase capacity by 2
        System.out.println("Increasing Bag 1 capacity by 2.");
        bag1.increaseCapacity(2);
        System.out.println("Bag 1 can hold up to " + bag1.capacity() + " items.");

        // Select a random item and print it.
        Object item = bag1.grab();
        System.out.println("grabbed " + item);
        System.out.println();

        // Iterate over the objects in bag1, printing them one per
        // line.
        Object[] items = bag1.toArray();
        for (int i = 0; i < items.length; i++)
            System.out.println(items[i]);
        System.out.println();

        // Get an item to remove from bag1, remove it, and reprint the bag.
        System.out.print("item to remove: ");
        itemStr = in.nextLine();
        if (bag1.contains(itemStr))
            bag1.remove(itemStr);
        System.out.println("bag 1 = " + bag1);
        System.out.println();

        // Remove all items from bag1 and reprint the bag.
        Bag other1 = new ArrayBag(2);
        other1.add("ff");
        other1.add("aa");
        System.out.println("Removing all items {aa, ff}");
        bag1.removeItems(other1);
        System.out.println("bag 1 = " + bag1);
        System.out.println();

         // Create two ArrayBags and return their union (with no duplicates)
        // {2, 2, 3, 5, 7, 7, 7, 8}
        Bag b1 = new ArrayBag(8);
        b1.add("2");
        b1.add("2");
        b1.add("3");
        b1.add("5");
        b1.add("7");
        b1.add("7");
        b1.add("7");
        b1.add("8");
        System.out.println("Bag b1 is " + b1);

        //{2, 3, 4, 5, 5, 6, 7}
        Bag b2 = new ArrayBag(7);
        b2.add("2");
        b2.add("3");
        b2.add("4");
        b2.add("5");
        b2.add("5");
        b2.add("6");
        b2.add("7");
        System.out.println("Bag b2 is " + b2);

        // Be sure to test case with zero items
        Bag b3 = new ArrayBag();
        b3 = b1.unionWith(b2);
        System.out.println("Union of b1 & b2 is " + b3);
        System.out.println("b3 capacity is " + b3.capacity());
        System.out.println();

    }
}