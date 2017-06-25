package com.theleapofcode.algs.undirectedgraph;

import org.junit.Test;

public class TestDepthFirstPaths {

	@Test
	public void testPathTo() {
		UndirectedGraph graph = new UndirectedGraph(7);

		graph.addEdge(0, 1);
		graph.addEdge(0, 2);
		graph.addEdge(0, 5);
		graph.addEdge(0, 6);
		graph.addEdge(6, 4);
		graph.addEdge(4, 3);
		graph.addEdge(4, 5);
		graph.addEdge(5, 3);

		DepthFirstPaths dfp = new DepthFirstPaths(graph, 0);

		Iterable<Integer> path = dfp.pathTo(3);
		path.forEach(System.out::println);
	}

}
