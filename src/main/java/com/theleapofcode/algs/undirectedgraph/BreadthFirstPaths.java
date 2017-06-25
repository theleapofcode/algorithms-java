package com.theleapofcode.algs.undirectedgraph;

import com.theleapofcode.algs.stacksandqueues.Queue;
import com.theleapofcode.algs.stacksandqueues.QueueLinkedListImpl;
import com.theleapofcode.algs.stacksandqueues.Stack;
import com.theleapofcode.algs.stacksandqueues.StackLinkedListImpl;

/**
 * The {@code BreadthFirstPaths} class represents a data type for finding
 * shortest paths (number of edges) from a source vertex <em>s</em> (or a set of
 * source vertices) to every other vertex in an undirected graph.
 * <p>
 * This implementation uses breadth-first search. The constructor takes time
 * proportional to <em>V</em> + <em>E</em>, where <em>V</em> is the number of
 * vertices and <em>E</em> is the number of edges. It uses extra space (not
 * including the graph) proportional to <em>V</em>.
 */
public class BreadthFirstPaths {
	private static final int INFINITY = Integer.MAX_VALUE;
	private boolean[] marked; // marked[v] = is there an s-v path
	private int[] edgeTo; // edgeTo[v] = previous edge on shortest s-v path
	private int[] distTo; // distTo[v] = number of edges shortest s-v path

	/**
	 * Computes the shortest path between the source vertex {@code s} and every
	 * other vertex in the graph {@code G}.
	 * 
	 * @param G
	 *            the graph
	 * @param s
	 *            the source vertex
	 * @throws IllegalArgumentException
	 *             unless {@code 0 <= s < V}
	 */
	public BreadthFirstPaths(UndirectedGraph G, int s) {
		marked = new boolean[G.V()];
		distTo = new int[G.V()];
		edgeTo = new int[G.V()];
		validateVertex(s);
		bfs(G, s);
	}

	/**
	 * Computes the shortest path between any one of the source vertices in
	 * {@code sources} and every other vertex in graph {@code G}.
	 * 
	 * @param G
	 *            the graph
	 * @param sources
	 *            the source vertices
	 * @throws IllegalArgumentException
	 *             unless {@code 0 <= s < V} for each vertex {@code s} in
	 *             {@code sources}
	 */
	public BreadthFirstPaths(UndirectedGraph G, Iterable<Integer> sources) {
		marked = new boolean[G.V()];
		distTo = new int[G.V()];
		edgeTo = new int[G.V()];
		for (int v = 0; v < G.V(); v++)
			distTo[v] = INFINITY;
		validateVertices(sources);
		bfs(G, sources);
	}

	// breadth-first search from a single source
	private void bfs(UndirectedGraph G, int s) {
		Queue<Integer> q = new QueueLinkedListImpl<>();
		for (int v = 0; v < G.V(); v++)
			distTo[v] = INFINITY;
		distTo[s] = 0;
		marked[s] = true;
		q.enque(s);

		while (!q.isEmpty()) {
			int v = q.deque();
			for (int w : G.adj(v)) {
				if (!marked[w]) {
					edgeTo[w] = v;
					distTo[w] = distTo[v] + 1;
					marked[w] = true;
					q.enque(w);
				}
			}
		}
	}

	// breadth-first search from multiple sources
	private void bfs(UndirectedGraph G, Iterable<Integer> sources) {
		Queue<Integer> q = new QueueLinkedListImpl<>();
		for (int s : sources) {
			marked[s] = true;
			distTo[s] = 0;
			q.enque(s);
		}
		while (!q.isEmpty()) {
			int v = q.deque();
			for (int w : G.adj(v)) {
				if (!marked[w]) {
					edgeTo[w] = v;
					distTo[w] = distTo[v] + 1;
					marked[w] = true;
					q.enque(w);
				}
			}
		}
	}

	/**
	 * Is there a path between the source vertex {@code s} (or sources) and
	 * vertex {@code v}?
	 * 
	 * @param v
	 *            the vertex
	 * @return {@code true} if there is a path, and {@code false} otherwise
	 * @throws IllegalArgumentException
	 *             unless {@code 0 <= v < V}
	 */
	public boolean hasPathTo(int v) {
		validateVertex(v);
		return marked[v];
	}

	/**
	 * Returns the number of edges in a shortest path between the source vertex
	 * {@code s} (or sources) and vertex {@code v}?
	 * 
	 * @param v
	 *            the vertex
	 * @return the number of edges in a shortest path
	 * @throws IllegalArgumentException
	 *             unless {@code 0 <= v < V}
	 */
	public int distTo(int v) {
		validateVertex(v);
		return distTo[v];
	}

	/**
	 * Returns a shortest path between the source vertex {@code s} (or sources)
	 * and {@code v}, or {@code null} if no such path.
	 * 
	 * @param v
	 *            the vertex
	 * @return the sequence of vertices on a shortest path, as an Iterable
	 * @throws IllegalArgumentException
	 *             unless {@code 0 <= v < V}
	 */
	public Iterable<Integer> pathTo(int v) {
		validateVertex(v);
		if (!hasPathTo(v))
			return null;
		Stack<Integer> path = new StackLinkedListImpl<>();
		int x;
		for (x = v; distTo[x] != 0; x = edgeTo[x])
			path.push(x);
		path.push(x);
		return path;
	}

	// throw an IllegalArgumentException unless {@code 0 <= v < V}
	private void validateVertex(int v) {
		int V = marked.length;
		if (v < 0 || v >= V)
			throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
	}

	// throw an IllegalArgumentException unless {@code 0 <= v < V}
	private void validateVertices(Iterable<Integer> vertices) {
		if (vertices == null) {
			throw new IllegalArgumentException("argument is null");
		}
		int V = marked.length;
		for (int v : vertices) {
			if (v < 0 || v >= V) {
				throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
			}
		}
	}

}
