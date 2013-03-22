package cscie160.hw2;

/**
 * Handles the exception when the Elevator is at capacity.
 *
 * User: Graham Schmidt
 * Date: 2/24/11
 * Time: 6:16 AM
 */

public class ElevatorFullException extends Exception {

    ElevatorFullException(String msg, Floor floor) {
        System.out.println("Elevator at capacity. " + msg);
        floor.addWaitingPassengers();
    }
}
