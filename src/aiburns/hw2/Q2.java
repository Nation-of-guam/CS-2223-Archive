package aiburns.hw2;

// You will need these when you copy this class file into your USERID.hw2 area.
import algs.hw2.*;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.SequentialSearchST;

import java.lang.reflect.InvocationTargetException;

/**
 * For this assignment, you should only have to modify the part where it says
 * 
 * "HERE IS WHERE YOUR LOGIC GOES."
 * 
 */
public class Q2 {
	static int[] inResetTimes;
	static int[] outResetTimes;
	/**
	 * Find all deals that bring each card to the top. Since there are multiple ones, any that
	 * you find (which can be validated) are allowed.
	 */
	public static void findDeals() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		SequentialSearchST<Card, Deck> ordered = new SequentialSearchST<>();
		SequentialSearchST<Card, String> shuffles = new SequentialSearchST<>();
		
		// Change below to instantiate YOUR DECK NOT MY FRAGMENTARY EXAMPLE

		inResetTimes = new int[25];
		outResetTimes = new int[25];

		for (int i = 1; i < inResetTimes.length; i++) {
			MyDeck referenceDeck = new MyDeck(i);

			inResetTimes[i] = DeckTester.countingTests(referenceDeck,
					MyDeck.class.getMethod("in"),
					MyDeck.class.getMethod("isInOrder"));
			outResetTimes[i] = DeckTester.countingTests(referenceDeck,
					MyDeck.class.getMethod("out"),
					MyDeck.class.getMethod("isInOrder"));
		}

		Deck deck = new MyDeck(10);


		
		// Start your search from the initial state, where the current shuffle is "" (empty)
		// Record that (for the top card which is the AC) this is the deck
		State state = new State(deck, "");
		shuffles.put(deck.peekTop(), "");
		ordered.put(deck.peekTop(), deck);
		
		Queue<State> queue = new Queue<>();
		queue.enqueue(state);
		
		// Until you have an entry for every possible card, continue your search
		while (ordered.size() < deck.size()) {
			// HERE IS WHERE YOUR LOGIC GOES.....
		}
		
		for (Card c : new AllCards()) {
			System.out.println(c + "\t" + shuffles.get(c));
		}
	}

	public static void main(String[] args) {
		try {
			findDeals();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	} 
}
