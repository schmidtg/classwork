package cscie160.project;

import java.rmi.RemoteException;
import java.rmi.UnknownHostException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;


/**
 * Implements the ATM interface
 */
public class ATMImpl extends UnicastRemoteObject implements ATM {

    private float atmBalance;
    private ArrayList<ATMListener> atmListeners;
    BankFactory bankObj = null;
    Security securityObj = null;

    /**
     * Default constructor that creates an account with a balance of 0
     * @throws RemoteException an error on bad connection
     */
    public ATMImpl() throws RemoteException {
        super();
        atmListeners = new ArrayList<ATMListener>();
        this.atmBalance = 500;
        Registry registry;

        try {
            registry = LocateRegistry.getRegistry("localhost", 1099);

            try {
                System.out.print("Looking up bank and security...");
                bankObj = (BankFactory) registry.lookup("bank");
                securityObj = (Security) registry.lookup("security");
                System.out.println(" ..lookup successful.");

            } catch (Exception e){
                System.out.println("Unable to find the Registry for the ATM service");
                return;
            }
        } catch (UnknownHostException uhe) {
            uhe.printStackTrace();
        } catch (RemoteException re) {
            re.printStackTrace();
        }
    }

    private float getAtmBalance() {
        return atmBalance;
    }

    private void withdrawAtm(float withdrawAmount) {
        atmBalance -= withdrawAmount;
    }

    /**
     * Deposit an amount into the account
     * @param amount - the amount to deposit
     * @throws RemoteException
     */
    public void deposit(AccountInfo n, float amount) throws AuthAcctException, AuthPinException, RemoteException {

        // Check PIN
        if( securityObj.authenticatePin(n) ) {

            // Check if authorize to deposit
            if( securityObj.authorizeAccount(n, Permissions.DEPOSIT) ) {
                Account a = bankObj.getAccount(n.getAccountNum());
                a.deposit(amount);
                TransactionNotification t = new TransactionNotification("Deposit $" + amount + " into account "
                        + n.getAccountNum() + "");
                doCallback(t);
            } else {
                TransactionNotification t = new TransactionNotification("Not authenticated to deposit for account " + n.getAccountNum() + ".");
                doCallback(t);
                throw new AuthAcctException("Deposit permission not authenticated.");
            }
        } else {
            TransactionNotification t = new TransactionNotification("");
            doCallback(t);
            throw new AuthPinException("PIN does not match.");
        }
    }

    /**
     * Withdraw an amount from an account
     * @param amount - the amount to withdraw
     * @throws RemoteException
     */
    public void withdraw(AccountInfo n, float amount) throws InsufficientFundsException, AuthAcctException, AuthPinException, ATMEmptyException, RemoteException {

        // Check if ATM has enough cash to withdraw
        if((getAtmBalance() - amount) >= 0) {

            // Check PIN
            if( securityObj.authenticatePin(n) ) {

                // Check if authorized to withdraw
                if( securityObj.authorizeAccount(n, Permissions.WITHDRAW) ) {

                    Account a = bankObj.getAccount(n.getAccountNum());
                    a.withdraw(amount); // Withdraw cash from Account
                    withdrawAtm(amount); // Withdraw cash from ATM
                    TransactionNotification t = new TransactionNotification("Withdraw $" + amount + " from account "
                            + n.getAccountNum() + ".");
                    doCallback(t);

                } else {
                    TransactionNotification t = new TransactionNotification("Not authenticated to withdraw for account " + n.getAccountNum() + ".");
                    doCallback(t);
                    throw new AuthAcctException("Withdraw permission not authenticated.");
                }
            } else {
                TransactionNotification t = new TransactionNotification("");
                doCallback(t);
                throw new AuthPinException("PIN does not match.");
            }
        } else {
            TransactionNotification t = new TransactionNotification("Sorry, but the ATM is out of order.");
            doCallback(t);
            throw new ATMEmptyException("Not enough cash available in ATM.");
        }

    }


    /**
     * Return the balance of an account
     * @param n - AccountInfo object
     * @return Return a floating decimal of the account balance
     * @throws RemoteException
     */
    public Float getBalance(AccountInfo n) throws AuthAcctException, AuthPinException, RemoteException {

        float balance;

        // Check PIN
        if( securityObj.authenticatePin(n) ) {

            // Check if authorized to get balance
            if( securityObj.authorizeAccount(n, Permissions.BALANCE) ) {
                Account a = bankObj.getAccount(n.getAccountNum());
                balance = a.getBalance();
            } else {
                TransactionNotification t = new TransactionNotification("Not authenticated to get balance for account " + n.getAccountNum() + ".");
                doCallback(t);
                throw new AuthAcctException("Balance permission not authenticated.");
            }
        } else {
            TransactionNotification t = new TransactionNotification("Failed PIN check on account " + n.getAccountNum() + ".");
            doCallback(t);
            throw new AuthPinException("PIN does not match.");
        }

        return balance;
    }

    /**
     * Transfer money between accounts
     * @param n - AccountInfo object
     * @throws RemoteException - implements Remote
     */
    public void transfer(AccountInfo n, AccountInfo m, float amount) throws InsufficientFundsException, AuthAcctException, AuthPinException, RemoteException {

        // Check PINs on both accounts
        if( securityObj.authenticatePin(n) && securityObj.authenticatePin(m) ) {

            // Check if authorized to withdraw on first account and deposit on second
            if( securityObj.authorizeAccount(n, Permissions.WITHDRAW) && securityObj.authorizeAccount(m, Permissions.DEPOSIT) ) {

                // Withdraw cash from Account 1
                Account a = bankObj.getAccount(n.getAccountNum());
                a.withdraw(amount);

                // Deposit amount to Account 2
                Account b = bankObj.getAccount(m.getAccountNum());
                b.deposit(amount);

                TransactionNotification t = new TransactionNotification("Transfer $" + amount + " from account "
                        + n.getAccountNum()
                        + " to account "
                        + m.getAccountNum());
                doCallback(t);
            } else {
                TransactionNotification t = new TransactionNotification("Not authenticated to make transfer.");
                doCallback(t);
                throw new AuthAcctException("Withdraw and/or deposit permissions not authenticated.");
            }
        } else {
            TransactionNotification t = new TransactionNotification("");
            doCallback(t);
            throw new AuthPinException("PIN does not match.");
        }

    }

    /**
     * Iterates through the array of ATMListeners to handle an event
     * @param t The transaction notification message
     * @throws RemoteException - implements Remote
     */
    public void doCallback(TransactionNotification t) throws RemoteException {
        for (Iterator<ATMListener> iter = atmListeners.iterator(); iter.hasNext();) {
            ATMListener a = iter.next();
            try {
                a.handleEvent(t);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Registers an atmListener
     * @param atmListener reference of the atmlistener object
     * @throws RemoteException
     */
    public void addListener(ATMListener atmListener) throws RemoteException {
        if (!atmListeners.contains(atmListener)) {
            System.out.println("Adding listener " + atmListener.getClass().getName());
            atmListeners.add(atmListener);
        }
    }

}