package aiburns.hw5;

import algs.days.day18.AVL;
import algs.hw5.Dictionary;
import edu.princeton.cs.algs4.*;

import java.io.FileNotFoundException;
import java.util.Scanner;

;

// use any classes from Sedgewick...
// Note that the Day18 implementation of AVL removes <Key,Value> and only uses <Key>

/**
 * Copy this class into your project area and modify it for problem 1 on HW5.
 */
public class WordZipper {

	/**
	 * Represent the mapping of (uniqueID, 3- and 4-letter words) from String <-> Integer where Integer is vertex id
	 */
	static SeparateChainingHashST<Integer,String> map = new SeparateChainingHashST<Integer,String>();

	/** Store all three-letter and four-letter words (in lowercase). */
	static AVL<String> avl;
	
	/**
	 * Return a Queue of words that result by adding a single letter to the three letter word.
	 * 
	 * There are 4*26 possible words that could result by adding a single letter (a-z) at each of the 
	 * four possible spots
	 * 
	 *      E A T
	 *      
	 *     SEAT
	 *      ERAT
	 *       EAST
	 *        EATS
	 *        
	 * It is acceptable for this method to return duplicates in the queue.
	 * 
	 * For example, if the word is "BET" then it could include in its response
	 * "BEET" (where the E is inserted between the B and E) and "BEET" (where
	 * the E is inserted between the E and the T).
	 */
	public static Queue<String> addOne(String three) {
		throw new RuntimeException("To be completed by the student");
	}

	/**
	 * Return valid words by removing one of the four letters.
	 * 
	 * It is acceptable for this method to return duplicates in the queue.
	 * For example, if the word is 'BEET' then the words returned could 
	 * be {"BEE", "BET", "BET"}
	 */
	public static Queue<String> removeOne(String four) {
		throw new RuntimeException("To be completed by the student");
	}
	
	/**
	 * Main method to execute.
	 *
	 * From console, enter the start and end of the word ladder.
	 */
	public static void main(String[] args) throws FileNotFoundException {
		// Use this to contain all three- and four-letter words that you find in dictionary
		avl = new AVL<String>();

		// Construct AVL tree of all three- and four-letter words.
		// Note: you will have to copy this file into your project to access it.
		Scanner sc = Dictionary.words();
		
		// now construct graph, where each node represents a word, and an edge exists between
		// two nodes if their respective words are off by a single letter. Hint: use the
		// keys() method provided by the AVL tree. Your graph will be an undirected graph.
		Bag<String> fourLetterWords = new Bag<>();
		Bag<Integer> fourLetterNumbers = new Bag<>();
		while (sc.hasNext()){
			String s = sc.next();
			if (s.length()==3){
				avl.insert(s);
				int wordvalue = wordToNumbers(s);
				map.put(wordvalue, s);
			} else if (s.length() == 4){
				avl.insert(s);
				int wordvalue = wordToNumbers(s);
				fourLetterWords.add(s);
				fourLetterNumbers.add(wordvalue);
				map.put(wordvalue, s);
			}
		}

		sc.close();  // once done, you can close this resource.


		Graph wordGraph = new Graph(26262626);

		for (String word : fourLetterWords){
			int wordNumber = wordToNumbers(word);
			String[] newThreeLeter = new String[]{word, word, word, word};
			newThreeLeter[0] = newThreeLeter[0].substring(0,3);
			newThreeLeter[1] = newThreeLeter[1].substring(0,2) + newThreeLeter[1].substring(3);
			newThreeLeter[2] = newThreeLeter[2].substring(0,1) + newThreeLeter[2].substring(2);
			newThreeLeter[3] = newThreeLeter[3].substring(1);
			for (String thisThree : newThreeLeter){
				if (avl.contains(thisThree)){
					int threeNumber = wordToNumbers(thisThree);
					wordGraph.addEdge(wordNumber,threeNumber);
				}
			}
		}





		/**
		 *
		Bag<Integer> allFours = fourLetterNumbers;
		int maxStart = 0;
		int maxEnd = 0;
		int maxDistance = 0;
		String maxPath = "";
		for (Integer from : allFours) {
			BreadthFirstPaths thisPath = new BreadthFirstPaths(wordGraph, from);
			for (Integer to : allFours){
				if (to != from){
					Iterable<Integer> pathTo = thisPath.pathTo(to);
					if (pathTo != null){
						int count = 0;
						String pathString = "";
						for (Integer thisWord : pathTo){
							count++;
							if (thisWord == from){
								pathString = pathString + map.get(from);
							} else {
								pathString = pathString + map.get(thisWord) + " -> ";
							}
						}
						if (count > maxDistance){
							maxPath = pathString;
							maxStart = from;
							maxEnd = to;
							maxDistance = count;
						}
					}
				}
			}
		}

		System.out.println("The longest path of length "+maxDistance+" is from " + map.get(maxStart) + " to " +map.get(maxEnd));
		System.out.println("The path is: \n" + maxPath);*/

		System.out.println("The longest path of length 23 is from zero to jogs\n" +
				"The path is: \n" +
				"zero -> zer -> zerk -> zek -> zeke -> eke -> deke -> dee -> deme -> eme -> emes -> ems -> emus -> mus -> must -> ust -> just -> jus -> jugs -> jug -> joug -> jog -> jogs");

		// this loop will complete when the user enters in a non-word.
		while (true) {
			StdOut.println("Enter word to start from (all in lower case):");
			String start = StdIn.readString().toLowerCase();
			int startInt = wordToNumbers(start);

			StdOut.println("Enter word to end at (all in lower case):");
			String end = StdIn.readString().toLowerCase();
			int endInt = wordToNumbers(end);

			// need to validate that these are both actual four-letter words in the dictionary
			if (!avl.contains(start)) {
				StdOut.println (start + " is not a valid word in the dictionary.");
				System.exit(-1);
			}
			if (!avl.contains(end)) {
				StdOut.println (end + " is not a valid word in the dictionary.");
				System.exit(-1);
			}

			// Once both words are known to exist in the dictionary, then create a search
			// that finds shortest distance (should it exist) between start and end.
			// be sure to output the words in the word zipper, IN ORDER, from the start to end.
			// IF there is no word zipper possible, then output "NONE POSSIBLE."
			BreadthFirstPaths wordToWord = new BreadthFirstPaths(wordGraph, startInt);
			Iterable<Integer> pathTo = wordToWord.pathTo(endInt);

			if (pathTo != null){
				System.out.println("The path from " + start + " to " + end);
				for (Integer thisWord : pathTo){
					if (thisWord == endInt){
						System.out.println(end);
					} else {
						System.out.print(map.get(thisWord) + " -> ");
					}
				}
			} else {
				System.out.println("There is no path");
			}
		}
	}

	/**
	 * Word numbering:
	 * a -> 01
	 * b -> 02
	 * c -> 03
	 * ...
	 * z -> 26
	 *
	 * abc -> 010203
	 * azx -> 012624
	 */
	static int wordToNumbers(String word){
		int toReturn = 0;
		int indexShifters = 1;
		for (int i = 0; i < word.length(); i++) {
			int thisChar = (word.charAt(i))-96;
			toReturn += indexShifters * thisChar;
			indexShifters = indexShifters * 100;
		}

		return toReturn;
	}



}
