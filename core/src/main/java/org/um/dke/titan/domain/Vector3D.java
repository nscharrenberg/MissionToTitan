package org.um.dke.titan.domain;

import com.badlogic.gdx.math.Vector3;
import org.um.dke.titan.interfaces.Vector3dInterface;

import java.util.Objects;

public class Vector3D implements Vector3dInterface {
    protected double x;
    protected double y;
    protected double z;

    public Vector3D() {
    }

    public Vector3D(Vector3 position) {
        this.x = position.x;
        this.y = position.y;
        this.z = position.z;
    }

    public Vector3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3D(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public void setX(double x) {
        this.x = x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public void setY(double y) {
        this.y = y;
    }

    @Override
    public double getZ() {
        return z;
    }

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
        Vector3dInterface resultant = new Vector3D(x, y, z);
        other = other.mul(scalar);
        resultant = resultant.add(other);

        return resultant;
    }

    /**
     * @return the Euclidean norm of a vector
     */
    @Override
    public double norm() {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
    }

    /**
     * @return the Euclidean distance between two vectors
     */
    @Override
    public double dist(Vector3dInterface other) {
        return Math.sqrt(Math.pow(x-other.getX(), 2) + Math.pow(y-other.getY(), 2) + Math.pow(z-other.getZ(), 2));
    }

    @Override
    public String toString() {
        return "(" +
                + x +
                "," + y +
                "," + z +
                ')';
    }

    public Vector3dInterface getUnit() {
        return this.mul(1/this.norm());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector3D vector3D = (Vector3D) o;
        return Double.compare(vector3D.x, x) == 0 &&
                Double.compare(vector3D.y, y) == 0 &&
                Double.compare(vector3D.z, z) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    @Override
    public Vector3D clone() {
        return new Vector3D(x, y, z);
    }
}
