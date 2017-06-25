package com.theleapofcode.algs.digraph;

import org.junit.Test;

public class TestPrecedenceConstrainedJobScheduling {

	@Test
	public void test() {
		int n = 10;
		double[] durations = new double[] { 41.0, 51.0, 50.0, 36.0, 38.0, 45.0, 21.0, 32.0, 32.0, 29.0 };
		int[][] precedences = new int[n][];
		precedences[0] = new int[] { 1, 7, 9 };
		precedences[1] = new int[] { 2 };
		precedences[2] = new int[] {};
		precedences[3] = new int[] {};
		precedences[4] = new int[] {};
		precedences[5] = new int[] {};
		precedences[6] = new int[] { 3, 8 };
		precedences[7] = new int[] { 3, 8 };
		precedences[8] = new int[] { 2 };
		precedences[9] = new int[] { 4, 6 };

		PrecedenceConstrainedJobScheduling pcjs = new PrecedenceConstrainedJobScheduling(n, durations, precedences);

		double[][] schedule = pcjs.schedule();
		System.out.println(" job   start  finish");
		System.out.println("--------------------");
		for (int i = 0; i < schedule.length; i++) {
			System.out.printf("%4d %7.1f %7.1f\n", i, schedule[i][0], schedule[i][1]);
		}
		System.out.println();

		double finishTime = pcjs.scheduleFinishTime();
		System.out.println("Finish time - " + finishTime);
	}

}
