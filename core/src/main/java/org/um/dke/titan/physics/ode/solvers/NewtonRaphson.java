package org.um.dke.titan.physics.ode.solvers;

import org.um.dke.titan.domain.Vector3D;
import org.um.dke.titan.factory.FactoryProvider;
import org.um.dke.titan.interfaces.StateInterface;
import org.um.dke.titan.interfaces.Vector3dInterface;
import org.um.dke.titan.physics.ProbeSimulator;
import org.um.dke.titan.physics.ode.functions.solarsystemfunction.SystemState;
import org.um.dke.titan.utils.Matrix3;

import java.util.Random;

import static java.lang.Double.NaN;

/** test class for computing example multivariable root finding problems
 *  V(n+1) = V(n) - [J]-1 * F(x)
 *
 *  trying to solve :
 *
 *      { 3x   - cos(y*z) - 3/2
 *  F = { 4x^2 - 625*y^2  + 2*z - 1
 *      { 20*z + e^-x*y   + 9
 *
 * @author Daan
 * src: http://fourier.eng.hmc.edu/e176/lectures/NM/node21.html
 *      https://math.stackexchange.com/questions/728666/calculate-jacobian-matrix-without-closed-form-or-analytical-form
 *
 */


public class NewtonRaphson {

    static double h = 500;
    static double tf = 60 * 60 * 24 * 450;


    static ProbeSimulator probeSimulator = new ProbeSimulator();
    static StateInterface[] timeLineArray = FactoryProvider.getSolarSystemRepository().getTimeLineArray();
    static Vector3D pStart;

    /**
     * runs V(n+1) = V(n) - [J]-1 * F(x) where:
     * o V is a vector.
     * o J is the jacobian matrix.
     * o F is the column matrix containing all functions f(x1 ... xn).
     *   in the case of the probe, that means the coordinate of the probe (x,y,z)
     */
    public static Vector3dInterface get(Vector3dInterface position, Vector3dInterface destination) {
        System.out.println("running newton raphson:");
        double e = 1e-3; // error convergence bound;
        pStart = (Vector3D) position;


        // x1 can be whatever just to initialize, but distance between x1 and xPrev > e
        // for the loop to be able to start
        Vector3D x1 = new Vector3D(1,2,3); // x(n+1)
        Vector3D x = (Vector3D) randomVector(); // initial guess
        Vector3D xPrev = x;   //x(n-1)

        for (int i = 0; i < 200; i++) {
            double [][] jInverse = Matrix3.inverse(getJacobian(x));
            Vector3D F = (Vector3D) F(x);

            x1 = (Vector3D) x.sub(Matrix3.multiply(jInverse, F));

            xPrev = x;
            x = x1;
            System.out.print("x1: " + getMinDistanceToTitan(x1).norm() + ". x: " + F.norm());
            double error = x1.sub(xPrev).norm();
            System.out.print("  ||  Error: " + error);
            System.out.println("  ||  x1: " + x1);

            if (error < e) {
                return x1;
            }

            if (x1.getX() == NaN) {
                x = (Vector3D) randomVector();
            }
        }

        return x1;
    }

    static Vector3D getMinDistanceToTitan(Vector3dInterface startVelocity) {
        Vector3D[] probeArray =  (Vector3D[]) probeSimulator.trajectory(pStart, startVelocity, tf, h);
        String planetname = "Titan";
        Vector3D min = (Vector3D) ((SystemState)timeLineArray[0]).getPlanet(planetname).getPosition().sub(probeArray[0]).sub(new Vector3D(2574700 + 300000, 0, 0));

        for (int i = 0; i < probeArray.length; i++) {
            Vector3D probePos = probeArray[i];
            Vector3D planetPos = (Vector3D) ((SystemState)timeLineArray[i]).getPlanet(planetname).getPosition().sub(new Vector3D(2574700 + 300000, 0, 0));

            if (min.norm() > probePos.dist(planetPos)) {
                min = (Vector3D) planetPos.sub(probePos);
            }
        }
        return min;
    }

    /**
     * column matrix containing all functions f(x1 ... xn)
     */
    static Vector3dInterface F(Vector3dInterface x) {
        return getMinDistanceToTitan(x);
    }


    /**
     * returns the jacobi matrix with the partial derivatives
     * filled in with the values of vector x
     */
    static double[][] getJacobian(Vector3dInterface v) {
        double [][] J = new double[3][3];

        double h = 1;

        Vector3D xPlusH = new Vector3D(v.getX() + h, v.getY(), v.getZ());
        Vector3D xMinusH = new Vector3D(v.getX() - h, v.getY(), v.getZ());
        Vector3D yPlusH = new Vector3D(v.getX(), v.getY() + h, v.getZ());
        Vector3D yMinusH = new Vector3D(v.getX(), v.getY() - h, v.getZ());
        Vector3D zPlusH = new Vector3D(v.getX(), v.getY(), v.getZ() + h);
        Vector3D zMinusH = new Vector3D(v.getX(), v.getY(), v.getZ() - h);

        J[0][0] =  (F(xPlusH).getX() - F(xMinusH).getX()) / 2*h;
        J[0][1] =  (F(yPlusH).getX() - F(yMinusH).getX()) / 2*h;
        J[0][2] =  (F(zPlusH).getX() - F(zMinusH).getX()) / 2*h;

        J[1][0] =  (F(xPlusH).getY() - F(xMinusH).getY()) / 2*h;
        J[1][1] =  (F(yPlusH).getY() - F(yMinusH).getY()) / 2*h;
        J[1][2] =  (F(zPlusH).getY() - F(zMinusH).getY()) / 2*h;

        J[2][0] =  (F(xPlusH).getZ() - F(xMinusH).getZ()) / 2*h;
        J[2][1] =  (F(yPlusH).getZ() - F(yMinusH).getZ()) / 2*h;
        J[2][2] =  (F(zPlusH).getZ() - F(zMinusH).getZ()) / 2*h;

        return J;
    }

    static Vector3dInterface randomVector() {
        Random r = new Random();

        double x = r.nextDouble()* - r.nextDouble();
        double y = r.nextDouble()* - r.nextDouble();
        double z = r.nextDouble()* - r.nextDouble();

        return new Vector3D(x,y,z).mul(r.nextInt(60000));
    }
}
