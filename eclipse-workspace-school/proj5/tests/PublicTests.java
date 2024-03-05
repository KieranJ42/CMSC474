package tests;

// (c) Larry Herman, 2021.  You are allowed to use this code yourself, but
// not to provide it to anyone else.

import linkyList.LinkyList;
import java.util.List;
import java.util.Iterator;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import org.junit.*;
import static org.junit.Assert.*;

public class PublicTests {

  // private utility methods ////////////////////////////////////////////

  // This is a used in multiple tests; these strings are added to a list
  // that most of the tests use, so these strings are also what an iterator
  // that is iterating over the list should be returning.
  //
  private static List<String> stringList= Arrays.asList("cat", "bat", "dog",
                                                        "eel", "bee", "emu",
                                                        "fox", "owl", "rat",
                                                        "cod", "ape");

  // This helper method compares two (Java) lists of strings to see if they
  // have the same elements.  If each one contains all of the elements of
  // the other then they must have exactly the same elements.  Note that
  // this has the effect of comparing whether the lists have the same
  // elements irrespective of the order of their elements.  (Since an
  // iterator is not guaranteed to return elements in any particular order
  // we have to compare the expected elements in a way that ignores order.)
  // In several places, to check if an iterator over a list is returning the
  // right elements (strings) we iterate over the list and save the elements
  // returned in an ArrayList, then use this method to compare it with
  // another ArrayList that has the expected elements.
  //
  private static boolean sameElements(List<String> actualElements,
                                      List<String> expectedElements) {
    return actualElements.containsAll(expectedElements) &&
           expectedElements.containsAll(actualElements);
  }

  // Returns a sample LinkyList that is storing several strings (the
  // ones in stringList above), which is used in most of the tests.
  private static LinkyList<String> exampleLinkyList() {
    LinkyList<String> list= new LinkyList<>();

    for (String str : stringList)
      list.orderedInsert(str);

    return list;
  }

  // tests //////////////////////////////////////////////////////////////

  // Checks that calling hasNext() on a new iterator over an empty
  // LinkyList returns false.
  @Test public void testPublic1() {
    LinkyList<Integer> list= new LinkyList<>();
    Iterator<Integer> iter= list.iterator();

    assertFalse(iter.hasNext());
  }

  // Checks that calling hasNext() on a new iterator over a nonempty
  // LinkyList returns true.
  @Test public void testPublic2() {
    LinkyList<String> list= exampleLinkyList();
    Iterator<String> iter= list.iterator();

    assertTrue(iter.hasNext());
  }

  // Checks that when hasNext() is called on an iterator for a
  // LinkyList with several elements, it still returns true after
  // the first call to next().
  @Test public void testPublic3() {
    LinkyList<String> list= exampleLinkyList();
    Iterator<String> iter= list.iterator();

    iter.next();  // we are just ignoring the element that is returned

    assertTrue(iter.hasNext());
  }

  // Checks the values returned by multiple iterations (meaning calling
  // next() multiple times) on an iterator that is iterating over a
  // LinkyList.  This is also testing that hasNext() properly
  // returns false when it should (after the last element is returned), and
  // the iterator doesn't have any errors when the last element is returned.
  @Test public void testPublic4() {
    LinkyList<String> list= exampleLinkyList();
    Iterator<String> iter= list.iterator();
    int count= 0;

    // besides ensuring that each element in the iteration is present in the
    // list of strings above, we ensure that each element is returned by the
    // iterator only once, and every element is returned once, by counting
    // the elements returned and comparing them to the number of strings
    // that should be in the list
    while (iter.hasNext()) {
      assertTrue(stringList.contains(iter.next()));
      count++;
    }

    assertEquals(stringList.size(), count);
  }

  // Checks that hasNext() can be successfully called multiple times in a
  // row, and returns the right value each time.
  @Test public void testPublic5() {
    LinkyList<String> list= exampleLinkyList();
    Iterator<String> iter= list.iterator();

    assertTrue(iter.hasNext());
    assertTrue(iter.hasNext());
    assertTrue(iter.hasNext());

    while (iter.hasNext())
      iter.next();

    assertFalse(iter.hasNext());
    assertFalse(iter.hasNext());
    assertFalse(iter.hasNext());
  }

  // Verifies that an iterator's next() method properly throws a
  // NoSuchElementException when next() is called more times than the number
  // of Strings in the list that it's iterating over.
  @Test(expected= NoSuchElementException.class) public void testPublic6() {
    LinkyList<String> list= exampleLinkyList();
    Iterator<String> iter= list.iterator();

    while (true)  // let's try to continue forever, and see how far we get
      iter.next();
  }

  // Tests that a new iterator can iterate over a list after one iterator
  // has already iterated over it.
  @Test public void testPublic7() {
    LinkyList<String> list= exampleLinkyList();
    Iterator<String> iter= list.iterator();
    List<String> actual= new ArrayList<String>();
    
    // use the iterator to iterate over the list and store all of the
    // elements in an ArrayList, for comparison purposes
    while (iter.hasNext())
      actual.add(iter.next());

    assertTrue(sameElements(actual, stringList));  // compare elements

    // now do the same thing with a new iterator
    iter= list.iterator();
    actual.clear();  // clear the contents of the ArrayList

    while (iter.hasNext())
      actual.add(iter.next());

    assertTrue(sameElements(actual, stringList));  // compare elements again
  }

  // Tests that two iterators can simultaneously iterate over the same list.
  @Test public void testPublic8() {
    LinkyList<String> list= exampleLinkyList();
    Iterator<String> iter1= list.iterator();
    Iterator<String> iter2= null;
    int count= 0;  // will count every element returned by both iterators

    // continue until the second iterator has returned all of the shapes, as
    // well as iterate before the second iterator has even started
    while (iter2 == null || iter2.hasNext()) {
      if (iter1.hasNext()) {
        assertTrue(stringList.contains(iter1.next()));
        count++;
      }

      // start the second iterator when the first iterator has processed a
      // few elements
      if (count == 4)
        iter2= list.iterator();
      else
        if (count > 4) {
          assertTrue(stringList.contains(iter2.next()));
          count++;
        }
    }

    assertEquals(2 * stringList.size(), count);
  }

  // Tests that an iterator works correctly after the LinkyList that
  // it is iterating over has been modified by adding new elements.  The
  // list is iterated over, elements are added, and the list is iterated
  // over again, to ensure that the iterator is iterating over the list's
  // correct current contents.
  @Test public void testPublic9() {
    LinkyList<String> list= exampleLinkyList();
    Iterator<String> iter= list.iterator();
    List<String> actual= new ArrayList<String>();
    List<String> copyOfExpected;
    
    // use the iterator to iterate over the list and store all of the
    // elements in an ArrayList, for comparison purposes
    while (iter.hasNext())
      actual.add(iter.next());

    assertTrue(sameElements(actual, stringList));  // compare elements

    // add new elements to the list
    list.orderedInsert("cow");
    list.orderedInsert("pig");
    list.orderedInsert("yak");

    // make a copy of the list used for checking the iterator, and add the
    // new strings to it
    copyOfExpected= new ArrayList<String>(stringList);
    copyOfExpected.add("cow");
    copyOfExpected.add("pig");
    copyOfExpected.add("yak");

    // now create a new iterator and check the list's contents again
    actual.clear();
    iter= list.iterator();
    while (iter.hasNext())
      actual.add(iter.next());

    // compare elements again
    assertTrue(sameElements(actual, copyOfExpected));
  }

  // Tests the basic operation of the iterator remove() method to remove
  // just one element from a list.
  @Test public void testPublic10() {
    LinkyList<String> list= exampleLinkyList();
    Iterator<String> iter= list.iterator();

    iter.next();
    iter.remove();

    // check the remaining elements
    while (iter.hasNext())
      assertTrue(stringList.contains(iter.next()));
  }

  // Tests that the right element is removed by calling the iterator
  // remove() method, meaning the one returned by the most recent call to
  // next().
  @Test public void testPublic11() {
    LinkyList<String> list= exampleLinkyList();
    Iterator<String> iter= list.iterator();
    List<String> actual= new ArrayList<String>();
    List<String> copyOfExpected= new ArrayList<>(stringList);
    String stringToRemove;

    iter.next();
    stringToRemove= iter.next();  // save the string that will be removed
    iter.remove();

    // now remove the string that was removed from the list from the copy of
    // stringList, then iterate over the list again, making sure that all of
    // the remaining strings are present in it (meaning that the right one
    // was removed)
    copyOfExpected.remove(stringToRemove);

    // check the elements remaining, using another iterator
    iter= list.iterator();
    while (iter.hasNext())
      actual.add(iter.next());

    assertTrue(sameElements(actual, copyOfExpected));  // compare elements
  }

  // Tests that the iterator remove() method throws the expected
  // IllegalStateException if it is called before next() was called.
  @Test(expected= IllegalStateException.class) public void testPublic12() {
    LinkyList<String> list= exampleLinkyList();
    Iterator<String> iter= list.iterator();

    iter.remove();
  }

  // Tests that the iterator remove() method throws the expected
  // IllegalStateException if it has already been called after the last call
  // to next(), so the element has already been removed.
  @Test(expected= IllegalStateException.class) public void testPublic13() {
    LinkyList<String> list= exampleLinkyList();
    Iterator<String> iter= list.iterator();

    iter.next();
    iter.next();

    iter.remove();
    iter.remove();
  }

  // Test calling the iterator remove() method to remove only some elements
  // from a list (the first several that are returned from the iteration).
  @Test public void testPublic14() {
    LinkyList<String> list= exampleLinkyList();
    Iterator<String> iter= list.iterator();
    int i, count= 0, numToRemove= 5;

    for (i= 1; i <= stringList.size(); i++) {
      iter.next();
      if (i <= numToRemove)
        iter.remove();
    }
    
    // now count the elements remaining and ensure that there are the right
    // number; the list should still be valid for a new iterator to work
    iter= list.iterator();
    while (iter.hasNext()) {
      iter.next();
      count++;
    }
    assertEquals(stringList.size() - numToRemove, count);
  }

  // Test calling the iterator remove() method to remove only some elements
  // from a list (the last several that are returned from the iteration).
  @Test public void testPublic15() {
    LinkyList<String> list= exampleLinkyList();
    Iterator<String> iter= list.iterator();
    ArrayList<String> removedElements= new ArrayList<>();
    String curWord;
    int i, count= 0, numToRemove= 5;

    System.out.println(list);
    
    for (i= 1; i <= stringList.size(); i++) {
      curWord= iter.next();

      if (i > stringList.size() - numToRemove) {
        removedElements.add(curWord);
        iter.remove();
      }
    }
    
    System.out.println(list);

    // now count the elements remaining and ensure that there are the right
    // number; the list should still be valid for a new iterator to work
    iter= list.iterator();
    while (iter.hasNext()) {
      iter.next();
      count++;
    }
    assertEquals(stringList.size() - numToRemove, count);

    // lastly ensure that none of the removed elements are still in the list
    for (String removedWord : removedElements)
      assertNull(list.lookForElement(removedWord));
  }

  // Tests calling the iterator remove() method to remove only some elements
  // from a list (several that are returned in the middle of the iteration).
  @Test public void testPublic16() {
    LinkyList<String> list= exampleLinkyList();
    Iterator<String> iter= list.iterator();
    ArrayList<String> removedElements= new ArrayList<>();
    String curWord;
    int i, count= 0, firstToRemove= 4, numToRemove= 6;

    // remove six elements starting with the fourth one and save the
    // elements that are being removed in an ArrayList, so we can ensure
    // that none of them are present in the list after they have been
    // removed
    for (i= 1; i <= stringList.size(); i++) {
      curWord= iter.next();

      if (i >= firstToRemove && i - firstToRemove + 1 <= numToRemove) {
        removedElements.add(curWord);
        iter.remove();
      }
    }

    // now count the elements remaining and ensure that there are the right
    // number; the list should still be valid for a new iterator to work
    iter= list.iterator();
    while (iter.hasNext()) {
      iter.next();
      count++;
    }
    assertEquals(stringList.size() - numToRemove, count);
 
    // lastly ensure that none of the removed elements are still in the list
    for (String removedWord : removedElements)
      assertNull(list.lookForElement(removedWord));
  }

  // Tests using the iterator remove() method to remove all of the elements
  // from a list.
  @Test public void testPublic17() {
    LinkyList<String> list= exampleLinkyList();
    Iterator<String> iter= list.iterator();
    int i;

    for (i= 1; i <= stringList.size(); i++) {
      assertTrue(stringList.contains(iter.next()));
      iter.remove();
    }

    // ensure everything has been removed from the list
    iter= list.iterator();
    assertFalse(iter.hasNext());
    assertEquals(0, list.listNumElements());
  }

  // Tests that its hasNext() method returns false after all of the elements
  // have been removed from a list by calling the remove() iterator method.
  @Test public void testPublic18() {
    LinkyList<String> list= exampleLinkyList();
    Iterator<String> iter= list.iterator();

    while (iter.hasNext()) {
      assertTrue(stringList.contains(iter.next()));
      iter.remove();
    }

    // ensure everything has been removed from the list
    iter= list.iterator();
    assertFalse(iter.hasNext());
    assertEquals(0, list.listNumElements());
  }

  // Tests that the iterator next() method throws the expected
  // NoSuchElementException when called after all of the elements have been
  // removed by remove().
  @Test(expected= NoSuchElementException.class) public void testPublic19() {
    LinkyList<String> list= exampleLinkyList();
    Iterator<String> iter= list.iterator();

    while (iter.hasNext()) {
      iter.next();
      iter.remove();
    }

    assertFalse(list.iterator().hasNext());
    // the list should be empty now, so this should throw the exception
    iter.next();
  }

  // Tests using the iterator remove() method to remove every other string
  // from a list.  First half of the elements are removed, then then half of
  // the remaining ones are removed.  Then the list's contents are checked,
  // and the remaining ones are removed, to ensure that the list remained
  // valid after some of the elements were removed.
  @Test public void testPublic20() {
    LinkyList<String> list= exampleLinkyList();
    Iterator<String> iter= list.iterator();
    int idx;

    idx= 1;
    while (iter.hasNext()) {
      iter.next();

      if (idx % 2 != 0)
        iter.remove();

      idx++;
    }

    iter= list.iterator();  // a new iterator that starts at the beginning
    idx= 1;
    while (iter.hasNext()) {
      iter.next();

      if (idx % 2 == 0)
        iter.remove();

      idx++;
    }

    // remove remaining contents
    iter= list.iterator();
    while (iter.hasNext()) {
      iter.next();
      iter.remove();
    }

    // ensure everything has been removed
    iter= list.iterator();
    assertFalse(iter.hasNext());
    assertEquals(0, list.listNumElements());
  }

}
