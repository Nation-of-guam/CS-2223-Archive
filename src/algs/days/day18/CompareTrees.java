package algs.days.day18;

import algs.days.day17.BST;
import algs.hw3.ShakespearePlay;
import edu.princeton.cs.algs4.StdRandom;

public class CompareTrees {

	public static void main(String[] args) {
		ascending();
		random();
		shakespeare();
	}
	
	static void ascending() {
		BST<Integer> bt = new BST<Integer>();
		AVL<Integer> avl = new AVL<Integer>();

		for (int i = 0; i < 100; i++) {
			bt.insert(i);
			avl.insert(i);
		}

		System.out.println("BST for ascending has height of " + bt.height());
		System.out.println("AVL for ascending has height of " + avl.height());
	}

	// random BST appears to be no worse than twice as bad.
	static void random() {
		BST<Double> bt = new BST<Double>();
		AVL<Double> avl = new AVL<Double>();

		for (int i = 0; i < 100000; i++) {
			double r = StdRandom.uniform();   // random value between 0 and 1.
			bt.insert(r);
			avl.insert(r);
		}

		System.out.println("BST for random has height of " + bt.height());
		System.out.println("AVL for random has height of " + avl.height());
	}


	// What about words from a Shakespeare play?
	// Can someone tell me what the word with the greatest depth is?
	// WRITE A PROGRAM TO DO THIS: Nice challenge problem.
	static void shakespeare() {
		BST<String> bt = new BST<String>();
		AVL<String> avl = new AVL<String>();

		// NOTE: DUPLICATE WORDS ARE ADDED AS SEPARATE NODES....
		ShakespearePlay sp = new ShakespearePlay(7);
		for (String s : sp) {
			
			bt.insert(s);
			avl.insert(s);
		}

		System.out.println("BST for Shakespeare of " + bt.height());
		System.out.println("AVL for Shakespeare of " + avl.height());

		// see if you can write a program using this API....
		// System.out.println(bt.deepestKey());
	}

}
