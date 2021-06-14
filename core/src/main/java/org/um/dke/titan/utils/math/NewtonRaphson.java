package org.um.dke.titan.utils.math;

public class NewtonRaphson {
    private static int JACOBIAN_MATRIX_SIZE = 3;

    private double h;
    private double[][] jacobian;

    public NewtonRaphson(double h) {
        this.h = h;

        jacobian = new double[JACOBIAN_MATRIX_SIZE][JACOBIAN_MATRIX_SIZE];
    }
}
