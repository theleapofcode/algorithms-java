package com.theleapofcode.algs.string;

import com.theleapofcode.algs.digraph.Digraph;
import com.theleapofcode.algs.digraph.DirectedDepthFirstSearch;
import com.theleapofcode.algs.stacksandqueues.Bag;
import com.theleapofcode.algs.stacksandqueues.BagLinkedListImpl;
import com.theleapofcode.algs.stacksandqueues.Stack;
import com.theleapofcode.algs.stacksandqueues.StackLinkedListImpl;

/**
 * The {@code NFARegexMatch} class provides a data type for creating a
 * <em>nondeterministic finite state automaton</em> (NFA) from a regular
 * expression and testing whether a given string is matched by that regular
 * expression. It supports the following operations: <em>concatenation</em>,
 * <em>closure</em>, <em>binary or</em>, and <em>parentheses</em>. It does not
 * support <em>mutiway or</em>, <em>character classes</em>,
 * <em>metacharacters</em> (either in the text or pattern), <em>capturing
 * capabilities</em>, <em>greedy</em> or <em>relucantant</em> modifiers, and
 * other features in industrial-strength implementations such as
 * {@link java.util.regex.Pattern} and {@link java.util.regex.Matcher}.
 * <p>
 * This implementation builds the NFA using a digraph and a stack and simulates
 * the NFA using digraph search (see the textbook for details). The constructor
 * takes time proportional to <em>m</em>, where <em>m</em> is the number of
 * characters in the regular expression. The <em>recognizes</em> method takes
 * time proportional to <em>m n</em>, where <em>n</em> is the number of
 * characters in the text.
 */
public class NFARegexMatch {

	private Digraph graph; // digraph of epsilon transitions
	private String regexp; // regular expression
	private final int m; // number of characters in regular expression

	/**
	 * Initializes the NFA from the specified regular expression.
	 *
	 * @param regexp
	 *            the regular expression
	 */
	public NFARegexMatch(String regexp) {
		this.regexp = regexp;
		m = regexp.length();
		Stack<Integer> ops = new StackLinkedListImpl<>();
		graph = new Digraph(m + 1);
		for (int i = 0; i < m; i++) {
			int lp = i;
			if (regexp.charAt(i) == '(' || regexp.charAt(i) == '|')
				ops.push(i);
			else if (regexp.charAt(i) == ')') {
				int or = ops.pop();

				// 2-way or operator
				if (regexp.charAt(or) == '|') {
					lp = ops.pop();
					graph.addEdge(lp, or + 1);
					graph.addEdge(or, i);
				} else if (regexp.charAt(or) == '(') {
					lp = or;
				}
			}

			// closure operator (uses 1-character lookahead)
			if (i < m - 1 && regexp.charAt(i + 1) == '*') {
				graph.addEdge(lp, i + 1);
				graph.addEdge(i + 1, lp);
			}
			if (regexp.charAt(i) == '(' || regexp.charAt(i) == '*' || regexp.charAt(i) == ')')
				graph.addEdge(i, i + 1);
		}
		if (ops.size() != 0)
			throw new IllegalArgumentException("Invalid regular expression");
	}

	/**
	 * Returns true if the text is matched by the regular expression.
	 * 
	 * @param txt
	 *            the text
	 * @return {@code true} if the text is matched by the regular expression,
	 *         {@code false} otherwise
	 */
	public boolean recognizes(String txt) {
		DirectedDepthFirstSearch dfs = new DirectedDepthFirstSearch(graph, 0);
		Bag<Integer> pc = new BagLinkedListImpl<>();
		for (int v = 0; v < graph.V(); v++)
			if (dfs.marked(v))
				pc.add(v);

		// Compute possible NFA states for txt[i+1]
		for (int i = 0; i < txt.length(); i++) {
			if (txt.charAt(i) == '*' || txt.charAt(i) == '|' || txt.charAt(i) == '(' || txt.charAt(i) == ')')
				throw new IllegalArgumentException("text contains the metacharacter '" + txt.charAt(i) + "'");

			Bag<Integer> match = new BagLinkedListImpl<>();
			for (int v : pc) {
				if (v == m)
					continue;
				if ((regexp.charAt(v) == txt.charAt(i)) || regexp.charAt(v) == '.')
					match.add(v + 1);
			}
			dfs = new DirectedDepthFirstSearch(graph, match);
			pc = new BagLinkedListImpl<>();
			for (int v = 0; v < graph.V(); v++)
				if (dfs.marked(v))
					pc.add(v);

			// optimization if no states reachable
			if (pc.size() == 0)
				return false;
		}

		// check for accept state
		for (int v : pc)
			if (v == m)
				return true;
		return false;
	}

}
