package busTerminal;

import java.util.*;
/* 
 * The BusTerminal class has purpose of both storing the list of buses located
 * in the terminal as well as holding methods to direct passengers to a bus 
 * that can take them to there location.
 * As a whole the class is less so intended to store multiple types of 
 * information, rather it is intended to perform the calculations required to
 * make terminals functional
 */
public class BusTerminal {
	
	private List<Bus> busList;
	
	public BusTerminal() {
		busList = new ArrayList<Bus>();
	}
	/* 
	 * This method checks to make sure that all conditions are met to be 
	 * able to add a bus to the terminal
	 */
  public BusTerminal addBusToTerminal(int busNumber, int numSeats,
                                      List<String> destinations) {
	  /*
	   * the following lines make sure that each of the four required 
	   * conditions: no buses with the same number, a positive number of seats,
	   * 25 buses not already in the terminal, and actual destinations
	   * If they are not met a bus can't be added to the terminal
	   */
	  boolean canAdd = true;
	  if (findBusIndex(busNumber) != -1) 
		  canAdd = false;
	  if (numSeats < 0)
		  canAdd = false;
	  if (busList.size() >= 25)
		  canAdd = false;
	  if (destinations == null)
		  canAdd = false;
	  
	  /*
	   * if a bus can be added based on the previous conditions it is added 
	   * then it returns the BusTerminal reference
	   */
	  if (canAdd)
		  busList.add(new Bus(busNumber, numSeats, destinations));
	  return this;
  }

  //returns the total number of buses at the terminal
  public int numBusesAtTerminal() {
    return busList.size();
  }

  /*
   * attempts to add a passenger to a specific bus if the conditions are met
   */
  public boolean addPassengerToBus(int busNumber, String name,
                                   String destinationCity) {
	  /*
	   * if the bus number doesn't have a corresponding bus (index = -1) or 
	   * no seats are left, then the passenger isn't added and the method 
	   * returns false
	   */
	boolean successfulAdd = false;
	int index = findBusIndex(busNumber);
    if (index == -1)
    	return successfulAdd;
    if (!busList.get(index).openSeats())
    	return successfulAdd;
    
    
    /*
     * parses through the destinations of the bus with the right busNumber 
     * to check to see if it matches the desired destination city
     * If it does, the passenger is added to the bus and the method returns
     * true.
     * Otherwise it returns false
     */
    for (int j = 0; j < busList.get(index).getDestinations().size(); j++) {
    	if (busList.get(index).getDestinations().get(j)
    			.equals(destinationCity)) {
    		busList.get(index).addPassenger(name, destinationCity);
    		successfulAdd = true;
    		break;
    	}
    }
    return successfulAdd;
  }
  
  
  /*
   * parses through the list of buses to find the index of the bus with
   * the right busNumber
   */
  public int findBusIndex(int busNumber) {
	  int index = -1;
	  for (int i = 0; i < busList.size(); i++) {
	    	if (busList.get(i).getBusNumber() == busNumber) {
	    		return i;
	    	}
	  }
	  return index;
  }
  
  
  /*
   * Makes sure that the bus exists and then returns the number of passengers
   * on the bus with the right busNumber
   */
  public int numPeopleOnBus(int busNumber) {
	  int retVal = -1;
	  int index = findBusIndex(busNumber);
	  if (index == -1) 
		  return retVal;
	  return busList.get(index).getNumPassengers();
  }

  
  /*
   * Makes sure that the bus exists and then returns the number of passengers
   * on the bus with the right busNumber and name
   */
  public int numPeopleOnBus(int busNumber, String name) {
	  int retVal = -1;
	  int index = findBusIndex(busNumber);
	  if (index == -1 || name == null) 
		  return retVal;
	  return busList.get(index).getNumPassengers(name);
  }
  
  
  /*
   * returns the number of buses going to a specific city
   */
  public int numBusesGoingToCity(String city) {
	  int counter = 0;
	  
	  //makes sure the String for the city isn't null
	  if (city == null)
		  return counter;
	  
	  /*
	   * parses through the list of buses to check which go to city and returns
	   * how many buses go to city
	   */
	  for (int i = 0; i < busList.size(); i++) 
		  if (busList.get(i).goesToCity(city)) 
			  counter++;
	  return counter;
  }

  
  /*
   * parses through each bus to return the total number of people going to city
   * between all buses
   */
  public int numPeopleGoingToCity(String city) {
	  int counter = 0;
	  
	  //makes sure the String for the city isn't null
	  if (city == null)
		  return counter;
	  
	  /*
	   * parses through the list of buses to see how many people from each bus
	   * go to city and then returns the total number of people going to city
	   */
	  for (int i = 0; i < busList.size(); i++) 
		  counter += busList.get(i).numPeopleToCity(city);
	  return counter;
  }


  /*
   * Tries to remove a bus from the list of buses and returns whether or not
   * it was successful in removing a bus with the given busNumber
   */
  public boolean cancelBus(int busNumber) {
	  int index = findBusIndex(busNumber);
	  if (index == -1)
		  return false;
	  busList.remove(index);
	  return true;
  }

}
