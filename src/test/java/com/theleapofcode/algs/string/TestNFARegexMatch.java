package com.theleapofcode.algs.string;

import org.junit.Assert;
import org.junit.Test;

public class TestNFARegexMatch {

	@Test
	public void test() {
		String text = "CAAABD";
		String regex = "(.*(A*B).*)";
		NFARegexMatch nfa = new NFARegexMatch(regex);
		boolean match = nfa.recognizes(text);
		Assert.assertTrue(match);
	}

}
