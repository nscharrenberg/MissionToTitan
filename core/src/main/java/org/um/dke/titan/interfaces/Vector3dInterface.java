/*
 * @author Pieter Collins, Christof Seiler, Katerina Stankova, Nico Roos, Katharina Schueller
 * @version 0.99.0
 *
 * This interface serves as the API for students in phase 1.
 */

package org.um.dke.titan.interfaces;

public interface Vector3dInterface {

    double getX();

    void setX(double x);

    double getY();

    void setY(double y);

    double getZ();

    void setZ(double z);

    Vector3dInterface add(Vector3dInterface other);

    Vector3dInterface sub(Vector3dInterface other);

    Vector3dInterface mul(double scalar);

    /**
     * Scalar x vector multiplication, followed by an addition
     *
     * @param scalar the double used in the multiplication step
     * @param other  the vector used in the multiplication step
     * @return the result of the multiplication step added to this vector,
     * for example:
     * <p>
     * Vector3d a = Vector();
     * double h = 2;
     * Vector3d b = Vector();
     * ahb = a.addMul(h, b);
     * <p>
     * ahb should now contain the result of this mathematical operation:
     * a+h*b
     */
    Vector3dInterface addMul(double scalar, Vector3dInterface other);

    /**
     * @return the Euclidean norm of a vector
     */
    double norm();

    /**
     * @return the Euclidean distance between two vectors
     */
    double dist(Vector3dInterface other);

    /**
     * @return A string in this format:
     * Vector3d(-1.0, 2, -3.0) should print out (-1.0,2.0,-3.0)
     */
    String toString();

}