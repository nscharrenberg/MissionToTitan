package gui.javafx.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import interfaces.gui.IDrawableContext;
import interfaces.gui.IDrawableDetails;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

public class DrawingDetailImage implements IDrawableDetails{
	private String path;
	private Image image;
	private ImagePattern pattern;

    public DrawingDetailImage(String path) {
        this.path = path;
        try {
			image = new Image(new FileInputStream(path));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        pattern = new ImagePattern(image);
    }

    @Override
    public void getDetails(IDrawableContext context) {
       if (!(context instanceof DrawingContext)) {
           return;
       }

       GraphicsContext gc = ((DrawingContext) context).getContext();
       fill(gc);
    }

    private void fill(GraphicsContext context) {
        if (pattern == null) {
            context.setFill(Color.BLACK);
        }

        context.setFill(pattern);
    }
	
}
