package tests;

// (c) Larry Herman, 2021.  You are allowed to use this code yourself, but
// not to provide it to anyone else.

// This class contains a helper method used in the public tests, and example
// graphs that the tests call methods on.

import graphDir.GraphDir;
import java.util.Arrays;
import java.util.List;
import java.util.Collection;

public class TestData {

  // utility methods ////////////////////////////////////////////////////

  // In various tests we have to check the contents of a Collection returned
  // by a method, but we can't create a Collection that has the expected
  // values and use the equals() method to compare against the Collection,
  // because we don't even know what kind of Collection the methods will
  // return.  This method takes a Collection to check and another Collection
  // with the expected values (this Collection will actually be some type of
  // List).  Then it uses the Collection containsAll() method to compare the
  // two parameter Collections.  If we have two collections A and B, and A
  // contains all of the elements of B, and B contains all of the elements
  // of A, then it must be the case that they must have all the same
  // elements, and only the same elements.  Of course this is not
  // particularly efficient, but our goal is just to make it easy to check
  // the results of tests.
  public static <T> boolean compareCollections(Collection<T> collection,
                                               Collection<T> expected) {
    return collection.containsAll(expected) &&
           expected.containsAll(collection);
  }

  // At times we want to check all of the components of a graph.  This
  // method takes a graph and checks its components using the remaining
  // parameters.  The vertices of the graph should be equal to the values in
  // the second parameter.  The neighbors of each vertex should be equal to
  // the values in the third parameter, where the neighbors of the i'th
  // vertex in the parameter vertices should be the i'th list in the
  // parameter neighbors.  The costs of the edges are represented by the
  // last parameter costs, where the i'th element of costs is the costs of
  // the edges to the i'th vertex in the parameter vertices, and the costs
  // should be in the same order as the neighbors are in the parameter
  // neighbors.
  //
  // Note that you don't need to understand the code to use this method in
  // your own tests, as long as you understand what it's doing and what to
  // pass into it.  (Take a look at the public test that calls it.)
  //
  // This method relies on your getVertices() and getNeighbors() methods
  // working right, because it calls them.
  public static <T extends Comparable<T>> boolean
           compareGraph(GraphDir<T> graph, List<T> vertices,
                        List<List<T>> neighbors, List<List<Integer>> costs) {
    T curVertex, curNeighbor;
    boolean result;
    int i, j;

    // first check the vertices
    result= compareCollections(vertices, graph.getVertices());

    // next check the neighbors of each vertex; the i'th vertex in the
    // second parameter should have the neighbors that are in the i'th
    // list of the third parameter
    i= 0;
    while (i < vertices.size() && result) {
      result= result &&
              compareCollections(neighbors.get(i),
                                graph.vertexNeighbors(vertices.get(i)));
      i++;
    }

    // lastly, check the costs of the edges to the neighbors of each vertex
    i= 0;
    while (i < vertices.size() && result) {
      // for each vertex, which is the i'th element of the second parameter
      curVertex= vertices.get(i);

      j= 0;
      while (j < neighbors.get(i).size() && result) {
        // for each neighbor of each vertex, which is element j of the
        // i'th list of the third parameter
        curNeighbor= neighbors.get(i).get(j);
        // get the cost for that neighbor of that vertex from element j
        // of the i'th list of the fourth parameter
        result= result && (costs.get(i).get(j) ==
                           graph.edgeGet(curVertex, curNeighbor));
        j++;
      }

      i++;
    }

    return result;
  }

  // example graphs /////////////////////////////////////////////////////

  // Returns a very simple graph instantiated with Strings that has vertices
  // but no edges.
  public static GraphDir<String> exampleGraph1() {
    GraphDir<String> graph= new GraphDir<>();

    // add vertices to the graph
    for (String vertex : Arrays.asList("sheep", "beagle", "cat",
                                       "donkey", "elephant", "flamingo",
                                       "giraffe", "hyena", "iguana",
                                       "jaguar", "koala", "lemur",
                                       "manatee", "numbat", "ocelot"))
      graph.vertexAdd(vertex);

    return graph;
  }

  // Returns a simple graph instantiated with Characters.
  public static GraphDir<Character> exampleGraph2() {
    GraphDir<Character> graph= new GraphDir<>();
    char[] vertices= {'e', 'd', 'u', 'c', 'a', 't', 'i', 'o', 'n'};
    int i;

    // add vertices to the graph
    for (char vertex : vertices)
      graph.vertexAdd(vertex);

    // add some edges to the graph; the vertex corresponding to every
    // element in the array above has an edge to the next one in the array,
    // except for the last one
    for (i= 0; i < vertices.length - 1; i++)
      graph.edgeAdd(vertices[i], vertices[i + 1], 1);

    return graph;
  }

  // Returns a more complex graph instantiated with Strings.
  public static GraphDir<String> exampleGraph3() {
    GraphDir<String> graph= new GraphDir<>();
    String[] vertices= {"aardvark", "beagle", "cat", "donkey", "elephant",
                        "flamingo", "giraffe", "hyena", "iguana", "jaguar",
                        "koala", "lemur", "manatee", "numbat", "ocelot"};
    int i, j;

    // add vertices to the graph
    for (i= 0; i < vertices.length; i++)
      graph.vertexAdd(vertices[i]);

    // add some edges to the graph
    for (i= 0; i < vertices.length - 1; i++)
      for (j= i + 1; j < vertices.length; j++)
        graph.edgeAdd(vertices[i], vertices[j], 1);

    return graph;
  }

  // Returns another more complex graph instantiated with Integers.
  public static GraphDir<Integer> exampleGraph4() {
    GraphDir<Integer> graph= new GraphDir<>();
    int[] from= {1, 1, 1, 1, 2, 3, 5, 6, 6, 6, 7, 7, 8, 8, 8};
    int[] to=   {2, 3, 4, 5, 4, 4, 4, 3, 4, 5, 3, 6, 4, 6, 7};
    int i;

    for (i= 1; i <= 8; i++)
      graph.vertexAdd(i);

    for (i= 0; i < from.length; i++)
      graph.edgeAdd(from[i], to[i], from.length - i);

    return graph;
  }

  // Returns another more complex graph instantiated with Characters.
  public static GraphDir<Character> exampleGraph5() {
    GraphDir<Character> graph= new GraphDir<>();

    // add vertices to the graph
    for (char vertex : new Character[]{'f', 'r', 'o', 'g',
                                       'c', 'a', 'm', 'e', 'l'})
      graph.vertexAdd(vertex);

    // add some edges to the graph
    graph.edgeAdd('f', 'o', 2);
    graph.edgeAdd('o', 'g', 2);
    graph.edgeAdd('g', 'r', 2);
    graph.edgeAdd('r', 'o', 2);
    graph.edgeAdd('r', 'f', 2);
    graph.edgeAdd('c', 'a', 2);
    graph.edgeAdd('c', 'e', 2);
    graph.edgeAdd('a', 'e', 2);
    graph.edgeAdd('e', 'c', 2);
    graph.edgeAdd('e', 'm', 2);
    graph.edgeAdd('m', 'l', 2);
    graph.edgeAdd('l', 'c', 2);
    graph.edgeAdd('l', 'e', 2);

    return graph;
  }

}
