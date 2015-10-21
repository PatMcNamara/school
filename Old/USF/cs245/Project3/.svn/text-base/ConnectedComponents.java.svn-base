import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Finds the connected components in a graph.
 * @author pjmcnamara2
 *
 */
public class ConnectedComponents {
	Graph<String> graph = new Graph<String>(true);
	List<Integer> fTimes = new MyList<Integer>(); // Finishing times of DFS
	boolean verbose;
	
	ConnectedComponents(boolean verbose) {
		this.verbose = verbose;
	}

	/**
	 * Calculate finishing times of graph and inserts the nodes into fTimes List
	 *  with lowest finishing times first.  Uses helper finishingTime recursive
	 *  method to calculate finishing times.  This method calls finishingTimes
	 *  with the first vertex, then calls it again with the next edge that
	 *  wasn't covered the previous times until it runs out of edges.
	 */
	void finishingTimes(){
		// Save a copy of adj list since this operation is destructive
		ArrayList<MyList<Integer>> copy = 
				new ArrayList<MyList<Integer>>(graph.adjList);
		
		for(int i=0; i < graph.adjList.size(); i++){
			List<Integer> tmp = graph.adjList.get(i);
			if(tmp != null){
				graph.adjList.set(i, null);
				finishingTimes(tmp);
				fTimes.add(i);
			}
		}
		graph.adjList = copy;
	}
	/**
	 * Helper recursive method used by finishingTimes()
	 * @param list  Vertexes that can be reached from this vertex
	 */
	private void finishingTimes(List<Integer> list){
		for(int i: list) {
			List<Integer> tmp = graph.adjList.get(i);
			if(tmp != null){
				graph.adjList.set(i, null);
				finishingTimes(tmp);
				fTimes.add(i);
			}
		}
	}
	/**
	 * Recursive Depth First Search starting at specified vertex.
	 * @param vertex
	 * @return
	 */
	MyList<Integer> DFS(int vertex) {
		MyList<Integer> retVal = new MyList<Integer>();
		// Base case
		if(graph.adjList.get(vertex) == null)
			return retVal;
		
		MyList<Integer> tmp = graph.adjList.get(vertex);
		graph.adjList.set(vertex, null);
		retVal.add(vertex);
		
		for(int i: tmp)
			retVal.addAll(DFS(i));
		return retVal;
	}
	
	ArrayList<MyList<Integer>> time(){
		finishingTimes();
		graph.transpose();
		
		if(verbose)
			System.out.println("Transpose:" + graph + "\n");
		
		ArrayList<MyList<Integer>> retVal = new ArrayList<MyList<Integer>>();
		Collections.reverse(fTimes);
		
		for(int nextLongest: fTimes){
			MyList<Integer> tmp = DFS(nextLongest);
			if(!tmp.isEmpty())
				retVal.add(tmp);
		}
		return retVal;
	}
	
	void findConnectedComponents(Scanner s){
		// Create graph
		graph.parseFile(s);
		
		if(verbose)
			System.out.println("Graph:" + graph + "\n");
		// Calculate connected components
		ArrayList<MyList<Integer>> connectedComponents = time();
		//print result
		int i = 1;
		for(MyList<Integer> component: connectedComponents){
			System.out.println("Connected Component #" + i++);
			for(Integer vertex: component)
				System.out.println(graph.stringMap.get(vertex));
			System.out.println();
		}
	}
	
	public static void main(String[] args){
		boolean verbose = false;
		int fileNameArg = 0;
		
		if(args.length == 2 && args[0].equals("-v")) {
			verbose = true;
			fileNameArg++;
		} else if(args.length != 1)
			throw new IllegalArgumentException();
		
		File file = new File(args[fileNameArg]);
		
		ConnectedComponents cc = new ConnectedComponents(verbose);
		cc.graph = new Graph<String>(true);
		
		try {
			cc.findConnectedComponents(new Scanner(file));
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("File not found");
		}
	}
}