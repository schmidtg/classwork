package cscie160.hw5;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.UnknownHostException;

/**
 * Creates a Client that connects to a server. The two communicate through a proxy.
 */
   public class Client {
     public static void main(String[] args) {
        ATM atm = null;
        try {
           ATMFactory factory = (ATMFactory)Naming.lookup("//localhost/atmfactory");
           atm = factory.getATM();
        } catch (MalformedURLException mue) {
           mue.printStackTrace();
        } catch (NotBoundException nbe) {
           nbe.printStackTrace();
        } catch (UnknownHostException uhe) {
           uhe.printStackTrace();
        } catch (RemoteException re) {
           re.printStackTrace();
        }
        if (atm!=null) {
           try {
              // get initial account balance
              System.out.println("Initial Balances");
              System.out.println("Balance(0000001): "+atm.getBalance(0000001));
              System.out.println("Balance(0000002): "+atm.getBalance(0000002));
              System.out.println("Balance(0000003): "+atm.getBalance(0000003));
              System.out.println();
              // make $1000 depoist in account 0000001 and get new balance
              System.out.println("Depositting(0000001): 1000 ");
              atm.deposit(0000001, 1000);
              System.out.println("Balance(0000001): "+atm.getBalance(0000001));
              // make $100 withdrawal from account 0000002 and get new balance
              System.out.println("Withdrawing(0000002): 100 ");
              atm.withdraw(0000002, 100);
              System.out.println("Balance(0000002): "+atm.getBalance(0000002));
              // make $500 deposit in account 0000003 and get new balance
              System.out.println("Depositting(0000003): 500 ");
              atm.deposit(0000003, 500);
              System.out.println("Balance(0000003): "+atm.getBalance(0000003));
              // get final account balance
              System.out.println();
              System.out.println("Final Balances");
              System.out.println("Balance(0000001): "+atm.getBalance(0000001));
              System.out.println("Balance(0000002): "+atm.getBalance(0000002));
              System.out.println("Balance(0000003): "+atm.getBalance(0000003));
           } catch (RemoteException re) {
              System.out.println("An exception occurred while communicating with the ATM");
              re.printStackTrace();
           }
        }
     }
  }
