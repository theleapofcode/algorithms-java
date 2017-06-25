package com.theleapofcode.algs.linearprogramming;

import org.junit.Test;

public class TestLinearProgrammingSimplex {

	@Test
	public void test() {
		double[] c = { 13.0, 23.0 };
		double[] b = { 480.0, 160.0, 1190.0 };
		double[][] A = { { 5.0, 15.0 }, { 4.0, 4.0 }, { 35.0, 20.0 }, };

		LinearProgrammingSimplex lp = new LinearProgrammingSimplex(A, b, c);
		System.out.println("value = " + lp.value());
		double[] x = lp.primal();
		for (int i = 0; i < x.length; i++)
			System.out.println("x[" + i + "] = " + x[i]);
		double[] y = lp.dual();
		for (int j = 0; j < y.length; j++)
			System.out.println("y[" + j + "] = " + y[j]);
	}

}
