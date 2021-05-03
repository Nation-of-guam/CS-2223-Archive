package algs.days.maze.builder;

import java.util.Iterator;

import algs.days.maze.Bag;
import algs.days.maze.Maze;
import algs.days.maze.Position;
import algs.days.maze.solvers.Constants;

/**
 * A Maze is a rectangular structure defined by a number of rows (numrows) and columns (numcols).
 * 
 * This code uses an innovative solution for generating a random maze. Specifically, it starts
 * with a "maze" where each of the numrows x numcols rooms has all of its four walls intact.
 * It then conducts a randomized depth first traversal of the rooms, knocking down walls in the
 * process.
 * 
 * The end result is a finalNeighbors[][] array which stores the neighboring positions (r,c) for
 * each (r,c) in the maze.
 * 
 * To make it easy to render a maze, this class also stores two arrays, hasSouthWall[][] and hasEastWall[][]
 * which determines whether a wall exists to the south of a given (r,c) position or to the east of a given
 * (r,c) position. If you think about this for a bit, this is all the information you need to render a maze.
 * Specifically, the bottommost positions in the maze (the final row) will have hasSouthWall set to True while
 * the rightmost positions in the maze (the final column) will have hasEastWeel set to True.
 *
 * The end result is a challenging maze that looks quite professional.
 */
public class DepthFirstBuilder extends Maze {

	public DepthFirstBuilder(int height, int width, int size) {
		super(height, width, size);
	}

	/** During the DFS traversal while building the maze, this represents color for each position. */
	int color [][];
	
	/**
	 * Subclasses are responsible for providing logic of constructing a mze.
	 */
	protected void constructMaze(Position p) {

		color = new int[rows()][columns()];
		
		// use dfsVisit to explore the maze, knocking down walls in the process.
		dfsVisit(start);
	}
	
	/** Key to entire algorithm: Choose a random element from a Bag. Only call this method if not empty. */
	Position randomNeighbor(Position pos) {
		Bag<Position> bag = neighbors[pos.row][pos.col];
		
		if (bag.size() == 0) { return null; }
		
		// randomly choose one by iterating over entire set.
		int i = (int)(Math.random()*bag.size());
		Iterator<Position> it = bag.iterator();
		Position retVal = null;
		while (i-- >= 0) {
			retVal = it.next();
		}
		
		return retVal;
	}
	
	/**
	 * Conduct the depth first search exploration of an 'empty' maze by randomly choosing
	 * a neighbor and knocking down the walls along the way
	 * @param pos
	 */
	void dfsVisit(Position pos) {
		// we mark position as being visited.
		color[pos.row][pos.col] = Constants.Gray;

		// Make sure to visit all neighbors, one by one; each time removing it from our
		// neighbor set for efficiency's sake.
		while (neighbors[pos.row][pos.col].size() > 0) {
			Position cell = randomNeighbor(pos);
			neighbors[pos.row][pos.col].remove(cell);
			
			// if this direction has not yet been visited, knock down the wall and proceed
			if (color[cell.row][cell.col] == Constants.White) {
				clearWall(pos.row, pos.col, cell.row, cell.col);
				
				dfsVisit(cell);
			}
		}

		color[pos.row][pos.col] = Constants.Black;
	}
	
}



