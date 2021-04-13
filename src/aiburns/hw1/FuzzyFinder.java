package aiburns.hw1;

import algs.hw1.api.*;
import algs.hw1.Coordinate;
import algs.hw1.FuzzySquare;

/**
 * Copy this class into your USERID.hw1 package and complete it.
 */
public class FuzzyFinder implements IFuzzySquareFinder {
	final int[] nullFuzzStatus = new int[]{-1, -1, -1, -1};

	int size, lastTarget;
	Coordinate lastCoords;

	/**
	 * Return the Coordinate(r,c) where target exists. If it is not in 
	 * the 2D array, return null.
	 * 
	 * You can inspect the contents of the array for fs using the probe3x3() method.
	 */
	public Coordinate find(FuzzySquare fs, int target) {
		if (size != fs.N && lastTarget < target){
			size = fs.N;
			lastCoords = null;
		}

		return bump(fs, target);
	}


	public Coordinate bump(FuzzySquare fs, int target){
		Coordinate toReturn = null;

		int e = 0;

		if (lastCoords == null){
			for(int r = 1; r < fs.N; r+=3) {
				for(int c = 1; c < fs.N; c+=3) {
					int fuzStatus = fs.probe3x3(r,c, target);
					if (fuzStatus == 5){
						return toReturn;
					} else if (fuzStatus == 0){
						toReturn = feelCol(fs, target, new Coordinate(r,c));
						if (r > 1){
							if (toReturn.column != size-1){
								lastCoords = new Coordinate(r,c);
							} else {
								lastCoords = new Coordinate(r,1);
							}
						}
						return toReturn;
					} else if (c == size - 3 && c%3 == 1){
						c--;
					}
				}
				if (r == size - 3 && (r%3 == 1)){
					r--;
				}
			}
		} else {
			for(int r = lastCoords.row; r < fs.N; r+=3) {
				for(int c = lastCoords.column; c < fs.N; c+=3) {
					int fuzStatus = fs.probe3x3(r,c, target);
					if (fuzStatus == 5){
						return toReturn;
					} else if (fuzStatus == 0){
						toReturn = feelCol(fs, target, new Coordinate(r,c));
						if (r > 1){
							if (toReturn.column != size-1){
								lastCoords = new Coordinate(r,c);
							} else {
								lastCoords = new Coordinate(r,1);
							}
						}
						return toReturn;
					} else if (c == size - 3 && c%3 == 1){
						c--;
					}
				}
				if (r == size - 3 && (r%3 == 1)){
					r--;
				}
			}
		}
		return toReturn;
	}

	/**
	 *
	 * @param fs
	 * @param target
	 * @return
	 */
	public Coordinate feelCol(FuzzySquare fs, int target, Coordinate center){
		boolean[] fuzStatus;
		int[] fuzStatusNum = nullFuzzStatus;
		int row = center.row;
		int col = center.column;



		if (row == size - 1){
			fuzStatusNum[0] = fs.probe3x3(row-2,col, target);
			if (col == size - 1){
				fuzStatusNum[3] = fs.probe3x3(row, col-2, target);

				fuzStatus = fuzStatusFiller(fuzStatusNum);

				if (fuzStatus[0]){
					if (fuzStatus[3]){
						return new Coordinate(row-1, col-1);
					} else {
						return new Coordinate(row-1, col);
					}
				} else if (fuzStatus[3]) {
					return new Coordinate(row, col-1);
				}
			} else if (col == 1) {
				fuzStatusNum[1] = fs.probe3x3(row,col+2, target);
				fuzStatusNum[3] = fs.probe3x3(row,col+1, target);
				fuzStatus = fuzStatusFiller(fuzStatusNum);
				fuzStatus[3] = !fuzStatus[3];

				if (fuzStatus[0]){
					if (fuzStatus[1]){
						return new Coordinate(row-1, col+1);
					} else if (fuzStatus[3]){
						return new Coordinate(row-1, col-1);
					} else {
						return new Coordinate(row-1, col);
					}
				} else if (fuzStatus[1]){
					return new Coordinate(row, col+1);
				} else if (fuzStatus[3]){
					return new Coordinate(row, col-1);
				}

			} else {
				fuzStatusNum[1] = fs.probe3x3(row,col + 2, target);
				fuzStatusNum[3] = fs.probe3x3(row, col-2, target);

				fuzStatus = fuzStatusFiller(fuzStatusNum);

				if (fuzStatus[0]){
					if (fuzStatus[1]){
						return new Coordinate(row-1, col+1);
					} else if (fuzStatus[3]){
						return new Coordinate(row-1, col-1);
					} else {
						return new Coordinate(row-1, col);
					}
				} else if (fuzStatus[1]){
					return new Coordinate(row, col+1);
				} else if (fuzStatus[3]){
					return new Coordinate(row, col-1);
				}
			}
		}  else if (row == 1) {
			if (col == size - 1){
				fuzStatusNum[0] = fs.probe3x3(row+1,col, target);
				fuzStatusNum[2] = fs.probe3x3(row+2,col, target);
				fuzStatusNum[3] = fs.probe3x3(row,col-2, target);

				fuzStatus = fuzStatusFiller(fuzStatusNum);
				fuzStatus[0] = !fuzStatus[0];


				if (fuzStatus[0]){
					if (fuzStatus[3]){
						return new Coordinate(row-1, col - 1);
					} else {
						return new Coordinate(row-1, col);
					}
				} else if (fuzStatus[2]){
					if (fuzStatus[3]){
						return new Coordinate(row + 1, col - 1);
					} else {
						return new Coordinate(row+1, col);
					}
				} else if (fuzStatus[3]){
					return new Coordinate(row, col-1);
				}
			} else if (col == 1) {
				fuzStatusNum[0] = fs.probe3x3(row + 1,col, target);
				fuzStatusNum[1] = fs.probe3x3(row,col + 2, target);
				fuzStatusNum[2] = fs.probe3x3(row+2,col, target);
				fuzStatusNum[3] = fs.probe3x3(row,col+1, target);

				fuzStatus = fuzStatusFiller(fuzStatusNum);
				fuzStatus[0] = !fuzStatus[0];
				fuzStatus[3] = !fuzStatus[3];

				if (fuzStatus[0]){
					if (fuzStatus[1]){
						return new Coordinate(row-1, col+1);
					} else if (fuzStatus[3]){
						return new Coordinate(row-1, col-1);
					} else {
						return new Coordinate(row-1, col);
					}
				} else if (fuzStatus[1]){
					if (fuzStatus[2]){
						return new Coordinate(row+1, col+1);
					} else {
						return new Coordinate(row, col+1);
					}
				} else if (fuzStatus[2]){
					if (fuzStatus[3]){
						return new Coordinate(row+1, col-1);
					} else {
						return new Coordinate(row+1, col);
					}
				} else if (fuzStatus[3]){
					return new Coordinate(row, col-1);
				}
			} else {
				fuzStatusNum[0] = fs.probe3x3(row+1,col, target);
				fuzStatusNum[1] = fs.probe3x3(row,col - 1, target);
				fuzStatusNum[2] = fs.probe3x3(row+2,col, target);
				fuzStatusNum[3] = fs.probe3x3(row, col-2, target);

				fuzStatus = fuzStatusFiller(fuzStatusNum);
				fuzStatus[0] = !fuzStatus[0];
				fuzStatus[1] = !fuzStatus[1];

				if (fuzStatus[0]){
					if (fuzStatus[1]){
						return new Coordinate(row-1, col+1);
					} else if (fuzStatus[3]){
						return new Coordinate(row-1, col-1);
					} else {
						return new Coordinate(row-1, col);
					}
				} else if (fuzStatus[1]){
					if (fuzStatus[2]){
						return new Coordinate(row+1, col+1);
					} else {
						return new Coordinate(row, col+1);
					}
				} else if (fuzStatus[2]){
					if (fuzStatus[3]){
						return new Coordinate(row+1, col-1);
					} else {
						return new Coordinate(row+1, col);
					}
				} else if (fuzStatus[3]){
					return new Coordinate(row, col-1);
				}
			}
		} else {
			if (col == 1) {
				fuzStatusNum[0] = fs.probe3x3(row-2,col, target);
				fuzStatusNum[1] = fs.probe3x3(row,col+2, target);
				fuzStatusNum[2] = fs.probe3x3(row-1,col, target);
				fuzStatusNum[3] = fs.probe3x3(row,col+1, target);

				fuzStatus = fuzStatusFiller(fuzStatusNum);
				fuzStatus[2] = !fuzStatus[2];
				fuzStatus[3] = !fuzStatus[3];

				if (fuzStatus[0]){
					if (fuzStatus[1]){
						return new Coordinate(row-1, col+1);
					} else if (fuzStatus[3]){
						return new Coordinate(row-1, col-1);
					} else {
						return new Coordinate(row-1, col);
					}
				} else if (fuzStatus[1]){
					if (fuzStatus[2]){
						return new Coordinate(row+1, col+1);
					} else {
						return new Coordinate(row, col+1);
					}
				} else if (fuzStatus[2]){
					if (fuzStatus[3]){
						return new Coordinate(row+1, col-1);
					} else {
						return new Coordinate(row+1, col);
					}
				} else if (fuzStatus[3]){
					return new Coordinate(row, col-1);
				}

			} else if (col == size - 1){
				fuzStatusNum[0] = fs.probe3x3(row-2,col, target);
				fuzStatusNum[2] = fs.probe3x3(row-1,col, target);
				fuzStatusNum[3] = fs.probe3x3(row,col-2, target);

				fuzStatus = fuzStatusFiller(fuzStatusNum);
				fuzStatus[2] = !fuzStatus[2];


				if (fuzStatus[0]){
					if (fuzStatus[3]){
						return new Coordinate(row-1, col - 1);
					} else {
						return new Coordinate(row-1, col);
					}
				} else if (fuzStatus[2]){
					if (fuzStatus[3]){
						return new Coordinate(row + 1, col - 1);
					} else {
						return new Coordinate(row+1, col);
					}
				} else if (fuzStatus[3]){
					return new Coordinate(row, col-1);
				}
			} else{
				fuzStatusNum[0] = fs.probe3x3(row-2,col, target);
				fuzStatusNum[1] = fs.probe3x3(row,col - 1, target);
				fuzStatusNum[2] = fs.probe3x3(row+2,col, target);
				fuzStatusNum[3] = fs.probe3x3(row, col-2, target);

				fuzStatus = fuzStatusFiller(fuzStatusNum);
				fuzStatus[1] = !fuzStatus[1];

				if (fuzStatus[0]){
					if (fuzStatus[1]){
						return new Coordinate(row-1, col+1);
					} else if (fuzStatus[3]){
						return new Coordinate(row-1, col-1);
					} else {
						return new Coordinate(row-1, col);
					}
				} else if (fuzStatus[1]){
					if (fuzStatus[2]){
						return new Coordinate(row+1, col+1);
					} else {
						return new Coordinate(row, col+1);
					}
				} else if (fuzStatus[2]){
					if (fuzStatus[3]){
						return new Coordinate(row+1, col-1);
					} else {
						return new Coordinate(row+1, col);
					}
				} else if (fuzStatus[3]){
					return new Coordinate(row, col-1);
				}
			}
		}


		return center;
	}


	public boolean[] fuzStatusFiller(int[] fuzStatus){
		boolean[] toReturn = new boolean[4];
		for (int i = 0; i < fuzStatus.length; i++){
			toReturn[i] = (fuzStatus[i] == 0);
		}
		return toReturn;
	}

	/**
	 *
	 * @param coordinate
	 * @return
	 */
	public boolean inBounds(Coordinate coordinate){
		return ((coordinate.row >= 0 && coordinate.row < size)&&(coordinate.column >= 0 && coordinate.column < size));
	}


	// You do not need to modify below this line.
	// ------------------------------------------
	public static void main(String[] args) {

		for (int i = 5; i < 400; i++) {
			FuzzySquare fs = new FuzzySquare(i, 99);
			fs.solver(new FuzzyFinder());
			System.out.println(i + "\t" + fs.getNumProbes());
		}
	}
}
