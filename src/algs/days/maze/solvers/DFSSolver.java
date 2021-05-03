package algs.days.maze.solvers;

import java.util.Iterator;

import algs.days.maze.Maze;
import algs.days.maze.MazePanel;
import algs.days.maze.Position;

/**
 * Solver conducts a modified Depth First Search. Specifically, it stops prematurely when a specific
 * destination has been reached.
 */
public class DFSSolver extends PausableThread {
	// GUI into that maze
	final MazePanel panel;
	final Maze      maze;
	
	/** Color records the progress of the Depth First Search. */
	final int color [][];
	
	/**
	 * Construct solver with the GUI that needs to be refreshed as the progress continues, and 
	 * store our destination.
	 */ 
	public DFSSolver (MazePanel panel) {
		this.panel = panel;
		this.maze = panel.getMaze();
		color = new int[maze.rows()][maze.columns()];
		
		this.panel.setProgress(color);
	}
	
	boolean dfsVisit(Position pos) {
		color[pos.row][pos.col] = Constants.Gray;
		panel.redraw();
		panel.repaint();

		checkPaused();
		
		// immediately force all processing to unwind...
		if (pos.equals (maze.getDestination())) {
			return true;
		}
		
		for (Iterator<Position> it = maze.finalNeighbors[pos.row][pos.col].iterator(); it.hasNext(); ) {
			Position cell = it.next();
			if (color[cell.row][cell.col] == Constants.White) {
				if (dfsVisit(cell)) {
					return true;
				}
			}
		}

		color[pos.row][pos.col] = Constants.Black;
		panel.redraw();
		panel.repaint();
		return false;
	}
	
	public void run () {
		try {
			dfsVisit(maze.getStart());
			System.out.println("normal termination");
			System.out.println("Done...");
		} catch (StackOverflowError e) {
			System.out.println ("Reecursion too deep to complete DFS.");
        }
        
		this.panel.setProgress(null);
	}

}
