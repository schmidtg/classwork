package cscie160.hw3;

/**
 * Handles the exception when the Elevator is at capacity.
 *
 * User: Graham Schmidt
 * Date: 2/24/11
 * Time: 6:16 AM
 */

public class ElevatorFullException extends Exception {

    ElevatorFullException(String msg) {
        System.out.println("We're sorry, but the elevator is at capacity.");
        System.out.println(msg);
    }
}
