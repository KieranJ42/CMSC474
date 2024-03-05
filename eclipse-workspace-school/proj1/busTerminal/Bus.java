package busTerminal;

import java.util.*;

/*
 * This class is the primary storage location for the project and does some
 * calculations for the project
 */

public class Bus {
	
	private int busNumber;
	private int numSeats;
	private List<String> destinations;
	private ArrayList<Person> passengers;
	
	public Bus(int busNumber, int numSeats, List<String> destinations) {
		this.busNumber = busNumber;
		this.numSeats = numSeats;
		this.destinations = destinations;
		passengers = new ArrayList<Person>();
	}
	
	
	//returns true if there are more seats on the bus then current passengers
	public boolean openSeats() {
		return (numSeats-passengers.size() > 0);
	}
	
	
	//returns the list of destinations that a bus goes to
	public List<String> getDestinations() {
		return destinations;
	}
	
	
	//adds a passenger to the current bus
	public void addPassenger(String name, String destinationCity) {
		passengers.add(new Person(name, destinationCity));
	}

	
	//returns busNumber
	public int getBusNumber() {
		return busNumber;
	}
	
	
	//returns the total number of passengers
	public int getNumPassengers() {
		return passengers.size();
	}
	
	
	// returns the total number of passengers with a specific name
	public int getNumPassengers(String name) {
		int counter = 0;
		
		
		/*
		 * parses through the list of passengers to see how many have a given
		 * name
		 */
		for (int i = 0; i < passengers.size(); i++)
			if (passengers.get(i).getName().equals(name))
				counter++;
		return counter;
	}
	
	
	//returns true if the current bus goes to a specific destination
	public boolean goesToCity(String destination) {
		/*
		 * parses the list of destinations to see if it matches the parameter
		 * destination
		 */
		for (int i = 0; i < destinations.size(); i++)
			if (destinations.get(i).equals(destination))
				return true;
		return false;
	}
	
	
	/*
	 * returns the number of people on the current bus going to a specific
	 * destination
	 */
	public int numPeopleToCity(String destination) {
		int counter = 0;
		/*
		 * parses through the list of passengers to check to see if they
		 * are going to destination
		 */
		for (int i = 0; i < passengers.size(); i++) {
			if (passengers.get(i).getDestination().equals(destination))
				counter++;
		}
		return counter;
	}
	
}
