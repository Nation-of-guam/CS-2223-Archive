package algs.days.day17;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

// In this example, we eliminate the value associated with key, just 
// so we can show a common behavior for using the BST structure simply
// to store information.

// Just trying to show some alternative behavior.

public class BST<Key extends Comparable<Key>> {

	Node root;               // root of the tree

	class Node {
		Key    key;          // only stores KEY value...
		Node   left, right;  // left and right subtrees
		int    N;            // number of nodes in subtree
		
		public Node(Key key, int N) {
			this.key = key;
			this.N = N;
		}

		public String toString() { return "[" + key + "]"; }
	}

	public boolean isEmpty() { return root == null; }
	
	/** Return number of keys in BST. */
	public int size() { return size(root); }

	// Helper method that deals with "empty nodes"
	private int size(Node node) {
        if (node == null) return 0;
        
        return node.N;
    }

	public String toString() { return "<bst: root=" + root +">"; }

	// One-line method for containment. 
	public boolean contains(Key key) { return get(root, key); }

	private boolean get(Node parent, Key key) {
		if (parent == null) return false;

		int cmp = key.compareTo(parent.key);

		if      (cmp < 0) return get(parent.left, key);
		else if (cmp > 0) return get(parent.right, key);
		else              return true;
	}

	/** Insert key into BST. */
	public void insert(Key key) {
		root = insert(root, key);
	}

	private Node insert(Node parent, Key key) {
		if (parent == null) return new Node(key, 1);

		int cmp = key.compareTo(parent.key);
		if (cmp <= 0) {
			parent.left  = insert(parent.left,  key);
		} else {
			parent.right = insert(parent.right, key);
		}

		return parent;
	}

	public Key min() { return min(root).key; }

	private Node min (Node parent) {
		if (parent.left == null) { return parent; }
		return min(parent.left);
	}

	public Key floor(Key key) {
		Node rc = floor(root, key);
		if (rc == null) return null;
		return rc.key;
	} 

	private Node floor(Node parent, Key key) {
		if (parent == null) return null;

		int cmp = key.compareTo(parent.key);
		if (cmp == 0) return parent;                   // found? Then this is floor
		if (cmp <  0) return floor(parent.left, key);  // smaller? must be in left subtree

		Node t = floor(parent.right, key);             // greater? we might be floor, but
		if (t != null) return t;                       // only if 
		else return parent; 
	} 

	// traversal ideas
	// invoke an inorder traversal of the tree
	public void inorder() { inorder(root); StdOut.println(); }
	private void inorder(Node n) {
		if (n == null) { return; }
		
		inorder (n.left);
		StdOut.print (n.key + " ");
		inorder (n.right);
	}

	// traversal ideas
	// invoke a pre-order traversal of the tree
	public void preorder() { preorder(root); StdOut.println();  }
	private void preorder(Node n) {
		if (n == null) { return; }
		
		StdOut.print (n.key + " ");
		preorder (n.left);
		preorder (n.right);
	}

	public void postorder() { postorder(root); StdOut.println();  }
	private void postorder(Node n) {
		if (n == null) { return; }
		
		postorder (n.left);
		postorder (n.right);
		StdOut.print (n.key + " ");
	}
	
	
	/** Implement method to return Value when removing largest element. */
	public void deleteMin() {
		if (root != null) { root = deleteMin(root);	}
	}

	Node deleteMin(Node parent) {
		if (parent.left == null) {
			return parent.right;
		}

		parent.left = deleteMin(parent.left);
		parent.N = size(parent.left) + size(parent.right) + 1;
		return parent;
	}

	// new methods for discussion
	public Key max() { return max(root).key; }

	private Node max (Node parent) {
		if (parent.right == null) { return parent; }
		return max(parent.right);
	}

	public void delete(Key key) { root = delete(root, key); }

	private Node delete(Node parent, Key key) {
		if (parent == null) return null;

		// recurse until you find parent with this key.
		int cmp = key.compareTo(parent.key);
		if      (cmp < 0) parent.left  = delete(parent.left,  key);
		else if (cmp > 0) parent.right = delete(parent.right, key);
		else { 
			// handle easy cases first:
			if (parent.right == null) return parent.left;
			if (parent.left  == null) return parent.right;

			// has two children: Plan on returning min of our right child
			Node old = parent;
			parent = min(old.right);     // will eventually be "new parent"

			// Note this is a simpler case: Delete min from right subtree
			// and DON'T FORGET to stitch back in the original left child
			parent.right = deleteMin(old.right);   
			parent.left = old.left;
		} 

		// as recursions unwind, pass back potential new parent
		parent.N = size(parent.left) + size(parent.right) + 1;
		return parent;
	}

	int numCmp = 0;
	
	/**
	 * Returns all keys in the symbol table as an <tt>Iterable</tt>.
	 * To iterate over all of the keys in the symbol table named <tt>st</tt>,
	 * use the foreach notation: <tt>for (Key key : st.keys())</tt>.
	 *
	 * @return all keys in the symbol table
	 */
	public Iterable<Key> keys() { numCmp = 0; return keys(min(), max()); }

	public Iterable<Key> keys(Key lo, Key hi) {
		Queue<Key> queue = new Queue<Key>();
		keys(root, queue, lo, hi);
		return queue;
	} 

	private void keys(Node node, Queue<Key> queue, Key lo, Key hi) { 
		if (node == null) return; 

		// check if contained within this range
		int cmplo = lo.compareTo(node.key); 
		int cmphi = hi.compareTo(node.key);
		numCmp += 2; // instrument
		
		// much like a traversal; builds up state in the queue.
		if (cmplo < 0)                 keys(node.left, queue, lo, hi); 
		if (cmplo <= 0 && cmphi >= 0)  queue.enqueue(node.key); 
		if (cmphi > 0)                 keys(node.right, queue, lo, hi); 
	}
	

	// Daily Question for today (Day 17: April 08 2019. Try to solve first
	// before reviewing these two methods below...
	public void removeAllLeafNodes() {
		if (root != null) {
			root = removeAllLeafNodes(root);
		}
	}
	
	public Node removeAllLeafNodes(Node parent) {
		if (parent == null) { return null; }
		
		// this is a leaf node. Get rid of it (in recursive call)
		if (parent.left == null && parent.right == null) return null;

		// order matters! Now we work to remove all old leaves. Note in doing so
		// we may create NEW leaves, but it is ok...
		parent.left = removeAllLeafNodes(parent.left); 
		parent.right = removeAllLeafNodes(parent.right);
		
		return parent; // ok to stay
	}
	
	// --------------------------------------------------------------------------
	// newly added after Lecture 18 and good preparation for HW3
	
	// Output all keys ASCENDING and their respective depth
	public void outputDepthInfo() {
		outputDepthInfo(root, 0);   // I KNOW DEPTH OF ROOT is ZERO
	}
	
	/** 
	 * Helper method to continue process of printing depth information for each key in
	 * ascending sorted order. Note the key ideas is to have the Caller pass in the depth of the
	 * parent node, since that can be used to deduce the depths of the children nodes in the 
	 * recursive call
	 *  
	 * @param parent     root of the subtree being processed
	 * @param myDepth    depth associated with this node.
	 */
	void outputDepthInfo(Node parent, int myDepth) {
		if (parent == null) { return; }    // handle the case for non-existent tree rooted at null.
		
		// to be in ascending must doLeft, doMe, doRight
		outputDepthInfo(parent.left, myDepth + 1);

		// how do I find what depth I am on? and then print it out
		System.out.println(parent.key + " is at depth " + myDepth);
		
		// how do I make sure I report on the depths of my children.
		outputDepthInfo(parent.right, myDepth + 1);
	}
	
}
