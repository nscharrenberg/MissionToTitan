package org.um.dke.titan.utils;

import org.um.dke.titan.domain.Vector3D;
import org.um.dke.titan.interfaces.Vector3dInterface;

public class RotationMatrix {
    /**
     * Rotates any given vector in 2d by degrees counterclockwise
     * @param degrees to turn clockwise
     * @return a turned vector
     */
    public static Vector3dInterface rotate(double degrees, double x, double y){
        double d = degrees * Math.PI/180.0;//conversion degrees to radian
        System.out.println("DEG"+d);
        double[][] m = rotationMatrix(d);
        double newX = x * m[0][0] + y * m[0][1];
        double newY = x * m[1][0] + y * m[1][1];
        Vector3dInterface newV = new Vector3D(newX, newY, 0);
        return newV;
    }

    /**
     * Creates a 2-dimensional clockwise rotation matrix for any given degrees
     * @param degrees
     * @return rotation matrix fo the degrees
     */
    public static double[][] rotationMatrix(double degrees) {
        double[][] m = new double[2][2];
        if(Math.abs(degrees) == 360)
            degrees = 0;
        degrees *= -1;
        m[0][0] = Math.cos(degrees);
        m[0][1] = -1* Math.sin(degrees);
        m[1][0] = Math.sin(degrees);
        m[1][1] = m[0][0];
        return m;
    }
}
