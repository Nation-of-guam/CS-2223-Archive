package aiburns.hw3;

import algs.days.day18.AVL;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Aidan Burns  4/23/2021
 * This project does Q4 on the IntelliJ IDEA
 */

public class Q4 {
    public static void main(String[] args) {

        AVL<Integer>[] randomTrees = new AVL[400000];
        ArrayList<Integer> randInts = new ArrayList<Integer>();

        for (int k = 1; k < 41; k++) {
            randInts.add(k);
            for (int i = (k-1)*10000; i < k*10000; i++) {
                randomTrees[i] = new AVL<Integer>();
                Collections.shuffle(randInts);
                for (int j = 0; j < randInts.size(); j++) {
                    randomTrees[i].insert(randInts.get(j));
                }
            }
        }


        int[][] height = new int[40][];
        for (int i = 0; i < height.length; i++){
            height[i] = new int[]{0,0};
        }

        for (int i = 0; i < randomTrees.length; i++) {
            if (height[(i/10000)][0] < randomTrees[i].height()){
                height[i/10000][0] = randomTrees[i].height();
                height[i/10000][1] = 1;
            } else if (height[i/10000][0] == randomTrees[i].height()) {
                height[i/10000][1]++;
            }
        }

        System.out.println("N \t \tLargest Height \tNumber Found");
        int lastLargestHeight = 0;
        for (int i = 1; i < height.length; i++){
            if (height[i][0] > lastLargestHeight){
                System.out.println((i + 1) + ":  \t" + height[i][0] + "\t\t\t\t " + height[i][1]);
                lastLargestHeight = height[i][0];
            }
        }
    }
}
