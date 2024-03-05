package tests;

// (c) Larry Herman, 2021.  You are allowed to use this code yourself, but
// not to provide it to anyone else.

import java.util.Arrays;
import busTerminal.BusTerminal;

/* This class contains utility methods that create and return example
 * BusTerminal objects that the public (and secret) tests can use, to reduce
 * the amount of code needed in different tests to create objects to test
 * the methods with.  You can use these methods in your student tests as
 * well.  But do not change this class, because our version will be used
 * when your project is submitted.
 */

public class TestData {

  // Returns a busTerminal that has one bus, with no passengers, which is only
  // going to one city.
  public static BusTerminal busTerminal1() {
    BusTerminal busTerminal= new BusTerminal();

    busTerminal.addBusToTerminal(1752, 100, Arrays.asList("Asheville"));

    return busTerminal;
  }

  // Returns a busTerminal that has one bus with one passenger, which is only
  // going to one city.
  public static BusTerminal busTerminal2() {
    BusTerminal busTerminal= new BusTerminal();

    busTerminal.addBusToTerminal(8209, 100, Arrays.asList("Seattle"));

    busTerminal.addPassengerToBus(8209, "Sheep", "Seattle");

    return busTerminal;
  }

  // Returns a busTerminal that has one bus, with several passengers (all
  // with different names).  The bus is only going to one city.
  public static BusTerminal busTerminal3() {
    BusTerminal busTerminal= new BusTerminal();

    busTerminal.addBusToTerminal(1166, 100, Arrays.asList("Minneapolis"));

    busTerminal.addPassengerToBus(1166, "Camel", "Minneapolis");
    busTerminal.addPassengerToBus(1166, "Kangaroo", "Minneapolis");
    busTerminal.addPassengerToBus(1166, "Hamster", "Minneapolis");
    busTerminal.addPassengerToBus(1166, "Rhinoceros", "Minneapolis");
    busTerminal.addPassengerToBus(1166, "Elephant", "Minneapolis");
    busTerminal.addPassengerToBus(1166, "Koala", "Minneapolis");
    busTerminal.addPassengerToBus(1166, "Hedgehog", "Minneapolis");
    busTerminal.addPassengerToBus(1166, "Deer", "Minneapolis");

    return busTerminal;
  }

  // Returns a busTerminal that has several buses, with different numbers of
  // passengers (all with different names).  The buses are all only going
  // to one city, but they are going to different cities.
  public static BusTerminal busTerminal4() {
    BusTerminal busTerminal= new BusTerminal();

    busTerminal.addBusToTerminal(1752, 100, Arrays.asList("Asheville"));
    busTerminal.addBusToTerminal(834, 100, Arrays.asList("Phoenix"));
    busTerminal.addBusToTerminal(2917, 100, Arrays.asList("New York"));
    busTerminal.addBusToTerminal(8128, 100, Arrays.asList("Bangor"));

    busTerminal.addPassengerToBus(834, "Rabbit", "Phoenix");
    busTerminal.addPassengerToBus(2917, "Hedgehog", "New York");
    busTerminal.addPassengerToBus(8128, "Hippopotamus", "Bangor");
    busTerminal.addPassengerToBus(834, "Parrot", "Phoenix");
    busTerminal.addPassengerToBus(2917, "Monkey", "New York");
    busTerminal.addPassengerToBus(8128, "Koala", "Bangor");
    busTerminal.addPassengerToBus(834, "Zebra", "Phoenix");
    busTerminal.addPassengerToBus(834, "Alligator", "Phoenix");
    busTerminal.addPassengerToBus(2917, "Manatee", "New York");
    busTerminal.addPassengerToBus(834, "Jaybird", "Phoenix");

    return busTerminal;
  }

  // Returns a busTerminal that has several buses, with different numbers of
  // passengers (some with the same names).  The buses are all only going
  // to one city, but they are going to different cities.
  public static BusTerminal busTerminal5() {
    BusTerminal busTerminal= new BusTerminal();

    busTerminal.addBusToTerminal(1234, 100, Arrays.asList("Indianapolis"));
    busTerminal.addBusToTerminal(4567, 100, Arrays.asList("San Francisco"));
    busTerminal.addBusToTerminal(9876, 100, Arrays.asList("Albuquerque"));
    busTerminal.addBusToTerminal(132, 100, Arrays.asList("Boston"));
    busTerminal.addBusToTerminal(321, 100, Arrays.asList("Indianapolis"));
    busTerminal.addBusToTerminal(4789, 100, Arrays.asList("Chicago"));
    busTerminal.addBusToTerminal(71675, 100, Arrays.asList("Austin"));

    busTerminal.addPassengerToBus(132, "Sheep", "Boston");
    busTerminal.addPassengerToBus(132, "Gerbil", "Boston");
    busTerminal.addPassengerToBus(9876, "Lion", "Albuquerque");
    busTerminal.addPassengerToBus(4567, "Frog", "San Francisco");
    busTerminal.addPassengerToBus(321, "Opossum", "Indianapolis");
    busTerminal.addPassengerToBus(1234, "Quokka", "Indianapolis");
    busTerminal.addPassengerToBus(132, "Sheep", "Boston");
    busTerminal.addPassengerToBus(9876, "Tarantula", "Albuquerque");
    busTerminal.addPassengerToBus(4789, "Gecko", "Chicago");
    busTerminal.addPassengerToBus(4789, "Orangutan", "Chicago");
    busTerminal.addPassengerToBus(71675, "Viper", "Austin");
    busTerminal.addPassengerToBus(1234, "Squirrel", "Indianapolis");
    busTerminal.addPassengerToBus(132, "Sheep", "Boston");
    busTerminal.addPassengerToBus(71675, "Platypus", "Austin");
    busTerminal.addPassengerToBus(4567, "Viper", "San Francisco");
    busTerminal.addPassengerToBus(321, "Quokka", "Indianapolis");
    busTerminal.addPassengerToBus(1234, "Cricket", "Indianapolis");

    return busTerminal;
  }

  // Returns a busTerminal that has several buses, with different numbers of
  // passengers (some with the same names).  The buses are each going to
  // multiple (different) cities.
  public static BusTerminal busTerminal6() {
    BusTerminal busTerminal= new BusTerminal();

    busTerminal.addBusToTerminal(1234, 100,
                              Arrays.asList("Baltimore", "Wilmington",
                                           "Philadelphia", "Trenton",
                                           "Newark", "New York", "New Haven",
                                            "Hartford", "Boston"));
    busTerminal.addBusToTerminal(9876, 100,
                              Arrays.asList("Charlottesville", "Montgomery",
                                           "Charleston", "Ashland",
                                           "Cincinnati", "Indianapolis",
                                            "Chicago"));

    busTerminal.addPassengerToBus(1234, "Sloth", "Hartford");
    busTerminal.addPassengerToBus(9876, "Lion", "Cincinnati");
    busTerminal.addPassengerToBus(1234, "Robin", "Newark");
    busTerminal.addPassengerToBus(9876, "Goat", "Ashland");
    busTerminal.addPassengerToBus(9876, "Tarantula", "Charleston");
    busTerminal.addPassengerToBus(1234, "Aardvark", "Baltimore");
    busTerminal.addPassengerToBus(9876, "Orangutan", "Chicago");
    busTerminal.addPassengerToBus(1234, "Bear", "Newark");
    busTerminal.addPassengerToBus(9876, "Numbat", "Indianapolis");
    busTerminal.addPassengerToBus(9876, "Bison", "Indianapolis");
    busTerminal.addPassengerToBus(1234, "Iguana", "Boston");
    busTerminal.addPassengerToBus(1234, "Sheep", "Boston");
    busTerminal.addPassengerToBus(9876, "Wallaby", "Indianapolis");
    busTerminal.addPassengerToBus(9876, "Gecko", "Chicago");
    busTerminal.addPassengerToBus(1234, "Gerbil", "Trenton");

    return busTerminal;
  }

  // Returns a busTerminal that has 25 buses (with no passengers).
  public static BusTerminal busTerminal7() {
    BusTerminal busTerminal= new BusTerminal();
    int i;

    // adds 25 buses with number i and destination Cityi
    for (i= 1; i <= 25; i++)
      busTerminal.addBusToTerminal(i, 100, Arrays.asList("City" + i));

    return busTerminal;
  }

}
