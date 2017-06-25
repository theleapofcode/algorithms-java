package com.theleapofcode.algs.digraph;

/**
 * The {@code KosarajuSharirSCC} class represents a data type for determining
 * the strong components in a digraph. The <em>id</em> operation determines in
 * which strong component a given vertex lies; the <em>areStronglyConnected</em>
 * operation determines whether two vertices are in the same strong component;
 * and the <em>count</em> operation determines the number of strong components.
 * 
 * The <em>component identifier</em> of a component is one of the vertices in
 * the strong component: two vertices have the same component identifier if and
 * only if they are in the same strong component.
 * 
 * <p>
 * This implementation uses the Kosaraju-Sharir algorithm. The constructor takes
 * time proportional to <em>V</em> + <em>E</em> (in the worst case), where
 * <em>V</em> is the number of vertices and <em>E</em> is the number of edges.
 * Afterwards, the <em>id</em>, <em>count</em>, and
 * <em>areStronglyConnected</em> operations take constant time.
 */
public class KosarajuSharirStronglyConnectedComponents implements StronglyConnectedComponents {
	private boolean[] marked; // marked[v] = has vertex v been visited?
	private int[] id; // id[v] = id of strong component containing v
	private int count; // number of strongly-connected components

	/**
	 * Computes the strong components of the digraph {@code G}.
	 * 
	 * @param G
	 *            the digraph
	 */
	public KosarajuSharirStronglyConnectedComponents(Digraph G) {

		// compute reverse postorder of reverse graph
		DepthFirstOrder dfs = new DepthFirstOrder(G.reverse());

		// run DFS on G, using reverse postorder to guide calculation
		marked = new boolean[G.V()];
		id = new int[G.V()];
		for (int v : dfs.reversePost()) {
			if (!marked[v]) {
				dfs(G, v);
				count++;
			}
		}
	}

	// DFS on graph G
	private void dfs(Digraph G, int v) {
		marked[v] = true;
		id[v] = count;
		for (int w : G.adj(v)) {
			if (!marked[w])
				dfs(G, w);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.theleapofcode.algs.digraph.StronglyConnectedComponents#count()
	 */
	@Override
	public int count() {
		return count;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.theleapofcode.algs.digraph.StronglyConnectedComponents#
	 * stronglyConnected(int, int)
	 */
	@Override
	public boolean stronglyConnected(int v, int w) {
		validateVertex(v);
		validateVertex(w);
		return id[v] == id[w];
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.theleapofcode.algs.digraph.StronglyConnectedComponents#id(int)
	 */
	@Override
	public int id(int v) {
		validateVertex(v);
		return id[v];
	}

	// throw an IllegalArgumentException unless {@code 0 <= v < V}
	private void validateVertex(int v) {
		int V = marked.length;
		if (v < 0 || v >= V)
			throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
	}

}
