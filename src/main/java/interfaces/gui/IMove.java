package interfaces.gui;

public interface IMove {
    void move(IPoint point);
    double getVelocity();
    void setSpeed(double val);
}
