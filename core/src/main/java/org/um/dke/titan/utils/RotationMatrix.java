package org.um.dke.titan.utils;

import org.um.dke.titan.domain.Vector3D;
import org.um.dke.titan.interfaces.Vector3dInterface;

public class RotationMatrix {
    /**
     * Rotates any given vector in 2d by degrees counterclockwise
     * @param degrees to turn counterclockwise
     * @return a turned vector
     */
    public static Vector3dInterface rotate(double degrees, double width, double height){
        double[][] m = rotationMatrix(degrees);
        double x = width * m[0][0] + height * m[0][1];
        double y = width * m[1][0] + height * m[1][1];
        Vector3dInterface newV = new Vector3D(x, y, 0);
        return newV;
    }

    /**
     * Creates a 2-dimensional rotation matrix for any given degreees
     * @param degrees
     * @return rotation matrix fo the degrees
     */
    public static double[][] rotationMatrix(double degrees) {
        double[][] m = new double[2][2];
        degrees *= -1;
        m[0][0] = Math.cos(degrees);
        m[0][1] = Math.sin(degrees);
        m[1][0] = -1* m[0][1];
        m[1][1] = m[0][0];
        return m;
    }
}
