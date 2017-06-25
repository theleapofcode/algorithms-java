package com.theleapofcode.algs.symboltable;

import java.util.List;

import org.junit.Test;

import com.theleapofcode.algs.geometry.SegmentHV;

public class TestSegmentHVIntersection {

	@Test
	public void testIntersection() {
		int N = 20;

		// generate N random h-v segments
		SegmentHV[] segments = new SegmentHV[N];
		for (int i = 0; i < N; i++) {
			int x1 = (int) (Math.random() * 10);
			int x2 = x1 + (int) (Math.random() * 3);
			int y1 = (int) (Math.random() * 10);
			int y2 = y1 + (int) (Math.random() * 5);
			if (Math.random() < 0.5)
				segments[i] = new SegmentHV(x1, y1, x1, y2);
			else
				segments[i] = new SegmentHV(x1, y1, x2, y1);
			System.out.println(segments[i]);
		}
		System.out.println();

		List<SegmentHV[]> intersections = SegmentHVIntersection.getIntersections(segments);

		for (SegmentHV[] segs : intersections) {
			System.out.println("Intersection:  " + segs[0]);
			System.out.println("               " + segs[1]);
		}
	}

}
