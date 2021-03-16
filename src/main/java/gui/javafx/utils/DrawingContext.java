package gui.javafx.utils;

import interfaces.gui.IDrawableContext;
import interfaces.gui.IShape;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class DrawingContext implements IDrawableContext {
    private Canvas canvas;
    private GraphicsContext context;

    public DrawingContext(Group root) {
        this.canvas = new Canvas();
        root.getChildren().add(canvas);
        this.context = canvas.getGraphicsContext2D();
    }

    @Override
    public void save() {
        this.context.save();
    }

    @Override
    public void reset() {
        this.context.restore();
        this.context.setFill(null);
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public GraphicsContext getContext() {
        return context;
    }

    public void drawShape(IShape shape) {
        shape.draw(this);
    }

    public void changeCanvasSizeFromScene(Scene scene) {
        canvas.widthProperty().bind(scene.widthProperty());
        canvas.heightProperty().bind(scene.heightProperty());
    }
}
