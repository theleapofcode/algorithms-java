package com.theleapofcode.algs.digraph;

public class PrecedenceConstrainedJobScheduling {

	private EdgeWeightedDigraph G;
	private AcyclicLongestPath lp;
	private int n;
	private int source;
	private int sink;

	/**
	 * @param n
	 *            The number of jobs
	 * @param durations
	 *            Durations of jobs
	 * @param precedence
	 *            Job precedences - int[i][j] ith job must be completed before
	 *            all jobs in int[j]
	 */
	public PrecedenceConstrainedJobScheduling(int n, double[] durations, int[][] precedences) {
		// 0 - n-1 vertices for job starts, n to 2n-1 vertices for job finish,
		// 2n is the source, 2n+1 is the sink
		source = 2 * n;
		sink = 2 * n + 1;

		this.n = n;

		G = new EdgeWeightedDigraph(2 * n + 2);
		for (int i = 0; i < n; i++) {
			double duration = durations[i];
			G.addEdge(new DirectedEdge(source, i, 0.0)); // zero weighted edge
															// from source to
															// all job start
															// vertices
			G.addEdge(new DirectedEdge(i + n, sink, 0.0));// zero weighted edge
															// all job finish
															// vertices to sink
			G.addEdge(new DirectedEdge(i, i + n, duration)); // Edge from job
																// start to job
																// end with
																// duration as
																// weight

			// precedence constraints
			for (int j = 0; j < precedences[i].length; j++) {
				int precedent = precedences[i][j];
				G.addEdge(new DirectedEdge(n + i, precedent, 0.0));
			}
		}

		// compute longest path
		lp = new AcyclicLongestPath(G, source);
	}

	/**
	 * @return job schedule - double[n][2] start and finish time of jobs
	 */
	public double[][] schedule() {
		double[][] schedule = new double[n][];
		for (int i = 0; i < n; i++) {
			schedule[i] = new double[2];
			schedule[i][0] = lp.distTo(i);
			schedule[i][1] = lp.distTo(i + n);
		}

		return schedule;
	}

	public double scheduleFinishTime() {
		return lp.distTo(sink);
	}

}
