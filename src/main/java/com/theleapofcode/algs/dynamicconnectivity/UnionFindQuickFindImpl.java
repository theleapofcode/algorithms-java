package com.theleapofcode.algs.dynamicconnectivity;

import java.util.Arrays;

public class UnionFindQuickFindImpl implements UnionFind {

	private int[] id; // Component identifiers
	private int count; // number of connected components

	/**
	 * Initializes an empty union–find data structure with {@code n} nodes.
	 *
	 * @param n
	 *            the number of nodes
	 * @throws IllegalArgumentException
	 *             if {@code n < 0}
	 */
	public UnionFindQuickFindImpl(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("Length must be > 0");
		}

		this.id = new int[n];
		this.count = n; // Initially each node is in separate connected
						// component

		for (int i = 0; i < n; i++) { // O(N)
			id[i] = i;
		}
	}

	private void validate(int x) throws IndexOutOfBoundsException {
		if (x < 0 || x > id.length) {
			throw new IndexOutOfBoundsException("Index must be between 0 and " + (id.length - 1));
		}
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

		int xId = id[x];
		int yId = id[y];

		if (xId == yId)
			return;

		for (int i = 0; i < id.length; i++) { // O(N) for 1 union and O(N^2) for
												// N unions of N nodes
			if (id[i] == xId) {
				id[i] = yId;
			}
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

		return id[x] == id[y];
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.theleapofcode.algs.dynamicconnectivity.UnionFind#find(int)
	 */
	@Override
	public int find(int x) {
		validate(x);

		return id[x];
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
		return Arrays.toString(id);
	}

}
