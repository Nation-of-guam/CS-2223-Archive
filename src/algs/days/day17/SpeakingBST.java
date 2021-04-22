package algs.days.day17;

/**
 * Just for fun, this BST describes (in English) what happens when you insert a node into a BST.
 * Try it yourself! This is a more complicated example than what is shown in lecture or on
 * the homework.
 * 
 * A simplified BST that only stores keys.
 *
 * @param <Key>
 */
public class SpeakingBST<Key extends Comparable<Key>> {

	Node root;               // root of the tree

	class Node {
		Key    key;          // only stores KEY value...
		Node   left, right;  // left and right subtrees
		
		public Node(Key key) {
			this.key = key;
		}

		public String toString() { return "[" + key + "]"; }
	}

	// needed for my speaking tree.
	class Pair {
		Node newParent;
		String explanation;
		
		public Pair(Node np, String s) {
			this.newParent = np;
			this.explanation = s;
		}
	}
	
	/** Insert key into BST. */
	public String insert(Key key) {
		Pair pair = insert(root, key, String.format("To insert %s, ", key.toString()));
		root = pair.newParent;
		return pair.explanation;
	}

	/** 
	 * Helper method that inserts key into the BST rooted at parent (and the given explanation so far), and
	 * returns a Pair object with (a) the new subtree for the resulting tree after key is inserted; and (b)
	 * a descriptive explanation of what was done to insert the value into the subtree.
	 */
	private Pair insert(Node parent, Key key, String soFar) {
		// Base Case
		if (parent == null) {
			return new Pair(new Node(key), String.format("%screate a new subtree with root of %s.", soFar, key.toString()));
		}

		// Recursive Case: since duplicates can be stored, handle (arbitrarily) <= for both < and =
		if (key.compareTo(parent.key) <= 0) {
			soFar += String.format("%s is smaller than or equal to %s, so insert %s into the left subtree of %s ", key, parent.key, key, parent.key);
			if (parent.left == null) {
				soFar += "but there is no left subtree, so ";
			} else {
				soFar += String.format("rooted at %s. Now ", parent.left.key);
			}
			
			Pair pair = insert(parent.left, key, soFar);
			parent.left = pair.newParent;
			return (new Pair(parent, pair.explanation));
		}
		
		// must be > node.
		soFar += String.format("%s is larger than or equal to %s, so insert %s into the right subtree of %s ", key, parent.key, key, parent.key);
		if (parent.right == null) {
			soFar += "but there is no right subtree, so ";
		} else {
			soFar += String.format("rooted at %s. Now ", parent.right.key);
		}
		
		Pair pair = insert(parent.right, key, soFar);
		parent.right = pair.newParent;
		return (new Pair(parent, pair.explanation));
	}

	public static void main(String[] args) {
		SpeakingBST<String> bst = new SpeakingBST<String>();
		
		System.out.println(bst.insert("S"));
		System.out.println(bst.insert("E"));
		System.out.println(bst.insert("A"));
		System.out.println(bst.insert("C"));
		System.out.println(bst.insert("R"));
		System.out.println(bst.insert("H"));
		System.out.println(bst.insert("M"));
		System.out.println(bst.insert("P"));
		System.out.println(bst.insert("X"));
		
		// now what happens with put L
		System.out.println(bst.insert("L"));
	}
}
