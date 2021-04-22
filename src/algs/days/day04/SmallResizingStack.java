package algs.days.day04;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/** 
 * Small Resizing Stack for the example
 */
public class SmallResizingStack<Item> {
    Item[] a;         // array of items
    int N;            // number of elements on stack

    /**
     * Initializes an empty stack.
     */
    public SmallResizingStack(int capacity) {
        a = (Item[]) new Object[capacity];
        N = 0;
    }

    /**
     * Is this stack empty?
     * @return true if this stack is empty; false otherwise
     */
    public boolean isEmpty() {
        return N == 0;
    }

    /**
     * Returns the number of items in the stack.
     * @return the number of items in the stack
     */
    public int size() {
        return N;
    }

    /** Helper method to expose capacity of the stack. */
    public int capacity() {
    	return a.length;
    }
    
    // resize the underlying array holding the elements
    private void resize(int capacity) {
        assert capacity >= N;
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < N; i++) {
            temp[i] = a[i];
        }
        a = temp;
    }

    /**
     * Adds the item to this stack.
     * @param item the item to add
     */
    public void push(Item item) {
        if (N == a.length) {
        	resize (2*a.length); 
        }
        a[N++] = item;                            // add item
    }

    /**
     * Removes and returns the item most recently added to this stack.
     * @return the item most recently added
     * @throws java.util.NoSuchElementException if this stack is empty
     */
    public Item pop() {
        if (isEmpty()) throw new RuntimeException("Stack underflow");
        Item item = a[N-1];
        a[N-1] = null;                              // to avoid loitering
        N--;
        
        if (N > 0 && N == a.length/4) {             // shrink size of array
        	resize(a.length/2);                     // if necessary
        }
        return item;
    }

    /**
     * Unit tests the <tt>Stack</tt> data type.
     */
    public static void main(String[] args) {
    	SmallResizingStack<Integer> stack = new SmallResizingStack<Integer>(4);
    	
    	StdOut.println("Enter numbers, one per line. Enter '-' to pop a value. Control-z to stop.");
		while (!StdIn.isEmpty()) {
			String item = StdIn.readString();
			if (!item.equals("-")) stack.push(Integer.valueOf(item)); 
			else if (stack.isEmpty())  StdOut.println("BAD INPUT"); 
			else                       StdOut.print(stack.pop() + " ");
		}
		StdOut.println();
		
		// print what's left on the stack
		StdOut.println("(" + stack.capacity() + " allocated) contains (in LIFO order):");
		while (!stack.isEmpty()) {
			StdOut.println(stack.pop());
		}
    }
}