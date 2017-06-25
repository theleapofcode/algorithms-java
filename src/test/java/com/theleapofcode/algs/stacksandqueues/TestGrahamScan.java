package com.theleapofcode.algs.stacksandqueues;

import com.theleapofcode.algs.geometry.Point2D;
import com.theleapofcode.algs.util.DrawingUtil;
import com.theleapofcode.algs.util.RandomUtil;

public class TestGrahamScan {

	public static void main(String[] args) {
		DrawingUtil.setCanvasSize(640, 480);
		DrawingUtil.setXscale(0, 100);
		DrawingUtil.setYscale(0, 100);
		DrawingUtil.setPenRadius(0.005);
		DrawingUtil.enableDoubleBuffering();
		DrawingUtil.setTitle("Graham Scan for convex hull");

		int n = 10;

		Point2D[] points = new Point2D[n];
		for (int i = 0; i < n; i++) {
			int x = RandomUtil.uniform(100);
			int y = RandomUtil.uniform(100);
			points[i] = new Point2D(x, y);
			DrawingUtil.setPenColor(DrawingUtil.RED);
			DrawingUtil.setPenRadius(0.02);
			points[i].draw();
		}

		Point2D[] convexHullPoints = GrahamScan.convexHull(points);

		DrawingUtil.setPenRadius();
		DrawingUtil.setPenColor(DrawingUtil.BLUE);
		for (int i = 0; i < convexHullPoints.length; i++) {
			int j = (i + 1) % convexHullPoints.length;
			convexHullPoints[i].drawTo(convexHullPoints[j]);
			DrawingUtil.show();
			DrawingUtil.pause(1000);
		}
	}

}
