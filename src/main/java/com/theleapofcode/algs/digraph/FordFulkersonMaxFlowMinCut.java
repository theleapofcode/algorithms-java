package com.theleapofcode.algs.digraph;

import com.theleapofcode.algs.stacksandqueues.Queue;
import com.theleapofcode.algs.stacksandqueues.QueueLinkedListImpl;

/**
 * The {@code FordFulkerson} class represents a data type for computing a
 * <em>maximum st-flow</em> and <em>minimum st-cut</em> in a flow network.
 * <p>
 * This implementation uses the <em>Ford-Fulkerson</em> algorithm with the
 * <em>shortest augmenting path</em> heuristic. The constructor takes time
 * proportional to <em>E V</em> (<em>E</em> + <em>V</em>) in the worst case and
 * extra space (not including the network) proportional to <em>V</em>, where
 * <em>V</em> is the number of vertices and <em>E</em> is the number of edges.
 * In practice, the algorithm will run much faster. Afterwards, the
 * {@code inCut()} and {@code value()} methods take constant time.
 * <p>
 * If the capacities and initial flow values are all integers, then this
 * implementation guarantees to compute an integer-valued maximum flow. If the
 * capacities and floating-point numbers, then floating-point roundoff error can
 * accumulate.
 */
public class FordFulkersonMaxFlowMinCut {
	private static final double FLOATING_POINT_EPSILON = 1E-11;

	private final int V; // number of vertices
	private boolean[] marked; // marked[v] = true iff s->v path in residual
								// graph
	private FlowEdge[] edgeTo; // edgeTo[v] = last edge on shortest residual
								// s->v path
	private double value; // current value of max flow

	/**
	 * Compute a maximum flow and minimum cut in the network {@code G} from
	 * vertex {@code s} to vertex {@code t}.
	 *
	 * @param G
	 *            the flow network
	 * @param s
	 *            the source vertex
	 * @param t
	 *            the sink vertex
	 * @throws IllegalArgumentException
	 *             unless {@code 0 <= s < V}
	 * @throws IllegalArgumentException
	 *             unless {@code 0 <= t < V}
	 * @throws IllegalArgumentException
	 *             if {@code s == t}
	 * @throws IllegalArgumentException
	 *             if initial flow is infeasible
	 */
	public FordFulkersonMaxFlowMinCut(FlowNetwork G, int s, int t) {
		V = G.V();
		validate(s);
		validate(t);
		if (s == t)
			throw new IllegalArgumentException("Source equals sink");
		if (!isFeasible(G, s, t))
			throw new IllegalArgumentException("Initial flow is infeasible");

		// while there exists an augmenting path, use it
		value = excess(G, t);
		while (hasAugmentingPath(G, s, t)) {

			// compute bottleneck capacity
			double bottle = Double.POSITIVE_INFINITY;
			for (int v = t; v != s; v = edgeTo[v].other(v)) {
				bottle = Math.min(bottle, edgeTo[v].residualCapacityTo(v));
			}

			// augment flow
			for (int v = t; v != s; v = edgeTo[v].other(v)) {
				edgeTo[v].addResidualFlowTo(v, bottle);
			}

			value += bottle;
		}
	}

	/**
	 * Returns the value of the maximum flow.
	 *
	 * @return the value of the maximum flow
	 */
	public double value() {
		return value;
	}

	/**
	 * Returns true if the specified vertex is on the {@code s} side of the
	 * mincut.
	 *
	 * @param v
	 *            vertex
	 * @return {@code true} if vertex {@code v} is on the {@code s} side of the
	 *         micut; {@code false} otherwise
	 * @throws IllegalArgumentException
	 *             unless {@code 0 <= v < V}
	 */
	public boolean inCut(int v) {
		validate(v);
		return marked[v];
	}

	// throw an IllegalArgumentException if v is outside prescibed range
	private void validate(int v) {
		if (v < 0 || v >= V)
			throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
	}

	// is there an augmenting path?
	// if so, upon termination edgeTo[] will contain a parent-link
	// representation of such a path
	// this implementation finds a shortest augmenting path (fewest number of
	// edges),
	// which performs well both in theory and in practice
	private boolean hasAugmentingPath(FlowNetwork G, int s, int t) {
		edgeTo = new FlowEdge[G.V()];
		marked = new boolean[G.V()];

		// breadth-first search
		Queue<Integer> queue = new QueueLinkedListImpl<>();
		queue.enque(s);
		marked[s] = true;
		while (!queue.isEmpty() && !marked[t]) {
			int v = queue.deque();

			for (FlowEdge e : G.adj(v)) {
				int w = e.other(v);

				// if residual capacity from v to w
				if (e.residualCapacityTo(w) > 0) {
					if (!marked[w]) {
						edgeTo[w] = e;
						marked[w] = true;
						queue.enque(w);
					}
				}
			}
		}

		// is there an augmenting path?
		return marked[t];
	}

	// return excess flow at vertex v
	private double excess(FlowNetwork G, int v) {
		double excess = 0.0;
		for (FlowEdge e : G.adj(v)) {
			if (v == e.from())
				excess -= e.flow();
			else
				excess += e.flow();
		}
		return excess;
	}

	// return excess flow at vertex v
	private boolean isFeasible(FlowNetwork G, int s, int t) {

		// check that capacity constraints are satisfied
		for (int v = 0; v < G.V(); v++) {
			for (FlowEdge e : G.adj(v)) {
				if (e.flow() < -FLOATING_POINT_EPSILON || e.flow() > e.capacity() + FLOATING_POINT_EPSILON) {
					System.err.println("Edge does not satisfy capacity constraints: " + e);
					return false;
				}
			}
		}

		// check that net flow into a vertex equals zero, except at source and
		// sink
		if (Math.abs(value + excess(G, s)) > FLOATING_POINT_EPSILON) {
			System.err.println("Excess at source = " + excess(G, s));
			System.err.println("Max flow         = " + value);
			return false;
		}
		if (Math.abs(value - excess(G, t)) > FLOATING_POINT_EPSILON) {
			System.err.println("Excess at sink   = " + excess(G, t));
			System.err.println("Max flow         = " + value);
			return false;
		}
		for (int v = 0; v < G.V(); v++) {
			if (v == s || v == t)
				continue;
			else if (Math.abs(excess(G, v)) > FLOATING_POINT_EPSILON) {
				System.err.println("Net flow out of " + v + " doesn't equal zero");
				return false;
			}
		}
		return true;
	}

}
