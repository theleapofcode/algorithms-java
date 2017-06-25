package com.theleapofcode.algs.symboltable;

import org.junit.Assert;
import org.junit.Test;

public class TestSeparateChainingHashSymbolTable {

	SymbolTable<String, String> st = new SeparateChainingHashSymbolTable<>();

	@Test
	public void testPut() {
		st.put("IronMan", "Tony Stark");
		st.put("CapTainAmerica", "Steve Rogers");
		st.put("Thor", "Thor Odinson");
		st.put("Hulk", "Bruce banner");
		st.put("HawkEye", "Clint Barton");
		st.put("BlackWidow", "Natasha Romanoff");

		Assert.assertEquals(
				"{[(HawkEye = Clint Barton)(Hulk = Bruce banner)][][(CapTainAmerica = Steve Rogers)(IronMan = Tony Stark)][(BlackWidow = Natasha Romanoff)(Thor = Thor Odinson)]}",
				st.toString());
		Assert.assertEquals(6, st.size());
	}

	@Test
	public void testGet() {
		st.put("IronMan", "Tony Stark");
		st.put("CapTainAmerica", "Steve Rogers");
		st.put("Thor", "Thor Odinson");
		st.put("Hulk", "Bruce banner");
		st.put("HawkEye", "Clint Barton");
		st.put("BlackWidow", "Natasha Romanoff");

		String result = st.get("Hulk");
		Assert.assertEquals("Bruce banner", result);
	}

	@Test
	public void testDelete() {
		st.put("IronMan", "Tony Stark");
		st.put("CapTainAmerica", "Steve Rogers");
		st.put("Thor", "Thor Odinson");
		st.put("Hulk", "Bruce banner");
		st.put("HawkEye", "Clint Barton");
		st.put("BlackWidow", "Natasha Romanoff");

		st.delete("Hulk");
		Assert.assertEquals(5, st.size());

		st.delete("BlackWidow");
		Assert.assertEquals(4, st.size());
	}

}
