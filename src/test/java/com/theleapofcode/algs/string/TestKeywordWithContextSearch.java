package com.theleapofcode.algs.string;

import java.util.List;

import org.junit.Test;

import com.theleapofcode.algs.util.FileUtil;

public class TestKeywordWithContextSearch {

	@Test
	public void test() {
		String text = FileUtil.getFileContent("tale.txt");
		List<String> result = KeywordWithContextSearch.search(text, 15, "majesty");
		System.out.println(result);
	}

}
