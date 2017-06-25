package com.theleapofcode.algs.symboltable;

import com.theleapofcode.algs.geometry.Point2D;
import com.theleapofcode.algs.geometry.RectHV;
import com.theleapofcode.algs.util.DrawingUtil;
import com.theleapofcode.algs.util.RandomUtil;

public class TestKdTree {

	public static void main(String[] args) {
		KdTree tree = new KdTree();

		System.out.println("Is empty: " + tree.isEmpty());

		DrawingUtil.setPenColor(DrawingUtil.BLACK);
		DrawingUtil.setPenRadius(.01);

		int n = 10;

		for (int i = 0; i < n; i++) {
			double x = RandomUtil.uniform(0d, 1d);
			double y = RandomUtil.uniform(0d, 1d);
			Point2D point = new Point2D(x, y);
			tree.insert(point);
		}

		System.out.println("Size: " + tree.size());
		System.out.println("Is empty: " + tree.isEmpty());
		tree.draw();

		Point2D point = new Point2D(0.5, 1.0);
		System.out.println("tree contains " + point.toString() + ": " + tree.contains(point));

		// DrawingUtil.setPenRadius();

		RectHV rect = new RectHV(0.0, 0.0, 0.5, 0.5);
		// rect.draw();
		System.out.println("Points in rectangle " + rect.toString() + ":");
		for (Point2D p : tree.range(rect)) {
			System.out.println(p.toString());
		}
		System.out.println("-");

		Point2D nearPoint = new Point2D(0.1, 0.1);
		// point.draw();
		System.out.println("The nearest point to point " + nearPoint.toString() + ": ");
		System.out.println(tree.nearest(nearPoint).toString());
	}

}
