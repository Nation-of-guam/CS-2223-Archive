package algs.days.day25;

import algs.days.day24.AbstractWeightedDigraph;
import algs.days.day24.DigraphAdjacencyList;
import algs.days.day24.DijkstraSP;
import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.SeparateChainingHashST;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

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
 * Here is a sample run discovering a graph with 5 vertices...

 5 5
0: 0->4 (1.00)  0->1 (-2.00)  
1: 1->2 (3.00)  
2: 2->4 (-3.00)  
3: 
4: 4->3 (1.00)  

// Dijkstra
0 to 0 (0.00)  
0 to 1 (-2.00)  0->1 (-2.00)   
0 to 2 (1.00)   0->1 (-2.00)   1->2 (3.00)   
0 to 3 (2.00)   0->1 (-2.00)   1->2 (3.00)   2->4 (-3.00)   4->3 (1.00)   
0 to 4 (-2.00)  0->1 (-2.00)   1->2 (3.00)   2->4 (-3.00)   

// Bellman-Ford
0 to 0 (0.00)  
0 to 1 (-2.00)  0->1 (-2.00)   
0 to 2 (1.00)   0->1 (-2.00)   1->2 (3.00)   
0 to 3 (-1.00)  0->1 (-2.00)   1->2 (3.00)   2->4 (-3.00)   4->3 (1.00)   
0 to 4 (-2.00)  0->1 (-2.00)   1->2 (3.00)   2->4 (-3.00)   

As you can see, Dijkstra believes shortest distance from 0 to 3 is still 2, even though
it is clearly -1 with this graph.
 
*/
public class DijkstraFailsNegative {
	public static void main(String[] args) {
		int n = 5;
		
		while (true) {
			AbstractWeightedDigraph digraph = new DigraphAdjacencyList(n); 
			SeparateChainingHashST<String, Boolean> used = new SeparateChainingHashST<>();
			for (int i = 1; i < 6; i++) {
				int rndn = 1;
				if (StdRandom.uniform() < 0.2) { rndn = -1; }
				int weight = rndn*StdRandom.uniform(4);
				if (weight == 0) { weight = 1; }
				DirectedEdge de = new DirectedEdge(StdRandom.uniform(n), StdRandom.uniform(n), weight);
				if (de.from() != de.to() && !used.contains(de.from() + ":" + de.to())) {
					digraph.addEdge(de);
					used.put(de.from()+":" + de.to(), true);
				}
			}
	
			DijkstraSP sp = new DijkstraSP(digraph, 0);
			
			BellmanFord bf = new BellmanFord(digraph, 0);
			if (bf.hasNegativeCycle) { continue; }
			
			for (int i = 0; i < n; i++) {
				if (sp.distTo(i) != bf.distTo(i)) {
					StdOut.println(digraph.toString());
					
					BadDijkstra.printPaths(digraph, sp, 0);
					BellmanFord.printPaths(digraph, bf, 0);
					System.exit(1);
				}
			}
		}
	}
}
