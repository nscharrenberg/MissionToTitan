package org.um.dke.titan.domain;

import java.util.Arrays;

public enum SpaceObjectEnum {
    SUN("Sun", "planets/Sun.png"),
    MERCURY("Mercury", "planets/Mercury.png"),
    VENUS("Venus", "planets/Venus.png"),
    EARTH("Earth", "planets/Earth.png"),
    MOON("Moon", "planets/Moon.png"),
    MARS("Mars", "planets/Mars.png"),
    JUPITER("Jupiter", "planets/Jupiter.png"),
    SATURN("Saturn", "planets/Saturn.png"),
    TITAN("Titan", "planets/Titan.png"),
    SHIP("Probe", "planets/Probe.png");

    private final String name;
    private final String texturePath;

    SpaceObjectEnum(String name, String texturePath) {
        this.name = name;
        this.texturePath = texturePath;
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
}
