package algs.hw4.map;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.SeparateChainingHashST;

/**
 * Demonstrate how to load up a TMG highway segment file into a graph. Currently
 * the only graph is an undirected graph with no edge weights.
 * 
 */
public class HighwayMap {
	
	public static Information undirectedGraph() {
		try {
			URL map = HighwayMap.class.getResource("/algs/hw4/resources/MA-region-simple.tmg");
			Scanner sc = new Scanner(map.openStream());
			return undirectedGraph(map.openStream());
		} catch (IOException ioe) {
			System.err.println("Failed to read graph:" + ioe.getMessage());
			return null;
		}
	}
		
	public static Information undirectedGraph(InputStream is) {
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
			graph.addEdge(u,  v);
		}

		sc.close();
		return new Information (graph, positions, labels);
	}
}
