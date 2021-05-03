package algs.days.day26;

import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class FloydWarshallExample {
	

	double dist[][];
	int pred[][];
	
	public FloydWarshallExample(EdgeWeightedDigraph G) {
		
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
			for (DirectedEdge edge : G.adj(u)) {
				int v = edge.to();
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
			
			path.push(s);
		}
		
		return path;
	}
	
	void outputResults(EdgeWeightedDigraph dig) {
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
		EdgeWeightedDigraph dig = new EdgeWeightedDigraph(5);
		
		dig.addEdge(new DirectedEdge(0, 1, 2));
		dig.addEdge(new DirectedEdge(0, 4, 4));
		dig.addEdge(new DirectedEdge(1, 2, 3));
		dig.addEdge(new DirectedEdge(2, 4, 1));
		dig.addEdge(new DirectedEdge(2, 3, 5));
		dig.addEdge(new DirectedEdge(3, 0, 8));
		dig.addEdge(new DirectedEdge(4, 3, 7));
		
		
		FloydWarshallExample fw = new FloydWarshallExample(dig);
		fw.outputResults(dig);
		
		while (true) {
			System.out.println("Enter start and end vertices separated by space:");
			int start = StdIn.readInt();
			int end = StdIn.readInt();
			
			StdOut.printf("Path from (%d, %d) has length %f\n", start, end, fw.dist[start][end]);
			for (int i : fw.shortestPath(start, end)) {
				StdOut.printf(" %d ", i);
			}
			System.out.println();
		}
	}

}
