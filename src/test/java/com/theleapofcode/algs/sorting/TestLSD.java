package com.theleapofcode.algs.sorting;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

public class TestLSD {

	@Test
	public void testString() {
		String[] strs = new String[] { "asd", "lkj", "poi", "mnb", "zxc" };
		LSD.sort(strs, 3);
		Assert.assertEquals("[asd, lkj, mnb, poi, zxc]", Arrays.toString(strs));
	}

	@Test
	public void testInt() {
		int[] ints = new int[] { 5, 1, 3, 2, 4 };
		LSD.sort(ints);
		Assert.assertEquals("[1, 2, 3, 4, 5]", Arrays.toString(ints));
	}

}
