package tests;

// (c) Larry Herman, 2021.  You are allowed to use this code yourself, but
// not to provide it to anyone else.

import linkyList.LinkyList;
import org.junit.*;
import static org.junit.Assert.*;

public class PublicTests {

  // Adds a few elements to a list and checks its size.
  @Test public void testPublic1() {
    LinkyList<Integer> list= TestData.exampleLinkyList1();
    
    assertEquals(5, list.listNumElements());
  }

  // Adds a few elements to a list and ensures that they're stored in the
  // right order.
  @Test public void testPublic2() {
    LinkyList<Integer> list= TestData.exampleLinkyList1();
    
    assertEquals("1 2 3 4 5", list.toString());
  }

  // Adds several elements to a list, including some that are duplicates, so
  // tests that lists properly store duplicate elements.
  @Test public void testPublic3() {
    LinkyList<Integer> list= new LinkyList<Integer>();

    list.orderedInsert(2);
    list.orderedInsert(9);
    list.orderedInsert(9);
    list.orderedInsert(6);
    list.orderedInsert(3);
    list.orderedInsert(2);
    list.orderedInsert(7);

    assertEquals("2 2 3 6 7 9 9", list.toString());
  }

  // Tests some situations for lookForElement().
  @Test public void testPublic4() {
    LinkyList<Integer> list= TestData.exampleLinkyList2();

    assertNotNull(list.lookForElement(2));
    assertNotNull(list.lookForElement(9));
    assertNotNull(list.lookForElement(7));
    assertNotNull(list.lookForElement(3));
  }

  // Tests getElementAtIndex() by calling it on several elements in a list.
  @Test public void testPublic5() {
    LinkyList<Integer> list= TestData.exampleLinkyList2();

    assertEquals(2, (int) list.getElementAtIndex(0));
    assertEquals(7, (int) list.getElementAtIndex(3));
    assertEquals(10, (int) list.getElementAtIndex(6));
    assertEquals(16, (int) list.getElementAtIndex(8));
  }

  // Tests calling getElementAtIndex() with an invalid index that is larger
  // than the size of the list, which should throw an
  // IndexOutOfBoundsException.
  @Test(expected= IndexOutOfBoundsException.class) public void testPublic6() {
    LinkyList<Integer> list= TestData.exampleLinkyList1();

    list.getElementAtIndex(100);
  }

  // Tests reset() by verifying that a list is empty after it's called.
  @Test public void testPublic7() {
    LinkyList<Integer> list= TestData.exampleLinkyList2();

    list.reset();

    assertEquals("", list.toString());
    assertEquals(0, list.listNumElements());
  }

  // Tests some situations for elementRemove().
  @Test public void testPublic8() {
    LinkyList<Integer> list= TestData.exampleLinkyList2();
    //System.out.println(list.toString());
    list.elementRemove(9);
    //System.out.println(list.toString());
    assertEquals("2 3 6 7 8 10 14 16", list.toString());
    //System.out.println(list.toString());
    list.elementRemove(6);
    assertEquals("2 3 7 8 10 14 16", list.toString());
    //System.out.println(list.toString());
  }

  // Tests some situations for removeElementWithIndex().
  @Test public void testPublic9() {
    LinkyList<Integer> list= TestData.exampleLinkyList2();

    list.removeElementWithIndex(5);
    list.removeElementWithIndex(3);
    list.removeElementWithIndex(1);

    assertEquals("2 6 8 10 14 16", list.toString());
  }

  // Tests some situations for subList().
  @Test public void testPublic10() {
    LinkyList<Integer> list= TestData.exampleLinkyList2();
    LinkyList<Integer> partOfLinkyList= new LinkyList<Integer>();

    partOfLinkyList= list.subList(0, 1);
    assertEquals("2 3", partOfLinkyList.toString());

    partOfLinkyList= list.subList(2, 4);
    assertEquals("6 7 8", partOfLinkyList.toString());
  }

  // Tests some special cases of behavior for subList().
  @Test public void testPublic11() {
    LinkyList<Integer> list= TestData.exampleLinkyList2();
    LinkyList<Integer> partOfLinkyList= new LinkyList<Integer>();

    partOfLinkyList= list.subList(0, 0);
    assertEquals("2", partOfLinkyList.toString());

    partOfLinkyList= list.subList(8, 8);
    assertEquals("16", partOfLinkyList.toString());

    partOfLinkyList= list.subList(0, 8);
    assertEquals("2 3 6 7 8 9 10 14 16", partOfLinkyList.toString());
  }

  // Tests some situations for compareTo().
  @Test public void testPublic12() {
    LinkyList<Character> list1= new LinkyList<>();
    LinkyList<Character> list2= new LinkyList<>();

    list1.orderedInsert('s');
    list1.orderedInsert('h');
    list1.orderedInsert('e');
    list1.orderedInsert('e');
    list1.orderedInsert('p');

    list2.orderedInsert('s');
    list2.orderedInsert('h');
    list2.orderedInsert('e');
    list2.orderedInsert('e');
    list2.orderedInsert('p');

    assertEquals(0, list1.compareTo(list2));
    assertEquals(0, list2.compareTo(list1));

    list2.orderedInsert('s');
    assertTrue(list1.compareTo(list2) < 0);
    assertTrue(list2.compareTo(list1) > 0);
  }

}
