package managers;

import domain.Moon;
import domain.PlanetEnum;
import domain.Vector3D;
import factory.FactoryProvider;
import gui.javafx.points.AbsolutePoint;
import gui.javafx.shapes.Circle;
import gui.javafx.shapes.Rectangle;
import gui.javafx.utils.DrawingDetail;
import gui.javafx.utils.DrawingDetailImage;
import interfaces.gui.IDrawable;
import interfaces.gui.IDrawableContext;
import interfaces.gui.IDrawableDetails;
import javafx.scene.paint.Color;
import utils.converter.PositionConverter;

import java.util.Arrays;
import java.util.HashSet;

public class DrawingManager extends Manager<IDrawable> {
    private IDrawableContext context;
    private IDrawable background;

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

        double width = 700;
        double height = 700;
        
        FactoryProvider.getSolarSystemFactory().getPlanets().forEach(planet -> {
        	IDrawableDetails found = new DrawingDetail(Color.RED);
            Vector3D v = (Vector3D) PositionConverter.convertToPixel(planet.getPosition(), width, height, planet.getName());
            
            System.out.println(planet.getName());
            System.out.println(v);
            PlanetEnum foundPlanet = PlanetEnum.getByName(planet.getName());

            if (foundPlanet != null) {
                found = foundPlanet.getDetail();
            }
            
            if(planet.getName().equals("Sun")) {
            	items.add(new Circle(30, new AbsolutePoint(v.getX(), v.getY()), found));
            } else if(planet instanceof Moon) {
            	items.add(new Circle(10, new AbsolutePoint(v.getX(), v.getY()), found));
            } else {
            	items.add(new Circle(18, new AbsolutePoint(v.getX(), v.getY()), found));
            }
        });
        background = new Rectangle(new AbsolutePoint(350, 350), width, height, new DrawingDetailImage("src/main/resources/sprites/night-sky.png"));
        super.refresh();
    }

    private void draw() {
    	background.draw(context);
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
