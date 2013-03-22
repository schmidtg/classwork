package cscie160.hw5;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * ATM Factory Implementation
 */
public class ATMFactoryImpl implements ATMFactory {
    /**
     * No-argument contructor
     */
    public ATMFactoryImpl() {
        super();
    }

    /**
     * Implements an ATMFactory method that defines an ATMImpl object and adds three accounts
     * @return atm object
     * @throws RemoteException
     */
    public ATM getATM() throws RemoteException {
        ATMImpl atm = new ATMImpl();
        atm.addAccount(0000001, 0.0f);
        atm.addAccount(0000002, 100.0f);
        atm.addAccount(0000003, 500.0f);

        return atm;
    }

}
