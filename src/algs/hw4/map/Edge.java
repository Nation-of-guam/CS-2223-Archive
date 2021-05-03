package algs.hw4.map;

/**
 * Simple Edge class to represent an edge in a graph.
 */
public class Edge {

	public final int u;
	public final int v;
	
	public Edge (int u, int v) {
		this.u = u;
		this.v = v;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) { return false; }
		if (o == this) { return true; }
		
		// undirected edge -- need to check both.
		if (o instanceof Edge) {
			Edge other = (Edge) o;
			if (u == other.u && v == other.v) { return true; }
			if (u == other.v && v == other.u) { return true; }
		}
		
		return false;
	}
}