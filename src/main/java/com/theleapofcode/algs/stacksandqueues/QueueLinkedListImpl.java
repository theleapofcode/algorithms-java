package com.theleapofcode.algs.stacksandqueues;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class QueueLinkedListImpl<T> implements Queue<T> {

	private static class Node<T> {
		T item;
		Node<T> next;

		Node(T item, Node<T> next) {
			this.item = item;
			this.next = next;
		}
	}

	private Node<T> first, last;
	private int size;

	public QueueLinkedListImpl() {
		this.first = null;
		this.last = null;
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
	 * @see com.theleapofcode.algs.stacksandqueues.Queue#enque(java.lang.Object)
	 */
	@Override
	public void enque(T item) {
		Node<T> oldlast = last;
		last = new Node<>(item, null);
		if (size == 0)
			first = last;
		else
			oldlast.next = last;
		size++;
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
		T item = first.item;
		first = first.next;
		size--;
		if (size == 0)
			last = null;
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
		return first.item;
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

		private Node<T> current = first;

		@Override
		public boolean hasNext() {
			return current != null;
		}

		@Override
		public T next() {
			if (!hasNext())
				throw new NoSuchElementException();
			T item = current.item;
			current = current.next;
			return item;
		}

	}

}
