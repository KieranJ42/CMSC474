package linkyList;

import java.util.Arrays;
import java.util.Iterator;

public class IteratorDemo {

  public static void main(String[] args) {
    LinkyList<String> list= new LinkyList<>();
    Iterator<String> iter;

    // add words
    for (String word : Arrays.asList("lambs", "are", "little", "young",
                                     "cute", "sheep"))
      list.orderedInsert(word);

    // get iterator
    iter= list.iterator();

    // iterate over iterator removing elements from the list in the process
    while (iter.hasNext())
      list.elementRemove(iter.next());

    // now the list should be empty
    if (list.listNumElements() == 0)
      System.out.println("Success!  The iterator worked!");
    else System.out.println("Fail!  The iterator (or the list itself) is " +
                            "borked!");
  }

}
