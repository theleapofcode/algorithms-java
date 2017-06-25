package com.theleapofcode.algs.stacksandqueues;

import org.junit.Assert;
import org.junit.Test;

public class TestMinPriorityQueue {

	private Queue<String> queue = new MinPriorityQueue<>();

	@Test
	public void testEnque() {
		queue.enque("IronMan");
		queue.enque("Thor");
		queue.enque("Hulk");

		Assert.assertEquals("Hulk, IronMan, Thor", queue.toString());
	}

	@Test
	public void testDeque() {
		queue.enque("IronMan");
		queue.enque("Thor");
		queue.enque("Hulk");

		String s = queue.deque();

		Assert.assertEquals("Hulk", s);
		Assert.assertEquals(2, queue.size());
		
		queue.deque();
		queue.deque();
	}

	@Test
	public void testPeek() {
		queue.enque("IronMan");
		queue.enque("Thor");
		queue.enque("Hulk");

		String s = queue.peek();

		Assert.assertEquals("Hulk", s);
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
