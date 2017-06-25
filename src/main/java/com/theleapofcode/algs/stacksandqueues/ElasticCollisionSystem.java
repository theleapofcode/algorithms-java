package com.theleapofcode.algs.stacksandqueues;

import com.theleapofcode.algs.geometry.Particle;
import com.theleapofcode.algs.util.DrawingUtil;

public class ElasticCollisionSystem {

	private final static double HZ = 0.5; // number of redraw events per clock
											// tick

	private MinPriorityQueue<Event> pq; // the priority queue
	private double t = 0.0; // simulation clock time
	private Particle[] particles; // the array of particles

	/**
	 * Initializes a system with the specified collection of particles. The
	 * individual particles will be mutated during the simulation.
	 *
	 * @param particles
	 *            the array of particles
	 */
	public ElasticCollisionSystem(Particle[] particles) {
		this.particles = particles.clone(); // defensive copy
	}

	// updates priority queue with all new events for particle a
	private void predict(Particle a, double limit) {
		if (a == null)
			return;

		// particle-particle collisions
		for (int i = 0; i < particles.length; i++) {
			double dt = a.timeToHit(particles[i]);
			if (t + dt <= limit)
				pq.enque(new Event(t + dt, a, particles[i]));
		}

		// particle-wall collisions
		double dtX = a.timeToHitVerticalWall();
		double dtY = a.timeToHitHorizontalWall();
		if (t + dtX <= limit)
			pq.enque(new Event(t + dtX, a, null));
		if (t + dtY <= limit)
			pq.enque(new Event(t + dtY, null, a));
	}

	// redraw all particles
	private void redraw(double limit) {
		DrawingUtil.clear();
		for (int i = 0; i < particles.length; i++) {
			particles[i].draw();
		}
		DrawingUtil.show();
		DrawingUtil.pause(20);
		if (t < limit) {
			pq.enque(new Event(t + 1.0 / HZ, null, null));
		}
	}

	/**
	 * Simulates the system of particles for the specified amount of time.
	 *
	 * @param limit
	 *            the amount of time
	 */
	public void simulate(double limit) {
		// initialize PQ with collision events and redraw event
		pq = new MinPriorityQueue<Event>();
		for (int i = 0; i < particles.length; i++) {
			predict(particles[i], limit);
		}
		pq.enque(new Event(0, null, null)); // redraw event

		// the main event-driven simulation loop
		while (!pq.isEmpty()) {

			// get impending event, discard if invalidated
			Event e = pq.deque();
			if (!e.isValid())
				continue;
			Particle a = e.a;
			Particle b = e.b;

			// physical collision, so update positions, and then simulation
			// clock
			for (int i = 0; i < particles.length; i++)
				particles[i].move(e.time - t);
			t = e.time;

			// process event
			if (a != null && b != null)
				a.bounceOff(b); // particle-particle collision
			else if (a != null && b == null)
				a.bounceOffVerticalWall(); // particle-wall collision
			else if (a == null && b != null)
				b.bounceOffHorizontalWall(); // particle-wall collision
			else if (a == null && b == null)
				redraw(limit); // redraw event

			// update the priority queue with new collisions involving a or b
			predict(a, limit);
			predict(b, limit);
		}
	}

	/***************************************************************************
	 * An event during a particle collision simulation. Each event contains the
	 * time at which it will occur (assuming no supervening actions) and the
	 * particles a and b involved.
	 *
	 * - a and b both null: redraw event - a null, b not null: collision with
	 * vertical wall - a not null, b null: collision with horizontal wall - a
	 * and b both not null: binary collision between a and b
	 *
	 ***************************************************************************/
	private static class Event implements Comparable<Event> {
		private final double time; // time that event is scheduled to occur
		private final Particle a, b; // particles involved in event, possibly
										// null
		private final int countA, countB; // collision counts at event creation

		// create a new event to occur at time t involving a and b
		public Event(double t, Particle a, Particle b) {
			this.time = t;
			this.a = a;
			this.b = b;
			if (a != null)
				countA = a.count();
			else
				countA = -1;
			if (b != null)
				countB = b.count();
			else
				countB = -1;
		}

		// compare times when two events will occur
		public int compareTo(Event that) {
			return Double.compare(this.time, that.time);
		}

		// has any collision occurred between when event was created and now?
		public boolean isValid() {
			if (a != null && a.count() != countA)
				return false;
			if (b != null && b.count() != countB)
				return false;
			return true;
		}

	}
}
