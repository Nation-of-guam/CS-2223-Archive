package aiburns.hw3;

import edu.princeton.cs.algs4.StdOut;
import java.util.NoSuchElementException;

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

	public void put(String key) {
		root = put(root, key);
	}
	private Node put(Node parent, String key) {
		if (parent == null) return new Node(key, 1, 1);

		int cmp = key.compareTo(parent.key);
		if      (cmp < 0) parent.left  = put(parent.left,  key);
		else if (cmp > 0) parent.right = put(parent.right, key);
		else              parent.count = parent.count + 1;

		parent.N = 1 + size(parent.left) + size(parent.right);
		return parent;
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
		StdOut.print(n.key);
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
		if (n == null) return 0;
		if (depth == 0) return 1;
		return depthCount(n.left, depth -1) + depthCount(n.right, depth - 1);
	}

	/** 
	 * Count and return the number of nodes at a given depth, 
	 * where root has depth of 0.
	 */
	public int depthCount(int depth) {
		if (depth < 0) return 0;
		int toReturn = 0;
		toReturn = depthCount(root, depth);
		return toReturn;
	}


	/**
	 * Return a copy of the BST. All (key,count) pairs from the current
	 * tree are used in the subsequent copy.
	 */
	public BST copy() {
		if (isEmpty()) return new BST();
		BST toReturn = new BST();
		toReturn.root = copy(root);
		return toReturn;
	}

	/** 
	 * Helper method for copy()
	 */
	Node copy(Node n) {
		if (n == null) return null;
		Node toReturn = new Node(n.key, n.count, n.N);
		toReturn.right = copy(n.right);
		toReturn.left = copy(n.left);
		return toReturn;
	}

	/**
	 * Return the key whose count is the greatest (that is, has the most 
	 * occurrences in the BST). If multiple keys have the same count, 
	 * then any of them can be returned as the most frequent.
	 */
	public String mostFrequent() {
		if (root == null) return null;
		Node toReturn =  mostFrequent(root, root);
		return toReturn.key;
	}

	/**
	 * This helper method looks unusual, but follow the logic. It is trying
	 * to find the node with greatest count. Let 'best' be the best Node
	 * in the tree found so far.
	 */
	Node mostFrequent(Node n, Node best) {
		if (n == null) return best;
		Node toReturn = best;
		Node left = mostFrequent(n.left, toReturn);
		Node right = mostFrequent(n.right, toReturn);
		if (n.count > toReturn.count){
			toReturn = n;
		}
		if ( left != null && left.count > toReturn.count){
			toReturn = left;
		}
		if ( right != null&& right.count > toReturn.count){
			toReturn = right;
		}

		return toReturn;
	}

	/** Helper method for truncate. */
	Node truncate(Node n, int depth) {
		if (n == null) return null;
		if (n.right == null && n.left == null) return n;
		if (n.left == null) {
			if (depth == 0){
				n.N = 1;
				n.right = null;
				return n;
			} else {
				truncate(n.right, depth -1);
				n.N = n.right.N + 1;
				return n;
			}
		}
		if (n.right == null) {
			if (depth == 0){
				n.N = 1;
				n.left = null;
				return n;
			} else {
				truncate(n.left, depth -1);
				n.N = n.left.N + 1;
				return n;
			}
		}
		if (depth == 0){
			n.N = 1;
			n.left = null;
			n.right = null;
			return n;
		}
		n.left = truncate(n.left, depth -1);
		n.right = truncate(n.right, depth -1);
		n.N = n.right.N + n.right.N + 1;
		return n;
	}

	/** 
	 * Truncate and remove all nodes BELOW THE GIVEN DEPTH.
	 * After calling this method, this.depthCount(depth+1) must be zero while
	 * this.depthCount(depth) must remain unchanged.
	 * 
	 * Note: YOU NEED TO PROPERLY UPDATE COUNT FOR N 
	 */
	public void truncate(int depth) {
		truncate(root, depth);
	}

}
