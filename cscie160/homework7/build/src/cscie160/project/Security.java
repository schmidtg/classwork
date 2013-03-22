package cscie160.project;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Defines the interface for a Security object
 */
public interface Security extends Remote {
    public Boolean authenticatePin(AccountInfo aToAuth) throws RemoteException;
    public Boolean authorizeAccount(AccountInfo aToAuth, Permissions transactionType) throws RemoteException;
}