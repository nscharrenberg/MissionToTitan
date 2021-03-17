package gui.javafx.shapes;

import gui.javafx.points.RelativePoint;
import interfaces.gui.IDrawableDetails;
import interfaces.gui.IPoint;
import javafx.scene.canvas.GraphicsContext;

import java.util.Objects;

// TODO: We might want to do some validation here.
public class Circle extends Shape {
    private double radius;

    public Circle(double radius) {
        super();
        this.radius = radius;
    }

    public Circle(double radius, IPoint point) {
        super(point);
        this.radius = radius;
    }

    public Circle(double radius, IPoint point, IDrawableDetails drawableDetails) {
        super(point, drawableDetails);
        this.radius = radius;
    }

    @Override
    protected void calcOutlines() {
        outlines.removeIf(point -> true);

        for (int i = 0; i < Shape.POLYGON_COUNT; i++) {
            outlines.add(new RelativePoint(this.point, Math.cos(i * Math.PI * 2 / Shape.POLYGON_COUNT) * radius, Math.sin(i * Math.PI * 2 / Shape.POLYGON_COUNT) * radius));
        }
    }

    @Override
    public void draw(GraphicsContext context) {
        context.fillOval(getPoint().getCoordinates().getX() - radius, getPoint().getCoordinates().getY() - radius, 2 * radius, 2 * radius);
    }

    @Override
    public double area() {
        return Math.PI * Math.pow(radius, 2);
    }

    @Override
    public boolean contains(IPoint point) {
        return this.getPoint().distanceTo(point) <= this.radius;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    @Override
    public String toString() {
        return "Circle{" +
                "radius=" + radius +
                ", point=" + point +
                ", outlines=" + outlines +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Circle circle = (Circle) o;
        return Double.compare(circle.radius, radius) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), radius);
    }
}
