package cscie160.project;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Creates an instance of the Bank and Security objects, exports it and registers it with the RMI registry.
 */
public class BankServer {

    /**
     * Registers a bank and security object on the server and registers them with the RMI registry
     * @param args - command line arguments
     */
    public static void main(String[] args) {
        try {
            Registry registry;
            String securityName = "security";
            String bankName = "bank";
            Security security = new SecurityImpl();
            BankFactory bank = new BankImpl();

            try {
                registry = LocateRegistry.getRegistry("localhost", 1099);
                registry.rebind(securityName, security);
                System.out.println("Security bound");
                registry.rebind(bankName, bank);
                System.out.println("Bank bound");
            } catch (Exception e) {

                if(e instanceof RemoteException) {
                    System.out.println("Unable to bind, creating registry");
                    registry = LocateRegistry.createRegistry(1099);
                    registry.rebind(securityName, security);
                    registry.rebind(bankName, bank);
                    System.out.println("created Registry, registered " + securityName + " and " + bankName);
                }
                System.err.println("Security and Bank exception:");
                e.printStackTrace();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
