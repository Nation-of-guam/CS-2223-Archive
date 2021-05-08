package algs.hw2.submission;

import algs.hw2.Card;
import algs.hw2.Deck;
import algs.hw2.Node;
import algs.hw2.Suit;

/**
 * COPY THIS CLASS into your development area and complete it.
 * @author Home
 *
 */
public class SampleDeck extends Deck {
	int N;
	
	/**
	 * Ensure that no one OUTSIDE of this class invokes the no-argument constructor. You will find
	 * it useful to have this constructor within the copy() method since it must return an accurate
	 * copy of the current Deck, and it will first need to construct an "empty" MyDeck object
	 * without using the MyDeck(int max_rank) constructor.
	 * 
	 */
	protected SampleDeck() {
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
	public SampleDeck(int max_rank) {
		N = 0;
		for (Suit suit : Suit.values()) {
			for (int rank = 1; rank <= max_rank; rank++) {
				Card card = new Card(suit, rank);
				N++;
				
				if (first == null) {
					first = new Node(card);
					last = first;
				} else {
					last.next = new Node(card);
					last = last.next;
				}
			}
		}
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
	public SampleDeck copy() {
		throw new RuntimeException("To Be Completed By Student");
	}

	@Override
	public int size() {
		throw new RuntimeException("To Be Completed By Student");
	}

	/** 
	 * This operation will return the first node in a linked list of 
	 * Card Nodes while MODIFYING THE CURRENT DECK TO ONLY CONTAIN THE 
	 * CARDS IN THE BOTTOM HALF OF THE DECK.
	 * 
	 * Returned linked list retains only the first N/2 elements.
	 * 
	 * IF THIS METHOD IS CALLED WHEN THE DECK HAS AN ODD NUMBER OF CARDS,
	 * THEN IT MUST THROW A RuntimeException for protection.
	 * 
	 * Note that this method is never called externally, so you are in control
	 * of when you call it.
	 * 
	 * Performance must be O(N) where N is the number of cards in the deck.
	 */
	@Override
	protected Node cutInHalf() {
		Node node = first;
		for (int i = 0; i < N/2 - 1; i++) {
			node = node.next;
		}
		
		N = N/2;
		Node toReturn = first;
		first = node.next;
		node.next = null;
		return toReturn;
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
