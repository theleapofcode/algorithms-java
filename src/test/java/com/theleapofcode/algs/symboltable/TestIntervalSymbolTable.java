package com.theleapofcode.algs.symboltable;

import org.junit.Test;

import com.theleapofcode.algs.geometry.Interval1D;

public class TestIntervalSymbolTable {

	@Test
	public void test() {
		int N = 10;

		// generate N random intervals and insert into data structure
		IntervalSymbolTable<String> st = new IntervalSymbolTable<String>();
		for (int i = 0; i < N; i++) {
			int min = (int) (Math.random() * 1000);
			int max = (int) (Math.random() * 50) + min;
			Interval1D interval = new Interval1D(min, max);
			System.out.println(interval);
			st.put(interval, "" + i);
		}

		// print out tree statistics
		System.out.println("height:          " + st.height());
		System.out.println("size:            " + st.size());
		System.out.println();

		// generate random intervals and check for overlap
		for (int i = 0; i < N; i++) {
			int min = (int) (Math.random() * 100);
			int max = (int) (Math.random() * 10) + min;
			Interval1D interval = new Interval1D(min, max);
			System.out.println(interval + ":  " + st.search(interval));
			System.out.print(interval + ":  ");
			for (Interval1D x : st.searchAll(interval))
				System.out.print(x + " ");
			System.out.println();
			System.out.println();
		}
	}

}
