package com.theleapofcode.algs.symboltable;

import org.junit.Assert;
import org.junit.Test;

public class TestRedBlackBSTSymbolTable {

	OrderedSymbolTable<String, String> bst = new RedBlackBSTSymbolTable<>();

	@Test
	public void testPut() {
		bst.put("S", "S");
		bst.put("E", "E");
		bst.put("A", "A");
		bst.put("R", "R");
		bst.put("C", "C");
		bst.put("H", "H");
		bst.put("X", "X");
		bst.put("M", "M");
		bst.put("P", "P");

		Assert.assertEquals(
				"(M = M) [ BLACK - (E = E) [ BLACK - (C = C) [ RED - (A = A) [,],],  BLACK - (H = H) [,]],  BLACK - (R = R) [ BLACK - (P = P) [,],  BLACK - (X = X) [ RED - (S = S) [,],]]]",
				bst.toString());
		Assert.assertEquals(9, bst.size());
		Assert.assertTrue(((RedBlackBSTSymbolTable<String, String>) bst).isBalanced());
	}

	@Test
	public void testGet() {
		bst.put("S", "S");
		bst.put("E", "E");
		bst.put("A", "A");
		bst.put("R", "R");
		bst.put("C", "C");
		bst.put("H", "H");
		bst.put("X", "X");
		bst.put("M", "M");
		bst.put("P", "P");

		String result = bst.get("H");
		Assert.assertEquals("H", result);
	}

	@Test
	public void testDelete() {
		bst.put("S", "S");
		bst.put("E", "E");
		bst.put("A", "A");
		bst.put("R", "R");
		bst.put("C", "C");
		bst.put("H", "H");
		bst.put("X", "X");
		bst.put("M", "M");
		bst.put("P", "P");

		bst.delete("P");
		Assert.assertEquals(8, bst.size());
		Assert.assertTrue(((RedBlackBSTSymbolTable<String, String>) bst).isBalanced());
	}

}
