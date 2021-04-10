package algs.days.day04.anagram;


import java.util.Hashtable;
import java.util.Stack;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

/** 
 * Subdivide words into bins by length.
 * 
 * @author George Heineman
 */
public class StringBuilderHeinemanFinder {

	// store letters here and the words from the dictionary.
	static char[] letters;
	static StringBuilder[][] words;  // StringBuilder/StringBuffer don't have hashCode???

	// maximum number of anagrams to discover
	static int MAX_NUM_ANAGRAMS = 1000; 
	
	static int ctr = 0;
	StringBuffer sb;
	
	// existing words that have been found as anagrams.
	static Hashtable<Integer,StringBuilder> anagrams = new Hashtable<>();

	/** See if there exists a word in a[] whose prefix matches key for the designated prefixSize characters. */
	public static boolean matchPrefix(StringBuilder key, StringBuilder[] a, int prefixSize) {
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
			StringBuilder matchS = a[mid];
			for (int ch = 0; ch < prefixSize; ch++) {
				// too short? can't say anything. This means before, so set cmp to -1
				if (ch >= matchS.length()) {
					match = false; 
					cmp = -1;
					break;
				}

				// or not match at that position? leave
				cmp = matchS.charAt(ch) - key.charAt(ch);
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
	public static int rank(StringBuilder key, StringBuilder[] a) {
		int lo = 0;
		int hi = a.length -1;
		int lenkey = key.length();
		while (lo <= hi) {
			int mid = lo + (hi - lo) / 2;
			StringBuilder smid = a[mid];
			// ARRGH. StringBUffer has no CompareTo
			 
	        int len2 = a[mid].length();
	        int lim = Math.min(lenkey, len2);
	       

	        int k = 0;
	        int cmp = 0;
	        while (k < lim) {
	            char c1 = key.charAt(k);
	            char c2 = smid.charAt(k);
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
	static int largestPrefixNonDictionaryPrefix(StringBuilder word) {
		for (int i = 1; i <= letters.length; i++) {
			if (!matchPrefix(word, words[word.length()], i)) {
				return i;
			}
		}

		return letters.length;
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
	static void process(Stack<Integer> positions) {
		
		// instead of using recursion, keep the stack and manipulate it constantly. 
		while (positions.size() > 0) {
			
			// We have a full stack representing a permutation, see if we have a word or we can short-circuit.
				
			// StringBuilder is faster for appending characters.
			StringBuilder word = new StringBuilder();
			for (int pos : positions) {
				word.append(letters[pos]);
			}
			
			ctr++;
			
			// if the constructed word exists in dictionary (i.e., its rank is not -1), then add to our
			// collection of anagrams. Do this way to prevent duplicates.
			if (rank(word, words[word.length()]) != -1) {
				System.out.println(word.hashCode());
				anagrams.put(word.hashCode(), word);  // cheat a bit -- and hope two anagrams don't have same hashcode.
				
				// return once done...
				if (anagrams.size() > MAX_NUM_ANAGRAMS) {
					return;
				}
			} else {
				// otherwise, find our largest prefix which is not the prefix for any word in the dictionary.
				// we can short-circuit the process from that point, which we do by removing letters from the
				// stack and continuing on to the next opportunity if possible.
				int shortCircuit = largestPrefixNonDictionaryPrefix(word);
				while (shortCircuit < positions.size()) {
					positions.pop();
				}
			}
			
			// advance! Do this by popping last one off and try to refill back to 15. If fail, because there
			// are no more options, pop off previous one and continue. Stop when FULL or EMPTY.
			int next = positions.pop()+1;
			while (positions.size() != 0 && positions.size() != letters.length) {

				// try to fill back up to max size.
				while (positions.size() != letters.length) {
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
		words = new StringBuilder[MaxLength][];
		for (String s : sample) {
			lengths[s.length()]++;
		}
		for (int i = 0; i < MaxLength; i++) {
			words[i] = new StringBuilder[lengths[i]];
		}
		for (String s : sample) {
			words[s.length()][--lengths[s.length()]] = new StringBuilder(s);
		}

		// note that if a letter appears multiple times in the input, there
		// could be multiple reports of the same word, since this only 
		// considers permutations. To prevent this, once a word is discovered
		// it is stored to prevent multiple reports of the same word.
		
		StdOut.println("Enter letters for which you want a SINGLE word anagram");
		String word = StdIn.readString().toLowerCase();
		letters = word.toCharArray();
		
		Stack<Integer> positions = new Stack<Integer>();
		for (int i = 0; i < letters.length; i++) {
			positions.push(i);
		}
		Stopwatch sw = new Stopwatch();
		process(positions);
		
		System.out.println("Here are the anagrams.");
		for (StringBuilder s : anagrams.values()) {
			System.out.println(s);
		}
		System.out.println("total time:" + sw.elapsedTime() + " with " + ctr + " lookups.");
	}
}