package algs.days.day04;

import java.util.Iterator;

import edu.princeton.cs.algs4.*;

/** Slightly modified from p. 141 implementation. */
public class FixedCapacityStack<E> implements Iterable<E> {
	private E[] a;      // holds the items
	private int N;      // number of items in stack

	// create an empty stack with given capacity
	public FixedCapacityStack(int capacity) {
		a = (E[]) new Object[capacity];
		N = 0;
	}

	/** Returns values in order that you would get them if you pop'd them one at a time. */ 
	class ReverseArrayIterator implements Iterator<E> {
		private int i = N;
		public boolean hasNext() { return i > 0;  }
		public E next()    { return a[--i]; }
	}

	public boolean isEmpty()            {  return N == 0;        }
	public boolean isFull()             {  return N == a.length; }
	public void push(E item)            {  a[N++] = item;        }
	public E pop()                      {  return a[--N];        }
	public Iterator<E> iterator()       {  return new ReverseArrayIterator(); } 

	public static void main(String[] args) {
		StdOut.println("Enter numbers, one per line. Enter '-' to pop a value. Control-z to stop.");
		FixedCapacityStack<Integer> stack = new FixedCapacityStack<Integer>(100);
		while (!StdIn.isEmpty()) {
			String item = StdIn.readString();
			if (!item.equals("-")) stack.push(Integer.valueOf(item)); 
			else if (stack.isEmpty())  StdOut.println("BAD INPUT"); 
			else                       StdOut.print(stack.pop() + " ");
		}
		StdOut.println();

		// print what's left on the stack
		StdOut.println("(" + stack.N + " left on the stack)");
		for (Integer i : stack) { 
			StdOut.print(i + " ");
			StdOut.println();
		}
	} 
}