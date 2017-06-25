package com.theleapofcode.algs.symboltable;

import com.theleapofcode.algs.stacksandqueues.Queue;
import com.theleapofcode.algs.stacksandqueues.QueueLinkedListImpl;

public class SequentialSearchSymbolTable<K, V> implements SymbolTable<K, V> {

	private class Node {
		K key;
		V value;
		Node next;

		Node(K key, V value, Node next) {
			this.key = key;
			this.value = value;
			this.next = next;
		}

		@Override
		public String toString() {
			return new StringBuilder().append(key).append(" = ").append(value).toString();
		}
	}

	private Node first; // the linked list of key-value pairs
	private int size;

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("[");
		for (Node node = first; node != null; node = node.next) {
			sb.append("(").append(node.key).append(" = ").append(node.value).append(")");
		}
		sb.append("]");

		return sb.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.theleapofcode.algs.symboltable.SymbolTable#put(java.lang.Object,
	 * java.lang.Object)
	 */
	@Override
	public void put(K key, V value) {
		if (key == null)
			throw new IllegalArgumentException("key is null");

		if (value == null) {
			delete(key);
			return;
		}

		for (Node node = first; node != null; node = node.next) {
			if (key.equals(node.key)) {
				node.value = value;
				return;
			}
		}
		first = new Node(key, value, first);
		size++;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.theleapofcode.algs.symboltable.SymbolTable#get(java.lang.Object)
	 */
	@Override
	public V get(K key) {
		if (key == null)
			throw new IllegalArgumentException("key is null");
		if (isEmpty())
			return null;

		for (Node node = first; node != null; node = node.next) {
			if (key.equals(node.key)) {
				return node.value;
			}
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.theleapofcode.algs.symboltable.SymbolTable#delete(java.lang.Object)
	 */
	@Override
	public void delete(K key) {
		if (key == null)
			throw new IllegalArgumentException("key is null");
		if (isEmpty())
			return;

		Node node = first;
		if (key.equals(node.key)) {
			first = node.next;
			node.next = null;
			size--;
			return;
		}

		while (node.next != null) {
			if (key.equals(node.next.key)) {
				Node next = node.next.next;
				node.next.next = null;
				node.next = next;
				size--;
				return;
			}
			node = node.next;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.theleapofcode.algs.symboltable.SymbolTable#contains(java.lang.Object)
	 */
	@Override
	public boolean contains(K key) {
		return get(key) != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.theleapofcode.algs.symboltable.SymbolTable#size()
	 */
	@Override
	public int size() {
		return size;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.theleapofcode.algs.symboltable.SymbolTable#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.theleapofcode.algs.symboltable.SymbolTable#keys()
	 */
	@Override
	public Iterable<K> keys() {
		Queue<K> queue = new QueueLinkedListImpl<>();
		for (Node node = first; node != null; node = node.next) {
			queue.enque(node.key);
		}

		return queue;
	}

}
