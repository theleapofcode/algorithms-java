package com.theleapofcode.algs.stacksandqueues;

import com.theleapofcode.algs.geometry.Particle;
import com.theleapofcode.algs.util.DrawingUtil;

public class TestElasticCollision {

	public static void main(String[] args) {
		DrawingUtil.setCanvasSize(512, 512);

		// enable double buffering
		DrawingUtil.enableDoubleBuffering();

		// the array of particles
		Particle[] particles;

		// create n random particles
		int n = 100;
		particles = new Particle[n];
		for (int i = 0; i < n; i++)
			particles[i] = new Particle();

		// create collision system and simulate
		ElasticCollisionSystem system = new ElasticCollisionSystem(particles);
		system.simulate(10000);
	}

}
