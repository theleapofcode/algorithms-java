package com.theleapofcode.algs.sorting;

import org.junit.Assert;
import org.junit.Test;

public class TestQuickSelect {

	@Test
	public void testSelect() {
		Integer[] arr = { 30, 20, 10, 50, 40 };
		Integer item = QuickSelect.select(arr, 2);
		Assert.assertEquals(new Integer(30), item);
	}

}
