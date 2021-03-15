package domain;

import interfaces.Vector3dInterface;

public class Vector3D implements Vector3dInterface {
    private double x;
    private double y;
    private double z;

    public Vector3D(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * get the x coordinate
     * @return x - the x coordinate
     */
    @Override
    public double getX() {
        return this.x;
    }

    /**
     * Change the z coordinate
     * @param x - the new x coordinate
     */
    @Override
    public void setX(double x) {
        this.x = x;
    }

    /**
     * get the y coordinate
     * @return y - the y coordinate
     */
    @Override
    public double getY() {
        return this.y;
    }

    /**
     * Change the y coordinate
     * @param y - the new y coordinate
     */
    @Override
    public void setY(double y) {
        this.y = y;
    }

    /**
     * get the z coordinate
     * @return z - the z coordinate
     */
    @Override
    public double getZ() {
        return this.z;
    }

    /**
     * Change the z coordinate
     * @param z - the new z coordinate
     */
    @Override
    public void setZ(double z) {
        this.z = z;
    }

    /**
     * Vector + vector addition
     * @param other - the vector added to this
     * @return the result
     */
    @Override
    public Vector3dInterface add(Vector3dInterface other) {
        return new Vector3D(x + other.getX(), y + other.getY(), z + other.getZ());
    }

    /**
     * Vector - vector subtraction
     * @param other - the vector subtracted from this
     * @return the result
     */
    @Override
    public Vector3dInterface sub(Vector3dInterface other) {
        return new Vector3D(x - other.getX(), y - other.getY(), z - other.getZ());
    }

    /**
     * Scalar x vector multiplication
     * @param scalar - the double multiplied by this
     * @return the result
     */
    @Override
    public Vector3dInterface mul(double scalar) {
        return new Vector3D(x * scalar, y * scalar, z * scalar);
    }

    /**
     * Scalar x vector multiplication, followed by an addition
     *
     * @param scalar the double used in the multiplication step
     * @param other  the vector used in the multiplication step
     * @return the result of the multiplication step added to this vector,
     * for example:
     *
     *       Vector3d a = Vector();
     *       double h = 2;
     *       Vector3d b = Vector();
     *       ahb = a.addMul(h, b);
     *
     * ahb should now contain the result of this mathematical operation:
     *       a+h*b
     */
    @Override
    public Vector3dInterface addMul(double scalar, Vector3dInterface other) {
        Vector3dInterface resultant = new Vector3D(this.x, this.y, this.z);
        resultant.add(other);
        resultant.mul(scalar);
        return resultant;
    }

    /**
     * @return the Euclidean norm of a vector
     */
    @Override
    public double norm() {
        return Math.sqrt(x*x+y*y+z*z);
    }

    /**
     * @return the Euclidean distance between two vectors
     */
    @Override
    public double dist(Vector3dInterface other) {
        return Math.sqrt((x-other.getX())*(x-other.getX()) + (y-other.getY())*(y-other.getY()) + (z-other.getZ())*(z-other.getZ()));
    }

    /**
     * @return A string in this format:
     * Vector3d(-1.0, 2, -3.0) should print out (-1.0, 2.0, -3.0)
     */
    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }
}
