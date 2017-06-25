package com.theleapofcode.algs.digraph;

public class CurrencyExchangeArbitrage {

	private EdgeWeightedDigraph G;
	private BellmanFordShortestPath spt;

	/**
	 * @param n
	 *            The number of currency types
	 * @param names
	 *            Names of currencies
	 * @param exchangeRates
	 *            Exchange rates - nxn array
	 */
	public CurrencyExchangeArbitrage(double[][] exchangeRates) {
		// Goal is to find a cycle with product of edge weights > 1.
		// -log(weights) converts this problem to sum of weights < 0 which is a
		// negative cycle
		int n = exchangeRates.length;

		G = new EdgeWeightedDigraph(n);
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				double rate = exchangeRates[i][j];
				DirectedEdge e = new DirectedEdge(i, j, -Math.log(rate));
				G.addEdge(e);
			}
		}

		// compute Bellman-Ford shortest path tree
		spt = new BellmanFordShortestPath(G, 0);
	}

	public String getArbitrageDetails(String[] names, double stake) {
		if (!hasArbitrageOppertunity())
			return null;

		StringBuilder sb = new StringBuilder();
		for (DirectedEdge e : spt.negativeCycle()) {
			sb.append(String.format("%10.5f %s ", stake, names[e.from()]));
			stake *= Math.exp(-e.weight());
			sb.append(String.format("= %10.5f %s\n", stake, names[e.to()]));
		}

		return sb.toString();
	}

	public boolean hasArbitrageOppertunity() {
		return spt.hasNegativeCycle();
	}

}
