package com.theleapofcode.algs.dynamicconnectivity;

import java.util.Arrays;

public class UnionFindWeightedQuickUnionWithPathCompressionImpl implements UnionFind {

	private int[] parent; // parent[i] is the parent of i
	private byte[] rank; // rank of tree at i (never more than 31)
	private int count; // number of connected components

	/**
	 * Initializes an empty union–find data structure with {@code n} nodes.
	 *
	 * @param n
	 *            the number of nodes
	 * @throws IllegalArgumentException
	 *             if {@code n < 0}
	 */
	public UnionFindWeightedQuickUnionWithPathCompressionImpl(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("Length must be > 0");
		}

		this.parent = new int[n];
		this.rank = new byte[n];
		this.count = n; // Initially each node is in separate connected
						// component

		for (int i = 0; i < n; i++) { // O(N)
			parent[i] = i;
			rank[i] = 0;
		}
	}

	private void validate(int x) throws IndexOutOfBoundsException {
		if (x < 0 || x > parent.length) {
			throw new IndexOutOfBoundsException("Index must be between 0 and " + (parent.length - 1));
		}
	}

	// O(log N) as tree is mostly short
	private int root(int x) {
		while (x != parent[x]) {
			parent[x] = parent[parent[x]];
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

		if (rank[xRoot] > rank[yRoot]) { // Make taller tree parent of shorter
											// tree
			parent[yRoot] = xRoot;
		}
		if (rank[xRoot] < rank[yRoot]) {
			parent[xRoot] = yRoot;
		} else {
			parent[xRoot] = yRoot;
			rank[yRoot]++;
		}

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
