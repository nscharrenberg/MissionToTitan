package org.um.dke.titan.utils;

import org.um.dke.titan.domain.Vector3D;
import org.um.dke.titan.interfaces.Vector3dInterface;

public class WindGenerator {
    private final double maxForce;
    private double[] param, paramDerivative;

    public WindGenerator(double maxForce) {
        this.maxForce = maxForce;
        param = new double[]{
                /*x0*/12,
                /*x1*/0.7,
                /*x2*/4,
                /*x3*/1.1,
                /*x4*/89
        };
        paramDerivative = new double[param.length - 1];
        for(int i = 1; i < param.length; i++) {
            paramDerivative[i - 1] = param[i] * i;
        }
    }

    /**
     * Calculates the force and point at which the wind strikes the lander
     * at the given time point t.
     * Both the size of the form and the angle of the force are determined deterministically,
     * the point of impact is generated at random between the highest and lowest exposed point
     * that is reachable from the side that the wind is coming in from.
     * The output has the following order:
     * [0] force
     * [1] distance to center
     * @param t Time at which the wind function should be evaluated
     * @param center Center point of the square
     * @param angle Angle at which the square is rotated
     * @return An array with the force vector and a distance from the center vector
     */
    public Vector3dInterface[] getWind(double t, Vector3dInterface center, double angle) {
        Vector3dInterface[] output = new Vector3dInterface[2];
        //need x, random force
        double f = evalPolyInRange(t, param, -10, 10);
        boolean left;
        if(f > 0) {
            left = true;
        } else {// force <= 0
            left = false;
        }
        Vector3dInterface[] corners = SquareHandling.calculateCorners(center, angle);
        double[] interval = SquareHandling.exposedSide(center, corners, angle, left);
        double y = SquareHandling.generateRandom(interval[0], interval[1]);
        double x = SquareHandling.calculateAccX(center, corners, left, y);
        Vector3dInterface force = new Vector3D(f, 0, 0);
        output[0] = force;
        Vector3dInterface dist = SquareHandling.calculateDist(center, x, y);
        output[1] = dist;
        //angle according to derivative
        //TODO : Make the wind (force vector) come in at a random angle between 90 (straight down) and -90 (straight up)
        return output;
    }

    public static double evalPolyInRange(double x, double[] param, double min, double max) {
        double y = SquareHandling.evalPoly(param, x);
        return ((x - min)/(max - min));
    }
}
