package gui.javafx.points;

import domain.Vector3D;
import interfaces.Vector3dInterface;
import interfaces.gui.IPoint;

import java.util.Objects;
import java.util.Vector;

public class AbsolutePoint implements IPoint {
    protected Vector3dInterface coordinates;

    public AbsolutePoint(Vector3dInterface coordinates) {
        this.coordinates = coordinates;
    }

    public AbsolutePoint(double x, double y) {
        this.coordinates = new Vector3D(x, y, 0);
    }

    public AbsolutePoint(double x, double y, double z) {
        this.coordinates = new Vector3D(x, y, z);
    }

    @Override
    public Vector3dInterface getCoordinates() {
        return this.coordinates;
    }

    public void setCoordinates(Vector3dInterface coordinates) {
        this.coordinates = coordinates;
    }

    public double getX() {
        return this.coordinates.getX();
    }

    public double getY() {
        return this.coordinates.getY();
    }

    public double getZ() {
        return this.coordinates.getZ();
    }

    public void setX(double x) {
        this.coordinates.setX(x);
    }

    public void setY(double y) {
        this.coordinates.setY(y);
    }

    public void setZ(double z) {
        this.coordinates.setZ(z);
    }

    public void move(double vx, double vy) {
        setX(getX() + vx);
        setY(getY() + vy);
    }

    public void move(double vx, double vy, double vz) {
        move(vx, vy);
        setZ(getZ() + vz);
    }

    @Override
    public String toString() {
        return "AbsolutePoint{" +
                "coordinates=" + coordinates +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbsolutePoint that = (AbsolutePoint) o;
        return coordinates.equals(that.coordinates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinates);
    }
}
