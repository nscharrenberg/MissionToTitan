package domain;

import gui.javafx.utils.DrawingDetail;
import interfaces.gui.IDrawableDetails;
import javafx.scene.paint.Color;

public enum PlanetEnum {
    EARTH("earth", new DrawingDetail(Color.GREEN)),
    SUN("sun", new DrawingDetail(Color.ORANGE));

    private PlanetEnum(String name, IDrawableDetails detail) {
        this.name = name;
        this.detail = detail;
    }

    private String name;
    private IDrawableDetails detail;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public IDrawableDetails getDetail() {
        return detail;
    }

    public void setDetail(IDrawableDetails detail) {
        this.detail = detail;
    }

    public static PlanetEnum getByName(String name) {
        PlanetEnum[] values = PlanetEnum.values();

        for (PlanetEnum val : values) {
            if (val.getName().toLowerCase().equals(name.toLowerCase())) {
                return val;
            }
        }

        return null;
    }
}
