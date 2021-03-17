package gui.javafx.points;

import interfaces.Vector3dInterface;
import interfaces.gui.IPoint;

import java.util.Objects;

public class RelativePoint implements IPoint {
    protected IPoint point;
    protected double vx;
    protected double vy;
    protected double vz = 0;

    public RelativePoint(IPoint point, double vx, double vy) {
        this.point = point;
        this.vx = vx;
        this.vy = vy;
    }

    public RelativePoint(IPoint point, double vx, double vy, double vz) {
        this.point = point;
        this.vx = vx;
        this.vy = vy;
        this.vz = vz;
    }

    @Override
    public Vector3dInterface getCoordinates() {
        return null;
    }

    public IPoint getPoint() {
        return point;
    }

    public void setPoint(IPoint point) {
        this.point = point;
    }

    public double getVx() {
        return vx;
    }

    public void setVx(double vx) {
        this.vx = vx;
    }

    public double getVy() {
        return vy;
    }

    public void setVy(double vy) {
        this.vy = vy;
    }

    public double getVz() {
        return vz;
    }

    public void setVz(double vz) {
        this.vz = vz;
    }

    @Override
    public String toString() {
        return "RelativePoint{" +
                "point=" + point +
                ", vx=" + vx +
                ", vy=" + vy +
                ", vz=" + vz +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RelativePoint that = (RelativePoint) o;
        return Double.compare(that.vx, vx) == 0 &&
                Double.compare(that.vy, vy) == 0 &&
                Double.compare(that.vz, vz) == 0 &&
                point.equals(that.point);
    }

    @Override
    public int hashCode() {
        return Objects.hash(point, vx, vy, vz);
    }
}
