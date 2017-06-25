package com.theleapofcode.algs.digraph;

public interface StronglyConnectedComponents {

	/**
	 * Returns the number of strong components.
	 * 
	 * @return the number of strong components
	 */
	public int count();

	/**
	 * Are vertices {@code v} and {@code w} in the same strong component?
	 * 
	 * @param v
	 *            one vertex
	 * @param w
	 *            the other vertex
	 * @return {@code true} if vertices {@code v} and {@code w} are in the same
	 *         strong component, and {@code false} otherwise
	 * @throws IllegalArgumentException
	 *             unless {@code 0 <= v < V}
	 * @throws IllegalArgumentException
	 *             unless {@code 0 <= w < V}
	 */
	public boolean stronglyConnected(int v, int w);

	/**
	 * Returns the component id of the strong component containing vertex
	 * {@code v}.
	 * 
	 * @param v
	 *            the vertex
	 * @return the component id of the strong component containing vertex
	 *         {@code v}
	 * @throws IllegalArgumentException
	 *             unless {@code 0 <= s < V}
	 */
	public int id(int v);

}
