package com.theleapofcode.algs.stacksandqueues;

import org.junit.Assert;
import org.junit.Test;

public class TestMaxPriorityQueue {

	private Queue<String> queue = new MaxPriorityQueue<>();

	@Test
	public void testEnque() {
		queue.enque("IronMan");
		queue.enque("Thor");
		queue.enque("Hulk");

		Assert.assertEquals("Thor, IronMan, Hulk", queue.toString());
	}

	@Test
	public void testDeque() {
		queue.enque("IronMan");
		queue.enque("Thor");
		queue.enque("Hulk");

		String s = queue.deque();

		Assert.assertEquals("Thor", s);
		Assert.assertEquals(2, queue.size());
	}

	@Test
	public void testPeek() {
		queue.enque("IronMan");
		queue.enque("Thor");
		queue.enque("Hulk");

		String s = queue.peek();

		Assert.assertEquals("Thor", s);
		Assert.assertEquals(3, queue.size());
	}

	@Test
	public void testIterator() {
		queue.enque("IronMan");
		queue.enque("Thor");
		queue.enque("Hulk");

		for (String s : queue) {
			System.out.println(s);
		}
	}

}
