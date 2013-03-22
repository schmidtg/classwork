package cscie160.hw3;

import java.util.List;
import java.util.ArrayList;

/**
 * Creates an Elevator object that carries passengers up and down the floors to a building.
 *
 * User: Graham Schmidt
 * Date: 2/15/11
 * Time: 5:50 PM
 *
 */

public class Elevator {
    /** Class constants
     */
    private static final int CAPACITY = 7;
    private static final int MAX_FLOOR = 10;
    private static final String newline = System.getProperty("line.separator");

    /**
     * ENUM class for direction
     */
    private enum Direction {UP, DOWN}

    /**
     * Instance Variables
     */
    private int currentFloor;
    private Direction currDirection;

    public Floor[] floor = new Floor[MAX_FLOOR + 1];
    public int[] registerRequest = new int[floor.length];

    private List<Passenger> passengersUp = new ArrayList<Passenger>();
    private List<Passenger> passengersDown = new ArrayList<Passenger>();

    /**
     * General Constructor - Sets the current floor to 1, direction as UP, and initializes a floor object for each floor in the building
     */
    public Elevator() {
        currentFloor = 1;
        currDirection = Direction.UP;

        // Initialize a floor object for each floor in the building
        for(int i = 0; i < floor.length; i++) {
            floor[i] = new Floor(i);
        }
    }

    /**
     * Returns which direction Elevator is moving.
     * @return The direction of the elevator.
     */
    private Direction getDirection() {
        return currDirection;
    }

    /**
     * Changes directions based on which floor is requested.
     * @param floor A floor in the building.
     */
    private void changeDirection(int floor) {
        currDirection = (floor >= currentFloor) ? Direction.UP : Direction.DOWN;
    }

    /**
     * Determines if elevator is moving upward.
     * @return Returns boolean if elevator is moving up.
     */
    public boolean goingUp() {
        return this.getDirection() == Direction.UP;
    }

    /**
     * Returns the current floor.
     * @return Current building floor.
     */
    public int getCurrentFloor() {
        return currentFloor;
    }

    /**
     * Returns true if the Elevator is full.
     * @return True if the passengers on board is equal to the capacity.
     */
    public boolean isFull() {
        return getPassengersOnBoard() == CAPACITY;
    }

    /**
     * Get the number of floors in the building.
     * @return How many floors in the building.
     */
    public int getMaxFloor() {
        return MAX_FLOOR;
    }

    /**
     * Returns how many passengers are on board the Elevator.
     * @return Number of passengers on board the Elevator.
     */
    private int getPassengersOnBoard() {

        return passengersUp.size() + passengersDown.size();
    }

    /**
     * Returns the number of passengers waiting on all Floors
     * @return Number of passengers waiting for all Floors
     */
    private int getPassengersQueued() {
        int passengersToBoard = 0;
        for(int i = 0; i < floor.length; i++) {
            passengersToBoard = passengersToBoard + floor[i].getPassengersQueued();
        }

        return passengersToBoard;
    }

    /**
     * Adds a passenger to the elevator up or down queues based on the destination floor of the passenger.
     * @param p Passenger that has had it's destination and current floors set
     */
    public void addPassenger(Passenger p) {
        if(p.getDestFloor() > p.getCurrFloor())
            this.passengersUp.add(p);
        else
            this.passengersDown.add(p);
    }

    /**
     * Gets the passengers on the Elevator going up.
     * @return List of Passengers going up
     */
    public List<Passenger> getPassengersUp() {
        return passengersUp;
    }

    /**
     * Get the passengers on the Elevator going down.
     * @return List of Passengers going down
     */
    public List<Passenger> getPassengersDown() {
        return passengersDown;
    }

    /**
     * Registers a stop request for a particular floor
     * @param floor The floor to request a stop.
     */
    public void registerRequest(int floor) {
        registerRequest[floor] = 1;
    }

    /**
     * Removes a stop request for a particular floor
     * @param floor The floor to remove a stop request.
     */
    public void removeRegisterRequest(int floor) {
        registerRequest[floor] = 0;
    }

    /**
     * Returns whether the elevator is at the top
     * @return true if current floor is equal to the top building floor
     */
    private boolean atTop() {
        return currentFloor == MAX_FLOOR;
    }

    /**
     * Returns whether the elevator is at the ground floor.
     * @return true if current floor is equal to ground floor (1)
     */
    private boolean atBottom() {
        return currentFloor == 1;
    }

    /**
     * Increases the number of passengers on board by one, and sets the destination floor of the boarding passenger.
     * @param destFloor Destination floor of boarding passenger.
     * @throws IllegalFloorException Throws an IllegalFloorException if a requested floor is not between 1 and the MAX_FLOOR
     * @throws ElevatorFullException Throws an ElevatorFullException if the elevator is at capacity and a request to board a passenger is made
     */
    public void boardPassenger(int destFloor) throws ElevatorFullException, IllegalFloorException {
        if((destFloor > MAX_FLOOR) || (destFloor < 1)) {
            throw new IllegalFloorException(destFloor + ". Must be between 1 and " + MAX_FLOOR + ".");
        } else {
            if(this.isFull()) {
                throw new ElevatorFullException(" > Queueing passenger on Floor " + currentFloor + " going to Floor " + destFloor + ".");
            } else {
                changeDirection(destFloor);
                Passenger p = new Passenger(currentFloor,destFloor);
                if(this.getDirection() == Direction.UP)
                    passengersUp.add(p);
                else
                    passengersDown.add(p);

                registerRequest(destFloor);
                System.out.println("Boarding passenger for Floor " + destFloor + ".");
            }
        }
    }

    /**
     * Keeps track of the current floor and stops if there are any passengers destined for the current floor.
     * If there are passengers waiting on a floor it sets a stop request.
     * If the Elevator is at the top of the building, it changes direction to go down.
     * If the Elevator is at the first floor of the building, it changes direction to go up.
     */
    public void move() {
        if(this.goingUp()) {
            // Prevent the currentFloor from incrementing above the MAX_FLOOR
            currentFloor = (currentFloor < MAX_FLOOR) ? currentFloor + 1 : MAX_FLOOR;

            // Stop if there are any passengers waiting on the floor to board
            if(floor[currentFloor].getPassengersQueued() > 0)
                stop();

            // Stop if the passengers on board have this floor as a destination
            if(registerRequest[currentFloor] == 1) {
                stop();
            }

            if((this.atTop() == true)) {
                changeDirection(1); // Go Down
                System.out.println("At top, going to ground floor.");
            }
        } else {
            // Prevent the currentFloor from decrementing below the ground floor (1)
            currentFloor = (currentFloor > 1) ? currentFloor - 1 : 1;

            // Stop if there are any passengers waiting on the floor to board
            if(floor[currentFloor].getPassengersQueued() > 0)
                   stop();

            // Stop if the passengers on board have this floor as a destination
            if(registerRequest[currentFloor] == 1) {
                stop();
            }

            if((this.atBottom() == true)) {
                changeDirection(MAX_FLOOR); // Go Up
                System.out.println("At bottom, going to top.");
            }
        }
    }

    /**
     * Discharges any passengers destined for a particular floor.
     */
    public void stop() {
        System.out.println(newline + "Stopping on Floor " + currentFloor + ".");
        floor[currentFloor].unloadPassengers(this);
        System.out.println(this);
    }

    /**
     * Outputs the current state of the elevator.
     * @return String describing how many passengers are on board going up and going down, and the statuses of all passengers (whether they have arrived or not).
     */
    public String toString() {
        String passengersUpStatus = "";
        if(this.passengersUp.size() > 0) {
            passengersUpStatus = newline + "Passengers On Board Going Up: " + newline;
            for(Passenger p : passengersUp) {
                passengersUpStatus += p.toString();
            }
        }

        String passengersDownStatus = "";
        if(this.passengersDown.size() > 0) {
            passengersDownStatus = newline + "Passengers On Board Going Down: " + newline;
            for(Passenger p : passengersDown) {
                passengersDownStatus += p.toString();
            }
        }

        String passengerFloorStatuses = "";
        if(this.getPassengersQueued() > 0) {
            passengerFloorStatuses = newline + "All Passenger Statuses: " + newline;
            for(int i = 0; i < floor.length; i++) {
                passengerFloorStatuses += floor[i].toString();
            }
        }

        return "Current Floor: " + this.getCurrentFloor() + newline
                + passengersUpStatus
                + passengersDownStatus
                + passengerFloorStatuses;
    }

}
