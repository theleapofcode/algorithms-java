package com.theleapofcode.algs.stacksandqueues;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class MaxPriorityQueue<T extends Comparable<T>> implements Queue<T> {

	private static final int DEFAULT_CAPACITY = 2;

	private T[] items;
	private int size;

	@SuppressWarnings("unchecked")
	public MaxPriorityQueue() {
		this.items = (T[]) new Comparable[DEFAULT_CAPACITY];
		this.size = 0;
	}

	@SuppressWarnings("unchecked")
	public MaxPriorityQueue(int capacity) {
		this.items = (T[]) new Comparable[capacity];
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
			sb.append(items[i]).append(", ");
		}

		return sb.substring(0, sb.length() - 2);
	}

	@SuppressWarnings("unchecked")
	private void resize(int newSize) {
		T[] newArray = (T[]) new Comparable[newSize];
		for (int i = 0; i < size; i++) {
			newArray[i] = items[i];
		}

		items = newArray;
	}

	private boolean less(int i, int j) {
		return items[i].compareTo(items[j]) < 0;
	}

	private void exch(int i, int j) {
		T swap = items[i];
		items[i] = items[j];
		items[j] = swap;
	}

	private void swim(int k) {
		while (less(k / 2, k)) {
			exch(k, k / 2);
			k = k / 2;
		}
	}

	private void sink(int k) {
		while (2 * k < size) {
			int j = 2 * k; // Left child
			if (j < size - 1 && less(j, j + 1)) // j holds greater of children
				j++;
			if (!less(k, j)) // Compare parent and greater of children
				break;
			exch(k, j);
			k = j;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.theleapofcode.algs.stacksandqueues.Queue#enque(java.lang.Object)
	 */
	@Override
	public void enque(T item) {
		// double size of array if necessary
		if (size == items.length) // When array is 100%, resize to double
			resize(2 * items.length);

		// add x, and percolate it up to maintain heap invariant
		items[size] = item;
		size++;
		swim(size - 1);
	}

	@Override
	public T deque() throws NoSuchElementException {
		if (isEmpty())
			throw new NoSuchElementException("Queue underflow");
		T max = items[0];
		exch(0, --size);
		items[size] = null;
		sink(0);

		if (size == items.length / 4) { // When array is 25%, resize to half
			resize(items.length / 2);
		}

		return max;
	}

	@Override
	public T peek() throws NoSuchElementException {
		if (isEmpty())
			throw new NoSuchElementException("Queue underflow");
		return items[0];
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public Iterator<T> iterator() {
		return new HeapIterator();
	}

	private class HeapIterator implements Iterator<T> {

		// create a copy priority queue
		private MaxPriorityQueue<T> copyQueue;

		// takes linear time since already in heap order so no keys move
		public HeapIterator() {
			copyQueue = new MaxPriorityQueue<>(size);
			for (int i = 0; i < size; i++)
				copyQueue.enque(items[i]);
		}

		@Override
		public boolean hasNext() {
			return !copyQueue.isEmpty();
		}

		@Override
		public T next() {
			if (!hasNext())
				throw new NoSuchElementException();
			return copyQueue.deque();
		}

	}

}
