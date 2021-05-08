package algs.days.day23;

import java.io.File;

import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;

/** 
 * Given an undirected graph, a Hamiltonian Path visits every vertex
 * exactly once.
 */
public class HamiltonPath {
	final Graph graph;
	Stack<Integer> visited = new Stack<>();
	boolean[] marked;
	boolean verbose = false;
	
	public HamiltonPath(Graph graph) {
		this.graph = graph;
		marked = new boolean[graph.V()];
	}
	
	void setVerbose(boolean b) { this.verbose = b; }
	
	void output() {
		Stack<Integer> copy = new Stack<>();
		StringBuffer path = new StringBuffer();
		while (!visited.isEmpty()) {
			int v = visited.pop();
			path.append(v).append("-");
			copy.push(v);
		}
		System.out.println(path);
		while (!copy.isEmpty()) {
			visited.push(copy.pop());
		}
	}
	
	boolean search(int v, int num) {
		marked[v] = true;
		
		if (num == graph.V()) {
			if (verbose) { output(); }
			return true;
		}
		
		for (int w : graph.adj(v)) {
			if (!marked[w]) {
				visited.push(w);
				if (search (w, num+1)) {
					return true;
				}
			}
		}
		
		marked[v] = false;
		visited.pop();
		return false;
	}
	
	public static void main(String[] args) {
		// durer.txt and petersen.txt have paths... This one does not...
		In in = new In(new File("petersen.txt"));
		Graph graph = new Graph(in);
		
		HamiltonPath hp = new HamiltonPath(graph);
		hp.verbose = true;
		hp.visited.push(0);
		hp.search(0, 1);
	}
	
}
