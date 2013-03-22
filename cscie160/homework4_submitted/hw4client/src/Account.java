package cscie160.hw4;

/**
 * Creates an account to hold a balance that can be credited and debited
 */
public class Account {
    private float balance;

    /**
     * No argument constructor that initializes a balance to 0
     */
    Account() {
        this.balance = 0;
    }

    /**
     * Initializes an account to a specific balance
     * @param amount The initial balance in the account
     */
    Account(float amount) {
        this.balance = amount;
    }

    /**
     * Set the balance
     * @param amount - The amount to deposit in the account
     */
    public void setBalance(float amount) {
        this.balance = amount;

    }

    /**
     * Get the balance
     * @return balance - the current amount in the account
     */
    public float getBalance() {
        return balance;
    }
}