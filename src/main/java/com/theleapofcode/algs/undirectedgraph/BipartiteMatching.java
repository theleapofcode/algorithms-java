package com.theleapofcode.algs.undirectedgraph;

import com.theleapofcode.algs.stacksandqueues.Queue;
import com.theleapofcode.algs.stacksandqueues.QueueLinkedListImpl;

/**
 * The {@code BipartiteMatching} class represents a data type for computing a
 * <em>maximum (cardinality) matching</em> and a <em>minimum (cardinality)
 * vertex cover</em> in a bipartite graph. A <em>bipartite graph</em> in a graph
 * whose vertices can be partitioned into two disjoint sets such that every edge
 * has one endpoint in either set. A <em>matching</em> in a graph is a subset of
 * its edges with no common vertices. A <em>maximum matching</em> is a matching
 * with the maximum number of edges. A <em>perfect matching</em> is a matching
 * which matches all vertices in the graph. A <em>vertex cover</em> in a graph
 * is a subset of its vertices such that every edge is incident to at least one
 * vertex. A <em>minimum vertex cover</em> is a vertex cover with the minimum
 * number of vertices. By Konig's theorem, in any biparite graph, the maximum
 * number of edges in matching equals the minimum number of vertices in a vertex
 * cover. The maximum matching problem in <em>nonbipartite</em> graphs is also
 * important, but all known algorithms for this more general problem are
 * substantially more complicated.
 * <p>
 * This implementation uses the <em>alternating path algorithm</em>. It is
 * equivalent to reducing to the maximum flow problem and running the augmenting
 * path algorithm on the resulting flow network, but it does so with less
 * overhead. The order of growth of the running time in the worst case is
 * (<em>E</em> + <em>V</em>) <em>V</em>, where <em>E</em> is the number of edges
 * and <em>V</em> is the number of vertices in the graph. It uses extra space
 * (not including the graph) proportional to <em>V</em>.
 */
public class BipartiteMatching {
	private static final int UNMATCHED = -1;

	private final int V; // number of vertices in the graph
	private Bipartite bipartition; // the bipartition
	private int cardinality; // cardinality of current matching
	private int[] mate; // mate[v] = w if v-w is an edge in current matching
						// = -1 if v is not in current matching
	private boolean[] inMinVertexCover; // inMinVertexCover[v] = true iff v is
										// in min vertex cover
	private boolean[] marked; // marked[v] = true iff v is reachable via
								// alternating path
	private int[] edgeTo; // edgeTo[v] = w if v-w is last edge on path to w

	/**
	 * Determines a maximum matching (and a minimum vertex cover) in a bipartite
	 * graph.
	 *
	 * @param G
	 *            the bipartite graph
	 * @throws IllegalArgumentException
	 *             if {@code G} is not bipartite
	 */
	public BipartiteMatching(UndirectedGraph G) {
		bipartition = new Bipartite(G);
		if (!bipartition.isBipartite()) {
			throw new IllegalArgumentException("graph is not bipartite");
		}

		this.V = G.V();

		// initialize empty matching
		mate = new int[V];
		for (int v = 0; v < V; v++)
			mate[v] = UNMATCHED;

		// alternating path algorithm
		while (hasAugmentingPath(G)) {

			// find one endpoint t in alternating path
			int t = -1;
			for (int v = 0; v < G.V(); v++) {
				if (!isMatched(v) && edgeTo[v] != -1) {
					t = v;
					break;
				}
			}

			// update the matching according to alternating path in edgeTo[]
			// array
			for (int v = t; v != -1; v = edgeTo[edgeTo[v]]) {
				int w = edgeTo[v];
				mate[v] = w;
				mate[w] = v;
			}
			cardinality++;
		}

		// find min vertex cover from marked[] array
		inMinVertexCover = new boolean[V];
		for (int v = 0; v < V; v++) {
			if (bipartition.color(v) && !marked[v])
				inMinVertexCover[v] = true;
			if (!bipartition.color(v) && marked[v])
				inMinVertexCover[v] = true;
		}
	}

	/*
	 * is there an augmenting path? - if so, upon termination adj[] contains the
	 * level graph; - if not, upon termination marked[] specifies those vertices
	 * reachable via an alternating path from one side of the bipartition
	 *
	 * an alternating path is a path whose edges belong alternately to the
	 * matching and not to the matching
	 *
	 * an augmenting path is an alternating path that starts and ends at
	 * unmatched vertices
	 *
	 * this implementation finds a shortest augmenting path (fewest number of
	 * edges), though there is no particular advantage to do so here
	 */
	private boolean hasAugmentingPath(UndirectedGraph G) {
		marked = new boolean[V];

		edgeTo = new int[V];
		for (int v = 0; v < V; v++)
			edgeTo[v] = -1;

		// breadth-first search (starting from all unmatched vertices on one
		// side of bipartition)
		Queue<Integer> queue = new QueueLinkedListImpl<>();
		for (int v = 0; v < V; v++) {
			if (bipartition.color(v) && !isMatched(v)) {
				queue.enque(v);
				marked[v] = true;
			}
		}

		// run BFS, stopping as soon as an alternating path is found
		while (!queue.isEmpty()) {
			int v = queue.deque();
			for (int w : G.adj(v)) {

				// either (1) forward edge not in matching or (2) backward edge
				// in matching
				if (isResidualGraphEdge(v, w) && !marked[w]) {
					edgeTo[w] = v;
					marked[w] = true;
					if (!isMatched(w))
						return true;
					queue.enque(w);
				}
			}
		}

		return false;
	}

	// is the edge v-w a forward edge not in the matching or a reverse edge in
	// the matching?
	private boolean isResidualGraphEdge(int v, int w) {
		if ((mate[v] != w) && bipartition.color(v))
			return true;
		if ((mate[v] == w) && !bipartition.color(v))
			return true;
		return false;
	}

	/**
	 * Returns the vertex to which the specified vertex is matched in the
	 * maximum matching computed by the algorithm.
	 *
	 * @param v
	 *            the vertex
	 * @return the vertex to which vertex {@code v} is matched in the maximum
	 *         matching; {@code -1} if the vertex is not matched
	 * @throws IllegalArgumentException
	 *             unless {@code 0 <= v < V}
	 *
	 */
	public int mate(int v) {
		validate(v);
		return mate[v];
	}

	/**
	 * Returns true if the specified vertex is matched in the maximum matching
	 * computed by the algorithm.
	 *
	 * @param v
	 *            the vertex
	 * @return {@code true} if vertex {@code v} is matched in maximum matching;
	 *         {@code false} otherwise
	 * @throws IllegalArgumentException
	 *             unless {@code 0 <= v < V}
	 *
	 */
	public boolean isMatched(int v) {
		validate(v);
		return mate[v] != UNMATCHED;
	}

	/**
	 * Returns the number of edges in a maximum matching.
	 *
	 * @return the number of edges in a maximum matching
	 */
	public int size() {
		return cardinality;
	}

	/**
	 * Returns true if the graph contains a perfect matching. That is, the
	 * number of edges in a maximum matching is equal to one half of the number
	 * of vertices in the graph (so that every vertex is matched).
	 *
	 * @return {@code true} if the graph contains a perfect matching;
	 *         {@code false} otherwise
	 */
	public boolean isPerfect() {
		return cardinality * 2 == V;
	}

	/**
	 * Returns true if the specified vertex is in the minimum vertex cover
	 * computed by the algorithm.
	 *
	 * @param v
	 *            the vertex
	 * @return {@code true} if vertex {@code v} is in the minimum vertex cover;
	 *         {@code false} otherwise
	 * @throws IllegalArgumentException
	 *             unless {@code 0 <= v < V}
	 */
	public boolean inMinVertexCover(int v) {
		validate(v);
		return inMinVertexCover[v];
	}

	private void validate(int v) {
		if (v < 0 || v >= V)
			throw new IndexOutOfBoundsException("vertex " + v + " is not between 0 and " + (V - 1));
	}

}
