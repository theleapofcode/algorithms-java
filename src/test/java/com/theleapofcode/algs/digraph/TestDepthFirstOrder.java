package com.theleapofcode.algs.digraph;

import org.junit.Test;

public class TestDepthFirstOrder {

	@Test
	public void testTopologicalSort() {
		Digraph graph = new Digraph(7);

		graph.addEdge(0, 1);
		graph.addEdge(0, 2);
		graph.addEdge(0, 5);
		graph.addEdge(0, 6);
		graph.addEdge(6, 4);
		graph.addEdge(4, 3);
		graph.addEdge(4, 5);
		graph.addEdge(5, 3);

		DepthFirstOrder dfo = new DepthFirstOrder(graph);

		Iterable<Integer> topologicalSort = dfo.reversePost();
		topologicalSort.forEach(System.out::println);
	}

}
