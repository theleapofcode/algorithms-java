package com.theleapofcode.algs.undirectedgraph;

import com.theleapofcode.algs.stacksandqueues.Stack;
import com.theleapofcode.algs.stacksandqueues.StackLinkedListImpl;

/**
 * The {@code Bipartite} class represents a data type for determining whether an
 * undirected graph is bipartite or whether it has an odd-length cycle. The
 * <em>isBipartite</em> operation determines whether the graph is bipartite. If
 * so, the <em>color</em> operation determines a bipartition; if not, the
 * <em>oddCycle</em> operation determines a cycle with an odd number of edges.
 * <p>
 * This implementation uses depth-first search. The constructor takes time
 * proportional to <em>V</em> + <em>E</em> (in the worst case), where <em>V</em>
 * is the number of vertices and <em>E</em> is the number of edges. Afterwards,
 * the <em>isBipartite</em> and <em>color</em> operations take constant time;
 * the <em>oddCycle</em> operation takes time proportional to the length of the
 * cycle. See {@link BipartiteX} for a nonrecursive version that uses
 * breadth-first search.
 */
public class Bipartite {
	private boolean isBipartite; // is the graph bipartite?
	private boolean[] color; // color[v] gives vertices on one side of
								// bipartition
	private boolean[] marked; // marked[v] = true if v has been visited in DFS
	private int[] edgeTo; // edgeTo[v] = last edge on path to v
	private Stack<Integer> cycle; // odd-length cycle

	/**
	 * Determines whether an undirected graph is bipartite and finds either a
	 * bipartition or an odd-length cycle.
	 *
	 * @param G
	 *            the graph
	 */
	public Bipartite(UndirectedGraph G) {
		isBipartite = true;
		color = new boolean[G.V()];
		marked = new boolean[G.V()];
		edgeTo = new int[G.V()];

		for (int v = 0; v < G.V(); v++) {
			if (!marked[v]) {
				dfs(G, v);
			}
		}
	}

	private void dfs(UndirectedGraph G, int v) {
		marked[v] = true;
		for (int w : G.adj(v)) {

			// short circuit if odd-length cycle found
			if (cycle != null)
				return;

			if (!marked[w]) { // found uncolored vertex, so recur
				edgeTo[w] = v;
				color[w] = !color[v];
				dfs(G, w);
			} else if (color[w] == color[v]) { // if v-w create an odd-length
												// cycle, find it
				isBipartite = false;
				cycle = new StackLinkedListImpl<>();
				for (int x = v; x != w; x = edgeTo[x]) {
					cycle.push(x);
				}
				cycle.push(w);
			}
		}
	}

	/**
	 * Returns true if the graph is bipartite.
	 *
	 * @return {@code true} if the graph is bipartite; {@code false} otherwise
	 */
	public boolean isBipartite() {
		return isBipartite;
	}

	/**
	 * Returns the side of the bipartite that vertex {@code v} is on.
	 *
	 * @param v
	 *            the vertex
	 * @return the side of the bipartition that vertex {@code v} is on; two
	 *         vertices are in the same side of the bipartition if and only if
	 *         they have the same color
	 * @throws IllegalArgumentException
	 *             unless {@code 0 <= v < V}
	 * @throws UnsupportedOperationException
	 *             if this method is called when the graph is not bipartite
	 */
	public boolean color(int v) {
		validateVertex(v);
		if (!isBipartite)
			throw new UnsupportedOperationException("graph is not bipartite");
		return color[v];
	}

	/**
	 * Returns an odd-length cycle if the graph is not bipartite, and
	 * {@code null} otherwise.
	 *
	 * @return an odd-length cycle if the graph is not bipartite (and hence has
	 *         an odd-length cycle), and {@code null} otherwise
	 */
	public Iterable<Integer> oddCycle() {
		return cycle;
	}

	private void validateVertex(int v) {
		int V = marked.length;
		if (v < 0 || v >= V)
			throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
	}

}
