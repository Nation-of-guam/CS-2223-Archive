package algs.days.day06;

/** Simple node structure. */
class Node {
	
	int value;
	Node next;
	
	/** Use this constructor to construct a single node. */
	public Node(int v) {
		value = v;
	}
	
	/** Use this constructor when you know the rest of the list to attach. */
	public Node(int v, Node n) {
		value = v;
		next = n;
	}
}

