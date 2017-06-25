package com.theleapofcode.algs.symboltable;

import java.util.LinkedList;
import java.util.List;

import com.theleapofcode.algs.geometry.Interval1D;
import com.theleapofcode.algs.geometry.RectHV;
import com.theleapofcode.algs.stacksandqueues.MinPriorityQueue;
import com.theleapofcode.algs.stacksandqueues.Queue;

public class RectHVIntersection {

	// helper class for events in sweep line algorithm
	public static class Event implements Comparable<Event> {
		double time;
		RectHV rect;

		public Event(double time, RectHV rect) {
			this.time = time;
			this.rect = rect;
		}

		public int compareTo(Event that) {
			if (this.time < that.time)
				return -1;
			else if (this.time > that.time)
				return +1;
			else
				return 0;
		}
	}

	public static List<RectHV[]> getIntersections(RectHV[] rects) {
		List<RectHV[]> intersections = new LinkedList<>();
		// create events
		Queue<Event> pq = new MinPriorityQueue<Event>();
		for (int i = 0; i < rects.length; i++) {
			Event e1 = new Event(rects[i].xmin(), rects[i]);
			Event e2 = new Event(rects[i].xmax(), rects[i]);
			pq.enque(e1);
			pq.enque(e2);
		}

		// run sweep-line algorithm
		IntervalSymbolTable<RectHV> st = new IntervalSymbolTable<>();

		while (!pq.isEmpty()) {
			Event e = pq.deque();
			double sweep = e.time;
			RectHV rect = e.rect;

			// next event is left edge of rect
			if (sweep == rect.xmin()) {
				Interval1D interval = new Interval1D(rect.ymin(), rect.ymax());
				Iterable<Interval1D> list = st.searchAll(interval);
				for (Interval1D rectInterval : list) {
					RectHV intersectedRect = st.get(rectInterval);
					intersections.add(new RectHV[] { rect, intersectedRect });
				}

				st.put(interval, rect);
			}

			// next event is right edge of rect
			else if (sweep == rect.xmax()) {
				st.remove(new Interval1D(rect.ymin(), rect.ymax()));
			}
		}

		return intersections;
	}

}