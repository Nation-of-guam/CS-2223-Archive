package algs.hw5;

import java.awt.Color;

import algs.days.day21.MapAnimation;
import algs.days.day26.FloydWarshallUndirected;
import algs.hw4.map.Information;

/** 
 * No exploring, but rather, just takes the results from FloydWarshal to display.
 * 
 * Do not copy this class into your local area. USE AS IS.
 */
public class FloydWarshallSolutionAnimation extends MapAnimation {
	FloydWarshallUndirected fw;
	
	public FloydWarshallSolutionAnimation (FloydWarshallUndirected fw) {
		super();
		this.fw = fw;
	}
	
	public FloydWarshallSolutionAnimation (Information info) {
		super(info);
	}
	
	@Override
	protected boolean explore(int s, int target) {
		visualizer.highlightNode(s, Color.green);
		visualizer.highlightNode(target, Color.red);
		
		// To rediscover shortest path between source and target, follow 'pred' points 
		// back to the source. If ever you hit '-1' then you never can make it to source.
		int node = target;
		while (node != s && node != -1) {
			int pred = fw.pred(s, node);
			visualizer.highlightEdge(pred, node, Color.red);
			delayAndRefresh();
			node = pred;
		}
		
		return true;
	}
}