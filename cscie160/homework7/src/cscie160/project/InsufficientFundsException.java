package cscie160.project;

/**
 * Customer exception when the account does not have enough money to perform a withdraw
 */
public class InsufficientFundsException extends Exception {

    InsufficientFundsException(String msg) {
        super(msg);
    }
}
