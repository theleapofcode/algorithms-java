package com.theleapofcode.algs.trie;

import org.junit.Assert;
import org.junit.Test;

public class TestRWayTrieSymbolTable {

	@Test
	public void testPut() {
		StringSymbolTable<Integer> st = new RWayTrieSymbolTable<>();
		String[] arr = new String[] { "she", "sells", "sea", "shells", "by", "the", "shore" };
		for (int i = 0; i < arr.length; i++) {
			st.put(arr[i], i);
		}

		Assert.assertEquals("[(by = 4)(sea = 2)(sells = 1)(she = 0)(shells = 3)(shore = 6)(the = 5)]", st.toString());
		Assert.assertEquals(7, st.size());
	}

	@Test
	public void testGet() {
		StringSymbolTable<Integer> st = new RWayTrieSymbolTable<>();
		String[] arr = new String[] { "she", "sells", "sea", "shells", "by", "the", "shore" };
		for (int i = 0; i < arr.length; i++) {
			st.put(arr[i], i);
		}

		int val = st.get("she");
		Assert.assertEquals(0, val);
	}

	@Test
	public void testDelete() {
		StringSymbolTable<Integer> st = new RWayTrieSymbolTable<>();
		String[] arr = new String[] { "she", "sells", "sea", "shells", "by", "the", "shore" };
		for (int i = 0; i < arr.length; i++) {
			st.put(arr[i], i);
		}

		st.delete("she");
		Assert.assertNull(st.get("she"));
		Assert.assertEquals(6, st.size());
	}

}
