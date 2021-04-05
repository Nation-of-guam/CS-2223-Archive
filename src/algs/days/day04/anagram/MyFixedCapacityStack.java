package algs.days.day04.anagram;

/** Slightly modified from p. 141 implementation. */
public class MyFixedCapacityStack {
	int[] a;      // holds the items
	int N;      // number of items in stack

	// create an empty stack with given capacity
	public MyFixedCapacityStack(int capacity) {
		a = new int[capacity];
		N = 0;
	}

	public boolean isEmpty()            {  return N == 0;        }
	public boolean isFull()             {  return N == a.length; }
	public void push(int item)          {  a[N++] = item;        }
	public int pop()                    {  return a[--N];        }

	// cheat a bit, and allow method to search through stack for entry.
	public boolean contains(int next) {
		for (int i = 0; i < N; i++) {
			if (a[i] == next) { return true; }
		}
		return false;
	}
}
