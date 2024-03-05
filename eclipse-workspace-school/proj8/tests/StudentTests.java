package tests;

import graphDir.GraphDir;
import java.util.Arrays;
import java.util.List;
import org.junit.*;
import static org.junit.Assert.*;

public class StudentTests {

	/*
	 * makes sure vertices and edges are added properly in both the vertexAdd
	 * and edgeAdd functions
	 * Also checks the functionality of getVertices()
	 */
	@Test public void studentTest1() {
		GraphDir<Integer> graph = new GraphDir<>();
		
		graph.vertexAdd(1);
		graph.vertexAdd(2);
		graph.vertexAdd(3);
		//graph.vertexAdd(4);
		//graph.vertexAdd(5);
		//graph.vertexAdd(6);
		
		graph.edgeAdd(1, 2, 10);
		graph.edgeAdd(2, 4, 7);
		graph.edgeAdd(3, 5, 8);
		graph.edgeAdd(4, 3, 3);
		graph.edgeAdd(5, 2, 5);
		graph.edgeAdd(6, 1, 9);
		graph.edgeAdd(1, 5, 1);
		graph.edgeAdd(2, 6, 4);
		graph.edgeAdd(3, 3, 10);
		graph.edgeAdd(4, 6, 6);
		graph.edgeAdd(5, 4, 3);
		graph.edgeAdd(6, 5, 11);
		
		assertTrue(graph.vertexCheck(1));
		assertTrue(graph.vertexCheck(5));
		assertEquals(graph.getVertices().size(), 6);
	}

	
	/*
	 * Checks to make sure graph is directed and that a weight is updated after
	 * a second edgeAdd() call of the same vertexFrom and vertexTo values
	 */
	@Test public void studentTest2() {
		GraphDir<Integer> graph = new GraphDir<>();
		graph.vertexAdd(1);
		graph.vertexAdd(2);
		
		graph.edgeAdd(1, 2, 10);
		assertEquals(graph.edgeGet(1, 2), 10);
		assertEquals(graph.edgeGet(2, 1), -1);
		
		graph.edgeAdd(1, 2, 6);
		assertEquals(graph.edgeGet(1, 2), 6);
	}
	
	
	/*
	 * Checks functionality of edgeRemove function makes sure it both removes 
	 * the proper edge, and its return value for a call on a nonexistent edge
	 */
	@Test public void studentTest3() {
		GraphDir<Integer> graph = new GraphDir<>();
		graph.vertexAdd(1);
		graph.vertexAdd(2);
		
		graph.edgeAdd(1, 2, 10);
		assertEquals(graph.edgeGet(1, 2), 10);
		
		graph.edgeRemove(1, 2);
		assertEquals(graph.edgeGet(1, 2), -1);
		
		assertFalse(graph.edgeRemove(1, 2));
	}
	
	
	/*
	 * Tests the functionality of the vertexDelete method and makes sure it has
	 * the right return value on calling it with a vertex not part of the graph
	 */
	@Test public void studentTest4() {
		GraphDir<Integer> graph = new GraphDir<>();

		graph.vertexAdd(1);
		graph.vertexAdd(2);
		graph.vertexAdd(3);
		graph.vertexAdd(4);
		graph.vertexAdd(5);
		graph.vertexAdd(6);
		
		graph.edgeAdd(1, 2, 10);
		graph.edgeAdd(2, 4, 7);
		graph.edgeAdd(3, 5, 8);
		graph.edgeAdd(4, 3, 3);
		graph.edgeAdd(5, 2, 5);
		graph.edgeAdd(6, 1, 9);
		graph.edgeAdd(1, 5, 1);
		graph.edgeAdd(2, 6, 4);
		graph.edgeAdd(3, 3, 10);
		graph.edgeAdd(4, 6, 6);
		graph.edgeAdd(5, 4, 3);
		graph.edgeAdd(6, 5, 11);
		
		assertEquals(graph.getVertices().size(), 6);
		
		graph.vertexDelete(3);
		assertEquals(graph.getVertices().size(), 5);
		
		assertFalse(graph.vertexDelete(3));
	}
	
	
	/*
	 * Tests the functionality of the vertexNeighbors and edgeContract functions
	 * Makes sure edge contract gives the proper weights to any weights
	 * associated with it
	 */
	@Test public void studentTest5() {
		GraphDir<Integer> graph = new GraphDir<>();

		graph.vertexAdd(1);
		graph.vertexAdd(2);
		graph.vertexAdd(3);
		graph.vertexAdd(4);
		graph.vertexAdd(5);
		graph.vertexAdd(6);
		
		graph.edgeAdd(1, 2, 10);
		graph.edgeAdd(2, 4, 7);
		graph.edgeAdd(3, 5, 8);
		graph.edgeAdd(4, 3, 3);
		graph.edgeAdd(5, 2, 5);
		graph.edgeAdd(6, 1, 9);
		graph.edgeAdd(1, 5, 1);
		graph.edgeAdd(2, 6, 6);
		graph.edgeAdd(3, 3, 10);
		graph.edgeAdd(4, 6, 4);
		graph.edgeAdd(5, 4, 3);
		graph.edgeAdd(6, 5, 11);
		
		assertEquals(graph.getVertices().size(), 6);
		
		graph.edgeContract(2, 4);
		assertEquals(graph.getVertices().size(), 5);
		
		assertEquals(graph.vertexNeighbors(2).size(), 2);
		assertEquals(graph.edgeGet(2, 6), 4);
	}
}
