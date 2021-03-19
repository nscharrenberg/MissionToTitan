package managers;

import domain.*;
import factory.FactoryProvider;
import gui.javafx.points.AbsolutePoint;
import gui.javafx.shapes.Circle;
import gui.javafx.shapes.Rectangle;
import gui.javafx.shapes.Shape;
import gui.javafx.utils.DrawingContext;
import gui.javafx.utils.DrawingDetail;
import gui.javafx.utils.DrawingDetailImage;
import interfaces.Vector3dInterface;
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
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class DrawingManager extends Manager<IDrawable> {
    private IDrawableContext context;
    private IDrawable background;
    private IDrawable backgroundCircle, center;
    private HashMap<String,Double> radii;
    private double zoomFactor;
    private Vector3dInterface offset;
    private String centered;
    private AtomicBoolean newCenter;
    private int centeredI;
    
    //
    public final Vector3dInterface DEFAULT_OFFSET = new Vector3D(0,0,0);
    public static final double ZOOM_VALUE = 0.25;

    public DrawingManager() {
        items = new HashSet<>();
        zoomFactor = 1;
        centered = "Sun";
        newCenter = new AtomicBoolean(false);
        centeredI = 0;
    }

    @Override
    public void init() {
        background = new Rectangle(new AbsolutePoint( FactoryProvider.getSettingRepository().getCanvasWidth() / 2,  FactoryProvider.getSettingRepository().getAppHeight() / 2), FactoryProvider.getSettingRepository().getCanvasWidth(), FactoryProvider.getSettingRepository().getAppHeight(), new DrawingDetailImage("src/main/resources/sprites/starry-night-sky.png"));
        double size = FactoryProvider.getSettingRepository().getAppHeight();

        if (FactoryProvider.getSettingRepository().getCanvasWidth() < FactoryProvider.getSettingRepository().getAppHeight()) {
            size = FactoryProvider.getSettingRepository().getCanvasWidth();
        }
        backgroundCircle = new Circle(size / 2, new AbsolutePoint( FactoryProvider.getSettingRepository().getCanvasWidth() / 2,  FactoryProvider.getSettingRepository().getAppHeight() / 2), new DrawingDetail(Color.BLACK));
        //center = new Circle(1.0, new AbsolutePoint( FactoryProvider.getSettingRepository().getCanvasWidth() / 2,  FactoryProvider.getSettingRepository().getAppHeight() / 2), new DrawingDetail(Color.RED));
        radii = new HashMap<>();
        ArrayList<Planet> planets = PlanetReader.getPlanetsWithRadii();

        if (!FactoryProvider.getSettingRepository().isRealisticSize()) {
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
        } else {
            for(Planet temp : planets) {
                radii.put(temp.getName(), PositionConverter.convert(temp.getRadius(),new Scale((FactoryProvider.getSettingRepository().getCanvasWidth()/2), 1.51E10)));
            }
        }

        radii.put("Probe", 10d);
        offset = DEFAULT_OFFSET;
    }

    @Override
    public void reset() {
        items = new HashSet<>();
    }

    @Override
    public void update() {
        refresh();
        draw();
    }

    @Override
    protected void refresh() {
        items = new HashSet<>();
        double size = FactoryProvider.getSettingRepository().getAppHeight();

        if (FactoryProvider.getSettingRepository().getCanvasWidth() < FactoryProvider.getSettingRepository().getAppHeight()) {
            size = FactoryProvider.getSettingRepository().getCanvasWidth();
        }
        backgroundCircle = new Circle(zoomFactor*(size / 2), new AbsolutePoint( zoomFactor*(FactoryProvider.getSettingRepository().getCanvasWidth() / 2) + offset.getX(),  zoomFactor*(FactoryProvider.getSettingRepository().getAppHeight() / 2)+ offset.getY()), new DrawingDetail(Color.BLACK));
        
        List<MovingObject> planets = FactoryProvider.getUpdateManager().getItems().stream().filter(obj -> obj instanceof MovingObject).map(obj -> (MovingObject) obj).collect(Collectors.toList());
        

        if (context instanceof DrawingContext) {
            DrawingContext ct = (DrawingContext) context;
            ct.getCanvas().setOnScroll(c -> {
            	int sign = (int) (c.getDeltaY()/Math.abs(c.getDeltaY()));
            	zoomFactor += sign*(ZOOM_VALUE/5);
            });
            ct.getCanvas().setOnMouseClicked(c -> {
                System.out.println(String.format("%s-%s", c.getX(), c.getY()));
                System.out.println(items.size());
                items.forEach(shape -> {
                    if (shape instanceof Circle) {
                        Circle circle = (Circle) shape;
                        boolean collided = circle.contains(new AbsolutePoint(c.getX(), c.getY()));

                        if (collided) {
                            String path = ((DrawingDetailImage)(circle.getDetails())).getPath();
                            centered = (String) path.subSequence(path.lastIndexOf('/')+1, path.indexOf(".png"));
                            newCenter.set(true);
                            MovingObject tmp = planets.stream().filter(p -> p.getName().equals(centered)).findFirst().orElse(null);
                            //Vector3D v = (Vector3D) tmp.getPosition();
                            
                            Vector3D v = new Vector3D(c.getX(), c.getY(), 0);
                        	Vector3D w = (Vector3D) new Vector3D(FactoryProvider.getSettingRepository().getCanvasWidth() / 2,  FactoryProvider.getSettingRepository().getAppHeight() / 2, 0).sub(v.sub(offset));
                            offset = w;
                            return;
                        }
                    }
                });
            });
        }
        PositionConverter.convertToPixel(planets, zoomFactor*(FactoryProvider.getSettingRepository().getCanvasWidth()), zoomFactor*(FactoryProvider.getSettingRepository().getAppHeight()), offset).forEach(planet -> {
	        IDrawableDetails found = new DrawingDetail(Color.RED);
	        Vector3D v = (Vector3D) planet.getPosition();
	        PlanetEnum foundPlanet = PlanetEnum.getByName(planet.getName());

            if (foundPlanet != null) {
                found = foundPlanet.getDetail();
            }

            double radius = zoomFactor*radii.get(planet.getName());

            double xPos = v.getX();
            double yPos = v.getY();

            if (FactoryProvider.getSettingRepository().getLayoutView().equals(LayoutView.XZ)) {
                xPos = v.getX();
                yPos = v.getZ();
            }

            items.add(new Circle(radius, new AbsolutePoint(xPos, yPos), found));


        });
        
        ArrayList<IDrawable> al = new ArrayList<IDrawable>(items);
        Circle c = null;
        Circle found = null;
        String path;
        String name = new String();
        for(IDrawable id : al) {
        	c = (Circle) id;
        	path = ((DrawingDetailImage)(c.getDetails())).getPath();
            name = (String) path.subSequence(path.lastIndexOf('/')+1, path.indexOf(".png"));
            if(name.equals(centered)) {
            	found = c;
            	break;
            }
        }
        Vector3D v = new Vector3D(found.getPoint().getCoordinates().getX(), found.getPoint().getCoordinates().getY(), 0);
    	Vector3D w = (Vector3D) new Vector3D(FactoryProvider.getSettingRepository().getCanvasWidth() / 2,  FactoryProvider.getSettingRepository().getAppHeight() / 2, 0).sub(v.sub(offset));
        offset = w;
        super.refresh();
    }

    private void draw() {
        context.reset();
    	background.draw(context);
    	backgroundCircle.draw(context);
        items.forEach(item -> {
            item.draw(context);
        });
        //center.draw(context);
    }

    public IDrawableContext getContext() {
        return context;
    }

    public void setContext(IDrawableContext context) {
        this.context = context;
    }

	public double getZoomFactor() {
		return zoomFactor;
	}

	public void decreaseZoom() {
		if(zoomFactor > 0) {
			zoomFactor -= ZOOM_VALUE;
		}
	}
	public void increaseZoom() {
		if(zoomFactor < 10) {
			zoomFactor += ZOOM_VALUE;
		}
	}
	public void defaultZoom() {
		zoomFactor = 1;
		offset = DEFAULT_OFFSET;
		centered = "Sun";
	}
    
}
