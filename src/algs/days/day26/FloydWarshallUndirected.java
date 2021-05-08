package algs.days.day26;

import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;

/**
 * Run in Shell as follows:
 * 
 * % java algs.days.day26.FloydWarshallUndirected hw5.txt
 */
public class FloydWarshallUndirected {
	
	double dist[][];
	int pred[][];
	
	public double distTo(int u, int v) { return dist[u][v]; }
	public int pred(int u, int v) { return pred[u][v]; }
	
	public FloydWarshallUndirected(EdgeWeightedGraph G) {
		
		// pred[u][v] means "on shortest path from u to v, last edge came from vertex pred[u][v]."
		pred = new int[G.V()][G.V()];
		dist = new double[G.V()][G.V()];
		
		// initialize edges and base cases
		for (int u = 0; u < G.V(); u++) {
			for (int v = 0; v < G.V(); v++) {
				dist[u][v] = Integer.MAX_VALUE;
				pred[u][v] = -1;
			}
			
			dist[u][u] = 0;   // distance to self is 0
			for (Edge edge : G.adj(u)) {
				int v = edge.other(u);
				dist[u][v] = edge.weight();
				pred[u][v] = u;   // base case
			}
		}
		
		// algorithm now proceeds
		for (int k = 0; k < G.V(); k++) {
			for (int u = 0; u < G.V(); u++) {
				for (int v = 0; v < G.V(); v++) {
					
					// can we shorten distance from u to v by going through k.
					double newLen = dist[u][k] + dist[k][v];
					if (newLen < dist[u][v]) {
						dist[u][v] = newLen;
						pred[u][v] = pred[k][v];    // TRICK: 
					}
				}
			}
		}
	}
	
	/** For simplicity: assume s and t are valid. */
	public Iterable<Integer> shortestPath(int s, int t) {
		Stack<Integer> path = new Stack<>();
		path.push(t);
		while (t != s) {
			t = pred[s][t];
			if (t == -1) { 
				// error! No path
				return new Stack<Integer>();
			}
			
			path.push(t);
		}
		
		return path;
	}
	
	void outputResults(EdgeWeightedGraph dig) {
		System.out.print("\t");
		for (int v = 0; v < dig.V(); v++) {
			System.out.print(v + "\t");
		}
		System.out.println();
		for (int u = 0; u < dig.V(); u++) {
			System.out.print(u + "\t");
			for (int v = 0; v < dig.V(); v++) {
				if (dist[u][v] == Integer.MAX_VALUE) {
					System.out.printf(" - \t");
				} else {
					System.out.printf("%d\t", (int) dist[u][v]);
				}
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public static void main(String[] args) {
		In in = new In(args[0]);
		EdgeWeightedGraph graph = new EdgeWeightedGraph(in);

		FloydWarshallUndirected fw = new FloydWarshallUndirected(graph);
		fw.outputResults(graph);
	}
	
}
