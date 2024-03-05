package tests;

import org.junit.*;
import static org.junit.Assert.*;
import busTerminal.BusTerminal;
import java.util.Arrays;

public class StudentTests {

  	/*
	 * Makes a terminal with 25 buses (max number) and then tries to add 
	 * another bus to confirm that a bus is not actually added to the list
	 */
	@Test public void studentTest1() {
		BusTerminal terminal = new BusTerminal();
		
		for (int i = 0; i < 25; i++) {
			terminal.addBusToTerminal(100+i, 250, 
					Arrays.asList("College Park"));
		}
		
		assertEquals(terminal.numBusesAtTerminal(), terminal
				.addBusToTerminal(200, 60, Arrays.asList("stamford"))
				.numBusesAtTerminal());
			
	}
	
	
	/*
	 * Makes sure cancelBus() works when no buses have been added
	 */
	@Test public void studentTest2() {
		BusTerminal terminal = new BusTerminal();
		
		assertEquals(false, terminal.cancelBus(12));
	}
	
	
	/*
	 * Makes sure findBusIndex() works when no buses have been added
	 */
	@Test public void studentTest3() {
		BusTerminal terminal = new BusTerminal();
		
		assertEquals(-1, terminal.findBusIndex(123));
	}
	
	
	/*
	 * Tests the numPeopleOnBus() method and all it's return possibilities
	 */
	@Test public void studentTest4() {
		BusTerminal terminal = new BusTerminal();
		
		terminal.addBusToTerminal(100, 26, Arrays.asList("stamford"));
		
		terminal.addPassengerToBus(100, "John", "stamford");
		terminal.addPassengerToBus(100, "Paul", "stamford");
		terminal.addPassengerToBus(100, "Joe", "stamford");
		terminal.addPassengerToBus(100, "Chris", "stamford");
		terminal.addPassengerToBus(100, "Steve", "stamford");
		terminal.addPassengerToBus(100, "Roger", "stamford");
		terminal.addPassengerToBus(100, "John", "stamford");
		terminal.addPassengerToBus(100, "Mark", "stamford");
		
		assertEquals(2, terminal.numPeopleOnBus(100, "John"));
		assertEquals(1, terminal.numPeopleOnBus(100, "Paul"));
		assertEquals(0, terminal.numPeopleOnBus(100, "Maverick"));
		assertEquals(-1, terminal.numPeopleOnBus(100, null));
	}
	
	
	/*
	 * Tests to make sure a person won't be added to a bus if their destination
	 * doesn't have any corresponding buses
	 * 
	 * Also tests the cancel feature by returning false when a person with 
	 * a corresponding destination city of a deleted bus
	 */
	@Test public void studentTest5() {
		BusTerminal terminal = new BusTerminal();
		
		terminal.addBusToTerminal(16, 23, Arrays.asList("Atlanta"));
		
		assertEquals(false, terminal.addPassengerToBus(16, "George", "Vale"));
		
		terminal.cancelBus(16);
		
		assertEquals(false, terminal.addPassengerToBus(16, "John", "Atlanta"));
	}
	
	
	/*
	 * Tests to make sure a person can't be added to an already full bus
	 */
	@Test public void studentTest6() {
		BusTerminal terminal = new BusTerminal();
		
		terminal.addBusToTerminal(16, 3, Arrays.asList("Atlanta"));
		
		terminal.addPassengerToBus(16, "George", "Atlanta");
		terminal.addPassengerToBus(16, "John", "Atlanta");
		terminal.addPassengerToBus(16, "Al", "Atlanta");
		
		assertEquals(false, terminal.addPassengerToBus(16, "Fred", "Atlanta"));
	}

}
