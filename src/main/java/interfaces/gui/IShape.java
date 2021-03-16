package interfaces.gui;

import java.util.Collection;

public interface IShape extends IDrawable {
    double area();
    void updateOutlines();
    Collection<IPoint> getOutlines();
    IPoint getPoint();
    void setPoint(IPoint point);
    boolean contains(IPoint point);
    boolean intersects(IShape other);
    boolean covers(IShape other);
    boolean isSameShape(IShape shape);
}
