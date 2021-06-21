package org.um.dke.titan.utils;

import org.um.dke.titan.domain.Vector3D;
import org.um.dke.titan.interfaces.Vector3dInterface;

import java.util.*;

/**
 * This class contains all the logic to calculate the corners of a square with a center point in space,
 * rotate the square, rotate points around a center, calculate points on the perimeter of the square,
 * etc, etc, etc.
 * @author filip
 */
public class SquareHandling {
    public static final double SIDE_LENGTH = 150.00;
    /**
     * Comparator that sorts Vectors based on their x-coordinate
     */
    public static final Comparator<Vector3dInterface> xComp = new Comparator<Vector3dInterface>() {
        public int compare(Vector3dInterface o1, Vector3dInterface o2) {
            double x1 = o1.getX(), x2 = o2.getX();
            if(x1 < x2)
                return -1;
            if(x1 > x2)
                return 1;
            return 0;
        }
    };

    /**
     * Comparator that sorts Vectors based on their y-coordinate
     */
    public static final Comparator<Vector3dInterface> yComp = new Comparator<Vector3dInterface>() {
        public int compare(Vector3dInterface o1, Vector3dInterface o2) {
            double y1 = o1.getY(), y2 = o2.getY();
            if(y1 < y2)
                return -1;
            if(y1 > y2)
                return 1;
            return 0;
        }
    };

    //--------------------------RADIANS--------------------------------
    /**
     * Converts from radians to degrees
     * @param radian Angle in radians
     * @return Angle in degrees
     */
    public static double radianToDegree(double radian) {
        return radian * (180.0/Math.PI);
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
        Collections.sort(Arrays.asList(corners), xComp);
        double[] exposed = new double[2];
        if(radian % Math.PI/2.0 == 0) {// square stands upright
            exposed[0] = corners[0].getY();
            exposed[1] = corners[1].getY();
        } else { //square is at an angle
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
        for(int i = 0; i < oldCorners.length; i++) {
            Vector3dInterface v = oldCorners[i];
            rotatedCorners[i] = rotateAroundCenter(v, center, radian);
        }
        return rotatedCorners;
    }

    /**
     * Rotates a point around a center point
     * @param p The point to be rotated
     * @param c The center point to be rotated around
     * @param degree The angle in degrees to rotate the point around
     * @return The rotated point
     */
    public static Vector3dInterface rotateAroundCenter(Vector3dInterface p, Vector3dInterface c, double degree) {
        //translate to 0,0
        System.out.println("Original:"+(Vector3D)p);
        Vector3dInterface translated = new Vector3D(p.getX() - c.getX(), p.getY() - c.getY(), 0);
        System.out.println("Translated:"+translated);
        //rotate by degrees
        Vector3dInterface rotated = new Vector3D(translated.getX() * Math.cos(degree) - translated.getY() * Math.sin(degree),
                translated.getX() * Math.sin(degree) + translated.getY() * Math.cos(degree), 0);
        System.out.println("Rotated:"+rotated);
        //translate back
        Vector3dInterface translatedBack = new Vector3D(rotated.getX() + c.getX(), rotated.getY() + c.getY(), 0);
        System.out.println("Translated back:"+translatedBack);
        return translatedBack;
    }

    //--------------------------DEGREES--------------------------------
    /**
     * Converts from degrees to radian
     * @param degree Angle in degrees
     * @return Angle in radians
     */
    public static double degreeToRadian(double degree) {
        return degree * (Math.PI/180.0);
    }

    /**
     * Calculates the y positions that can be reached by wind from the indicated side,
     * IMPORTANT: corners have to be provided in rotated form
     * @param center Center point of the square
     * @param corners Corner points of the square
     * @param degree Angle in degree
     * @param left If true calculates for the left side, if false for the right side
     * @return
     */
    public static double[] exposedSideD(Vector3dInterface center, Vector3dInterface[] corners, double degree, boolean left) {
        return exposedSide(center, corners, degreeToRadian(degree), left);
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
     * Rotates a point around a center point
     * @param p The point to be rotated
     * @param c The center point to be rotated around
     * @param degree The angle in degrees to rotate the point around
     * @return The rotated point
     */
    public static Vector3dInterface rotateAroundCenterD(Vector3dInterface p, Vector3dInterface c, double degree) {
        return rotateAroundCenter(p, c, degreeToRadian(degree));
    }


    //--------------------------REST-----------------------------------

    /**
     * Calculates the x-position according to a y-position on either the left or the right side of the square
     * @param center The center point of the square
     * @param corners The corner points of the square
     * @param left If true left, if false right
     * @param y Y-coordinate to fetch x-coordinate to
     * @return x-position according to y-position
     */
    public static double calculateAccX(Vector3dInterface center, Vector3dInterface[] corners, boolean left, double y) {
        //1. find the two corners the position lies between
        Collections.sort(Arrays.asList(corners), xComp);
        Vector3dInterface[] points = null;
        if(left) { //exposure from left side
            points = new Vector3dInterface[]{corners[0], corners[1], corners[2]};
        } else { //exposure from the right side
            points = new Vector3dInterface[]{corners[1], corners[2], corners[3]};
        }
        //1.a.find the two points
        ArrayList<Vector3dInterface> smaller = new ArrayList<>();
        ArrayList<Vector3dInterface> bigger = new ArrayList<>();
        for(int i = 0; i < points.length; i++) {
            if(points[i].getY() <= y) {
                smaller.add(points[i]);
            } else if(points[i].getY() >= y) {
                bigger.add(points[i]);
            }
        }
        Collections.sort(smaller, yComp);
        Collections.sort(bigger, yComp);

        Vector3dInterface[] line = new Vector3dInterface[2];
        if(smaller.size() == 1) { // means bigger.size() = 2
            line[0] = smaller.get(0);
        } else { // means bigger.size() = 1
            line[0] = smaller.get(1);
        }
        line[1] = bigger.get(0);

        //2. calculate the equation for the line
        double[] param = calculateParameters(line[0], line[1]);
        //3. calculate the x coordinate of the point
        double x = - ((param[0]-y)/param[1]);
        return x;
    }

    /**
     * Calculates distance from the center to the point as a vector
     * @param center Center of the square
     * @param x x-coordinate of the point
     * @param y y-coordinate of the point
     * @return Distance from center to the point
     */
    public static Vector3dInterface calculateDist(Vector3dInterface center, double x, double y) {
        Vector3dInterface v = new Vector3D(x, y,0);

        //4. calculate distance vector to center
        return v.sub(center);
    }

    /**
     * Calculates the parameters for a linear function, based on two points
     * @param p1 First point
     * @param p2 Second point
     * @return Parameters for a linear function, where [0] is n or a0 and [1] is m or a1
     */
    public static double[] calculateParameters(Vector3dInterface p1, Vector3dInterface p2) {
        double[] param = new double[2];
        //calculate slope
        double m = (p2.getY() - p1.getY())/(p2.getX() - p1.getX());
        param[1] = m;

        //calculate y-intercept n
        double n = p1.getY() - m*p1.getX();
        param[0] = n;
        return param;
    }

    /**
     * Generates a random double out of a selected interval
     * @param min Minimum value for the random number
     * @param max Maximum value for the random number
     * @return A random number
     */
    public static double generateRandom(double min, double max) {
        double r = (Math.random() * ((max - min) + 1.0)) + min;
        return r;
    }

    public static double evalPoly(double[] param, double x) {
        double y = 0;
        for(int i = 0; i < param.length; i++) {
            y += param[i] * Math.pow(x, i);
        }
        return y;
    }
}
