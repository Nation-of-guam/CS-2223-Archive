package algs.hw2.submission;

import algs.hw2.Card;
import algs.hw2.Deck;
import algs.hw2.Node;

/**
 * COPY THIS CLASS into your development area and complete it.
 * @author Home
 *
 */
public class MyDeck extends Deck {
	
	/**
	 * Ensure that no one OUTSIDE of this class invokes the no-argument constructor. You will find
	 * it useful to have this constructor within the copy() method since it must return an accurate
	 * copy of the current Deck, and it will first need to construct an "empty" MyDeck object
	 * without using the MyDeck(int max_rank) constructor.
	 * 
	 */
	protected MyDeck() {
		// You do not need to modify this method. This constructor exists to ensure that 
		// within this class, you can construct an empty MyDeck whose first and last are null.
	}
	
	/** 
	 * Construct a playing deck with {max_rank} cards in specific order.
	 * 
	 * Once done, the linked list of card Nodes must represent a deck that looks like the following (if 
	 * {max_rank} were 3). The suites are ordered Club < Diamond < Hearts < Spades.
	 * 
	 * AC -> 2C -> 3C -> AD -> 2D -> 3D -> AH -> 2D -> 3H -> AS -> 2S -> 3S
	 * 
	 * Note your deck will have 4*{max_rank} cards.
	 * 
	 * Performance must be O(N) where N is max_rank.
	 */
	public MyDeck(int max_rank) {
		if (max_rank < Card.ACE || max_rank > Card.KING) { throw new IllegalArgumentException("max_rank must be between " + Card.ACE + " and " + Card.KING + " respectively"); }
		
		throw new RuntimeException("To Be Completed By Student");
	}

	@Override
	public Card peekTop() {
		throw new RuntimeException("To Be Completed By Student");
	}

	@Override
	public Card peekBottom() {
		throw new RuntimeException("To Be Completed By Student");
	}

	@Override
	public boolean match(Card c, int n) {
		throw new RuntimeException("To Be Completed By Student");
	}
	
	@Override
	public Deck copy() {
		throw new RuntimeException("To Be Completed By Student");
	}

	@Override
	public int size() {
		throw new RuntimeException("To Be Completed By Student");
	}

	@Override
	protected Node cutInHalf() {
		throw new RuntimeException("To Be Completed By Student");
	}

	@Override
	public void out() {
		throw new RuntimeException("To Be Completed By Student");
	}

	@Override
	public void in() {
		throw new RuntimeException("To Be Completed By Student");
	}

	@Override
	public String representation() {
		throw new RuntimeException("To Be Completed By Student");
	}
	
	@Override
	public boolean isInOrder() {
		throw new RuntimeException("To Be Completed By Student");
	}

	@Override
	public boolean isInReverseOrder() {
		throw new RuntimeException("To Be Completed By Student");
	}
}
