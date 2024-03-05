package tests;

// (c) Larry Herman, 2021.  You are allowed to use this code yourself, but
// not to provide it to anyone else.

import recursiveMethods.DisarrayList;
import recursiveMethods.RecursionClass;
import java.util.Arrays;
import org.junit.*;
import static org.junit.Assert.*;

public class PublicTests {

  // Tests one case of elementAfter().
  @Test public void testPublic1() {
    DisarrayList<Integer> list= new DisarrayList<>(Arrays.asList(1, 3, 2));

    assertEquals(3, (int) RecursionClass.elementAfter(list, 1));
  }

  // Tests one case of areReversed().
  @Test public void testPublic2() {
    DisarrayList<Integer> list1=
      new DisarrayList<>(Arrays.asList(8, 1, 9, 5, 4, 7, 5, 6));
    DisarrayList<Integer> list2=
      new DisarrayList<>(Arrays.asList(6, 5, 7, 4, 5, 9, 1, 8));

    assertTrue(RecursionClass.areReversed(list1, list2));
  }

  // Tests one case of replaceNthOccurrence().
  @Test public void testPublic3() {
    DisarrayList<Character> list=
      new DisarrayList<>(Arrays.asList('s', 'l', 'e', 'e', 'p'));

    RecursionClass.replaceNthOccurrence(list, 'l', 'h', 1);

    assertEquals("s h e e p", list.toString());
  }

  // Tests one case of omitAfter().
  @Test public void testPublic4() {
    DisarrayList<Integer> list=
      new DisarrayList<>(Arrays.asList(8, 1, 9, 5, 4, 7, 5, 6));

    assertEquals("8 1 9 4 7 5 6",
                 RecursionClass.omitAfter(list, 9).toString());
  }

}
