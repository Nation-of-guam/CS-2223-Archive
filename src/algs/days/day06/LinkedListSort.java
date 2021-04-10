package algs.days.day06;

import edu.princeton.cs.algs4.StopwatchCPU;

/**
 * Today in class I asked a Rhetorical question about being inspired to sort a linked
 * list, based on the presentation of Selection Sort and Insertion Sort.
 *
 * Turns out, Selection Sort is inspiring for sorting a linked list: check out 
		
		16		0.0
		32		0.0
		64		0.0
		128		0.0
		256		0.0
		512		0.0
		1024	0.0
		2048	0.0
		4096	0.03125
		8192	0.09375
		16384	0.390625
		32768	1.671875

 */
public class LinkedListSort {
	
	/**
	 * Given a linked list of values, return a new linked list in sorted order. Note
	 * that the original list is 'irrevocably changed' and a new first is returned.
	 * 
	 * If you continue to use the original value of `list` as your first, then you might
	 * miss some elements in the list!  
	 */
	static Node sort(Node list) {
		/** BASE CASE */
		if (list == null) { return null; }
		
		Node first_sorted = null;
		Node last_sorted = null;
		
		while (list != null) {
			Node smallest = findSmallest(list);
			list = remove(list, smallest.value);
			
			// Either create first node in new linked list OR append to end.
			if (first_sorted == null) {
				first_sorted = last_sorted = smallest;
				first_sorted.next = null;
			} else {
				last_sorted.next = smallest;
				last_sorted = last_sorted.next;
				last_sorted.next = null;
			}
		}
		
		return first_sorted;
	}
	
	public static void main (String[] args) {
		Node n1 = new Node(8);
		n1.next = new Node(3);
		n1.next.next = new Node(11);
		n1.next.next.next = new Node(7);
		
		// 8 -> 3 -> 11 -> 7
		output(n1);
		
		n1 = sort(n1);
		
		// 3 -> 7 -> 8 -> 11
		output(n1);
		
		// performance trials (WORST CASE!)
		for (int n = 16; n <= 32768; n*= 2) {
			
			// create list in reverse order (WORST CASE!)
			Node first = new Node(0);
			for (int i = 1; i < n; i++) {
				first = new Node(i, first);
			}
			
			StopwatchCPU timer = new StopwatchCPU();
			sort(first);
			double result = timer.elapsedTime();
			
			System.out.println(n + "\t" + result);
		}
	}
	
	static void output (Node n) {
		System.out.print("[");
		while (n != null) {
			System.out.print(n.value);
			if (n.next != null) {
				 System.out.print(" -> ");
			}
			
			n = n.next;
		}
		System.out.println("]");
	}

	
	
	/** Remove value from a list (when you know such a node exists), and return new list. */
	static Node remove(Node list, int value) {
		
		if (list.value == value) { return list.next; }
		Node prev = list;
		Node n = prev.next;
		while (n.value != value) {
			prev = n;
			n = n.next;
		}
		
		// Link Around
		prev.next = n.next;
		return list;
	}
	
	/** Return a reference to the smallest node. */
	static Node findSmallest(Node n) {
		if (n == null) {
			return null;
		}
		
		Node smallest = n;
		n = n.next;
		while (n != null) {
			if (n.value < smallest.value) {
				smallest = n;
			}
			
			n = n.next;
		}
		
		return smallest;		
	}
}
