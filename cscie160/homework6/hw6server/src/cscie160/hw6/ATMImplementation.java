package cscie160.hw6;

/**
 * Implements the ATM interface
 */

public class ATMImplementation implements ATM {

    private Account account;

    /**
     * Default constructor that creates an account with a balance of 0
     */
    ATMImplementation() {
        account = new Account();
    }

    /**
     * Create an account with a starting balance set to the incoming amount
     * @param amount - the initial balance in the account
     */
    ATMImplementation(float amount) {
        account = new Account(amount);
    }

    /**
     * Deposit an amount into the account
     * @param amount - the amount to deposit
     * @throws ATMException
     */
    public void deposit(float amount) throws ATMException {
        float newBalance;
        newBalance = account.getBalance() + amount;

        account.setBalance(newBalance);
    }

    /**
     * Withdraw an amount from an account
     * @param amount - the amount to withdraw
     * @throws ATMException
     */
    public void withdraw(float amount) throws ATMException {
        float newBalance;
        newBalance = account.getBalance() - amount;

        account.setBalance(newBalance);
    }

    /**
     * Return the balance of an account
     * @return Return a floating decimal of the account balance
     * @throws ATMException
     */
    public Float getBalance() throws ATMException {
        return account.getBalance();
    }
}