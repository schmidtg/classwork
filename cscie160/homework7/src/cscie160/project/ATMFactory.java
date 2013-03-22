package cscie160.project;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Defines the interface for getting an ATM remote object
 */
public interface ATMFactory extends Remote {
	public ATM getATM() throws RemoteException;
}
