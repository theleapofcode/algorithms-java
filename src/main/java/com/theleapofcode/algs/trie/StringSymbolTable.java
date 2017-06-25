package com.theleapofcode.algs.trie;

import com.theleapofcode.algs.symboltable.SymbolTable;

public interface StringSymbolTable<V> extends SymbolTable<String, V> {

	/**
	 * Returns all of the keys in the set that start with {@code prefix}.
	 * 
	 * @param prefix
	 *            the prefix
	 * @return all of the keys in the set that start with {@code prefix}, as an
	 *         iterable
	 */
	public Iterable<String> keysWithPrefix(String prefix);

	/**
	 * Returns all of the keys in the symbol table that match {@code pattern},
	 * where . symbol is treated as a wildcard character.
	 * 
	 * @param pattern
	 *            the pattern
	 * @return all of the keys in the symbol table that match {@code pattern},
	 *         as an iterable, where . is treated as a wildcard character.
	 */
	public Iterable<String> keysThatMatch(String pattern);

	/**
	 * Returns the string in the symbol table that is the longest prefix of
	 * {@code query}, or {@code null}, if no such string.
	 * 
	 * @param query
	 *            the query string
	 * @return the string in the symbol table that is the longest prefix of
	 *         {@code query}, or {@code null} if no such string
	 * @throws NullPointerException
	 *             if {@code query} is {@code null}
	 */
	public String longestPrefixOf(String query);

}
