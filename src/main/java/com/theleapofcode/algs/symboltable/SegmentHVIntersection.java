package com.theleapofcode.algs.symboltable;

import java.util.LinkedList;
import java.util.List;

import com.theleapofcode.algs.geometry.SegmentHV;
import com.theleapofcode.algs.stacksandqueues.MinPriorityQueue;
import com.theleapofcode.algs.stacksandqueues.Queue;

public class SegmentHVIntersection {

	private static final int INFINITY = Integer.MAX_VALUE;

	// helper class for events in sweep line algorithm
	public static class Event implements Comparable<Event> {
		int time;
		SegmentHV segment;

		public Event(int time, SegmentHV segment) {
			this.time = time;
			this.segment = segment;
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

	public static List<SegmentHV[]> getIntersections(SegmentHV[] segments) {
		List<SegmentHV[]> intersections = new LinkedList<>();
		// create events
		Queue<Event> pq = new MinPriorityQueue<Event>();
		for (int i = 0; i < segments.length; i++) {
			if (segments[i].isVertical()) {
				Event e = new Event(segments[i].x1, segments[i]);
				pq.enque(e);
			} else if (segments[i].isHorizontal()) {
				Event e1 = new Event(segments[i].x1, segments[i]);
				Event e2 = new Event(segments[i].x2, segments[i]);
				pq.enque(e1);
				pq.enque(e2);
			}
		}

		// run sweep-line algorithm
		OrderedSymbolTable<SegmentHV, String> st = new RedBlackBSTSymbolTable<>();

		while (!pq.isEmpty()) {
			Event e = pq.deque();
			int sweep = e.time;
			SegmentHV segment = e.segment;

			// vertical segment
			if (segment.isVertical()) {
				// a bit of a hack here - use infinity to handle degenerate
				// intersections
				SegmentHV seg1 = new SegmentHV(-INFINITY, segment.y1, -INFINITY, segment.y1);
				SegmentHV seg2 = new SegmentHV(+INFINITY, segment.y2, +INFINITY, segment.y2);
				Iterable<SegmentHV> list = st.keys(seg1, seg2);
				for (SegmentHV seg : list) {
					intersections.add(new SegmentHV[] { segment, seg });
				}
			}

			// next event is left endpoint of horizontal h-v segment
			else if (sweep == segment.x1) {
				st.put(segment, segment.toString());
			}

			// next event is right endpoint of horizontal h-v segment
			else if (sweep == segment.x2) {
				st.delete(segment);
			}
		}

		return intersections;
	}

}