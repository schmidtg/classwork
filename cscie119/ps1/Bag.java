/* 
 * Bag.java
 * 
 * Author:          Computer Science S-111 staff
 * Modified by:     Graham Schmidt, schmidtg@gmail.com
 * Date modified:   Sept. 21, 2011
 */

/**
 * An interface for a Bag ADT.
 */
public interface Bag {
    /** 
     * adds the specified item to the Bag.  Returns true on success
     * and false if there is no more room in the Bag.
     */
    boolean add(Object item);

    /** 
     * removes one occurrence of the specified item (if any) from the
     * Bag.  Returns true on success and false if the specified item
     * (i.e., an object equal to item) is not in the Bag.
     */
    boolean remove(Object item);

    /**
     * returns true if the specified item is in the Bag, and false
     * otherwise.
     */
    boolean contains(Object item);

    /**
     * returns true if the calling object contain all of the items in
     * otherBag, and false otherwise.  Also returns false if otherBag 
     * is null or empty. 
     */
    boolean containsAll(Bag otherBag);

    /**
     * returns the number of items in the Bag.
     */
    int numItems();

    /**
     * returns the maximum number of items the Bag can hold
     */
    int capacity();

    /**
     * returns if the Bag is full
     */
    boolean isFull();

    /**
     * increaseCapacity - increases the maximum capacity of the bag by
     * the specified amount
     */
    void increaseCapacity(int increment);

    /**
     * Remove items from the calling ArrayBag all occurrences of
     * the items found in the parameter other.
     */
    boolean removeItems(Bag other);

    /**
     * Creates and returns an ArrayBag containing one occurrence of
     * any item that is found in either the calling object or the
     * parameter other
     */
    Bag unionWith(Bag other);

    /**
     * grab - returns a reference to a randomly chosen in the Bag.
     */
    Object grab();

    /**
     * toArray - return an array containing the current contents of the bag
     */
    Object[] toArray();
} 
