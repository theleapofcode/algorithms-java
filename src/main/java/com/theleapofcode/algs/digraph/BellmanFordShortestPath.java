package com.theleapofcode.algs.digraph;

import com.theleapofcode.algs.stacksandqueues.Queue;
import com.theleapofcode.algs.stacksandqueues.QueueLinkedListImpl;
import com.theleapofcode.algs.stacksandqueues.Stack;
import com.theleapofcode.algs.stacksandqueues.StackLinkedListImpl;

/**
 * The {@code BellmanFordShortestPath} class represents a data type for solving
 * the single-source shortest paths problem in edge-weighted digraphs with no
 * negative cycles. The edge weights can be positive, negative, or zero. This
 * class finds either a shortest path from the source vertex <em>s</em> to every
 * other vertex or a negative cycle reachable from the source vertex.
 * <p>
 * This implementation uses the Bellman-Ford-Moore algorithm. The constructor
 * takes time proportional to <em>V</em> (<em>V</em> + <em>E</em>) in the worst
 * case, where <em>V</em> is the number of vertices and <em>E</em> is the number
 * of edges. Afterwards, the {@code distTo()}, {@code hasPathTo()}, and
 * {@code hasNegativeCycle()} methods take constant time; the {@code pathTo()}
 * and {@code negativeCycle()} method takes time proportional to the number of
 * edges returned.
 */
public class BellmanFordShortestPath {
	private double[] distTo; // distTo[v] = distance of shortest s->v path
	private DirectedEdge[] edgeTo; // edgeTo[v] = last edge on shortest s->v
									// path
	private boolean[] onQueue; // onQueue[v] = is v currently on the queue?
	private Queue<Integer> queue; // queue of vertices to relax
	private int cost; // number of calls to relax()
	private Iterable<DirectedEdge> cycle; // negative cycle (or null if no such
											// cycle)

	/**
	 * Computes a shortest paths tree from {@code s} to every other vertex in
	 * the edge-weighted digraph {@code G}.
	 * 
	 * @param G
	 *            the acyclic digraph
	 * @param s
	 *            the source vertex
	 * @throws IllegalArgumentException
	 *             unless {@code 0 <= s < V}
	 */
	public BellmanFordShortestPath(EdgeWeightedDigraph G, int s) {
		distTo = new double[G.V()];
		edgeTo = new DirectedEdge[G.V()];
		onQueue = new boolean[G.V()];
		for (int v = 0; v < G.V(); v++)
			distTo[v] = Double.POSITIVE_INFINITY;
		distTo[s] = 0.0;

		// Bellman-Ford algorithm
		queue = new QueueLinkedListImpl<>();
		queue.enque(s);
		onQueue[s] = true;
		while (!queue.isEmpty() && !hasNegativeCycle()) {
			int v = queue.deque();
			onQueue[v] = false;
			relax(G, v);
		}
	}

	// relax vertex v and put other endpoints on queue if changed
	private void relax(EdgeWeightedDigraph G, int v) {
		for (DirectedEdge e : G.adj(v)) {
			int w = e.to();
			if (distTo[w] > distTo[v] + e.weight()) {
				distTo[w] = distTo[v] + e.weight();
				edgeTo[w] = e;
				if (!onQueue[w]) {
					queue.enque(w);
					onQueue[w] = true;
				}
			}
			if (cost++ % G.V() == 0) {
				findNegativeCycle();
				if (hasNegativeCycle())
					return; // found a negative cycle
			}
		}
	}

	/**
	 * Is there a negative cycle reachable from the source vertex {@code s}?
	 * 
	 * @return {@code true} if there is a negative cycle reachable from the
	 *         source vertex {@code s}, and {@code false} otherwise
	 */
	public boolean hasNegativeCycle() {
		return cycle != null;
	}

	/**
	 * Returns a negative cycle reachable from the source vertex {@code s}, or
	 * {@code null} if there is no such cycle.
	 * 
	 * @return a negative cycle reachable from the soruce vertex {@code s} as an
	 *         iterable of edges, and {@code null} if there is no such cycle
	 */
	public Iterable<DirectedEdge> negativeCycle() {
		return cycle;
	}

	// by finding a cycle in predecessor graph
	private void findNegativeCycle() {
		int V = edgeTo.length;
		EdgeWeightedDigraph spt = new EdgeWeightedDigraph(V);
		for (int v = 0; v < V; v++)
			if (edgeTo[v] != null)
				spt.addEdge(edgeTo[v]);

		EdgeWeightedDirectedCycle finder = new EdgeWeightedDirectedCycle(spt);
		cycle = finder.cycle();
	}

	/**
	 * Returns the length of a shortest path from the source vertex {@code s} to
	 * vertex {@code v}.
	 * 
	 * @param v
	 *            the destination vertex
	 * @return the length of a shortest path from the source vertex {@code s} to
	 *         vertex {@code v}; {@code Double.POSITIVE_INFINITY} if no such
	 *         path
	 * @throws UnsupportedOperationException
	 *             if there is a negative cost cycle reachable from the source
	 *             vertex {@code s}
	 * @throws IllegalArgumentException
	 *             unless {@code 0 <= v < V}
	 */
	public double distTo(int v) {
		validateVertex(v);
		if (hasNegativeCycle())
			throw new UnsupportedOperationException("Negative cost cycle exists");
		return distTo[v];
	}

	/**
	 * Is there a path from the source {@code s} to vertex {@code v}?
	 * 
	 * @param v
	 *            the destination vertex
	 * @return {@code true} if there is a path from the source vertex {@code s}
	 *         to vertex {@code v}, and {@code false} otherwise
	 * @throws IllegalArgumentException
	 *             unless {@code 0 <= v < V}
	 */
	public boolean hasPathTo(int v) {
		validateVertex(v);
		return distTo[v] < Double.POSITIVE_INFINITY;
	}

	/**
	 * Returns a shortest path from the source {@code s} to vertex {@code v}.
	 * 
	 * @param v
	 *            the destination vertex
	 * @return a shortest path from the source {@code s} to vertex {@code v} as
	 *         an iterable of edges, and {@code null} if no such path
	 * @throws UnsupportedOperationException
	 *             if there is a negative cost cycle reachable from the source
	 *             vertex {@code s}
	 * @throws IllegalArgumentException
	 *             unless {@code 0 <= v < V}
	 */
	public Iterable<DirectedEdge> pathTo(int v) {
		validateVertex(v);
		if (hasNegativeCycle())
			throw new UnsupportedOperationException("Negative cost cycle exists");
		if (!hasPathTo(v))
			return null;
		Stack<DirectedEdge> path = new StackLinkedListImpl<>();
		for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {
			path.push(e);
		}
		return path;
	}

	// throw an IllegalArgumentException unless {@code 0 <= v < V}
	private void validateVertex(int v) {
		int V = distTo.length;
		if (v < 0 || v >= V)
			throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
	}

}
