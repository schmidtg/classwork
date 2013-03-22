package cscie160.hw5;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Creates an instance of the atmfactory, exports it and registers it with the RMI registry.
 */
public class Server {
	
    public static void main(String[] args) {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            String name = "atmfactory";
            ATMFactory atm = new ATMFactoryImpl();
            ATMFactory stub =
                (ATMFactory) UnicastRemoteObject.exportObject(atm, 0);
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind(name, stub);
            System.out.println("ATMFactoryImpl bound");
        } catch (Exception e) {
            System.err.println("ATMFactoryImpl exception:");
            e.printStackTrace();
        }
    }
}
