package com.theleapofcode.algs.symboltable;

import java.util.LinkedList;

import com.theleapofcode.algs.geometry.Interval1D;

public class IntervalSymbolTable<T> {

	private Node root; // root of the BST

	// BST helper node data type
	private class Node {
		Interval1D interval; // key
		T value; // associated data
		Node left, right; // left and right subtrees
		int N; // size of subtree rooted at this node
		double max; // max endpoint in subtree rooted at this node

		Node(Interval1D interval, T value) {
			this.interval = interval;
			this.value = value;
			this.N = 1;
			this.max = interval.max();
		}
	}

	public boolean contains(Interval1D interval) {
		return (get(interval) != null);
	}

	public T get(Interval1D interval) {
		return get(root, interval);
	}

	private T get(Node x, Interval1D interval) {
		if (x == null)
			return null;
		int cmp = Interval1D.MIN_ENDPOINT_ORDER.compare(interval, x.interval);
		if (cmp < 0)
			return get(x.left, interval);
		else if (cmp > 0)
			return get(x.right, interval);
		else
			return x.value;
	}

	public void put(Interval1D interval, T T) {
		if (contains(interval)) {
			remove(interval);
		}
		root = randomizedInsert(root, interval, T);
	}

	// make new node the root with uniform probability
	private Node randomizedInsert(Node x, Interval1D interval, T T) {
		if (x == null)
			return new Node(interval, T);
		if (Math.random() * size(x) < 1.0)
			return rootInsert(x, interval, T);
		int cmp = Interval1D.MIN_ENDPOINT_ORDER.compare(interval, x.interval);
		if (cmp < 0)
			x.left = randomizedInsert(x.left, interval, T);
		else
			x.right = randomizedInsert(x.right, interval, T);
		fix(x);
		return x;
	}

	private Node rootInsert(Node x, Interval1D interval, T T) {
		if (x == null)
			return new Node(interval, T);
		int cmp = Interval1D.MIN_ENDPOINT_ORDER.compare(interval, x.interval);
		if (cmp < 0) {
			x.left = rootInsert(x.left, interval, T);
			x = rotR(x);
		} else {
			x.right = rootInsert(x.right, interval, T);
			x = rotL(x);
		}
		return x;
	}

	private Node joinLR(Node a, Node b) {
		if (a == null)
			return b;
		if (b == null)
			return a;

		if (Math.random() * (size(a) + size(b)) < size(a)) {
			a.right = joinLR(a.right, b);
			fix(a);
			return a;
		} else {
			b.left = joinLR(a, b.left);
			fix(b);
			return b;
		}
	}

	public T remove(Interval1D interval) {
		T T = get(interval);
		root = remove(root, interval);
		return T;
	}

	private Node remove(Node h, Interval1D interval) {
		if (h == null)
			return null;
		int cmp = Interval1D.MIN_ENDPOINT_ORDER.compare(interval, h.interval);
		if (cmp < 0)
			h.left = remove(h.left, interval);
		else if (cmp > 0)
			h.right = remove(h.right, interval);
		else
			h = joinLR(h.left, h.right);
		fix(h);
		return h;
	}

	// return an interval in data structure that intersects the given interval;
	// return null if no such interval exists
	// running time is proportional to log N
	public Interval1D search(Interval1D interval) {
		return search(root, interval);
	}

	// look in subtree rooted at x
	public Interval1D search(Node x, Interval1D interval) {
		while (x != null) {
			if (interval.intersects(x.interval))
				return x.interval;
			else if (x.left == null)
				x = x.right;
			else if (x.left.max < interval.min())
				x = x.right;
			else
				x = x.left;
		}
		return null;
	}

	// return *all* intervals in data structure that intersect the given
	// interval
	// running time is proportional to R log N, where R is the number of
	// intersections
	public Iterable<Interval1D> searchAll(Interval1D interval) {
		LinkedList<Interval1D> list = new LinkedList<Interval1D>();
		searchAll(root, interval, list);
		return list;
	}

	// look in subtree rooted at x
	public boolean searchAll(Node x, Interval1D interval, LinkedList<Interval1D> list) {
		boolean found1 = false;
		boolean found2 = false;
		boolean found3 = false;
		if (x == null)
			return false;
		if (interval.intersects(x.interval)) {
			list.add(x.interval);
			found1 = true;
		}
		if (x.left != null && x.left.max >= interval.min())
			found2 = searchAll(x.left, interval, list);
		if (found2 || x.left == null || x.left.max < interval.min())
			found3 = searchAll(x.right, interval, list);
		return found1 || found2 || found3;
	}

	// return number of nodes in subtree rooted at x
	public int size() {
		return size(root);
	}

	private int size(Node x) {
		if (x == null)
			return 0;
		else
			return x.N;
	}

	// height of tree (empty tree height = 0)
	public int height() {
		return height(root);
	}

	private int height(Node x) {
		if (x == null)
			return 0;
		return 1 + Math.max(height(x.left), height(x.right));
	}

	// fix auxilliar information (subtree count and max fields)
	private void fix(Node x) {
		if (x == null)
			return;
		x.N = 1 + size(x.left) + size(x.right);
		x.max = max3(x.interval.max(), max(x.left), max(x.right));
	}

	private double max(Node x) {
		if (x == null)
			return Integer.MIN_VALUE;
		return x.max;
	}

	// precondition: a is not null
	private double max3(double a, double b, double c) {
		return Math.max(a, Math.max(b, c));
	}

	// right rotate
	private Node rotR(Node h) {
		Node x = h.left;
		h.left = x.right;
		x.right = h;
		fix(h);
		fix(x);
		return x;
	}

	// left rotate
	private Node rotL(Node h) {
		Node x = h.right;
		h.right = x.left;
		x.left = h;
		fix(h);
		fix(x);
		return x;
	}

}