package cscie160.project;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


/**
 * ATM Factory Implementation
 */
public class ATMFactoryImpl extends UnicastRemoteObject implements ATMFactory {
    /**
     * No-argument constructor
     * @throws java.rmi.RemoteException - message
     */
    public ATMFactoryImpl() throws RemoteException {
        super();
    }

    /**
     * Implements an ATMFactory method that defines an ATMImpl object and adds three accounts
     * @return atm object
     * @throws RemoteException - message
     */
    public ATM getATM() throws RemoteException {
        ATMImpl atm = new ATMImpl();
        return atm;
    }

}
