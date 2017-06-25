package com.theleapofcode.algs.stacksandqueues;

public class ArithmeticExpressionEvaluator {

	public static double evaluate(String expression) {
		Stack<Character> operators = new StackLinkedListImpl<>();
		Stack<Double> values = new StackLinkedListImpl<>();

		for (char c : expression.toCharArray()) {
			if (c == '(') {
			} else if (c == '+' || c == '-' || c == '*' || c == '/') {
				operators.push(c);
			} else if (c == ')') {
				char operator = operators.pop();
				double second = values.pop();
				double first = values.pop();
				switch (operator) {
				case '+':
					values.push(first + second);
					break;
				case '-':
					values.push(first - second);
					break;
				case '*':
					values.push(first * second);
					break;
				case '/':
					values.push(first / second);
					break;
				default:
					break;
				}
			} else if (c == ' ') {
			} else {
				values.push(Double.parseDouble(String.valueOf(c)));
			}
		}

		return values.pop();
	}

}
