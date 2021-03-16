package managers;

import domain.PlanetEnum;
import domain.Vector3D;
import factory.FactoryProvider;
import gui.javafx.points.AbsolutePoint;
import gui.javafx.shapes.Circle;
import gui.javafx.utils.DrawingDetail;
import interfaces.gui.IDrawable;
import interfaces.gui.IDrawableContext;
import interfaces.gui.IDrawableDetails;
import javafx.scene.paint.Color;
import utils.converter.PositionConverter;

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
            DrawingDetail found = new DrawingDetail(Color.RED);

            double width = 700;
            double height = 700;
            Vector3D v = (Vector3D) PositionConverter.convertToPixel(planet.getPosition(), width, height);

            System.out.println(v);

            PlanetEnum foundPlanet = PlanetEnum.getByName(planet.getName());

            if (foundPlanet != null) {
                found = (DrawingDetail) foundPlanet.getDetail();
            }

            items.add(new Circle(10, new AbsolutePoint(v.getX(), v.getY()), found));
        });

        super.refresh();
    }

    private void draw() {
        items.forEach(item -> {
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
