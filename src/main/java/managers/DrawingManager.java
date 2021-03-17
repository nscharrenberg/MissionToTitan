package managers;

import domain.Moon;
import domain.Planet;
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
import interfaces.gui.IUpdate;
import javafx.scene.paint.Color;
import utils.converter.PositionConverter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class DrawingManager extends Manager<IDrawable> {
    private IDrawableContext context;
    double width = 700;
    double height = 700;
    private IDrawable background = new Rectangle(new AbsolutePoint(350, 350), width, height, new DrawingDetailImage("src/main/resources/sprites/night-sky.png"));


    public DrawingManager() {
        items = new HashSet<>();
        scheduledAdditions = new HashSet<>();
        scheduledRemovals = new HashSet<>();
    }

    @Override
    public void init() {
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
        refresh();
        draw();
    }

    @Override
    protected void refresh() {
        items = new HashSet<>();

       List<Planet> planets = FactoryProvider.getUpdateManager().getItems().stream().filter(obj -> obj instanceof Planet).map(obj -> (Planet) obj).collect(Collectors.toList());
        PositionConverter.convertToPixel(planets, width, height).forEach(planet -> {
//        FactoryProvider.getUpdateManager().getItems().forEach(p -> {
//            Planet planet = (Planet) p;
        	IDrawableDetails found = new DrawingDetail(Color.RED);
            //Vector3D v = (Vector3D) PositionConverter.convertToPixel(planet.getPosition(), width, height, planet.getName());
            Vector3D v = (Vector3D) planet.getPosition();
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


        super.refresh();
    }

    private void draw() {
        context.reset();
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
