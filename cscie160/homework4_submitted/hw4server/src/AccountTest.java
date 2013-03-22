package cscie160.hw4;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import junit.framework.TestCase;

/**
 * Test the balance in the account
 */
public class AccountTest extends TestCase {
    public AccountTest(String name) {
    	super(name);
    }
    
    @Test
    public void testBalance() throws ATMException {
        Account acct = new Account();
        acct.setBalance(50);
        assertEquals("Bal = 50:",acct.getBalance(),50.0f,0.0);
    }
    
    public static void main(String argv[]) {
    	junit.textui.TestRunner.run(AccountTest.class);
    }
}