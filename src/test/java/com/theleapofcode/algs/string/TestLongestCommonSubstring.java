package com.theleapofcode.algs.string;

import org.junit.Test;

import com.theleapofcode.algs.util.FileUtil;

public class TestLongestCommonSubstring {

	@Test
	public void test() {
		String tale = FileUtil.getFileContent("tale.txt");
		String moby = FileUtil.getFileContent("mobydick.txt");
		String result = LongestCommonSubstring.lcs(tale, moby);
		System.out.println(result);
	}

}
