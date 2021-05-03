package algs.days.day25;

/**
 * Demonstrate code for Lowest Common Ancestor (k1, k2) in a given tree. This was a sample exam question
 * which proved to be a bit more complicated.
 * 
 * Assuming that k1 and k2 exist in a Binary Search tree, find the lowest common ancestor, that
 * is, the node N which is furthest from the root, and for which k1 is a descendant of N and k2 is
 * a descendant of N.
 * 
 * Note there is a special case to consider: what if k1 is a descendant of k2? Then k1 is the LCA.
 * Similarly, what if k2 is a descendant of k1? Then k1 is the LCA.
 * 
 * @param <Key>
 * @param <Value>
 */
public class BST<Key extends Comparable<Key>, Value> {

	Node root;               // root of the tree
	
	class Node {
		Key    key;          
		Value  val;         
		Node   left, right;  // left and right subtrees
		int    N;            // number of nodes in subtree

		public Node(Key key, Value val, int N) {
			this.key = key;
			this.val = val;
			this.N = N;
		}
	}

	public boolean isEmpty() { return size() == 0; }

	/** Return number of key-value pairs in ST. */
	public int size()                { return size(root); }

	// Helper method that deals with "empty nodes"
	private int size(Node node) {
        if (node == null) return 0;
        
        return node.N;
    }

	// One-line method for containment. 
	public boolean contains(Key key) { return get(key) != null; }

	/** Search parent. */
	public Value get(Key key)        { return get(root, key); }
	
	private Value get(Node parent, Key key) {
		if (parent == null) return null;
		
		int cmp = key.compareTo(parent.key);
		
		if      (cmp < 0) return get(parent.left, key);
		else if (cmp > 0) return get(parent.right, key);
		else              return parent.val;
	}

	/** Invoke put on parent, should it exist. */
	public void put(Key key, Value val) {
		root = put(root, key, val);
	}
	
	/** 
	 * Assume that k1 and k2 are both keys in the tree.
	 * 
	 * Handle bizarre case where k1 = k2. 
	 */
	public Key lca(Key k1, Key k2) {
		
		if (k1.equals(k2)) { return k1; }
		
		return lca(root, k1, k2);
	}
	
	private Key lca(Node parent, Key k1, Key k2) {
		if (parent == null) { return null; }
		
		// if found one? Then return it.
		int cmp1 = parent.key.compareTo(k1);
		if (cmp1 == 0) { return k1; }
		int cmp2 = parent.key.compareTo(k2);
		if (cmp2 == 0) { return k2; }
		
		// IF both k1,k2 are smaller than our key, traverse left
		if (cmp1 > 0 && cmp2 > 0) {
			return lca(parent.left, k1, k2);
		}
			
		// IF both k1,k2 are larger than our key, traverse right
		if (cmp1 < 0 && cmp2 < 0) {
			return lca(parent.right, k1, k2);
		}
		
		// One on left and one on right? Then WE are the lCA.
		return parent.key;
	}
	

	private Node put(Node parent, Key key, Value val) {
		if (parent == null) return new Node(key, val, 1);
		
		int cmp = key.compareTo(parent.key);
		if      (cmp < 0) parent.left  = put(parent.left,  key, val);
		else if (cmp > 0) parent.right = put(parent.right, key, val);
		else              parent.val   = val;
		
		parent.N = 1 + size(parent.left) + size(parent.right);
		return parent;
	}
	
	public static void main (String args[]) {
		// test out the LCA.
		
		/**
		 
		                 15
		              /      \
		             9        30
		            / \      /   \
		           5   12   24    40
		          /     \    \    /  \
		         1       13   29 35  60 
		 
		 LCA(1,60) = 15
		 LCA(1,13) = 9
		 LCA(9,13) = 9
		 LCA(13,9) = 9
		 LCA(35,60) = 40
		 
		 */
		BST<Integer,Boolean> bst = new BST<>();
		int[] inserts = new int[] { 15, 9, 30, 5, 12, 24, 40, 1, 13, 29, 35, 60};
		for (int i : inserts) {
			bst.put(i,true);
		}
		
		System.out.println(bst.lca(1, 60) + " = " + 15);
		System.out.println(bst.lca(1, 13) + " = " + 9);
		System.out.println(bst.lca(9, 13) + " = " + 9);
		System.out.println(bst.lca(13, 9) + " = " + 9);
		System.out.println(bst.lca(35, 60) + " = " + 40);
		System.out.println(bst.lca(29, 30) + " = " + 30);
	}
}
