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

/**
 * This class tests the LoopStation class, and by extension, the Track class
 */
public class LoopStationTester {
  
  /**
   * Checks the correctness of the createPod() method. This method should:
   * - create a Pod with the given capacity and podClass
   * - add it to the correct end of the correct Track in the LoopStation
   * - return a reference (shallow copy) to that Pod
   * Note that the tracks in LoopStation are protected, so you may access them directly for testing
   * purposes
   * @return true if createPod() is functioning correctly, false otherwise
   */
	public static boolean testCreatePod() { 
	    try {
	        // Setup a LoopStation with predefined waiting tracks
	        LoopStation station = new LoopStation();
	        int capacity = 5;
	        boolean podClass = true; // true for FIRST class, false for ECONOMY class

	        // Create a Pod and capture the reference
	        Pod createdPod = station.createPod(capacity, podClass);

	        // Verify the Pod is created correctly
	        if (createdPod == null || createdPod.getCapacity() != capacity || createdPod.getPodClass() != (podClass ? Pod.FIRST : Pod.ECONOMY)) {
	            return false; // Pod creation failed or mismatched attributes
	        }

	        
	        return true; // Test passed

	    } catch (Exception e) {
	        e.printStackTrace();
	        return false; // An exception occurred during the test
	    }
	}

  
  /**
   * Checks the correctness of the launchPod() method. This method should:
   * - throw a NoSuchElementException if no pods are waiting to launch
   * - launch first class pods from the END of the waitingFirst track
   * - launch economy class pods from the BEGINNING of the waitingEconomy track
   * - launch ALL first class pods before launching ANY economy class pods
   * Note that the tracks in LoopStation are protected, so you may access them directly for testing
   * purposes
   * @return true if launchPod() is functioning correctly, false otherwise
   */
  public static boolean testLaunchPod() {
	    try {
	        LoopStation station = new LoopStation();

	        // Create and add Pods to the waiting tracks
	        Pod firstPod1 = station.createPod(5, true);
	        Pod firstPod2 = station.createPod(5, true);
	        Pod economyPod1 = station.createPod(3, false);
	        Pod economyPod2 = station.createPod(3, false);

	        // Launch first-class Pods
	        station.launchPod(); // This will launch firstPod2 (last added to waitingFirst)
	        station.launchPod(); // This will launch firstPod1 (last added to waitingFirst)

	       
	        // Verify that the first-class Pods were launched correctly
	        if (!station.launched.get(1).equals(firstPod1) || !station.launched.get(2).equals(firstPod2)) {
	        	System.out.println(station.launched.get(1));
	            System.out.println("First-class launch failed");
	            return false; // First-class Pods were not launched in the correct order
	        }

	        // Launch economy-class Pods
	        station.launchPod(); // This will launch economyPod1 (first added to waitingEconomy)
	        station.launchPod(); // This will launch economyPod2 (last added to waitingEconomy)

	        // Verify that the economy-class Pods were launched correctly
	        if (!station.launched.get(2).equals(economyPod1) || !station.launched.get(3).equals(economyPod2)) {
	            System.out.println("Economy launch failed");
	            return false; // Economy-class Pods were not launched in the correct order
	        }

	        // Check that launching from an empty track throws an exception

	        return true; // All tests passed
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false; // An unexpected exception occurred
	    }
	}
  
  /**
   * Checks the correctness of the clearMalfunctioning() method. This method should:
   * - repeatedly check the launched track for malfunctioning pods
   * - remove those pods correctly
   * - report the number of pods it removed once there are no longer any malfunctioning pods
   * 
   * Things to consider when you are testing:
   * 
   * - there is a protected setNonFunctional() method you may use for testing purposes to ensure
   *   that at least one pod is non-functional
   *   
   * - calling isFunctional() on a Pod may cause it to malfunction! You should come up with an
   *   alternate way to check whether a Pod is functional, if you have not already.
   *   
   * - verify that the difference in number of pods from before the method was called and after
   *   the method was called is equal to the number that it reported
   *   
   * @return true if clearMalfunctioning() is functioning correctly, false otherwise
   */
  public static boolean testClearMalfunctioning() {
	    try {
	        // Create a new LoopStation
	        LoopStation station = new LoopStation();

	        // Create and add Pods to the launched track
	        Pod pod1 = station.createPod(5, true);
	        Pod pod2 = station.createPod(5, true);
	        Pod pod3 = station.createPod(3, false);
	        Pod pod4 = station.createPod(3, false);

	        // Simulate launching the Pods
	        station.launchPod(); // Launch pod1
	        station.launchPod(); // Launch pod2
	        station.launchPod(); // Launch pod3
	        station.launchPod(); // Launch pod4

	        // Make pod2 and pod3 non-functional for testing
	        pod2.setNonFunctional();
	        pod3.setNonFunctional();

	        // Count the number of Pods before clearing malfunctioning Pods
	        int initialCount = station.getNumLaunched(); // Total Pods in launched track

	        // Call clearMalfunctioning() and get the number of removed Pods
	        int removedCount = station.clearMalfunctioning();

	        // Count the number of Pods after clearing malfunctioning Pods
	        int finalCount = station.getNumLaunched(); // Total Pods in launched track

	        // Verify that the number of removed Pods matches the expected count
	        int expectedRemovedCount = 2; // pod2 and pod3 should be removed
	        if (removedCount != expectedRemovedCount) {
	            System.out.println("Failed: Expected to remove " + expectedRemovedCount + " but removed " + removedCount);
	            return false;
	        }

	        // Verify that the difference in count is correct
	        if (initialCount - finalCount != removedCount) {
	            System.out.println("Failed: Initial count was " + initialCount + ", final count is " + finalCount);
	            return false; // The counts do not match the expected difference
	        }

	        return true; // All tests passed
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false; // An unexpected exception occurred
	    }
	}
  
  /**
   * Checks the correctness of the three getNumXXX() methods from LoopStation. This will require
   * adding Pods of various types, loading them with passengers, and launching them.
   * @return true if the getNumXXX() methods are all functioning correctly, false otherwise
   */
  public static boolean testGetNums() {
	    try {
	        // Create a new LoopStation
	        LoopStation station = new LoopStation();

	        // Create and add first-class Pods
	        Pod firstPod1 = station.createPod(5, true); // Capacity of 5
	        Pod firstPod2 = station.createPod(5, true); // Capacity of 5

	        // Create and add economy-class Pods
	        Pod economyPod1 = station.createPod(3, false); // Capacity of 3
	        Pod economyPod2 = station.createPod(3, false); // Capacity of 3

	        // Add passengers to the first-class Pods
	        firstPod1.addPassenger("Alice");
	        firstPod1.addPassenger("Bob");
	        firstPod2.addPassenger("Charlie");

	        // Add passengers to the economy Pods
	        economyPod1.addPassenger("Dave");
	        economyPod1.addPassenger("Eve");
	        economyPod2.addPassenger("Frank");

	        // Check the number of waiting Pods
	        int expectedWaitingCount = 4; // All Pods are waiting
	        if (station.getNumWaiting() != expectedWaitingCount) {
	            System.out.println("Failed: Expected " + expectedWaitingCount + " waiting Pods, but got " + station.getNumWaiting());
	            return false;
	        }

	        // Launch first-class Pods
	        station.launchPod(); // Launch firstPod1
	        station.launchPod(); // Launch firstPod2

	        // Check the number of launched Pods
	        int expectedLaunchedCount = 2; // Two first-class Pods launched
	        if (station.getNumLaunched() != expectedLaunchedCount) {
	            System.out.println("Failed: Expected " + expectedLaunchedCount + " launched Pods, but got " + station.getNumLaunched());
	            return false;
	        }

	        // Check the number of passengers in launched Pods
	        int expectedPassengerCount = 3; // 2 in firstPod1, 1 in firstPod2, 2 in economyPod1, 1 in economyPod2
	        if (station.getNumPassengers() != expectedPassengerCount) {
	            System.out.println("Failed: Expected " + expectedPassengerCount + " passengers, but got " + station.getNumPassengers());
	            return false;
	        }

	        // Now launch the economy Pods
	        station.launchPod(); // Launch economyPod1
	        station.launchPod(); // Launch economyPod2

	        // Final check after all launches
	        expectedWaitingCount = 0; // No Pods waiting now
	        if (station.getNumWaiting() != expectedWaitingCount) {
	            System.out.println("Failed: Expected " + expectedWaitingCount + " waiting Pods, but got " + station.getNumWaiting());
	            return false;
	        }
	        
	     // Final passenger count check after launching all Pods
	        expectedPassengerCount = 6; // 2 in firstPod1, 1 in firstPod2, 2 in economyPod1, 1 in economyPod2
	        if (station.getNumPassengers() != expectedPassengerCount) {
	            System.out.println("Failed: Expected " + expectedPassengerCount + " passengers after all launches, but got " + station.getNumPassengers());
	            return false;
	        }
	        

	        return true; // All tests passed
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false; // An unexpected exception occurred
	}
  }

  public static void main(String[] args) {
    boolean test1 = testCreatePod();
    System.out.println("testCreatePod: "+(test1?"PASS":"fail"));
    
    boolean test2 = testLaunchPod();
    System.out.println("testLaunchPod: "+(test2?"PASS":"fail"));
    
    boolean test3 = testClearMalfunctioning();
    System.out.println("testClearMalfunctioning: "+(test3?"PASS":"fail"));
    
    boolean test4 = testGetNums();
    System.out.println("testGetNums: "+(test4?"PASS":"fail"));
    
    System.out.println("ALL TESTS: "+((test1&&test2&&test3&&test4)?"PASS":"fail"));
  }

}
