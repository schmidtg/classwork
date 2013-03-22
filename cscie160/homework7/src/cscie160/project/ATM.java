package cscie160.project;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Defines the interface for an ATM object
 */
public interface ATM extends Remote {
	public void deposit(AccountInfo a, float amount) throws AuthAcctException, AuthPinException, RemoteException;
	public void withdraw(AccountInfo a, float amount) throws InsufficientFundsException, AuthAcctException, AuthPinException, ATMEmptyException, RemoteException;
	public Float getBalance(AccountInfo a) throws AuthAcctException, AuthPinException, RemoteException;
    public void transfer(AccountInfo a, AccountInfo b, float amount) throws InsufficientFundsException, AuthAcctException, AuthPinException, RemoteException;
    public void addListener(ATMListener atmListener) throws RemoteException;
}