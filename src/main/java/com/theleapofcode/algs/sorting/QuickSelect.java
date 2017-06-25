package com.theleapofcode.algs.sorting;

import com.theleapofcode.algs.util.RandomUtil;

public class QuickSelect {

	private static <T extends Comparable<T>> int partition(T[] a, int lo, int hi) {
		int i = lo;
		int j = hi + 1;
		T v = a[lo];
		while (true) {

			// find item on lo to swap
			while (less(a[++i], v))
				if (i == hi)
					break;

			// find item on hi to swap
			while (less(v, a[--j]))
				if (j == lo)
					break; // redundant since a[lo] acts as sentinel

			// check if pointers cross
			if (i >= j)
				break;

			exch(a, i, j);
		}

		// put partitioning item v at a[j]
		exch(a, lo, j);

		// now, a[lo .. j-1] <= a[j] <= a[j+1 .. hi]
		return j;
	}

	// is v < w ?
	private static <T extends Comparable<T>> boolean less(T v, T w) {
		return v.compareTo(w) < 0;
	}

	// exchange a[i] and a[j]
	private static void exch(Object[] a, int i, int j) {
		Object swap = a[i];
		a[i] = a[j];
		a[j] = swap;
	}

	// Get kth smallest element
	public static <T extends Comparable<T>> T select(T[] a, int k) {
		if (k < 0 || k >= a.length) {
			throw new IndexOutOfBoundsException("Selected element out of bounds");
		}
		RandomUtil.shuffle(a); // Shuffle to eliminate worst case
		int lo = 0, hi = a.length - 1;
		while (hi > lo) {
			int i = partition(a, lo, hi);
			if (i > k)
				hi = i - 1;
			else if (i < k)
				lo = i + 1;
			else
				return a[i];
		}
		return a[lo];
	}

}
