package cscie160.hw3;

/**
 * Represents a passenger on an elevator.
 *
 * User: Graham Schmidt
 * Date: 2/28/11
 * Time: 11:25 PM
 */

public class Passenger {

    private int currFloor;
    private int destFloor;
    private boolean arrive;
    private static final String newline = System.getProperty("line.separator");

    /**
     * Initialize a passenger with their current and destination floor
     * @param currentFloor the floor the passenger boards from
     * @param destFloor the floor the passenger is going to
     */
    Passenger(int currentFloor, int destFloor) {
        this.currFloor = currentFloor;
        this.destFloor = destFloor;
        arrive = false;
    }

    /**
     * Returns whether the passenger 'arrive' flag is true
     * @return whether the passenger has arrived at the destination floor or not
     */
    public boolean isArrive() {
        return arrive;
    }

    /**
     * Sets the passenger status for 'arrive' to true or false
     * @param arrive the status of arriving at the destination floor
     */
    public void setArrive(boolean arrive) {
        this.arrive = arrive;
    }

    /**
     * Returns the destination floor of a passenger
     * @return the destination floor
     */
    public int getDestFloor() {
        return destFloor;
    }

    /**
     * Returns the current floor of the passenger
     * @return the current floor
     */
    public int getCurrFloor() {
        return currFloor;
    }

    /**
     * Returns the status of the passenger including the boarding floor, the destination floor and whether the passenger has arrived or not
     * @return status of passenger
     */
    public String toString() {
        String statusArrive = (this.isArrive() == true ? "arrived" : "not arrived");
        return "  Passenger going from Floor " + getCurrFloor() + " to Floor " + getDestFloor() + " has " + statusArrive + "." + newline;
    }
}
