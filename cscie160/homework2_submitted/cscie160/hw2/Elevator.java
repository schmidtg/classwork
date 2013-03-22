package cscie160.hw2;

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
     * Returns which direction Elevator is moving
     * @return Direction of elevator
     */
    private Direction getDirection() {
        return currDirection;
    }

    /**
     * Changes directions based on which floor is requested
     * @param floor - Floor in building
     */
    private void changeDirection(int floor) {
        currDirection = (floor >= currentFloor) ? Direction.UP : Direction.DOWN;
    }

    /**
     * Returns the current floor
     * @return Current building floor
     */
    private int getCurrentFloor() {
        return currentFloor;
    }

    /**
     * Returns true if Elevator is full
     * @return true if passengers on board is equal to the capacity
     */
    public boolean isFull() {
        return getPassengersOnBoard() == CAPACITY;
    }

    /**
     * Returns how many passengers are on board
     * @return Number of passengers on board
     */
    private int getPassengersOnBoard() {
        int passengersOnBoard = 0;
        for(int i = 0; i < floor.length; i++) {
            passengersOnBoard = passengersOnBoard + floor[i].getNumOnBoard();

        }
        return passengersOnBoard;
    }

    /**
     * Returns the number of passengers waiting
     * @return Number of passengers waiting for all floors
     */
    private int getPassengersWaiting() {
        int passengersBoarding = 0;
        for(int i = 0; i < floor.length; i++) {
            passengersBoarding = passengersBoarding + floor[i].getNumToBoard();

        }

        return passengersBoarding;
    }

    /**
     * Registers a stop request for a particular floor
     * @param floor which floor to place a stop
     */
    public void registerRequest(int floor) {
        registerRequest[floor] = 1;
    }

    /**
     * Removes a stop request for a particular floor
     * @param floor which floor to remove the stop
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
     * Returns whether the elevator is at the bottom
     * @return true if current floor is equal to ground floor (1)
     */
    private boolean atBottom() {
        return currentFloor == 1;
    }

    /**
     * Increases number of passengers on board by one, sets the destination floor for boarding passenger
     * @param destFloor Destination floor of boarding passenger
     * @throws cscie160.hw2.IllegalFloorException Throws an IllegalFloorException if a requested floor is not between 1 and the MAX_FLOOR
     * @throws cscie160.hw2.ElevatorFullException Throws an ElevatorFullException if the elevator is at capacity and a request to board a passenger is made
     */
    public void boardPassenger(int destFloor) throws ElevatorFullException, IllegalFloorException {

        if((destFloor > MAX_FLOOR) || (destFloor < 1)) {
            throw new IllegalFloorException(destFloor + ". Must be between 1 and " + MAX_FLOOR);
        } else {
            if(this.isFull()) {
                throw new ElevatorFullException("Queued passenger on Floor " + currentFloor + ".", floor[currentFloor]);
            } else {
                changeDirection(destFloor);
                floor[destFloor].setNumOnBoard(1);
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
        if(this.getDirection() == Direction.UP) {
            // Prevent the currentFloor from incrementing above the MAX_FLOOR
            currentFloor = (currentFloor < MAX_FLOOR) ? currentFloor + 1 : MAX_FLOOR;

            // Set stop on floor if there are people waiting (from overflow)
            if(floor[currentFloor].getNumToBoard() > 0)
                registerRequest(currentFloor);

            if(registerRequest[currentFloor] == 1) {
                stop();
            }

            if((this.atTop() == true)) {
                changeDirection(1); // Go Down
                //System.out.println("At top, going to ground floor.");
            }
        } else {
            // Prevent the currentFloor from decrementing below the ground floor (1)
            currentFloor = (currentFloor > 1) ? currentFloor - 1 : 1;

            // Set stop on floor if there are people waiting (from overflow)
            if(floor[currentFloor].getNumToBoard() > 0)
                registerRequest(currentFloor);

            if(registerRequest[currentFloor] == 1) {
                stop();
            }

            if((this.atBottom() == true)) {
                changeDirection(MAX_FLOOR); // Go Up
                //System.out.println("At bottom, going to top.");
            }
        }
    }

    /**
     * Discharges any passengers destined for a particular floor
     */
    public void stop() {
        System.out.println("Stopping on Floor " + currentFloor + ".");
        floor[currentFloor].unloadPassengers(this);
        removeRegisterRequest(currentFloor);
        System.out.println(this);
    }

    /**
     * Outputs the current state of the elevator
     * @return String describing how many passengers are on board, how many are waiting, and the current floor
     */
    public String toString() {
        String passengerS1 = (this.getPassengersOnBoard() == 1 ? "passenger" : "passengers");
        String passengerS2 = (this.getPassengersWaiting() == 1 ? "passenger" : "passengers");

        return "Currently " + this.getPassengersOnBoard() + " " + passengerS1 + " on board and "
                            + this.getPassengersWaiting() + " " + passengerS2 + " waiting to board."
                            + newline+ "Current Floor: " + this.getCurrentFloor()
                            + newline;
    }

}
