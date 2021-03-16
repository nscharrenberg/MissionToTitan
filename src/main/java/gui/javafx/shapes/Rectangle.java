package gui.javafx.shapes;

import gui.javafx.points.RelativePoint;
import interfaces.gui.IPoint;
import javafx.scene.canvas.GraphicsContext;

import java.util.Objects;

// TODO: We might want to do some validation here.
public class Rectangle extends Shape {
    private double width;
    private double height;

    public Rectangle(double width, double height) {
        super();
        this.width = width;
        this.height = height;
    }

    public Rectangle(IPoint point, double width, double height) {
        super(point);
        this.width = width;
        this.height = height;
    }

    @Override
    protected void calcOutlines() {
        outlines.clear();

        for (int i = -Shape.POLYGON_COUNT / 2; i <= Shape.POLYGON_COUNT; i++) {
            // Top
            outlines.add(new RelativePoint(getPoint(), i * (width / Shape.POLYGON_COUNT), -height / 2));
            // Bottom
            outlines.add(new RelativePoint(getPoint(), i * (width / Shape.POLYGON_COUNT), height / 2));
            // Left
            outlines.add(new RelativePoint(getPoint(), -width / 2, i * (height / Shape.POLYGON_COUNT)));
            // Right
            outlines.add(new RelativePoint(getPoint(), width / 2, i * (height / Shape.POLYGON_COUNT)));
        }
    }

    @Override
    public void draw(GraphicsContext context) {
        context.fillRect(getPoint().getCoordinates().getX() - width / 2, getPoint().getCoordinates().getY() - height / 2, width, height);
    }

    @Override
    public double area() {
        return width * height;
    }

    @Override
    public boolean contains(IPoint point) {
        return  point.getCoordinates().getX() <= (getPoint().getCoordinates().getX() + width / 2)
                && point.getCoordinates().getX() >= (getPoint().getCoordinates().getX() - width / 2)
                && point.getCoordinates().getY() <= (getPoint().getCoordinates().getY() + height / 2)
                && point.getCoordinates().getY() >= (getPoint().getCoordinates().getY() - height / 2);
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "Rectangle{" +
                "width=" + width +
                ", height=" + height +
                ", point=" + point +
                ", outlines=" + outlines +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Rectangle rectangle = (Rectangle) o;
        return Double.compare(rectangle.width, width) == 0 &&
                Double.compare(rectangle.height, height) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), width, height);
    }
}
