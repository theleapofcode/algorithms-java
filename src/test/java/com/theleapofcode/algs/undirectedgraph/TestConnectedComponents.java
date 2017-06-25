package com.theleapofcode.algs.undirectedgraph;

import org.junit.Assert;
import org.junit.Test;

public class TestConnectedComponents {

	@Test
	public void testPathTo() {
		UndirectedGraph graph = new UndirectedGraph(13);

		graph.addEdge(0, 1);
		graph.addEdge(0, 2);
		graph.addEdge(0, 5);
		graph.addEdge(0, 6);
		graph.addEdge(6, 4);
		graph.addEdge(4, 3);
		graph.addEdge(4, 5);
		graph.addEdge(5, 3);

		graph.addEdge(7, 8);

		graph.addEdge(9, 10);
		graph.addEdge(9, 11);
		graph.addEdge(9, 12);
		graph.addEdge(11, 12);

		ConnectedComponents cc = new ConnectedComponents(graph);

		Assert.assertTrue(cc.connected(0, 3));

		Assert.assertFalse(cc.connected(0, 11));

		Assert.assertEquals(3, cc.count());
	}

}
