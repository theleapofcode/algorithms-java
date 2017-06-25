package com.theleapofcode.algs.stacksandqueues;

import java.util.Arrays;

import com.theleapofcode.algs.geometry.Point2D;

public class GrahamScan {

	public static Point2D[] convexHull(Point2D[] points) {
		Stack<Point2D> hull = new StackLinkedListImpl<>();

		if (points == null)
			throw new IllegalArgumentException("argument is null");
		if (points.length == 0)
			throw new IllegalArgumentException("array is of length 0");

		// defensive copy
		int n = points.length;
		Point2D[] a = new Point2D[n];
		for (int i = 0; i < n; i++) {
			if (points[i] == null)
				throw new IllegalArgumentException("points[" + i + "] is null");
			a[i] = points[i];
		}

		// preprocess so that a[0] has lowest y-coordinate; break ties by
		// x-coordinate
		// a[0] is an extreme point of the convex hull
		// (alternatively, could do easily in linear time)
		Arrays.sort(a);

		// sort by polar angle with respect to base point a[0],
		// breaking ties by distance to a[0]
		Arrays.sort(a, 1, n, a[0].polarOrder());

		hull.push(a[0]); // a[0] is first extreme point

		// find index k1 of first point not equal to a[0]
		int k1;
		for (k1 = 1; k1 < n; k1++)
			if (!a[0].equals(a[k1]))
				break;

		if (k1 != n) {
			// find index k2 of first point not collinear with a[0] and a[k1]
			int k2;
			for (k2 = k1 + 1; k2 < n; k2++)
				if (Point2D.ccw(a[0], a[k1], a[k2]) != 0)
					break;
			hull.push(a[k2 - 1]); // a[k2-1] is second extreme point

			// Graham scan; note that a[n-1] is extreme point different from
			// a[0]
			for (int i = k2; i < n; i++) {
				Point2D top = hull.pop();
				while (Point2D.ccw(hull.peek(), top, a[i]) <= 0) {
					top = hull.pop();
				}
				hull.push(top);
				hull.push(a[i]);
			}
		}

		Point2D[] convexHullPoints = new Point2D[hull.size()];
		int x = hull.size();
		for (Point2D p : hull)
			convexHullPoints[--x] = p;
		return convexHullPoints;
	}

}
