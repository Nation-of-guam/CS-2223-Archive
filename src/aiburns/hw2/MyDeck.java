package aiburns.hw2;

import algs.hw2.Card;
import algs.hw2.Deck;
import algs.hw2.Node;

/**
 * COPY THIS CLASS into your development area and complete it.
 * @author Home
 *
 */
public class MyDeck extends Deck {
	protected Node first;
	protected Node last;
	protected int size;

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

		first = new Node(new Card("1C"));
		Node cur = first;
		size = 1;
		for (int j = 0; j < 4; j++) {
			int finalJ = j;
			for (int i = 1; i < max_rank; i++) {
				if (i == 1 && j == 1){
					i++;
				} else {
					String actualRealRepresentationOfTheCard = "";
					switch (j) {
						case 0:
							actualRealRepresentationOfTheCard = i + "C";
							break;
						case 1:
							actualRealRepresentationOfTheCard = i + "D";
							break;
						case 2:
							actualRealRepresentationOfTheCard = i + "H";
							break;
						case 3:
							actualRealRepresentationOfTheCard = i + "S";
							break;
						default:
							break;
					}

					cur.next = new Node(new Card(actualRealRepresentationOfTheCard));
					cur = cur.next;
					size++;
				}
			}
		}
		last = cur;
	}





	@Override
	public Card peekTop() {
		return first.card;
	}

	@Override
	public Card peekBottom() {
		return last.card;
	}

	@Override
	public boolean match(Card c, int n) {
		for (int i = 0; i < size; i++) {

		}
		return false;
	}

	@Override
	public Deck copy() {
		try{
			return (Deck) this.clone();
		} catch (CloneNotSupportedException e){
			Deck toReturn = new MyDeck(size/4);
			return toReturn;
		}
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	protected Node cutInHalf() {
		Node toReturn = first;
		if (size % 2 == 1){
			throw new RuntimeException("haha no middle of odd cards");
		} else if (size == 2) {
			toReturn.next = null;
			return toReturn;
		}
		Node cur = first.next;

		for(int i = 1; i < size/2; i++){
			cur = cur.next;
		}

		cur.next = null;
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

		return new String();
	}

	@Override
	public boolean isInOrder() {
		Node cur = first;
		for (int i = 0; i < size-1; i++) {
			if (cur.card.compareTo(cur.next.card) <= 0){
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean isInReverseOrder() {
		Node cur = first;
		for (int i = 0; i < size-1; i++) {
			if (cur.card.compareTo(cur.next.card) > 0){
				return false;
			}
		}
		return true;
	}


}
