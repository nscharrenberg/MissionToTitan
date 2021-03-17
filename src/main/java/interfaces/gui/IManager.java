package interfaces.gui;

import java.util.Collection;

public interface IManager<T> {
    void scheduleAddition(T item);
    void scheduleRemoval(T item);
    void init();
    void reset();
    Collection<T> getItems();
}
