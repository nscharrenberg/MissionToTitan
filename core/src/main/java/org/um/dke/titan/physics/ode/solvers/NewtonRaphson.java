package org.um.dke.titan.physics.ode.solvers;

import org.um.dke.titan.domain.Vector3D;
import org.um.dke.titan.factory.FactoryProvider;
import org.um.dke.titan.interfaces.StateInterface;
import org.um.dke.titan.interfaces.Vector3dInterface;
import org.um.dke.titan.physics.ProbeSimulator;
import org.um.dke.titan.physics.ode.functions.solarsystemfunction.PlanetRate;
import org.um.dke.titan.physics.ode.functions.solarsystemfunction.PlanetState;
import org.um.dke.titan.physics.ode.functions.solarsystemfunction.SystemState;
import org.um.dke.titan.utils.Matrix3;

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


public class NewtonRaphson {
    static double h = 500;
    static double tf = 60 * 60 * 24 * 1500;

    static ProbeSimulator probeSimulator = new ProbeSimulator();
    static StateInterface[] timeLineArray = FactoryProvider.getSolarSystemRepository().getTimeLineArray();
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
        Vector3D x1 = new Vector3D(1,2,3); // x(n+1)
        Vector3D x = (Vector3D) randomVector(); // initial guess
        Vector3D xPrev = x;   //x(n-1)

        for (int i = 0; i < 20000; i++) {
            double [][] jInverse = Matrix3.inverse(getJacobian(x));

            x1 = (Vector3D) x.sub(Matrix3.multiply(jInverse, F(x)));

            xPrev = x;
            x = x1;

            Vector3dInterface fx1 = F(x1);
            double error = fx1.sub(F(xPrev)).norm();
            FactoryProvider.getTestRepository().checkAndPrint(((F(x1).norm()) - 2575.5e3), x1, error,  F(xPrev), fx1);

            if (error < e) {
                return x1;
            }

            if (Double.isNaN(x1.getX())) {
                x = (Vector3D) randomVector();
            }
        }

        return x1;
    }

    static Vector3D getMinDistanceToDestination(Vector3dInterface startVelocity, Vector3dInterface destination) {
        Vector3D[] probeArray =  (Vector3D[]) probeSimulator.trajectory(pStart, startVelocity, tf, h);
        Vector3D min = (Vector3D) destination.sub(probeArray[0]);

        for (int i = 0; i < probeArray.length; i++) {
            Vector3D probePos = probeArray[i];
            Vector3D planetPos = (Vector3D) ((SystemState)timeLineArray[i]).getPlanet("Titan").getPosition();

            if (min.norm() > probePos.dist(planetPos)) {
                min = (Vector3D) planetPos.sub(probePos);
            }
        }
        return min;
    }

    /**
     * column matrix containing all functions f(x1 ... xn)
     */
    public static Vector3dInterface F(Vector3dInterface x) {
        Vector3D earthVelocity = new Vector3D(5.427193405797901e+03, -2.931056622265021e+04, 6.575428158157592e-01);
        Vector3D unit = (Vector3D) x.mul(1/x.norm());
        x = unit.mul(45000);
        return getMinDistanceToDestination(x.add(earthVelocity), destinationPoint);
    }


    /**
     * returns the jacobi matrix with the partial derivatives
     * filled in with the values of vector x
     */
    static double[][] getJacobian(Vector3dInterface v) {
        double [][] J = new double[3][3];

        double h = 2;

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
        double z = r.nextDouble()/10* - r.nextDouble()/10;

        Vector3D rand = new Vector3D(x,y,z);

        return rand.mul(1/rand.norm());
    }

}
