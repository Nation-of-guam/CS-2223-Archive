package algs.hw4.map;

import java.io.FileInputStream;

import javax.swing.JFrame;

/** 
 * Standalone JFrame in which a TMG graph file is visualized.
 * 
 * Execute from Shell as:
 * 
 * % java algs.hw4.map.MappApp LOCAL-FILE-PATH, like...
 * 
 * % java algs.hw4.map.MapApp src\algs\days\day19\london25-area-simple.tmg
 */
public class MapApp extends JFrame {
	
	public final Visualizer visualizer;
	
	public MapApp(Information info) {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1014, 542);
		setResizable(false);
		visualizer = new Visualizer(info);
		setContentPane(visualizer);
	}
	
	public static void main(String[] args) throws Exception {
		Information info;
		if (args.length == 0) {
			info = HighwayMap.undirectedGraph();
		} else {
			info = HighwayMap.undirectedGraph(new FileInputStream(args[0]));
		}

		MapApp ma = new MapApp(info);
		ma.setVisible(true);
	}
}