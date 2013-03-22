package cscie160.project;

/**
 * Customer exception when the ATM does not have enough cash on hand
 */
public class ATMEmptyException extends Exception {

    ATMEmptyException(String msg) {
        super(msg);
    }
}
