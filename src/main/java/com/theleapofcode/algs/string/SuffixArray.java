package com.theleapofcode.algs.string;

import java.util.Arrays;

/**
 * The {@code SuffixArray} class represents a suffix array of a string of length
 * <em>n</em>. It supports the <em>selecting</em> the <em>i</em>th smallest
 * suffix, getting the <em>index</em> of the <em>i</em>th smallest suffix,
 * computing the length of the <em>longest common prefix</em> between the
 * <em>i</em>th smallest suffix and the <em>i</em>-1st smallest suffix, and
 * determining the <em>rank</em> of a query string (which is the number of
 * suffixes strictly less than the query string).
 * <p>
 * This implementation uses a nested class {@code Suffix} to represent a suffix
 * of a string (using constant time and space) and {@code Arrays.sort()} to sort
 * the array of suffixes. The <em>index</em> and <em>length</em> operations
 * takes constant time in the worst case. The <em>lcp</em> operation takes time
 * proportional to the length of the longest common prefix. The <em>select</em>
 * operation takes time proportional to the length of the suffix and should be
 * used primarily for debugging.
 */
public class SuffixArray {
	private Suffix[] suffixes;

	/**
	 * Initializes a suffix array for the given {@code text} string.
	 * 
	 * @param text
	 *            the input string
	 */
	public SuffixArray(String text) {
		int n = text.length();
		this.suffixes = new Suffix[n];
		for (int i = 0; i < n; i++)
			suffixes[i] = new Suffix(text, i);
		Arrays.sort(suffixes);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(Suffix suffix : suffixes) {
			sb.append(suffix.toString()).append(System.lineSeparator());
		}
		return sb.toString();
	}

	private static class Suffix implements Comparable<Suffix> {
		private final String text;
		private final int index;

		private Suffix(String text, int index) {
			this.text = text;
			this.index = index;
		}

		private int length() {
			return text.length() - index;
		}

		private char charAt(int i) {
			return text.charAt(index + i);
		}

		public int compareTo(Suffix that) {
			if (this == that)
				return 0; // optimization
			int n = Math.min(this.length(), that.length());
			for (int i = 0; i < n; i++) {
				if (this.charAt(i) < that.charAt(i))
					return -1;
				if (this.charAt(i) > that.charAt(i))
					return +1;
			}
			return this.length() - that.length();
		}

		public String toString() {
			return text.substring(index);
		}
	}

	/**
	 * Returns the length of the input string.
	 * 
	 * @return the length of the input string
	 */
	public int length() {
		return suffixes.length;
	}

	/**
	 * Returns the index into the original string of the <em>i</em>th smallest
	 * suffix. That is, {@code text.substring(sa.index(i))} is the <em>i</em>th
	 * smallest suffix.
	 * 
	 * @param i
	 *            an integer between 0 and <em>n</em>-1
	 * @return the index into the original string of the <em>i</em>th smallest
	 *         suffix
	 * @throws java.lang.IndexOutOfBoundsException
	 *             unless {@code 0 <= i < n}
	 */
	public int index(int i) {
		if (i < 0 || i >= suffixes.length)
			throw new IndexOutOfBoundsException();
		return suffixes[i].index;
	}

	/**
	 * Returns the length of the longest common prefix of the <em>i</em>th
	 * smallest suffix and the <em>i</em>-1st smallest suffix.
	 * 
	 * @param i
	 *            an integer between 1 and <em>n</em>-1
	 * @return the length of the longest common prefix of the <em>i</em>th
	 *         smallest suffix and the <em>i</em>-1st smallest suffix.
	 * @throws java.lang.IndexOutOfBoundsException
	 *             unless {@code 1 <= i < n}
	 */
	public int lcp(int i) {
		if (i < 1 || i >= suffixes.length)
			throw new IndexOutOfBoundsException();
		return lcp(suffixes[i], suffixes[i - 1]);
	}

	// longest common prefix of s and t
	private static int lcp(Suffix s, Suffix t) {
		int n = Math.min(s.length(), t.length());
		for (int i = 0; i < n; i++) {
			if (s.charAt(i) != t.charAt(i))
				return i;
		}
		return n;
	}

	/**
	 * Returns the <em>i</em>th smallest suffix as a string.
	 * 
	 * @param i
	 *            the index
	 * @return the <em>i</em> smallest suffix as a string
	 * @throws java.lang.IndexOutOfBoundsException
	 *             unless {@code 0 <= i < n}
	 */
	public String select(int i) {
		if (i < 0 || i >= suffixes.length)
			throw new IndexOutOfBoundsException();
		return suffixes[i].toString();
	}

	/**
	 * Returns the number of suffixes strictly less than the {@code query}
	 * string. We note that {@code rank(select(i))} equals {@code i} for each
	 * {@code i} between 0 and <em>n</em>-1.
	 * 
	 * @param query
	 *            the query string
	 * @return the number of suffixes strictly less than {@code query}
	 */
	public int rank(String query) {
		int lo = 0, hi = suffixes.length - 1;
		while (lo <= hi) {
			int mid = lo + (hi - lo) / 2;
			int cmp = compare(query, suffixes[mid]);
			if (cmp < 0)
				hi = mid - 1;
			else if (cmp > 0)
				lo = mid + 1;
			else
				return mid;
		}
		return lo;
	}

	// compare query string to suffix
	private static int compare(String query, Suffix suffix) {
		int n = Math.min(query.length(), suffix.length());
		for (int i = 0; i < n; i++) {
			if (query.charAt(i) < suffix.charAt(i))
				return -1;
			if (query.charAt(i) > suffix.charAt(i))
				return +1;
		}
		return query.length() - suffix.length();
	}

}
