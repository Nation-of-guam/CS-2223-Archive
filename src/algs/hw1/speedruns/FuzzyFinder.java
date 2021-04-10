package algs.hw1.speedruns;  // bgkresge.hw1;

import algs.hw1.Coordinate;
import algs.hw1.FuzzySquare;
import algs.hw1.api.IFuzzySquareFinder;


public class FuzzyFinder implements IFuzzySquareFinder {
    private int squareSize;
    private FuzzySquare fs;
    int target;

    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    //Should the cheeky method to find Coordinates be used?
    boolean useCheekyFind = true;//<---CHANGE ME TO TRUE TO ACHIEVE BEST PROBE COUNT
    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    /**
     * Returns the Coordinate in the given FuzzySquare that contains the given target value if it exists in the square.
     * Otherwise, returns null. This is achieved through a slightly more optimized Binary ArraySearch (BAS) that
     * uses the ABOVE and BELOW values from probe to go "left" and "right" respectively. This implementation also utilizes
     * the ranges defined by M1 and M2 to further reduce the range of indices to be searched.
     *
     * @param fs     the FuzzySquare instance that is being searched through
     * @param target the value that is being searched for
     * @return the Coordinate in the given FuzzySquare that contains the given target value if it exists in the square.
     * Otherwise, returns null.
     */
    public Coordinate find(FuzzySquare fs, int target) {//IDK if I can even call this mess a BAS anymore...
        squareSize = fs.N;
        this.fs = fs;
        this.target = target;
        if (useCheekyFind == true) {//re-route to the cheeky method if the useCheekyFind flag is true
            return cheekyFind();
        }
        int lowerBoundIndex = 0;
        int upperBoundIndex = coordinateToIndex(new Coordinate(squareSize - 1, squareSize - 1));
        //The actual BAS starts here
        while (upperBoundIndex - lowerBoundIndex >= 3) {/*normally would be > 3 not >= 3, since horizontalResolve() can
            deal with up to 3 coordinates, but this change reduces the probe count for 13x13 seed 99 by almost 100*/

            //Same as (lowerBoundIndex + upperBoundIndex) / 2, but is resistant to overflow
            int middleIndex = (lowerBoundIndex + upperBoundIndex) >>> 1;
            Coordinate probeLocation = indexToCoordinate(middleIndex);
            //Stores the result of probing the "middle" Coordinate
            int probeResult = probeByCoordinate(probeLocation);

            /*used to define the Coordinate in the 3x3 area of a probe that is next to a Coordinate of interest. For
            example, if the probeResult is ABOVE we care about the Coordinate that is to the left of the top-left
            Coordinate of the 3x3 probe area, therefore, referencePoint would hold the top-left Coordinate of the 3x3
            probe area*/
            Coordinate referencePoint;

            switch (probeResult) {
                case FuzzySquare.FOUND://Target hit!
                    return enhanceResolution(probeLocation);
                case FuzzySquare.NOT_PRESENT://Impossible for the square to contain target
                    return null;
                case FuzzySquare.ABOVE://Target is above (or to the left from index POV) IF it exists in the square
                    referencePoint = shiftCoordinate(probeLocation, -1, -1);
                    upperBoundIndex = coordinateToIndex(nextValidCoordinate(referencePoint, -1));
                    break;
                case FuzzySquare.BELOW://Target is below (or to the right from index POV) IF it exits in the square
                    referencePoint = shiftCoordinate(probeLocation, 1, 1);
                    lowerBoundIndex = coordinateToIndex(nextValidCoordinate(referencePoint, 1));
                    break;
                case FuzzySquare.M1://Range can be reduced down to only the indices in M1
                    referencePoint = shiftCoordinate(probeLocation, -1, 1);
                    //Apparently this max needs to be here? No clue why...
                    lowerBoundIndex = Math.max(lowerBoundIndex, coordinateToIndex(nextValidCoordinate(referencePoint, 1)));
                    referencePoint = shiftCoordinate(probeLocation, 0, -1);
                    upperBoundIndex = coordinateToIndex(nextValidCoordinate(referencePoint, -1));
                    break;
                case FuzzySquare.M2://Range can be reduced down to only the indices in M2
                    referencePoint = shiftCoordinate(probeLocation, 0, 1);
                    //Apparently this max needs to be here? No clue why...
                    lowerBoundIndex = Math.max(lowerBoundIndex, coordinateToIndex(nextValidCoordinate(referencePoint, 1)));
                    referencePoint = shiftCoordinate(probeLocation, 1, -1);
                    upperBoundIndex = coordinateToIndex(nextValidCoordinate(referencePoint, -1));
            }
        }
        return horizontalResolve(lowerBoundIndex, upperBoundIndex, false);/*All we know is that we no longer
        have a large enough range of indices to make using the BAS worthwhile. The remaining range may or may not include
        the target value.*/
    }

    /**
     * Returns the Coordinate of the FuzzySquare that contains the target value if it exists, otherwise null.
     * This is achieved by resolving 3 or fewer potential and sequential indices into either null, or the Coordinate
     * representation of the index that contains the target value.
     * <p></p>
     * Worst case when the range MUST contain the target is 2 probes. Otherwise, 3 probes when the range MIGHT contain
     * the target.
     *
     * @param min         the minimum index that could contain the target
     * @param max         the maximum index that could contain the target
     * @param mustContain if true, the range [min,max] MUST contain the target value
     * @return the Coordinate of the FuzzySquare that contains the target value if it exists, otherwise <null></null>
     */
    private Coordinate horizontalResolve(int min, int max, boolean mustContain) {/*Might cause problems with non in-line
         inputs, watch out!*/
        int delta = max - min;
        if (delta < 0) {//went through the entire range without finding the target value (base case)
            return null;
        }
        if (delta == 0 && mustContain) {//was given one coordinate that must contain the value
            return indexToCoordinate(min);
        }
        //use the Coordinate one up, and one to the left to see if the min value index contains the target value
        int probeResult = probeByCoordinate(shiftCoordinate(indexToCoordinate(min), -1, -1));
        if (probeResult == FuzzySquare.FOUND) {//min index does contain the target value
            return indexToCoordinate(min);
        }
        return horizontalResolve(++min, max, mustContain);//recursively call itself, eliminating min from the range
    }

    /**
     * Identifies which of the 9 Coordinates in the 3x3 probe area is responsible for a FOUND result. Uses a max of 2
     * probes to do so. If the probe was squished against one of the sides, there is a 50% chance that the function will
     * only require 1 probe.
     *
     * @param sourceLocation the Coordinate that produced FOUND when probed
     * @return the Coordinate that contains the target value
     */
    private Coordinate enhanceResolution(Coordinate sourceLocation) {
        //diagram of potential Coordinates that could contain the target value (Fig. 1):
        //   x A B C x
        //   1 D E F 2
        //   x G H I x
        //E is the sourceLocation, which is the Coordinate which produced FOUND when probed
        //1 and 2 are the two Coordinates to probe to get more information
        //x denotes Coordinates that cannot contain the target and are not useful
        //A-I are the Coordinates that could contain the target

        //stores the result of the most recent probe
        int probeResult;
        if (sourceLocation.column > 0) {//the 3x3 area is NOT squished against the left side
            //probe at Coordinate 1 from Fig. 1
            probeResult = probeByCoordinate(shiftCoordinate(sourceLocation, 0, -2));

            boolean onEdge = sourceLocation.column == squareSize - 1;/*Is the sourceLocation squished against the right
            side of the FuzzySquare? If so, then the new area to search is only 3x2 since the target can't be contained
            to the right of the sourceLocation, eliminating C,F,and I. That looks like this: */
            //   x A B x x
            //   1 D E x 2
            //   x G H x x

            //used to account for the squished case
            int secondIndexOffset = onEdge ? 0 : 1;
            //the left most index in the row found to contain the target
            int firstIndex;
            switch (probeResult) {
                case FuzzySquare.M1://must be B or C
                    firstIndex = coordinateToIndex(shiftCoordinate(sourceLocation, -1, 0));
                    return horizontalResolve(firstIndex, firstIndex + secondIndexOffset, true);
                case FuzzySquare.M2://must be E or F
                    firstIndex = coordinateToIndex(sourceLocation);
                    return horizontalResolve(firstIndex, firstIndex + secondIndexOffset, true);
                case FuzzySquare.BELOW://must be H or I
                    firstIndex = coordinateToIndex(shiftCoordinate(sourceLocation, 1, 0));
                    return horizontalResolve(firstIndex, firstIndex + secondIndexOffset, true);
                case FuzzySquare.FOUND://must be A,D, or G
                    //probe Coordinate F
                    probeResult = probeByCoordinate(shiftCoordinate(sourceLocation, 0, 1));
                    switch (probeResult) {
                        case FuzzySquare.ABOVE://A contains target
                            return shiftCoordinate(sourceLocation, -1, -1);
                        case FuzzySquare.M1://D contains target
                            return shiftCoordinate(sourceLocation, 0, -1);
                        case FuzzySquare.M2://G contains target
                            return shiftCoordinate(sourceLocation, 1, -1);
                    }
            }
        } else {//We know the section of the square is squished against the left side, resulting in a 3x2 area instead
            // x x B C x
            // 1 x E F 2
            // x x H I x

            //probe at Coordinate 2
            probeResult = probeByCoordinate(shiftCoordinate(sourceLocation, 0, 2));
            switch (probeResult) {
                case FuzzySquare.ABOVE://must be B
                    return shiftCoordinate(sourceLocation, -1, 0);
                case FuzzySquare.M1://must be E
                    return sourceLocation;
                case FuzzySquare.M2://must be H
                    return shiftCoordinate(sourceLocation, 1, 0);
                case FuzzySquare.FOUND://must be C,F, or I
                    //probe Coordinate D
                    probeResult = probeByCoordinate(shiftCoordinate(sourceLocation, 0, -1));
                    switch (probeResult) {
                        case FuzzySquare.M1://must be C
                            return shiftCoordinate(sourceLocation, -1, 1);
                        case FuzzySquare.M2://must be F
                            return shiftCoordinate(sourceLocation, 0, 1);
                        case FuzzySquare.BELOW://must be I
                            return shiftCoordinate(sourceLocation, 1, 1);
                    }
            }
        }
        throw new RuntimeException("Something went terribly wrong in enhanceResolution() with source:" + sourceLocation);
    }

    /**
     * Attempts to give the Coordinate asked for, but if that Coordinate is outside of the FuzzySquare, returns the
     * closest valid Coordinate to what was asked for.
     *
     * @param origin the Coordinate that serves as the anchor for delta to act upon
     * @param delta  how far and in which direction from the origin Coordinate the desired Coordinate is
     * @return the closest valid Coordinate to what was asked for
     */
    private Coordinate nextValidCoordinate(Coordinate origin, int delta) {
        if (origin.row >= 0 && origin.row < squareSize && origin.column >= 0 && origin.column < squareSize)
            return indexToCoordinate(coordinateToIndex(origin) + delta);//the desired coordinate is already valid
        int column = delta > 0 ? 0 : squareSize - 1;//decides which way to loop the Coordinate to give the closest one
        return new Coordinate(origin.row + delta, column);
    }

    /**
     * Returns the result of shifting a given Coordinate in a specified way (without consuming the original Coordinate)
     *
     * @param original    the Coordinate that serves as the starting point for the shift
     * @param deltaRow    the direction and amount to shift the original Coordinate in the y-axis
     * @param deltaColumn the direction and amount to shift the original Coordinate in the x-axis
     * @return the Coordinate that results from shifting the original Coordinate by the given number of rows and columns
     */
    private Coordinate shiftCoordinate(Coordinate original, int deltaRow, int deltaColumn) {
        return new Coordinate(original.row + deltaRow, original.column + deltaColumn);
    }

    /**
     * Utility function used to reduce the necessity of decomposing a Coordinate to rows and columns in order to probe.
     * <p></p>
     * NOTE: Utilizes the global variable "target" set in find() to further reduce parameters required
     *
     * @param probeLocation the Coordinate that the probe should be centered at
     * @return the result of a normal probe at this location
     */
    private int probeByCoordinate(Coordinate probeLocation) {
        return fs.probe3x3(probeLocation.row, probeLocation.column, target);
    }

    /**
     * Facilitates the conversion from 1D mapping of the problem to 2D mapping.
     *
     * @param index the 1D index that should be converted
     * @return the Coordinate that corresponds with the given index
     */
    private Coordinate indexToCoordinate(int index) {
        return new Coordinate(index / squareSize, index % squareSize);
    }

    /**
     * Allows the 2D FuzzySquare to be represented by 1D, index based mapping.
     *
     * @param coordinate the 2D Coordinate that should be converted
     * @return the 1D index that corresponds with the given Coordinate
     */
    private int coordinateToIndex(Coordinate coordinate) {
        return coordinate.row * squareSize + coordinate.column;
    }

    //holds the index the last found target was located at (used for cheekyFind())
    int c_lastFoundIndex = -1;
    //holds the value of the last found target (used for cheekyFind())
    int c_lastFound = -1;

    /**
     * Uses multiple attack vectors in conjunction to achieve minimal probe counts.
     *
     * @return the Coordinate that contains the current target if the target exists in the FuzzySquare, otherwise null
     */
    private Coordinate cheekyFind() {//note that target is not passed as a parameter, since it is a global variable
        if (c_lastFoundIndex == squareSize * squareSize - 1) {//already found all the values
            return null;
        } else {//still more to go
            if (target - c_lastFound == 8) {/*values will never be more than 8 apart
            (A[r][c] = last + 1 + rand.nextInt(8);)<--- found on line 124 in the initialize(int) method of FuzzySquare*/
                c_lastFound = target;
                return indexToCoordinate(++c_lastFoundIndex);
            } else {
                if (probeByCoordinate(shiftCoordinate(indexToCoordinate(c_lastFoundIndex + 1), -1, -1))
                        == FuzzySquare.FOUND) {/*checks if the target is at the next possible index by probing the
                        Coordinate to the top-left of the next possible index*/
                    c_lastFound = target;
                    return indexToCoordinate(++c_lastFoundIndex);
                }
            }
        }
        return null;
    }


    // You do not need to modify below this line.
    // ------------------------------------------
    public static void main(String[] args) {
        for (int i = 1; i < 41; i++) {
            FuzzySquare fs = new FuzzySquare(i, 99);
            fs.solver(new FuzzyFinder());
            System.out.println(i + "\t" + fs.getNumProbes());
        }
    }
}
