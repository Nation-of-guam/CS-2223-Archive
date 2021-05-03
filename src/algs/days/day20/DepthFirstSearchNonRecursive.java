package algs.days.day20;

import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

/**
 * Run in Shell as follows:
 * 
 * % java algs.days.day20.DepthFirstSearchNonRecursive tinyG.txt
 * 
 * Example of dfs with out recursion
 */
public class DepthFirstSearchNonRecursive {
	
	boolean marked[];	// which vertices have been seen already
	int[] edgeTo;       // edgeTo[v] = w means (w,v) on s-v path
	int count;			// how many connected
	int s;              // source vertex
	Graph g;			// graph being searched
	String indent = "";
	public DepthFirstSearchNonRecursive (Graph g, int s) {
		marked = new boolean[g.V()];
		edgeTo = new int[g.V()];
		this.g = g;
		this.s = s;
		dfs(s);
	}
	
	public int count() { return count; }    			 // number of vertices connected to s
	public boolean marked(int v) { return marked[v]; }
	
	/** Continue DFS search over graph by visiting vertex v. */  
	void dfs (int v) {
		marked[v] = true;    // we have now seen this vertex 
		count++;
		Stack<Integer> stack = new Stack<>();
		stack.push(v);
		
		while (!stack.isEmpty()) {
			v = stack.pop();
			
			// look into neighbors
			for (int w : g.adj(v)) {
				if (!marked[w]) {
					marked[w] = true;
					edgeTo[w] = v;
					count++;
					stack.push(w);
				}
			}
		}
	}

	public boolean hasPathTo(int v) { return marked[v]; }
	
	/** recover path from s to v. */
	public Iterable<Integer> pathTo(int target) {
		if (!hasPathTo(target)) return null;
		
		Stack<Integer> path = new Stack<Integer>();
		int v = target;
		while (v != s) {
			path.push(v);
			v = edgeTo[v];
		}

		// last one to push is the source, which makes it
		// the first one to be retrieved
		path.push(s);
		return path;
	}
	
	
	public static void main(String[] args) {
		In in = new In(args[0]);
		Graph g = new Graph(in);
		
		// conduct a DFS over entire graph
		DepthFirstSearchNonRecursive dfs = new DepthFirstSearchNonRecursive(g, 0);
		
		// see who was connected
		StdOut.printf("path from %d to %d: ", 0, g.V()-1);   // pick highest 
		Iterable<Integer> path = dfs.pathTo(g.V()-1);
		if (path == null) {
			StdOut.println ("No Path");
		} else {
			for (int v : path) {
				StdOut.print(v + " - ");
			}
			StdOut.println();
		}
		StdOut.println(dfs.count() + " connected vertices.");
	}
}
