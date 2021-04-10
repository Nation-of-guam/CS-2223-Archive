package algs.hw1.speedruns;   // package bgkresge.hw1;

import algs.hw1.Coordinate;
import algs.hw1.Slicer;
import algs.hw1.api.ISlicerFinder;

import java.util.function.BiFunction;


public class SlicerFinder implements ISlicerFinder {
    private Slicer slicer;
    private int target;

    //keeps track of which rows can hold the target
    int[] rowHelper;
    //keeps track of which Coordinates in a given row can hold the target
    int[][] columnHelpers;

    //used to distinguish between the vertical and horizontal BAS
    enum Mode {
        ROW,
        COLUMN
    }

    //once the row that contains the target value is found, it is stored here
    int lockedRowReferenceIndex = -1;

    /**
     * Finds the Coordinate that contains the target value by using two Binary Array Search steps to find the row then
     * column of the target. This utilizes some structures that store which locations need to be included in the searches
     * in order to reduce probe count.
     *
     * @param s      the Slicer to be searched
     * @param target the target value to be searched for
     * @return the Coordinate that contains the target value
     */
    public Coordinate find(Slicer s, int target) {
        if (target == 0) {//initialize row and columnHelpers
            rowHelper = new int[s.N];
            columnHelpers = new int[s.N][s.N];
            for (int i = 0; i < s.N; i++) {
                rowHelper[i] = i;
            }
            for (int i = 0; i < s.N; i++) {
                columnHelpers[i] = copyArray(rowHelper);
            }
        }
        this.slicer = s;
        this.target = target;
        //Use a MBAS to find the row the target is in, ignoring column for now
        int row = findHelper(Mode.ROW);
        //Use a MBAS to find the column, using the row found earlier
        int column = findHelper(Mode.COLUMN);
        return new Coordinate(row, column);
    }

    private int findHelper(Mode mode) {
        //stores which function should be used based on what mode is specified
        BiFunction<Integer, Integer, Boolean> function = (mode == Mode.ROW) ? slicer::inTop : slicer::inLeft;
        //holds the helper that will actually be used to aid the BAS
        int[] relevantHelper = (mode == Mode.ROW) ? rowHelper : columnHelpers[rowHelper[lockedRowReferenceIndex]];
        int lowerBound = 0;
        int upperBound = relevantHelper.length - 1;
        int middleIndex;

        //Holds the result of the inspection method
        boolean isInSection;

        //Modified Binary Array Search (MBAS)
        while (lowerBound < upperBound) {
            //Same as (lowerBound + upperBound) / 2, but is resistant to overflow
            middleIndex = (lowerBound + upperBound) >>> 1;
            isInSection = function.apply(relevantHelper[middleIndex], target);

            if (!isInSection) {//Target is to the right of or below middleIndex, IF it exists in the Slicer
                lowerBound = middleIndex + 1;
            } else {//Target is to the left of or above middleIndex or IS middleIndex, IF it exists in the Slicer
                upperBound = middleIndex;//Is NOT middleIndex - 1, because the functions are INCLUSIVE
            }
        }

        if (lowerBound == upperBound) {//Target found! Hurray!
            if (mode == Mode.COLUMN) {
                /*removes the Coordinate that contains the target from the helper that holds the potential locations of
                 future targets*/
                int[] reducedColumnHelper = produceReducedArray(relevantHelper, lowerBound);
                columnHelpers[rowHelper[lockedRowReferenceIndex]] = reducedColumnHelper;
                if (reducedColumnHelper.length == 0) {/*if the last Coordinate of the row was just used, never look in
                     this row again*/
                    rowHelper = produceReducedArray(rowHelper, lockedRowReferenceIndex);
                }
            } else {
                //store the row found to be used later
                lockedRowReferenceIndex = lowerBound;
            }
            return relevantHelper[lowerBound];
        }
        throw new RuntimeException("Somehow managed to fail to find " + target + " in the Slicer, time to debug!");
    }

    /**
     * Produces a copy of an array.
     *
     * @param original the original array that will be copied
     * @return a copy of the original array
     */
    private int[] copyArray(int[] original) {
        int[] copy = new int[original.length];
        for (int i = 0; i < original.length; i++) {
            copy[i] = original[i];
        }
        return copy;
    }

    /**
     * Essentially performs the remove() function of ArrayLists.
     *
     * @param original the array that should have an index removed
     * @param index    the index that should be removed from the given array
     * @return the array without the specified index
     */
    private int[] produceReducedArray(int[] original, int index) {
        int[] newArray = new int[original.length - 1];//make a new smaller array
        for (int i = 0; i < original.length; i++) {
            if (i < index) {//nothing has changed yet
                newArray[i] = original[i];
            } else if (i > index) {//all entries have to move one to the left to fill the gap left by the removed index
                newArray[i - 1] = original[i];
            }
        }
        return newArray;
    }

    // You do not need to modify below this line.
    // ------------------------------------------
    public static void main(String[] args) {
        for (int i = 1; i < 20; i++) {
            Slicer s = new Slicer(i, 99);
            s.solver(new SlicerFinder());

            System.out.println(i + "\t" + s.getNumProbes());
        }
        System.out.println();
        for (int n = 1; n < 65; n *= 2) {
            Slicer s = new Slicer(n, 99);
            int numProbes = s.solver(new SlicerFinder());
            System.out.println(n + "\t" + numProbes);
        }
    }
}
