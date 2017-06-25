package com.theleapofcode.algs.string;

import org.junit.Test;

import com.theleapofcode.algs.util.FileUtil;

public class TestLongestRepeatedSubstring {

	@Test
	public void test() {
		String text = FileUtil.getFileContent("tale.txt");
		String result = LongestRepeatedSubstring.lrs(text);
		System.out.println(result);
	}

}
