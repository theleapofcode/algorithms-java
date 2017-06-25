package com.theleapofcode.algs.sorting;

import com.theleapofcode.algs.util.RandomUtil;

public class QuickSort3WayPartitioning {

	// O(N log N)
	public static <T extends Comparable<T>> void sort(T[] a) {
		RandomUtil.shuffle(a);
		sort(a, 0, a.length - 1);
	}

	private static <T extends Comparable<T>> void sort(T[] a, int lo, int hi) {
		if (hi <= lo)
			return;
		int lt = lo, gt = hi;
		T v = a[lo];
		int i = lo;
		while (i <= gt) {
			int cmp = a[i].compareTo(v);
			if (cmp < 0)
				exch(a, lt++, i++);
			else if (cmp > 0)
				exch(a, i, gt--);
			else
				i++;
		}

		// a[lo..lt-1] < v = a[lt..gt] < a[gt+1..hi].
		sort(a, lo, lt - 1);
		sort(a, gt + 1, hi);
	}

	// exchange a[i] and a[j]
	private static void exch(Object[] a, int i, int j) {
		Object swap = a[i];
		a[i] = a[j];
		a[j] = swap;
	}

}
