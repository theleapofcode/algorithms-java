package com.theleapofcode.algs.stacksandqueues;

import org.junit.Assert;
import org.junit.Test;

public class TestStackLinkedListImpl {

	private Stack<String> stack = new StackLinkedListImpl<>();

	@Test
	public void testPush() {
		stack.push("IronMan");
		stack.push("Thor");
		stack.push("Hulk");

		Assert.assertEquals("Hulk, Thor, IronMan", stack.toString());
	}

	@Test
	public void testPop() {
		stack.push("IronMan");
		stack.push("Thor");
		stack.push("Hulk");

		String s = stack.pop();

		Assert.assertEquals("Hulk", s);
		Assert.assertEquals(2, stack.size());
	}

	@Test
	public void testPeek() {
		stack.push("IronMan");
		stack.push("Thor");
		stack.push("Hulk");

		String s = stack.peek();

		Assert.assertEquals("Hulk", s);
		Assert.assertEquals(3, stack.size());
	}

	@Test
	public void testIterator() {
		stack.push("IronMan");
		stack.push("Thor");
		stack.push("Hulk");

		for (String s : stack) {
			System.out.println(s);
		}
	}

}
