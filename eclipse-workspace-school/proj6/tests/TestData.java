package tests;

// (c) Larry Herman, 2021.  You are allowed to use this code yourself, but
// not to provide it to anyone else.

/* This class contains utility methods that create and return example
 * NonEmptyPolyBsearchPolyTree objects that the public (and secret) tests
 * can use, to reduce the amount of code needed in different tests to create
 * objects to test the methods with.  You can use these methods in your
 * student tests as well, but don't modify this file, because our version is
 * going to be used on the submit server.  (You can either write your own
 * helper methods in your StudentTests class, and you can add your own
 * classes to the tests package, if you want to use modified versions of
 * these methods.)
 */

import bsearchPolyTree.BsearchPolyTree;
import bsearchPolyTree.EmptyBsearchPolyTree;
import java.util.List;
import java.util.Arrays;
import java.util.Iterator;

public class TestData {

  // utility method /////////////////////////////////////////////////////

  @SuppressWarnings("unchecked")
  public static <T extends Comparable<T>, U> BsearchPolyTree<T, U>
                createBsearchPolyTree(T[] keyArr, U[] valueArr) {
    BsearchPolyTree<T, U> tree= EmptyBsearchPolyTree.getInstance();
    int min, i;

    if (keyArr != null && valueArr != null) {
      // if the arrays are for some reason of different sizes just iterate
      // over the number of elements in the smaller one
      min= keyArr.length < valueArr.length ? keyArr.length : valueArr.length;
      for (i= 0; i < min; i++)
        tree= tree.storeKeyAndValue(keyArr[i], valueArr[i]);
    }

    return tree;
  }

  // Adds all elements of a list of keys (of any type) and a list of values
  // (of any type) to a tree, and returns it.  The lists should be the same
  // size, but we handle things gracefully if they're not.
  @SuppressWarnings("unchecked")
  public static <T extends Comparable<T>, U> BsearchPolyTree<T, U>
                createBsearchPolyTree(List<T> keyList, List<U> valueList) {
    BsearchPolyTree<T, U> tree= EmptyBsearchPolyTree.getInstance();
    Iterator<T> keyIter;
    Iterator<U> valueIter;

    if (keyList != null && valueList != null) {
      keyIter= keyList.iterator();
      valueIter= valueList.iterator();

      while (keyIter.hasNext() && valueIter.hasNext())
        tree= tree.storeKeyAndValue(keyIter.next(), valueIter.next());
    }

    return tree;
  }

  // Returns a small tree with several elements, with Integer keys and
  // Character values.
  public static BsearchPolyTree<Integer, Character> sampleTree1() {
    return createBsearchPolyTree(Arrays.asList(7, 5, 13, 1, 6, 11, 17),
                                 Arrays.asList('p', 'o', 'i', 'd', 'l',
                                               'h', 'n'));
  }

  // Returns a small tree with several elements, with Integer keys and
  // Character values.
  public static BsearchPolyTree<Integer, Character> sampleTree2() {
    return createBsearchPolyTree(Arrays.asList(7, 13, 11,  5,  1, 17,  3, 2),
                          Arrays.asList('h', 'n', 'a', 'p', 'e', 't', 'e',
                                        'l'));
  }

  // Returns a slightly larger tree with Character keys and Character values.
  public static BsearchPolyTree<Character, Character> sampleTree3() {
    return createBsearchPolyTree(Arrays.asList('e', 'b', 'l', 'c', 'g', 'd',
                                               'k', 'f', 'j', 'n', 'i', 'a',
                                               'm', 'h'),
                                 Arrays.asList('l', 'a', 'a', 'v', 'v', 'a',
                                               's', 'o', 'u', 'l', 's', 'j',
                                               'l', 'e'));
  }

}
