/////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    Hyperloop
// Course:   CS 300 Fall 2024
//
// Author:   Vedanth Jana
// Email:    vjana @wisc.edu
// Lecturer:  Blerina Gkotse)
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
// 
// Partner Name:    None
// Partner Email:   None
// Partner Lecturer's Name: None
// 
// VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
//   _x__ Write-up states that pair programming is allowed for this assignment.
//   _x__ We have both read and understand the course Pair Programming Policy.
//   _x__ We have registered our team prior to the team registration deadline.
//
//////////////////////// ASSISTANCE/HELP CITATIONS ////////////////////////////
//
// Persons:         My mother helped me debug some code and also explained what some methods did
// Online Sources:  None
//
///////////////////////////////////////////////////////////////////////////////
import java.util.NoSuchElementException;

public class LoopStation {
    protected Track waitingFirst;   // Track for first-class Pods
    protected Track waitingEconomy;  // Track for economy-class Pods
    protected Track launched;        // Track for launched Pods

    public LoopStation() {
        waitingFirst = new Track(); // Initialize waiting tracks
        waitingEconomy = new Track();
        launched = new Track();
    }

    public Pod createPod(int capacity, boolean podClass) {
        int podClass1=0;
        if(podClass == true) {
        	 podClass1 = Pod.FIRST; 
        }
        Pod newPod = new Pod(capacity, podClass1); // Create a new Pod
        if (podClass1 == Pod.FIRST) {
            waitingFirst.add(newPod); // Add to waitingFirst track
        } else if (podClass1 == Pod.ECONOMY) {
            waitingEconomy.add(newPod); // Add to waitingEconomy track
        }

        return newPod; // Return the newly created Pod
    }
    
    
    public void launchPod() {
        if (!waitingFirst.isEmpty()) {
            Pod podToLaunch = (Pod) waitingFirst.remove(waitingFirst.size() - 1); // Launch from waitingFirst
            launched.add(podToLaunch); // Add to launched track
            //return podToLaunch;
        } else if (!waitingEconomy.isEmpty()) {
            Pod podToLaunch = (Pod) waitingEconomy.remove(0); // Launch from waitingEconomy
            launched.add(podToLaunch); // Add to launched track
           // return podToLaunch;
        } else {
            throw new NoSuchElementException("No pods available to launch");
        }
    }

    public int clearMalfunctioning() {
    	int removedCount = 0;
        for (int i = 0; i < launched.size(); i++) {
            Pod pod = (Pod) launched.get(i); // Get Pod from launched track
            if (!pod.isFunctional()) {
                launched.remove(i); // Remove malfunctioning Pod
                i--; // Adjust index after removal
                removedCount++;
            }
        }
        return removedCount; // Return total removed Pods
    }

    public int getNumWaiting() {
        return waitingFirst.size() + waitingEconomy.size(); // Total waiting Pods
    }

    public int getNumLaunched() {
        return launched.size(); // Total launched Pods
    }

    public int getNumPassengers() {
        int totalPassengers = 0;
      
        for (int i = 0; i < launched.size(); i++) {
            Pod pod = (Pod) launched.get(i); // Get Pod from launched track
            if (pod.isFunctional()) {
                try {
                    totalPassengers += pod.getNumPassengers(); // Attempt to add passengers from functional Pods
                } catch (MalfunctioningPodException e) {
                    // Handle the exception, e.g., log it or ignore it
                    System.err.println("Error retrieving passengers from Pod: " + e.getMessage());
                }
            }
        }

        return totalPassengers; // Return total passengers
    }
}