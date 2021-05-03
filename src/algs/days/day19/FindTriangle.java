package algs.days.day19;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class FindTriangle {
	static int a,b,c,d,e;

	/** Comments below are for WORST case when every edge in graph exists. */ 
	static int triangleSearchRepetitions (Graph g, boolean print) {
		a=b=c=d=e=0;
		
		// find triangles. Assume no self loops, thus u can never be in g.adj(u)
		int count = 0;
		for (int u = 0; u < g.V(); u++) { a++;            // for all vertices u...
			for (Integer v : g.adj(u)) { b++;             //   go find a neighbor v 
				for (Integer w : g.adj(v)) { c++;    	  //   then find neighbor w to v 
					for (Integer x : g.adj(u)) { d++;
						if (x == w) { e++; 
							if (print) {
								System.out.println("Triangle Found: (" + u + "," + v + "," + w + ")");
							}
							count++;
						}
					}
				}
			}
		}

		return count/6;    // make sure to avoid over-counting: given 3 elements, there are 3! permutations
	}


	/**
	 * Build complete graphs and search
	 */
	static void completeGraphTriangleSearch(int k) {
		Graph g = new Graph(k);
		for (int i = 0; i < k-1; i++) {
			for (int j = i+1; j < k; j++) {
				g.addEdge(i, j);
			}
		}

		int count = triangleSearchRepetitions(g, false);
		System.out.println(k + "\t" + count + "\t"+ a + "\t" + b + "\t" + c + "\t" + d + "\t" + e);
	}

	public static void main(String[] args) {
		Graph g;
		int count;

		if (args.length != 0) {
			In in = new In(args[0]);
			g = GraphLoader.load(in);
			count = triangleSearchRepetitions(g, true);
			System.out.println(g.V() + "\t" + count + "\t"+ a + "\t" + b + "\t" + c + "\t" + d + "\t" + e);
			return;
		}

		StdOut.println("N\tcount\ta\tb\tc\td\te");
		for (int i = 3; i <= 7; i++) {
			completeGraphTriangleSearch(i);
		}
		StdOut.println();

		StdOut.println("This is going to take awhile!");
		StdOut.println("N\tE\tCount\tEst.");
		int N = 8;
		while (N <= 1024) {
			g = new Graph(N);

			// with probability 1/2, generate each possible edge. Note this means
			// that the number of edges will be ~ (1/2) C(N,2) or N^2/4-N/4 
			for (int i = 0; i < N-1; i++) {
				for (int j = i+1; j < N; j++) {
					if (StdRandom.uniform() < 0.5) {
						g.addEdge(i,j);
					}
				}
			}

			count = triangleSearchRepetitions(g, false);
			// Expected number of triangles is (1/8) C(N,3) 
			// http://www.sciencedirect.com/science/article/pii/S0012365X0400370X
			// however, this doesn't pan out. Curious to see the number of triangles
			// remain mostly constant...
			//			N		E		Count	Est.
			//			64		985		4833	5208.0
			//			128		4045	42112	42672.0
			//			256		16339	173600	345440.0	* off by x2
			//			512		65264	689410	2779840.0	* off by x4
			//			1024	261563	2763238	2.2304128E7	* off by x8

			float estimate = N*(N-1)*(N-2)/48.0f;
			StdOut.println(N + "\t" + g.E() + "\t" + count + "\t" + estimate);
			N *= 2;
		}
	}
}
