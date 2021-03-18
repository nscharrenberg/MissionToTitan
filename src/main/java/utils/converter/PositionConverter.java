package utils.converter;

import domain.MovingObject;
import domain.Planet;
import domain.Vector3D;
import factory.FactoryProvider;
import interfaces.Vector3dInterface;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;
import java.util.List;

public class PositionConverter {
    private Scale scale;

    public PositionConverter(Scale scale) {
        this.scale = scale;
    }

    public Vector3dInterface convert(Vector3dInterface v) {
        double x = scale.getScale()*v.getX();
        double y = scale.getScale()*v.getY();
        double z = scale.getScale()*v.getZ();
        return new Vector3D(x, y, z);
    }
    public static List<MovingObject> convertToPixel(List<MovingObject> planets, double screenWidth, double screenHeight){
    	Rectangle2D canvas = new Rectangle2D.Double();
        double marginX = 0, marginY = 0;
        if(screenWidth == screenHeight) {
            marginX = screenWidth * 0.05;
            marginY = screenHeight * 0.05;
            canvas.setFrame(0, 0, screenWidth - 2*marginX, screenHeight - 2*marginY);
        } else if(screenWidth > screenHeight) {
            double tmp = screenWidth - screenHeight;
            marginX = tmp/2 + screenHeight * 0.05;
            marginY = screenHeight * 0.05;
            canvas.setFrame(0, 0, screenHeight - 2*marginY, screenHeight - 2*marginY);
        } else if(screenHeight > screenWidth){
            double tmp = screenHeight - screenWidth;
            marginX = screenWidth * 0.05;
            marginY = tmp/2 + screenWidth * 0.05;
            canvas.setFrame(0, 0, screenHeight - 2*marginY, screenHeight - 2*marginY);
        }

        double width = canvas.getWidth();
        double height = canvas.getHeight();
        Scale s = new Scale((width/2), 1.51E12);
        boolean correction = true;
        for(MovingObject p : planets) {
        	String name = p.getName();
        	Vector3D w = (Vector3D) convert(p.getPosition(), s);
            w = (Vector3D) w.add(new Vector3D(width/2, width/2, width/2));
            w = (Vector3D) w.add(new Vector3D(marginX, marginY, 0)); 
            if(FactoryProvider.getSettingRepository().isGuiFormatting()) {
		        if(name.equals("Mercury")) {
		        	w = (Vector3D) w.addMul(2.3, w.sub(new Vector3D(screenWidth/2, screenHeight/2,0)));
		        }
		        if(name.equals("Venus")) {
		        	w = (Vector3D) w.addMul(2.3, w.sub(new Vector3D(screenWidth/2, screenHeight/2,0)));
		        }
		        if(name.equals("Earth") || name.equals("Moon")) {
		        	w = (Vector3D) w.addMul(2.8, w.sub(new Vector3D(screenWidth/2, screenHeight/2,0)));
		        }
		        if(name.equals("Moon")) {
		        	MovingObject earth = planets.stream().filter(planet -> planet.getName().equals("Earth")).findFirst().orElse(null);
		        	w = (Vector3D) w.addMul(70, w.sub(earth.getPosition()));
		            System.out.println(p.getName() + " "+p.getPosition());
		        }
		        if(name.equals("Mars")) {
		        	w = (Vector3D) w.addMul(3, w.sub(new Vector3D(screenWidth/2, screenHeight/2,0)));
		        }
		        if(name.equals("Jupiter")) {
		        	w = (Vector3D) w.addMul(0.4, w.sub(new Vector3D(screenWidth/2, screenHeight/2,0)));
		        }
		        if(name.equals("Titan")) {
		        	MovingObject saturn = planets.stream().filter(planet -> planet.getName().equals("Saturn")).findFirst().orElse(null);
		        	w = (Vector3D) w.addMul(100, w.sub(saturn.getPosition()));
		        }
            }
	        p.setPosition(w);
        }
        
    	return planets;
    }
//    public static Vector3dInterface convertToPixel(Vector3dInterface v, double screenWidth, double screenHeight, String name) {
//        Rectangle2D canvas = new Rectangle2D.Double();
//        double marginX = 0, marginY = 0;
//        if(screenWidth == screenHeight) {
//            marginX = screenWidth * 0.05;
//            marginY = screenHeight * 0.05;
//            canvas.setFrame(0, 0, screenWidth - 2*marginX, screenHeight - 2*marginY);
//        } else if(screenWidth > screenHeight) {
//            double tmp = screenWidth - screenHeight;
//            marginX = tmp/2 + screenHeight * 0.05;
//            marginY = screenHeight * 0.05;
//            canvas.setFrame(0, 0, screenHeight - 2*marginY, screenHeight - 2*marginY);
//        } else if(screenHeight > screenWidth){
//            double tmp = screenHeight - screenWidth;
//            marginX = screenWidth * 0.05;
//            marginY = tmp/2 + screenWidth * 0.05;
//            canvas.setFrame(0, 0, screenHeight - 2*marginY, screenHeight - 2*marginY);
//        }
//
//        double width = canvas.getWidth();
//        double height = canvas.getHeight();
//        Scale s = new Scale((width/2), 1.51E12);
//        Vector3D w = (Vector3D) convert(v, s);
//        w = (Vector3D) w.add(new Vector3D(width/2, width/2, width/2));
//        w = (Vector3D) w.add(new Vector3D(marginX, marginY, 0));
//
//        if(name.equals("Mercury")) {
//        	w = (Vector3D) w.addMul(1.5, w.sub(new Vector3D(screenWidth/2, screenHeight/2,0)));
//        }
//        if(name.equals("Venus")) {
//        	System.out.println("VENUS"+w.sub(new Vector3D(screenWidth/2, screenHeight/2,0)));
//        	w = (Vector3D) w.addMul(3, w.sub(new Vector3D(screenWidth/2, screenHeight/2,0)));
//        }
//        if(name.equals("Earth") || name.equals("Moon")) {
//        	System.out.println("EARTHLINGS"+w);
//        	System.out.println("EARTHLINGS"+w.sub(new Vector3D(screenWidth/2, screenHeight/2,0)));
//        	w = (Vector3D) w.addMul(2.8, w.sub(new Vector3D(screenWidth/2, screenHeight/2,0)));
//        	System.out.println("EARTHLINGS"+w);
//        }
//        if(name.equals("Moon")) {
//        }
//        if(name.equals("Mars")) {
//        	w = (Vector3D) w.addMul(1.8, w.sub(new Vector3D(screenWidth/2, screenHeight/2,0)));
//        }
//        return w;
//    }
    
//    public static Vector3dInterface convertToPixel(Vector3dInterface v, double screenWidth, double screenHeight) {
//        Rectangle2D canvas = new Rectangle2D.Double();
//        double marginX = 0, marginY = 0;
//        if(screenWidth == screenHeight) {
//            marginX = screenWidth * 0.05;
//            marginY = screenHeight * 0.05;
//            canvas.setFrame(0, 0, screenWidth - 2*marginX, screenHeight - 2*marginY);
//        } else if(screenWidth > screenHeight) {
//            double tmp = screenWidth - screenHeight;
//            marginX = tmp/2 + screenHeight * 0.05;
//            marginY = screenHeight * 0.05;
//            canvas.setFrame(0, 0, screenHeight - 2*marginY, screenHeight - 2*marginY);
//        } else if(screenHeight > screenWidth){
//            double tmp = screenHeight - screenWidth;
//            marginX = screenWidth * 0.05;
//            marginY = tmp/2 + screenWidth * 0.05;
//            canvas.setFrame(0, 0, screenHeight - 2*marginY, screenHeight - 2*marginY);
//        }
//
//        double width = canvas.getWidth();
//        double height = canvas.getHeight();
//        Scale s = new Scale((width/2), 1.51E12);
//        Vector3D w = (Vector3D) convert(v, s);
//        w = (Vector3D) w.add(new Vector3D(width/2, width/2, width/2));
//        w = (Vector3D) w.add(new Vector3D(marginX, marginY, 0));
//        return w;
//    }

    public static Vector3dInterface convert(Vector3dInterface v, Scale scale) {
        double x = scale.getScale()*v.getX();
        double y = scale.getScale()*v.getY();
        double z = scale.getScale()*v.getZ();
        return new Vector3D(x, y, z);
    }
}
