package com.theleapofcode.algs.undirectedgraph;

import com.theleapofcode.algs.dynamicconnectivity.UnionFind;
import com.theleapofcode.algs.dynamicconnectivity.UnionFindQuickUnionImpl;
import com.theleapofcode.algs.stacksandqueues.MinPriorityQueue;
import com.theleapofcode.algs.stacksandqueues.Queue;
import com.theleapofcode.algs.stacksandqueues.QueueLinkedListImpl;

/**
 * The {@code KruskalMST} class represents a data type for computing a
 * <em>minimum spanning tree</em> in an edge-weighted graph. The edge weights
 * can be positive, zero, or negative and need not be distinct. If the graph is
 * not connected, it computes a <em>minimum spanning forest</em>, which is the
 * union of minimum spanning trees in each connected component. The
 * {@code weight()} method returns the weight of a minimum spanning tree and the
 * {@code edges()} method returns its edges.
 * <p>
 * This implementation uses <em>Krusal's algorithm</em> and the union-find data
 * type. The constructor takes time proportional to <em>E</em> log <em>E</em>
 * and extra space (not including the graph) proportional to <em>V</em>, where
 * <em>V</em> is the number of vertices and <em>E</em> is the number of edges.
 * Afterwards, the {@code weight()} method takes constant time and the
 * {@code edges()} method takes time proportional to <em>V</em>.
 */
public class KruskalMST {
	private double weight; // weight of MST
	private Queue<Edge> mst = new QueueLinkedListImpl<>(); // edges in MST

	/**
	 * Compute a minimum spanning tree (or forest) of an edge-weighted graph.
	 * 
	 * @param G
	 *            the edge-weighted graph
	 */
	public KruskalMST(EdgeWeightedUndirectedGraph G) {
		// more efficient to build heap by passing array of edges
		MinPriorityQueue<Edge> pq = new MinPriorityQueue<>();
		for (Edge e : G.edges()) {
			pq.enque(e);
		}

		// run greedy algorithm
		UnionFind uf = new UnionFindQuickUnionImpl(G.V());
		while (!pq.isEmpty() && mst.size() < G.V() - 1) {
			Edge e = pq.deque();
			int v = e.either();
			int w = e.other(v);
			if (!uf.isConnected(v, w)) { // v-w does not create a cycle
				uf.union(v, w); // merge v and w components
				mst.enque(e); // add edge e to mst
				weight += e.weight();
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
		return weight;
	}

}
