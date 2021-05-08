package algs.hw3.submission;

import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;

/**
 * Minimum implementation of Binary Search Tree (BST) as a Symbol Table<String, Integer>
 *
 * You need to copy this class into your USERID.hw3 and add methods to the end of this class.
 */
public class BST {
 
	Node root;               // root of the tree

	class Node {
		String    key;          
		Integer   count;         
		Node      left, right;  // left and right subtrees
		int       N;            // number of nodes in subtree

		public Node(String key, int ct, int N) {
			this.key = key;
			this.count = ct;
			this.N = N;
		}

		public String toString() { return "[" + key + "]"; }
	}

	public boolean isEmpty() { return size() == 0; }

	/** Return number of key-value pairs in ST. */
	public int size()                { return size(root); }

	// Helper method that deals with "empty nodes"
	private int size(Node node) {
		if (node == null) return 0;

		return node.N;
	}

	/** Search for key in BST. */
	public Integer get(String key)      { return get(root, key); }

	/** Helper method to search for key in BST rooted at parent. */
	private Integer get(Node parent, String key) {
		if (parent == null) return null;

		int cmp = key.compareTo(parent.key);

		if      (cmp < 0) return get(parent.left, key);
		else if (cmp > 0) return get(parent.right, key);
		else              return parent.count;
	}

	/** Invoke put on parent, should it exist. */
	public void put(String key, Integer val) {
		root = put(root, key, val);
	}

	/** Helper method to put (key, ct) pair into BST rooted at parent. */
	private Node put(Node parent, String key, Integer ct) {
		if (parent == null) return new Node(key, ct, 1);

		int cmp = key.compareTo(parent.key);
		if      (cmp < 0) parent.left  = put(parent.left,  key, ct);
		else if (cmp > 0) parent.right = put(parent.right, key, ct);
		else              parent.count = ct;

		parent.N = 1 + size(parent.left) + size(parent.right);
		return parent;
	}

	// traversal ideas
	// invoke an inorder traversal of the tree
	public void inorder() { inorder(root); }
	private void inorder(Node n) {
		if (n == null) { return; }

		inorder (n.left);
		StdOut.println (n.key);
		inorder (n.right);
	}

	/**
	 * Removes the specified key and its associated value from this symbol table     
	 * (if the key is in this symbol table).    
	 *
	 * @param  key the key
	 * @throws IllegalArgumentException if {@code key} is {@code null}
	 */
	public void delete(String key) {
		if (key == null) throw new IllegalArgumentException("calls delete() with a null key");
		root = delete(root, key);
	}

	/** Taken from Sedgewick algo. */
	private Node delete(Node x, String key) {
		if (x == null) return null;

		int cmp = key.compareTo(x.key);
		if      (cmp < 0) x.left  = delete(x.left,  key);
		else if (cmp > 0) x.right = delete(x.right, key);
		else { 
			if (x.right == null) return x.left;
			if (x.left  == null) return x.right;
			Node t = x;
			x = min(t.right);
			x.right = deleteMin(t.right);
			x.left = t.left;
		} 
		return x;
	} 

	private Node min(Node x) { 
		if (x.left == null) return x; 
		else                return min(x.left); 
	} 

	/**
	 * Removes the smallest key and associated value from the symbol table.
	 *
	 * @throws NoSuchElementException if the symbol table is empty
	 */
	public void deleteMin() {
		if (isEmpty()) throw new NoSuchElementException("Symbol table underflow");
		root = deleteMin(root);
	}

	private Node deleteMin(Node x) {
		if (x.left == null) return x.right;
		x.left = deleteMin(x.left);
		return x;
	}
	
	public boolean isComplete() {
    	if (root == null) { return false; }
    	
    	// make sure N is a power of two minus one.
    	int check = (int) (Math.log(root.N + 1)/Math.log(2));
    	int result = (int) (Math.pow(2, check))-1;
    	if (result != root.N) { return false; }
    	
    	// OK, now 2^k - 1. Check each one.
    	int num = 1;
    	int total = 0;
    	int depth = 0;
    	while (total < root.N) {
    		if (this.depthCount(depth) != num) { return false; }
    		total += num;
    		num *= 2;
    		depth += 1;
    	}
    
    	return true; // SUCCESS!
    }

	// ------------------------------------------------------------------------------------------------
	// YOU WILL ADD METHODS BELOW. THERE IS NO NEED TO MODIFY CODE ABOVE.
	// ------------------------------------------------------------------------------------------------
	
	/** 
	 * Helper method for copy()
	 */
	int depthCount(Node n, int depth) {
		throw new RuntimeException("STUDENT COMPLETES");
	}

	/** 
	 * Count and return the number of nodes at a given depth, 
	 * where root has depth of 0.
	 */
	public int depthCount(int depth) {
		throw new RuntimeException("STUDENT COMPLETES");
	}


	/**
	 * Return a copy of the BST. All (key,count) pairs from the current
	 * tree are used in the subsequent copy.
	 */
	public BST copy() {
		throw new RuntimeException("STUDENT COMPLETES");
	}

	/** 
	 * Helper method for copy()
	 */
	Node copy(Node n) {
		throw new RuntimeException("STUDENT COMPLETES");
	}

	/**
	 * Return the key whose count is the greatest (that is, has the most 
	 * occurrences in the BST). If multiple keys have the same count, 
	 * then any of them can be returned as the most frequent.
	 */
	public String mostFrequent() {
		throw new RuntimeException("STUDENT COMPLETES");
	}

	/**
	 * This helper method looks unusual, but follow the logic. It is trying
	 * to find the node with greatest count. Let 'best' be the best Node
	 * in the tree found so far.
	 */
	Node mostFrequent(Node n, Node best) {
		throw new RuntimeException("STUDENT COMPLETES");
	}

	/** Helper method for truncate. */
	Node truncate(Node n, int depth) {
		throw new RuntimeException("STUDENT COMPLETES");
	}

	/** 
	 * Truncate and remove all nodes BELOW THE GIVEN DEPTH.
	 * After calling this method, this.depthCount(depth+1) must be zero while
	 * this.depthCount(depth) must remain unchanged.
	 * 
	 * Note: YOU NEED TO PROPERLY UPDATE COUNT FOR N 
	 */
	public void truncate(int depth) {
		throw new RuntimeException("STUDENT COMPLETES");
	}

}
