package cscie160.hw2;

/**
 * Represents the number of floors in the building.
 *
 * User: Graham Schmidt
 * Date: 2/24/11
 * Time: 6:03 AM
 *
 */

public class Floor {
    /**
     * Declare instance variables
     */
    private final int buildingFloor;
    private int numOnBoard;
    private int numToBoard;
    private final int destFloor;

    /**
     * @param buildingFloor Sets which floor this instance of floor refers to
     */
    public Floor(int buildingFloor) {
        this.buildingFloor = buildingFloor;
        numOnBoard = 0;
        numToBoard = 0;
        destFloor = 1;   // Assume every passenger waiting at this floor wants to go to first floor
    }

    /**
     * Increases the number of passengers on board the elevator for this floor
     * @param numOnBoard Number of passengers
     */
    public void setNumOnBoard(int numOnBoard) {
        this.numOnBoard = numOnBoard + this.numOnBoard;
    }

    /**
     * Increases the waiting passengers on this floor by 1
     */
    public void addWaitingPassengers() {
        ++numToBoard;
    }

    /**
     * Removes the waiting passengers on this floor by 1
     */
    public void removeWaitingPassengers() {
        --numToBoard;
    }

    /**
     * Returns how many passengers are on board the Elevator from this floor.
     * @return numOnBoard
     */
    public int getNumOnBoard() {
        return numOnBoard;
    }

    /**
     * Returns how many passengers waiting to board.
     * @return numToBoard
     */
    public int getNumToBoard() {
        return numToBoard;
    }

    /**
     * Returns the building floor
     * @return buildingFloor
     */
    public int getBuildingFloor() {
        return buildingFloor;
    }

    /**
     * Returns the destination floor
     * @return  destFloor
     */
    public int getDestFloor() {
        return destFloor;
    }

    /**
     * Unloads any passengers on board the current floor. Boards any passengers waiting at this floor.
     * @param elevator Accepts Elevator object
     */
    public void unloadPassengers(Elevator elevator) {

        // Unloads passengers
        if(this.getNumOnBoard() > 0) {
            System.out.println(this);
            setNumOnBoard(-this.getNumOnBoard());
        }

        // Board any passengers that are waiting on this floor
        while((!elevator.isFull() && (this.getNumToBoard() > 0))) {
                try {
                    elevator.boardPassenger(this.getDestFloor());
                    removeWaitingPassengers();
                    elevator.removeRegisterRequest(getBuildingFloor());
                } catch (IllegalFloorException e) {}
                catch (ElevatorFullException e) {
                    addWaitingPassengers();
                }
        }

        // Tell Elevator to stop at this floor next time it passes to get remaining people
        elevator.registerRequest(this.getBuildingFloor());
    }

    /**
     * Prints out status of the floor
     * @return String
     */
    public String toString() {
        String passengerS1 = (this.getNumOnBoard() == 1 ? "passenger" : "passengers");
        String passengerS2 = (this.getNumToBoard() == 1 ? "passenger" : "passengers");
        return "Unloading " + getNumOnBoard() + " " + passengerS1 + ". " + getNumToBoard() + " " + passengerS2 + " waiting to board on Floor " + getBuildingFloor() + ".";
    }
}