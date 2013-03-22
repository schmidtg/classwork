package cscie160.hw5;

import java.rmi.RemoteException;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Implements the ATM interface
 */
public class ATMImpl extends UnicastRemoteObject implements ATM {

    private List<Account> account;

    /**
     * Default constructor that creates an account with a balance of 0
     * @throws RemoteException an error on bad connection
     */
    ATMImpl() throws RemoteException {
        account = new ArrayList<Account>();
        account.add(new Account());
    }

    /**
     * Create an account with a starting balance set to the incoming amount
     * @param amount - the initial balance in the account
     * @param accountNum - the account number
     * @throws RemoteException an error that occurs a bad connection to the server
     */
    ATMImpl(int accountNum, float amount) throws RemoteException {
        account = new ArrayList<Account>();
        account.add(new Account(amount, accountNum));
    }

    /**
     * Create an account with a starting balance set to the incoming amount
     * @param amount - the initial balance in the account
     * @param accountNum - the account number
     * @throws RemoteException - error
     */
    public void addAccount(int accountNum, float amount) throws RemoteException {
        account.add(new Account(amount, accountNum));
    }

    /**
     * Deposit an amount into the account
     * @param amount - the amount to deposit
     * @throws RemoteException
     */
    public void deposit(int accountNum, float amount) throws RemoteException {
        float newBalance;
        for (Iterator<Account> iter = account.iterator(); iter.hasNext();) {
            Account a = iter.next();
            if(a.getAccountNum() == accountNum) {
                newBalance = a.getBalance() + amount;
                a.setBalance(newBalance);
            }
        }
    }

    /**
     * Withdraw an amount from an account
     * @param amount - the amount to withdraw
     * @throws RemoteException
     */
    public void withdraw(int accountNum, float amount) throws RemoteException {
        float newBalance;
        for (Iterator<Account> iter = account.iterator(); iter.hasNext();) {
            Account a = iter.next();
            if(a.getAccountNum() == accountNum) {
                newBalance = a.getBalance() - amount;
                a.setBalance(newBalance);
            }
        }
    }

    /**
     * Return the balance of an account
     * @return Return a floating decimal of the account balance
     * @throws RemoteException
     */
    public Float getBalance(int accountNum) throws RemoteException {
        float balance = 0;
        for (Iterator<Account> iter = account.iterator(); iter.hasNext();) {
            Account a = iter.next();
            if(a.getAccountNum() == accountNum) {
                balance = a.getBalance();
            }
        }
        return balance;
    }
}