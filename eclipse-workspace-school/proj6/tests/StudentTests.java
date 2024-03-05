package tests;

import org.junit.*;

import bsearchPolyTree.BsearchPolyTree;
import bsearchPolyTree.EmptyBsearchPolyTree;
import bsearchPolyTree.EmptyBsearchPolyTreeException;
import org.junit.*;
import static org.junit.Assert.*;

import static org.junit.Assert.*;

public class StudentTests {


	/*
	 * tests basic functions of toString(), numElements(), and getValueForKey()
	 */
	@Test public void studentTest1() {
		BsearchPolyTree<Integer, String> tree = 
				EmptyBsearchPolyTree.getInstance(); 
		tree = tree.storeKeyAndValue(1, "a");
		tree = tree.storeKeyAndValue(-2, "b");
		tree = tree.storeKeyAndValue(3, "c");
		tree = tree.storeKeyAndValue(0, "d");
		tree = tree.storeKeyAndValue(5, "e");
		tree = tree.storeKeyAndValue(6, "f");
		tree = tree.storeKeyAndValue(1, "g");
		tree = tree.storeKeyAndValue(3, "h");
		tree = tree.storeKeyAndValue(6, "i");


		assertEquals("-2->b 0->d 1->g 3->h 5->e 6->i ", tree.toString());
		assertEquals(6, tree.numElements());
		assertEquals("g", tree.getValueForKey(1));
		assertEquals(null, tree.getValueForKey(4));		

	}


	/*
	 * Tests functionality of the min() and max() functions
	 */
	@Test public void studentTest2() {
		BsearchPolyTree<Integer, String> tree = 
				EmptyBsearchPolyTree.getInstance(); 
		tree = tree.storeKeyAndValue(1, "a");
		tree = tree.storeKeyAndValue(-2, "b");
		tree = tree.storeKeyAndValue(3, "c");
		tree = tree.storeKeyAndValue(0, "d");
		tree = tree.storeKeyAndValue(5, "e");
		tree = tree.storeKeyAndValue(6, "f");
		tree = tree.storeKeyAndValue(1, "g");
		tree = tree.storeKeyAndValue(3, "h");
		tree = tree.storeKeyAndValue(6, "i");

		BsearchPolyTree<Integer, String> tree2 = 
				EmptyBsearchPolyTree.getInstance();

		String test1 = null;
		String test2 = null;
		String test3 = null;
		String test4 = null;
		try {
			test1 = tree.max().toString();
			test2 = tree2.max().toString();
		} catch (EmptyBsearchPolyTreeException ex) {
			test2 = "good failure";
		}
		try {
			test3 = tree.min().toString();
			test4 = tree2.min().toString();
		} catch (EmptyBsearchPolyTreeException ex) {
			test4 = "good failure";
		}

		assertEquals(test1, "6");
		assertEquals(test2, "good failure");
		assertEquals(test3, "-2");
		assertEquals(test4, "good failure");
	}


	/*
	 * tests functionality of the function numElementsAtLevel
	 * making sure it returns the proper values even for nonexistent levels
	 */
	@Test public void studentTest3() {
		BsearchPolyTree<Integer, String> tree = 
				EmptyBsearchPolyTree.getInstance(); 
		tree = tree.storeKeyAndValue(1, "a");
		tree = tree.storeKeyAndValue(-2, "b");
		tree = tree.storeKeyAndValue(3, "c");
		tree = tree.storeKeyAndValue(0, "d");
		tree = tree.storeKeyAndValue(5, "e");
		tree = tree.storeKeyAndValue(6, "f");
		tree = tree.storeKeyAndValue(1, "g");
		tree = tree.storeKeyAndValue(3, "h");
		tree = tree.storeKeyAndValue(6, "i");

		assertEquals(0,tree.numElementsAtLevel(-1));
		assertEquals(1,tree.numElementsAtLevel(0));
		assertEquals(2,tree.numElementsAtLevel(1));
		assertEquals(2,tree.numElementsAtLevel(2));
		assertEquals(1,tree.numElementsAtLevel(3));
		assertEquals(0,tree.numElementsAtLevel(4));
	}


	/*
	 * Tests the delete method on the root node of a large tree
	 */
	@Test public void studentTest4() {
		BsearchPolyTree<Integer, String> tree3 = 
				EmptyBsearchPolyTree.getInstance();
		tree3 = tree3.storeKeyAndValue(0, "a");
		tree3 = tree3.storeKeyAndValue(4, "b");
		tree3 = tree3.storeKeyAndValue(-4, "c");
		tree3 = tree3.storeKeyAndValue(6, "d");
		tree3 = tree3.storeKeyAndValue(2, "e");
		tree3 = tree3.storeKeyAndValue(7, "a");
		tree3 = tree3.storeKeyAndValue(5, "b");
		tree3 = tree3.storeKeyAndValue(3, "c");
		tree3 = tree3.storeKeyAndValue(1, "d");
		tree3 = tree3.storeKeyAndValue(-6, "e");
		tree3 = tree3.storeKeyAndValue(-2, "a");
		tree3 = tree3.storeKeyAndValue(-1, "b");
		tree3 = tree3.storeKeyAndValue(-3, "c");
		tree3 = tree3.storeKeyAndValue(-5, "d");
		tree3 = tree3.storeKeyAndValue(-7, "e");

		tree3 = tree3.deleteKeyAndValue(0);
		assertEquals("-7->e -6->e -5->d -4->c -3->c -2->a -1->b "
				+ "1->d 2->e 3->c 4->b 5->b 6->d 7->a ", tree3.toString());
	}


	/*
	 * tests the hasSameKeys() function for when it should work correctly
	 */
	@Test public void studentTest5() {
		BsearchPolyTree<Integer, String> tree2 = 
				EmptyBsearchPolyTree.getInstance();
		tree2 = tree2.storeKeyAndValue(0, "a");
		tree2 = tree2.storeKeyAndValue(4, "b");
		tree2 = tree2.storeKeyAndValue(-4, "c");
		tree2 = tree2.storeKeyAndValue(6, "d");
		tree2 = tree2.storeKeyAndValue(2, "e");
		tree2 = tree2.storeKeyAndValue(7, "a");
		tree2 = tree2.storeKeyAndValue(5, "b");
		tree2 = tree2.storeKeyAndValue(3, "c");
		tree2 = tree2.storeKeyAndValue(1, "d");
		tree2 = tree2.storeKeyAndValue(-6, "e");
		tree2 = tree2.storeKeyAndValue(-2, "a");
		tree2 = tree2.storeKeyAndValue(-1, "b");
		tree2 = tree2.storeKeyAndValue(-3, "c");
		tree2 = tree2.storeKeyAndValue(-5, "d");
		tree2 = tree2.storeKeyAndValue(-7, "e");

		BsearchPolyTree<Integer, String> tree3 = 
				EmptyBsearchPolyTree.getInstance();
		tree3 = tree3.storeKeyAndValue(0, "a");
		tree3 = tree3.storeKeyAndValue(1, "b");
		tree3 = tree3.storeKeyAndValue(2, "c");
		tree3 = tree3.storeKeyAndValue(3, "d");
		tree3 = tree3.storeKeyAndValue(4, "e");
		tree3 = tree3.storeKeyAndValue(5, "a");
		tree3 = tree3.storeKeyAndValue(6, "b");
		tree3 = tree3.storeKeyAndValue(7, "c");
		tree3 = tree3.storeKeyAndValue(-1, "d");
		tree3 = tree3.storeKeyAndValue(-2, "e");
		tree3 = tree3.storeKeyAndValue(-3, "a");
		tree3 = tree3.storeKeyAndValue(-4, "b");
		tree3 = tree3.storeKeyAndValue(-5, "c");
		tree3 = tree3.storeKeyAndValue(-6, "d");
		tree3 = tree3.storeKeyAndValue(-7, "e");

		assertTrue(tree2.hasSameKeys(tree3));
	}

}
