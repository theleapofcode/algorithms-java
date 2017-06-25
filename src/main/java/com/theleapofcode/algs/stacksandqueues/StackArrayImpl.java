package com.theleapofcode.algs.stacksandqueues;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class StackArrayImpl<T> implements Stack<T> {

	private static final int DEFAULT_SIZE = 2;

	private T[] items;
	private int size;

	@SuppressWarnings("unchecked")
	public StackArrayImpl() {
		this.items = (T[]) new Object[DEFAULT_SIZE];
		this.size = 0;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = size - 1; i >= 0; i--) {
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
	 * @see com.theleapofcode.algs.stacksandqueues.Stack#push(java.lang.Object)
	 */
	@Override
	public void push(T item) {
		if (size == items.length) { // When array is 100%, resize to double
			resize(2 * items.length);
		}

		items[size++] = item;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.theleapofcode.algs.stacksandqueues.Stack#pop()
	 */
	@Override
	public T pop() {
		if (isEmpty())
			throw new NoSuchElementException("Stack underflow");

		if (size == items.length / 4) { // When array is 25%, resize to half
			resize(items.length / 2);
		}

		T item = items[--size];
		items[size] = null;
		return item;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.theleapofcode.algs.stacksandqueues.Stack#peek()
	 */
	@Override
	public T peek() {
		if (isEmpty())
			throw new NoSuchElementException("Stack underflow");
		return items[size - 1];
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.theleapofcode.algs.stacksandqueues.Stack#size()
	 */
	@Override
	public int size() {
		return size;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.theleapofcode.algs.stacksandqueues.Stack#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public Iterator<T> iterator() {
		return new StackIterator();
	}

	private class StackIterator implements Iterator<T> {

		private int current = size - 1;

		@Override
		public boolean hasNext() {
			return current >= 0;
		}

		@Override
		public T next() {
			T item = items[current];
			current--;
			return item;
		}

	}

}
