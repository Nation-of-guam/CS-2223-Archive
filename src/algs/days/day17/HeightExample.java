package algs.days.day17;

public class HeightExample {
	public static void main(String[] args) {
		BST<String> bst = new BST<String>();
		
		bst.insert("R");
		bst.insert("U");
		bst.insert("A");
		bst.insert("T");
		bst.insert("W");
		bst.insert("P");
		bst.insert("I");
		
		System.out.println("Height of tree is: " + bst.height());
	}
}
