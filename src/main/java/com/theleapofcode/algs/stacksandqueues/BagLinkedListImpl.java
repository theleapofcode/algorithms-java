package com.theleapofcode.algs.stacksandqueues;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class BagLinkedListImpl<T> implements Bag<T> {
	private Node<T> first; // beginning of bag
	private int n; // number of elements in bag

	// helper linked list class
	private static class Node<T> {
		private T item;
		private Node<T> next;
	}

	/**
	 * Initializes an empty bag.
	 */
	public BagLinkedListImpl() {
		first = null;
		n = 0;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		Node<T> node = first;
		while (node != null) {
			sb.append(node.item).append(", ");
			node = node.next;
		}

		return sb.substring(0, sb.length() - 2);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.theleapofcode.algs.stacksandqueues.Bag#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return first == null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.theleapofcode.algs.stacksandqueues.Bag#size()
	 */
	@Override
	public int size() {
		return n;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.theleapofcode.algs.stacksandqueues.Bag#add(java.lang.Object)
	 */
	@Override
	public void add(T item) {
		Node<T> oldfirst = first;
		first = new Node<T>();
		first.item = item;
		first.next = oldfirst;
		n++;
	}

	/**
	 * Returns an iterator that iterates over the items in this bag in arbitrary
	 * order.
	 *
	 * @return an iterator that iterates over the items in this bag in arbitrary
	 *         order
	 */
	@Override
	public Iterator<T> iterator() {
		return new ListIterator(first);
	}

	private class ListIterator implements Iterator<T> {
		private Node<T> current;

		public ListIterator(Node<T> first) {
			current = first;
		}

		public boolean hasNext() {
			return current != null;
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}

		public T next() {
			if (!hasNext())
				throw new NoSuchElementException();
			T item = current.item;
			current = current.next;
			return item;
		}
	}

}
