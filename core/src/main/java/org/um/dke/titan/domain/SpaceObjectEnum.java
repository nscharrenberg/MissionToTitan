package org.um.dke.titan.domain;

import java.util.Arrays;

public enum SpaceObjectEnum {
    SUN("Sun", "planets/Sun.png", 0),
    MERCURY("Mercury", "planets/Mercury.png", -1),
    VENUS("Venus", "planets/Venus.png", -1),
    EARTH("Earth", "planets/Earth.png", 1),
    MOON("Moon", "planets/Moon.png", 2),
    MARS("Mars", "planets/Mars.png", -1),
    JUPITER("Jupiter", "planets/Jupiter.png", 3),
    SATURN("Saturn", "planets/Saturn.png", 4),
    TITAN("Titan", "planets/Titan.png", 5),
    SHIP("Probe", "planets/Probe.png", 6),
    SHIP_2("OtherProbe", "planets/Probe.png", -1);

    private final int id;
    private final String name;
    private final String texturePath;

    SpaceObjectEnum(String name, String texturePath, int id) {
        this.name = name;
        this.texturePath = texturePath;
        this.id = id;
    }

    public static SpaceObjectEnum getByName(final String name) {
        return Arrays.stream(SpaceObjectEnum.values()).filter(v -> v.getName().equals(name)).findFirst().orElse(null);
    }

    public String getName() {
        return name;
    }

    public String getTexturePath() {
        return texturePath;
    }

    public int getId() {
        return id;
    }
}
