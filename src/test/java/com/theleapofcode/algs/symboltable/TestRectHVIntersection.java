package com.theleapofcode.algs.symboltable;

import java.util.List;

import com.theleapofcode.algs.geometry.RectHV;
import com.theleapofcode.algs.util.DrawingUtil;
import com.theleapofcode.algs.util.RandomUtil;

public class TestRectHVIntersection {
	
	public static void main(String[] args) {
		int N = 10;
		
		DrawingUtil.setCanvasSize(512, 512);
		DrawingUtil.setXscale(0, 600);
		DrawingUtil.setYscale(0, 600);
		DrawingUtil.setPenColor(DrawingUtil.BLACK);
		DrawingUtil.setPenRadius(.01);
		DrawingUtil.enableDoubleBuffering();

		// generate N random rects
		RectHV[] rects = new RectHV[N];
		for (int i = 0; i < N; i++) {
			double x1 = RandomUtil.uniform(10d, 400d);
			double x2 = x1 + RandomUtil.uniform(10d, 100d);
			double y1 = RandomUtil.uniform(10d, 400d);
			double y2 = y1 + RandomUtil.uniform(10d, 100d);
			rects[i] = new RectHV(x1, y1, x2, y2);
			System.out.println(rects[i]);
			rects[i].draw();
		}
		DrawingUtil.show();
		System.out.println();

		List<RectHV[]> intersections = RectHVIntersection.getIntersections(rects);

		for (RectHV[] rectIntersection : intersections) {
			System.out.println("Intersection:  " + rectIntersection[0]);
			System.out.println("               " + rectIntersection[1]);
		}
	}

}
