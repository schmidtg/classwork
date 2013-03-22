package cscie160.hw2;

/**
 * Demonstrates the Elevator and Floor classes by loading and unloading passengers.
 *
 * User: Graham Schmidt
 * Date: 2/24/11
 * Time: 6:35 AM
 *
 */
public class Demo {

    /**
     * Main output. Board passengers destined for the 2nd and 3rd floors. Move elevator to top floor and back down.
     * @param argv Takes an input from the command line
     */
    public static void main(String argv[]) {
        Elevator myElevator = new Elevator();

        System.out.println(myElevator);
        try {
            myElevator.boardPassenger(7);
            myElevator.boardPassenger(7);
            myElevator.boardPassenger(7);
            myElevator.boardPassenger(7);
            myElevator.boardPassenger(7);
            myElevator.boardPassenger(7);
            myElevator.boardPassenger(7);
            myElevator.boardPassenger(7);
            myElevator.floor[3].addWaitingPassengers();
        } catch (IllegalFloorException e) {}
        catch (ElevatorFullException e) {}

        System.out.println(myElevator);

        for (int c = 0; c<17;c++)
        {
            myElevator.floor[8].addWaitingPassengers();
        }

        // Move
        for(int i = 1; i <= 20; i++) {
            myElevator.move();
        }
        System.out.println(myElevator);

        for (int i = 0; i<9; i++) {
            try {
                myElevator.boardPassenger(5);
            } catch (IllegalFloorException e) {}
            catch (ElevatorFullException e) {}
        }

        try {
            // Test out of bounds
            myElevator.boardPassenger(15);
        } catch (IllegalFloorException e) {}
        catch (ElevatorFullException e) {}

        // Move Down
        for(int i = 1; i <= 50; i++) {
            myElevator.move();

        }
        System.out.println(myElevator);
    }

}
