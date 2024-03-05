package busTerminal;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/* 
 * The BusTerminal class has purpose of both storing the list of buses located
 * in the terminal as well as holding methods to direct passengers to a bus 
 * that can take them to there location.
 * As a whole the class is less so intended to store multiple types of 
 * information, rather it is intended to perform the calculations required to
 * make terminals functional
 */
public class BusTerminal {

	private Map<Integer, Bus> busList;
	private int maxBuses;

	
	/*
	 * Constructor builds our BusTerminal and ensures it has a real max
	 * number of buses
	 */
	public BusTerminal(int maxBuses) {
		busList = new HashMap<Integer, Bus>();
		this.maxBuses = maxBuses;
		if (maxBuses <= 0)
			this.maxBuses = 25;
	}


	/* 
	 * This method checks to make sure that all conditions are met to be 
	 * able to add a bus to the terminal
	 */
	public BusTerminal addBusToTerminal(int busNumber, int numSeats,
			Collection<String> destinations) {

		/*
		 * the following lines make sure that each of the four required 
		 * conditions: no buses with the same number, a positive number of 
		 * seats, the terminal is not full, and actual destinations are listed
		 * If they are not met a bus can't be added to the terminal
		 */
		
		if (destinations == null || numSeats < 0 || !(busList.size() < maxBuses)
				|| busList.containsKey(busNumber))  {
			//Do nothing if these are the case
		} else
			busList.put(busNumber, new Bus(numSeats, destinations));
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
		Bus b = busList.get(busNumber);
		
		//Makes sure all conditions are met to be able to add a bus
		if (b == null || destinationCity == null || name == null || 
				!b.openSeats() || !b.containsDestination(destinationCity)
				|| b.hasName(name))
			return false;
		
		b.addPassenger(name, destinationCity);
		
		return true;
	}

	
	/*
	   * Makes sure that the bus exists and then returns the number of passengers
	   * on the bus with the right busNumber
	   */
	public int numPeopleOnBus(int busNumber) {
		Bus b = busList.get(busNumber);
		if (b == null)
			return -1;
		return b.numPassengers();
	}

	
	/*
	   * Makes sure that the bus exists and then returns the number of passengers
	   * on the bus with the right busNumber and name
	   */
	public int numPeopleOnBus(int busNumber, String name) {
		Bus b = busList.get(busNumber);
		if (b == null)
			return -1;
		return b.hasName(name) ? 1 : 0;
	}

	
	//returns the number of buses going to a specific city
	public int numBusesGoingToCity(String city) {
		Set<Integer> keySet = busList.keySet();
		int counter = 0;
		for (Integer i : keySet)
			if (busList.get(i).containsDestination(city))
				counter++;
		return counter;
	}

	/*
	   * parses through each bus to return the total number of people going to city
	   * between all buses
	   */
	public int numPeopleGoingToCity(String city) {
		Set<Integer> keySet = busList.keySet();
		int counter = 0;
		for (Integer i : keySet)
			if (busList.get(i).containsDestination(city))
				counter += busList.get(i).numPeopleToCity(city);
		return counter;
	}

	/*
	 * Attempts to delete the key value pair with key busNumber from busList
	 * If b is null, the deletion was unsuccessful 
	 */
	public boolean cancelBus(int busNumber) {
		Bus b = busList.remove(busNumber);
		return b != null;
	}

}
