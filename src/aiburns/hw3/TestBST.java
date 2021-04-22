package aiburns.hw3;

/**
 * Copy this class into the package where you have BST and it should run
 * without any errors
 */
public class TestBST {
	
	static void test(boolean b) {
		if (!b) { throw new RuntimeException("Fails Test case."); }
	}
	
	public static void main(String[] args) {
		aiburns.hw3.BST bst = new aiburns.hw3.BST();
		
		test(bst.mostFrequent() == null);        // empty? nothing can be returned.
		bst.truncate(2);                         // has no effect.
		test(bst.depthCount(0) == 0);            // works on empty tree....
		
		test(bst.copy().root == null);                // copy of an empty BST is null.
		
		bst.put("a", 1);
		test(bst.mostFrequent().equals("a"));    // single node is most frequent...
		bst.put("rose", 4);
		bst.put("by", 2);
		bst.put("any", 3);
		bst.put("other", 5);
		
		test(bst.depthCount(-5) == 0);               // invalid input is harmless...
		
		test(bst.mostFrequent().equals("other"));    // add a few more, and one is higher than others...
		
		bst.put("name",  4);
		bst.put("would",  7);
		bst.put("smell",  8);
		bst.put("as",  2);
		bst.put("sweet",  9);
		
		BST bst_copy = bst.copy();    // make a copy and test it out!
		test(9 == bst_copy.get("sweet"));
		
		test(3 == bst.depthCount(3));
		test(5 == bst.get("other"));
		test(bst.get("other") != null);   // 'other' exists at depth 3
		test(10 == bst.size());
		test(10 == bst_copy.size());
		bst.truncate(2);
		test(2 == bst.get("by"));
		test(4 == bst.size());
		test(bst.get("other") == null);   // no longer found, since truncated away
		
		test("would".equals(bst.mostFrequent()));    // last one standing 
		test(bst.depthCount(10) == 0);               // none that deep! 
		
	} 
}