package com.theleapofcode.algs.trie;

import com.theleapofcode.algs.stacksandqueues.Queue;
import com.theleapofcode.algs.stacksandqueues.QueueLinkedListImpl;

/**
 * The {@code TrieST} class represents an symbol table of key-value pairs, with
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
 * This implementation uses a 256-way trie. The <em>put</em>, <em>contains</em>,
 * <em>delete</em>, and <em>longest prefix</em> operations take time
 * proportional to the length of the key (in the worst case). Construction takes
 * constant time. The <em>size</em>, and <em>is-empty</em> operations take
 * constant time. Construction takes constant time.
 * It can be used for spell checking (26-way trie)
 */
public class RWayTrieSymbolTable<V> implements StringSymbolTable<V> {
	private static final int R = 256; // extended ASCII

	private Node root; // root of trie
	private int n; // number of keys in trie

	// R-way trie node
	private static class Node {
		private Object val;
		private Node[] next = new Node[R];
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("[");
		for (String key : keys()) {
			sb.append("(").append(key).append(" = ").append(get(key)).append(")");
		}
		sb.append("]");

		return sb.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.theleapofcode.algs.symboltable.SymbolTable#get(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public V get(String key) {
		Node x = get(root, key, 0);
		if (x == null)
			return null;
		return (V) x.val;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.theleapofcode.algs.symboltable.SymbolTable#contains(java.lang.Object)
	 */
	@Override
	public boolean contains(String key) {
		return get(key) != null;
	}

	private Node get(Node x, String key, int d) {
		if (x == null)
			return null;
		if (d == key.length())
			return x;
		char c = key.charAt(d);
		return get(x.next[c], key, d + 1);
	}

	/**
	 * @param key
	 * @param val
	 */
	@Override
	public void put(String key, V val) {
		if (val == null)
			delete(key);
		else
			root = put(root, key, val, 0);
	}

	private Node put(Node x, String key, V val, int d) {
		if (x == null)
			x = new Node();
		if (d == key.length()) {
			if (x.val == null)
				n++;
			x.val = val;
			return x;
		}
		char c = key.charAt(d);
		x.next[c] = put(x.next[c], key, val, d + 1);
		return x;
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
		return size() == 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.theleapofcode.algs.symboltable.SymbolTable#keys()
	 */
	@Override
	public Iterable<String> keys() {
		return keysWithPrefix("");
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
		Queue<String> results = new QueueLinkedListImpl<>();
		Node x = get(root, prefix, 0);
		collect(x, new StringBuilder(prefix), results);
		return results;
	}

	private void collect(Node x, StringBuilder prefix, Queue<String> results) {
		if (x == null)
			return;
		if (x.val != null)
			results.enque(prefix.toString());
		for (char c = 0; c < R; c++) {
			prefix.append(c);
			collect(x.next[c], prefix, results);
			prefix.deleteCharAt(prefix.length() - 1);
		}
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
		Queue<String> results = new QueueLinkedListImpl<>();
		collect(root, new StringBuilder(), pattern, results);
		return results;
	}

	private void collect(Node x, StringBuilder prefix, String pattern, Queue<String> results) {
		if (x == null)
			return;
		int d = prefix.length();
		if (d == pattern.length() && x.val != null)
			results.enque(prefix.toString());
		if (d == pattern.length())
			return;
		char c = pattern.charAt(d);
		if (c == '.') {
			for (char ch = 0; ch < R; ch++) {
				prefix.append(ch);
				collect(x.next[ch], prefix, pattern, results);
				prefix.deleteCharAt(prefix.length() - 1);
			}
		} else {
			prefix.append(c);
			collect(x.next[c], prefix, pattern, results);
			prefix.deleteCharAt(prefix.length() - 1);
		}
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
		int length = longestPrefixOf(root, query, 0, -1);
		if (length == -1)
			return null;
		else
			return query.substring(0, length);
	}

	// returns the length of the longest string key in the subtrie
	// rooted at x that is a prefix of the query string,
	// assuming the first d character match and we have already
	// found a prefix match of given length (-1 if no such match)
	private int longestPrefixOf(Node x, String query, int d, int length) {
		if (x == null)
			return length;
		if (x.val != null)
			length = d;
		if (d == query.length())
			return length;
		char c = query.charAt(d);
		return longestPrefixOf(x.next[c], query, d + 1, length);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.theleapofcode.algs.symboltable.SymbolTable#delete(java.lang.Object)
	 */
	@Override
	public void delete(String key) {
		root = delete(root, key, 0);
	}

	private Node delete(Node x, String key, int d) {
		if (x == null)
			return null;
		if (d == key.length()) {
			if (x.val != null)
				n--;
			x.val = null;
		} else {
			char c = key.charAt(d);
			x.next[c] = delete(x.next[c], key, d + 1);
		}

		// remove subtrie rooted at x if it is completely empty
		if (x.val != null)
			return x;
		for (int c = 0; c < R; c++)
			if (x.next[c] != null)
				return x;
		return null;
	}

}
