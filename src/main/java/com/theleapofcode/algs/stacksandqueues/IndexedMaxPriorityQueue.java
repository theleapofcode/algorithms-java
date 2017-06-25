/******************************************************************************
 *  Compilation:  javac IndexMaxPQ.java
 *  Execution:    java IndexMaxPQ
 *  Dependencies: StdOut.java
 *
 *  Maximum-oriented indexed PQ implementation using a binary heap.
 *
 ******************************************************************************/

package com.theleapofcode.algs.stacksandqueues;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The {@code IndexedMaxPriorityQueue} class represents an indexed priority
 * queue of generic keys. It supports the usual <em>insert</em> and
 * <em>delete-the-maximum</em> operations, along with <em>delete</em> and
 * <em>change-the-K</em> methods. In order to let the client refer to items on
 * the priority queue, an integer between {@code 0} and {@code maxN - 1} is
 * associated with each K—the client uses this integer to specify which K to
 * delete or change. It also supports methods for peeking at a maximum K,
 * testing if the priority queue is empty, and iterating through the keys.
 * <p>
 * This implementation uses a binary heap along with an array to associate keys
 * with integers in the given range. The <em>insert</em>,
 * <em>delete-the-maximum</em>, <em>delete</em>, <em>change-K</em>,
 * <em>decrease-K</em>, and <em>increase-K</em> operations take logarithmic
 * time. The <em>is-empty</em>, <em>size</em>, <em>max-index</em>,
 * <em>max-K</em>, and <em>K-of</em> operations take constant time. Construction
 * takes time proportional to the specified capacity.
 */
public class IndexedMaxPriorityQueue<K extends Comparable<K>> implements Iterable<Integer> {
	private int n; // number of elements on PQ
	private int[] pq; // binary heap using 1-based indexing
	private int[] qp; // inverse of pq - qp[pq[i]] = pq[qp[i]] = i
	private K[] keys; // keys[i] = priority of i

	/**
	 * Initializes an empty indexed priority queue with indices between
	 * {@code 0} and {@code maxN - 1}.
	 *
	 * @param maxN
	 *            the keys on this priority queue are index from {@code 0} to
	 *            {@code maxN - 1}
	 * @throws IllegalArgumentException
	 *             if {@code maxN < 0}
	 */
	@SuppressWarnings("unchecked")
	public IndexedMaxPriorityQueue(int maxN) {
		if (maxN < 0)
			throw new IllegalArgumentException();
		n = 0;
		keys = (K[]) new Comparable[maxN + 1]; // make this of length maxN??
		pq = new int[maxN + 1];
		qp = new int[maxN + 1]; // make this of length maxN??
		for (int i = 0; i <= maxN; i++)
			qp[i] = -1;
	}

	/**
	 * Returns true if this priority queue is empty.
	 *
	 * @return {@code true} if this priority queue is empty; {@code false}
	 *         otherwise
	 */
	public boolean isEmpty() {
		return n == 0;
	}

	/**
	 * Is {@code i} an index on this priority queue?
	 *
	 * @param i
	 *            an index
	 * @return {@code true} if {@code i} is an index on this priority queue;
	 *         {@code false} otherwise
	 * @throws IndexOutOfBoundsException
	 *             unless {@code 0 <= i < maxN}
	 */
	public boolean contains(int i) {
		return qp[i] != -1;
	}

	/**
	 * Returns the number of keys on this priority queue.
	 *
	 * @return the number of keys on this priority queue
	 */
	public int size() {
		return n;
	}

	/**
	 * Associate K with index i.
	 *
	 * @param i
	 *            an index
	 * @param K
	 *            the K to associate with index {@code i}
	 * @throws IndexOutOfBoundsException
	 *             unless {@code 0 <= i < maxN}
	 * @throws IllegalArgumentException
	 *             if there already is an item associated with index {@code i}
	 */
	public void insert(int i, K K) {
		if (contains(i))
			throw new IllegalArgumentException("index is already in the priority queue");
		n++;
		qp[i] = n;
		pq[n] = i;
		keys[i] = K;
		swim(n);
	}

	/**
	 * Returns an index associated with a maximum K.
	 *
	 * @return an index associated with a maximum K
	 * @throws NoSuchElementException
	 *             if this priority queue is empty
	 */
	public int maxIndex() {
		if (n == 0)
			throw new NoSuchElementException("Priority queue underflow");
		return pq[1];
	}

	/**
	 * Returns a maximum K.
	 *
	 * @return a maximum K
	 * @throws NoSuchElementException
	 *             if this priority queue is empty
	 */
	public K maxKey() {
		if (n == 0)
			throw new NoSuchElementException("Priority queue underflow");
		return keys[pq[1]];
	}

	/**
	 * Removes a maximum K and returns its associated index.
	 *
	 * @return an index associated with a maximum K
	 * @throws NoSuchElementException
	 *             if this priority queue is empty
	 */
	public int delMax() {
		if (n == 0)
			throw new NoSuchElementException("Priority queue underflow");
		int min = pq[1];
		exch(1, n--);
		sink(1);

		assert pq[n + 1] == min;
		qp[min] = -1; // delete
		keys[min] = null; // to help with garbage collection
		pq[n + 1] = -1; // not needed
		return min;
	}

	/**
	 * Returns the K associated with index {@code i}.
	 *
	 * @param i
	 *            the index of the K to return
	 * @return the K associated with index {@code i}
	 * @throws IndexOutOfBoundsException
	 *             unless {@code 0 <= i < maxN}
	 * @throws NoSuchElementException
	 *             no K is associated with index {@code i}
	 */
	public K keyOf(int i) {
		if (!contains(i))
			throw new NoSuchElementException("index is not in the priority queue");
		else
			return keys[i];
	}

	/**
	 * Change the K associated with index {@code i} to the specified value.
	 *
	 * @param i
	 *            the index of the K to change
	 * @param K
	 *            change the K associated with index {@code i} to this K
	 * @throws IndexOutOfBoundsException
	 *             unless {@code 0 <= i < maxN}
	 */
	public void changeKey(int i, K K) {
		if (!contains(i))
			throw new NoSuchElementException("index is not in the priority queue");
		keys[i] = K;
		swim(qp[i]);
		sink(qp[i]);
	}

	/**
	 * Increase the K associated with index {@code i} to the specified value.
	 *
	 * @param i
	 *            the index of the K to increase
	 * @param K
	 *            increase the K associated with index {@code i} to this K
	 * @throws IndexOutOfBoundsException
	 *             unless {@code 0 <= i < maxN}
	 * @throws IllegalArgumentException
	 *             if {@code K <= keyOf(i)}
	 * @throws NoSuchElementException
	 *             no K is associated with index {@code i}
	 */
	public void increaseKey(int i, K K) {
		if (!contains(i))
			throw new NoSuchElementException("index is not in the priority queue");
		if (keys[i].compareTo(K) >= 0)
			throw new IllegalArgumentException(
					"Calling increaseKey() with given argument would not strictly increase the K");

		keys[i] = K;
		swim(qp[i]);
	}

	/**
	 * Decrease the K associated with index {@code i} to the specified value.
	 *
	 * @param i
	 *            the index of the K to decrease
	 * @param K
	 *            decrease the K associated with index {@code i} to this K
	 * @throws IndexOutOfBoundsException
	 *             unless {@code 0 <= i < maxN}
	 * @throws IllegalArgumentException
	 *             if {@code K >= keyOf(i)}
	 * @throws NoSuchElementException
	 *             no K is associated with index {@code i}
	 */
	public void decreaseKey(int i, K K) {
		if (!contains(i))
			throw new NoSuchElementException("index is not in the priority queue");
		if (keys[i].compareTo(K) <= 0)
			throw new IllegalArgumentException(
					"Calling decreaseKey() with given argument would not strictly decrease the K");

		keys[i] = K;
		sink(qp[i]);
	}

	/**
	 * Remove the K on the priority queue associated with index {@code i}.
	 *
	 * @param i
	 *            the index of the K to remove
	 * @throws IndexOutOfBoundsException
	 *             unless {@code 0 <= i < maxN}
	 * @throws NoSuchElementException
	 *             no K is associated with index {@code i}
	 */
	public void delete(int i) {
		if (!contains(i))
			throw new NoSuchElementException("index is not in the priority queue");
		int index = qp[i];
		exch(index, n--);
		swim(index);
		sink(index);
		keys[i] = null;
		qp[i] = -1;
	}

	/***************************************************************************
	 * General helper functions.
	 ***************************************************************************/
	private boolean less(int i, int j) {
		return keys[pq[i]].compareTo(keys[pq[j]]) < 0;
	}

	private void exch(int i, int j) {
		int swap = pq[i];
		pq[i] = pq[j];
		pq[j] = swap;
		qp[pq[i]] = i;
		qp[pq[j]] = j;
	}

	/***************************************************************************
	 * Heap helper functions.
	 ***************************************************************************/
	private void swim(int k) {
		while (k > 1 && less(k / 2, k)) {
			exch(k, k / 2);
			k = k / 2;
		}
	}

	private void sink(int k) {
		while (2 * k <= n) {
			int j = 2 * k;
			if (j < n && less(j, j + 1))
				j++;
			if (!less(k, j))
				break;
			exch(k, j);
			k = j;
		}
	}

	/**
	 * Returns an iterator that iterates over the keys on the priority queue in
	 * descending order. The iterator doesn't implement {@code remove()} since
	 * it's optional.
	 *
	 * @return an iterator that iterates over the keys in descending order
	 */
	public Iterator<Integer> iterator() {
		return new HeapIterator();
	}

	private class HeapIterator implements Iterator<Integer> {
		// create a new pq
		private IndexedMaxPriorityQueue<K> copy;

		// add all elements to copy of heap
		// takes linear time since already in heap order so no keys move
		public HeapIterator() {
			copy = new IndexedMaxPriorityQueue<>(pq.length - 1);
			for (int i = 1; i <= n; i++)
				copy.insert(pq[i], keys[pq[i]]);
		}

		public boolean hasNext() {
			return !copy.isEmpty();
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}

		public Integer next() {
			if (!hasNext())
				throw new NoSuchElementException();
			return copy.delMax();
		}
	}

}
