package algs.days.day22;

import edu.princeton.cs.algs4.Stack;

// modified to store reverse of post order visit

public class DirectedDFS {
	boolean[]        marked;  // marked[v] = true if v is reachable
	int[]            edgeTo;
	Stack<Integer>   reversePost;
	int              numConnectedComponents = 0;

	/** Conduct DFS over digraph from specific vertex s. */
	public DirectedDFS(LectureDigraph G, int s) {
		marked = new boolean[G.V()];
		edgeTo = new int[G.V()];
		reversePost = new Stack<Integer>();
		dfs(G, s);
		numConnectedComponents++;
	}

	/** Computes the vertices in digraph connected from all unmarked vertices. */
	public DirectedDFS(LectureDigraph G) {
		marked = new boolean[G.V()];
		edgeTo = new int[G.V()];
		reversePost = new Stack<Integer>();
		for (int v = 0; v < G.V(); v++) {
			if (!marked[v]) {
				numConnectedComponents++;
				dfs(G, v);
			}
		}
	}

	void dfs(LectureDigraph G, int v) { 
		marked[v] = true;
		for (int w : G.adj(v)) {
			if (!marked[w]) {
				numConnectedComponents++;
				edgeTo[w] = v;
				dfs(G, w);
			}
		}

		// amazingly, this can be used to recover/restore the topological ordering
		reversePost.push(v);
	}

	/** Check whether specific vertex is reachable. */
	public boolean marked(int v) {
		return marked[v];
	}

	/** Retrieve reverse of postorder traversal. */
	public Iterable<Integer> reversePostorder() { return reversePost; }
	
	/** Retrieve number of connected components. */
	public int getConnectedComponents() { return numConnectedComponents; }
}