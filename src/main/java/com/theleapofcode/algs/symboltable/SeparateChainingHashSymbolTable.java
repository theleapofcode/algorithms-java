package com.theleapofcode.algs.symboltable;

import com.theleapofcode.algs.stacksandqueues.Queue;
import com.theleapofcode.algs.stacksandqueues.QueueLinkedListImpl;

public class SeparateChainingHashSymbolTable<K, V> implements SymbolTable<K, V> {

	private static final int INIT_CAPACITY = 4;

	private int size; // number of key-value pairs
	private int n; // hash table size
	private SymbolTable<K, V>[] st; // array of linked-list symbol
									// tables

	public SeparateChainingHashSymbolTable() {
		this(INIT_CAPACITY);
	}

	@SuppressWarnings("unchecked")
	public SeparateChainingHashSymbolTable(int n) {
		this.n = n;
		st = (SequentialSearchSymbolTable<K, V>[]) new SequentialSearchSymbolTable[n];
		for (int i = 0; i < n; i++)
			st[i] = new SequentialSearchSymbolTable<>();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("{");
		for (int i = 0; i < st.length; i++) {
			sb.append(st[i].toString());
		}
		sb.append("}");

		return sb.toString();
	}

	private void resize(int chains) {
		SeparateChainingHashSymbolTable<K, V> temp = new SeparateChainingHashSymbolTable<>(chains);
		for (int i = 0; i < n; i++) {
			for (K key : st[i].keys()) {
				temp.put(key, st[i].get(key));
			}
		}
		this.n = temp.n;
		this.size = temp.size;
		this.st = temp.st;
	}

	private int hash(K key) {
		return (key.hashCode() & 0x7fffffff) % n;
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

		// double table size if average length of list >= 10
		if (size >= 10 * n)
			resize(2 * n);

		int i = hash(key);
		if (!st[i].contains(key))
			size++;
		st[i].put(key, value);
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
		int i = hash(key);
		return st[i].get(key);
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

		int i = hash(key);
		if (st[i].contains(key))
			size--;
		st[i].delete(key);

		// halve table size if average length of list <= 2
		if (n > INIT_CAPACITY && size <= 2 * n)
			resize(n / 2);
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
		for (int i = 0; i < n; i++) {
			for (K key : st[i].keys())
				queue.enque(key);
		}
		return queue;
	}

}
