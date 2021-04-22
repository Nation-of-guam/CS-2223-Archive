package aiburns.hw2;

import algs.hw2.Card;
import algs.hw2.Deck;
import algs.hw2.Node;
import algs.hw2.Suit;

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
	 * Ensure that no one OUTSIDE of this class invokes
	 * the no-argument constructor. You will find
	 * it useful to have this constructor within the
	 * copy() method since it must return an accurate
	 * copy of the current Deck, and it will first need
	 * to construct an "empty" MyDeck object
	 * without using the MyDeck(int max_rank) constructor.
	 *
	 */
	protected MyDeck() {
		// You do not need to modify this method. T
		// his constructor exists to ensure that
		// within this class, you can construct an
		// empty MyDeck whose first and last are null.
	}

	/**
	 * Construct a playing deck with {max_rank} cards in specific order.
	 *
	 * Once done, the linked list of card Nodes must
	 * represent a deck that looks like the following (if
	 * {max_rank} were 3). The suites are ordered Club < Diamond < Hearts < Spades.
	 *
	 * AC -> 2C -> 3C -> AD -> 2D -> 3D -> AH -> 2D -> 3H -> AS -> 2S -> 3S
	 *
	 * Note your deck will have 4*{max_rank} cards.
	 *
	 * Performance must be O(N) where N is max_rank.
	 */
	public MyDeck(int max_rank) {
		if (max_rank < Card.ACE) { throw new IllegalArgumentException("max_rank must be above " + Card.ACE ); }

		Suit[] suits;
		suits = Suit.values();
		Node cur = new Node(new Card(suits[0], 1));
		first = cur;
		size = max_rank*4;



		for (int i = 2; i <= max_rank; i++) {
			cur.next = new Node(new Card(suits[0], i));
			last = cur.next;
			cur = cur.next;

		}
		for (int j = 1; j < 4; j++) {
			for (int i = 1; i <= max_rank; i++) {
				cur.next = new Node(new Card(suits[j], i));
				last = cur.next;
				cur = cur.next;

			}
		}
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
		if (n > size){
			throw new IndexOutOfBoundsException("n is greater than the size, " + n + " exceeds "+ size);
		}
		Node nodeIterative = first;

		for (int i = 1; i < n; i++) {
			nodeIterative = nodeIterative.next;
		}

		return nodeIterative.card.equals(c);
	}

	@Override
	public Deck copy() {
		return (Deck) copyMyDeck();
	}

	protected MyDeck copyMyDeck() {
		MyDeck toReturn = new MyDeck();

		Node thisCopy = first;

		Node iterativeNode = new Node(thisCopy.card);
		toReturn.first = iterativeNode;
		toReturn.size = size;
		thisCopy = thisCopy.next;

		while (thisCopy.next != null){
			iterativeNode.next = new Node(thisCopy.card);
			iterativeNode = iterativeNode.next;
			thisCopy = thisCopy.next;
		}
		iterativeNode.next = new Node(thisCopy.card);
		toReturn.last = thisCopy;


		return toReturn;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	protected Node cutInHalf() {
		Node toReturn;
		if (size % 2 == 1){
			throw new RuntimeException("haha no middle of odd cards");
		} else if (size == 2) {
			toReturn = first.next;
			first.next = null;
			return toReturn;
		}
		Node cur = first;
		for(int i = 1; i < size/2; i++){
			cur = cur.next;
		}
		last = cur;
		toReturn = cur.next;
		cur.next = null;

		size = size / 2;

		return toReturn;
	}

	@Override
	public void out() {
		Node temp = cutInHalf();
		merge(temp, false);
	}

	@Override
	public void in() {
		Node temp = cutInHalf();
		merge(temp, true);
	}



	/**
	 *
	 * @param otherNode the right deck to be merged
	 * @param rightFirst true if you want the first in otherNode to be the first in this
	 */
	private void merge(Node otherNode, boolean rightFirst){
		MyDeck storageCopy = copyMyDeck();
		Node firstNode;
		Node secondNode;
		Node temp;
		int counter = 2;


		if (rightFirst){
			temp = otherNode;
			first = temp;
			firstNode = otherNode.next;
			secondNode = storageCopy.first;
			temp.next = secondNode;
			secondNode = secondNode.next;
			temp = temp.next;
		} else {
			temp = first;
			firstNode = first.next;
			secondNode = otherNode;
			temp.next = secondNode;
			secondNode = secondNode.next;
			temp = temp.next;
		}

		while (firstNode != null && secondNode != null){

			temp.next = firstNode;
			firstNode = firstNode.next;
			temp = temp.next;

			temp.next = secondNode;
			temp.next = secondNode;
			secondNode = secondNode.next;
			temp = temp.next;
			counter+=2;
		}



		size = counter;
		last = secondNode;
	}


	@Override
	public String representation() {
		String toReturn = "";
		Node temp = first;

		while (temp!=null){
			toReturn = toReturn + temp.card.toString() + " ";
			temp = temp.next;
		}

		return toReturn;
	}

	@Override
	public String toString(){
		return representation();
	}

	/**
	 * @return false if in reverse order, true in order
	 */
	@Override
	public boolean isInOrder() {
		Node cur = first;
		while (cur.next != null){
			if (cur.card.compareTo(cur.next.card) >= 0){
				return false;
			}
			cur = cur.next;
		}
		return true;
	}


	/**
	 *
	 * @return true if in reverse order, false in order
	 */
	@Override
	public boolean isInReverseOrder() {
		Node cur = first;
		for (int i = 0; i < size-1; i++) {
			if (cur.card.compareTo(cur.next.card) <= 0){
				return false;
			}
			cur = cur.next;
		}
		return true;
	}


}
