package algs.hw2;

/**
 * Represents a Node in a linked list that stores a Card.
 */
public class Node {
	public Card    card;
	public Node    next;
	
	/** Constructs a new node for the card. */
	public Node(Card c) {
		this(c, null);
	}
	
	/**
	 * Constructor to use when you want to 'prepend' new node before a list.
	 * 
	 * @param c
	 * @param next
	 */
	public Node(Card c, Node next) {
		this.card = c;
		this.next = next;
	}
}

