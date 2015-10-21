import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
/**
 * Finds a valid Euler cycle (if one exists), in a given graph.
 * @author pjmcnamara2
 *
 */
public class Euler {
	Graph<String> graph = new Graph<String>(false);
	List<String> cycle = new MyList<String>(); // Euler cycle
	boolean verbose; // Verbose output
	
	Euler(boolean verbose){
		this.verbose = verbose;
	}
	
	void DFS(String i, int position){
		cycle.add(position, i);
		String vertex;
		while((vertex = graph.getOutgoingEdge(i)) != null){
			graph.removeEdge(i, vertex);
			DFS(vertex, position + 1);
		}
	}
	
	/**
	 * Checks that all nodes in the graph have a multiple of 2 edges.
	 * @return
	 */
	boolean validCycle() {
		boolean valid = true;
		for(List vertex: graph.adjList)
			valid &= vertex.size() % 2 == 0;
		return valid;
	}
	
	void findEulerCycle(Scanner s){
		graph.parseFile(s);
		if(verbose)
			System.out.println("Graph:" + graph + "\n");
		
		// these values are used to check it a Euler cycle exists
		int numEdges = graph.totalNumberOfEdges();
		boolean validCycle = validCycle();
		
		DFS(graph.stringMap.get(0), 0);
		if(cycle.size() == numEdges + 1 && validCycle)
			System.out.println("Euler Cycle:\n" + cycle);
		else
			System.out.println("No Euler Cycle Exists");
	}
	
	public static void main(String args[]){
		boolean verbose = false;
		int fileNameArg = 0;
		
		if(args.length == 2 && args[0].equals("-v")) {
			verbose = true;
			fileNameArg++;
		} else if(args.length != 1)
			throw new IllegalArgumentException();
		
		File file = new File(args[fileNameArg]);
		
		Euler euler = new Euler(verbose);
		euler.graph = new Graph<String>(false);
		
		try {
			euler.findEulerCycle(new Scanner(file));
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("File not found");
		}
	}
}
