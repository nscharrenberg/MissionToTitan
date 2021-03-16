package managers;

import factory.FactoryProvider;
import interfaces.gui.IDrawable;
import interfaces.gui.IDrawableContext;

import java.util.HashSet;

public class DrawingManager extends Manager<IDrawable> {
    private IDrawableContext context;

    public DrawingManager() {
        reset();
    }

    @Override
    public void init() {

    }

    @Override
    public void update() {
        if (isRunning()) {
            draw();
        }

        refresh();
    }

    private void draw() {
        items.forEach(item -> item.draw(context));
    }

    @Override
    public void reset() {
        this.isRunning = true;
        items = new HashSet<>();
        scheduledRemovals = new HashSet<>();
        scheduledAdditions = new HashSet<>();
    }

    @Override
    protected void refresh() {
        items = new HashSet<>();



        super.refresh();
    }

    public IDrawableContext getContext() {
        return context;
    }

    public void setContext(IDrawableContext context) {
        this.context = context;
    }
}
