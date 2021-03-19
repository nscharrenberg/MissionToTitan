package interfaces.gui;

import java.util.Collection;

public interface IManager<T> {
    void init();
    void reset();
    Collection<T> getItems();
}
