package com.theleapofcode.algs.stacksandqueues;

public class TopM<T extends Comparable<T>> {

	private MinPriorityQueue<T> pq;
	private int m;

	public TopM(int m) {
		this.m = m;
		this.pq = new MinPriorityQueue<>(m);
	}

	public void add(T item) {
		pq.enque(item);

		if (pq.size() > m)
			pq.deque();
	}

	public Iterable<T> getTopItems() {
		return pq;
	}

}
