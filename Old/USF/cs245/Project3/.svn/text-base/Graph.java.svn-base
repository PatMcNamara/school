import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import java.util.Scanner;
/**
 * Can represent either a directed or undirected graph, used by 
 * ConnectedComponents and Euler classes.
 * @author pjmcnamara2
 *
 * @param <T>
 */
public class Graph <T> {
	ArrayList<MyList<Integer>> adjList; // Adjacency List
	ArrayList<T> stringMap; // Maps vertex number to strings
	Map<String, Integer> hash; // Hash Table maps string to vertex number
	boolean isDirected; // Is this graph directed?
	
	Graph(boolean directed){
		adjList = new ArrayList<MyList<Integer>>();
		stringMap = new ArrayList<T>();
		hash = new MyHashTable();
		isDirected = directed;
	}
	
	/**
	 * Adds a vertex to the graph with no edges.  You will never have to call
	 *  this method because this class will ensure both vertexes of an edge
	 *  exist before adding the edge, but it can be used in debugging so user
	 *  can set numbering of vertex.
	 * @param vertex
	 * @return
	 */
	boolean addVertex(T vertex) {
		if(!hash.containsKey(vertex)) {
			stringMap.add(vertex);
			hash.put((String) vertex, stringMap.size() - 1);
			adjList.add(new MyList<Integer>());
			return true;
		}
		return false;
	}
	
	/**
	 * Add directed or undirected edge to graph.
	 * @param startVertex
	 * @param endVertex
	 * @return
	 */
	boolean addEdge(T startVertex, T endVertex) { 
		addVertex(startVertex);
		addVertex(endVertex);
		
		int start = hash.get(startVertex), 
		    end = hash.get(endVertex);

		boolean wasAdded = addDirectedEdge(start, end);
		if(!isDirected)
			wasAdded |= addDirectedEdge(end, start);
		return wasAdded;
	}
	
	/**
	 * Adds a single directed edge to the graph.  This is used even in
	 *  undirected graphs, the difference being that addEdge will call this
	 *  method twice for each edge inserted into an undirected edge.
	 * @param startVertex
	 * @param endVertex
	 * @return
	 */
	private boolean addDirectedEdge(int startVertex, int endVertex){
		List<Integer> list = adjList.get(startVertex);
		if(!list.contains(endVertex))
			return list.add(endVertex);
		return false;
	}
	
	boolean removeVertex(T vertex){
		if(adjList.set(hash.get(vertex), null) == null)
			return false;
		return true;
	}
	/**
	 * Same as addEdge but removes edge instead of adding it. 
	 * @param startVertex
	 * @param endVertex
	 * @return
	 */
	boolean removeEdge(T startVertex, T endVertex) {
		int start = hash.get(startVertex),
		    end = hash.get(endVertex);
		
		boolean wasAdded = removeDirectedEdge(start, end);
		if(!isDirected)
			wasAdded |= removeDirectedEdge(end, start);
		return wasAdded;
	}
	/**
	 * Same as addDirectedEdge but removes edge instead
	 * @param startVertex
	 * @param endVertex
	 * @return
	 */
	boolean removeDirectedEdge(int startVertex, int endVertex) {
		List<Integer> list = adjList.get(startVertex);
		return list.remove(new Integer(endVertex));
	}
	
	/**
	 * Sets current graph to its transpose.  This will not have an effect if
	 *  if this graph is directed.
	 */
	void transpose(){
		ArrayList<MyList<Integer>> tmp = new ArrayList<MyList<Integer>>(adjList.size());
		// add empty vertices to new list
		for(int i = 0; i < adjList.size(); i++)
			tmp.add(new MyList<Integer>());
		// add reverse of each edge into new list
		for(int i = 0; i < adjList.size(); i++)
			for(int j: adjList.get(i))
				tmp.get(j).add(i);
		// set old list to new list
		adjList = tmp;
	}
	
	List<T> getOutgoingEdges(T vertex) {
		List<T> edges = new MyList<T>();
		for(int outGoing: adjList.get(hash.get(vertex)))
			edges.add(stringMap.get(outGoing));
		return edges;
	}
	/**
	 * Returns a single out going edge or null if there are none.
	 * @param vertex
	 * @return
	 */
	T getOutgoingEdge(T vertex){
		List<T> tmp = getOutgoingEdges(vertex);
		return tmp.isEmpty() ? null : tmp.get(0);
	}
	
	/**
	 * Calculates total number of edges.
	 * @return
	 */
	int totalNumberOfEdges(){
		int i = 0;
		for(MyList vertex: adjList)
			i += vertex.size();
		return isDirected ? i : i/2;
	}
	
	/**
	 * Parses text file to build graph based on that text file.
	 * @param textFile
	 */
	void parseFile(Scanner textFile) {
		do {
			String[] s = textFile.nextLine().split(" ");
			addEdge((T) s[0], (T) s[1]);
		} while(textFile.hasNextLine());
	}
	
	public String toString() {
		String retVar = "";
		for(int i = 0; i < adjList.size(); i++) {
			retVar += "\n" + stringMap.get(i) + ":";
			for(int j: adjList.get(i))
				retVar += " " + stringMap.get(j);
		}
		return retVar;
	}
}