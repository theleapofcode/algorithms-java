package com.theleapofcode.algs.undirectedgraph;

import com.theleapofcode.algs.stacksandqueues.Bag;
import com.theleapofcode.algs.stacksandqueues.BagLinkedListImpl;
import com.theleapofcode.algs.stacksandqueues.Stack;
import com.theleapofcode.algs.stacksandqueues.StackLinkedListImpl;

public class UndirectedGraph {

	private static final String NEWLINE = System.getProperty("line.separator");

	private final int V;
	private int E;
	private Bag<Integer>[] adj;

	/**
	 * Initializes an empty graph with {@code V} vertices and 0 edges. param V
	 * the number of vertices
	 *
	 * @param V
	 *            number of vertices
	 * @throws IllegalArgumentException
	 *             if {@code V < 0}
	 */
	@SuppressWarnings("unchecked")
	public UndirectedGraph(int V) {
		if (V < 0)
			throw new IllegalArgumentException("Number of vertices must be nonnegative");
		this.V = V;
		this.E = 0;
		adj = (Bag<Integer>[]) new Bag[V];
		for (int v = 0; v < V; v++) {
			adj[v] = new BagLinkedListImpl<>();
		}
	}

	/**
	 * Initializes a new graph that is a deep copy of {@code G}.
	 *
	 * @param G
	 *            the graph to copy
	 */
	public UndirectedGraph(UndirectedGraph G) {
		this(G.V());
		this.E = G.E();
		for (int v = 0; v < G.V(); v++) {
			// reverse so that adjacency list is in same order as original
			Stack<Integer> reverse = new StackLinkedListImpl<>();
			for (int w : G.adj[v]) {
				reverse.push(w);
			}
			for (int w : reverse) {
				adj[v].add(w);
			}
		}
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(V + " vertices, " + E + " edges " + NEWLINE);
		for (int v = 0; v < V; v++) {
			s.append(v + ": ");
			for (int w : adj[v]) {
				s.append(w + " ");
			}
			s.append(NEWLINE);
		}
		return s.toString();
	}

	// throw an IllegalArgumentException unless {@code 0 <= v < V}
	private void validateVertex(int v) {
		if (v < 0 || v >= V)
			throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
	}

	/**
	 * Returns the number of vertices in this graph.
	 *
	 * @return the number of vertices in this graph
	 */
	public int V() {
		return V;
	}

	/**
	 * Returns the number of edges in this graph.
	 *
	 * @return the number of edges in this graph
	 */
	public int E() {
		return E;
	}

	/**
	 * Adds the undirected edge v-w to this graph.
	 *
	 * @param v
	 *            one vertex in the edge
	 * @param w
	 *            the other vertex in the edge
	 * @throws IllegalArgumentException
	 *             unless both {@code 0 <= v < V} and {@code 0 <= w < V}
	 */
	public void addEdge(int v, int w) {
		validateVertex(v);
		validateVertex(w);
		E++;
		adj[v].add(w);
		adj[w].add(v);
	}

	/**
	 * Returns the vertices adjacent to vertex {@code v}.
	 *
	 * @param v
	 *            the vertex
	 * @return the vertices adjacent to vertex {@code v}, as an iterable
	 * @throws IllegalArgumentException
	 *             unless {@code 0 <= v < V}
	 */
	public Iterable<Integer> adj(int v) {
		validateVertex(v);
		return adj[v];
	}

	/**
	 * Returns the degree of vertex {@code v}.
	 *
	 * @param v
	 *            the vertex
	 * @return the degree of vertex {@code v}
	 * @throws IllegalArgumentException
	 *             unless {@code 0 <= v < V}
	 */
	public int degree(int v) {
		validateVertex(v);
		return adj[v].size();
	}

}
