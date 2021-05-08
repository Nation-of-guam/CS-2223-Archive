package algs.days.day25;

import algs.days.day24.AbstractWeightedDigraph;
import algs.days.day24.DigraphAdjacencyList;
import algs.days.day24.DijkstraSP;
import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.StdOut;

/**
 * Searches for a graph (randomly) that fails Dijkstra but succeeds with Bellman Ford
 * this only works when you make the "fix" to DijkstraSP to prevent exceptions as follows:
 *
 *   void relax(DirectedEdge e) {
 *     int v = e.from();
 *     int w = e.to();
 *
 *     // distance from s ->v + edge weight (v, w) < distance from s to w
 *     if (distTo[w] > distTo[v] + e.weight()) {
 *       distTo[w] = distTo[v] + e.weight();
 *       edgeTo[w] = e;
 *       if (pq.contains(w)) { pq.decreaseKey(w, distTo[w]); }
 *     }
 *   }
 *
 * Note that the difference is Dijkstra believes 0->3 in total distance of 4 even though with
 * negative edge it should be 3.
 */
public class BadDijkstra {
	
	public static void printPaths(AbstractWeightedDigraph G, DijkstraSP sp, int s) {
		// print shortest path for a number of sample graphs.
		for (int t = 0; t < G.V(); t++) {
			if (sp.hasPathTo(t)) {
				StdOut.printf("%d to %d (%.2f)  ", s, t, sp.distTo(t));
				for (DirectedEdge e : sp.pathTo(t)) {
					StdOut.print(e + "   ");
				}
				StdOut.println();
			}
			else {
				StdOut.printf("%d to %d         no path\n", s, t);
			}
		}
	}
	public static void main(String[] args) {
		// example graph showing Dijkstra provides different values than Bellman-Ford
		DigraphAdjacencyList digraph = new DigraphAdjacencyList(5); 
		digraph.addEdge(new DirectedEdge(0, 1, 3));
		digraph.addEdge(new DirectedEdge(0, 4, 1));
		digraph.addEdge(new DirectedEdge(1, 2, -2));  // THIS IS THE PROBLEM
		digraph.addEdge(new DirectedEdge(4, 2, 1));
		digraph.addEdge(new DirectedEdge(2, 3, 2));
		
		BellmanFord bf = new BellmanFord(digraph, 0);

		StdOut.println ("Bellman-Ford");
		BellmanFord.printPaths(digraph, bf, 0);

		// this FAILS
		DijkstraSP sp = new DijkstraSP(digraph, 0);
		
		StdOut.println ("Dijkstra");
		printPaths(digraph, sp, 0);
		
		StdOut.println();
	}
}
