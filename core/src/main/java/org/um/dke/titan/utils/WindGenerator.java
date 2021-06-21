package org.um.dke.titan.utils;

import org.um.dke.titan.domain.Vector3D;
import org.um.dke.titan.interfaces.Vector3dInterface;

import java.util.Arrays;

public class WindGenerator {
    private final double maxForce;
    private double[] param, paramDerivative;
    private final double dt;
    private  double t;

    public WindGenerator(double maxForce, double dt) {
        this.dt = dt;
        t = 0;
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
     * @param center Center point of the square
     * @param angle Angle at which the square is rotated
     * @return An array with the force vector and a distance from the center vector
     */
    public Vector3dInterface[] getWind(Vector3dInterface center, double angle) {
        Vector3dInterface[] output = new Vector3dInterface[2];
        //need x, random force
        double f = Math.sin(t)*0.01;//evalPolyInRange(t, param, -10, 10);
        boolean left = isLeft(f);
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
        t += dt;
        return output;
    }

    public static double evalPolyInRange(double x, double[] param, double min, double max) {
        double y = SquareHandling.evalPoly(param, x);
        return ((x - min)/(max - min));
    }

    public static boolean isLeft(double f) {
        if(f > 0) {
            return true;
        } else {// force <= 0
            return false;
        }
    }

    /**
     * This method takes any angle and formats it into a value between 0 and 2* pi
     * @param radians
     * @return
     */
    public static double formatAngle(double radians) {
        return Math.abs(radians % (Math.PI * 2));
    }
}
