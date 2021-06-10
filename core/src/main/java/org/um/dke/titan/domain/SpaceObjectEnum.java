package org.um.dke.titan.domain;

import java.util.Arrays;

public enum SpaceObjectEnum {
    EARTH("Earth", "planets/Earth.png", 0),
    MOON("Moon", "planets/Moon.png", 1),
    MARS("Mars", "planets/Mars.png", 2),
    Neptune("Neptune", "planets/Neptune.png", 3),
    JUPITER("Jupiter", "planets/Jupiter.png", 4),
    SATURN("Saturn", "planets/Saturn.png", 5),
    TITAN("Titan", "planets/Titan.png", 6),
    VENUS("Venus", "planets/Venus.png", 7),
    URANUS("Uranus","planets/Uranus.png", 8),
    SUN("Sun", "planets/Sun.png", 9),
    MERCURY("Mercury", "planets/Mercury.png", 10),
    SHIP("Probe", "planets/Probe.png", 11),
    LANDER("Lander", "planets/Probe.png", 12);


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
