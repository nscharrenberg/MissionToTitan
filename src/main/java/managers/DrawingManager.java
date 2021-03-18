package managers;

import domain.*;
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
import utils.PlanetReader;
import utils.converter.Scale;
import utils.converter.PositionConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class DrawingManager extends Manager<IDrawable> {
    private IDrawableContext context;
    private IDrawable background;
    private IDrawable backgroundCircle;
    private HashMap<String,Double> radii;


    public DrawingManager() {
        items = new HashSet<>();
        scheduledAdditions = new HashSet<>();
        scheduledRemovals = new HashSet<>();
    }

    @Override
    public void init() {
        background = new Rectangle(new AbsolutePoint( FactoryProvider.getSettingRepository().getCanvasWidth() / 2,  FactoryProvider.getSettingRepository().getAppHeight() / 2), FactoryProvider.getSettingRepository().getCanvasWidth(), FactoryProvider.getSettingRepository().getAppHeight(), new DrawingDetailImage("src/main/resources/sprites/starry-night-sky.png"));
        double size = FactoryProvider.getSettingRepository().getAppHeight();

        if (FactoryProvider.getSettingRepository().getCanvasWidth() < FactoryProvider.getSettingRepository().getAppHeight()) {
            size = FactoryProvider.getSettingRepository().getCanvasWidth();
        }
        backgroundCircle = new Circle(size / 2, new AbsolutePoint( FactoryProvider.getSettingRepository().getCanvasWidth() / 2,  FactoryProvider.getSettingRepository().getAppHeight() / 2), new DrawingDetail(Color.BLACK));
        radii = new HashMap<>();
        ArrayList<Planet> planets = PlanetReader.getPlanetsWithRadii();
        for(Planet temp : planets) {
            double radius = temp.getRadius();
            if(radius <= 6371e3) {
                radii.put(temp.getName(), PositionConverter.convert(temp.getRadius(), new Scale(30, 6963400)));
            } else if(radius >= 696340e3 ){
                radii.put(temp.getName(), PositionConverter.convert(temp.getRadius(), new Scale(75, 696340e3)));
            } else {
                radii.put(temp.getName(), PositionConverter.convert(temp.getRadius(), new Scale(50, 69911e3)));
            }
        }

        radii.put("Probe", 10d);
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

        List<MovingObject> planets = FactoryProvider.getUpdateManager().getItems().stream().filter(obj -> obj instanceof MovingObject).map(obj -> (MovingObject) obj).collect(Collectors.toList());
        PositionConverter.convertToPixel(planets, FactoryProvider.getSettingRepository().getCanvasWidth(), FactoryProvider.getSettingRepository().getAppHeight()).forEach(planet -> {
	        IDrawableDetails found = new DrawingDetail(Color.RED);
	        Vector3D v = (Vector3D) planet.getPosition();
	        PlanetEnum foundPlanet = PlanetEnum.getByName(planet.getName());

	        if (planet.getName().equals("Probe")) {
                items.add(new Circle(100, new AbsolutePoint(350, 350), found));
                //planet.setPosition(new Vector3D(350,350,350));
            }

            if (foundPlanet != null) {
                found = foundPlanet.getDetail();
            }
            double radius = radii.get(planet.getName());
            items.add(new Circle(radius, new AbsolutePoint(v.getX(), v.getY()), found));


        });

        super.refresh();
    }

    private void draw() {
        context.reset();
    	background.draw(context);
    	backgroundCircle.draw(context);
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
