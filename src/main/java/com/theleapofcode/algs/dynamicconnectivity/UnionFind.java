package com.theleapofcode.algs.dynamicconnectivity;

public interface UnionFind {

	/**
	 * Connects {@code x} and {@code y}
	 * 
	 * @param x
	 * @param y
	 * 
	 * @throws IndexOutOfBoundsException
	 *             If x or y is < 0 or > n
	 */
	public void union(int x, int y) throws IndexOutOfBoundsException;

	/**
	 * Checks if x and y are connected
	 * 
	 * @param x
	 * @param y
	 * 
	 * @return {@code true} if {@code x} and {@code y} are in the same connected
	 *         component; {@code false} otherwise
	 * 
	 * @throws IndexOutOfBoundsException
	 *             If x or y is < 0 or > n
	 */
	public boolean isConnected(int x, int y) throws IndexOutOfBoundsException;

	/**
	 * Returns the component identifier for the component containing {@code x}.
	 *
	 * @param x
	 * 
	 * @return the component identifier for the component containing site
	 *         {@code x}
	 * 
	 * @throws IndexOutOfBoundsException
	 *             If x is < 0 or > n
	 */
	public int find(int x);

	/**
	 * Returns the number of connected components.
	 *
	 * @return the number of connected components
	 */
	public int count();

}
