package org.um.dke.titan.utils.lander.math;

public class Function {
	public static double[][] f(double[] coefficient, double[] exponent, double a, double b, double stepSize) {
		double[][] vals;
		double length = (b - a);
		int steps = (int)(length / stepSize);
		vals = new double[2][steps];
		vals[0] = xArray(a,b,stepSize);
		//function
		for(int i = 0; i < steps; i++) {
			vals[1][i] = 0;
			for(int j = 0; j < coefficient.length; j++) {
				vals[1][i] += evaluate(coefficient[j], exponent[j], vals[0][i]);
			}
		}
		return vals;
	}

	public static double[] xArray(double a, double b, double stepSize) {
		double length = (b - a);
		int steps = (int)(length / stepSize);
		double[] xVals = new double[steps];
		for(int i = 0; i < steps; i++) {
			xVals[i] = a + i*stepSize;
		}
		return xVals;
	}
	public static double evaluate(double coefficient, double exponent, double value) {
		return coefficient * Math.pow(value, exponent);
	}
}
