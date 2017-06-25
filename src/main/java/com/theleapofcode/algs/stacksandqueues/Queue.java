package com.theleapofcode.algs.stacksandqueues;

import java.util.NoSuchElementException;

public interface Queue<T> extends Iterable<T> {

	/**
	 * Adds the item to end of queue.
	 *
	 * @param item
	 *            the item to add
	 */
	public void enque(T item);

	/**
	 * Removes and returns the first item from the queue.
	 *
	 * @return the item first added
	 * @throws NoSuchElementException
	 *             if this queue is empty
	 */
	public T deque() throws NoSuchElementException;

	/**
	 * Returns (but does not remove) the first item from the queue.
	 *
	 * @return the item first added
	 * @throws NoSuchElementException
	 *             if this queue is empty
	 */
	public T peek() throws NoSuchElementException;

	/**
	 * Returns the number of items in this queue.
	 *
	 * @return the number of items in this queue
	 */
	public int size();

	/**
	 * Returns true if this queue is empty.
	 *
	 * @return true if this queue is empty; false otherwise
	 */
	public boolean isEmpty();

}
