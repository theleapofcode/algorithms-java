package com.theleapofcode.algs.linearprogramming;

/**
 * The {@code LinearProgrammingSimplex} class represents a data type for solving a
 * linear program of the form { max cx : Ax &le; b, x &ge; 0 }, where A is a
 * m-by-n matrix, b is an m-length vector, and c is an n-length vector. For
 * simplicity, we assume that A is of full rank and that b &ge; 0 so that x = 0
 * is a basic feasible soution.
 * <p>
 * The data type supplies methods for determining the optimal primal and dual
 * solutions.
 * <p>
 * This is a bare-bones implementation of the <em>simplex algorithm</em>. It
 * uses Bland's rule to determing the entering and leaving variables. It is not
 * suitable for use on large inputs. It is also not robust in the presence of
 * floating-point roundoff error.
 */
public class LinearProgrammingSimplex {
	private static final double EPSILON = 1.0E-10;
	private double[][] a; // tableaux
	private int m; // number of constraints
	private int n; // number of original variables

	private int[] basis; // basis[i] = basic variable corresponding to row i
							// only needed to print out solution, not book

	/**
	 * Determines an optimal solution to the linear program { max cx : Ax &le;
	 * b, x &ge; 0 }, where A is a m-by-n matrix, b is an m-length vector, and c
	 * is an n-length vector.
	 *
	 * @param A
	 *            the <em>m</em>-by-<em>b</em> matrix
	 * @param b
	 *            the <em>m</em>-length RHS vector
	 * @param c
	 *            the <em>n</em>-length cost vector
	 * @throws IllegalArgumentException
	 *             unless {@code b[i] >= 0} for each {@code i}
	 * @throws ArithmeticException
	 *             if the linear program is unbounded
	 */
	public LinearProgrammingSimplex(double[][] A, double[] b, double[] c) {
		m = b.length;
		n = c.length;
		for (int i = 0; i < m; i++)
			if (!(b[i] >= 0))
				throw new IllegalArgumentException("RHS must be nonnegative");

		a = new double[m + 1][n + m + 1];
		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++)
				a[i][j] = A[i][j];
		for (int i = 0; i < m; i++)
			a[i][n + i] = 1.0;
		for (int j = 0; j < n; j++)
			a[m][j] = c[j];
		for (int i = 0; i < m; i++)
			a[i][m + n] = b[i];

		basis = new int[m];
		for (int i = 0; i < m; i++)
			basis[i] = n + i;

		solve();
	}

	// run simplex algorithm starting from initial BFS
	private void solve() {
		while (true) {

			// find entering column q
			int q = bland();
			if (q == -1)
				break; // optimal

			// find leaving row p
			int p = minRatioRule(q);
			if (p == -1)
				throw new ArithmeticException("Linear program is unbounded");

			// pivot
			pivot(p, q);

			// update basis
			basis[p] = q;
		}
	}

	// lowest index of a non-basic column with a positive cost
	private int bland() {
		for (int j = 0; j < m + n; j++)
			if (a[m][j] > 0)
				return j;
		return -1; // optimal
	}

	// index of a non-basic column with most positive cost
	private int dantzig() {
		int q = 0;
		for (int j = 1; j < m + n; j++)
			if (a[m][j] > a[m][q])
				q = j;

		if (a[m][q] <= 0)
			return -1; // optimal
		else
			return q;
	}

	// find row p using min ratio rule (-1 if no such row)
	// (smallest such index if there is a tie)
	private int minRatioRule(int q) {
		int p = -1;
		for (int i = 0; i < m; i++) {
			// if (a[i][q] <= 0) continue;
			if (a[i][q] <= EPSILON)
				continue;
			else if (p == -1)
				p = i;
			else if ((a[i][m + n] / a[i][q]) < (a[p][m + n] / a[p][q]))
				p = i;
		}
		return p;
	}

	// pivot on entry (p, q) using Gauss-Jordan elimination
	private void pivot(int p, int q) {

		// everything but row p and column q
		for (int i = 0; i <= m; i++)
			for (int j = 0; j <= m + n; j++)
				if (i != p && j != q)
					a[i][j] -= a[p][j] * a[i][q] / a[p][q];

		// zero out column q
		for (int i = 0; i <= m; i++)
			if (i != p)
				a[i][q] = 0.0;

		// scale row p
		for (int j = 0; j <= m + n; j++)
			if (j != q)
				a[p][j] /= a[p][q];
		a[p][q] = 1.0;
	}

	/**
	 * Returns the optimal value of this linear program.
	 *
	 * @return the optimal value of this linear program
	 *
	 */
	public double value() {
		return -a[m][m + n];
	}

	/**
	 * Returns the optimal primal solution to this linear program.
	 *
	 * @return the optimal primal solution to this linear program
	 */
	public double[] primal() {
		double[] x = new double[n];
		for (int i = 0; i < m; i++)
			if (basis[i] < n)
				x[basis[i]] = a[i][m + n];
		return x;
	}

	/**
	 * Returns the optimal dual solution to this linear program
	 *
	 * @return the optimal dual solution to this linear program
	 */
	public double[] dual() {
		double[] y = new double[m];
		for (int i = 0; i < m; i++)
			y[i] = -a[m][n + i];
		return y;
	}

}
