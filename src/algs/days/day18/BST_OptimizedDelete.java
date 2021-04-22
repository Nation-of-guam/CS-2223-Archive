package algs.days.day18;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

// Take the BST from day17 and optimize its delete method.

public class BST_OptimizedDelete<Key extends Comparable<Key>> {

	Node root;               // root of the tree

	class Node {
		Key    key;        
		Node   left, right;  // left and right subtrees

		public Node(Key key) {
			this.key = key;
		}

		public String toString() { return "[" + key + "]"; }
	}

	public boolean isEmpty() { return root == null; }

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
		if (parent == null) return new Node(key);

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

	public Key nonRecursiveMin() {
		Node n = root;

		while (n.left != null) {
			n = n.left;
		}

		return n.key;
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
	public void inorder() { inorder(root); }
	private void inorder(Node n) {
		if (n != null) {
			inorder (n.left);
			StdOut.println (n.key);
			inorder (n.right);
		}
	}

	// traversal ideas
	// invoke a pre-order traversal of the tree
	public void preorder() { preorder(root); }
	private void preorder(Node n) {
		if (n != null) {
			StdOut.println (n.key);

			preorder (n.left);
			preorder (n.right);
		}
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
		return parent;
	}

	// new methods for discussion
	public Key max() { return max(root).key; }

	private Node max (Node parent) {
		if (parent.right == null) { return parent; }
		return max(parent.right);
	}

	public void delete(Key key) {
		root = delete(root, key);
	}

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

			// find min in right tree, swap its value with parent and clean up
			Node child = parent.right;
			Node grandParent = null;
			while (child.left != null) {
				grandParent = child;
				child = child.left;
			}

			parent.key = child.key;              // swap value into place
			if (grandParent != null) {
				grandParent.left = child.right;  // clean up
			} else {
				parent.right = child.right;
			}
		} 

		// as recursions unwind, pass back potential new parent
		return parent;
	}

	/**
	 * Returns all keys in the symbol table as an <tt>Iterable</tt>.
	 * To iterate over all of the keys in the symbol table named <tt>st</tt>,
	 * use the foreach notation: <tt>for (Key key : st.keys())</tt>.
	 *
	 * @return all keys in the symbol table
	 */
	public Iterable<Key> keys() { return keys(min(), max()); }

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

		// much like a traversal; builds up state in the queue.
		if (cmplo < 0)                 keys(node.left, queue, lo, hi); 
		if (cmplo <= 0 && cmphi >= 0)  queue.enqueue(node.key); 
		if (cmphi > 0)                 keys(node.right, queue, lo, hi); 
	}

	public static void main(String[] args) {
		// AVL is original self-balancing structure. RedBlackBST is Sedgewick's implementation
		// which is optimized for human readability and understanding. TreeMap is Java's built
		// in RedBlack tree which is optimized for performance.
		
		BST_OptimizedDelete<Integer> bst = new BST_OptimizedDelete<Integer>();
		for (int i = 0; i <5777; i++) {

			// find rnd number not in BST.
			int rnd = StdRandom.uniform(0, 50000);
			while (bst.contains(rnd)) {
				rnd = StdRandom.uniform(0, 50000);
			}
			
			bst.insert(rnd);
		}

		// randomly delete elements, one at a time.
		Queue<Integer> queue = (Queue<Integer>) bst.keys();
		Integer[] keys = new Integer[queue.size()];
		for (int idx = 0; idx < keys.length; idx++) {
			keys[idx] = queue.dequeue();
		}

		// complete until empty
		int count = keys.length;
		int spc = 0;
		while (count > 0) {
			int idx = (spc++); // StdRandom.uniform(0, count);
			bst.delete(keys[idx]);
			keys[idx] = keys[--count];
		}

		StdOut.println("root=" + bst.root);
	}
}
