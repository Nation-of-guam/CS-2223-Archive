package algs.hw5.map;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

import algs.hw4.map.GPS;
import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.SeparateChainingHashST;

/**
 * Demonstrate how to load up a TMG highway segment file into a graph. This time
 * the graph is a weighted graph, but still undirected.
 */
public class HighwayMapWeighted {
	
	public static WeightedInformation undirectedGraph() {
		Scanner sc = null;
		try {
			URL map = HighwayMapWeighted.class.getResource("/algs/hw4/resources/MA-region-simple.tmg");
			sc = new Scanner(map.openStream());
			WeightedInformation wi = undirectedGraph(map.openStream());
			sc.close();
			return wi;
		} catch (IOException ioe) {
			System.err.println("Failed to read graph:" + ioe.getMessage());
			if (sc != null) { sc.close(); }
			return null;
		}
	}
		
	public static WeightedInformation undirectedGraph(InputStream is) {
		Scanner sc = new Scanner(is);

		int line = 0;
		String sline = sc.nextLine();
		if (sline.indexOf("TMG") < 0) {
			throw new RuntimeException("MA-region-simple.tmg is not a valid TMG file.");
		}
		line++;
		
		sline = sc.nextLine();
		String[] vals = sline.split(" ");
		int num_nodes = Integer.valueOf(vals[0]);
		int num_edges = Integer.valueOf(vals[1]);
		line++;

		SeparateChainingHashST<Integer, GPS> positions = new SeparateChainingHashST<>();
		SeparateChainingHashST<Integer, String> labels = new SeparateChainingHashST<>();
		EdgeWeightedGraph ewgraph = new EdgeWeightedGraph(num_nodes);
		Graph graph = new Graph(num_nodes);
		for (int i = 0; i < num_nodes; i++) {
			sline = sc.nextLine();
			vals = sline.split(" ");
			line++;

			positions.put(i, new GPS(Float.valueOf(vals[1]), Float.valueOf(vals[2])));
			labels.put(i, vals[0]);
		}

		for (int i = 0; i < num_edges; i++) {
			sline = sc.nextLine();
			vals = sline.split(" ");
			line++;

			int u = Integer.valueOf(vals[0]);
			int v = Integer.valueOf(vals[1]);
			GPS pos_u = positions.get(u);
			GPS pos_v = positions.get(v);
			ewgraph.addEdge(new Edge(u,  v, pos_u.distance(pos_v)));
			graph.addEdge(u, v);
		}

		sc.close();
		return new WeightedInformation (graph, ewgraph, positions, labels);
	}
}
