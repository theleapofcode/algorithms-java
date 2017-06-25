package com.theleapofcode.algs.symboltable;

import java.util.NoSuchElementException;

import com.theleapofcode.algs.stacksandqueues.Queue;
import com.theleapofcode.algs.stacksandqueues.QueueArrayImpl;

public class RedBlackBSTSymbolTable<K extends Comparable<K>, V> implements OrderedSymbolTable<K, V> {

	private static final boolean RED = true;
	private static final boolean BLACK = false;

	private class Node {
		K key; // sorted by key
		V value; // associated data
		Node left, right; // left and right subtrees
		boolean color; // color of parent link
		int size; // number of nodes in subtree

		public Node(K key, V value, boolean color, int size) {
			this.key = key;
			this.value = value;
			this.color = color;
			this.size = size;
		}

		@Override
		public String toString() {
			return key.toString();
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
			if (node.color == RED)
				sb.append(" RED - (");
			else if (node.color == BLACK && node != root)
				sb.append(" BLACK - (");
			else
				sb.append("(");
			sb.append(node.key).append(" = ").append(node.value).append(")").append(" [");
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

	private boolean isRed(Node x) {
		if (x == null) // null links are BLACK
			return false;
		return x.color == RED;
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

	// make a left-leaning link lean to the right
	private Node rotateRight(Node h) {
		Node x = h.left;
		h.left = x.right;
		x.right = h;
		x.color = x.right.color;
		x.right.color = RED;
		x.size = h.size;
		h.size = size(h.left) + size(h.right) + 1;
		return x;
	}

	// make a right-leaning link lean to the left
	private Node rotateLeft(Node h) {
		Node x = h.right;
		h.right = x.left;
		x.left = h;
		x.color = x.left.color;
		x.left.color = RED;
		x.size = h.size;
		h.size = size(h.left) + size(h.right) + 1;
		return x;
	}

	// flip the colors of a node and its two children. h must have opposite
	// color of its two children
	private void flipColors(Node h) {
		h.color = !h.color;
		h.left.color = !h.left.color;
		h.right.color = !h.right.color;
	}

	// Assuming that h is red and both h.left and h.left.left
	// are black, make h.left or one of its children red.
	private Node moveRedLeft(Node h) {
		flipColors(h);
		if (isRed(h.right.left)) {
			h.right = rotateRight(h.right);
			h = rotateLeft(h);
			flipColors(h);
		}
		return h;
	}

	// Assuming that h is red and both h.right and h.right.left
	// are black, make h.right or one of its children red.
	private Node moveRedRight(Node h) {
		flipColors(h);
		if (isRed(h.left.left)) {
			h = rotateRight(h);
			flipColors(h);
		}
		return h;
	}

	// restore red-black tree invariant
	private Node balance(Node h) {
		if (isRed(h.right) && !isRed(h.left))
			h = rotateLeft(h);
		if (isRed(h.left) && isRed(h.left.left))
			h = rotateRight(h);
		if (isRed(h.left) && isRed(h.right))
			flipColors(h);

		h.size = size(h.left) + size(h.right) + 1;
		return h;
	}

	public void deleteMinimum() {
		if (isEmpty())
			throw new NoSuchElementException("BST underflow");

		// if both children of root are black, set root to red
		if (!isRed(root.left) && !isRed(root.right))
			root.color = RED;

		root = deleteMinimum(root);
		if (!isEmpty())
			root.color = BLACK;
	}

	private Node deleteMinimum(Node node) {
		if (node.left == null)
			return null;

		if (!isRed(node.left) && node.left != null && !isRed(node.left.left))
			node = moveRedLeft(node);

		node.left = deleteMinimum(node.left);
		return balance(node);
	}

	public void deleteMaximum() {
		if (isEmpty())
			throw new NoSuchElementException("BST underflow");

		// if both children of root are black, set root to red
		if (!isRed(root.left) && !isRed(root.right))
			root.color = RED;

		root = deleteMaximum(root);
		if (!isEmpty())
			root.color = BLACK;
	}

	private Node deleteMaximum(Node node) {
		if (isRed(node.left))
			node = rotateRight(node);

		if (node.right == null)
			return null;

		if (!isRed(node.right) && node.right != null && !isRed(node.right.left))
			node = moveRedRight(node);

		node.right = deleteMaximum(node.right);

		return balance(node);
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
		root.color = BLACK;
	}

	// Put in subtree of a Node
	private Node put(Node node, K key, V value) {
		if (node == null)
			return new Node(key, value, RED, 1);

		int cmp = key.compareTo(node.key);
		if (cmp < 0)
			node.left = put(node.left, key, value);
		else if (cmp > 0)
			node.right = put(node.right, key, value);
		else
			node.value = value;

		node = balance(node);

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

		// if both children of root are black, set root to red
		if (!isRed(root.left) && !isRed(root.right))
			root.color = RED;

		root = delete(root, key);
		if (!isEmpty())
			root.color = BLACK;
	}

	private Node delete(Node node, K key) {
		if (node == null)
			return null;

		if (key.compareTo(node.key) < 0) {
			if (!isRed(node.left) && node.left != null && !isRed(node.left.left))
				node = moveRedLeft(node);
			node.left = delete(node.left, key);
		} else {
			if (isRed(node.left))
				node = rotateRight(node);
			if (key.compareTo(node.key) == 0 && (node.right == null))
				return null;
			if (!isRed(node.right) && node.right != null && !isRed(node.right.left))
				node = moveRedRight(node);
			if (key.compareTo(node.key) == 0) {
				Node x = minimum(node.right);
				node.key = x.key;
				node.value = x.value;
				node.right = deleteMinimum(node.right);
			} else
				node.right = delete(node.right, key);
		}
		return balance(node);
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

	// do all paths from root to leaf have same number of black edges?
	public boolean isBalanced() {
		int black = 0; // number of black links on path from root to min
		Node x = root;
		while (x != null) {
			if (!isRed(x))
				black++;
			x = x.left;
		}
		return isBalanced(root, black);
	}

	// does every path from the root to a leaf have the given number of black
	// links?
	private boolean isBalanced(Node x, int black) {
		if (x == null)
			return black == 0;
		if (!isRed(x))
			black--;
		return isBalanced(x.left, black) && isBalanced(x.right, black);
	}

}
