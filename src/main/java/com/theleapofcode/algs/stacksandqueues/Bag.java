package com.theleapofcode.algs.stacksandqueues;

public interface Bag<T> extends Iterable<T> {

	/**
	 * Returns true if this bag is empty.
	 *
	 * @return {@code true} if this bag is empty; {@code false} otherwise
	 */
	public boolean isEmpty();

	/**
	 * Returns the number of items in this bag.
	 *
	 * @return the number of items in this bag
	 */
	public int size();

	/**
	 * Adds the T to this bag.
	 *
	 * @param T
	 *            the T to add to this bag
	 */
	public void add(T item);

}
