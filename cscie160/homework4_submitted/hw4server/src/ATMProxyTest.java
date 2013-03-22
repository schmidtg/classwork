package cscie160.hw4;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import java.net.Socket;
import junit.framework.TestCase;

/**
 * Test the deposit and of the ATMProxy
 */
public class ATMProxyTest extends TestCase {
    public ATMProxyTest(String name) {
    	super(name);
    }

    @Test
    public void testDeposit() throws ATMException {
        int port = 7777;

        try {
            ATM atm = new ATMProxy("localhost", port);
            atm.deposit(0);
            assertEquals("Bal = 100:",atm.getBalance(), 0.0f,0.0);
            // make $1000 deposit and get new balance
            atm.deposit(1000);
            assertEquals("Bal = 1000:",atm.getBalance(), 1000.0f,0.0);
            atm.withdraw(500);
            assertEquals("Bal = 500:",atm.getBalance(), 500.0f,0.0);
        } catch (Exception ae) {
            System.out.println("An exception occurred while communicating with the ATM");
            ae.printStackTrace();
		}
    }
    
    public static void main(String argv[]) {
    	junit.textui.TestRunner.run(ATMProxyTest.class);
    }

}