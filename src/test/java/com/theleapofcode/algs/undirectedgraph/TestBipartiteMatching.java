package com.theleapofcode.algs.undirectedgraph;

import org.junit.Test;

public class TestBipartiteMatching {

	@Test
	public void test() {
		UndirectedGraph graph = new UndirectedGraph(10);

		graph.addEdge(0, 5);
		graph.addEdge(0, 6);
		graph.addEdge(0, 8);
		graph.addEdge(1, 5);
		graph.addEdge(1, 6);
		graph.addEdge(2, 5);
		graph.addEdge(2, 7);
		graph.addEdge(2, 8);
		graph.addEdge(3, 6);
		graph.addEdge(3, 9);
		graph.addEdge(4, 6);
		graph.addEdge(4, 9);

		BipartiteMatching matching = new BipartiteMatching(graph);

		System.out.printf("Number of edges in max matching        = %d\n", matching.size());
		System.out.printf("Number of vertices in min vertex cover = %d\n", matching.size());
		System.out.printf("Graph has a perfect matching           = %b\n", matching.isPerfect());
		System.out.println();

		System.out.print("Max matching: ");
		for (int v = 0; v < graph.V(); v++) {
			int w = matching.mate(v);
			if (matching.isMatched(v) && v < w) // print each edge only once
				System.out.print(v + "-" + w + " ");
		}
		System.out.println();

		System.out.print("Min vertex cover: ");
		for (int v = 0; v < graph.V(); v++)
			if (matching.inMinVertexCover(v))
				System.out.print(v + " ");
		System.out.println();
	}

}
