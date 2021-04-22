package algs.days.day15;

public class Q3 {
	class Node {
		int value;                              // value in the node
		Node  next;                             // next node in the linked list
		public Node (int v) { this.value= v; }  // construct node with specific value}
	}
	
	/** Return new linked list whose nodes contain values that are twice as large as original. */
	public Node doubleUp (Node list) {
		if (list == null) { return null; }
		
		Node head = new Node(list.value*2);
		Node tail = head;
		Node n = list.next;
		while (n != null) {
			tail.next = new Node(n.value*2);
			tail = tail.next;
			n = n.next;
		}
		
		return head;				
	}
	
	public static void main(String[] args) {
		new Q3().doit();
	}

	private void doit() {
		Node n = new Node(3);
		n.next = new Node(4);
		n.next.next = new Node(2);
		
		Node copy = doubleUp(n);
		System.out.println(copy.value);
		System.out.println(copy.next.value);
		System.out.println(copy.next.next.value);
	}
}
