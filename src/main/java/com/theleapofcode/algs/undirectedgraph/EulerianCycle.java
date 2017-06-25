package com.theleapofcode.algs.undirectedgraph;

import com.theleapofcode.algs.stacksandqueues.Queue;
import com.theleapofcode.algs.stacksandqueues.QueueLinkedListImpl;
import com.theleapofcode.algs.stacksandqueues.Stack;
import com.theleapofcode.algs.stacksandqueues.StackLinkedListImpl;

/**
 * The {@code EulerianCycle} class represents a data type for finding an
 * Eulerian cycle or path in a graph. An <em>Eulerian cycle</em> is a cycle (not
 * necessarily simple) that uses every edge in the graph exactly once.
 * <p>
 * This implementation uses a nonrecursive depth-first search. The constructor
 * runs in O(<Em>E</em> + <em>V</em>) time, and uses O(<em>E</em> + <em>V</em>)
 * extra space, where <em>E</em> is the number of edges and <em>V</em> the
 * number of vertices All other methods take O(1) time.
 */
public class EulerianCycle {
	private Stack<Integer> cycle = new StackLinkedListImpl<>(); // Eulerian
																// cycle; null
	// if no such cycle

	// an undirected edge, with a field to indicate whether the edge has already
	// been used
	private static class Edge {
		private final int v;
		private final int w;
		private boolean isUsed;

		public Edge(int v, int w) {
			this.v = v;
			this.w = w;
			isUsed = false;
		}

		// returns the other vertex of the edge
		public int other(int vertex) {
			if (vertex == v)
				return w;
			else if (vertex == w)
				return v;
			else
				throw new IllegalArgumentException("Illegal endpoint");
		}
	}

	/**
	 * Computes an Eulerian cycle in the specified graph, if one exists.
	 * 
	 * @param G
	 *            the graph
	 */
	@SuppressWarnings("unchecked")
	public EulerianCycle(UndirectedGraph G) {

		// must have at least one edge
		if (G.E() == 0)
			return;

		// necessary condition: all vertices have even degree
		// (this test is needed or it might find an Eulerian path instead of
		// cycle)
		for (int v = 0; v < G.V(); v++)
			if (G.degree(v) % 2 != 0)
				return;

		// create local view of adjacency lists, to iterate one vertex at a time
		// the helper Edge data type is used to avoid exploring both copies of
		// an edge v-w
		Queue<Edge>[] adj = (QueueLinkedListImpl<Edge>[]) new QueueLinkedListImpl[G.V()];
		for (int v = 0; v < G.V(); v++)
			adj[v] = new QueueLinkedListImpl<>();

		for (int v = 0; v < G.V(); v++) {
			int selfLoops = 0;
			for (int w : G.adj(v)) {
				// careful with self loops
				if (v == w) {
					if (selfLoops % 2 == 0) {
						Edge e = new Edge(v, w);
						adj[v].enque(e);
						adj[w].enque(e);
					}
					selfLoops++;
				} else if (v < w) {
					Edge e = new Edge(v, w);
					adj[v].enque(e);
					adj[w].enque(e);
				}
			}
		}

		// initialize stack with any non-isolated vertex
		int s = nonIsolatedVertex(G);
		Stack<Integer> stack = new StackLinkedListImpl<>();
		stack.push(s);

		// greedily search through edges in iterative DFS style
		cycle = new StackLinkedListImpl<>();
		while (!stack.isEmpty()) {
			int v = stack.pop();
			while (!adj[v].isEmpty()) {
				Edge edge = adj[v].deque();
				if (edge.isUsed)
					continue;
				edge.isUsed = true;
				stack.push(v);
				v = edge.other(v);
			}
			// push vertex with no more leaving edges to cycle
			cycle.push(v);
		}

		// check if all edges are used
		if (cycle.size() != G.E() + 1)
			cycle = null;
	}

	/**
	 * Returns the sequence of vertices on an Eulerian cycle.
	 * 
	 * @return the sequence of vertices on an Eulerian cycle; {@code null} if no
	 *         such cycle
	 */
	public Iterable<Integer> cycle() {
		return cycle;
	}

	/**
	 * Returns true if the graph has an Eulerian cycle.
	 * 
	 * @return {@code true} if the graph has an Eulerian cycle; {@code false}
	 *         otherwise
	 */
	public boolean hasEulerianCycle() {
		return cycle != null;
	}

	// returns any non-isolated vertex; -1 if no such vertex
	private static int nonIsolatedVertex(UndirectedGraph G) {
		for (int v = 0; v < G.V(); v++)
			if (G.degree(v) > 0)
				return v;
		return -1;
	}

}
