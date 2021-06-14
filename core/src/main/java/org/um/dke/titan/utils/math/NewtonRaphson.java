package org.um.dke.titan.utils.math;

import org.um.dke.titan.domain.Vector3D;
import org.um.dke.titan.interfaces.Vector3dInterface;

public class NewtonRaphson {
    private static int JACOBIAN_MATRIX_SIZE = 3;

    private double h;
    private Matrix jacobian;

    public NewtonRaphson(double h) {
        this.h = h;

        jacobian = new Matrix(JACOBIAN_MATRIX_SIZE, JACOBIAN_MATRIX_SIZE);
    }

    public Vector3dInterface execute(Vector3dInterface g, Vector3dInterface v) {
        fill(g, v);
        Matrix inverse = jacobian.inverse();

        Matrix gM = Matrix.fromVector(g);

        Matrix product = inverse.multiply(gM);

        Matrix vM = Matrix.fromVector(v);

        return vM.substract(product).toVector();
    }

    /**
     * Fill the Jacobian Matrix using g'(V(k)) = (gx(Vx + h) - gx(Vx - h)) / 2h
     *
     * @param g - gx for being g(V(k))
     * @param v - Vx for being velocity
     */
    private void fill(Vector3dInterface g, Vector3dInterface v) {
        Vector3dInterface gx = new Vector3D(g.getX(), g.getX(), g.getX());
        Vector3dInterface gy = new Vector3D(g.getY(), g.getY(), g.getY());
        Vector3dInterface gz = new Vector3D(g.getZ(), g.getZ(), g.getZ());

        Vector3dInterface xPlus = new Vector3D(v.getX() + h, v.getY(), v.getZ());
        Vector3dInterface xMin = new Vector3D(v.getX() - h, v.getY(), v.getZ());
        Vector3dInterface yPlus = new Vector3D(v.getX(), v.getY() + h, v.getZ());
        Vector3dInterface yMin = new Vector3D(v.getX(), v.getY() - h, v.getZ());
        Vector3dInterface zPlus = new Vector3D(v.getX(), v.getY(), v.getZ() + h);
        Vector3dInterface zMin = new Vector3D(v.getX(), v.getY(), v.getZ() - h);

        jacobian.setValueAt(0, 0, jacobianValue(xPlus, xMin, gx, h));
        jacobian.setValueAt(0, 1, jacobianValue(yPlus, yMin, gx, h));
        jacobian.setValueAt(0, 2, jacobianValue(zPlus, zMin, gx, h));
        jacobian.setValueAt(1, 0, jacobianValue(xPlus, xMin, gy, h));
        jacobian.setValueAt(1, 1, jacobianValue(yPlus, yMin, gy, h));
        jacobian.setValueAt(1, 2, jacobianValue(zPlus, zMin, gy, h));
        jacobian.setValueAt(2, 0, jacobianValue(xPlus, xMin, gz, h));
        jacobian.setValueAt(2, 1, jacobianValue(yPlus, yMin, gz, h));
        jacobian.setValueAt(2, 2, jacobianValue(zPlus, zMin, gz, h));
    }

    private double product(Vector3dInterface a, Vector3dInterface b) {
        return a.getX() * b.getX() + a.getY() * b.getY() + a.getZ() * b.getZ();
    }

    private double jacobianValue(Vector3dInterface xP, Vector3dInterface xM, Vector3dInterface g, double h) {
        return (product(xP, g) - product(xM, g)) / (2*h);
    }
}
