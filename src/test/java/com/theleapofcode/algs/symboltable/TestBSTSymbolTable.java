package com.theleapofcode.algs.symboltable;

import org.junit.Assert;
import org.junit.Test;

public class TestBSTSymbolTable {

	OrderedSymbolTable<String, String> bst = new BSTSymbolTable<>();

	@Test
	public void testPut() {
		bst.put("IronMan", "Tony Stark");
		bst.put("CapTainAmerica", "Steve Rogers");
		bst.put("Thor", "Thor Odinson");
		bst.put("Hulk", "Bruce banner");
		bst.put("HawkEye", "Clint Barton");
		bst.put("BlackWidow", "Natasha Romanoff");

		Assert.assertEquals(
				"(IronMan = Tony Stark) [(CapTainAmerica = Steve Rogers) [(BlackWidow = Natasha Romanoff) [,], (Hulk = Bruce banner) [(HawkEye = Clint Barton) [,],]], (Thor = Thor Odinson) [,]]",
				bst.toString());
		Assert.assertEquals(6, bst.size());
	}

	@Test
	public void testGet() {
		bst.put("IronMan", "Tony Stark");
		bst.put("CapTainAmerica", "Steve Rogers");
		bst.put("Thor", "Thor Odinson");
		bst.put("Hulk", "Bruce banner");
		bst.put("HawkEye", "Clint Barton");
		bst.put("BlackWidow", "Natasha Romanoff");

		String result = bst.get("Hulk");
		Assert.assertEquals("Bruce banner", result);
	}

	@Test
	public void testDelete() {
		bst.put("IronMan", "Tony Stark");
		bst.put("CapTainAmerica", "Steve Rogers");
		bst.put("Thor", "Thor Odinson");
		bst.put("Hulk", "Bruce banner");
		bst.put("HawkEye", "Clint Barton");
		bst.put("BlackWidow", "Natasha Romanoff");

		bst.delete("Hulk");
		Assert.assertEquals(5, bst.size());
	}

}
