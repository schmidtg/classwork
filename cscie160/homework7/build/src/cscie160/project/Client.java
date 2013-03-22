package cscie160.project;

import java.rmi.RemoteException;
import java.rmi.UnknownHostException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Creates a Client that connects to a server. The two communicate through a proxy.
 */
   public class Client extends UnicastRemoteObject implements ATMListener {

    private static final String newline = System.getProperty("line.separator");

    /**
     * Default constructor
     * @throws RemoteException - a message that the remote object failed
     */
    public Client() throws RemoteException {
        super();
    }

    /**
     * Event listener
     * @param t - message object describing the event that happened
     * @throws RemoteException
     */
    public void handleEvent(TransactionNotification t) throws RemoteException {
        System.out.println(newline + t);
    }

    /**
     * Private function to return the data from an AccountInfo object
     * @param accountNum - the account number
     * @param pin - the account pin
     * @return - Returns an AccountInfo object
     */
    private static AccountInfo getAccountInfo(int accountNum, int pin) {
        return new AccountInfo(accountNum, pin);
    }

    /**
     * Main program that drives the Client to interact with a remote objects via a Server
     * @param args - Any command line arguments
     */
    public static void main(String[] args) {
        ATM atm = null;
        Registry registry;

        try {
            registry = LocateRegistry.getRegistry("localhost", 1099);

            try {
                System.out.print("Looking up atmfactory...");
                ATMFactory factory = (ATMFactory) registry.lookup("atmfactory");
                atm = factory.getATM();
                System.out.println(" ..lookup successful.");

                Client atmListener = new Client();
                atm.addListener(atmListener);

            } catch (Exception e){
                System.out.println("Unable to find the Registry for the ATM service");
                return;
            }
        } catch (UnknownHostException uhe) {
            uhe.printStackTrace();
        } catch (RemoteException re) {
            re.printStackTrace();
        }

        if (atm!=null) {
            testATM(atm);
        }
    }

    /**
     * Test method to ensure the program works as desired
     * @param atm Reference to an atm remote object
     */
    public static void testATM(ATM atm) {
       if (atm!=null) {
          printBalances(atm);
          performTestOne(atm);
          performTestTwo(atm);
          performTestThree(atm);
          performTestFour(atm);
          performTestFive(atm);
          performTestSix(atm);
          performTestSeven(atm);
          performTestEight(atm);
          performTestNine(atm);
          printBalances(atm);
       }
    }
    public static void printBalances(ATM atm) {
       try {
          System.out.println("Balance(0000001): "+atm.getBalance(getAccountInfo(0000001, 1234)));
          System.out.println("Balance(0000002): "+atm.getBalance(getAccountInfo(0000002, 2345)));
          System.out.println("Balance(0000003): "+atm.getBalance(getAccountInfo(0000003, 3456)));
       } catch (Exception e) {
          e.printStackTrace();
       }
    }
    public static void performTestOne(ATM atm) {
       try {
          atm.getBalance(getAccountInfo(0000001, 5555));
       } catch (Exception e) {
          System.out.println("Failed as expected: "+e);
       }
    }
    public static void performTestTwo(ATM atm) {
       try {
          atm.withdraw(getAccountInfo(0000002, 2345), 500);
       } catch (Exception e) {
          System.out.println("Failed as expected: "+e);
       }
    }
    public static void performTestThree(ATM atm) {
       try {
          atm.withdraw(getAccountInfo(0000001, 1234), 50);
       } catch (Exception e) {
          System.out.println("Failed as expected: "+e);
       }
    }
    public static void performTestFour(ATM atm) {
       try {
          atm.deposit(getAccountInfo(0000001, 1234), 500);
       } catch (Exception e) {
          System.out.println("Unexpected error: "+e);
       }
    }
    public static void performTestFive(ATM atm) {
       try {
          atm.deposit(getAccountInfo(0000002, 2345), 100);
       } catch (Exception e) {
          System.out.println("Unexpected error: "+e);
       }
    }
    public static void performTestSix(ATM atm) {
       try {
          atm.withdraw(getAccountInfo(0000001, 1234), 100);
       } catch (Exception e) {
          System.out.println("Unexpected error: "+e);
       }
    }
    public static void performTestSeven(ATM atm) {
       try {
          atm.withdraw(getAccountInfo(0000003, 3456), 300);
       } catch (Exception e) {
          System.out.println("Unexpected error: "+e);
       }
    }
    public static void performTestEight(ATM atm) {
       try {
          atm.withdraw(getAccountInfo(0000001, 1234), 200);
       } catch (Exception e) {
          System.out.println("Failed as expected: "+e);
       }
    }
    public static void performTestNine(ATM atm) {
       try {
          atm.transfer(getAccountInfo(0000001, 1234),getAccountInfo(0000002, 2345), 100);
       } catch (Exception e) {
          System.out.println("Unexpected error: "+e);
       }
    }

  }
