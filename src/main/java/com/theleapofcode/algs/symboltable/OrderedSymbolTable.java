package com.theleapofcode.algs.symboltable;

import java.util.NoSuchElementException;

public interface OrderedSymbolTable<K extends Comparable<K>, V> extends SymbolTable<K, V> {

	/**
	 * Returns the smallest key in this symbol table.
	 *
	 * @return the smallest key in this symbol table
	 * @throws NoSuchElementException
	 *             if this symbol table is empty
	 */
	public K minimum();

	/**
	 * Returns the largest key in this symbol table.
	 *
	 * @return the largest key in this symbol table
	 * @throws NoSuchElementException
	 *             if this symbol table is empty
	 */
	public K maximum();

	/**
	 * Returns the largest key in this symbol table less than or equal to
	 * {@code key}.
	 *
	 * @param key
	 *            the key
	 * @return the largest key in this symbol table less than or equal to
	 *         {@code key}
	 * @throws NoSuchElementException
	 *             if there is no such key
	 * @throws IllegalArgumentException
	 *             if {@code key} is {@code null}
	 */
	public K floor(K key);

	/**
	 * Returns the smallest key in this symbol table greater than or equal to
	 * {@code key}.
	 *
	 * @param key
	 *            the key
	 * @return the smallest key in this symbol table greater than or equal to
	 *         {@code key}
	 * @throws NoSuchElementException
	 *             if there is no such key
	 * @throws IllegalArgumentException
	 *             if {@code key} is {@code null}
	 */
	public K ceiling(K key);

	/**
	 * Return the kth smallest key in this symbol table.
	 *
	 * @param k
	 *            the order statistic
	 * @return the {@code k}th smallest key in this symbol table
	 * @throws IllegalArgumentException
	 *             unless {@code k} is between 0 and <em>n</em>–1
	 */
	public K select(int index);

	/**
	 * Returns all keys in this symbol table in the given range, as an
	 * {@code Iterable}.
	 *
	 * @param lo
	 *            minimum endpoint
	 * @param hi
	 *            maximum endpoint
	 * @return all keys in this symbol table between {@code lo} (inclusive) and
	 *         {@code hi} (inclusive)
	 * @throws IllegalArgumentException
	 *             if either {@code lo} or {@code hi} is {@code null}
	 */
	public Iterable<K> keys(K lo, K hi);

}
