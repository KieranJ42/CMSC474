package tests;

// (c) Larry Herman, 2021.  You are allowed to use this code yourself, but
// not to provide it to anyone else.

import java.util.Arrays;
import busTerminal.BusTerminal;
import org.junit.*;
import static org.junit.Assert.*;

// The utility methods busTerminal1() through busTerminal7() are, as the
// calls to them suggest, defined in the TestData class.

public class PublicTests {

  // Tests that a newly-instantiated busTerminal has no buses by calling
  // numBusesAtTerminal() on it.
  @Test public void testPublic1() {
    assertEquals(0, new BusTerminal(2).numBusesAtTerminal());
  }

  // Tests calling numBusesAtTerminal() on a busTerminal with just one
  // bus.
  @Test public void testPublic2() {
    BusTerminal busTerminal= TestData.busTerminal1();

    assertEquals(1, busTerminal.numBusesAtTerminal());
  }

  // Tests calling numPeopleOnBus(int) on a bus that no passengers have
  // been added to.
  @Test public void testPublic3() {
    BusTerminal busTerminal= TestData.busTerminal1();

    assertEquals(0, busTerminal.numPeopleOnBus(1752));
  }

  // Tests calling numPeopleOnBus(int, String) on a bus that no
  // passengers have been added to.
  @Test public void testPublic4() {
    BusTerminal busTerminal= TestData.busTerminal3();

    assertEquals(8, busTerminal.numPeopleOnBus(1166));
  }

  // Tests that addBusToTerminal() properly returns a reference to the
  // modified current object busTerminal, when a bus is added.
  @Test public void testPublic5() {
    BusTerminal busTerminal= new BusTerminal(5);

    busTerminal.addBusToTerminal(1, 10, Arrays.asList("Rockville")).
      addBusToTerminal(2, 20, Arrays.asList("Richmond")).
      addBusToTerminal(3, 30, Arrays.asList("Annapolis"));

    assertEquals(3, busTerminal.numBusesAtTerminal());

    assertEquals(1, busTerminal.numBusesGoingToCity("Rockville"));
    assertEquals(1, busTerminal.numBusesGoingToCity("Richmond"));
    assertEquals(1, busTerminal.numBusesGoingToCity("Annapolis"));
  }

  // Tests calling addBusToTerminal() with an invalid number of seats; the
  // bus should not be added, and a reference to the unmodified current
  // object should be returned.
  @Test public void testPublic6() {
    BusTerminal busTerminal= TestData.busTerminal2();

    busTerminal.addBusToTerminal(1, -999, Arrays.asList("Frederick"));

    // ensure that the bus was not added (the busTerminal should still have
    // just one bus)
    assertEquals(1, busTerminal.numBusesAtTerminal());
  }

  // Tests checking the number of people on a bus with one passenger using
  // numPeopleOnBus(int).
  @Test public void testPublic7() {
    BusTerminal busTerminal= TestData.busTerminal2();

    assertEquals(1, busTerminal.numPeopleOnBus(8209));
  }

  // Tests checking the number of people on a bus with one passenger using
  // numPeopleOnBus(int, String).
  @Test public void testPublic8() {
    BusTerminal busTerminal= TestData.busTerminal2();

    assertEquals(1, busTerminal.numPeopleOnBus(8209, "Sheep"));
  }

  // Tests checks the number of people on a bus with several passengers
  // using numPeopleOnBus(int) and numPeopleOnBus(int, String).
  @Test public void testPublic9() {
    BusTerminal busTerminal= TestData.busTerminal3();
    String[] expectedNames= {"Camel", "Kangaroo", "Hamster", "Rhinoceros",
                             "Elephant", "Koala", "Hedgehog", "Deer"};
    int i;

    // check the number of passengers
    assertEquals(8, busTerminal.numPeopleOnBus(1166));
    // check that all the passengers are present
    for (i= 0; i < expectedNames.length; i++)
      assertEquals(1, busTerminal.numPeopleOnBus(1166, expectedNames[i]));
  }

  // Tests calling numBusesGoingToCity() on a busTerminal with multiple
  // buses.
  @Test public void testPublic10() {
    BusTerminal busTerminal= TestData.busTerminal4();
    String[] expectedCities= {"Asheville", "Phoenix", "New York", "Bangor"};
    int i;

    for (i= 0; i < expectedCities.length; i++)
      assertEquals(1, busTerminal.numBusesGoingToCity(expectedCities[i]));
  }

  // Tests calling cancelBus() on the only bus at a busTerminal; the
  // busTerminal should have no buses after that.
  @Test public void testPublic11() {
    BusTerminal busTerminal= TestData.busTerminal2();

    busTerminal.cancelBus(8209);

    assertEquals(0, busTerminal.numBusesAtTerminal());
  }

  // Tests calling cancelBus() on one bus in a busTerminal that has several
  // buses.
  @Test public void testPublic12() {
    BusTerminal busTerminal= TestData.busTerminal4();

    busTerminal.cancelBus(2917);

    assertEquals(3, busTerminal.numBusesAtTerminal());
    assertEquals(0, busTerminal.numBusesGoingToCity("New York"));
  }

  // Tests calling numPeopleOnBus() on an invalid bus number (a number
  // that does not correspond to any bus that was added).
  @Test public void testPublic13() {
    BusTerminal busTerminal= TestData.busTerminal5();

    assertEquals(-1, busTerminal.numPeopleOnBus(500));
  }

  // Tests trying to add more than the maximum number of buses to a
  // busTerminal, which should not succeed.
  @Test public void testPublic14() {
    BusTerminal busTerminal= new BusTerminal(25);
    int i;

    // tries to add 50 buses with number i and destination Cityi, but this
    // should fail after 25 have been added
    for (i= 1; i <= 50; i++)
      busTerminal.addBusToTerminal(i, 100, Arrays.asList("City" + i));

    // make sure that fewer than 50 buses were added
    assertTrue(busTerminal.numBusesAtTerminal() < 50);
  }

  // Tests trying to pass null when calling numPeopleGoingToCity().
  @Test public void testPublic15() {
    BusTerminal busTerminal= TestData.busTerminal2();

    assertEquals(0, busTerminal.numPeopleGoingToCity(null));
  }

}
