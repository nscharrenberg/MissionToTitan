package gui.javafx.utils;

import interfaces.gui.IDrawableContext;
import interfaces.gui.IDrawableDetails;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawingDetail implements IDrawableDetails {
    Color color;

    public DrawingDetail(Color color) {
        this.color = color;
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
        if (color == null) {
            context.setFill(Color.BLACK);
        }

        context.setFill(color);
    }


}
