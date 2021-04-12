package org.um.dke.titan.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import org.um.dke.titan.domain.Moon;
import org.um.dke.titan.domain.Planet;
import org.um.dke.titan.domain.SpaceObject;
import org.um.dke.titan.domain.SpaceObjectEnum;
import org.um.dke.titan.factory.FactoryProvider;

public class FileImporter {
    public static void load(String name) {
        JsonReader jsonReader = new JsonReader();
        JsonValue base = jsonReader.parse(Gdx.files.internal(String.format("data/%s.json", name)));

        JsonValue planets = base.get("planets");
//        JsonValue rockets = base.get("rockets");

        for (JsonValue planet : planets) {
            SpaceObjectEnum found = SpaceObjectEnum.getByName(planet.get("name").asString());
            float zoomLevel = (float) ((planet.get("radius").asFloat() * 2) / 1e2);

            if (planet.has("zoomLevel")) {
                zoomLevel = planet.get("zoomLevel").asFloat();
            }

            Planet p = new Planet(found.getName(), planet.get("mass").asFloat(), planet.get("radius").asFloat(), new Vector3(planet.get("x").asFloat(),planet.get("y").asFloat(),0), zoomLevel);
            p.setTexture(found.getTexturePath());

            FactoryProvider.getSolarSystemRepository().addPlanet(found.getName(), p);

            if (planet.has("moons")) {
                for (JsonValue moon : planet.get("moons")) {
                    SpaceObjectEnum foundMoon = SpaceObjectEnum.getByName(moon.get("name").asString());

                    float zoomLevelMoon = (float) ((planet.get("radius").asFloat() * 2) / 1e2);

                    if (moon.has("zoomLevel")) {
                        zoomLevelMoon = moon.get("zoomLevel").asFloat();
                    }

                    Moon m = new Moon(foundMoon.getName(), moon.get("mass").asFloat(), moon.get("radius").asFloat(), new Vector3(moon.get("x").asFloat(),moon.get("y").asFloat(),0), zoomLevelMoon, p);
                    m.setTexture(foundMoon.getTexturePath());
                    p.addMoon(foundMoon.getName(), m);
                }
            }
        }

//        for (JsonValue rocket: rockets) {
//
//        }
    }
}
