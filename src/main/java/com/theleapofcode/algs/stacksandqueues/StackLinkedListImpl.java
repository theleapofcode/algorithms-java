package com.theleapofcode.algs.stacksandqueues;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class StackLinkedListImpl<T> implements Stack<T> {

	private static class Node<T> {
		T item;
		Node<T> next;

		Node(T item, Node<T> next) {
			this.item = item;
			this.next = next;
		}
	}

	private Node<T> first;
	private int size;

	public StackLinkedListImpl() {
		this.first = null;
		this.size = 0;
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
	 * @see com.theleapofcode.algs.stacksandqueues.Stack#push(java.lang.Object)
	 */
	@Override
	public void push(T item) {
		Node<T> oldfirst = first;
		first = new Node<>(item, oldfirst);
		size++;
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
		T item = first.item;
		first = first.next;
		size--;
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
		return first.item;
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

		private Node<T> current = first;

		@Override
		public boolean hasNext() {
			return current != null;
		}

		@Override
		public T next() {
			T item = current.item;
			current = current.next;
			return item;
		}

	}

}
