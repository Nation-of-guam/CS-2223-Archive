package algs.days.day18;

import java.util.ArrayList;
import java.util.Collections;

public class ExploreAVLWorstCase {
	public static void main(String[] args) {
		
		// 7  -->  8
		// 15 --> 22
		// 31 --> 52
		// 63 --> 114
		// 127 --> 240
		// 255 --> 494
		// 511 --> 1004
		int MAX = 1023; 
		
		System.out.println("N\tMaxrotations");
		for (int i = 3; i <= MAX; i = 2*i + 1) {
			trial(i);
		}
	}
	
	private static ArrayList<Double> trial(int n) {
		ArrayList<Double> orderToInsert = new ArrayList<>();
		
		// start in middle.
		orderToInsert.add(100.0);
		for (int i = 0; i < n; i++) {
			double next = expand(orderToInsert);
			orderToInsert.add(next);
		}
		
		// can always recreate the order...
		//System.out.println(orderToInsert);
		
		AVL<Double> avl = new AVL<Double>();
		for (double o : orderToInsert) { avl.insert(o); }
		System.out.println(n + "\t" + avl.rotations);
		return orderToInsert;
	}



	private static double expand(ArrayList<Double> orderToInsert) {
		// for each min value, subtract 10
		// for each max value, add 10
		
		ArrayList<Double> copy = new ArrayList<Double>();
		copy.addAll(orderToInsert);
		Collections.sort(copy);
		
		
		// for all other values BETWEEN, take midpoint.
		ArrayList<Double> candidates = new ArrayList<>();
		candidates.add(copy.get(0) - 10);
		
		// now do in between
		int n = orderToInsert.size();
		for (int i = 0; i < n-1; i++) {
			candidates.add((copy.get(i+1) + copy.get(i))/2.0);
		}
		
		// now extend
		candidates.add(copy.get(n-1) + 10);
		
		int max = -1;
		double maxBest = Integer.MIN_VALUE;
		for (double c : candidates) {
			AVL<Double> avl = new AVL<Double>();
			for (double o : orderToInsert) { avl.insert(o); }
			
			// now try to insert and count rotations
			avl.rotations = 0;
			avl.insert(c);
			if (avl.rotations > max) {
				max = avl.rotations;
				maxBest = c;
			}
		}
		
		// go ahead and add
		return maxBest;
	}
}
