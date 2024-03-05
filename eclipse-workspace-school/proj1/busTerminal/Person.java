package busTerminal;

/*
 * This class has the sole goal of storing information about passengers
 * on buses in the bus terminal
 */

public class Person {
	
	private String name;
	private String destination;
	
	public Person(String name, String destination ) {
		this.name = name;
		this.destination = destination;
	}
	
	
	//returns the passenger's name
	public String getName() {
		return name;
	}
	
	
	//returns the passenger's destination
	public String getDestination() {
		return destination;
	}

}
