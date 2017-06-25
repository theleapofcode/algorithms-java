package com.theleapofcode.algs.string;

import java.util.LinkedList;
import java.util.List;

public class KeywordWithContextSearch {

	public static List<String> search(String text, int context, String query) {
		List<String> result = new LinkedList<>();
		SuffixArray sa = new SuffixArray(text);
		int n = text.length();

		for (int i = sa.rank(query); i < n; i++) {
			int from1 = sa.index(i);
			int to1 = Math.min(n, from1 + query.length());
			if (!query.equals(text.substring(from1, to1)))
				break;
			int from2 = Math.max(0, sa.index(i) - context);
			int to2 = Math.min(n, sa.index(i) + context + query.length());
			result.add(text.substring(from2, to2));
		}

		return result;
	}

}
