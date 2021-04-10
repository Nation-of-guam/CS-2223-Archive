package algs.days.day05;

import edu.princeton.cs.algs4.*;

public class CircularBufferQueueNoLast<Item>  {
	private Item[] a;    // holds the items
	private int N;       // number of items in queue
	private int first;   // start of the queue

	// create an empty queue with given capacity
	public CircularBufferQueueNoLast(int capacity) {
		a = (Item[]) new Object[capacity];
		N = 0;
	}

	public boolean isEmpty()            {  return N == 0;                    }
	public boolean isFull()             {  return N == a.length;             }
	
	public void enqueue(Item item) {  
		if (isFull()) { throw new IllegalStateException("Queue is Full."); }
		
		a[(first+N)%a.length] = item;
		N++;
	}
	
	public Item dequeue() { 
		if (isEmpty()) { throw new IllegalStateException("Queue is Empty."); }
		
		Item val = a[first];
		a[first] = null;                       // clear unused...
		N--;                                   //   and reset size
		first = (first + 1) % a.length;
		return val;
	}

	static void output(CircularBufferQueueNoLast queue) {
		StdOut.print("[");
		for (int i = 0; i < queue.a.length; i++) {
			if (i != 0) { StdOut.print(","); }
			if (queue.a[i] == null) { StdOut.print("-"); } else { StdOut.print(queue.a[i]); }
		}
		StdOut.println("]");
	}
	
	public static void main(String[] args) {
		StdOut.println ("Enter integers to the queue, one at a time. Enter - to dequeue a value.");
		CircularBufferQueueNoLast<Integer> queue = new CircularBufferQueueNoLast<Integer>(8);
		output(queue);
		while (!StdIn.isEmpty()) {
			
			String item = StdIn.readString();
			if (item.equals("-")) {
				if (queue.isEmpty()) {
					StdOut.println ("BAD INPUT: Queue is empty");
				} else {
					StdOut.println ("Dequeued " + queue.dequeue());
				}
			} else {
				if (queue.isFull()) {
					StdOut.println("BAD INPUT: Queue is Full");
				} else {
					queue.enqueue(Integer.valueOf(item));
				}
			}
			output(queue);
		}
		StdOut.println();

		// print what's left on the queue
		StdOut.println("(" + queue.N + " item(s) left on the queue)");
	} 
} 