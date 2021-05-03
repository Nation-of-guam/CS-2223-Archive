package algs.days.day22;

import algs.days.day17.BST;

/**
 * It is possible to recover the BST structure just from a pre-order traversal of the nodes.
 * 
 * See if you can come up with a strategy given the following:
 */
public class RecoverFromPreOrder {
	public static void main(String[] args) {
		BST<Integer> bst = new BST<Integer>();
		
		/**
		 
		               8
		             /   \
		            4     12
		           / \    / \
		          3   7  10  17
		         /   /     \   \
		        1   5      11   20
		 
		 */
		
		// insert in this order
		int vals[] = new int[] { 8, 4, 12, 3, 7, 10, 17, 1, 5, 11, 20 };
		
		for (int v : vals) {
			bst.insert(v);
		}
		
		bst.preorder();
		
		// Now. Just from the preorder traversal can you reconstruct the BST above?
		
		// 8 4 3 1 7 5 12 10 11 17 20 
		
	}
}
