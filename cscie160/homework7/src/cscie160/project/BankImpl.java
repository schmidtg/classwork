package cscie160.project;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.rmi.server.UnicastRemoteObject;


/**
 * Creates a bank object that hosts remote Account objects
 */
public class BankImpl extends UnicastRemoteObject implements BankFactory {

    private List<Account> account;

    /**
     * Creates 3 default accounts with associated account numbers and balances
     * @throws RemoteException - message to client
     */
    public BankImpl() throws RemoteException {
        super();
        account = new ArrayList<Account>();
        account.add(new AccountImpl(0.0f, 0000001));
        account.add(new AccountImpl(100.0f, 0000002));
        account.add(new AccountImpl(500.0f, 0000003));
    }

    /**
     * Obtains a reference to a remote Account
     * @param accountNum - the account number
     * @return Returns the Account info
     */
    public Account getAccount(int accountNum) throws RemoteException {
        for (Iterator<Account> iter = account.iterator(); iter.hasNext();) {
            Account a = iter.next();
            if(a.getAccountNum() == accountNum) {
                return a;
            }
        }

        return new AccountImpl(); // Blank account
    }
}
