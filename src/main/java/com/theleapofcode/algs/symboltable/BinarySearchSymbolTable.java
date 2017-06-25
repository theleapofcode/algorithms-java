package com.theleapofcode.algs.symboltable;

import java.util.NoSuchElementException;

import com.theleapofcode.algs.stacksandqueues.Queue;
import com.theleapofcode.algs.stacksandqueues.QueueArrayImpl;

public class BinarySearchSymbolTable<K extends Comparable<K>, V> implements OrderedSymbolTable<K, V> {

	private static final int INIT_CAPACITY = 2;
	private K[] keys;
	private V[] values;
	private int size;

	public BinarySearchSymbolTable() {
		this(INIT_CAPACITY);
	}

	@SuppressWarnings("unchecked")
	public BinarySearchSymbolTable(int capacity) {
		this.keys = (K[]) new Comparable[capacity];
		this.values = (V[]) new Object[capacity];
		this.size = 0;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("[");
		for (int i = 0; i < size; i++) {
			sb.append("(").append(keys[i]).append(" = ").append(values[i]).append(")");
		}
		sb.append("]");

		return sb.toString();
	}

	// resize the underlying arrays
	@SuppressWarnings("unchecked")
	private void resize(int capacity) {
		K[] tempk = (K[]) new Comparable[capacity];
		V[] tempv = (V[]) new Object[capacity];
		for (int i = 0; i < size; i++) {
			tempk[i] = keys[i];
			tempv[i] = values[i];
		}
		values = tempv;
		keys = tempk;
	}

	private int rank(K key) {
		if (key == null)
			throw new IllegalArgumentException("key is null");

		int lo = 0, hi = size - 1;
		while (lo <= hi) {
			int mid = lo + (hi - lo) / 2;
			int cmp = key.compareTo(keys[mid]);
			if (cmp < 0)
				hi = mid - 1;
			else if (cmp > 0)
				lo = mid + 1;
			else
				return mid;
		}
		return lo;
	}

	/* (non-Javadoc)
	 * @see com.theleapofcode.algs.symboltable.SymbolTable#put(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void put(K key, V value) {
		if (key == null)
			throw new IllegalArgumentException("key is null");

		if (value == null) {
			delete(key);
			return;
		}

		int i = rank(key);

		// key is already in table. So update value
		if (i < size && keys[i].compareTo(key) == 0) {
			values[i] = value;
			return;
		}

		// insert new key-value pair
		if (size == keys.length)
			resize(2 * keys.length);

		for (int j = size; j > i; j--) {
			keys[j] = keys[j - 1];
			values[j] = values[j - 1];
		}
		keys[i] = key;
		values[i] = value;
		size++;
	}

	/* (non-Javadoc)
	 * @see com.theleapofcode.algs.symboltable.SymbolTable#get(java.lang.Object)
	 */
	@Override
	public V get(K key) {
		if (key == null)
			throw new IllegalArgumentException("key is null");
		if (isEmpty())
			return null;
		int i = rank(key);
		if (i < size && keys[i].compareTo(key) == 0)
			return values[i];
		return null;
	}

	/* (non-Javadoc)
	 * @see com.theleapofcode.algs.symboltable.SymbolTable#delete(java.lang.Object)
	 */
	@Override
	public void delete(K key) {
		if (key == null)
			throw new IllegalArgumentException("key is null");
		if (isEmpty())
			return;

		// compute rank
		int i = rank(key);

		// key not in table
		if (i == size || keys[i].compareTo(key) != 0) {
			return;
		}

		for (int j = i; j < size - 1; j++) {
			keys[j] = keys[j + 1];
			values[j] = values[j + 1];
		}

		size--;
		keys[size] = null; // to avoid loitering
		values[size] = null;

		// resize if 1/4 full
		if (size > 0 && size == keys.length / 4)
			resize(keys.length / 2);
	}

	/* (non-Javadoc)
	 * @see com.theleapofcode.algs.symboltable.SymbolTable#contains(java.lang.Object)
	 */
	@Override
	public boolean contains(K key) {
		return get(key) != null;
	}

	/* (non-Javadoc)
	 * @see com.theleapofcode.algs.symboltable.SymbolTable#size()
	 */
	@Override
	public int size() {
		return size;
	}

	/* (non-Javadoc)
	 * @see com.theleapofcode.algs.symboltable.SymbolTable#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	/* (non-Javadoc)
	 * @see com.theleapofcode.algs.symboltable.SymbolTable#keys()
	 */
	@Override
	public Iterable<K> keys() {
		return keys(minimum(), maximum());
	}

	/* (non-Javadoc)
	 * @see com.theleapofcode.algs.symboltable.SymbolTable#keys(java.lang.Object, java.lang.Object)
	 */
	@Override
	public Iterable<K> keys(K lo, K hi) {
		if (lo == null)
			throw new IllegalArgumentException("lo key is null");
		if (hi == null)
			throw new IllegalArgumentException("high key is null");

		Queue<K> queue = new QueueArrayImpl<>();
		if (lo.compareTo(hi) > 0)
			return queue;
		for (int i = rank(lo); i < rank(hi); i++)
			queue.enque(keys[i]);
		if (contains(hi))
			queue.enque(keys[rank(hi)]);
		return queue;
	}

	/* (non-Javadoc)
	 * @see com.theleapofcode.algs.symboltable.OrderedSymbolTable#minimum()
	 */
	@Override
	public K minimum() {
		if (isEmpty())
			throw new NoSuchElementException("Empty symbol table");
		return keys[0];
	}

	/* (non-Javadoc)
	 * @see com.theleapofcode.algs.symboltable.OrderedSymbolTable#maximum()
	 */
	@Override
	public K maximum() {
		if (isEmpty())
			throw new NoSuchElementException("Empty symbol table");
		return keys[size - 1];
	}

	/* (non-Javadoc)
	 * @see com.theleapofcode.algs.symboltable.OrderedSymbolTable#floor(java.lang.Comparable)
	 */
	@Override
	public K floor(K key) {
		if (key == null)
			throw new IllegalArgumentException("key is null");
		int i = rank(key);
		if (i < size && key.compareTo(keys[i]) == 0)
			return keys[i];
		if (i == 0)
			return null;
		else
			return keys[i - 1];
	}

	/* (non-Javadoc)
	 * @see com.theleapofcode.algs.symboltable.OrderedSymbolTable#ceiling(java.lang.Comparable)
	 */
	@Override
	public K ceiling(K key) {
		if (key == null)
			throw new IllegalArgumentException("key is null");
		int i = rank(key);
		if (i == size)
			return null;
		else
			return keys[i];
	}

	/* (non-Javadoc)
	 * @see com.theleapofcode.algs.symboltable.OrderedSymbolTable#select(int)
	 */
	@Override
	public K select(int index) {
		if (index < 0 || index >= size()) {
			throw new IllegalArgumentException("Invalid Index");
		}
		return keys[index];
	}

}
