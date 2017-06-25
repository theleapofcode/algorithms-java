package com.theleapofcode.algs.stacksandqueues;

import java.util.NoSuchElementException;

public interface Stack<T> extends Iterable<T> {

	/**
	 * Adds the item to this stack.
	 *
	 * @param item
	 *            the item to add
	 */
	public void push(T item);

	/**
	 * Removes and returns the item most recently added to this stack.
	 *
	 * @return the item most recently added
	 * @throws NoSuchElementException
	 *             if this stack is empty
	 */
	public T pop() throws NoSuchElementException;

	/**
	 * Returns (but does not remove) the item most recently added to this stack.
	 *
	 * @return the item most recently added to this stack
	 * @throws NoSuchElementException
	 *             if this stack is empty
	 */
	public T peek() throws NoSuchElementException;

	/**
	 * Returns the number of items in this stack.
	 *
	 * @return the number of items in this stack
	 */
	public int size();

	/**
	 * Returns true if this stack is empty.
	 *
	 * @return true if this stack is empty; false otherwise
	 */
	public boolean isEmpty();

}
