package cscie160.hw3;

/**
 * Handles the exception when an invalid floor is requested.
 *
 * User: Graham Schmidt
 * Date: 2/24/11
 * Time: 6:32 AM
 */

public class IllegalFloorException extends Exception {
    IllegalFloorException(String msg) {
        System.out.println("Invalid Floor " + msg);
    }
}
