package org.um.dke.titan.utils;

import com.badlogic.gdx.math.Vector3;
import org.um.dke.titan.interfaces.Vector3dInterface;

public class VectorConverter {
    /**
     * Convert float values to a Vector3 libgdx object.
     * @param x - the x-coordinate
     * @param y - the y-coordinate
     * @param z - the z-coordinate
     * @return the Vector3 object
     */
    public static Vector3 convertToVector3(float x, float y, float z) {
        return new Vector3(x, y, z);
    }

    /**
     * Convert the Vector3DInterface to a Vector3 libgdx object
     * @param vector3dInterface - the Vector3DInterface object with the coordinates
     * @return the Vector3 object
     */
    public static Vector3 convertToVector3(Vector3dInterface vector3dInterface) {
        return new Vector3((float) vector3dInterface.getX(), (float)vector3dInterface.getY(), (float)vector3dInterface.getZ());
    }

    /**
     * Convert double values to a Vector3 libgdx object.
     * @param x - the x-coordinate
     * @param y - the y-coordinate
     * @param z - the z-coordinate
     * @return the Vector3 object
     */
    public static Vector3 convertToVector3(double x, double y, double z) {
        return new Vector3((float)x, (float)y, (float)z);
    }
}
