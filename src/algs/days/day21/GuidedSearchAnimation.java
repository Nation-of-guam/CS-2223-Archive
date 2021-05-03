package algs.days.day21;

import java.awt.Color;

import algs.hw4.map.GPS;
import algs.hw4.map.Information;
import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.MaxPQ;

public class GuidedSearchAnimation extends MapAnimation {
	int[] color;
	int[] edgeTo;
	
	public GuidedSearchAnimation () {
		super();
	}
	
	public GuidedSearchAnimation (Information info) {
		super(info);
	}
	
	class Key implements Comparable<Key> {
		final double priority;
		final int node;
		
		public Key (int node, double priority) {
			this.node = node;
			this.priority = priority;
		}

		@Override
		public int compareTo(Key o) {
			return (int) (priority - o.priority);
		}
	}
	
	protected boolean explore(int s, int target) {
		Graph g = info.graph;
		color = new int[g.V()];
		edgeTo = new int[g.V()];
		MaxPQ<Key> pq = new MaxPQ<Key>(g.V());
		
		// positions in the Queue are Gray and under investigation.
		pq.insert(new Key(s, 0));
		while (!pq.isEmpty()) {
			Key k = pq.delMax();

			checkPause();  // see if request pause by space bar. 
			int u = k.node;
			for (int v : g.adj(u)) {
				if (color[v] == White) {
					edgeTo[v] = u;
					color[v] = Gray;
					visualizer.highlightNode(v, Color.lightGray);
					delayAndRefresh();
					
					if (v == target) {
						color[v] = Black;
						visualizer.unhighlightAllNodes();
						visualizer.highlightNode(s, Color.green);
						visualizer.highlightNode(target, Color.red);
						
						showPath(s, target);
						visualizer.clear();
						return true;
					}

					pq.insert(new Key(v, -distance(v, target)));
				}
			}
			
			color[u] = Black;
			visualizer.highlightNode(u, Color.black);
			delayAndRefresh();
		}
		
		return false;
	}
	
	/**
	 * Return reasonably distance in miles. Based on helpful method found here:
	 *
    https://stackoverflow.com/questions/27928/calculate-distance-between-two-latitude-longitude-points-haversine-formula
    
	 * @param args
	 */
	double distance(int v, int target) {
		GPS pv = info.positions.get(v);
		GPS pt = info.positions.get(target);

		float lat1 = pv.latitude;
		float long1 = pv.longitude;
		float lat2 = pt.latitude;
		float long2 = pt.longitude;

		double p = Math.PI/180;
		double a = 0.5 - Math.cos((lat2-lat1)*p)/2 + Math.cos(lat1*p) * Math.cos(lat2*p) * (1-Math.cos((long2-long1)*p))/2;
		return 7917.509282 * Math.asin(Math.sqrt(a));    // convert into miles and use 12742 as earth diameter in KM
	}

	void showPath(int s, int target) {
		if (color[target] != Black) { return; }
		for (int x = target; x != s; x = edgeTo[x]) {
			visualizer.highlightEdge(x, edgeTo[x], Color.red);
			delayAndRefresh();
		}
	}
	
	public static void main(String[] args) {
		new GuidedSearchAnimation().launch(2256, 389);
	}
}