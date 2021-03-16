package utils;


import java.awt.geom.Rectangle2D;

import domain.Vector3D;
import interfaces.Vector3dInterface;

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
	
	public static Vector3dInterface convertToPixel(Vector3dInterface v, double screenWidth, double screenHeight) {
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
		System.out.println(marginX + " "+ marginY);
		System.out.println(canvas.getWidth() + " "+ canvas.getHeight());
		double width = canvas.getWidth();
		double height = canvas.getHeight();
		Scale s = new Scale((width/2), 1.51E12);
		Vector3D w = (Vector3D) convert(v, s);
		w = (Vector3D) w.add(new Vector3D(width/2, width/2, width/2));
		w = (Vector3D) w.add(new Vector3D(marginX, marginY, 0));
		return w;
	}
	
	public static Vector3dInterface convert(Vector3dInterface v, Scale scale) {
		double x = scale.getScale()*v.getX();
		double y = scale.getScale()*v.getY();
		double z = scale.getScale()*v.getZ();
		return new Vector3D(x, y, z);
	}
}
