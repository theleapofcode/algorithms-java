package com.theleapofcode.algs.undirectedgraph;

import com.theleapofcode.algs.stacksandqueues.Queue;
import com.theleapofcode.algs.stacksandqueues.QueueLinkedListImpl;
import com.theleapofcode.algs.stacksandqueues.Stack;
import com.theleapofcode.algs.stacksandqueues.StackLinkedListImpl;

/**
 * The {@code EulerianPath} class represents a data type for finding an Eulerian
 * path in a graph. An <em>Eulerian path</em> is a path (not necessarily simple)
 * that uses every edge in the graph exactly once.
 * <p>
 * This implementation uses a nonrecursive depth-first search. The constructor
 * runs in O(<em>E</em> + <em>V</em>) time, and uses O(<em>E</em> + <em>V</em>)
 * extra space, where <em>E</em> is the number of edges and <em>V</em> the
 * number of vertices All other methods take O(1) time.
 */
public class EulerianPath {
	private Stack<Integer> path = null; // Eulerian path; null if no such path

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
	 * Computes an Eulerian path in the specified graph, if one exists.
	 * 
	 * @param G
	 *            the graph
	 */
	@SuppressWarnings("unchecked")
	public EulerianPath(UndirectedGraph G) {

		// find vertex from which to start potential Eulerian path:
		// a vertex v with odd degree(v) if it exits;
		// otherwise a vertex with degree(v) > 0
		int oddDegreeVertices = 0;
		int s = nonIsolatedVertex(G);
		for (int v = 0; v < G.V(); v++) {
			if (G.degree(v) % 2 != 0) {
				oddDegreeVertices++;
				s = v;
			}
		}

		// graph can't have an Eulerian path
		// (this condition is needed for correctness)
		if (oddDegreeVertices > 2)
			return;

		// special case for graph with zero edges (has a degenerate Eulerian
		// path)
		if (s == -1)
			s = 0;

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
		Stack<Integer> stack = new StackLinkedListImpl<>();
		stack.push(s);

		// greedily search through edges in iterative DFS style
		path = new StackLinkedListImpl<>();
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
			// push vertex with no more leaving edges to path
			path.push(v);
		}

		// check if all edges are used
		if (path.size() != G.E() + 1)
			path = null;
	}

	/**
	 * Returns the sequence of vertices on an Eulerian path.
	 * 
	 * @return the sequence of vertices on an Eulerian path; {@code null} if no
	 *         such path
	 */
	public Iterable<Integer> path() {
		return path;
	}

	/**
	 * Returns true if the graph has an Eulerian path.
	 * 
	 * @return {@code true} if the graph has an Eulerian path; {@code false}
	 *         otherwise
	 */
	public boolean hasEulerianPath() {
		return path != null;
	}

	// returns any non-isolated vertex; -1 if no such vertex
	private static int nonIsolatedVertex(UndirectedGraph G) {
		for (int v = 0; v < G.V(); v++)
			if (G.degree(v) > 0)
				return v;
		return -1;
	}

}
