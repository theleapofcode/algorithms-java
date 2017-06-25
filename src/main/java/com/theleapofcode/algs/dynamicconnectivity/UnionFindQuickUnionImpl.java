package com.theleapofcode.algs.dynamicconnectivity;

import java.util.Arrays;

public class UnionFindQuickUnionImpl implements UnionFind {

	private int[] parent; // parent[i] is the parent of i
	private int count; // number of connected components

	/**
	 * Initializes an empty union–find data structure with {@code n} nodes.
	 *
	 * @param n
	 *            the number of nodes
	 * @throws IllegalArgumentException
	 *             if {@code n < 0}
	 */
	public UnionFindQuickUnionImpl(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("Length must be > 0");
		}

		this.parent = new int[n];
		this.count = n; // Initially each node is in separate connected
						// component

		for (int i = 0; i < n; i++) { // O(N)
			parent[i] = i;
		}
	}

	private void validate(int x) throws IndexOutOfBoundsException {
		if (x < 0 || x > parent.length) {
			throw new IndexOutOfBoundsException("Index must be between 0 and " + (parent.length - 1));
		}
	}

	// O(N) as tree may be long and skinny
	private int root(int x) {
		while (x != parent[x]) {
			x = parent[x];
		}
		return x;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.theleapofcode.algs.dynamicconnectivity.UnionFind#union(int, int)
	 */
	@Override
	public void union(int x, int y) throws IndexOutOfBoundsException {
		validate(x);
		validate(y);

		int xRoot = root(x); // O(N)
		int yRoot = root(y); // O(N)

		if (xRoot == yRoot)
			return;

		parent[xRoot] = yRoot;

		count--; // each connection reduces the connected components by 1
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.theleapofcode.algs.dynamicconnectivity.UnionFind#isConnected(int,
	 * int)
	 */
	@Override
	public boolean isConnected(int x, int y) throws IndexOutOfBoundsException {
		validate(x);
		validate(y);

		return root(x) == root(y); // O(N)
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.theleapofcode.algs.dynamicconnectivity.UnionFind#find(int)
	 */
	@Override
	public int find(int x) {
		validate(x);

		return root(x); // O(N)
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.theleapofcode.algs.dynamicconnectivity.UnionFind#count()
	 */
	@Override
	public int count() {
		return count;
	}

	@Override
	public String toString() {
		return Arrays.toString(parent);
	}

}
