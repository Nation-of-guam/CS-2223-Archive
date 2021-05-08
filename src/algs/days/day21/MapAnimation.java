package algs.days.day21;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import algs.hw4.map.HighwayMap;
import algs.hw4.map.Information;
import algs.hw4.map.MapApp;
import algs.hw4.map.Visualizer;

/**
 * Builds from the HW4 resources to demonstrate BFS and DFS (even Guided) in the context
 * of a highway map system.
 * 
 * If you set VERBOSE to be true, additional debugging information is printed out. This might
 * be useful for the BONUS question...
 */
public abstract class MapAnimation {
	protected Information info;
	protected MapApp mapApp;
	protected Visualizer visualizer;
	
	protected boolean verbose = false;
	
	public static final int White = 0;
	public static final  int Gray = 1;
	public static final  int Black = 2;
	public static final  Color colors[] = { Color.gray, Color.blue, Color.green };
	
	// pause the animation
	protected boolean pause = false;
	
	// 50 ms delay for animation purpose. Make a field so it can be changed.
	int delay = 5;
	
	protected MapAnimation () {
		info = HighwayMap.undirectedGraph();
		initialize(info);
	}
	
	protected MapAnimation (Information info) {
		this.info = info;
		initialize(info);
	}
	
	private void initialize(Information info) {
		mapApp = new MapApp(info);
		visualizer = mapApp.visualizer;
		mapApp.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent event) {
				pressed(event);
			}
		});
	}
	
	// 100 ms delay for animation.
	protected void delayAndRefresh() {
		visualizer.repaint();
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/** Mouse was pressed. */
	protected void pressed(MouseEvent event) {
		pause = !pause;
	}
	
	/** While paused. Spin in this loop, checking every 10ms whether to leave. */
	protected void checkPause() {
		while (pause) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	

	/** Useful for bonus questions, during verbose execution. */
	protected void outputNode(Integer u) {
		System.out.println(u + "(" + info.labels.get(u) + " -> " + info.positions.get(u) + ")");
	}
	
	public void setVerbose(boolean b) { this.verbose = b; }
	public boolean isVerbose() { return verbose; }

	
	/** Launch the application, delegating to subclass. */
	public boolean launch(int s, int target) {
		mapApp.setVisible(true);
		return explore(s, target);
	}
	
	/** Filled in by subclasses. */
	protected abstract boolean explore(int s, int target);
}
