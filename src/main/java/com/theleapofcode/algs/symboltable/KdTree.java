package com.theleapofcode.algs.symboltable;

import com.theleapofcode.algs.geometry.Point2D;
import com.theleapofcode.algs.geometry.RectHV;
import com.theleapofcode.algs.stacksandqueues.Queue;
import com.theleapofcode.algs.stacksandqueues.QueueLinkedListImpl;
import com.theleapofcode.algs.util.DrawingUtil;

public class KdTree {

	// Representation of Point with Rectangle containing it
	private static class Node {
		private Point2D p; // Point
		private RectHV rect; // Rectangle containing the point
		private Node lb; // Left or Bottom
		private Node rt; // Right or Top
		private boolean vertical; // Is the partition at the point vertical?

		public Node(Point2D p, boolean vertical, RectHV rect) {
			this.p = p;
			this.rect = rect;
			this.vertical = vertical;
		}

		@Override
		public String toString() {
			return p.toString();
		}
	}

	private Node root; // Root of tree
	private RectHV plain; // Entire plain containing root
	private int count; // Number of points

	public KdTree() {
		// construct an empty set of points
		plain = new RectHV(0, 0, 1, 1);
	}

	public boolean isEmpty() {
		// is the set empty?
		return size() == 0;
	}

	public int size() {
		// number of points in the set
		return count;
	}

	public void insert(Point2D p) {
		// add the point to the tree
		if (p == null) {
			throw new IllegalArgumentException("Point is null");
		}
		root = insert(root, p, true, plain);
	}

	private Node insert(Node x, Point2D p, boolean vertical, RectHV rect) {
		if (x == null) {
			count++;
			return new Node(p, vertical, rect);
		}
		if (x.p.equals(p)) {
			return x;
		}

		boolean cmp;
		if (vertical) {
			cmp = p.x() < x.p.x();
		} else {
			cmp = p.y() < x.p.y();
		}
		RectHV nextRect;
		if (cmp) {
			if (x.lb == null) {
				double x1 = rect.xmin();
				double y1 = rect.ymin();
				double x2, y2;
				if (vertical) {
					x2 = x.p.x();
					y2 = rect.ymax();
				} else {
					x2 = rect.xmax();
					y2 = x.p.y();
				}
				nextRect = new RectHV(x1, y1, x2, y2);
			} else {
				nextRect = x.lb.rect;
			}
			x.lb = insert(x.lb, p, !vertical, nextRect);
		} else {
			if (x.rt == null) {
				double x1, y1;
				if (vertical) {
					x1 = x.p.x();
					y1 = rect.ymin();
				} else {
					x1 = rect.xmin();
					y1 = x.p.y();
				}
				double x2 = rect.xmax();
				double y2 = rect.ymax();
				nextRect = new RectHV(x1, y1, x2, y2);
			} else {
				nextRect = x.rt.rect;
			}
			x.rt = insert(x.rt, p, !vertical, nextRect);
		}
		return x;
	}

	public boolean contains(Point2D p) {
		if (p == null) {
			throw new IllegalArgumentException("Point is null");
		}
		return contains(root, p);
	}

	private boolean contains(Node x, Point2D p) {
		if (x == null) {
			return false;
		}
		if (x.p.equals((Object) p)) {
			return true;
		}
		boolean cmp;
		if (x.vertical) {
			cmp = p.x() < x.p.x();
		} else {
			cmp = p.y() < x.p.y();
		}
		if (cmp) {
			return contains(x.lb, p);
		} else {
			return contains(x.rt, p);
		}
	}

	public void draw() {
		draw(root, true);
	}

	private void draw(Node x, boolean vertical) {
		if (x == null) {
			throw new IllegalArgumentException("node is null");
		}
		DrawingUtil.setPenColor(DrawingUtil.BLACK);
		DrawingUtil.setPenRadius(.02);
		x.p.draw();
		if (vertical) {
			DrawingUtil.setPenColor(DrawingUtil.RED);
			DrawingUtil.setPenRadius(.01);
			DrawingUtil.line(x.p.x(), x.rect.ymin(), x.p.x(), x.rect.ymax());
		} else {
			DrawingUtil.setPenColor(DrawingUtil.BLUE);
			DrawingUtil.setPenRadius(.01);
			DrawingUtil.line(x.rect.xmin(), x.p.y(), x.rect.xmax(), x.p.y());
		}
		if (x.lb != null) {
			draw(x.lb, !vertical);
		}
		if (x.rt != null) {
			draw(x.rt, !vertical);
		}
	}

	public Iterable<Point2D> range(RectHV rect) {
		if (rect == null) {
			throw new IllegalArgumentException("rect is null");
		}
		Queue<Point2D> points = new QueueLinkedListImpl<>();
		Queue<Node> queue = new QueueLinkedListImpl<>();
		queue.enque(root);
		while (!queue.isEmpty()) {
			Node x = queue.deque();
			if (x == null) {
				continue;
			}
			if (rect.contains(x.p)) {
				points.enque(x.p);
			}
			if (x.lb != null) {
				queue.enque(x.lb);
			}
			if (x.rt != null) {
				queue.enque(x.rt);
			}
		}
		return points;
	}

	public Point2D nearest(Point2D p) {
		if (p == null) {
			throw new IllegalArgumentException("Point is null");
		}
		Point2D nearest = null;
		double nearestDistance = Double.MAX_VALUE;
		Queue<Node> queue = new QueueLinkedListImpl<>();
		queue.enque(root);
		while (!queue.isEmpty()) {
			Node x = queue.deque();
			if (x == null) {
				continue;
			}
			double distance = p.distanceTo(x.p);
			if (distance < nearestDistance) {
				nearest = x.p;
				nearestDistance = distance;
			}
			queue.enque(x.lb);
			queue.enque(x.rt);
		}
		return nearest;
	}

}
