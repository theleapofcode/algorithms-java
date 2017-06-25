package com.theleapofcode.algs.symboltable;

public interface SymbolTable<K, V> {

	/**
	 * Inserts the specified key-value pair into the symbol table, overwriting
	 * the old value with the new value if the symbol table already contains the
	 * specified key. Deletes the specified key (and its associated value) from
	 * this symbol table if the specified value is {@code null}.
	 *
	 * @param key
	 *            the key
	 * @param val
	 *            the value
	 * @throws IllegalArgumentException
	 *             if {@code key} is {@code null}
	 */
	public void put(K key, V value);

	/**
	 * Returns the value associated with the given key in this symbol table.
	 *
	 * @param key
	 *            the key
	 * @return the value associated with the given key if the key is in the
	 *         symbol table and {@code null} if the key is not in the symbol
	 *         table
	 * @throws IllegalArgumentException
	 *             if {@code key} is {@code null}
	 */
	public V get(K key);

	/**
	 * Removes the specified key and associated value from this symbol table (if
	 * the key is in the symbol table).
	 *
	 * @param key
	 *            the key
	 * @throws IllegalArgumentException
	 *             if {@code key} is {@code null}
	 */
	public void delete(K key);

	/**
	 * Does this symbol table contain the given key?
	 *
	 * @param key
	 *            the key
	 * @return {@code true} if this symbol table contains {@code key} and
	 *         {@code false} otherwise
	 * @throws IllegalArgumentException
	 *             if {@code key} is {@code null}
	 */
	public boolean contains(K key);

	/**
	 * Returns the number of key-value pairs in this symbol table.
	 *
	 * @return the number of key-value pairs in this symbol table
	 */
	public int size();

	/**
	 * Returns true if this symbol table is empty.
	 *
	 * @return {@code true} if this symbol table is empty; {@code false}
	 *         otherwise
	 */
	public boolean isEmpty();

	/**
	 * Returns all keys in this symbol table as an {@code Iterable}. To iterate
	 * over all of the keys in the symbol table named {@code st}, use the
	 * foreach notation: {@code for (Key key : st.keys())}.
	 *
	 * @return all keys in this symbol table
	 */
	public Iterable<K> keys();

}
