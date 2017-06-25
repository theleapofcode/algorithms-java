package com.theleapofcode.algs.digraph;

import org.junit.Test;

public class TestCurrencyExchangeArbitrage {

	@Test
	public void test() {
		String[] currencies = new String[] { "USD", "EUR", "GBP", "CHF", "CAD" };

		double[][] exchangeRates = new double[5][5];
		exchangeRates[0][0] = 1;
		exchangeRates[0][1] = 0.741;
		exchangeRates[0][2] = 0.657;
		exchangeRates[0][3] = 1.061;
		exchangeRates[0][4] = 1.011;
		exchangeRates[1][0] = 1.350;
		exchangeRates[1][1] = 1;
		exchangeRates[1][2] = 0.888;
		exchangeRates[1][3] = 1.433;
		exchangeRates[1][4] = 1.366;
		exchangeRates[2][0] = 1.521;
		exchangeRates[2][1] = 1.126;
		exchangeRates[2][2] = 1;
		exchangeRates[2][3] = 1.614;
		exchangeRates[2][4] = 1.538;
		exchangeRates[3][0] = 0.943;
		exchangeRates[3][1] = 0.698;
		exchangeRates[3][2] = 0.620;
		exchangeRates[3][3] = 1;
		exchangeRates[3][4] = 0.953;
		exchangeRates[4][0] = 0.995;
		exchangeRates[4][1] = 0.732;
		exchangeRates[4][2] = 0.650;
		exchangeRates[4][3] = 1.049;
		exchangeRates[4][4] = 1;

		CurrencyExchangeArbitrage cea = new CurrencyExchangeArbitrage(exchangeRates);

		String details = cea.getArbitrageDetails(currencies, 1000.0);
		System.out.println(details);
	}

}
