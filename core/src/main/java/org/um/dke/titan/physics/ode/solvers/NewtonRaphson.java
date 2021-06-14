package org.um.dke.titan.physics.ode.solvers;

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


public class NewtonRaphson {

    static double h = 50;

    /**
     * runs V(n+1) = V(n) - [J]-1 * F(x) where:
     * o V is a vector.
     * o J is the jacobian matrix.
     * o F is the column matrix containing all functions f(x1 ... xn).
     *   in the case of the probe, that means the coordinate of the probe (x,y,z)
     */
    public static Vector3dInterface get(Vector3dInterface position, Vector3dInterface destination, Vector3dInterface velocity) {
        double e = 1e-3; // error convergence bound;


        // x1 can be whatever just to initialize, but distance between x1 and xPrev > e
        // for the loop to be able to start
        Vector3D x1 = new Vector3D(1,2,3); // x(n+1)

        Vector3D x = (Vector3D) velocity; // initial guess
        Vector3D xPrev = x;   //x(n-1)

        //while(x1.sub(xPrev).norm() > e) {
            double [][] jInverse = Matrix3.inverse(getJacobian(position, velocity));
            Vector3D F = (Vector3D) destination.sub(position);

            x1 = (Vector3D) x.sub(Matrix3.multiply(jInverse, F));

            //System.out.println("x = " + x);

            xPrev = x;
            x = x1;
            //System.out.println("x1 = " + x1);
            //System.out.println("error: " + x1.sub(xPrev).norm());
        //}

        return x1.mul(-1);
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
     * returns the jacobi matrix with the partial derivatives
     * filled in with the values of vector x
     */
    static double[][] getJacobian(Vector3dInterface g, Vector3dInterface v) {
        double [][] J = new double[3][3];

        /**
         * TODO: COPIED, PLEASE DO NOT LEAVE IT LIKE THIS DAAN! (that's me)
         */

        Vector3D xPlusH = new Vector3D(v.getX() + h, v.getY(), v.getZ());
        Vector3D xMinusH = new Vector3D(v.getX() - h, v.getY(), v.getZ());
        Vector3D yPlusH = new Vector3D(v.getX(), v.getY() + h, v.getZ());
        Vector3D yMinusH = new Vector3D(v.getX(), v.getY() - h, v.getZ());
        Vector3D zPlusH = new Vector3D(v.getX(), v.getY(), v.getZ() + h);
        Vector3D zMinusH = new Vector3D(v.getX(), v.getY(), v.getZ() - h);
        Vector3dInterface gX = new Vector3D(g.getX(), g.getX(), g.getX());
        Vector3dInterface gY = new Vector3D(g.getY(), g.getY(), g.getY());
        Vector3dInterface gZ = new Vector3D(g.getZ(), g.getZ(), g.getZ());

        J[0][0] =  (xPlusH.innerProduct(gX) - xMinusH.innerProduct(gX)) / 2*h;
        J[0][1] =  (yPlusH.innerProduct(gX) - yMinusH.innerProduct(gX)) / 2*h;
        J[0][2] =  (zPlusH.innerProduct(gX) - zMinusH.innerProduct(gX)) / 2*h;
        J[1][0] =  (xPlusH.innerProduct(gY) - xMinusH.innerProduct(gY)) / 2*h;
        J[1][1] =  (yPlusH.innerProduct(gY) - yMinusH.innerProduct(gY)) / 2*h;
        J[1][2] =  (zPlusH.innerProduct(gY) - zMinusH.innerProduct(gY)) / 2*h;
        J[2][0] =  (xPlusH.innerProduct(gZ) - xMinusH.innerProduct(gZ)) / 2*h;
        J[2][1] =  (yPlusH.innerProduct(gZ) - yMinusH.innerProduct(gZ)) / 2*h;
        J[2][2] =  (zPlusH.innerProduct(gZ) - zMinusH.innerProduct(gZ)) / 2*h;

        return J;
    }
}
