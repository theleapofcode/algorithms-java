package com.theleapofcode.algs.digraph;

import org.junit.Test;

public class TestBreadthFirstPaths {

	@Test
	public void testPathTo() {
		Digraph graph = new Digraph(7);

		graph.addEdge(0, 1);
		graph.addEdge(0, 2);
		graph.addEdge(0, 5);
		graph.addEdge(0, 6);
		graph.addEdge(6, 4);
		graph.addEdge(4, 3);
		graph.addEdge(4, 5);
		graph.addEdge(5, 3);

		BreadthFirstDirectedPaths bfp = new BreadthFirstDirectedPaths(graph, 0);

		Iterable<Integer> path = bfp.pathTo(3);
		path.forEach(System.out::println);
	}

}
