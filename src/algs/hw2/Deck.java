package algs.hw2;

/**
 * DO NOT MAKE ANY CHANGES TO THIS CLASS.
 * YOUR HOMEWORK SUBMISSION (AS SHOWN IN MY EXAMPLE) WILL EXTEND THIS CLASS AND PROVIDE
 * IMPLEMENTATIONS.
 */
public abstract class Deck {
	
	/** Remember the Node containing the first card in the deck. */
	protected Node first;
	
	/** Remember the Node containing the last card in the deck. */
	protected Node last; 
	
	/**
	 * Peek to see the top card in the deck.
	 * 
	 * Performance must be O(1)
	 */
	public abstract Card peekTop();

	/**
	 * Peek to see the bottom card in the deck. 
	 * 
	 * Performance must be O(1)
	 */
	public abstract Card peekBottom();

	/** 
	 * Return a duplicate deck, with the cards in same order.
	 * 
	 * Performance must be O(N) where N is the number of cards in the deck.
	 */
	public abstract Deck copy();

	/**
	 * Return the number of cards remaining in the deck.
	 * 
	 * Performance must be O(1) independent of the number of cards in the deck.
	 */
	public abstract int size();
	
	/**
	 * Determines whether the nth card from the top of the deck matches the given card, c.
	 * The parameter, n, is a value from 1 to size() of the deck. IT IS NOT ZERO-BASED
	 * 
	 * Performance must be O(N).
	 */
	public abstract boolean match(Card c, int n);
	
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
	protected abstract Node cutInHalf();
	
	/**
	 * Determines whether the deck is in ascending order REGARDLESS of how many
	 * of each rank exist.
	 * 
	 * That is, for a deck with two ranks, the following deck would return true.
	 * 
	 * AC -> 2C -> AD -> 2D -> AH -> 2H -> AS -> 2S 
	 * 
	 * For a deck with three ranks, the following deck would return true.
	 * 
	 * AC -> 2C -> 3C -> AD -> 2D -> 3D -> AH -> 2H -> 3H -> AS -> 2S -> 3S
	 * 
	 * Performance must be O(N) where N is the number of cards in the deck.
	 */
	public abstract boolean isInOrder();

	/**
	 * Determines whether the deck is in descending order REGARDLESS of how many
	 * of each rank exist.
	 * 
	 * That is, for a deck with two ranks, the following deck would return true.
	 * 
	 * 2S -> AS -> 2H -> AH -> 2D -> AD -> 2C -> AC
	 * 
	 * For a deck with three ranks, the following deck would return true.
	 * 
	 * 3S -> 2S -> AS -> 3H -> 2H -> AH -> 3D -> 2D -> AD -> 3C -> 2C -> AC
	 * 
	 * Performance must be O(N) where N is the number of cards in the deck.
	 */
	public abstract boolean isInReverseOrder();
	
	/** 
	 * Conduct an out shuffle where the deck is cut in half and the TOP and BOTTOM cards remain in their place.
	 * 
	 * If the Deck contained EIGHT cards and 'first' points to the TOP of the deck (AH below) 
	 * 
	 * AC -> 2C -> AD -> 2D -> AH -> 2H -> AS -> 2S 
	 * 
	 * Then an 'out' shuffle would look like this:
	 * 
	 * LEFT:    AC    2C    AD    2D
	 * RIGHT:    | AH  | 2H  | AS  | 2S
     *           |  |  |  |  |  |  |  |
	 *           v  v  v  v  v  v  v  v
	 *          
	 * RESULT:  AC AH 2C 2H AD AS 2D 2S 
	 *  
	 * Which shows that the top and bottom cards remain the same. 
	 * 
	 * Hint: It is acceptable to create new Node objects as part of this method, if you want.
	 * 
	 * Performance must be O(N) where N is the number of cards in the deck.
	 */
	public abstract void out();

	/** 
	 * Conduct an in shuffle where the deck is cut in half and the TOP and BOTTOM cards remain in their place.
	 * 
	 * If the Deck contained EIGHT cards and 'first' points to the TOP of the deck (AH below) 
	 * 
	 * AC -> 2C -> AD -> 2D -> AH -> 2H -> AS -> 2S 
	 * 
	 * Then an 'in' shuffle would look like this:
	 * 
	 * LEFT:       AC    2C    AD    2D
	 * RIGHT:   AH  | 2H  | AS  | 2S  |
	 *           |  |  |  |  |  |  |  |
	 *           v  v  v  v  v  v  v  v
	 *          
	 * IN:      AH AC 2H 2C AS AD 2S 2D    
	 *  
	 * Which shows that the original top card is second, and the original bottom card is second from the bottom.
	 * 
	 * Performance must be O(N) where N is the number of cards in the deck.
	 */
	public abstract void in();

	/**
	 * Return string representation of deck.
	 * 
	 * For a deck containing just eight cards, the result is
	 * something like "AS AH 2S 2H 3S 3H 4S 4H"
	 * 
	 * Performance must be O(N) where N is the number of cards in the deck.
	 */
	public abstract String representation();
	
	/** 
	 * Override the default toString() so it simply calls your representation method. 
	 */
	@Override
	public String toString() { 
		return representation(); 
	}
}
