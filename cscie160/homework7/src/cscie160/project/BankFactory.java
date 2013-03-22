package cscie160.project;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface that defines a remote Bank object
 */
public interface BankFactory extends Remote {
    public Account getAccount(int accountNum) throws RemoteException;
}