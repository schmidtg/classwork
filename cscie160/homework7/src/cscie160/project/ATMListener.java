package cscie160.project;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Defines the event listener for an ATM object
 */
public interface ATMListener extends Remote {
	public void handleEvent(TransactionNotification t) throws RemoteException;
}