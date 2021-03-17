package managers;

import interfaces.gui.IUpdateManager;

import java.util.Collection;

public abstract class Manager<T> implements IUpdateManager<T> {
    protected boolean isRunning = true;
    protected Collection<T> items;
    protected Collection<T> scheduledAdditions;
    protected Collection<T> scheduledRemovals;

    @Override
    public void toggleUpdate() {
        this.isRunning = !isRunning;
    }

    @Override
    public void scheduleAddition(T item) {
        scheduledAdditions.add(item);
    }

    @Override
    public void scheduleRemoval(T item) {
        scheduledRemovals.add(item);
    }

    protected void refresh() {
        items.addAll(scheduledAdditions);
        items.removeAll(scheduledRemovals);
        scheduledRemovals.clear();
        scheduledAdditions.clear();
    }

    @Override
    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    @Override
    public Collection<T> getItems() {
        return items;
    }

    public void setItems(Collection<T> items) {
        this.items = items;
    }

    public Collection<T> getScheduledAdditions() {
        return scheduledAdditions;
    }

    public void setScheduledAdditions(Collection<T> scheduledAdditions) {
        this.scheduledAdditions = scheduledAdditions;
    }

    public Collection<T> getScheduledRemovals() {
        return scheduledRemovals;
    }

    public void setScheduledRemovals(Collection<T> scheduledRemovals) {
        this.scheduledRemovals = scheduledRemovals;
    }
}
