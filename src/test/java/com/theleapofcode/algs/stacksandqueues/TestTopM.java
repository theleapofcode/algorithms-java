package com.theleapofcode.algs.stacksandqueues;

import org.junit.Test;

public class TestTopM {

	private TopM<String> topM = new TopM<>(3);

	@Test
	public void test() {
		topM.add("IronMan");
		topM.add("Thor");
		topM.add("Hulk");
		topM.add("CaptainAmerica");
		topM.add("HawkEye");
		topM.add("BlackWidow");

		for (String item : topM.getTopItems())
			System.out.println(item);
	}

}
