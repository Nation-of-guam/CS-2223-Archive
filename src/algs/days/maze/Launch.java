package algs.days.maze;

import algs.days.maze.builder.DepthFirstBuilder;

/**
 * Simpler launcher that constructs a Frame and makes it visible
 * Resize to generate smaller or larger mazes.
 * Left Click anywhere to launch a DepthFirst Search solver.
 * Right Click anywhere to launch a BreadthFirst Search solver.
 * 
 * Launch special solvers as follows:
 */
public class Launch {
	
	public static void main(String[] args) {
		Maze m = new DepthFirstBuilder(400, 400, 15);
		MazeGUI frame = new MazeGUI(m);
		frame.setVisible(true);
	}
}
