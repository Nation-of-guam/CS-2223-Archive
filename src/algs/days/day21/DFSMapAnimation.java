package algs.days.day21;

import java.awt.Color;

import algs.hw4.map.Information;
import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.Stack;

public class DFSMapAnimation extends MapAnimation {
	int[] color;
	int[] edgeTo;
	
	public DFSMapAnimation () {
		super();
	}
	
	public DFSMapAnimation (Information info) {
		super(info);
	}
	
	protected boolean explore(int s, int target) {
		Graph g = info.graph;
		color = new int[g.V()];
		edgeTo = new int[g.V()];
		Stack<Integer> stack = new Stack<Integer>();
		
		// positions in the Queue are Gray and under investigation.
		stack.push(s);
		while (!stack.isEmpty()) {
			Integer u = stack.pop();
			if (isVerbose()) { 
				outputNode(u);
			}
			
			checkPause();  // see if request pause by space bar. 
			
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

					stack.push(v);
				}
			}
			
			color[u] = Black;
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
		new DFSMapAnimation().launch(2256, 389);
	}
}