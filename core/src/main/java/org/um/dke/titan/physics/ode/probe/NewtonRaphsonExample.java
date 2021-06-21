package org.um.dke.titan.physics.ode.probe;

import org.um.dke.titan.domain.Vector3D;
import org.um.dke.titan.interfaces.Vector3dInterface;
import org.um.dke.titan.utils.Matrix3;

/** test class for computing example multivariable root finding problems
 *  V(n+1) = V(n) - [J]-1 * F(x)
 *
 *  trying to solve :
 *
 *      { 3x   - cos(y*z) - 3/2
 *  F = { 4x^2 - 625*y^2  + 2*z - 1
 *      { 20*z + e^-x*y   + 9
 *
 */


public class NewtonRaphsonExample {

    public static void main(String[] args) {
        System.out.println(get());
    }

    /**
     * runs V(n+1) = V(n) - [J]-1 * F(x) where:
     * o V is a vector.
     * o J is the jacobian matrix.
     * o F is the column matrix containing all functions f(x1 ... xn).
     *   in the case of the probe, that means the coordinate of the probe (x,y,z)
     */
    public static Vector3dInterface get() {
        double e = 1e-9; // error convergence bound;


        // x1 can be whatever just to initialize, but distance between x1 and xPrev > e
        // for the loop to be able to start
        Vector3D x1 = new Vector3D(1,2,3); // x(n+1)

        Vector3D x = new Vector3D(1,1,1); // initial guess
        Vector3D xPrev = x;   //x(n-1)

        while(x1.sub(xPrev).norm() > e) {
            double [][] jInverse = Matrix3.inverse(getJacobian(x));
            x1 = (Vector3D) x.sub(Matrix3.multiply(jInverse, F(x)));

            xPrev = x;
            x = x1;
        }

        return x1;
    }

    /**
     * column matrix containing all functions f(x1 ... xn)
     */
    static Vector3dInterface F(Vector3dInterface vector) {
        double x1 = vector.getX();
        double x2 = vector.getY();
        double x3 = vector.getZ();

        double x = 3.0 * x1 - Math.cos(x2 * x3) - 3.0/2.0;
        double y = 4.0 * (x1*x1) - 625.0 * (x2*x2) + 2*x3 - 1.0;
        double z = 20 * x3 + Math.exp(-x1*x2) + 9;

        return new Vector3D(x,y,z);
    }


    /**
     * returns the jacobi matrix wi th the partial derivatives
     * filled in with the values of vector x
     */
    static double[][] getJacobian(Vector3D x) {
        double [][] J = new double[3][3];

        double x1 = x.getX();
        double x2 = x.getY();
        double x3 = x.getZ();

        J[0][0] = 3.0;
        J[0][1] = x3 * Math.sin(x2 * x3);
        J[0][2] = x2 * Math.sin(x2 * x3);

        J[1][0] = 8.0 * x1;
        J[1][1] = -1250.0 * x2;
        J[1][2] = 2.0;

        J[2][0] = -2.0 * Math.exp(-x1 * x2);
        J[2][1] = -x1 * Math.exp(-x1 * x2);
        J[2][2] = 20.0;

        return J;
    }
}
