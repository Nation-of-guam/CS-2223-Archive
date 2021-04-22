package algs.days.day17;

public class DepthExploration {
	public static void main(String[] args) {
		BST<String> bst = new BST<String>();
		
		bst.insert("it");
		bst.insert("was");
		bst.insert("the");
		bst.insert("best");
		bst.insert("of");
		
		bst.outputDepthInfo();
	}
}
