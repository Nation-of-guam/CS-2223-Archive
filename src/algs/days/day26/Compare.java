package algs.days.day26;

import java.io.File;

import edu.princeton.cs.algs4.DijkstraSP;
import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StopwatchCPU;
/**
 * Compare Floyd-Warshall All-Pairs Shortest Path vs. multiple iterations of Dijkstra Single-Source Shortest Path.
 * 
 *
 */
public class Compare {

	public static void outputResults(double dist[][]) {
		System.out.print("\t");
		for (int v = 0; v < dist.length; v++) {
			System.out.print(v + "\t");
		}
		System.out.println();
		for (int u = 0; u < dist.length; u++) {
			System.out.print(u + "\t");
			for (int v = 0; v < dist.length; v++) {
				if (dist[u][v] >= Integer.MAX_VALUE) {
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
		System.out.println("Demonstrate results for Floyd-Warshall and Dijstra-SP V times are the same.");
		EdgeWeightedDigraph dig = new EdgeWeightedDigraph(new In(new File ("day24.txt")));
		FloydWarshallExample fw = new FloydWarshallExample(dig);
		fw.outputResults(dig);

		double dist[][] = new double[dig.V()][dig.V()];
		for (int i = 0; i < dig.V(); i++) {
			DijkstraSP sp = new DijkstraSP(dig, i);
			for (int j = 0; j < dig.V(); j++) {
				dist[i][j] = sp.distTo(j);
			}
		}

		outputResults(dist);


		// timing performance consideration.
		// BREAK-EVEN graph is when the #Edges is on the order of V^2/log(V). Aim for
		// N*(N-1)/log V. For example, for DIRECTED graphs, you can have up to N*(N-1)
		// possible edges.  This is true, since edge (A->B) is different from (B->A).
		// when N=32 one should have 198 edges in total out of 992.
		// So with independent probability p=O(1/log V), you should have desired effect.
		// to be sure, set p = 0.5/log V

		// with BreakEven Graph (p = 0.5/log V)
		//
		//		16		16		27		0.0			0.0
		//		32		32		119		0.0			0.0
		//		64		64		320		0.0			0.0156001
		//		128		128		1107	0.0156001	0.0156001
		//		256		256		4123	0.0312002	0.0780005
		//		512		512		14775	0.156001	0.1872012
		//		1024	1024	52667	1.404009	1.3416086
		//		2048	2048	190906	11.2476721	14.0868903
		
		//
		// But what about with p = .5
		//		16		16		131		0.0			0.0
		//		32		32		515		0.0			0.0156001
		//		64		64		2017	0.0			0.0156001
		//		128		128		8150	0.0156001	0.0624004
		//		256		256		32784	0.0312002	0.156001
		//		512		512		130860	0.1872012	1.4976096
		//		1024	1024	523943	1.4820095	15.1008968
		System.out.println("V\tE\ttime-FW\ttime-ChainedDSP");
		for (int N = 16; N <= 2048; N*=2) {
			dig = new EdgeWeightedDigraph(N);
			double log2N = Math.log(N)/Math.log(2);
			double p = .5/log2N; // p = .5/log(V)
			
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					if (Math.random() <= p) {
						// add edge with random weight.
						dig.addEdge(new DirectedEdge(i, j, Math.random()));
					}
				}
			}
			
			StopwatchCPU fwCPU = new StopwatchCPU();
			fw = new FloydWarshallExample(dig);
			double timeFW = fwCPU.elapsedTime();
			
			StopwatchCPU dspCPU = new StopwatchCPU();
			for (int i = 0; i < dig.V(); i++) {
				DijkstraSP sp = new DijkstraSP(dig, i);
			}
			double timeDSP = dspCPU.elapsedTime();
			
			System.out.printf("%d\t%d\t%.4f\t%.4f\n", dig.V(), dig.E(), timeFW, timeDSP);
		}
	}
}
