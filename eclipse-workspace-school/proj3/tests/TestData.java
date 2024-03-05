package tests;

// (c) Larry Herman, 2021.  You are allowed to use this code yourself, but
// not to provide it to anyone else.

import linkyList.LinkyList;

/* This class contains utility methods that create and return example
 * LinkyList objects that the public (and secret) tests can use, to reduce
 * the amount of code needed in different tests to create objects to test
 * the methods with.  You can use these methods in your student tests as
 * well, but don't modify this file, because our version is going to be used
 * on the submit server.  (You can either write your own helper methods in
 * your StudentTests class, and you can add your own classes to the tests
 * package, if you want to use modified versions of these methods.)
 */

public class TestData {

  // Adds all elements of an array of any type (as long as the type is one
  // that implements the Comparable interface) to a LinkyList of the same type
  // and returns it, for use in creating lists for testing the methods.
  public static <T extends Comparable<T>> LinkyList<T> makeLinkyList(T[] arr) {
    LinkyList<T> list= new LinkyList<T>();

    if (arr != null)
      for (T elt : arr)
        list.orderedInsert(elt);

    return list;
  }

  // Creates and returns a list of Integers with just a few elements.
  public static LinkyList<Integer> exampleLinkyList1() {
    return makeLinkyList(new Integer[]{1, 2, 3, 4, 5});
  }

  // Creates and returns a list of Integers with a few more elements.
  public static LinkyList<Integer> exampleLinkyList2() {
    return makeLinkyList(new Integer[]{2, 9, 16, 3, 7, 14, 8, 10, 6});
  }

}
