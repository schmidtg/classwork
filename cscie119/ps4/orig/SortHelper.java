/*
 * SortHelper.java
 *
 * Computer Science E-119
 *
 * YOU SHOULD *NOT* NEED TO MODIFY THIS FILE.
 */

public class SortHelper {
    /*
     * swap - swap the values of the elements in positions a and b
     * in both the keys and dataItems arrays.
     */
    private static void swap(int[] keys, Object[] dataItems,
      int a, int b) {
        int temp = keys[a];
        keys[a] = keys[b];
        keys[b] = temp;
        
        Object temp2 = dataItems[a];
        dataItems[a] = dataItems[b];
        dataItems[b] = temp2;
    }  
    
    /* 
     * partition - helper method for qSort. 
     * Partitions the array/subarray keys[first:last] and also
     * makes the corresponding swaps to the values in the 
     * dataItems array.
     */
    private static int partition(int[] keys, Object[] dataItems,
      int first, int last) {
        int pivot = keys[(first + last)/2];
        int i = first - 1;  // index going left to right
        int j = last + 1;   // index going right to left

        while (true) {
            do {
                i++;
            } while (keys[i] < pivot);
            do {
                j--;
            } while (keys[j] > pivot); 

            if (i < j)
                swap(keys, dataItems, i, j);
            else
                return j;   // index of last element in the left subarray
        }                   
    }

    /* 
     * qSort - recursive method that does the work for quickSort.
     * Sorts the array/subarray keys[first:last], while also
     * making the corresponding changes to the values in the 
     * dataItems array.
     */
    private static void qSort(int[] keys, Object[] dataItems,
      int first, int last) {
        int split = partition(keys, dataItems, first, last);

        if (first < split)
            qSort(keys, dataItems, first, split);      // left subarray
        if (last > split + 1)
            qSort(keys, dataItems, split + 1, last);   // right subarray
    }

    /*
     * quicksort. Sorts the keys array, while also
     * making the corresponding changes to the values in the 
     * dataItems array. For example, if a key is moved from 
     * position i to position j in the keys array, then the
     * corresponding data item is also moved from position i
     * to position j in the dataItems array.
     */
    public static void quickSort(int[] keys, Object[] dataItems) {
        qSort(keys, dataItems, 0, keys.length - 1); 
    }
}
