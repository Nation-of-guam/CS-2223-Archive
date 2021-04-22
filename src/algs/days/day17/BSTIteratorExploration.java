package algs.days.day17;

import edu.princeton.cs.algs4.StdOut;

public class BSTIteratorExploration {
	public static void main(String[] args) {
		// construct binary tree on p. 401
		BST<String> bst = new BST<String>();

		bst.insert("R");
		bst.insert("U");
		bst.insert("A");
		bst.insert("T");
		bst.insert("W");
		bst.insert("P");
		bst.insert("I");

		// This is very different from the traversal, since we are able
		// to act on the keys, whereas the traversal simply printed the
		// values out to the screen.
		for (String s : bst.keys()) {
			StdOut.print(s);
			if (isVowel(s.charAt(0))) { 
				StdOut.println(" is a vowel");
			} else {
				StdOut.println();
			}
		}
	}
	
	/** Determine if character is a vowel... */
	static boolean isVowel(char ch) {
		return "aeiouyAEIOUY".indexOf(ch) != -1;
	}
}
