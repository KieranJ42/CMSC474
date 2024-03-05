package socialNetwork;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GraphDir<V extends Comparable<V>> {

	/*
	 * The graph is stored using the following variable
	 * Each vertex value is mapped to another map of vertex values and the 
	 * weight that is associated with the edge of the two vertices
	 */
	private Map<V, HashMap<V, Integer>> graph;

	//constructor to initialize our graph variable
	public GraphDir() {
		graph = new HashMap<>();
	}

	/*
	 * If the vertex already exists in the graph, return false
	 * Otherwise add the vertex to the graph and return true
	 */
	public boolean vertexAdd(V vertexInfo) {
		if (vertexInfo == null)
			throw new IllegalArgumentException();
		
		if (vertexCheck(vertexInfo))
			return false;
		graph.put(vertexInfo, new HashMap<>());
		return true;
	}

	/*
	 * returns true if the vertex is in the graph
	 */
	public boolean vertexCheck(V vertexInfo) {
		if (vertexInfo == null)
			throw new IllegalArgumentException();
		
		return graph.containsKey(vertexInfo);
	}

	/*
	 * returns a collection of all the vertices in the graph
	 */
	public Collection<V> getVertices() {
		//new keyword and copy constructor ensures the set is not connected to
		//our graph
		return new HashSet(graph.keySet());
	}

	/*
	 * adds an edge between two vertices with a given weight
	 */
	public boolean edgeAdd(V vertexFrom, V vertexTo, int weight) {
		if (vertexFrom == null || vertexTo == null)
			throw new IllegalArgumentException();
		
		if (weight < 0)
			return false;
		
		
		//adds the vertices if they don't already exist in the graph
		if (!vertexCheck(vertexFrom))
			vertexAdd(vertexFrom);
		if (!vertexCheck(vertexTo))
			vertexAdd(vertexTo);
		
		//adds the edge
		graph.get(vertexFrom).put(vertexTo, weight);
		return true;
	}

	/*
	 * returns the weight of an edge between to vertices
	 * returns -1 if the edge doesn't exist
	 */
	public int edgeGet(V vertexFrom, V vertexTo) {
		if (vertexFrom == null || vertexTo == null)
			throw new IllegalArgumentException();
		
		if (graph.containsKey(vertexFrom) && graph.containsKey(vertexTo)) {
			if (graph.get(vertexFrom).containsKey(vertexTo))
				return graph.get(vertexFrom).get(vertexTo);
		} 
		return -1;
	}

	/*
	 * removes an edge from two vertices
	 */
	public boolean edgeRemove(V vertexFrom, V vertexTo) {
		if (vertexFrom == null || vertexTo == null)
			throw new IllegalArgumentException();
		
		//if the vertices aren't in the graph return false
		if (graph.containsKey(vertexFrom) && graph.containsKey(vertexTo)) {
			
			//if the edge doesn't exist return false
			if (graph.get(vertexFrom).containsKey(vertexTo)) {
				
				//edge exists so we return the weight
				graph.get(vertexFrom).remove(vertexTo);
				return true;
			}
		}
		return false;
	}

	/*
	 * deletes a vertex and all incoming and outgoing edges for the vertex
	 */
	public boolean vertexDelete(V vertexInfo) {
		if (vertexInfo == null)
			throw new IllegalArgumentException();
		
		//if the edge doesn't exist return false
		if (graph.containsKey(vertexInfo)) {
			
			//remove the vertex itself
			graph.remove(vertexInfo);
			
			//remove all edges that point to the vertex we want to delete
			Collection<HashMap<V, Integer>> values = graph.values();
			for (HashMap<V, Integer> value : values)
				value.remove(vertexInfo);
			return true;
		}
		return false;
	}

	/*
	 * returns a collection of the neighbors a specific vertex has edges
	 * that point to 
	 */
	public Collection<V> vertexNeighbors(V vertexInfo) {
		if (vertexInfo == null)
			throw new IllegalArgumentException();
		
		if (graph.containsKey(vertexInfo)) 
			return new HashSet(graph.get(vertexInfo).keySet());
		return null;
	}

	/*
	 * Combines two edges if they share at least one edge in either direction
	 * Uses the key between the two with the smallest compareTo value
	 * Makes all edges to and from the two vertices that need to combine
	 * the smaller of the weights between the two edge options
	 */
	public boolean edgeContract(V vertex1, V vertex2) {
		if (vertex1 == null || vertex2 == null)
			throw new IllegalArgumentException();
		
		//return false if at least one of the vertices doesn't exist
		if (graph.containsKey(vertex1) && graph.containsKey(vertex2)) {
			
			//returns false if there is no edge between the vertices
			if (graph.get(vertex1).containsKey(vertex2) || 
					graph.get(vertex2).containsKey(vertex1)) {
				
				//gets the smaller of the two vertices
				V smallerVertex = vertex1.compareTo(vertex2) < 0 ? vertex1 :
					vertex2;
				
				//Combine self-edges
				Integer weight = smallestWeight(vertex1, vertex1, vertex2,
						vertex2);
				if (weight != null)
					graph.get(smallerVertex).put(smallerVertex, weight);
				
				//Combine outgoing edges
				for (V vertex : getVertices()) {
					if (!vertex.equals(vertex1) && !vertex.equals(vertex2)) {
						weight = smallestWeight(vertex1, vertex, vertex2,
								vertex);
						if (weight != null)
							graph.get(smallerVertex).put(vertex, weight);
					}	
				}
				
				//Combine incoming edges
				for (V vertex : getVertices()) {
					if (!vertex.equals(vertex1) && !vertex.equals(vertex2)) {
						weight = smallestWeight(vertex, vertex1, vertex,
								vertex2);
						if (weight != null)
							graph.get(vertex).put(smallerVertex, weight);
					}
				}
				
				//delete the larger vertex now that everything was added
				//to the smaller one
				vertexDelete(vertex1.compareTo(vertex2) > 0 ? vertex1 :
					vertex2);
				
				return true;
			}
		}
		return false;
	}
	
	/*
	 * helper method for the edgeContract method that returns the smaller
	 * weight between two edges
	 */
	private Integer smallestWeight(V fromV1, V toV1, V fromV2, V toV2) {
		int temp1 = edgeGet(fromV1, toV1);
		int temp2 = edgeGet(fromV2, toV2);
		if (temp1 == -1 && temp2 == -1)
			return null;
		if (temp1 == -1)
			return temp2;
		if (temp2 == -1)
			return temp1;
		return temp1 < temp2 ? temp1 : temp2;
	}

}
