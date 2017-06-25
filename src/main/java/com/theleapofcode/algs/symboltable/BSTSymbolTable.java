package com.theleapofcode.algs.symboltable;

import java.util.NoSuchElementException;

import com.theleapofcode.algs.stacksandqueues.Queue;
import com.theleapofcode.algs.stacksandqueues.QueueArrayImpl;

public class BSTSymbolTable<K extends Comparable<K>, V> implements OrderedSymbolTable<K, V> {

	private class Node {
		private K key; // sorted by key
		private V value; // associated data
		private Node left, right; // left and right subtrees
		private int size; // number of nodes in subtree

		public Node(K key, V value, int size) {
			this.key = key;
			this.value = value;
			this.size = size;
		}
	}

	private Node root; // root of BST

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		return treeStringBuilder(sb, this.root).toString();
	}

	private StringBuilder treeStringBuilder(StringBuilder sb, Node node) {
		if (node != null) {
			sb.append("(").append(node.key).append(" = ").append(node.value).append(")").append(" [");
			if (node.left != null) {
				treeStringBuilder(sb, node.left);
			}
			sb.append(",");

			if (node.right != null) {
				sb.append(" ");
				treeStringBuilder(sb, node.right);
			}
			sb.append("]");
		}
		return sb;
	}

	public int rank(K key) {
		if (key == null)
			throw new IllegalArgumentException("argument to rank() is null");
		return rank(key, root);
	}

	// Number of keys in the subtree less than key.
	private int rank(K key, Node node) {
		if (node == null)
			return 0;
		int cmp = key.compareTo(node.key);
		if (cmp < 0) // Node key greater than key, then find rank of left
						// subtree
			return rank(key, node.left);
		else if (cmp > 0) // Node key less than key, then find that node + all
							// left nodes + check for rank of right nodes
			return 1 + size(node.left) + rank(key, node.right);
		else // Node key equal than key, then find size of left subtree
			return size(node.left);
	}

	public Node deleteMinimum(Node node) {
		if (node.left == null)
			return node.right;
		node.left = deleteMinimum(node.left);
		node.size = size(node.left) + size(node.right) + 1;
		return node;
	}

	public Node deleteMaximum(Node node) {
		if (node.right == null)
			return node.left;
		node.right = deleteMaximum(node.right);
		node.size = size(node.left) + size(node.right) + 1;
		return node;
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
		root = put(root, key, value);
	}

	// Put in subtree of a Node
	private Node put(Node node, K key, V value) {
		if (node == null)
			return new Node(key, value, 1);
		int cmp = key.compareTo(node.key);
		if (cmp < 0)
			node.left = put(node.left, key, value);
		else if (cmp > 0)
			node.right = put(node.right, key, value);
		else
			node.value = value;
		node.size = 1 + size(node.left) + size(node.right);
		return node;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.theleapofcode.algs.symboltable.SymbolTable#get(java.lang.Object)
	 */
	@Override
	public V get(K key) {
		return get(root, key);
	}

	private V get(Node node, K key) {
		if (key == null)
			throw new IllegalArgumentException("key is null");
		if (node == null)
			return null;
		int cmp = key.compareTo(node.key);
		if (cmp < 0)
			return get(node.left, key);
		else if (cmp > 0)
			return get(node.right, key);
		else
			return node.value;
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

		root = delete(root, key);
	}

	private Node delete(Node node, K key) {
		if (node == null)
			return null;

		int cmp = key.compareTo(node.key);
		if (cmp < 0)
			node.left = delete(node.left, key);
		else if (cmp > 0)
			node.right = delete(node.right, key);
		else {
			if (node.right == null)
				return node.left;
			if (node.left == null)
				return node.right;
			Node temp = node;
			node = minimum(temp.right);
			node.right = deleteMinimum(temp.right);
			node.left = temp.left;
		}
		node.size = size(node.left) + size(node.right) + 1;
		return node;
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
		return size(root);
	}

	private int size(Node node) {
		if (node == null)
			return 0;
		else
			return node.size;
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
	public Iterable<K> keys() {
		return keys(minimum(), maximum());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.theleapofcode.algs.symboltable.SymbolTable#keys(java.lang.Object,
	 * java.lang.Object)
	 */
	@Override
	public Iterable<K> keys(K lo, K hi) {
		if (lo == null)
			throw new IllegalArgumentException("lo key is null");
		if (hi == null)
			throw new IllegalArgumentException("high key is null");

		Queue<K> queue = new QueueArrayImpl<>();
		keys(root, queue, lo, hi);
		return queue;
	}

	private void keys(Node node, Queue<K> queue, K lo, K hi) {
		if (node == null)
			return;
		int cmplo = lo.compareTo(node.key);
		int cmphi = hi.compareTo(node.key);
		if (cmplo < 0) // Node key is less than lo, then check Keys in left
						// subtree
			keys(node.left, queue, lo, hi);
		if (cmplo <= 0 && cmphi >= 0) // Node key is in between lo and high,
										// then add to queue (In-order
										// traversal)
			queue.enque(node.key);
		if (cmphi > 0) // Node key is greater than hi, then check Keys in right
						// subtree
			keys(node.right, queue, lo, hi);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.theleapofcode.algs.symboltable.OrderedSymbolTable#minimum()
	 */
	@Override
	public K minimum() {
		if (isEmpty())
			throw new NoSuchElementException("Empty symbol table");
		return minimum(root).key;
	}

	private Node minimum(Node node) {
		if (node.left == null)
			return node;
		else
			return minimum(node.left);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.theleapofcode.algs.symboltable.OrderedSymbolTable#maximum()
	 */
	@Override
	public K maximum() {
		if (isEmpty())
			throw new NoSuchElementException("Empty symbol table");
		return maximum(root).key;
	}

	private Node maximum(Node node) {
		if (node.right == null)
			return node;
		else
			return maximum(node.right);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.theleapofcode.algs.symboltable.OrderedSymbolTable#floor(java.lang.
	 * Comparable)
	 */
	@Override
	public K floor(K key) {
		if (key == null)
			throw new IllegalArgumentException("key is null");

		Node node = floor(root, key);
		if (node == null)
			return null;
		else
			return node.key;
	}

	private Node floor(Node node, K key) {
		if (node == null)
			return null;
		int cmp = key.compareTo(node.key);
		if (cmp == 0)
			return node;
		if (cmp < 0)
			return floor(node.left, key);
		Node temp = floor(node.right, key);
		if (temp != null)
			return temp;
		else
			return node;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.theleapofcode.algs.symboltable.OrderedSymbolTable#ceiling(java.lang.
	 * Comparable)
	 */
	@Override
	public K ceiling(K key) {
		if (key == null)
			throw new IllegalArgumentException("key is null");

		Node node = ceiling(root, key);
		if (node == null)
			return null;
		else
			return node.key;
	}

	private Node ceiling(Node node, K key) {
		if (node == null)
			return null;
		int cmp = key.compareTo(node.key);
		if (cmp == 0)
			return node;
		if (cmp < 0) {
			Node temp = ceiling(node.left, key);
			if (temp != null)
				return temp;
			else
				return node;
		}
		return ceiling(node.right, key);
	}

	@Override
	public K select(int index) {
		if (index < 0 || index >= size()) {
			throw new IllegalArgumentException("Invalid Index");
		}

		Node node = select(root, index);
		return node.key;
	}

	// Return Node of rank index.
	private Node select(Node node, int index) {
		if (node == null)
			return null;
		int t = size(node.left);
		if (t > index)
			return select(node.left, index);
		else if (t < index)
			return select(node.right, index - t - 1);
		else
			return node;
	}

}
