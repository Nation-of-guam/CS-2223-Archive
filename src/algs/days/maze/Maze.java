package algs.days.maze;

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
public abstract class Maze {

	/** size of each box. */ 
	public int size;
	
	/** Number of rows and columns in the maze. */
	int numrows;
	int numcols;
	
	/** Initially all true, these will eventually. */
	public boolean hasSouthWall[][];
	public boolean hasEastWall[][];
	
	/** 
	 * Initially all cells have neighbors; as the maze is constructed, these are removed and 
	 * processed until all neighbors are empty.
	 */
	public Bag<Position> neighbors[][];
	
	/** Once maze is constructed, this properly records which positions are neighbors to each other. */
	public Bag<Position> finalNeighbors[][];
	
	/** Once maze is constructed, this is the known starting point. */
	protected Position start;
	
	/** Once maze is constructed, this is the known destination. */
	protected Position destination;

	public Maze(int height, int width, int size) {
		this.size = size;
		
		if (height < size) { height = size; }
		if (width < size) { width = size; }
		
		resize (width, height, size);
	}

	public void resize(int width, int height, int boxSize) {
		this.size = boxSize;
		numrows = height/size;
		numcols = width/size;
		hasSouthWall = new boolean [numrows][numcols];
		hasEastWall = new boolean [numrows][numcols];
		neighbors = new Bag[numrows][numcols];
		finalNeighbors = new Bag[numrows][numcols];

		// construct the initial maze which has all walls intact and each potential neighbor
		// is present in the neighbors[r][c] array. Note neighbors are only horizontal or vertical
		// and neighbors are restricted to being part of the proper maze when on the edge.
		for (int r = 0; r < numrows; r++) {
			for (int c = 0; c < numcols; c++) {
				hasEastWall[r][c] = true;
				hasSouthWall[r][c] = true;

				neighbors[r][c] = new Bag<Position>();
				finalNeighbors[r][c] = new Bag<Position>();
				if (r != 0) {
					neighbors[r][c].add(new Position (r-1, c));
				}
				if (r != numrows-1) {
					neighbors[r][c].add(new Position (r+1, c));
				}
				if (c != 0) {
					neighbors[r][c].add (new Position (r, c-1)); 
				}
				if (c != numcols-1) {
					neighbors[r][c].add (new Position (r, c+1));
				}
			}
		}

		// start the maze from the top row and halfway across it
		start = new Position (0, (numcols-1)/2);
		
		// use dfsVisit to explore the maze, knocking down walls in the process.
		constructMaze(start);
		
		// arbitrarily determine the end of the maze as being in the bottom row and halfway across it.
		destination = new Position (numrows-1, (numcols-1)/2);
		hasSouthWall[destination.row][destination.col] = false;
	}
	
	/**
	 * Subclasses are responsible for providing logic of constructing a mze.
	 */
	protected abstract void constructMaze(Position p);
	
	/**
	 * Knock down the wall from (fr,fc) to (tr,tc).
	 * 
	 * Handles the case for vertical or horizontal walls. 
	 */
	protected void clearWall(int fromCellRow, int fromCellCol, int toCellRow, int toCellCol) {
		if (fromCellCol == toCellCol) {
			hasSouthWall[Math.min(fromCellRow, toCellRow)][fromCellCol] = false;
		} else {
			hasEastWall[fromCellRow][Math.min(fromCellCol, toCellCol)] = false; 
		}

		// These two statements record that pos & cell are neighbors now.
		finalNeighbors[fromCellRow][fromCellCol].add(new Position (toCellRow, toCellCol));
		finalNeighbors[toCellRow][toCellCol].add(new Position (fromCellRow, fromCellCol));
	}
	
	
	public Position getDestination() { return destination; }
	public Position getStart() { return start; }
	public int rows() { return numrows; }
	public int columns() { return numcols; }

	
}



