package algs.days.day21;

import edu.princeton.cs.algs4.*;

/**
 * Run in Shell as follows:
 * 
 * % java algs.days.day21.BreadthFirstPaths day21.txt
 * 
 * Note: output will not match the one we do BY HAND because of the numbering of vertices.
 * Don't Panic! On an exam, you will always visit adjacent vertices in ascending order.
 */
public class BreadthFirstPaths {
	private static final int INFINITY = Integer.MAX_VALUE;
	private boolean[] marked;  // marked[v] = is there an s-v path
	private int[] edgeTo;      // edgeTo[v] = w means (w,v) on s-v path
	private int[] distTo;      // distTo[v] = number of edges shortest s-v path

	public BreadthFirstPaths(Graph G, int s) {
		marked = new boolean[G.V()];
		distTo = new int[G.V()];
		edgeTo = new int[G.V()];
		bfs(G, s);
	}

	public boolean hasPathTo(int v) { return marked[v]; }
	public int distTo(int v) { return distTo[v]; }
	
	// breadth-first search from a single source
	private void bfs(Graph G, int s) {
		Queue<Integer> q = new Queue<Integer>();
		for (int v = 0; v < G.V(); v++) { distTo[v] = INFINITY; }
		distTo[s] = 0;
		marked[s] = true;
		q.enqueue(s);

		while (!q.isEmpty()) {
			int v = q.dequeue();
			for (int w : G.adj(v)) {
				if (!marked[w]) {
					edgeTo[w] = v;
					distTo[w] = distTo[v] + 1;
					marked[w] = true;
					q.enqueue(w);
				}
			}
		}
	}


	/**
	 * Returns a shortest path between the source vertex {@code s} (or sources)
	 * and {@code v}, or {@code null} if no such path.
	 * @param  v the vertex
	 * @return the sequence of vertices on a shortest path, as an Iterable
	 * @throws IllegalArgumentException unless {@code 0 <= v < V}
	 */
	public Iterable<Integer> pathTo(int v) {
		if (!marked[v]) return null;
		Stack<Integer> path = new Stack<Integer>();
		int x;
		for (x = v; distTo[x] != 0; x = edgeTo[x])
			path.push(x);
		path.push(x);
		return path;
	}

	public static void main(String[] args) {
		String input;
		if (args.length != 0) {
			input = args[0];
		} else {
			input = "day21.txt";
		}

		In in = new In(input);
		Graph g = new Graph(in);
		System.out.println(g);
		
		// conduct a DFS over entire graph
		BreadthFirstPaths bfs = new BreadthFirstPaths(g, 0);
		Iterable<Integer> paths = bfs.pathTo(5);
		
		// Show path to v5
		for (int i : paths) {
			StdOut.print(i + " - ");
		}
	}

}