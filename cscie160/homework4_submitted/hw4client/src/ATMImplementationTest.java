package cscie160.hw4;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import junit.framework.TestCase;

/**
 * Test the deposit and withdraw methods of the ATMImplementation class
 */
public class ATMImplementationTest extends TestCase {
    public ATMImplementationTest(String name) {
    	super(name);
    }

    @Test
    public void testDeposit() throws ATMException {
        ATM atm = new ATMImplementation();
        atm.deposit(100);
        assertEquals("Bal = 100:",atm.getBalance(), 100.0f,0.0);
        atm.withdraw(100);
        assertEquals("Bal = 0:",atm.getBalance(),0,0.0);
    }
    @Test
    public void testWithdraw() throws ATMException {
        ATM atm = new ATMImplementation();
        atm.deposit(100);
        assertEquals("Bal = 100:",atm.getBalance(), 100.0f,0.0);
        atm.withdraw(50);
        assertEquals("Bal = 50:",atm.getBalance(),50.0f,0.0);
    }
    
    public static void main(String argv[]) {
    	junit.textui.TestRunner.run(ATMImplementationTest.class);
    }
}