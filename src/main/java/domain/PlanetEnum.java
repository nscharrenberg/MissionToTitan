package domain;

import gui.javafx.utils.DrawingDetail;
import gui.javafx.utils.DrawingDetailImage;
import interfaces.gui.IDrawableDetails;
import javafx.scene.paint.Color;

public enum PlanetEnum {
	SUN("sun", new DrawingDetailImage("src/main/resources/sprites/Sun.png"), 0),
	MERCURY("mercury", new DrawingDetailImage("src/main/resources/sprites/Mercury.png"),-1),
	VENUS("venus", new DrawingDetailImage("src/main/resources/sprites/Venus.png"), -1),
    EARTH("earth", new DrawingDetailImage("src/main/resources/sprites/Earth.png"), 1),
    MOON("moon", new DrawingDetailImage("src/main/resources/sprites/Moon.png"), 2),
    MARS("mars", new DrawingDetailImage("src/main/resources/sprites/Mars.png"),-1),
    JUPITER("jupiter", new DrawingDetailImage("src/main/resources/sprites/Jupiter.png"),3),
    SATURN("saturn", new DrawingDetailImage("src/main/resources/sprites/Saturn.png"),4),
    TITANT("titan", new DrawingDetailImage("src/main/resources/sprites/Titan.png"),5),
	SHIP("Probe", new DrawingDetailImage("src/main/resources/sprites/Probe.png"),6);

    private PlanetEnum(String name, IDrawableDetails detail, int id) {
        this.name = name;
        this.detail = detail;
        this.id = id;
    }

    private String name;
    private IDrawableDetails detail;
    private int id;

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

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
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
