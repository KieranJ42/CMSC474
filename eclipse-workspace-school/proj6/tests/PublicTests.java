package tests;

// (c) Larry Herman, 2021.  You are allowed to use this code yourself, but
// not to provide it to anyone else.

import bsearchPolyTree.BsearchPolyTree;
import bsearchPolyTree.EmptyBsearchPolyTree;
import bsearchPolyTree.EmptyBsearchPolyTreeException;
import org.junit.*;
import static org.junit.Assert.*;

@SuppressWarnings({"unchecked"})
public class PublicTests {

  // Tests calling numElements() on an empty tree.
  @Test public void testPublic1() {
    assertEquals(0, EmptyBsearchPolyTree.getInstance().numElements());
  }

  // Tests calling numElements() on nonempty trees; this also tests that
  // storeKeyAndValue() is working.
  @Test public void testPublic2() {
    assertEquals(7, TestData.sampleTree1().numElements());
    assertEquals(8, TestData.sampleTree2().numElements());
  }

  // Tests calling getValueForKey() on the key of the root element of a
  // nonempty tree.
  @Test public void testPublic3() {
    assertEquals(Character.valueOf('p'),
                 TestData.sampleTree1().getValueForKey(7));
  }

  // Tests calling getValueForKey() on the key of an interior element of a
  // nonempty tree.
  @Test public void testPublic4() {
    assertEquals(Character.valueOf('i'),
                 TestData.sampleTree1().getValueForKey(13));
  }

  // Tests calling toString() on an empty tree.
  @Test public void testPublic5() {
    assertEquals("", EmptyBsearchPolyTree.getInstance().toString());
  }

  // Tests calling toString() on a nonempty tree.
  @Test public void testPublic6() {
    assertEquals("1->d 5->o 6->l 7->p 11->h 13->i 17->n ",
                 TestData.sampleTree1().toString());
  }

  // Tests the basic functionality of numElements().
  @Test public void testPublic7() {
    BsearchPolyTree<String, String> tree= EmptyBsearchPolyTree.getInstance();

    assertEquals(0, tree.numElements());

    tree= tree.storeKeyAndValue("Sheena", "Sheep");
    assertEquals(1, tree.numElements());
  }

  // Tests the basic functionality of max().
  @Test public void testPublic8() throws EmptyBsearchPolyTreeException {
    assertEquals(Integer.valueOf(17), TestData.sampleTree1().max());
  }

  // Tests the basic functionality of min().
  @Test public void testPublic9() throws EmptyBsearchPolyTreeException {
    assertEquals(Integer.valueOf(1), TestData.sampleTree2().min());
  }

  // Tests the basic functionality of numElementsAtLevel().
  @Test public void testPublic10() {
    assertEquals(4, TestData.sampleTree3().numElementsAtLevel(3));
  }

  // Tests calling numElementsAtLevel() on an empty tree.
  @Test public void testPublic11() {
    assertEquals(0, EmptyBsearchPolyTree.getInstance().numElementsAtLevel(1));
  }

  // Tests calling hasSameKeys() on two empty trees.
  @Test public void testPublic12() {
    BsearchPolyTree<String, String> tree1=
      EmptyBsearchPolyTree.getInstance();
    BsearchPolyTree<String, String> tree2=
      EmptyBsearchPolyTree.getInstance();

    assertTrue(tree1.hasSameKeys(tree2));
  }

  // Tests the basic functionality of hasSameKeys().
  @Test public void testPublic13() {
    BsearchPolyTree<String, String> tree1=
      EmptyBsearchPolyTree.getInstance();
    BsearchPolyTree<String, String> tree2=
      EmptyBsearchPolyTree.getInstance();

    tree1= tree1.storeKeyAndValue("Sheena", "Sheep");
    tree2= tree2.storeKeyAndValue("Donny", "Donkey");
    tree2= tree2.storeKeyAndValue("Horace", "Horse");

    assertFalse(tree1.hasSameKeys(tree2));
  }

  // Tests calling deleteKeyAndValue() on an interior element of a nonempty
  // tree.
  @Test public void testPublic14() {
    BsearchPolyTree<Integer, Character> tree= TestData.sampleTree2();

    tree= tree.deleteKeyAndValue(13);

    assertEquals(7, tree.numElements());
    assertEquals("1->e 2->l 3->e 5->p 7->h 11->a 17->t ", tree.toString());
  }

  // Tests calling deleteKeyAndValue() on a key that is not present in a
  // nonempty tree.
  @Test public void testPublic15() {
    BsearchPolyTree<Integer, Character> tree= TestData.sampleTree2();

    tree= tree.deleteKeyAndValue(100000);

    assertEquals(8, tree.numElements());
    assertEquals("1->e 2->l 3->e 5->p 7->h 11->a 13->n 17->t ",
                 tree.toString());
  }

}
