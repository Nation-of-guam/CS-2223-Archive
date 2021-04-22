package algs.days.day04.anagram;


import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.Hashtable;

/** 
 * Subdivide words into bins by length.
 * 
 * @author George Heineman
 */
public class RawArraysHeinemanFinder {

	// store letters here and the words from the dictionary.
	static char[] letters;
	
	// three dimension
	// first is LENGTH of the word
	// second is the array of letters 
	// third is the letter array
	static char[][][] words;   // triple-dimension

	// maximum number of anagrams to discover
	static int MAX_NUM_ANAGRAMS = 1000; 
	
	// letters under suspicion;
	static MyFixedCapacityStack positions;
	static int N;
	
	static int ctr = 0;
	StringBuffer sb;
	
	// existing words that have been found as anagrams.
	static Hashtable<Integer,String> anagrams = new Hashtable<>();

	/** See if there exists a word in a[] whose prefix matches key for the designated prefixSize characters. */
	public static boolean matchPrefix(char[][] a, int prefixSize) {
		int lo = 0;
		int hi = a.length -1;
		// NOTE: in reverse order...
		while (lo <= hi) {
			int mid = lo + (hi - lo) / 2;

			// Compares the first 'prefixSize' characters of key and a[mid].
			// this could likely be optimized to avoid a second check to key.compare() but deal with later.
			// we want to see if we have hit upon a word whose prefix (for prefixSize characters) matches
			// the desired key for whom we are looking for a prefix of designated length.
			//
			// see if the first 'prefixSize' characters of key matches any prefix of same 
			// size for any word in a[] that we see in our binary search.
			boolean match = true;
			int cmp = 0;
			char[] matchS = a[mid];
			for (int ch = 0; ch < prefixSize; ch++) {
				// too short? can't say anything. This means before, so set cmp to -1
				if (ch >= matchS.length) {
					match = false; 
					cmp = -1;
					break;
				}

				// or not match at that position? leave
				cmp = matchS[ch] - letters[positions.a[ch]];
				if (cmp != 0) { match = false; break; }
			}
			if (match) { return true; }
			
			// thereafter, simply continue the binary search 

			if (cmp > 0) lo = mid + 1;
			else if (cmp < 0) hi = mid - 1;
			else return true; // a match! so prefix is a match too. Shouldn't get here
		}

		return false;
	}

	/** Look up word via REVERSE binary array search. */
	public static int rank(char[][] a) {
		int lo = 0;
		int hi = a.length -1;
		
		while (lo <= hi) {
			int mid = lo + (hi - lo) / 2;
			char[] smid = a[mid];
			// ARRGH. StringBUffer has no CompareTo
			 
	        int k = 0;
	        int cmp = 0;
	        while (k < N) {
	            char c1 = letters[positions.a[k]];
	            char c2 = smid[k];
	            if (c1 != c2) {
	                cmp = c1-c2;
	                break;
	            }
	            k++;
	        }
	      
			if (cmp > 0) hi = mid - 1;
			else if (cmp < 0) lo = mid + 1;
			else return mid;
		}

		return -1;
	}

	/** Find largest prefix which IS NOT a prefix to any word in the dictionary. */
	static int largestPrefixNonDictionaryPrefix() {
		for (int i = 1; i <= N; i++) {
			if (!matchPrefix(words[N], i)) {
				return i;
			}
		}

		return letters.length;
	}

	// stolen from String
	static int myHashCode() {
		int h = 0;
        for (int i = 0; i < N; i++) {
           h = 31 * h + letters[positions.a[i]];
        }
        return h;
	}
	
	static String formWord() {
		StringBuilder sb = new StringBuilder(N);
		for (int i = 0; i < N; i++) {
			sb.append(letters[positions.a[i]]);
		}
		return sb.toString();
	}
	
	
	/**
	 * Construct iteratively a stack 'candidate' which contains a permutation of the letters in canonical 
	 * order (that is, starting with 0,1,2,3,...,n-1 and eventually getting to n-1,n-2,...,2,1,0
	 * 
	 * Should a sample word NOT exist, then find that word's largest prefix which is not the prefix for 
	 * any word in the dictionary. Short-circuit the evaluation to deny any further investigations with 
	 * that prefix and advance the last letter to the next valid position, if possible.
	 * 
	 * Note: I had initially had a recursive solution, but it ran out of stack space because of the deep
	 * recursive calls necessary. This tricky bit of logic was the hard-fought result of wrestling the
	 * recursion into an iterative solution. 
	 */
	static void process() {
		
		// instead of using recursion, keep the stack and manipulate it constantly. 
		while (positions.N > 0) {
			
			// We have a full stack representing a permutation, see if we have a word or we can short-circuit.
			ctr++;
			
			// if the constructed word exists in dictionary (i.e., its rank is not -1), then add to our
			// collection of anagrams. Do this way to prevent duplicates.
			if (rank(words[N]) != -1) {
				int hc = myHashCode();
				if (!anagrams.containsKey(hc)) {
					anagrams.put(hc, formWord());  // cheat a bit -- and hope two anagrams don't have same hashcode.
				}
				
				// return once done...
				if (anagrams.size() > MAX_NUM_ANAGRAMS) {
					return;
				}
			} else {
				// otherwise, find our largest prefix which is not the prefix for any word in the dictionary.
				// we can short-circuit the process from that point, which we do by removing letters from the
				// stack and continuing on to the next opportunity if possible.
				int shortCircuit = largestPrefixNonDictionaryPrefix();
				while (shortCircuit < positions.N) {
					positions.pop();
				}
			}
			
			// advance! Do this by popping last one off and try to refill back to 15. If fail, because there
			// are no more options, pop off previous one and continue. Stop when FULL or EMPTY.
			int next = positions.pop()+1;
			while (positions.N != 0 && positions.N != letters.length) {

				// try to fill back up to max size.
				while (positions.N != letters.length) {
					while (next < letters.length) {
						if (!positions.contains(next)) {
							break;
						}
						next++;
					}

					// advance the prior value
					if (next == letters.length) {
						// if no more positions we are done!
						if (positions.isEmpty()) { break; }
						
						next = positions.pop() + 1;
						continue;
					}
					
					positions.push(next);
					next = 0;
				}
			}
			
			// if we get to 15 we are ready to try again...
		}
	}


	public static void main(String[] args) {
		In in = new In ("words.english.txt");
		String[] sample = in.readAllStrings();
		int MaxLength = 30;
		int lengths[] = new int[MaxLength];
		
		// when this is done words[k] will have all k-letter words in reverse order.
		words = new char[MaxLength][][];
		for (String s : sample) {
			lengths[s.length()]++;
		}
		for (int i = 0; i < MaxLength; i++) {
			words[i] = new char[lengths[i]][];
		}
		for (String s : sample) {
			int idx = --lengths[s.length()];
			words[s.length()][idx] = new char[s.length()];
			for (int i = 0; i < s.length(); i++) { 
				words[s.length()][idx][i] = s.charAt(i);
			}
		}

		// note that if a letter appears multiple times in the input, there
		// could be multiple reports of the same word, since this only 
		// considers permutations. To prevent this, once a word is discovered
		// it is stored to prevent multiple reports of the same word.
		
		StdOut.println("Enter letters for which you want a SINGLE word anagram");
		String word = StdIn.readString().toLowerCase();
		letters = word.toCharArray();
		
		positions = new MyFixedCapacityStack(word.length());
		for (int i = 0; i < letters.length; i++) {
			positions.push(i);
		}
		Stopwatch sw = new Stopwatch();
		N = word.length();  
		process();
		
		System.out.println("Here are the anagrams.");
		for (String s : anagrams.values()) {
			System.out.println(s);
		}
		System.out.println("total time:" + sw.elapsedTime() + " with " + ctr + " lookups.");
	}
}