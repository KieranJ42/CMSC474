package tests;

// (c) Larry Herman, 2021.  You are allowed to use this code yourself, but
// not to provide it to anyone else.

import graphDir.GraphDir;
import java.util.Arrays;
import java.util.List;
import org.junit.*;
import static org.junit.Assert.*;

public class PublicTests {

  /* A few tests use a method compareCollections() defined in the TestData
   * class that checks a Collection to see if it contains expected contents.
   * See the comments before it in the TestData class for more info.  One
   * test uses a method compareGraph(), also in the TestData class.  It is
   * passed a graph and some other parameters representing the expected
   * components of the graph, and it compares to see if the graph's
   * components match the parameter values.  Again, see the comments before
   * it in the TestData class for explanation.
   *
   * You can use both of these methods in your own student tests as needed,
   * if you take a few moments first to understand what they are doing.
   * (What this means is to understand what these methods are doing and how
   * to call them- it's not necessary to understand their code to use these
   * helper methods in your own tests, as long as you understand what
   * they're doing and what to pass into them.)
   */

  // Tests calling vertexCheck() on the vertices of a simple graph that has
  // vertices but no edges, and calling vertexAdd() on an
  // already-existing vertex.
  @Test public void testPublic1() {
    GraphDir<String> graph= TestData.exampleGraph1();

    for (String vertex : Arrays.asList("sheep", "beagle", "cat",
                                       "donkey", "elephant", "flamingo",
                                       "giraffe", "hyena", "iguana",
                                       "jaguar", "koala", "lemur",
                                       "manatee", "numbat", "ocelot"))
      assertTrue(graph.vertexCheck(vertex));

    // test the return value of vertexAdd() in the normal case
    assertTrue(graph.vertexAdd("parrot"));
    assertTrue(graph.vertexCheck("parrot"));

    assertFalse(graph.vertexAdd("parrot"));
  }

  // Tests calling getVertices() on an empty graph.
  @Test public void testPublic2() {
    GraphDir<Character> graph= new GraphDir<>();

    assertEquals(0, graph.getVertices().size());
  }

  // Tests calling vertexCheck() on an empty graph and on a nonexistent vertex,
  // and calling getVertices() on a graph that contains vertices but no
  // edges.
  @Test public void testPublic3() {
    GraphDir<String> graph= new GraphDir<>();

    assertFalse(graph.vertexCheck("zebra"));
    graph= TestData.exampleGraph1();
    assertFalse(graph.vertexCheck("zebra"));

    assertTrue(TestData.compareCollections(
                 Arrays.asList("sheep", "beagle", "cat", "donkey",
                               "elephant", "flamingo", "giraffe", "hyena",
                               "iguana", "jaguar", "koala", "lemur",
                               "manatee", "numbat", "ocelot"),
                 graph.getVertices()));
  }

  // Tests calling vertexDelete() to remove some vertices from a graph that
  // contains vertices but no edges.
  @Test public void testPublic4() {
    GraphDir<String> graph= TestData.exampleGraph1();
    int pos= 1;

    // remove half of the current vertices
    for (String vertex : Arrays.asList("sheep", "cat", "elephant",
                                       "giraffe", "iguana", "koala",
                                       "manatee", "ocelot"))
      assertTrue(graph.vertexDelete(vertex));

    for (String vertex : Arrays.asList("sheep", "beagle", "cat",
                                       "donkey", "elephant", "flamingo",
                                       "giraffe", "hyena", "iguana",
                                       "jaguar", "koala", "lemur",
                                       "manatee", "numbat", "ocelot")) {
      // every other vertex was removed from this list of original vertex
      // names, so test alternating vertices for presence and absence
      if (pos % 2 == 1)
        assertFalse(graph.vertexCheck(vertex));
      else assertTrue(graph.vertexCheck(vertex));

      pos++;
    }
  }

  // Tests calling edgeAdd() and edgeGet() in normal cases.
  @Test public void testPublic5() {
    GraphDir<Character> graph= TestData.exampleGraph2();  // calls edgeAdd()
    char[] vertices= {'e', 'd', 'u', 'c', 'a', 't', 'i', 'o', 'n'};
    int i;

    for (i= 0; i < vertices.length - 1; i++)
      assertEquals(1, graph.edgeGet(vertices[i], vertices[i + 1]));
  }

  // Tests calling edgeAdd() to try to create an edge with negative weight,
  // which should fail, and to try to add already-existing edge, which
  // should succeed and change the edge's weight.
  @Test public void testPublic6() {
    // this is going to be a very small graph with only two vertices, "true"
    // and "false"
    GraphDir<Boolean> graph= new GraphDir<>();

    graph.vertexAdd(true);
    graph.vertexAdd(false);

    assertTrue(graph.edgeAdd(true, false, 132));
    assertFalse(graph.edgeAdd(false, true, -1));
    assertTrue(graph.edgeAdd(true, false, 216));
    assertEquals(216, graph.edgeGet(true, false));
  }

  // Tests calling edgeGet() on existing vertices that don't have an edge
  // between them.
  @Test public void testPublic7() {
    GraphDir<String> graph= TestData.exampleGraph3();
    String[] vertices= {"sheep", "beagle", "cat", "donkey", "elephant",
                        "flamingo", "giraffe", "hyena", "iguana", "jaguar",
                        "koala", "lemur", "manatee", "numbat", "ocelot"};
    int i, j;

    for (i= vertices.length - 1; i >= 0; i--)
      for (j= i; j >= 0; j--)
        assertEquals(-1, graph.edgeGet(vertices[i], vertices[j]));
  }

  // Tests that edgeAdd() will create an edge with zero weight, and that
  // it only creates an edge in the correct direction, not one in the
  // opposite direction as well.
  @Test public void testPublic8() {
    GraphDir<Character> graph= TestData.exampleGraph5();

    assertTrue(graph.edgeAdd('r', 'a', 0));

    assertEquals(0, graph.edgeGet('r', 'a'));
    assertEquals(-1, graph.edgeGet('a', 'r'));
  }

  // Tests calling edgeAdd() to create an edge from a vertex to itself,
  // which is fine.
  @Test public void testPublic9() {
    GraphDir<Character> graph= new GraphDir<>();

    assertTrue(graph.vertexAdd('a'));
    assertTrue(graph.edgeAdd('a', 'a', 1));
    assertEquals(1, graph.edgeGet('a', 'a'));
  }

  // Tests creating a larger graph with a lot of intermixed insertions and
  // deletions of vertices and insertions of edges.
  @Test public void testPublic10() {
    GraphDir<Character> graph= new GraphDir<>();
    char[] vertices= {'e', 'd', 'u', 'c', 'a', 't', 'i', 'o', 'n'};
    int i;

    // add vertices to the graph
    for (char vertex : vertices)
      graph.vertexAdd(vertex);

    for (i= 0; i < vertices.length - 1; i++)
      graph.edgeAdd(vertices[i], vertices[i + 1], 1);

    assertTrue(graph.edgeAdd('e', 'c', 1));
    assertTrue(graph.edgeAdd('a', 't', 1));
    assertTrue(graph.vertexDelete('a'));
    assertFalse(graph.vertexCheck('a'));
    assertTrue(graph.vertexAdd('a'));
    assertTrue(graph.vertexCheck('a'));
    assertTrue(graph.edgeAdd('a', 't', 1));
    assertTrue(graph.vertexCheck('a'));
    assertTrue(graph.edgeAdd('d', 'u', 3));
    assertTrue(graph.vertexDelete('c'));
    assertTrue(graph.edgeAdd('d', 'u', 3));
    assertTrue(graph.edgeAdd('o', 'e', 3));
    assertFalse(graph.edgeAdd('i', 'd', -3));
    assertEquals(8, graph.getVertices().size());
  }

  // Tests calling vertexDelete() on a vertex that has outgoing edges.
  @Test public void testPublic11() {
    GraphDir<Integer> graph= TestData.exampleGraph4();

    assertTrue(graph.vertexDelete(8));
    assertFalse(graph.vertexCheck(8));
  }

  // Tests calling vertexDelete() on a vertex that has both outgoing and
  // incoming edges.
  @Test public void testPublic12() {
    GraphDir<Integer> graph= TestData.exampleGraph4();

    assertTrue(graph.vertexDelete(6));
    assertFalse(graph.vertexCheck(6));
    assertEquals(-1, graph.edgeGet(6, 3));
    assertEquals(-1, graph.edgeGet(6, 4));
    assertEquals(-1, graph.edgeGet(6, 5));
    assertEquals(-1, graph.edgeGet(7, 6));
    assertEquals(-1, graph.edgeGet(8, 6));
  }

  // Tests calling vertexDelete() to remove all of the vertices from a graph
  // (that contains vertices but no edges) and then adds some new vertices.
  @Test public void testPublic13() {
    GraphDir<String> graph= TestData.exampleGraph1();
    String[] vertices= {"sheep", "beagle", "cat", "donkey", "elephant",
                        "flamingo", "giraffe", "hyena", "iguana",
                        "jaguar", "koala", "lemur", "manatee", "numbat",
                        "ocelot"};
    String[] newVertices= {"penguin", "quokka", "rhinoceros",
                           "salamander", "turtle", "umbrellabird",
                           "vulture", "wallaby", "beagle", "koala"};

    for (String vertex : vertices)
      graph.vertexDelete(vertex);

    for (String vertex : vertices)
      assertFalse(graph.vertexCheck(vertex));

    for (String vertex : newVertices)
      graph.vertexAdd(vertex);

    for (String vertex : newVertices)
      assertTrue(graph.vertexCheck(vertex));
  }

  // Tests calling vertexNeighbors() in the normal case.
  @Test public void testPublic14() {
    GraphDir<Integer> graph= TestData.exampleGraph4();
    // a List of Lists of integers
    List<List<Integer>> expectedNeighbors=
      Arrays.asList(
        Arrays.asList(2, 3, 4, 5),  // neighbors of 1
        Arrays.asList(4),           // neighbors of 2
        Arrays.asList(4),           // neighbors of 3
        Arrays.asList(),            // neighbors of 4
        Arrays.asList(4),           // neighbors of 5
        Arrays.asList(3, 4, 5),     // neighbors of 6
        Arrays.asList(3, 6),        // neighbors of 7
        Arrays.asList(4, 6, 7)      // neighbors of 8
    );
    int i;

    // normal cases
    for (i= 0; i < expectedNeighbors.size(); i++)
      assertTrue(TestData.compareCollections(expectedNeighbors.get(i),
                                             graph.vertexNeighbors(i + 1)));
  }

  // Tests calling vertexNeighbors() on a nonexistent vertex.
  @Test public void testPublic15() {
    GraphDir<Character> graph= TestData.exampleGraph2();

    assertNull(graph.vertexNeighbors('x'));
  }

  // Tests calling vertexAdd() and vertexCheck() with null arguments.  Using
  // exceptions we can test that more than one expected exception is thrown
  // in the same test.
  @Test public void testPublic16() {
    GraphDir<Character> graph= new GraphDir<>();

    try {
      graph.vertexAdd(null);
      // if we reach here- meaning if the expected exception was not thrown-
      // the test should fail
      fail();
    } catch (IllegalArgumentException e) {
      // if we get here the expected exception was thrown, so continue to
      // the next thing we want to test
    }

    try {
      graph.vertexCheck(null);
      // if we reach here- meaning if the expected exception was not thrown-
      // the test should fail
      fail();
    } catch (IllegalArgumentException e) {
      // if we get here the expected exception was thrown, so continue to
      // the next thing we want to test
    }

    assertEquals(0, graph.getVertices().size());
  }

  // Tests the basic operation of edgeContract() for a simple case of a very
  // small graph.
  @Test public void testPublic17() {
    GraphDir<Integer> graph= new GraphDir<>();

    graph.vertexAdd(132);
    graph.vertexAdd(216);
    graph.edgeAdd(132, 216, 75);

    graph.edgeContract(132, 216);

    // now the graph has only one vertex, 132, which has no neighbors
    assertTrue(TestData.compareCollections(Arrays.asList(132),
                                           graph.getVertices()));
    assertEquals(0, graph.vertexNeighbors(132).size());
  }

}
