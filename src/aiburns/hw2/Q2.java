package aiburns.hw2;

// You will need these when you copy this class file into your USERID.hw2 area.
import algs.hw2.AllCards;
import algs.hw2.Card;
import algs.hw2.Deck;
import algs.hw2.State;
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
	static int inResetTimes;
	static int outResetTimes;
	/**
	 * Find all deals that bring each card to the top. Since there are multiple ones, any that
	 * you find (which can be validated) are allowed.
	 */
	public static void findDeals() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		SequentialSearchST<Card, Deck> ordered = new SequentialSearchST<>();
		SequentialSearchST<Card, String> shuffles = new SequentialSearchST<>();
		
		// Change below to instantiate YOUR DECK NOT MY FRAGMENTARY EXAMPLE
		int length = 5;



		Deck deck = new MyDeck(length);


		inResetTimes = DeckTester.countingTests(deck.copy(),
				MyDeck.class.getMethod("in"),
				MyDeck.class.getMethod("isInOrder"));
		outResetTimes = DeckTester.countingTests(deck.copy(),
				MyDeck.class.getMethod("out"),
				MyDeck.class.getMethod("isInOrder"));


		
		// Start your search from the initial state, where the current shuffle is "" (empty)
		// Record that (for the top card which is the AC) this is the deck
		State state = new State(deck, "");
		shuffles.put(deck.peekTop(), "");
		ordered.put(deck.peekTop(), deck);
		
		Queue<State> queue = new Queue<>();
		queue.enqueue(state);
		


		// Until you have an entry for every possible card, continue your search
		while (ordered.size() < deck.size()) {
			State mainState = queue.dequeue();


			if (3*inResetTimes < mainState.shuffle.chars().filter(i -> i == 'I').count()){
				if (3*outResetTimes < mainState.shuffle.chars().filter(i -> i == 'O').count()){

				} else {
					String outString = mainState.shuffle + "O";
					Deck out = mainState.deck.copy();
					out.out();
					queue.enqueue(new State(out, outString));

					if (shuffles.get(out.peekTop()) == null){
						shuffles.put(out.peekTop(), outString);
						ordered.put(out.peekTop(), out);
					} else if (shuffles.get(out.peekTop()).length() > outString.length()) {
						shuffles.put(out.peekTop(), outString);
						ordered.put(out.peekTop(), out);
					}
				}
			} else if (3*outResetTimes < mainState.shuffle.chars().filter(i -> i == 'O').count()) {
				String inString = mainState.shuffle + "I";
				Deck in = mainState.deck.copy();
				in.in();
				queue.enqueue(new State(in, inString));

				if (shuffles.get(in.peekTop()) == null){
					shuffles.put(in.peekTop(), inString);
					ordered.put(in.peekTop(), in);

				} else if (shuffles.get(in.peekTop()).length() > inString.length()) {
					shuffles.put(in.peekTop(), inString);
					ordered.put(in.peekTop(), in);

				}

			} else {
				String inString = mainState.shuffle + "I";
				String outString = mainState.shuffle + "O";

				Deck in = mainState.deck.copy();
				in.in();
				Deck out = mainState.deck.copy();
				out.out();

				queue.enqueue(new State(in, inString));
				queue.enqueue(new State(out, outString));

				if (shuffles.get(in.peekTop()) == null){
					shuffles.put(in.peekTop(), inString);
					ordered.put(in.peekTop(), in);
				} else if (shuffles.get(in.peekTop()).length() > inString.length()) {
					shuffles.put(in.peekTop(), inString);
					ordered.put(in.peekTop(), in);

				}

				if (shuffles.get(out.peekTop()) == null){
					shuffles.put(out.peekTop(), outString);
					ordered.put(out.peekTop(), out);

				} else if (shuffles.get(out.peekTop()).length() > outString.length()) {
					shuffles.put(out.peekTop(), outString);
					ordered.put(out.peekTop(), out);

				}
			}
		}
		
		for (Card c : new AllCards()) {
			System.out.println( c + "\t" + shuffles.get(c));
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
