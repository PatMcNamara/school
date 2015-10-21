import java.util.Arrays;
import java.util.LinkedList;

class Graphtest {
    
    public static final int GRAPH_SIZE = 15;
    public static final double EDGE_PERCENT = 0.3;


    public static void PrintPath(int Parent[], int endvertex) {
    	PrintPathHelper(Parent, endvertex);
    	System.out.println();
    }
    
    private static void PrintPathHelper(int Parent[], int endvertex) {
    	System.out.print(endvertex + " ");
    	if(Parent[endvertex] != -1)
    		PrintPathHelper(Parent, Parent[endvertex]);
    }

    public static void BFS(Graph G, int Parent[], int startVertex, boolean Visited[]) {
    	LinkedList<Integer> queue = new LinkedList<Integer>();
    	queue.offer(startVertex);
    	Visited[startVertex] = true;
    	while(queue.size() > 0) {
    		int vertex = queue.poll();
    		for(Edge edge = G.edges[vertex]; edge != null; edge = edge.next) {
    			if(!Visited[edge.neighbor]) {
    				queue.offer(edge.neighbor);
    				Visited[edge.neighbor] = true;
    				Parent[edge.neighbor] = vertex;
    			}
    		}
    	}
    }

    public static void main(String args[]) {
	boolean Visited[] = new boolean[GRAPH_SIZE];
	int Parent[] = new int[GRAPH_SIZE];
	Graph G = new Graph(GRAPH_SIZE);
	int i;
	for (i=0; i<G.numVertex;i++) {
	    Visited[i] = false;
	    Parent[i] = -1;
	}
	G.randomize(EDGE_PERCENT);
	G.print();
	BFS(G,Parent,0,Visited);
	for (i=0; i<G.numVertex;i++) {
	    System.out.println("Path from 0 to " + i + ":");
	    PrintPath(Parent,i);
	}
	for (i=0; i<G.numVertex;i++) {
	    Visited[i] = false;
	    Parent[i] = -1;
	}
    }
}