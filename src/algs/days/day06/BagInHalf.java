package algs.days.day06;

public class BagInHalf<Item> {
	Node first;     // first node in the list (may be null)

	class Node {
		Item    item;
		Node    next;
	}

	/** Add item to the bag (at front). */
	public void add (Item item) {
		Node oldfirst = first;

		first = new Node();
		first.item = item;
		first.next = oldfirst;
	}

	/** 
	 * This operation will return a new Bag that contains the
	 * remaining N - floor(N/2) elements while modifying the
	 * current bag to retain only the first floor(N/2) elements. 
	 */
	public BagInHalf<Item> cutInHalf() {
		// start both off at same place
		Node n1 = first;
		Node n2 = first;
		
		
		while (n1 != null) {
			
			// Try to advance n2 by two. 
			n2 = n2.next; 
			
			// If not possible then n2 has "fallen off" the edge of list, and n1 is the "last" one of the left-side 
			if (n2 == null || n2.next == null) {
				// sever the connection between the left side (from first up until n1) and the right side (n1.next and beyond)
				Node secondHalf = n1.next;
				n1.next = null;

				// put together a new bag and set its first to this new right-hand side, returning it...
				BagInHalf<Item> secondBag = new BagInHalf<>();
				secondBag.first = secondHalf;
				return secondBag;
			}
			
			// we were able to advance two. Now advance n1 just one (which ensures we get to halfway point).
			n2 = n2.next;
			n1 = n1.next; 
		}
		
		// empty to begin with!
		return null;
	}
	
	/** Return number of items in the bag. */
	public int size() {
		int count = 0;
		Node n = first;
		while (n != null) {
			n = n.next;
			count++;
		}
		
		return count;
	}

	public static void main(String[] args) {
		BagInHalf<Integer> bag = new BagInHalf<>();

		int N = 13;  // how many to add
		
		for (int i = 1; i <= N; i++) {
			bag.add(i);
		}
		
		BagInHalf<Integer> other = bag.cutInHalf();
		System.out.println("bag now has: " + bag.size());
		System.out.println("other bag now has: " + other.size());
	}


}
