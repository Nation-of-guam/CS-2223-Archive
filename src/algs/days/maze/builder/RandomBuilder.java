package algs.days.maze.builder;

import java.util.Iterator;

import algs.days.maze.Bag;
import algs.days.maze.Maze;
import algs.days.maze.Position;
import algs.days.maze.solvers.Constants;

/**
 * A RandomBuilder uses a fixed probability, p, to determine whether there is a wall between
 * two neighboring cells (each cell has four potential neighbors).
 * 
 */
public class RandomBuilder extends Maze {

	final double prob;
	
	public RandomBuilder(int height, int width, int size, double prob) {
		super(height, width, size);
		
		this.prob = prob;
	}
	
	/**
	 * Randomly remove wall if probability < prob.
	 * 
	 * No walls, set prob to 1
	 * 
	 * All walls, set prob to 0
	 */
	protected void constructMaze(Position start) {

		for (int r = 0; r < rows(); r++) { 
			for (int c = 0; c < columns(); c++) {
				Bag<Position> bag = neighbors[r][c];
				Iterator<Position> it = bag.iterator();
				while (it.hasNext()) {
					Position p = it.next();
					if (Math.random() < prob) {
						this.clearWall(r, c, p.row, p.col);
					}
				}
			}
		}
	}
}



