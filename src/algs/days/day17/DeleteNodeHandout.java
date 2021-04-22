package algs.days.day17;

public class DeleteNodeHandout {
	public static void main(String[] args) {
		// construct binary tree on p. 401
		BST<Integer> bst = new BST<Integer>();
		
		bst.insert(7);
		bst.insert(5);
		bst.insert(2);
		bst.insert(15);
		bst.insert(10);
		bst.insert(20);
		bst.insert(1);
		bst.insert(6);
		bst.insert(17);
		
		bst.inorder();
		
		// delete the root!
		bst.delete(7);
		
		// nodes can still be retrieved in order.
		bst.inorder();
	}
}
