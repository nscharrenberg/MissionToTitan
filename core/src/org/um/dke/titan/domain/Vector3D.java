package org.um.dke.titan.domain;

import com.badlogic.gdx.math.Vector3;
import org.um.dke.titan.interfaces.Vector3dInterface;

import java.util.Objects;

public class Vector3D implements Vector3dInterface {
    protected Vector3 position;

    public Vector3D(Vector3 position) {
        this.position = position;
    }

    public Vector3D(double x, double y, double z) {
        position = new Vector3((float)x, (float)y, (float)z);
    }

    public Vector3D(float x, float y, float z) {
        position = new Vector3(x, y, z);
    }

    @Override
    public double getX() {
        return position.x;
    }

    @Override
    public void setX(double x) {
        position.x = (float)x;
    }

    @Override
    public double getY() {
        return position.y;
    }

    @Override
    public void setY(double y) {
        position.y = (float)y;
    }

    @Override
    public double getZ() {
        return position.z;
    }

    @Override
    public void setZ(double z) {
        position.z = (float)z;
    }

    /**
     * Vector + vector addition
     * @param other - the vector added to this
     * @return the result
     */
    @Override
    public Vector3dInterface add(Vector3dInterface other) {
        return new Vector3D(position.x + other.getX(), position.y + other.getY(), position.z + other.getZ());
    }

    /**
     * Vector - vector subtraction
     * @param other - the vector subtracted from this
     * @return the result
     */
    @Override
    public Vector3dInterface sub(Vector3dInterface other) {
        return new Vector3D(position.x - other.getX(), position.y - other.getY(), position.z - other.getZ());
    }

    /**
     * Scalar x vector multiplication
     * @param scalar - the double multiplied by this
     * @return the result
     */
    @Override
    public Vector3dInterface mul(double scalar) {
        return new Vector3D(position.x * scalar, position.y * scalar, position.z * scalar);
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
        Vector3dInterface resultant = new Vector3D(position.x, position.y, position.z);
        other = other.mul(scalar);
        resultant = resultant.add(other);

        return resultant;
    }

    /**
     * @return the Euclidean norm of a vector
     */
    @Override
    public double norm() {
        return Math.sqrt(Math.pow(position.x, 2) + Math.pow(position.y, 2) + Math.pow(position.z, 2));
    }

    /**
     * @return the Euclidean distance between two vectors
     */
    @Override
    public double dist(Vector3dInterface other) {
        return Math.sqrt(Math.pow(position.x-other.getX(), 2) + Math.pow(position.y-other.getY(), 2) + Math.pow(position.z-other.getZ(), 2));
    }

    @Override
    public String toString() {
        return "Vector3D{" +
                "x=" + position.x +
                ", y=" + position.y +
                ", z=" + position.z +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector3D vector3D = (Vector3D) o;
        return Objects.equals(position, vector3D.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position);
    }

    public Vector3D clone() {
        return new Vector3D(position);
    }
}
