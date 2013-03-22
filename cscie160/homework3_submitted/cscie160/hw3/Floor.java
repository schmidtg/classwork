package cscie160.hw3;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Represents the number of floors in the building.
 *
 * User: Graham Schmidt
 * Date: 3/8/11
 * Time: 8:03 PM
 *
 */

public class Floor {
    /**
     * Declare instance variables
     */
    private int buildingFloor;

    private List<Passenger> passengersUp = new ArrayList<Passenger>();
    private List<Passenger> passengersDown = new ArrayList<Passenger>();
    private List<Passenger> passengersArrived = new ArrayList<Passenger>();


    /**
     * Floor constructor initializes the building floor for this object
     * @param buildingFloor sets which floor this instance of floor refers to
     */
    public Floor(int buildingFloor) {
        this.buildingFloor = buildingFloor;
    }

    /**
     * Add a passenger to this floor's queue going Up or Down (based on the destination floor).
     * @param destFloor destination floor of the passenger.
     */
    public void addWaitingPassenger(int destFloor) {
        if (destFloor >= this.buildingFloor) {
            passengersUp.add(new Passenger(buildingFloor,destFloor));
        } else {
            passengersDown.add(new Passenger(buildingFloor,destFloor));
        }
    }

    /**
     * Returns how many passengers are waiting to board.
     * @return how many passengers queued to board
     */
    public int getPassengersQueued() {
        return passengersUp.size() + passengersDown.size();
    }

    /**
     * Unloads any passengers who are destined for this floor. Sets the passenger's arrive status to true.
     * Boards any passengers waiting on this floor.
     * @param elevator Elevator that contains floors and passengers
     */
    public void unloadPassengers(Elevator elevator) {

        // Unload any passengers on the elevator that were going up
        int numUnloadedU = 0;
        for (Iterator<Passenger> iter = elevator.getPassengersUp().iterator(); iter.hasNext();) {
            Passenger p = iter.next();
            if(p.getDestFloor() == this.buildingFloor) {
                p.setArrive(true);
                passengersArrived.add(p);
                iter.remove();
                numUnloadedU++;
            }
            elevator.removeRegisterRequest(this.buildingFloor);
        }

        // Unload any passengers on the elevator that were going down
        int numUnloadedD = 0;
        for (Iterator<Passenger> iter = elevator.getPassengersDown().iterator(); iter.hasNext();) {
            Passenger p = iter.next();
            if(p.getDestFloor() == this.buildingFloor) {
                p.setArrive(true);
                passengersArrived.add(p);
                iter.remove();
                numUnloadedD++;
            }
            elevator.removeRegisterRequest(this.buildingFloor);
        }

        // Print out message if we unloaded any passengers
        int totalUnloaded = numUnloadedU + numUnloadedD;
        if(totalUnloaded > 0) {
            String passengerS = (totalUnloaded == 1 ? "passenger" : "passengers");
            System.out.println("Unloaded " + totalUnloaded + " " + passengerS + ".");
        }

        // Board any passengers waiting to go up
        for(Iterator<Passenger> iter = this.passengersUp.iterator(); iter.hasNext();) {
            Passenger p = iter.next();

            try {
                elevator.boardPassenger(p.getDestFloor());
                iter.remove();
            } catch (IllegalFloorException e) {}
            catch (ElevatorFullException e) {}
        }

        // Board any passengers waiting to go down
        for(Iterator<Passenger> iter = this.passengersDown.iterator(); iter.hasNext();) {
            Passenger p = iter.next();

            try {
                elevator.boardPassenger(p.getDestFloor());
                iter.remove();
            } catch (IllegalFloorException e) {}
            catch (ElevatorFullException e) {}
        }
    }

    /**
     * Prints out the status of the floor and all its passengers
     * @return String
     */
    public String toString() {
        String floorPassengersUpStatus = "";
        for(Passenger p : this.passengersUp) {
            floorPassengersUpStatus += p.toString();
        }

        String floorPassengersDownStatus = "";
        for(Passenger p : this.passengersDown) {
            floorPassengersDownStatus += p.toString();
        }

        String floorPassengersArrivedStatus = "";
        for(Passenger p : this.passengersArrived) {
            floorPassengersArrivedStatus += p.toString();
        }

        return floorPassengersUpStatus
                + floorPassengersDownStatus
                + floorPassengersArrivedStatus;
    }
}