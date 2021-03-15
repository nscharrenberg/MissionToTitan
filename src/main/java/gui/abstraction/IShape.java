package gui.abstraction;

public interface IShape extends IDrawable {
    boolean contains(IPoint point);
    boolean intersects(IShape other);
    boolean covers(IShape other);
    boolean isSameShape(IShape other);
    double area();
    IPoint getCenter();
    void setCenter(IPoint point);
    IShape copy();
}
