package com.theleapofcode.algs.stacksandqueues;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class QueueArrayImpl<T> implements Queue<T> {

	private static final int DEFAULT_SIZE = 2;

	private T[] items;
	private int size, head, tail;

	@SuppressWarnings("unchecked")
	public QueueArrayImpl() {
		this.items = (T[]) new Object[DEFAULT_SIZE];
		this.size = 0;
		this.head = 0;
		this.tail = 0;
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
		T[] newArray = (T[]) new Object[newSize];
		for (int i = 0; i < size; i++) {
			newArray[i] = items[i];
		}

		items = newArray;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.theleapofcode.algs.stacksandqueues.Queue#enque(java.lang.Object)
	 */
	@Override
	public void enque(T item) {
		items[tail] = item;
		size++;

		if (size == items.length) { // When array is 100%, resize to double
			resize(2 * items.length);
		}

		tail = (tail + 1) % items.length;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.theleapofcode.algs.stacksandqueues.Queue#deque()
	 */
	@Override
	public T deque() {
		if (isEmpty())
			throw new NoSuchElementException("Queue underflow");

		T item = items[head];
		items[head] = null;
		size--;

		if (size == items.length / 4) { // When array is 25%, resize to half
			resize(items.length / 2);
		}

		head = (head + 1) % items.length;

		return item;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.theleapofcode.algs.stacksandqueues.Queue#peek()
	 */
	@Override
	public T peek() {
		if (isEmpty())
			throw new NoSuchElementException("Queue underflow");
		return items[head];
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.theleapofcode.algs.stacksandqueues.Queue#size()
	 */
	@Override
	public int size() {
		return size;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.theleapofcode.algs.stacksandqueues.Queue#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public Iterator<T> iterator() {
		return new QueueIterator();
	}

	private class QueueIterator implements Iterator<T> {

		private int current = 0;

		@Override
		public boolean hasNext() {
			return current < size;
		}

		@Override
		public T next() {
			if (!hasNext())
				throw new NoSuchElementException();
			T item = items[current];
			current++;
			return item;
		}

	}

}
