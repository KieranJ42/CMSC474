package busTerminal;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/*
 * This class is the primary storage location for the project and does some
 * calculations for the project 
 * also stores all the information for passengers
 */

public class Bus {
	
	private int numSeats;
	private Collection<String> destinations;
	private Map<String, String> passengers;
	
	
	/*
	 * Construction to make our bus object its fields
	 */
	public Bus(int numSeats, Collection<String> destinations) {
		this.numSeats = numSeats;
		this.destinations = destinations;
		passengers = new HashMap<String, String>();
	}
	
	
	//returns true if there are more seats on the bus then current passengers
	public boolean openSeats() {
		return (numSeats-passengers.size() > 0);
	}
	
	
	//returns true if destination is in destinations
	public boolean containsDestination(String destination) {
		return destinations.contains(destination);
	}
	
	
	//adds a passenger to the current bus
	public void addPassenger(String name, String destinationCity) {
		passengers.put(name, destinationCity);
	}

	
	//returns the total number of passengers
	public int numPassengers() {
		return passengers.size();
	}
	
	
	// returns the total number of passengers with a specific name
	public boolean hasName(String name) {
		return passengers.containsKey(name);
	}
	
	
	//returns true if the current bus goes to a specific destination
	public boolean goesToCity(String destination) {
		return destinations.contains(destination);
	}
	
	
	/*
	 * returns the number of people on the current bus going to a specific
	 * destination
	 */
	public int numPeopleToCity(String destination) {
		int counter = 0;
		for (String s : passengers.keySet())
			if (passengers.get(s).equals(destination))
				counter++;
		
		return counter;
	}

}
