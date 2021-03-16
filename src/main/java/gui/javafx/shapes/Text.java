package gui.javafx.shapes;

import gui.javafx.utils.DrawingDetail;
import interfaces.gui.*;
import javafx.application.Platform;
import javafx.scene.paint.Color;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

// TODO: Might want to do some input validation.
public class Text implements IDrawable, ITimer, IRemovable {
    public static final Color DEFAULT_COLOR = Color.WHITE;
    public static final double DEFAULT_SIZE = 12;
    public static final int DEFAULT_LIFETIME = 10;

    private String text;
    private IPoint point;
    private double size = DEFAULT_SIZE;
    private DrawingDetail drawingDetail = new DrawingDetail(DEFAULT_COLOR);
    private int lifetime = DEFAULT_LIFETIME;

    public Text(String text) {
        this.text = text;
    }

    public Text(String text, IPoint point) {
        this.text = text;
        this.point = point;
    }

    public Text(String text, IPoint point, double size, int lifetime, DrawingDetail drawingDetail) {
        this.text = text;
        this.point = point;
        this.size = size;
        this.lifetime = lifetime;
        this.drawingDetail = drawingDetail;
    }

    public Text(String text, IPoint point, double size, int lifetime, Color color) {
        this.text = text;
        this.point = point;
        this.size = size;
        this.lifetime = lifetime;
        this.drawingDetail = new DrawingDetail(color);
    }

    private void init() {
        // TODO: Register Item

        setTimer(this.lifetime);
    }

    @Override
    public void draw(IDrawableContext context) {

    }

    @Override
    public void setTimer(int interval) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> remove());
            }
        }, interval);
    }

    @Override
    public void update() {
        // Do Nothing
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public IPoint getPoint() {
        return point;
    }

    public void setPoint(IPoint point) {
        this.point = point;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public DrawingDetail getDrawingDetail() {
        return drawingDetail;
    }

    public void setDrawingDetail(DrawingDetail drawingDetail) {
        this.drawingDetail = drawingDetail;
    }

    public int getLifetime() {
        return lifetime;
    }

    public void setLifetime(int lifetime) {
        this.lifetime = lifetime;
    }

    @Override
    public void remove() {
        // TODO: Remove from registered items in Manager Class
    }

    @Override
    public boolean hasParent() {
        return true;
    }

    @Override
    public String toString() {
        return "Text{" +
                "text='" + text + '\'' +
                ", point=" + point +
                ", size=" + size +
                ", drawingDetail=" + drawingDetail +
                ", lifetime=" + lifetime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Text)) return false;
        Text text1 = (Text) o;
        return Double.compare(text1.size, size) == 0 &&
                lifetime == text1.lifetime &&
                text.equals(text1.text) &&
                point.equals(text1.point) &&
                drawingDetail.equals(text1.drawingDetail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, point, size, drawingDetail, lifetime);
    }
}
