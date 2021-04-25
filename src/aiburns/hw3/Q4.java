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
        int differentNumbers = 40;
        AVL<Integer>[] randomTrees = new AVL[differentNumbers*10000];
        ArrayList<Integer> randInts = new ArrayList<Integer>();
        ArrayList<Integer> randInts1 = new ArrayList<Integer>();
        ArrayList<Integer> randInts2 = new ArrayList<Integer>();
        ArrayList<Integer> randInts3 = new ArrayList<Integer>();
        ArrayList<Integer> randInts4 = new ArrayList<Integer>();



        try {
            Thread thread1 = new Thread() {
                public void run() {
                    for (int k = 1; k <= differentNumbers/4; k++) {
                        randInts1.add(k);
                        for (int i = (k-1)*10000; i < k*10000; i++) {
                            randomTrees[i] = new AVL<Integer>();
                            Collections.shuffle(randInts1);
                            for (int j = 0; j < randInts1.size(); j++) {
                                randomTrees[i].insert(randInts1.get(j));
                            }
                        }
                    }
                }
            };

            Thread thread2 = new Thread() {
                public void run() {
                    for (int i = 1; i <= (differentNumbers/4); i++) {
                        randInts2.add(i);
                    }
                    for (int k = (differentNumbers/4)+1; k <= (differentNumbers/2); k++) {
                        randInts2.add(k);
                        for (int i = (k-1)*10000; i < k*10000; i++) {
                            randomTrees[i] = new AVL<Integer>();
                            Collections.shuffle(randInts2);
                            for (int j = 0; j < randInts2.size(); j++) {
                                randomTrees[i].insert(randInts2.get(j));
                            }
                        }
                    }
                }
            };
            Thread thread3 = new Thread() {
                public void run() {
                    for (int i = 1; i <= (differentNumbers/2); i++) {
                        randInts3.add(i);
                    }
                    for (int k = (differentNumbers/2)+1; k <= ((differentNumbers*3)/4); k++) {
                        randInts3.add(k);
                        for (int i = (k-1)*10000; i < k*10000; i++) {
                            randomTrees[i] = new AVL<Integer>();
                            Collections.shuffle(randInts3);
                            for (int j = 0; j < randInts3.size(); j++) {
                                randomTrees[i].insert(randInts3.get(j));
                            }
                        }
                    }
                }
            };
            Thread thread4 = new Thread() {
                public void run() {
                    for (int i = 1; i <= (((differentNumbers*3)/4)); i++) {
                        randInts4.add(i);
                    }
                    for (int k = ((differentNumbers*3)/4)+1; k <= differentNumbers; k++) {
                        randInts4.add(k);
                        for (int i = (k-1)*10000; i < k*10000; i++) {
                            randomTrees[i] = new AVL<Integer>();
                            Collections.shuffle(randInts4);
                            for (int j = 0; j < randInts4.size(); j++) {
                                randomTrees[i].insert(randInts4.get(j));
                            }
                        }
                    }
                }
            };

            // Start the downloads.
            thread1.start();
            thread2.start();
            thread3.start();
            thread4.start();

            thread1.join();
            thread2.join();
            thread3.join();
            thread4.join();


        } catch (Exception e){
            System.out.println("It doesn't like something about all of these threads for some reason");
            /*
             * I have made this
             */
            for (int k = 1; k <= differentNumbers; k++) {
                randInts.add(k);
                for (int i = (k-1)*10000; i < k*10000; i++) {
                    randomTrees[i] = new AVL<Integer>();
                    Collections.shuffle(randInts);
                    for (int j = 0; j < randInts.size(); j++) {
                        randomTrees[i].insert(randInts.get(j));
                    }
                }
            }
        }




        int[][] height = new int[differentNumbers][];
        for (int i = 0; i < height.length; i++){
            height[i] = new int[]{0,0};
        }

        for (int i = 0; i < randomTrees.length; i++) {
            int h = (i/10000);
            if (height[h][0] < randomTrees[i].height()){
                height[h][0] = randomTrees[i].height();
                height[h][1] = 1;
            } else if (height[h][0] == randomTrees[i].height()) {
                height[h][1]++;
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

        System.out.println("\n\nAre these values one less than [or are very close to] the N = (Fibonacci(Largest Height + 3) - 1)" +
                ", with larger numbers, the 10,000 trials start to be not enough to keep up with it.");
        System.out.println("The ideal table with the relation of N and largest height is: ");

        System.out.println("Largest Height \t \tN");
        for (int i = 1; i < 12; i++){
            System.out.println((i) + ":  \t\t\t\t" + (fibonacci_recursive(i+3)-1) );
        }
    }

    /** Standard inefficient recursive implementation. There are many ways to improve this. */
    static long fibonacci_recursive(int n) {
        // base case
        if (n == 0) { return 0; }
        if (n == 1) { return 1; }

        return fibonacci_recursive(n-1) + fibonacci_recursive(n-2);
    }
}
