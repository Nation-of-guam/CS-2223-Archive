package algs.days.day04;

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.*;

/** 
 * A Copy of resize that provides two resize strategies:
 * 
 *    1. extend size of array by 100 items with each new one; and
 *    2. double array in size once full.
 *    
 * Sample run looks like this:
 * 
 * 
Resize by extending by 100 positions
Time: 0.05700 (size = 60006, #Resize = 601) Avg=0.02907 +/- 0.01413
Time: 0.04400 (size = 60160, #Resize = 602) Avg=0.01488 +/- 0.01283
Time: 0.03400 (size = 60148, #Resize = 602) Avg=0.01194 +/- 0.00997
Time: 0.03500 (size = 60066, #Resize = 601) Avg=0.01153 +/- 0.00979
Time: 0.04400 (size = 59462, #Resize = 595) Avg=0.01697 +/- 0.01356
Resize by doubling
Time: 0.01200 (size = 59670, #Resize = 15) Avg=0.00738 +/- 0.00278
Time: 0.00800 (size = 60154, #Resize = 15) Avg=0.00426 +/- 0.00254
Time: 0.00700 (size = 59582, #Resize = 15) Avg=0.00361 +/- 0.00178
Time: 0.00700 (size = 59908, #Resize = 15) Avg=0.00386 +/- 0.00177
Time: 0.00600 (size = 60256, #Resize = 15) Avg=0.00321 +/- 0.00179

 * Resize by doubling wins hands down.
 */
public class ResizingArrayStackResizeStrategies<Item> implements Iterable<Item> {
    private Item[] a;         // array of items
    private int N;            // number of elements on stack

    static int numResizes = 0;
    static boolean AdditiveStrategy = true;    // grow array by fixed incremental growth of +100
    
    /**
     * Initializes an empty stack.
     */
    public ResizingArrayStackResizeStrategies() {
        a = (Item[]) new Object[2];
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


    // resize the underlying array holding the elements
    private void resize(int capacity) {
        assert capacity >= N;
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < N; i++) {
            temp[i] = a[i];
        }
        a = temp;
        numResizes++;
    }

    /**
     * Adds the item to this stack.
     * @param item the item to add
     */
    public void push(Item item) {
        if (N == a.length) {
        	// evaluate two strategies
        	if (AdditiveStrategy) { 
        		resize(100+a.length);
        	} else { 
        		resize (2*a.length); 
        	}
        }
        a[N++] = item;                            // add item
    }

    /**
     * Removes and returns the item most recently added to this stack.
     * @return the item most recently added
     * @throws java.util.NoSuchElementException if this stack is empty
     */
    public Item pop() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        Item item = a[N-1];
        a[N-1] = null;                              // to avoid loitering
        N--;
        
        if (N > 0 && N == a.length/4) {             // shrink size of array
        	resize(a.length/2);                     // if necessary
        }
        return item;
    }

    /**
     * Returns (but does not remove) the item most recently added to this stack.
     * @return the item most recently added to this stack
     * @throws java.util.NoSuchElementException if this stack is empty
     */
    public Item peek() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        return a[N-1];
    }

    /**
     * Returns an iterator to this stack that iterates through the items in LIFO order.
     * @return an iterator to this stack that iterates through the items in LIFO order.
     */
    public Iterator<Item> iterator() {
        return new ReverseArrayIterator();
    }

    // an iterator, doesn't implement remove() since it's optional
    private class ReverseArrayIterator implements Iterator<Item> {
        private int i;

        public ReverseArrayIterator() {
            i = N-1;
        }

        public boolean hasNext() {
            return i >= 0;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return a[i--];
        }
    }

    /** Run a single trial. */
    static void runTrial() {
    	numResizes = 0;
    	ResizingArrayStackResizeStrategies<Integer> stack = new ResizingArrayStackResizeStrategies<Integer>();
        
        // start with a stack of 100 random elements
        for (int i = 0; i < 100; i++) {
        	stack.push(StdRandom.uniform(1000));
        }
        
        // for large number of operations randomly push and pop with .80 probability of push
        int numOperations = 100000;
        Stopwatch sw = new Stopwatch();
        double results[] = new double[numOperations];
        for (int i = 0; i < numOperations; i++) { results[i] = 0; }  // eliminate timing issues
        
        for (int i = 0; i < numOperations; i++) {
        	if (StdRandom.uniform() < 0.80) {
        		stack.push(StdRandom.uniform(1000));
        	} else {
        		stack.pop();
        	}
        	results[i] = sw.elapsedTime();
        }
        
        StdOut.printf("Time: %.5f (size = %d, #Resize = %d) Avg=%.5f +/- %.5f\n",
        		sw.elapsedTime(), stack.size(), numResizes, StdStats.mean(results), StdStats.stddev(results));
    }
    
    /**
     * Unit tests the <tt>Stack</tt> data type.
     */
    public static void main(String[] args) {
    	System.out.println("Resize by extending by 100 positions");
    	AdditiveStrategy = true;
        for (int i = 0; i < 5; i++) {
        	runTrial();
        }
        
        System.out.println("Resize by doubling");
    	AdditiveStrategy = false;
        for (int i = 0; i < 5; i++) {
        	runTrial();
        }
    }
}