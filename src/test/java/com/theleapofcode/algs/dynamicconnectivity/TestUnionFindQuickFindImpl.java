package com.theleapofcode.algs.dynamicconnectivity;

import org.junit.Assert;
import org.junit.Test;

public class TestUnionFindQuickFindImpl {

	private UnionFind uf = new UnionFindQuickFindImpl(10);

	@Test
	public void testUnion() {
		uf.union(4, 3);
		uf.union(3, 8);
		uf.union(6, 5);
		uf.union(9, 4);
		Assert.assertEquals("[0, 1, 2, 8, 8, 5, 5, 7, 8, 8]", uf.toString());
	}

	@Test
	public void testIsConnected() {
		uf.union(4, 3);
		uf.union(3, 8);
		uf.union(6, 5);
		uf.union(9, 4);

		boolean isConnected = uf.isConnected(4, 8);
		Assert.assertTrue(isConnected);

		boolean isConnected2 = uf.isConnected(4, 5);
		Assert.assertFalse(isConnected2);
	}

	@Test
	public void testFind() {
		uf.union(4, 3);
		uf.union(3, 8);
		uf.union(6, 5);
		uf.union(9, 4);

		int result = uf.find(4);
		Assert.assertEquals(8, result);
	}

	@Test
	public void testCount() {
		uf.union(4, 3);
		uf.union(3, 8);
		uf.union(6, 5);
		uf.union(9, 4);

		int count = uf.count();
		Assert.assertEquals(6, count);
	}

}
