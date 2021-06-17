package org.um.dke.titan.utils;

import org.um.dke.titan.domain.Vector3D;
import org.um.dke.titan.interfaces.Vector3dInterface;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class Triangulation {
    public static final double SIDE_LENGTH = 15.00;

    public static Vector3dInterface calculateDistance(Vector3dInterface center, double radian, double y) {
        //1. find the two corners the position lies between
        //2. calculate the
    }

    /**
     * Calculates the y positions that can be reached by wind from the indicated side,
     * IMPORTANT: corners have to be provided in rotated form
     * @param center Center point of the square
     * @param corners Corner points of the square
     * @param @param degree Angle in degree
     * @param left If true calculates for the left side, if false for the right side
     * @return
     */
    public static double[] exposedSideD(Vector3dInterface center, Vector3dInterface[] corners, double degree, boolean left) {
        return exposedSide(center, corners, degreeToRadian(degree), left);
    }

    /**
     * Calculates the y positions that can be reached by wind from the indicated side,
     * IMPORTANT: corners have to be provided in rotated form
     * @param center Center point of the square
     * @param corners Corner points of the square
     * @param radian Angle in radians
     * @param left If true calculates for the left side, if false for the right side
     * @return
     */
    public static double[] exposedSide(Vector3dInterface center, Vector3dInterface[] corners, double radian, boolean left) {
        //sort by x coordinate
        Collections.sort(Arrays.asList(corners), new Comparator<Vector3dInterface>() {
            public int compare(Vector3dInterface o1, Vector3dInterface o2) {
                double x1 = o1.getX(), x2 = o2.getX();
                if(x1 < x2)
                    return -1;
                if(x1 > x2)
                    return 1;
                return 0;
            }
        });
        double[] exposed = new double[2];
        if(radian % Math.PI/2.0 == 0) {// square stands upright
            exposed[0] = corners[0].getY();
            exposed[1] = corners[1].getY();
        } else { //square is at an angle
            Comparator<Vector3dInterface> yComp = new Comparator<Vector3dInterface>() {
                public int compare(Vector3dInterface o1, Vector3dInterface o2) {
                    double y1 = o1.getY(), y2 = o2.getY();
                    if(y1 < y2)
                        return -1;
                    if(y1 > y2)
                        return 1;
                    return 0;
                }
            };
            Vector3dInterface[] points = null;
            if(left) { //exposure from left side
                points = new Vector3dInterface[]{corners[0], corners[1], corners[2]};
            } else { //exposure from the right side
                points = new Vector3dInterface[]{corners[1], corners[2], corners[3]};
            }
            Collections.sort(Arrays.asList(points), yComp);
            exposed[0] = points[0].getY();
            exposed[1] = points[2].getY();
        }
        return exposed;
    }

    /**
     * Calculates the absolute positions of the four corners in space
     * @param center The center point of the square
     * @param radian The angle in radian by which the square has been rotated
     * @return An array of vectors with the positions of the four corners
     */
    public static Vector3dInterface[] calculateCorners(Vector3dInterface center, double radian) {
        double cx = center.getX(), cy = center.getY();
        double halfSide = SIDE_LENGTH/2.0;
        Vector3dInterface[] oldCorners = new Vector3D[]{
                new Vector3D(cx - halfSide, cy - halfSide, 0), //top left
                new Vector3D(cx + halfSide, cy - halfSide, 0), //top right
                new Vector3D(cx + halfSide, cy + halfSide, 0), //bottom right
                new Vector3D(cx - halfSide, cy + halfSide, 0)  //bottom left
        };
        Vector3dInterface[] rotatedCorners = new Vector3D[4];
        int i = 0;
        for(Vector3dInterface v : oldCorners) {
            rotatedCorners[i] = rotateAroundCenter(center, v, radian);
            i++;
        }
        return rotatedCorners;
    }

    /**
     * Calculates the absolute positions of the four corners in space
     * @param center The center point of the square
     * @param degree The angle in radian by which the square has been rotated
     * @return An array of vectors with the positions of the four corners
     */
    public static Vector3dInterface[] calculateCornersD(Vector3dInterface center, double degree) {
        return calculateCorners(center, degreeToRadian(degree));
    }

    /**
     * Converts from degrees to radian
     * @param degree Angle in degrees
     * @return Angle in radians
     */
    public static double degreeToRadian(double degree) {
        return degree * (Math.PI/180.0);
    }

    /**
     * Converts from radians to degrees
     * @param radian Angle in radians
     * @return Angle in degrees
     */
    public static double radianToDegree(double radian) {
        return radian * (180.0/Math.PI);
    }

    /**
     * Rotates a point around a center point
     * @param p The point to be rotated
     * @param c The center point to be rotated around
     * @param degree The angle in degrees to rotate the point around
     * @return The rotated point
     */
    public static Vector3dInterface rotateAroundCenterD(Vector3dInterface p, Vector3dInterface c, double degree) {
        //translate to 0,0
        Vector3dInterface translated = new Vector3D(p.getX() - c.getX(), p.getY() - c.getY(), 0);
        //rotate by degrees
        Vector3dInterface rotated = new Vector3D(translated.getX() * Math.cos(degree) - translated.getY() * Math.sin(degree),
                translated.getX() * Math.sin(degree) + translated.getY() * Math.cos(degree), 0);
        //translate back
        Vector3dInterface translatedBack = new Vector3D(rotated.getX() + c.getX(), rotated.getY() + c.getY(), 0);
        return translatedBack;
    }

    /**
     * Rotates a point around a center point
     * @param p The point to be rotated
     * @param c The center point to be rotated around
     * @param radian The angle in degrees to rotate the point around
     * @return The rotated point
     */
    public static Vector3dInterface rotateAroundCenter(Vector3dInterface p, Vector3dInterface c, double radian) {
        return rotateAroundCenterD(p, c, radianToDegree(radian));
    }
}
