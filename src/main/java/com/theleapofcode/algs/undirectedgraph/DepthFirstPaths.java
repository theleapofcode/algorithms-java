package com.theleapofcode.algs.undirectedgraph;

import com.theleapofcode.algs.stacksandqueues.Stack;
import com.theleapofcode.algs.stacksandqueues.StackLinkedListImpl;

/**
 * The {@code DepthFirstPaths} class represents a data type for finding paths
 * from a source vertex <em>s</em> to every other vertex in an undirected graph.
 * <p>
 * This implementation uses depth-first search. The constructor takes time
 * proportional to <em>V</em> + <em>E</em>, where <em>V</em> is the number of
 * vertices and <em>E</em> is the number of edges. It uses extra space (not
 * including the graph) proportional to <em>V</em>.
 */
public class DepthFirstPaths {
	private boolean[] marked; // marked[v] = is there an s-v path?
	private int[] edgeTo; // edgeTo[v] = last edge on s-v path
	private final int s; // source vertex

	/**
	 * Computes a path between {@code s} and every other vertex in graph
	 * {@code G}.
	 * 
	 * @param G
	 *            the graph
	 * @param s
	 *            the source vertex
	 * @throws IllegalArgumentException
	 *             unless {@code 0 <= s < V}
	 */
	public DepthFirstPaths(UndirectedGraph G, int s) {
		this.s = s;
		edgeTo = new int[G.V()];
		marked = new boolean[G.V()];
		validateVertex(s);
		dfs(G, s);
	}

	// depth first search from v
	private void dfs(UndirectedGraph G, int v) {
		marked[v] = true;
		for (int w : G.adj(v)) {
			if (!marked[w]) {
				edgeTo[w] = v;
				dfs(G, w);
			}
		}
	}

	/**
	 * Is there a path between the source vertex {@code s} and vertex {@code v}?
	 * 
	 * @param v
	 *            the vertex
	 * @return {@code true} if there is a path, {@code false} otherwise
	 * @throws IllegalArgumentException
	 *             unless {@code 0 <= v < V}
	 */
	public boolean hasPathTo(int v) {
		validateVertex(v);
		return marked[v];
	}

	/**
	 * Returns a path between the source vertex {@code s} and vertex {@code v},
	 * or {@code null} if no such path.
	 * 
	 * @param v
	 *            the vertex
	 * @return the sequence of vertices on a path between the source vertex
	 *         {@code s} and vertex {@code v}, as an Iterable
	 * @throws IllegalArgumentException
	 *             unless {@code 0 <= v < V}
	 */
	public Iterable<Integer> pathTo(int v) {
		validateVertex(v);
		if (!hasPathTo(v))
			return null;
		Stack<Integer> path = new StackLinkedListImpl<>();
		for (int x = v; x != s; x = edgeTo[x])
			path.push(x);
		path.push(s);
		return path;
	}

	private void validateVertex(int v) {
		int V = marked.length;
		if (v < 0 || v >= V)
			throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
	}

}
