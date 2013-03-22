package cscie160.project;

/**
 * Customer exception when an account does not have the proper permissions to perform a transaction
 */
public class AuthAcctException extends Exception {

    AuthAcctException(String msg) {
        super(msg);
    }
}
