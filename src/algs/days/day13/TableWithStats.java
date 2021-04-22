package algs.days.day13;

import edu.princeton.cs.algs4.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

// A GOOD HASH TABLE SIZE WITH A BAD HASH TABLE SIZE
// 524288	5.166677565713467	126.0
// 524289	160565.0	        321129.0

// BE CAREFUL RUNNING THIS CODE -- TAKES A LONG TIME TO RUN....

public class TableWithStats<Key, Value> {
	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(new File("words.english.txt"));
		ArrayList<String> words = new ArrayList<>();
		while (sc.hasNextLine()) {
			String s = sc.nextLine();
			words.add(s);
		}
		sc.close();
		
		System.out.println("This will take awhile! it's what happens when you have a bad HASH strategy");
		// check one before, and one after. Look at difference!
		for (int m = 524287; m <= 524289; m++) {
			TableWithStats<String,Integer> tbl = new TableWithStats<>(m);
			for (String s : words) { 
				tbl.put(s, 99);
			}
			
			double stat[] = tbl.stats();
			System.out.println(m + "\t" + stat[0] + "\t" + stat[1]);
		}
	}
	

	/** Compute average and max length of chains. */
	public double[] stats() {
		int size= keys.length; 
		int max_length = 0;
		SeparateChainingHashST<Integer, Integer> sizes = new SeparateChainingHashST<>();
		for (int idx = 0; idx < size; idx++) {
			if (this.keys[idx] != null) {
				int num = 0;
				int i = idx;
				while (this.keys[i] != null) {
					i = (i+1) % size;
					num++;
				}

				if (sizes.contains(num)) {
					sizes.put(num, 1 + sizes.get(num));
				} else {
					sizes.put(num, 1);
				}

				if (num > max_length) { max_length = num; }

			}
		}

		double weighted_total = 0;
		for (int sz : sizes.keys()) {
			weighted_total += sz*sizes.get(sz);
		}
		
		return new double[] { weighted_total/n, max_length};
	}

		/**
		 *  The {@code LinearProbingHashST} class represents a symbol table of generic
		 *  key-value pairs.
		 *  It supports the usual <em>put</em>, <em>get</em>, <em>contains</em>,
		 *  <em>delete</em>, <em>size</em>, and <em>is-empty</em> methods.
		 *  It also provides a <em>keys</em> method for iterating over all of the keys.
		 *  A symbol table implements the <em>associative array</em> abstraction:
		 *  when associating a value with a key that is already in the symbol table,
		 *  the convention is to replace the old value with the new value.
		 *  Unlike {@link java.util.Map}, this class uses the convention that
		 *  values cannot be {@code null}—setting the
		 *  value associated with a key to {@code null} is equivalent to deleting the key
		 *  from the symbol table.
		 *  <p>
		 *  This implementation uses a linear probing hash table. It requires that
		 *  the key type overrides the {@code equals()} and {@code hashCode()} methods.
		 *  The expected time per <em>put</em>, <em>contains</em>, or <em>remove</em>
		 *  operation is constant, subject to the uniform hashing assumption.
		 *  The <em>size</em>, and <em>is-empty</em> operations take constant time.
		 *  Construction takes constant time.
		 *  <p>
		 *  For additional documentation, see <a href="https://algs4.cs.princeton.edu/34hash">Section 3.4</a> of
		 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
		 *  For other implementations, see {@link ST}, {@link BinarySearchST},
		 *  {@link SequentialSearchST}, {@link BST}, {@link RedBlackBST}, and
		 *  {@link SeparateChainingHashST},
		 *
		 *  @author Robert Sedgewick
		 *  @author Kevin Wayne
		 */

		// must be a power of 2
		private static final int INIT_CAPACITY = 4;

		private int n;           // number of key-value pairs in the symbol table
		private int m;           // size of linear probing table
		private Key[] keys;      // the keys
		private Value[] vals;    // the values


		/**
		 * Initializes an empty symbol table.
		 */
		public TableWithStats() {
			this(INIT_CAPACITY);
		}

		/**
		 * Initializes an empty symbol table with the specified initial capacity.
		 *
		 * @param capacity the initial capacity
		 */
		public TableWithStats(int capacity) {
			m = capacity;
			n = 0;
			keys = (Key[])   new Object[m];
			vals = (Value[]) new Object[m];
		}

		/**
		 * Returns the number of key-value pairs in this symbol table.
		 *
		 * @return the number of key-value pairs in this symbol table
		 */
		public int size() {
			return n;
		}

		/**
		 * Returns true if this symbol table is empty.
		 *
		 * @return {@code true} if this symbol table is empty;
		 *         {@code false} otherwise
		 */
		public boolean isEmpty() {
			return size() == 0;
		}

		/**
		 * Returns true if this symbol table contains the specified key.
		 *
		 * @param  key the key
		 * @return {@code true} if this symbol table contains {@code key};
		 *         {@code false} otherwise
		 * @throws IllegalArgumentException if {@code key} is {@code null}
		 */
		public boolean contains(Key key) {
			if (key == null) throw new IllegalArgumentException("argument to contains() is null");
			return get(key) != null;
		}
		
		 // hash function for keys - returns value between 0 and m-1
	    private int hashTextbook(Key key) {
	        return (key.hashCode() & 0x7fffffff) % m;
	    }

	    // hash function for keys - returns value between 0 and m-1 (assumes m is a power of 2)
	    // (from Java 7 implementation, protects against poor quality hashCode() implementations)
	    private int hash(Key key) {
	        int h = key.hashCode();
	        h ^= (h >>> 20) ^ (h >>> 12) ^ (h >>> 7) ^ (h >>> 4);
	        return h & (m-1);
	    }

		/**
		 * Inserts the specified key-value pair into the symbol table, overwriting the old 
		 * value with the new value if the symbol table already contains the specified key.
		 * Deletes the specified key (and its associated value) from this symbol table
		 * if the specified value is {@code null}.
		 *
		 * @param  key the key
		 * @param  val the value
		 * @throws IllegalArgumentException if {@code key} is {@code null}
		 */
		public void put(Key key, Value val) {
			int i;
			for (i = hash(key); keys[i] != null; i = (i + 1) % m) {
				if (keys[i].equals(key)) {
					vals[i] = val;
					return;
				}
			}
			keys[i] = key;
			vals[i] = val;
			n++;
		}

		/**
		 * Returns the value associated with the specified key.
		 * @param key the key
		 * @return the value associated with {@code key};
		 *         {@code null} if no such value
		 * @throws IllegalArgumentException if {@code key} is {@code null}
		 */
		public Value get(Key key) {
			if (key == null) throw new IllegalArgumentException("argument to get() is null");
			for (int i = hash(key); keys[i] != null; i = (i + 1) % m)
				if (keys[i].equals(key))
					return vals[i];
			return null;
		}

	}

	/******************************************************************************
	 *  Copyright 2002-2020, Robert Sedgewick and Kevin Wayne.
	 *
	 *  This file is part of algs4.jar, which accompanies the textbook
	 *
	 *      Algorithms, 4th edition by Robert Sedgewick and Kevin Wayne,
	 *      Addison-Wesley Professional, 2011, ISBN 0-321-57351-X.
	 *      http://algs4.cs.princeton.edu
	 *
	 *
	 *  algs4.jar is free software: you can redistribute it and/or modify
	 *  it under the terms of the GNU General Public License as published by
	 *  the Free Software Foundation, either version 3 of the License, or
	 *  (at your option) any later version.
	 *
	 *  algs4.jar is distributed in the hope that it will be useful,
	 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
	 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	 *  GNU General Public License for more details.
	 *
	 *  You should have received a copy of the GNU General Public License
	 *  along with algs4.jar.  If not, see http://www.gnu.org/licenses.
	 ******************************************************************************/
