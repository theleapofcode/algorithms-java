/******************************************************************************
 *  Compilation:  javac PrimMST.java
 *  Execution:    java PrimMST filename.txt
 *  Dependencies: EdgeWeightedGraph.java Edge.java Queue.java
 *                IndexMinPQ.java UF.java In.java StdOut.java
 *  Data files:   http://algs4.cs.princeton.edu/43mst/tinyEWG.txt
 *                http://algs4.cs.princeton.edu/43mst/mediumEWG.txt
 *                http://algs4.cs.princeton.edu/43mst/largeEWG.txt
 *
 *  Compute a minimum spanning forest using Prim's algorithm.
 *
 *  %  java PrimMST tinyEWG.txt 
 *  1-7 0.19000
 *  0-2 0.26000
 *  2-3 0.17000
 *  4-5 0.35000
 *  5-7 0.28000
 *  6-2 0.40000
 *  0-7 0.16000
 *  1.81000
 *
 *  % java PrimMST mediumEWG.txt
 *  1-72   0.06506
 *  2-86   0.05980
 *  3-67   0.09725
 *  4-55   0.06425
 *  5-102  0.03834
 *  6-129  0.05363
 *  7-157  0.00516
 *  ...
 *  10.46351
 *
 *  % java PrimMST largeEWG.txt
 *  ...
 *  647.66307
 *
 ******************************************************************************/

package com.theleapofcode.algs.undirectedgraph;

import com.theleapofcode.algs.stacksandqueues.IndexedMinPriorityQueue;
import com.theleapofcode.algs.stacksandqueues.Queue;
import com.theleapofcode.algs.stacksandqueues.QueueLinkedListImpl;

/**
 * The {@code EagerPrimMST} class represents a data type for computing a
 * <em>minimum spanning tree</em> in an edge-weighted graph. The edge weights
 * can be positive, zero, or negative and need not be distinct. If the graph is
 * not connected, it computes a <em>minimum spanning forest</em>, which is the
 * union of minimum spanning trees in each connected component. The
 * {@code weight()} method returns the weight of a minimum spanning tree and the
 * {@code edges()} method returns its edges.
 * <p>
 * This implementation uses <em>Prim's algorithm</em> with an indexed binary
 * heap. The constructor takes time proportional to <em>E</em> log <em>V</em>
 * and extra space (not including the graph) proportional to <em>V</em>, where
 * <em>V</em> is the number of vertices and <em>E</em> is the number of edges.
 * Afterwards, the {@code weight()} method takes constant time and the
 * {@code edges()} method takes time proportional to <em>V</em>.
 */
public class EagerPrimMST {
	private Edge[] edgeTo; // edgeTo[v] = shortest edge from tree vertex to
							// non-tree vertex
	private double[] distTo; // distTo[v] = weight of shortest such edge
	private boolean[] marked; // marked[v] = true if v on tree, false otherwise
	private IndexedMinPriorityQueue<Double> pq;

	/**
	 * Compute a minimum spanning tree (or forest) of an edge-weighted graph.
	 * 
	 * @param G
	 *            the edge-weighted graph
	 */
	public EagerPrimMST(EdgeWeightedUndirectedGraph G) {
		edgeTo = new Edge[G.V()];
		distTo = new double[G.V()];
		marked = new boolean[G.V()];
		pq = new IndexedMinPriorityQueue<>(G.V());
		for (int v = 0; v < G.V(); v++)
			distTo[v] = Double.POSITIVE_INFINITY;

		for (int v = 0; v < G.V(); v++) // run from each vertex to find
			if (!marked[v])
				prim(G, v); // minimum spanning forest
	}

	// run Prim's algorithm in graph G, starting from vertex s
	private void prim(EdgeWeightedUndirectedGraph G, int s) {
		distTo[s] = 0.0;
		pq.insert(s, distTo[s]);
		while (!pq.isEmpty()) {
			int v = pq.delMin();
			scan(G, v);
		}
	}

	// scan vertex v
	private void scan(EdgeWeightedUndirectedGraph G, int v) {
		marked[v] = true;
		for (Edge e : G.adj(v)) {
			int w = e.other(v);
			if (marked[w])
				continue; // v-w is obsolete edge
			if (e.weight() < distTo[w]) {
				distTo[w] = e.weight();
				edgeTo[w] = e;
				if (pq.contains(w))
					pq.decreaseKey(w, distTo[w]);
				else
					pq.insert(w, distTo[w]);
			}
		}
	}

	/**
	 * Returns the edges in a minimum spanning tree (or forest).
	 * 
	 * @return the edges in a minimum spanning tree (or forest) as an iterable
	 *         of edges
	 */
	public Iterable<Edge> edges() {
		Queue<Edge> mst = new QueueLinkedListImpl<>();
		for (int v = 0; v < edgeTo.length; v++) {
			Edge e = edgeTo[v];
			if (e != null) {
				mst.enque(e);
			}
		}
		return mst;
	}

	/**
	 * Returns the sum of the edge weights in a minimum spanning tree (or
	 * forest).
	 * 
	 * @return the sum of the edge weights in a minimum spanning tree (or
	 *         forest)
	 */
	public double weight() {
		double weight = 0.0;
		for (Edge e : edges())
			weight += e.weight();
		return weight;
	}

}
