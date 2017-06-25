package com.theleapofcode.algs.sorting;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

public class TestMergeSortBottomUp {

	@Test
	public void testSortAlreadySorted() {
		Integer[] arr = { 10, 20, 30, 40, 50 };
		MergeSortBottomUp.sort(arr);
		Assert.assertEquals("[10, 20, 30, 40, 50]", Arrays.toString(arr));
	}

	@Test
	public void testSortReverseSorted() {
		Integer[] arr = { 50, 40, 30, 20, 10 };
		MergeSortBottomUp.sort(arr);
		Assert.assertEquals("[10, 20, 30, 40, 50]", Arrays.toString(arr));
	}

	@Test
	public void testSortRandomCase() {
		Integer[] arr = { 30, 10, 40, 20, 50 };
		MergeSortBottomUp.sort(arr);
		Assert.assertEquals("[10, 20, 30, 40, 50]", Arrays.toString(arr));
	}
	
}
