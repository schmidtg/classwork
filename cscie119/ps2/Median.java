/*
 * Median.java
 *
 * Author:          Graham Schmidt, schmidtg@gmail.com
 * Date modified:   October 5, 2011
 */

public class Median {
    /* partition - helper method for your recursive median-finding method */
    private static int partition(int[] arr, int first, int last) {
        int pivot = arr[(first + last)/2];
        int i = first - 1;  // index going left to right
        int j = last + 1;   // index going right to left
        
        while (true) {
            do {
                i++;
            } while (arr[i] < pivot);
            do {
                j--;
            } while (arr[j] > pivot); 
            
            if (i < j)
                Sort.swap(arr, i, j);
            else
                return j;   // index of last element in the left subarray
        }                   
    }

    /* 
     * findMedian - "wrapper" method for your recursive median-finding method.
     * It just makes the initial call to that method, passing it
     * whatever initial parameters make sense.
     */
    public static void findMedian(int[] arr) {

        // call medianSort
        int[] medianIndex = getMedianIndex(arr);
        medianSort(arr, 0, arr.length - 1, medianIndex);
    }

    /* medianSort - recursive method that determines the median */
    private static void medianSort(int[] arr, int first, int last, int[] median) {

        int split = partition(arr, first, last);

        // Initialize two skip flags
        int skipLeft = 0;
        int skipRight = 0;

        // If split is less than the median index, don't bother looking at left subarray
        if (split < median[0]) {
            skipLeft = 1;
        }

        if (first < split && skipLeft != 1) {
            medianSort(arr, first, split, median);      // left subarray
        }

        // If median index is greater than or equal to (first) and less than (last),
        // then skip sorting the right subarray
        if (
            (split + 1) > median[1]
            && last > median[1]
        ) {
            skipRight = 1;
        }

        if ((last > split + 1) && skipRight != 1) {
            medianSort(arr, split + 1, last, median);   // right subarray
        }
    }

    /**
     * Helper function to determine the medianIndex
     * @param arr - array of integers
     * @return array with two medians (in case of even length array)
     */
    private static int[] getMedianIndex(int[] arr) {
        int medianIndex = (arr.length - 1)/2;

        int[] medians = new int[2];

        // If remainder of array length is 1,
        // then it's even, otherwise it's odd
        if ((arr.length - 1) % 2 == 1) {
            medians[0] = medianIndex;
            medians[1] = medianIndex + 1;
        } else {
            medians[0] = medianIndex;
            medians[1] = medianIndex;
        }

        return medians;
    }

    /**
     * Prints the specified array in the following form:
     * { arr[0] arr[1] ... }
     */
    public static void printArray(int[] arr) {
        // Don't print it if it's more than 10 elements.
        if (arr.length > 10)
            return;

        System.out.print("{ ");

        for (int i = 0; i < arr.length; i++)
            System.out.print(arr[i] + " ");

        System.out.println("}");
    }
    

    public static void main(String[] args) {
        // the median of this array is 15
        int[] oddLength = {4, 18, 12, 34, 7, 42, 15, 22, 5};
        
        // the median of this array is the average of 15 and 18 = 16.5
        int[] evenLength = {4, 18, 12, 34, 7, 42, 15, 22, 5, 27};

        ////////////////
        // ODD
        ////////////////

        // Find Median
        findMedian(oddLength);

        int[] indexes1 = getMedianIndex(oddLength);

        System.out.println("The median is: " + oddLength[indexes1[0]]);
        System.out.println("After: Find median in odd array: ");
        printArray(oddLength);

        ////////////////
        // EVEN
        ////////////////
        System.out.println("");
        System.out.println("");

        // Find Median
        findMedian(evenLength);

        int[] indexes2 = getMedianIndex(evenLength);

        System.out.println("The medians are: " + evenLength[indexes2[0]] + " and " +  evenLength[indexes2[1]]);
        System.out.println("After: Find median in even array: ");
        printArray(evenLength);

    }
}

