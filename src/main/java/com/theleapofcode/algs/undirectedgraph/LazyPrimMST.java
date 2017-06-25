package com.theleapofcode.algs.undirectedgraph;

import com.theleapofcode.algs.stacksandqueues.MinPriorityQueue;
import com.theleapofcode.algs.stacksandqueues.Queue;
import com.theleapofcode.algs.stacksandqueues.QueueLinkedListImpl;

/**
 * The {@code LazyPrimMST} class represents a data type for computing a
 * <em>minimum spanning tree</em> in an edge-weighted graph. The edge weights
 * can be positive, zero, or negative and need not be distinct. If the graph is
 * not connected, it computes a <em>minimum spanning forest</em>, which is the
 * union of minimum spanning trees in each connected component. The
 * {@code weight()} method returns the weight of a minimum spanning tree and the
 * {@code edges()} method returns its edges.
 * <p>
 * This implementation uses a lazy version of <em>Prim's algorithm</em> with a
 * binary heap of edges. The constructor takes time proportional to <em>E</em>
 * log <em>E</em> and extra space (not including the graph) proportional to
 * <em>E</em>, where <em>V</em> is the number of vertices and <em>E</em> is the
 * number of edges. Afterwards, the {@code weight()} method takes constant time
 * and the {@code edges()} method takes time proportional to <em>V</em>.
 */
public class LazyPrimMST {
	private double weight; // total weight of MST
	private Queue<Edge> mst; // edges in the MST
	private boolean[] marked; // marked[v] = true if v on tree
	private MinPriorityQueue<Edge> pq; // edges with one endpoint in tree

	/**
	 * Compute a minimum spanning tree (or forest) of an edge-weighted graph.
	 * 
	 * @param G
	 *            the edge-weighted graph
	 */
	public LazyPrimMST(EdgeWeightedUndirectedGraph G) {
		mst = new QueueLinkedListImpl<>();
		pq = new MinPriorityQueue<>();
		marked = new boolean[G.V()];
		for (int v = 0; v < G.V(); v++) // run Prim from all vertices to
			if (!marked[v])
				prim(G, v); // get a minimum spanning forest
	}

	// run Prim's algorithm
	private void prim(EdgeWeightedUndirectedGraph G, int s) {
		scan(G, s);
		while (!pq.isEmpty()) { // better to stop when mst has V-1 edges
			Edge e = pq.deque(); // smallest edge on pq
			int v = e.either(), w = e.other(v); // two endpoints
			if (marked[v] && marked[w])
				continue; // lazy, both v and w already scanned
			mst.enque(e); // add e to MST
			weight += e.weight();
			if (!marked[v])
				scan(G, v); // v becomes part of tree
			if (!marked[w])
				scan(G, w); // w becomes part of tree
		}
	}

	// add all edges e incident to v onto pq if the other endpoint has not yet
	// been scanned
	private void scan(EdgeWeightedUndirectedGraph G, int v) {
		marked[v] = true;
		for (Edge e : G.adj(v))
			if (!marked[e.other(v)])
				pq.enque(e);
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
