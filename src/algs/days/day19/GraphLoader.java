package algs.days.day19;

import java.io.File;

import edu.princeton.cs.algs4.In;

/** Not needed any more since in the latest Sedgewick code, they added a constructor for Graph. */
class GraphLoader {

	public static void main(String[] args) {
		Graph g = load(new File("tinyG.txt"));
		for (int i = 0; i < g.V(); i++) {
			System.out.print(i + ": ");
			for (int j : g.adj(i)) {
				System.out.print(j + " ");
			}
			System.out.println();
		}
	}
	
	public static Graph load(File f) {
		In in = new In(f);
		return load(in);
	}
	
	public static Graph load(In in) {
		int N = in.readInt();
		Graph g = new Graph(N);
		int E = in.readInt();

		for (int i = 0; i < E; i++) {
			int v = in.readInt();
			int w = in.readInt();
			g.addEdge (v,w);
		}
		return g;
	}
}
