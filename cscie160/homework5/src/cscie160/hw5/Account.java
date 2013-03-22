package cscie160.hw5;

/**
 * Creates an account to hold a balance that can be credited and debited
 */
public class Account {
    private float balance;
    private int accountNum;

    /**
     * No argument constructor that initializes a balance to 0
     */
    Account() {
        this.balance = 0;
        this.accountNum = 0;
    }

    /**
     * Initializes an account to a specific balance
     * @param amount The initial balance in the account
     * @param accountNum The initial account number
     */
    Account(float amount, int accountNum) {
        this.balance = amount;
        this.accountNum = accountNum;
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
        return this.balance;
    }

    /**
     * Return the account number
     * @return accountNum
     */
    public int getAccountNum() {
        return this.accountNum;
    }
}