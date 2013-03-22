package cscie160.project;

/**
 * Customer exception when a PIN does not match
 */
public class AuthPinException extends Exception {

    AuthPinException(String msg) {
        super(msg);
    }
}
