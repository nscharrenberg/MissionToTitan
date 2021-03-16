package managers;

import interfaces.gui.IUpdateManager;

import java.util.Collection;

public abstract class Manager<T> implements IUpdateManager<T> {
    protected Collection<T> items;
    protected Collection<T> scheduledAdditions;
    protected Collection<T> scheduledRemovals;
    protected boolean isRunning = true;

    @Override
    public void toggleUpdate() {
        this.isRunning = !this.isRunning;
    }

    @Override
    public boolean isRunning() {
        return isRunning;
    }

    @Override
    public void scheduleAddition(T item) {
        this.scheduledAdditions.add(item);
    }

    @Override
    public void scheduleRemoval(T item) {
        this.scheduledRemovals.add(item);
    }

    @Override
    public void reset() {
        this.isRunning = true;
        this.scheduledRemovals.clear();
        this.scheduledAdditions.clear();
        this.items.clear();
    }

    @Override
    public Collection<T> getItems() {
        return items;
    }

    protected void refresh() {
        items.addAll(scheduledAdditions);

        scheduledAdditions.removeAll(scheduledRemovals);
        scheduledRemovals.clear();
        scheduledAdditions.clear();
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

    public void setRunning(boolean running) {
        isRunning = running;
    }
}
