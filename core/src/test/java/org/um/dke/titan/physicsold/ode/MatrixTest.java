package org.um.dke.titan.physicsold.ode;

import org.junit.Test;
import org.um.dke.titan.domain.MovingObject;
import org.um.dke.titan.domain.Vector3D;
import org.um.dke.titan.interfaces.ODEFunctionInterface;
import org.um.dke.titan.interfaces.StateInterface;
import org.um.dke.titan.utils.math.Matrix;

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
}
