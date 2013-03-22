package cscie160.project;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.rmi.server.UnicastRemoteObject;


/**
 * Authorizes and authenticates AccountInfo objects
 */
public class SecurityImpl extends UnicastRemoteObject implements Security {

    private List<AccountInfo> accountInfo;
    private List<AccountPermissions> accountPermissions;

    /**
     * Creates two lists. One for accountInfo objects to authenticate against PINS, and another list for
     * account permissions
     * @throws java.rmi.RemoteException - message of a problem
     */
    public SecurityImpl() throws RemoteException {
        super();
        accountInfo = new ArrayList<AccountInfo>();
        accountInfo.add(new AccountInfo(0000001, 1234));
        accountInfo.add(new AccountInfo(0000002, 2345));
        accountInfo.add(new AccountInfo(0000003, 3456));

        accountPermissions = new ArrayList<AccountPermissions>();
        accountPermissions.add(new AccountPermissions(0000001, true, true, true));
        accountPermissions.add(new AccountPermissions(0000002, true, false, true));
        accountPermissions.add(new AccountPermissions(0000003, false, true, true));
    }

    /**
     * Check if PINs match
     * @param aToAuth the AccountInfo object to authenticate
     * @return Returns true if match, otherwise false
     * @throws RemoteException - message to user it did not match
     */
    public Boolean authenticatePin(AccountInfo aToAuth) throws RemoteException {
        for (Iterator<AccountInfo> iter = accountInfo.iterator(); iter.hasNext();) {
            AccountInfo a = iter.next();
            if( a.getAccountNum() == aToAuth.getAccountNum() ) {
                if(a.getPin() == aToAuth.getPin())
                    return true; // PINS matched
            }
        }
        return false; // PINs did not match
    }

    /**
     * Check if user has permission to perform particular transaction
     * @param aToAuth the AccountInfo object to authenticate
     * @param transactionType Deposit, Withdraw or Balance
     * @return Returns true/false based on the permission granted to that account
     * @throws RemoteException
     */
    public Boolean authorizeAccount(AccountInfo aToAuth, Permissions transactionType) throws RemoteException {
        for (Iterator<AccountPermissions> iter = accountPermissions.iterator(); iter.hasNext();) {
            AccountPermissions a = iter.next();
            if( a.getAccountNum() == aToAuth.getAccountNum() ) {

                // Determine if account has appropriate transaction permission
                switch(transactionType) {
                    case DEPOSIT:
                        return a.isPermissionDeposit();
                    case WITHDRAW:
                        return a.isPermissionWithdraw();
                    case BALANCE:
                        return a.isPermissionBalance();
                    default:
                        return false;
                }
            }
        }
        return false; // not authorized by default
    }


}
