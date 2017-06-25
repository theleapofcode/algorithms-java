package com.theleapofcode.algs.sorting;

public class MergeSortBottomUp {

	private static <T extends Comparable<T>> void merge(T[] a, T[] aux, int lo, int mid, int hi) {

		// copy to aux[]
		for (int k = lo; k <= hi; k++) {
			aux[k] = a[k];
		}

		// merge back to a[]
		int i = lo, j = mid + 1;
		for (int k = lo; k <= hi; k++) {
			if (i > mid)
				a[k] = aux[j++]; // this copying is unneccessary
			else if (j > hi)
				a[k] = aux[i++];
			else if (aux[j].compareTo(aux[i]) < 0)
				a[k] = aux[j++];
			else
				a[k] = aux[i++];
		}

	}

	@SuppressWarnings("unchecked")
	public static <T extends Comparable<T>> void sort(T[] a) {
		int n = a.length;
		T[] aux = (T[]) new Comparable[n];
		for (int len = 1; len < n; len *= 2) {
			for (int lo = 0; lo < n - len; lo += len + len) {
				int mid = lo + len - 1;
				int hi = Math.min(lo + len + len - 1, n - 1);
				merge(a, aux, lo, mid, hi);
			}
		}
	}

}
