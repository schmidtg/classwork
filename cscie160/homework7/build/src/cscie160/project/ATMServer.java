package cscie160.project;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.RemoteException;

/**
 * Creates an instance of the atmfactory, exports it and registers it with the RMI registry.
 */
public class ATMServer {

    /**
     * Registers an atm object on a server and registers it with the RMI registry
     * @param args - command line arguments
     */
    public static void main(String[] args) {
        try {
            Registry registry;
            String name = "atmfactory";
            ATMFactory atm = new ATMFactoryImpl();

            try {
                registry = LocateRegistry.getRegistry("localhost", 1099);
                registry.rebind(name, atm);
                System.out.println("ATMFactoryImpl bound");
            } catch (Exception e) {
                if(e instanceof RemoteException) {
                    System.out.println("Unable to bind, creating registry");
                    registry = LocateRegistry.createRegistry(1099);
                    registry.rebind(name, atm);
                    System.out.println("created Registry, registered " + name);
                }
                System.err.println("ATMFactoryImpl exception:");
                e.printStackTrace();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
