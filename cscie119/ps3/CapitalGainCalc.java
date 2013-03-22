/*
 * CapitalGainCalc.java
 *
 * Modified by:     Graham Schmidt, schmidtg@gmail.com
 * Date modified:   Oct. 24, 2011
 */

import java.util.*;

/**
 * A class for calculating capital gains on stock sales.
 */
public class CapitalGainCalc {
    /* 
     * An inner class for storing information about a stock purchase.
     */
    private class Purchase {
        private int numShares;
        private int price;

        Purchase(int numShares, int price) {
            this.numShares = numShares;
            this.price = price;
        }
    }

    /* Put your instance variables below */
    private Queue<Purchase> purchases = null;
    private int totalNumShares;

    public CapitalGainCalc() {
        /* Put your implementation of the constructor below. */

        // For the purposes of this assignment, 20 items of ArrayQueue is suffice
        purchases = new ArrayQueue<Purchase>(20);
        totalNumShares = 0;
    }

    public void processPurchase(int numShares, int price) {
        /* Put your implementation of this method below. */

        Purchase p = new Purchase(numShares, price);
        purchases.insert(p);

        // Bookkeep
        updateNumShares(numShares);
    }

    public int processSale(int numSharesToSell, int price) {
        /* Replace the line below with your implementation of this method. */

        int sharesSold = 0;
        int sharesToSell = numSharesToSell;
        int processGain = 0;

        // Check if there are enough total number of shares still available
        if (sharesToSell > totalNumShares) {
            throw new IllegalArgumentException("** You don't have " + sharesToSell + " shares to sell. **");
        }

        // Extract exactly numSharesToSell at the specified price
        // Calculate gains on each purchase, up until no more shares available
        while (sharesSold < numSharesToSell) {

            // Process each purchase
            Purchase p = purchases.peek();

            // Process the sale
            if (p.numShares <= sharesToSell) {
                Purchase pS = purchases.remove();

                // Bookkeep
                updateNumShares(-pS.numShares);
                sharesSold = sharesSold + pS.numShares;

                // Calculate gain/loss
                int gain = pS.numShares * (price - pS.price);
                processGain = processGain + gain;
                sharesToSell = sharesToSell - pS.numShares;

            // Get remaining shares, update next queue purchase item
            } else {

                // Calculate last shares to withdraw
                int lastShares = numSharesToSell - sharesSold;

                // Make sale on final remaining shares
                p.numShares = p.numShares - lastShares;

                int gain = lastShares * (price - p.price);
                processGain = processGain + gain;
                sharesToSell = sharesToSell - lastShares;

                updateNumShares(-lastShares);
                sharesSold = sharesSold + lastShares;
            }
        }

        return processGain;
    }

    /**
     * Update the total number of shares
     * @param shares - # of shares for transaction
     */
    private void updateNumShares(int shares) {
        totalNumShares = totalNumShares + shares;
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        CapitalGainCalc calc = new CapitalGainCalc();
        int totalGain = 0;
        int gain = 0;

        while (true) {
            // Get the choice of operation.
            System.out.print("\n(1) purchase, (2) sale, or (3) done: ");
            int choice = scan.nextInt();
            if (choice == 3)
                break;
            if (choice != 1 && choice != 2) {
                System.err.println("Please enter 1, 2, or 3.");
                continue;
            } 

            // Get the number of shares and the price.
            System.out.print("number of shares: ");
            int numShares = scan.nextInt();
            System.out.print("price: ");
            int price = scan.nextInt();
            if (numShares <= 0 || price <= 0) {
                System.err.println("Please enter positive numbers.");
                continue;
            }

            if (choice == 1)
                calc.processPurchase(numShares, price);
            else {
                try {
                    gain = calc.processSale(numShares, price);
                    System.out.println("Capital gain on sale: $" + gain);
                    totalGain += gain;
                } catch (IllegalArgumentException e) {
                    System.err.println(e.getMessage());
                }
            }
        }               

        System.out.println("Total capital gain: $" + totalGain);
    }
}
