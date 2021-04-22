package algs.days.day18;

import edu.princeton.cs.algs4.SeparateChainingHashST;
import edu.princeton.cs.algs4.StdRandom;

public class CountingObject {
	public static void main(String[] args) {

		// key is the random number that was generated
		// value is frequency or the count
		SeparateChainingHashST<Integer, Integer>  table = new SeparateChainingHashST<>();
		
		// Bernoulli independent trials
		int T = 10000;
		
		for (int i = 0; i < T; i++) {
			int idx = StdRandom.uniform(20);
			
			// count how many times I've seen this number
			// if table HAS the key ...
			//     ... increment count associated with that key
			// if not ...
			//     ... then put (idx, 1) into the Table
			Integer oldValue = table.get(idx);
			if (oldValue != null) {
				table.put(idx, oldValue + 1);
			} else {
				table.put(idx, 1);
			}
		}
		
		// retrieve from the hash table how many times the index was seen
		for (int idx = 0; idx < 20; idx++) {
			System.out.println(idx + "\t" + table.get(idx));
		}
	}
}
