package gui.javafx.utils;

import interfaces.gui.IDrawableContext;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class BlankDrawingDetail extends DrawingDetail {
    public BlankDrawingDetail() {
        super(null);
    }

    public BlankDrawingDetail(Color color) {
        super(color);
    }

    @Override
    public void getDetails(IDrawableContext context) {
        // Do nothing, as this is for a blank context.
    }
}
