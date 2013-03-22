/* 
 * MagicSquare.java
 * 
 * Author:          Computer Science E-119 staff
 * Modified by:     Graham Schmidt, schmidtg@gmail.com
 * Date modified:   Sept. 20, 2011
 */

import java.util.*;

public class MagicSquare {
    // the current contents of the cells of the puzzle values[r][c]
    // gives the value in the cell at row r, column c
    private int[][] values;

    // the order (i.e., the dimension) of the puzzle
    private int order;
    private int magicSum;
    private int[] availNums;
    private int boundary;

    /**
     * Creates a MagicSquare object for a puzzle with the specified
     * dimension/order.
     */
    public MagicSquare(int order) {
        values = new int[order][order];
        this.order = order;

        // Determine sum for each row, col
        magicSum = (order * order * order + order) / 2;

        // Determine boundary
        boundary = order - 1;

        // Create an array of available integers for the square, and initialize
        availNums = new int[order*order];
        for(int i = 0; i < availNums.length; i++) {
            availNums[i] = i + 1;
        }

        // Initialize the values for the Magic Square to 0
        for(int i = 0; i < order; i++) {
            for(int j = 0; j < order; j++) {
                values[i][j] = 0;
            }
        }
    }

    /**
     * Sets the value in the square position to an available number
     * and sets the available number to 0 (not available)
     *
     * @param num - the index of the available number
     * @param row - row position
     * @param col - column position
     */
    public void placeNum(int num, int row, int col) {
        values[row][col] = num;

        // Remove num from list of available numbers
        if(num > 0) {
            availNums[num - 1] = 0;
        }
    }

    /**
     * Sets the value in the square position to 0
     * and places the number back into the list of available numbers
     *
     * @param n - the index of the available number
     * @param row - row position
     * @param col - column position
     */
    public void removeNum(int n, int row, int col) {
        values[row][col] = 0;

        // Add num back to list of available numbers
        if(n > 0) {
            availNums[n] = n + 1;
        }
    }

    public boolean isValid(int num, int row, int col) {
        // add the num values up for both row and col

        int[] sum = findSum(row, col);

        // First or second columns must be less than or equal to the magicSum
        if ((col < order - 1) && (row < order - 1)) {
            return ((sum[0] < magicSum && sum[1] < magicSum) ? true : false);
        }

        // All values must subtract to give zero
        return ((magicSum - num - sum[0] == 0 || magicSum - num - sum[1] == 0) ? true : false);
    }

    /**
     * Helper function to sum the amount for the row or column
     *
     * @param row - particular row to sum
     * @param col - particular col to sum
     * @return sum of all the nums in the row or col
     */
    public int[] findSum(int row, int col) {

        int[] sum = new int[order];

        // Add the contents of the row or col
        for(int k = 0; k < order; k++) {
            sum[0] += values[row][k];
            sum[1] += values[k][col];
        }

        return sum;
    }

    /**
     * Return boolean if the row or col is equal to the boundary
     *
     * @param elem - int row or col
     * @return boolean true or false
     */
    private boolean isBoundary(int elem) {
        return elem == boundary;
    }

    /**
     * Return boolean if the row or col is equal to the boundary
     *
     * @param elem - int row or col
     * @return boolean true or false
     */
    private boolean isBeforeBoundary(int elem) {
        return (elem < boundary);
    }

    /**
     * Recursively backtrack to find a solution to the Magic Square problem
     *
     * @param row - current row
     * @param col - current column
     * @param direction - 0 for right, 1 for down
     * @return true if it reached the base cases (last cell)
     */
    public boolean findSolution(int row, int col, int direction) {
        // Solution found when all available numbers have been placed
        if(row == order && col == boundary) {
            return true;    //displaySolution() -> return true
        }

        // Loop through each available number
        for (int n = 0; n < availNums.length; n++) {

            // Make sure the available number is greater than 0 (0 is not available)
            if (availNums[n] > 0) {

                // Valid if sum is less than or equal to magicSum
                if(isValid(availNums[n], row, col)) {

                    // Direction is right
                    if(direction == 1) {

                        // Change direction
                        if (isBoundary(col)) {
                            // Place the number in the magicSquare
                            placeNum(availNums[n], row, col);

                            // Last column, go to next row and start from the column equal to current row
                            if(findSolution(row + 1, row, 0)) {
                                return true;
                            } else {
                                // Remove the available number from values, and add it back to availNums
                                removeNum(n, row, col);
                            }
                        }

                        // Check values from left to right
                        if (isBeforeBoundary(col)) {
                            // Place the number in the magicSquare
                            placeNum(availNums[n], row, col);

                            if(findSolution(row, col + 1, 1)) {
                                return true;
                            } else {
                                // Remove the available number from values, and add it back to availNums
                                removeNum(n, row, col);
                            }

                        }
                    }

                    // Direction is down
                    if(direction == 0) {

                        // Boundary going going down
                        if (isBoundary(row)) {
                            // Place the number in the magicSquare
                            placeNum(availNums[n], row, col);

                            // Last column, go to next row and start from first column
                            if(findSolution(col + 1, col + 1, 1)) {
                                return true;
                            } else {
                                // Remove the available number from values, and add it back to availNums
                                removeNum(n, row, col);
                            }
                        }

                        // Check values from left to right
                        if (isBeforeBoundary(col)) {
                            // Place the number in the magicSquare
                            placeNum(availNums[n], row, col);

                            // At bottom but not solved yet
                            // Only happens for order of 4
                            if ((isBoundary(row))) {
                                // Remove the available number from values, and add it back to availNums
                                removeNum(n, row, col);
                                return false;
                            }

                            if(findSolution(row + 1, col, 0)) {
                                return true;
                            } else {
                                // Remove the available number from values, and add it back to availNums
                                removeNum(n, row, col);
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * This method should call the separate recursive-backtracking method
     * that you will write, passing it the appropriate initial parameter(s).
     * It should return true if a solution is found, and false otherwise.
     */
    public boolean solve() {

        // Find a solution from the position (0,0)
        if(findSolution(0, 0, 1)) {
            return true;
        }

        return false;
    }

    /**
     * Displays the current state of the puzzle.
     * You should not change this method.
     */
    public void display() {
        for (int r = 0; r < order; r++) {
            printRowSeparator();
            for (int c = 0; c < order; c++) {
                System.out.print("|");
                if (values[r][c] == 0)
                    System.out.print("   ");
                else {
                    if (values[r][c] < 10) {
                        System.out.print(" ");
                    }
                    System.out.print(" " + values[r][c] + " ");
                }
            }
            System.out.println("|");
        }
        printRowSeparator();
    }

    // A private helper method used by display()
    // to print a line separating two rows of the puzzle.
    private void printRowSeparator() {
        for (int i = 0; i < order; i++)
            System.out.print("-----");
        System.out.println("-");
    }
    
    public static void main(String[] args) {
        /*******************************************************
          **** You should NOT change any code in this method ****
          ******************************************************/

        Scanner console = new Scanner(System.in);
        System.out.print("What order Magic Square would you like to solve? ");
        int order = console.nextInt();
        
        MagicSquare puzzle = new MagicSquare(order);
        if (puzzle.solve()) {
            System.out.println("Here's the solution:");
            puzzle.display();
        } else {
            System.out.println("No solution found.");
        }
    }
}