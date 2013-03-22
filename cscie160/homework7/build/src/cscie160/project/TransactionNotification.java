package cscie160.project;

import java.io.Serializable;

/**
 * Displays a message that a transaction has been made
 */
public class TransactionNotification implements Serializable {

    private String message;

    /**
     * Notifies an event listener with a transaction message
     * @param message - The message string
     */
    public TransactionNotification(String message) {
        this.message = message;
    }

    /**
     * Prints out the transaction notification
     * @return - The message string
     */
    public String toString() {
        return message;
    }
}