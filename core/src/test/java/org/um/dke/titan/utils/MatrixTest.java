package org.um.dke.titan.utils;

import org.junit.Test;
import org.um.dke.titan.domain.Vector3D;
import org.um.dke.titan.interfaces.Vector3dInterface;

import static org.junit.Assert.*;

public class MatrixTest {

    @Test
    public void testInverseMatrix(){

        double[][] m = new double[][]{{1, -2}, {3, 4}};
        double[][] actInverse = new double[][]{{0.4, 0.2}, {-0.3, 0.1}};

        Matrix matrix = new Matrix(m);
        Matrix inv = matrix.inverse();

        for (int i = 0; i < matrix.getRows(); i++) {
            for (int j = 0; j < matrix.getColumns(); j++) {
                assertEquals(actInverse[i][j], inv.getMatrix()[i][j], 1e-7);
            }
        }
    }

    @Test
    public void testJacobianMatrix(){
        double x1 = 1;
        double x2= -2;
        double x3 = Math.PI/3;

        double f1Val = f1(x1, x2, x3);
        double f2Val = f2(x1, x2, x3);
        double f3Val = f3(x1, x2, x3);

        Matrix mtrx = new Matrix(3, 1);
        mtrx.setValueAt( 0, 0, f1Val);
        mtrx.setValueAt(1, 0, f2Val);
        mtrx.setValueAt(2, 0, f3Val);

        Vector3dInterface g = new Vector3D(f1Val, f2Val, f3Val);

        NewtonRaphson nr = new NewtonRaphson(0.25);

        Vector3dInterface actual = nr.execute(g, new Vector3D(0, 0, 0));

        Matrix expected = new Matrix(3, 3);
        expected.setValueAt(0, 0, 2);
        expected.setValueAt(0, 1, -12);
        expected.setValueAt(0, 2, -0.5);
        expected.setValueAt(1, 0, 3);
        expected.setValueAt(1, 1, -0.9069);
        expected.setValueAt(1, 2, 1.7321);
        expected.setValueAt(2, 0, 14.7781);
        expected.setValueAt(2, 1, -7.3891);
        expected.setValueAt(2, 2, 3);

        assertTrue(true);

    }

    private static double f1(double x1, double x2, double x3) {
        return  2*x1 + 3*(x2*x2) - Math.sin(x3);
    }

    private static double f2(double x1, double x2, double x3) {
        return 3*x1 - Math.cos(x2*x3) - 1.5;
    }

    private static double f3(double x1, double x2, double x3) {
        return Math.exp(-x1*x2) + 3*x3;
    }

    @Test
    public void testNewtonRaphson() {
        NewtonRaphson nr = new NewtonRaphson(0.25);

        Vector3dInterface g = F(new Vector3D(1, 1, 1));
        Vector3dInterface v = F(new Vector3D(1, 2, 3));

        Vector3dInterface actual = nr.execute(g, v);

        Vector3dInterface expected = new Vector3D(0.8332816138167559, 0.03533461613948914, -0.49854927781103736);

        assertEquals(expected.getX(), actual.getX(), 1e-7);
        assertEquals(expected.getY(), actual.getY(), 1e-7);
        assertEquals(expected.getZ(), actual.getZ(), 1e-7);
    }

    static Vector3dInterface F(Vector3dInterface vector) {
        double x1 = vector.getX();
        double x2 = vector.getY();
        double x3 = vector.getZ();

        double x = 3.0 * x1 - Math.cos(x2 * x3) - 3.0/2.0;
        double y = 4.0 * (x1*x1) - 625.0 * (x2*x2) + 2*x3 - 1.0;
        double z = 20 * x3 + Math.exp(-x1*x2) + 9;

        return new Vector3D(x,y,z);
    }
}
