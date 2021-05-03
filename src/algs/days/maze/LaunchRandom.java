package algs.days.maze;

import algs.days.maze.builder.RandomBuilder;

/**
 * Simpler launcher that constructs a Frame and makes it visible
 * Resize to generate smaller or larger mazes.
 * Left Click anywhere to launch a DepthFirst Search solver.
 * Right Click anywhere to launch a BreadthFirst Search solver.
 * 
 * Generate random solvers with 35% of having a wall
 */
public class LaunchRandom {
	
	public static void main(String[] args) {
		Maze m = new RandomBuilder(400, 400, 15, .35);
		MazeGUI frame = new MazeGUI(m);
		frame.setVisible(true);
	}
}
