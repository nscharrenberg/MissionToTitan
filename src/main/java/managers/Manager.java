package managers;

import interfaces.gui.IUpdateManager;

import java.util.Collection;

public abstract class Manager<T> implements IUpdateManager<T> {
    protected Collection<T> items;

    protected void refresh() {
        // nothing
    }

    @Override
    public Collection<T> getItems() {
        return items;
    }

    public void setItems(Collection<T> items) {
        this.items = items;
    }
}
