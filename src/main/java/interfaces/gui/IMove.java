package interfaces.gui;

public interface IMove {
    void move(IPoint point);
    IPoint getCalculatedVelocity();
    void setCalculatedVelocity(double val);
}
