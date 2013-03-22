package cscie160.project;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface that defines a remote Bank object
 */
public interface Account extends Remote {
    public void deposit(float amount) throws RemoteException;
    public void withdraw(float amount) throws InsufficientFundsException, RemoteException;
    public float getBalance() throws RemoteException;
    public int getAccountNum() throws RemoteException;
}