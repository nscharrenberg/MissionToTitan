package org.um.dke.titan.physics.ode.solvers;

import org.um.dke.titan.domain.Vector3D;
import org.um.dke.titan.factory.FactoryProvider;
import org.um.dke.titan.interfaces.StateInterface;
import org.um.dke.titan.interfaces.Vector3dInterface;
import org.um.dke.titan.physics.ProbeSimulator;
import org.um.dke.titan.physics.ode.functions.solarsystemfunction.PlanetRate;
import org.um.dke.titan.physics.ode.functions.solarsystemfunction.PlanetState;
import org.um.dke.titan.utils.Matrix3;

import java.util.Random;

/** test class for computing example multivariable root finding problems
 *  V(n+1) = V(n) - [J]-1 * F(x)
 *
 * @author Daan
 *
 * src: http://fourier.eng.hmc.edu/e176/lectures/NM/node21.html
 *      https://math.stackexchange.com/questions/728666/calculate-jacobian-matrix-without-closed-form-or-analytical-form
 *
 */


public class NewtonRaphsonProbe {

    static double h = 500;
    static double tf = 60 * 60 * 24 * 450;


    static final double probeMass = 120000;
    static Vector3D destinationPoint;
    static PlanetState probeState;

    /**
     * runs V(n+1) = V(n) - [J]-1 * F(x) where:
     * o V is a vector.
     * o J is the jacobian matrix.
     * o F is the column matrix containing all functions f(x1 ... xn).
     *   in the case of the probe, that means the coordinate of the probe (x,y,z)
     */
    public static Vector3dInterface get(PlanetState probe, Vector3dInterface destination) {
        double e = 1e-3; // error convergence bound;

        probeState = probe;
        destinationPoint = (Vector3D) destination;
        double speed = probe.getVelocity().norm();


        // x1 can be whatever just to initialize, but distance between x1 and xPrev > e
        // for the loop to be able to start
        Vector3D x1 = new Vector3D(1,2,3); // x(n+1)
        Vector3D x = new Vector3D(0,0,0); // initial guess
        Vector3D xPrev = x;   //x(n-1)

        for (int i = 0; i < 1; i++) {
            double [][] jInverse = Matrix3.inverse(getJacobian(x));

            x1 = (Vector3D) x.sub(Matrix3.multiply(jInverse, F(x)));

            xPrev = x;
            x = x1;

            double error = x1.sub(xPrev).norm();
            System.out.print("x1: " + F(x1).norm() + ". x: " + F(x).norm());
            System.out.print("  ||  Error: " + error);
            System.out.println("  ||  x1: " + x1);

            if (error < e) {
                System.out.println("x1 :   " + x1);
                return x1;
            }
        }

        System.out.println("x1 :FUCK   " + x1);
        return x1;
    }

    /**
     *  returns the difference between the unit vector of the probe velocity and the direction
     *  between the probe and the destination
     */
    static Vector3dInterface getDifference(Vector3dInterface x,PlanetState probe, Vector3dInterface destination) {

        PlanetState newState = step(probe, h, x);
        Vector3D distanceUnit = (Vector3D) destination.sub(newState.getPosition()).mul(1/(destination.sub(newState.getPosition()).norm()));
        Vector3D velocityUnit = (Vector3D)  newState.getVelocity().mul(1/newState.getVelocity().norm());
        Vector3D sum = (Vector3D) distanceUnit.add(velocityUnit);

        System.out.println(distanceUnit);
        System.out.println(velocityUnit);

        System.out.println("delta:   " + distanceUnit.sub(velocityUnit));
        return (sum.mul(1/sum.norm())).sub(velocityUnit);
    }


    /**
     * column matrix containing all functions f(x1 ... xn)
     */
    static Vector3dInterface F(Vector3dInterface x) {
        return getDifference(x, probeState, destinationPoint);
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








    private static PlanetRate call(double t, PlanetState y, Vector3dInterface addedForce) {
        Vector3dInterface rateAcceleration = y.getForce().add(addedForce).mul(1 / probeMass); // a = F/m
        Vector3dInterface rateVelocity = y.getVelocity().add(rateAcceleration.mul(t));
        return new PlanetRate(rateVelocity, rateAcceleration);
    }

    private static PlanetState step(PlanetState y, double h, Vector3dInterface force) {
        PlanetRate k1 = call(h, y, force).mul(h);
        PlanetRate k2 = call(0.5*h, y.addMul(0.5, k1), force).mul(h);
        PlanetRate k3 = call(0.5*h, y.addMul(0.5, k2), force).mul(h);
        PlanetRate k4 = call(h, y.addMul(1, k3), force).mul(h);
        return y.addMul(1/6d, k1.addMul(2, k2).addMul(2, k3).addMul(1, k4));
    }


}
