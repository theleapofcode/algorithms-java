package com.theleapofcode.algs.sorting;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

public class TestMSD {

	@Test
	public void testString() {
		String[] strs = new String[] { "asdf", "lkj", "poiu", "mnb", "zxcv" };
		LSD.sort(strs, 3);
		Assert.assertEquals("[asdf, lkj, mnb, poiu, zxcv]", Arrays.toString(strs));
	}

	@Test
	public void testInt() {
		int[] ints = new int[] { 5, 1, 3, 2, 4 };
		LSD.sort(ints);
		Assert.assertEquals("[1, 2, 3, 4, 5]", Arrays.toString(ints));
	}

}
