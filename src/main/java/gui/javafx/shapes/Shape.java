package gui.javafx.shapes;

import gui.javafx.points.AbsolutePoint;
import gui.javafx.utils.DrawingContext;
import interfaces.gui.IDrawableContext;
import interfaces.gui.IPoint;
import interfaces.gui.IShape;
import javafx.scene.canvas.GraphicsContext;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public abstract class Shape implements IShape {
    public static final int POLYGON_COUNT = 10;

    protected IPoint point;
    protected List<IPoint> outlines = new LinkedList<>();

    public Shape(IPoint point) {
        this.point = point;
    }

    public Shape() {
        this.point = new AbsolutePoint(0, 0);
    }

    protected abstract void calcOutlines();
    public abstract void draw(GraphicsContext context);

    @Override
    public void updateOutlines() {
        if (outlines.size() > POLYGON_COUNT) {
            return;
        }

        calcOutlines();
    }

    @Override
    public Collection<IPoint> getOutlines() {
        return this.outlines;
    }

    @Override
    public IPoint getPoint() {
        return this.point;
    }

    @Override
    public void setPoint(IPoint point) {
        this.point = point;
    }

    @Override
    public boolean intersects(IShape other) {
        if (this == other) {
            return false;
        }

        return outlines.stream().anyMatch(other::contains) || covers(other);
    }

    @Override
    public boolean covers(IShape other) {
        if (!(other instanceof Shape)) {
            return false;
        }

        return ((Shape) other).outlines.stream().allMatch(this::contains);
    }

    @Override
    public boolean isSameShape(IShape other) {
        if (!(other instanceof Shape)) {
            return false;
        }

        return this.point.isSamePosition(other.getPoint())
                && area() == other.area()
                && this.outlines.equals(((Shape) other).outlines);
    }

    @Override
    public void draw(IDrawableContext context) {
        if (!(context instanceof DrawingContext)) {
            throw new UnsupportedOperationException("No JavaFX Drawing Context found");
        }

        draw(((DrawingContext) context).getContext());
    }

    @Override
    public String toString() {
        return "Shape{" +
                "point=" + point +
                ", outlines=" + outlines +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Shape)) return false;
        Shape shape = (Shape) o;
        return point.equals(shape.point) &&
                outlines.equals(shape.outlines);
    }

    @Override
    public int hashCode() {
        return Objects.hash(point, outlines);
    }
}
