package cscie160.hw5;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Defines the interface for an ATM object
 */
public interface ATM extends Remote {
	public void deposit(int accountNum, float amount) throws RemoteException;
	public void withdraw(int accountNum, float amount) throws RemoteException;
	public Float getBalance(int accountNum) throws RemoteException;
}