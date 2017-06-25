package com.theleapofcode.algs.string;

import org.junit.Test;

public class TestSuffixArray {

	@Test
	public void test() {
		SuffixArray sa = new SuffixArray("ABRACADABRA");
		System.out.println(sa.toString());
	}

}
