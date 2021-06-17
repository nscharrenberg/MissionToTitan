package org.um.dke.titan.physics.ode.solvers;

import org.um.dke.titan.domain.Vector3D;
import org.um.dke.titan.interfaces.Vector3dInterface;
import org.um.dke.titan.physics.ProbeSimulator;
import org.um.dke.titan.physics.ode.functions.solarsystemfunction.PlanetState;
import org.um.dke.titan.utils.Matrix4;

import java.util.Random;

/** test class for computing example multivariable root finding problems
 *  V(n+1) = V(n) - [J]-1 * F(x)
 *
 *
 * @author Daan
 * src: http://fourier.eng.hmc.edu/e176/lectures/NM/node21.html
 *      https://math.stackexchange.com/questions/728666/calculate-jacobian-matrix-without-closed-form-or-analytical-form
 *
 */


public class NewtonRaphson2 {

    static double h = 500;
    static double tf = 60 * 60 * 24 * 450;


    static ProbeSimulator probeSimulator = new ProbeSimulator();
    static Vector3D pStart;

    static Vector3D destinationPoint;

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
        destinationPoint = (Vector3D) destination;


        // x1 can be whatever just to initialize, but distance between x1 and xPrev > e
        // for the loop to be able to start
        Vector4D x1 = new Vector4D(1,2,3, 20000); // x(n+1)
        Vector4D x = randomVector(); // initial guess
        Vector4D xPrev = x;   //x(n-1)

        for (int i = 0; i < 20000; i++) {
            double[][] J = getJacobian(x);
            double [][] jInverse = Matrix4.inverse(getJacobian(x));

            x1 = x.sub(Matrix4.multiply(jInverse, F(x) ));

            xPrev = x;
            x = x1;

            double error = x1.sub(xPrev).norm();

            //if ( F(x).getV() < 60000)
            {
                System.out.print("x1: " + getMinDistanceToDestination(x1, destination).norm() + ". x: " + F(x).norm());
                System.out.print("  ||  Error: " + error);
                System.out.println("  ||  x1: " + x1);
            }

            if (error < e) {
                return x1;
            }

            if (Double.isNaN(x1.getX())) {
                x = randomVector();
            }
        }

        return x1;
    }

    static Vector4D getMinDistanceToDestination(Vector3dInterface startVelocity, Vector3dInterface destination) {
        PlanetState[] probeArray =  probeSimulator.stateTrajectory(pStart, startVelocity, tf, h);
        Vector4D min = new Vector4D(destination.sub(probeArray[0].getPosition()), startVelocity.norm());

        for (int i = 0; i < probeArray.length; i++) {
            PlanetState probe = probeArray[i];
            Vector3D planetPos = (Vector3D) destination;

            if (min.norm() > probe.getPosition().dist(planetPos)) {
                min = new Vector4D(planetPos.sub(probe.getPosition()), probe.getVelocity().norm());
            }
        }
         return min;
    }

    /**
     * column matrix containing all functions f(x1 ... xn)
     */
    static Vector4D F(Vector4D x) {
        Vector3D earthVelocity = new Vector3D(5.427193405797901e+03, -2.931056622265021e+04, 6.575428158157592e-01);
        Vector4D result = getMinDistanceToDestination(x.getUnit().mul(x.getV()).add(earthVelocity), destinationPoint);
        result.setV(result.getV() - 50000);
        return result;
    }


    /**
     * returns the jacobi matrix with the partial derivatives
     * filled in with the values of vector x
     */
    static double[][] getJacobian(Vector4D v) {
        double [][] J = new double[4][4];

        double h = 0.3;

        Vector4D xPlusH  = new Vector4D(new Vector3D(v.getX() + h, v.getY(), v.getZ()).getUnit(), v.getV());
        Vector4D xMinusH = new Vector4D(new Vector3D(v.getX() - h, v.getY(), v.getZ()).getUnit(), v.getV());
        Vector4D yPlusH  = new Vector4D(new Vector3D(v.getX(), v.getY() + h, v.getZ()).getUnit(), v.getV());
        Vector4D yMinusH = new Vector4D(new Vector3D(v.getX(), v.getY() - h, v.getZ()).getUnit(), v.getV());
        Vector4D zPlusH  = new Vector4D(new Vector3D(v.getX(), v.getY(), v.getZ() + h).getUnit(), v.getV());
        Vector4D zMinusH = new Vector4D(new Vector3D(v.getX(), v.getY(), v.getZ() - h).getUnit(), v.getV());
        Vector4D vPlusH =  new Vector4D(new Vector3D(v.getX(), v.getY(), v.getZ()).getUnit(), v.getV() + h);
        Vector4D vMinusH = new Vector4D(new Vector3D(v.getX(), v.getY(), v.getZ()).getUnit(), v.getV() - h);

        J[0][0] =  (F(xPlusH).getX() - F(xMinusH).getX()) / 2*h;
        J[0][1] =  (F(yPlusH).getX() - F(yMinusH).getX()) / 2*h;
        J[0][2] =  (F(zPlusH).getX() - F(zMinusH).getX()) / 2*h;
        J[0][3] =  (F(vPlusH).getX() - F(vMinusH).getX()) / 2*h;

        J[1][0] =  (F(xPlusH).getY() - F(xMinusH).getY()) / 2*h;
        J[1][1] =  (F(yPlusH).getY() - F(yMinusH).getY()) / 2*h;
        J[1][2] =  (F(zPlusH).getY() - F(zMinusH).getY()) / 2*h;
        J[1][3] =  (F(vPlusH).getY() - F(vMinusH).getY()) / 2*h;

        J[2][0] =  (F(xPlusH).getZ() - F(xMinusH).getZ()) / 2*h;
        J[2][1] =  (F(yPlusH).getZ() - F(yMinusH).getZ()) / 2*h;
        J[2][2] =  (F(zPlusH).getZ() - F(zMinusH).getZ()) / 2*h;
        J[2][3] =  (F(vPlusH).getZ() - F(vMinusH).getZ()) / 2*h;

        J[3][0] =  (F(xPlusH).getV() - F(xMinusH).getV()) / 2*h;
        J[3][1] =  (F(yPlusH).getV() - F(yMinusH).getV()) / 2*h;
        J[3][2] =  (F(zPlusH).getV() - F(zMinusH).getV()) / 2*h;
        J[3][3] =  (F(vPlusH).getV() - F(vMinusH).getV()) / 2*h;

        return J;
    }

    static Vector4D randomVector() {
        Random r = new Random();

        double x = r.nextDouble()* - r.nextDouble();
        double y = r.nextDouble()* - r.nextDouble();
        double z = r.nextDouble()/6* - r.nextDouble()/6;
        double v = r.nextDouble()*60000;

        Vector3D unit = new Vector3D(x,y,z);

        return new Vector4D(unit.getUnit(),v);
    }

}
