package algs.days.day05;

import edu.princeton.cs.algs4.*;

// Strawman implementation. This is not the way a queue should be implemented....
public class FixedCapacityQueue<Item>  {
	private Item[] a;  // holds the items
	private int N;       // number of items in queue

	// create an empty queue with given capacity
	public FixedCapacityQueue(int capacity) {
		a = (Item[]) new Object[capacity];
		N = 0;
	}

	public boolean isEmpty()            {  return N == 0;                    }
	public boolean isFull()             {  return N == a.length;             }
	public void enqueue(Item item)      {  a[N++] = item;                    }
	
	
	public Item dequeue() { 
		Item val = a[0];
		for (int i = 0; i < N-1; i++) {   // shift all elements down one
			a[i] = a[i+1];
		}
		a[--N] = null;                  // clear unused and reset size
		return val;
	}
	
	static void output(FixedCapacityQueue<?> queue) {
		StdOut.print("[");
		for (int i = 0; i < queue.a.length; i++) {
			if (i != 0) { StdOut.print(","); }
			if (queue.a[i] == null) { StdOut.print("-"); } else { StdOut.print(queue.a[i]); }
		}
		StdOut.println("]");
	}

	public static void main(String[] args) {
		FixedCapacityQueue<Integer> queue = new FixedCapacityQueue<Integer>(10);
		StdOut.println ("Enter integers to the queue, one at a time. Enter - to dequeue a value.");
		output(queue);
		while (!StdIn.isEmpty()) {
			String item = StdIn.readString();
			if (item.equals("-")) {
				if (queue.isEmpty()) {
					StdOut.println ("BAD INPUT");
				} else {
					StdOut.println ("Dequeued " + queue.dequeue());
				}
			} else {
				queue.enqueue(Integer.valueOf(item));
			}
			output(queue);
		}
		StdOut.println();

		// print what's left on the queue
		StdOut.println("(" + queue.N + " left on the queue)");
	} 
} 