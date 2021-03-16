package interfaces.gui;

public interface IUpdateManager<T> extends IManager<T>, IUpdate {
    void toggleUpdate();
    boolean isRunning();
}
