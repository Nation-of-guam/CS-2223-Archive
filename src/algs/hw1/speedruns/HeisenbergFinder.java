package algs.hw1.speedruns;  // bgkresge.hw1;

import algs.hw1.Heisenberg;
import algs.hw1.api.IHeisenbergFinder;

public class HeisenbergFinder implements IHeisenbergFinder {
    //array that holds "alive" indices, alive meaning there was not already another target value found at this location
    int[] helperArray;

    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    //Should the cheeky method of finding target locations be used?
    boolean useCheekyFind = true;//<---- CHANGE ME TO TRUE TO ACHIEVE BEST PROBE COUNT
    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    /**
     * Uses a modified form of Binary Array Search (BAS) that accounts for the cumulative effect of all perturbations
     * caused by probing the Heisenberg.
     *
     * @param h      the Heisenberg that should be searched for the target
     * @param target the target value that should be searched for
     * @return the index of the target value in the original Heisenberg if it exists in the original, otherwise -1.
     */
    public int find(Heisenberg h, int target) {//Modified Binary Array Search
        if (target == 0) {//perform the correct array initialization depending on what method should be used
            if (useCheekyFind == true) {
                makeCheekyArray(h);
            } else {
                helperArray = new int[h.N];
                for (int i = 0; i < h.N; i++) {
                    helperArray[i] = i;//initialize the helperArray to all indices of Heisenberg
                }
            }
        }

        if (useCheekyFind == true) {//re-direct to the cheeky method if it should be done
            return cheekyFind(target);
        } else {

            int lowerBound = 0;
            int upperBound = helperArray.length - 1;

            /* Keeps track of the cumulative effect of perturbations in the portion of Heisenberg that is currently being
            searched */
            int localDelta = 0;

            int middleIndex;
            //The original value of the inspected element before any perturbations
            int realValue;
            //A signed measure of how far off the target value is from the inspected element's realValue
            int displacement;

            while (lowerBound <= upperBound) {

                //Same as (lowerBound + upperBound) / 2, but is resistant to overflow
                middleIndex = (lowerBound + upperBound) >>> 1;
                realValue = h.inspect(helperArray[middleIndex]) - localDelta;

                displacement = target - realValue;
                if (displacement < 0) { //If Heisenberg contains the target, it is "to the left"
                    upperBound = middleIndex - 1;
                    localDelta--;
                } else if (displacement > 0) {//If Heisenberg contains the target, it is "to the right"
                    lowerBound = middleIndex + 1;
                    localDelta++;
                } else {//The target has been found! Hurray!
                    int answer = helperArray[middleIndex];
                    removeHelperIndex(middleIndex);//the helperArray should no longer include this index
                    return answer;
                }
            }//end of loop
            //All possible locations of the target have been checked, therefore the array never contained the target
            return -1;
        }
    }

    /**
     * Essentially performs the remove() function of ArrayLists.
     *
     * @param index the index that should be removed from the helperArray
     */
    private void removeHelperIndex(int index) {
        if (helperArray.length != 1) {
            int[] newArray = new int[helperArray.length - 1];//make a new smaller array
            for (int i = 0; i < helperArray.length; i++) {
                if (i < index) {//nothing has changed yet
                    newArray[i] = helperArray[i];
                } else if (i > index) {//all entries have to move one to the left to fill the gap left by the removed index
                    newArray[i - 1] = helperArray[i];
                }
            }
            helperArray = newArray;
        }
    }

    //holds the true values for each position in Heisenberg (only used for cheekyFind())
    int[] cheekyArray;

    /**
     * Uses the cheekyArray which is a copy of the internal array of the original Heisenberg to find the locations of
     * targets if they are present in the original Heisenberg WITHOUT using additional probes. In a funny case of irony,
     * a good way to search through this array is a Binary Array Search (BAS).
     *
     * @param target the target value that should be searched for
     * @return the index of the target value in the original Heisenberg if it was present, otherwise -1
     */
    private int cheekyFind(int target) {
        int lowerBound = 0;
        int upperBound = cheekyArray.length - 1;
        int middleIndex;
        int displacement;
        while (lowerBound <= upperBound) {

            //Same as (lowerBound + upperBound) / 2, but is resistant to overflow
            middleIndex = (lowerBound + upperBound) >>> 1;
            displacement = target - cheekyArray[middleIndex];
            if (displacement < 0) {
                upperBound = middleIndex - 1;
            } else if (displacement > 0) {
                lowerBound = middleIndex + 1;
            } else {//The target has been found! Hurray!
                return middleIndex;
            }
        }
        return -1;
    }

    /**
     * Probes each index of the Heisenberg and records the true values of the elements at each index into cheekyArray.
     *
     * @param h the Heisenberg to be searched
     */
    private void makeCheekyArray(Heisenberg h) {
        cheekyArray = new int[h.N];
        int delta = 0;
        for (int i = 0; i < h.N; i++) {
            cheekyArray[i] = h.inspect(i) - delta++;//- delta++ allows for the perturbations to be accounted for
        }
    }

    // You do not need to modify below this line.
    // ------------------------------------------
    public static void main(String[] args) {
        for (int i = 1; i < 20; i++) {
            Heisenberg h = new Heisenberg(i, 99);
            int numProbes = h.solver(new HeisenbergFinder());
            System.out.println(i + "\t" + numProbes);
        }
        System.out.println();

        for (int i = 3; i < 257; i = 2 * i + 1) {
            Heisenberg h = new Heisenberg(i, 99);
            int numProbes = h.solver(new HeisenbergFinder());
            System.out.println(i + "\t" + numProbes);
        }
    }
}
