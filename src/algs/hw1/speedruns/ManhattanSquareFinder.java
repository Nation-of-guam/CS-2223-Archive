package algs.hw1.speedruns;  // package bgkresge.hw1;

import algs.hw1.Coordinate;
import algs.hw1.ManhattanSquare;
import algs.hw1.api.IManhattanSquareFinder;

public class ManhattanSquareFinder implements IManhattanSquareFinder {
    ManhattanSquare ms;
    //keeps track of which locations are still "alive," meaning they can hold the current target value
    boolean[][] locations;
    //keeps track of how many targets have been found so far
    int numLocationsFound = 0;

    /**
     * This algorithm works much like a GPS system. It implicitly finds the set of coordinates that are the correct
     * distance away from one known location. This would form a sort of circle around the known location if visualized.
     * It then "produces another circle" around a second known location, based on a distance found by inspection method.
     * There would normally be two points of intersection, but one of these points is always guaranteed to be outside of
     * the bounds of the problem. GPS works the same way, but in 3D rather than 2D, and it discards the intersection that
     * exists out in the middle of space somewhere. The remaining point of intersection must then be the solution for
     * both GPS and this algorithm.
     *
     * @param ms     the ManhattanSquare to be searched
     * @param target the target value to be searched for
     * @return the Coordinate that contains the target value
     **/
    public Coordinate find(ManhattanSquare ms, int target) {
        if (target == 0) {//initializes the matrix that will be used later
            this.ms = ms;
            locations = new boolean[ms.N][ms.N];
            for (boolean[] row : locations) {
                for (int i = 0; i < ms.N; i++) {
                    row[i] = true;
                }
            }
        }
        if (numLocationsFound == ms.N * ms.N - 1) {/*no need to search for the last target, there is only one possible
        location left*/
            for (int row = 0; row < ms.N; row++) {
                for (int column = 0; column < ms.N; column++) {
                    if (locations[row][column] == true) {
                        return new Coordinate(row, column);
                    }
                }
            }
        }
        //upperBound is the highest value row and column can have within the ManhattanSquare
        int upperBound = ms.N - 1;
        //Manhattan distance between the top left corner and the coordinate that holds the target
        int topLeftDistance = ms.distance(0, 0, target);
        if (topLeftDistance == upperBound * 2) {//Target is at (N-1,N-1)
            numLocationsFound++;
            locations[upperBound][upperBound] = false;
            return new Coordinate(upperBound, upperBound);
        }
        if (topLeftDistance == 0) {//Target is at (0,0)
            numLocationsFound++;
            locations[0][0] = false;
            return new Coordinate(0, 0);
        }
        Coordinate singleResult = getSingleResult(topLeftDistance);
        if (singleResult != null) {//hopefully there is only one Coordinate that could produce this distance
            return singleResult;
        }

        //Unfortunately, we must use one additional probe to find which of the Coordinates in the diagonal is correct
        //Manhattan distance between the bottom left corner and the coordinate that holds the target
        int bottomLeftDistance = ms.distance(upperBound, 0, target);

        /*Now we check each Coordinate in the diagonal defined by the bottomLeftDistance one at a time to see if it is
        the correct distance from (0,0)*/
        //deltaColumn is how many right steps are taken from the bottom left corner
        for (int deltaColumn = 0; deltaColumn <= bottomLeftDistance; deltaColumn++) {
            //deltaRow is the number of up steps taken from the bottom left corner
            //deltaColumn + deltaRow must equal the bottomLeftDistance by definition of Manhattan distance
            int deltaRow = bottomLeftDistance - deltaColumn;
            //The row you get to after taking deltaRow steps up from the bottom left corner
            int row = upperBound - deltaRow;
            //The column you get to after taking deltaColumns steps right from the bottom left corner
            int column = deltaColumn;
            /* Since the top left corner is (0,0), the number of steps to get to a certain row or column is
             * equal to that row or column's number itself. The distance from the top left corner to any other
             * point is the sum of the number of steps taken to the right, and the number of steps taken
             * downwards. Therefore, given a (row,column) coordinate, the Manhattan distance from the top left corner
             * and that coordinate can be found by adding the row and column together. */
            if (row + column == topLeftDistance) {//Target is found! Hurray!
                numLocationsFound++;
                locations[row][column] = false;
                return new Coordinate(row, column);
            }
        }
        throw new RuntimeException("Somehow managed to fail to find " + target + " in the ManhattanSquare, time to debug!");
    }

    /**
     * Attempt to single out the only Coordinate that can be a certain distance from (0,0). This will work IFF the diagonal
     * defined by the distance only has one valid Coordinate left.
     *
     * @param distance the distance from (0,0) that defines the diagonal of potential Coordinates
     * @return the single Coordinate that is possible in the diagonal, or null if there are more than one possibility
     */
    private Coordinate getSingleResult(int distance) {
        Coordinate currentResult = null;
        for (int right = distance; right >= 0; right--) {
            int down = distance - right;
            if (right < ms.N && down < ms.N) {
                if (locations[down][right] == true) {
                    if (currentResult != null) {
                        return null;
                    } else {
                        currentResult = new Coordinate(down, right);
                    }
                }
            }
        }
        return currentResult;
    }


    // You do not need to modify below this line.
    // ------------------------------------------
    public static void main(String[] args) {
        for (int n = 1; n < 14; n++) {
            ManhattanSquare ms = new ManhattanSquare(n, 99);
            int numProbes = ms.solver(new ManhattanSquareFinder());
            System.out.println(n + "\t" + numProbes);
        }
    }
}
