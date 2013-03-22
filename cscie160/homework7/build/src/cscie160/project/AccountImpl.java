package cscie160.project;

import java.io.Serializable;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

/**
 * Creates an account to hold a balance that can be credited and debited
 */
public class AccountImpl extends UnicastRemoteObject implements Account, Serializable {
    private float balance;
    private int accountNum;

    /**
     * No argument constructor that initializes a balance to 0
     * @throws java.rmi.RemoteException - message
     */
    AccountImpl() throws RemoteException {
        this.balance = 0;
        this.accountNum = 0;
    }

    /**
     * Initializes an account to a specific balance
     * @param amount The initial balance in the account
     * @param accountNum The initial account number
     * @throws java.rmi.RemoteException - message
     */
    AccountImpl(float amount, int accountNum) throws RemoteException{
        this.balance = amount;
        this.accountNum = accountNum;
    }

    /**
     * Deposits money in the account
     * @param amount - The amount to deposit in the account
     * @throws java.rmi.RemoteException - message
     */
    public void deposit(float amount) throws RemoteException {
        this.balance += amount;
    }

    /**
     * Withdraws money from the account
     * @param amount - The amount to withdraw in the account
     * @throws java.rmi.RemoteException - message
     */
    public void withdraw(float amount) throws InsufficientFundsException, RemoteException {
        // Check if enough money in the account
        if(balance - amount < 0)
            throw new InsufficientFundsException("Sorry, insufficient funds.");
        else
            this.balance -= amount;
    }

    /**
     * Get the balance
     * @return balance - the current amount in the account
     * @throws java.rmi.RemoteException - message
     */
    public float getBalance() throws RemoteException {
        return this.balance;
    }

    /**
     * Return the account number
     * @return accountNum
     * @throws java.rmi.RemoteException - message
     */
    public int getAccountNum() throws RemoteException {
        return this.accountNum;
    }

}