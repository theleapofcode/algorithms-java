package com.theleapofcode.algs.trie;

import com.theleapofcode.algs.stacksandqueues.Queue;
import com.theleapofcode.algs.stacksandqueues.QueueLinkedListImpl;

/**
 * The {@code TST} class represents an symbol table of key-value pairs, with
 * string keys and generic values. It supports the usual <em>put</em>,
 * <em>get</em>, <em>contains</em>, <em>delete</em>, <em>size</em>, and
 * <em>is-empty</em> methods. It also provides character-based methods for
 * finding the string in the symbol table that is the <em>longest prefix</em> of
 * a given prefix, finding all strings in the symbol table that <em>start
 * with</em> a given prefix, and finding all strings in the symbol table that
 * <em>match</em> a given pattern. A symbol table implements the <em>associative
 * array</em> abstraction: when associating a value with a key that is already
 * in the symbol table, the convention is to replace the old value with the new
 * value. Unlike {@link java.util.Map}, this class uses the convention that
 * values cannot be {@code null}—setting the value associated with a key to
 * {@code null} is equivalent to deleting the key from the symbol table.
 * <p>
 * This implementation uses a ternary search trie.
 */
public class TernarySearchTrieSymbolTable<V> implements StringSymbolTable<V> {
	private int n; // size
	private Node<V> root; // root of TST

	private static class Node<Value> {
		private char c; // character
		private Node<Value> left, mid, right; // left, middle, and right
												// subtries
		private Value val; // value associated with string
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.theleapofcode.algs.symboltable.SymbolTable#size()
	 */
	@Override
	public int size() {
		return n;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.theleapofcode.algs.symboltable.SymbolTable#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return n == 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.theleapofcode.algs.symboltable.SymbolTable#contains(java.lang.Object)
	 */
	@Override
	public boolean contains(String key) {
		if (key == null) {
			throw new IllegalArgumentException("argument to contains() is null");
		}
		return get(key) != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.theleapofcode.algs.symboltable.SymbolTable#get(java.lang.Object)
	 */
	@Override
	public V get(String key) {
		if (key == null) {
			throw new IllegalArgumentException("key is null");
		}
		if (key.length() == 0)
			throw new IllegalArgumentException("key must have length >= 1");
		Node<V> x = get(root, key, 0);
		if (x == null)
			return null;
		return x.val;
	}

	// return subtrie corresponding to given key
	private Node<V> get(Node<V> x, String key, int d) {
		if (x == null)
			return null;
		if (key.length() == 0)
			throw new IllegalArgumentException("key must have length >= 1");
		char c = key.charAt(d);
		if (c < x.c)
			return get(x.left, key, d);
		else if (c > x.c)
			return get(x.right, key, d);
		else if (d < key.length() - 1)
			return get(x.mid, key, d + 1);
		else
			return x;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.theleapofcode.algs.symboltable.SymbolTable#put(java.lang.Object,
	 * java.lang.Object)
	 */
	@Override
	public void put(String key, V val) {
		if (key == null) {
			throw new IllegalArgumentException("calls put() with null key");
		}
		if (!contains(key))
			n++;
		root = put(root, key, val, 0);
	}

	private Node<V> put(Node<V> x, String key, V val, int d) {
		char c = key.charAt(d);
		if (x == null) {
			x = new Node<>();
			x.c = c;
		}
		if (c < x.c)
			x.left = put(x.left, key, val, d);
		else if (c > x.c)
			x.right = put(x.right, key, val, d);
		else if (d < key.length() - 1)
			x.mid = put(x.mid, key, val, d + 1);
		else
			x.val = val;
		return x;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.theleapofcode.algs.trie.StringSymbolTable#longestPrefixOf(java.lang.
	 * String)
	 */
	@Override
	public String longestPrefixOf(String query) {
		if (query == null) {
			throw new IllegalArgumentException("query is null");
		}
		if (query.length() == 0)
			return null;
		int length = 0;
		Node<V> x = root;
		int i = 0;
		while (x != null && i < query.length()) {
			char c = query.charAt(i);
			if (c < x.c)
				x = x.left;
			else if (c > x.c)
				x = x.right;
			else {
				i++;
				if (x.val != null)
					length = i;
				x = x.mid;
			}
		}
		return query.substring(0, length);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.theleapofcode.algs.symboltable.SymbolTable#keys()
	 */
	@Override
	public Iterable<String> keys() {
		Queue<String> queue = new QueueLinkedListImpl<>();
		collect(root, new StringBuilder(), queue);
		return queue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.theleapofcode.algs.trie.StringSymbolTable#keysWithPrefix(java.lang.
	 * String)
	 */
	@Override
	public Iterable<String> keysWithPrefix(String prefix) {
		if (prefix == null) {
			throw new IllegalArgumentException("calls keysWithPrefix() with null argument");
		}
		Queue<String> queue = new QueueLinkedListImpl<>();
		Node<V> x = get(root, prefix, 0);
		if (x == null)
			return queue;
		if (x.val != null)
			queue.enque(prefix);
		collect(x.mid, new StringBuilder(prefix), queue);
		return queue;
	}

	// all keys in subtrie rooted at x with given prefix
	private void collect(Node<V> x, StringBuilder prefix, Queue<String> queue) {
		if (x == null)
			return;
		collect(x.left, prefix, queue);
		if (x.val != null)
			queue.enque(prefix.toString() + x.c);
		collect(x.mid, prefix.append(x.c), queue);
		prefix.deleteCharAt(prefix.length() - 1);
		collect(x.right, prefix, queue);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.theleapofcode.algs.trie.StringSymbolTable#keysThatMatch(java.lang.
	 * String)
	 */
	@Override
	public Iterable<String> keysThatMatch(String pattern) {
		Queue<String> queue = new QueueLinkedListImpl<>();
		collect(root, new StringBuilder(), 0, pattern, queue);
		return queue;
	}

	private void collect(Node<V> x, StringBuilder prefix, int i, String pattern, Queue<String> queue) {
		if (x == null)
			return;
		char c = pattern.charAt(i);
		if (c == '.' || c < x.c)
			collect(x.left, prefix, i, pattern, queue);
		if (c == '.' || c == x.c) {
			if (i == pattern.length() - 1 && x.val != null)
				queue.enque(prefix.toString() + x.c);
			if (i < pattern.length() - 1) {
				collect(x.mid, prefix.append(x.c), i + 1, pattern, queue);
				prefix.deleteCharAt(prefix.length() - 1);
			}
		}
		if (c == '.' || c > x.c)
			collect(x.right, prefix, i, pattern, queue);
	}

	@Override
	public void delete(String key) {

	}

}
