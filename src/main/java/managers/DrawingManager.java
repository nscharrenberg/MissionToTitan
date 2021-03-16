package managers;

import factory.FactoryProvider;
import gui.javafx.points.AbsolutePoint;
import gui.javafx.shapes.Circle;
import gui.javafx.utils.DrawingDetail;
import interfaces.gui.IDrawable;
import interfaces.gui.IDrawableContext;
import javafx.scene.paint.Color;

import java.util.HashSet;

public class DrawingManager extends Manager<IDrawable> {
    private IDrawableContext context;

    public DrawingManager() {
        items = new HashSet<>();
        scheduledAdditions = new HashSet<>();
        scheduledRemovals = new HashSet<>();
    }

    @Override
    public void init() {
        FactoryProvider.getSolarSystemFactory().init();
        refresh();
    }

    @Override
    public void reset() {
        toggleUpdate();
        items = new HashSet<>();
        scheduledAdditions = new HashSet<>();
        scheduledRemovals = new HashSet<>();
    }

    @Override
    public void update() {
        if (isRunning()) {
            System.out.println("Drawing");
            draw();
        }

        refresh();
    }

    @Override
    protected void refresh() {
        items = new HashSet<>();

        // TODO: Not correct logic. Should be revised
        FactoryProvider.getSolarSystemFactory().getPlanets().forEach(planet -> {
            Color color = Color.RED;
            if (planet.getName().equals("earth")) {
                color = Color.BLUE;
            }

            items.add(new Circle(10, new AbsolutePoint(planet.getPosition().getX(), planet.getPosition().getY()), new DrawingDetail(color)));
        });

        super.refresh();
    }

    private void draw() {
        items.forEach(item -> {
            System.out.println(item);
            item.draw(context);
        });
    }

    public IDrawableContext getContext() {
        return context;
    }

    public void setContext(IDrawableContext context) {
        this.context = context;
    }
}
