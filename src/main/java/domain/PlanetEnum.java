package domain;

import gui.javafx.utils.DrawingDetail;
import gui.javafx.utils.DrawingDetailImage;
import interfaces.gui.IDrawableDetails;
import javafx.scene.paint.Color;

public enum PlanetEnum {
	SUN("sun", new DrawingDetailImage("src/main/resources/sprites/Sun.png")),
	MERCURY("mercury", new DrawingDetailImage("src/main/resources/sprites/Mercury.png")),
	VENUS("venus", new DrawingDetailImage("src/main/resources/sprites/Venus.png")),
    EARTH("earth", new DrawingDetailImage("src/main/resources/sprites/Earth.png")),
    MOON("moon", new DrawingDetailImage("src/main/resources/sprites/Moon.png")),
    MARS("mars", new DrawingDetailImage("src/main/resources/sprites/Mars.png")),
    JUPITER("jupiter", new DrawingDetailImage("src/main/resources/sprites/Jupiter.png")),
    SATURN("saturn", new DrawingDetailImage("src/main/resources/sprites/Saturn.png")),
    TITANT("titan", new DrawingDetailImage("src/main/resources/sprites/Titan.png")),
	SHIP("Probe", new DrawingDetailImage("src/main/resources/sprites/spaceship.png"));

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
