package tests;

import org.junit.*;
import static org.junit.Assert.*;
import linkyList.LinkyList;

public class StudentTests {

	
	/*
	 * Makes sure elements are properly added to the LinkyList in order
	 */
	@Test public void studentTest1() {
		LinkyList<Integer> list = new LinkyList<Integer>();
		list.orderedInsert(1);
		list.orderedInsert(5);
		list.orderedInsert(1);
		list.orderedInsert(6);
		list.orderedInsert(4);
		
		assertEquals("1 1 4 5 6", list.toString());
	}
	
	
	/*
	 * Tests the two types of remove methods part of the LinkyList class
	 */
	@Test public void studentTest2() {
		LinkyList<Integer> list = new LinkyList<Integer>();
		list.orderedInsert(1);
		list.orderedInsert(5);
		list.orderedInsert(1);
		list.orderedInsert(6);
		list.orderedInsert(4);
		
		list.elementRemove(1);
		list.removeElementWithIndex(3);
		
		assertEquals("1 4 5", list.toString());
	}
	
	
	/*
	 * Makes sure that getIndexOfElement() returns index value of first element
	 */
	@Test public void studentTest3() {
		LinkyList<Integer> list = new LinkyList<Integer>();
		list.orderedInsert(1);
		list.orderedInsert(5);
		list.orderedInsert(1);
		list.orderedInsert(6);
		list.orderedInsert(4);
		
		assertEquals(0, list.getIndexOfElement(1));
	}
	
	
	/*
	 * Test to make sure lookForElement() function works properly
	 */
	@Test public void studentTest4() {
		LinkyList<Integer> list = new LinkyList<Integer>();
		list.orderedInsert(1);
		list.orderedInsert(5);
		list.orderedInsert(1);
		list.orderedInsert(6);
		list.orderedInsert(4);
		
		assertEquals(4, (int)list.lookForElement(4));
	}
	
	
	/*
	 * Test to make sure copy constructor works properly
	 */
	@Test public void studentTest5() {
		LinkyList<Integer> list = new LinkyList<Integer>();
		list.orderedInsert(1);
		list.orderedInsert(5);
		list.orderedInsert(1);
		list.orderedInsert(6);
		list.orderedInsert(4);
		
		LinkyList<Integer> list2 = new LinkyList<Integer>(list);
		
		assertEquals("1 1 4 5 6", list2.toString());
	}
	
	
	/*
	 * Test to make sure subList() function works properly
	 */
	@Test public void studentTest6() {
		LinkyList<Integer> list = new LinkyList<Integer>();
		list.orderedInsert(1);
		list.orderedInsert(5);
		list.orderedInsert(1);
		list.orderedInsert(6);
		list.orderedInsert(4);
		
		LinkyList<Integer> list2 = list.subList(1, 3);
		
		assertEquals("1 4 5", list2.toString());
	}
	
	
	/*
	 * Makes sure compareTo works properly when all values are the same in
	 * two lists except one is longer and more elements than the second
	 * (should return difference in size in that case 5 - 3 = 2) 
	 */
	@Test public void studentTest7() {
		LinkyList<Integer> list = new LinkyList<Integer>();
		list.orderedInsert(1);
		list.orderedInsert(5);
		list.orderedInsert(1);
		list.orderedInsert(6);
		list.orderedInsert(4);
		
		LinkyList<Integer> list2 = new LinkyList<Integer>();
		list2.orderedInsert(1);
		list2.orderedInsert(4);
		list2.orderedInsert(1);
		
		assertEquals(2, list.compareTo(list2));
	}

}
