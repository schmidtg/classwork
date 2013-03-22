package cscie160.project;

import java.io.Serializable;

/**
 * The AccountInfo object is a data class that includes an account number and a PIN.
 * Instances are passed by value between processes.
 */
public class AccountInfo implements Serializable {

    private int AccountNum;
    private int pin;

    /**
     * Initializes the data to lookup an account
     * @param AccountNum - the account number
     * @param pin - the account pin
     */
    public AccountInfo(int AccountNum, int pin) {
        this.AccountNum = AccountNum;
        this.pin = pin;
    }

    /**
     * Gets the account number
     * @return - the account number
     */
    public int getAccountNum() {
        return this.AccountNum;
    }

    /**
     * Gets the PIN number
     * @return - the pin number
     */
    public int getPin() {
        return this.pin;
    }
}