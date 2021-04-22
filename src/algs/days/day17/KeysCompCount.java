package algs.days.day17;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class KeysCompCount {
	public static void main(String[] args) {
		for (int N = 1; N <= 32768; N *= 2) {
			BST<Integer> b = new BST<Integer>();
			
			Integer[] vals = new Integer[N];
			for (int i = 0; i < N; i++) {
				vals[i] = i;
			}
			StdRandom.shuffle(vals);
			for (int v : vals) {
				b.insert(v);
			}
			
			b.keys();
			StdOut.println (N + "\t" + b.numCmp);
		}
	}
}
