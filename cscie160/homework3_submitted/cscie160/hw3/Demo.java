package cscie160.hw3;

import java.util.Random;

/**
 * Demonstrates the Elevator and Floor classes by loading and unloading passengers.
 *
 * User: Graham Schmidt
 * Date: 2/28/11
 * Time: 11:24 PM
 *
 */

public class Demo {
    /**
     * Manually create two passengers, one going up and one going down and add those passengers to the Elevator list
     * of boarded passengers.
     *
     * Create two passengers for each floor with random destination floors (using a Random number generator).
     * Move the elevator.
     * Try boarding a passenger for an invalid floor (15).
     * Print the status of all passengers who boarded and reached their destination floors.
     *
     * @param argv Takes an input from the command line
     */
    public static void main(String argv[]) {
        Random generator = new Random();

        Elevator myElevator = new Elevator();

        // Can create passengers with random destination floors and add them to the elevator queue
        Passenger p1 = new Passenger(3,4);
        Passenger p2 = new Passenger(5,3);

        myElevator.addPassenger(p1);
        myElevator.addPassenger(p2);

        int randomIndex1, randomIndex2;
        // Can also create a list of passengers waiting on a particular floor
        for(int i = 1; i <= myElevator.getMaxFloor(); i++) {
            // Generate random destination floors for each passenger
            randomIndex1 = generator.nextInt( myElevator.getMaxFloor() );
            randomIndex2 = generator.nextInt( myElevator.getMaxFloor() );

            if(randomIndex1 == 0) randomIndex1 = 1;
            if(randomIndex2 == 0) randomIndex2 = 1;

            myElevator.floor[i].addWaitingPassenger(randomIndex1);
            myElevator.floor[i].addWaitingPassenger(randomIndex2);
        }

        System.out.println(myElevator);

        // Move
        for(int i = 1; i <= 150; i++) {
            myElevator.move();
        }

        System.out.println(myElevator);

        int currFloor = myElevator.getCurrentFloor();
        int destFloor = 5;
        for (int i = 0; i<4; i++) {
            try {
                myElevator.boardPassenger(destFloor);
            } catch (IllegalFloorException e) {}
            catch (ElevatorFullException e) {
                myElevator.floor[currFloor].addWaitingPassenger(destFloor);
            }
        }

        try {
            // Test out of bounds
            myElevator.boardPassenger(15);
        } catch (IllegalFloorException e) {
            System.out.println("Not boarding this passenger.");
        }
        catch (ElevatorFullException e) {}

        // Move Down
        for(int i = 1; i <= 20; i++) {
            myElevator.move();

        }
        System.out.println(myElevator);
    }

}
