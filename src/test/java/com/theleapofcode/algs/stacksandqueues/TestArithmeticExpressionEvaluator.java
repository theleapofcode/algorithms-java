package com.theleapofcode.algs.stacksandqueues;

import org.junit.Assert;
import org.junit.Test;

public class TestArithmeticExpressionEvaluator {

	@Test
	public void testEvaluate() {
		String expression = "( 1 + ((2 + 3) * (4 * 5)))";
		double result = ArithmeticExpressionEvaluator.evaluate(expression);
		Assert.assertEquals(101, result, 0.01);
	}

}
