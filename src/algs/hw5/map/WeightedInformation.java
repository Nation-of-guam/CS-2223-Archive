package algs.hw5.map;

import algs.hw4.map.GPS;
import algs.hw4.map.Information;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.SeparateChainingHashST;

/**
 * Records a WeightedGraph for highway segments together with GPS information for each node id.
 * 
 * Extends Information so this class can integrate with MapAnimation. Keep 'graph' for visualization, and
 * use 'ewgraph' for processing.
 */
public class WeightedInformation extends Information {
	/** The Weighted graph. */
	public final EdgeWeightedGraph ewgraph;
	
	public WeightedInformation (Graph g, EdgeWeightedGraph ewg, SeparateChainingHashST<Integer, GPS> pos, SeparateChainingHashST<Integer, String> labels) {
		super(g, pos, labels);
		this.ewgraph = ewg;
	}
}
