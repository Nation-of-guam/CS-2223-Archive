package algs.days.day21;

import java.awt.Color;

import algs.hw4.map.Information;
import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.Queue;

public class BFSMapAnimation extends MapAnimation {
	int[] color;
	int[] edgeTo;
	
	public BFSMapAnimation () {
		super();
	}
	
	public BFSMapAnimation (Information info) {
		super(info);
	}
	
	@Override
	protected boolean explore(int s, int target) {
		Queue<Integer> queue = new Queue<Integer>();
		Graph g = info.graph;
		color = new int[g.V()];
		edgeTo = new int[g.V()];

		// positions in the Queue are Gray and under investigation.
		queue.enqueue(s);
		color[s] = Gray;
		while (!queue.isEmpty()) {
			Integer u = queue.dequeue();
			if (isVerbose()) { 
				outputNode(u);
			}
			
			checkPause();  // see if request pause by clicking mouse

			for (int v : g.adj(u)) {
				if (color[v] == White) {
					edgeTo[v] = u;
					color[v] = Gray;
					visualizer.highlightNode(v, Color.lightGray);

					if (v == target) {
						color[v] = Black;
						visualizer.unhighlightAllNodes();
						visualizer.highlightNode(s, Color.green);
						visualizer.highlightNode(target, Color.red);

						showPath(s, target);
						visualizer.clear();
						return true;
					}
					
					queue.enqueue(v);
				}
			}

			color[u] = Black; // done with vertex
			visualizer.highlightNode(u, Color.black); 
			delayAndRefresh();
		}
		
		return false;
	}
	void showPath(int s, int target) {
		if (color[target] != Black) { return; }
		for (int x = target; x != s; x = edgeTo[x]) {
			visualizer.highlightEdge(x, edgeTo[x], Color.red);
			delayAndRefresh();
		}
	}
	
	public static void main(String[] args) {
		new BFSMapAnimation().launch(2256, 389);
	}
}