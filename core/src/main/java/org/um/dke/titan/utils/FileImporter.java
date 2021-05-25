package org.um.dke.titan.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import org.um.dke.titan.domain.*;
import org.um.dke.titan.factory.FactoryProvider;

public class FileImporter {
    public static void load(String name) {
        JsonReader jsonReader = new JsonReader();
        JsonValue base = jsonReader.parse(Gdx.files.internal(String.format("data/%s.json", name)));

        JsonValue planets = base.get("planets");
        JsonValue rockets = base.get("rockets");

        for (JsonValue planet : planets) {
            SpaceObjectEnum found = SpaceObjectEnum.getByName(planet.get("name").asString());
            float zoomLevel = (float) ((planet.get("radius").asFloat() * 2) / 1e2);

            if (planet.has("zoomLevel")) {
                zoomLevel = planet.get("zoomLevel").asFloat();
            }

            Planet p = new Planet(found.getName(), planet.get("mass").asFloat(), planet.get("radius").asFloat(), new Vector3D(planet.get("x").asDouble(),planet.get("y").asDouble(),planet.get("z").asDouble()), zoomLevel, new Vector3D(planet.get("vx").asDouble(),planet.get("vy").asDouble(),planet.get("vz").asDouble()));

            if(FactoryProvider.getGameRepository().isGdx()) {
                p.setTexture(found.getTexturePath());
            }

            FactoryProvider.getSolarSystemRepository().addPlanet(found.getName(), p);

            if (planet.has("moons")) {
                for (JsonValue moon : planet.get("moons")) {
                    SpaceObjectEnum foundMoon = SpaceObjectEnum.getByName(moon.get("name").asString());

                    float zoomLevelMoon = (float) ((planet.get("radius").asFloat() * 2) / 1e2);

                    if (moon.has("zoomLevel")) {
                        zoomLevelMoon = moon.get("zoomLevel").asFloat();
                    }

                    Moon m = new Moon(foundMoon.getName(), moon.get("mass").asFloat(), moon.get("radius").asFloat(), new Vector3D(moon.get("x").asDouble(),moon.get("y").asDouble(),moon.get("z").asDouble()), zoomLevelMoon, new Vector3D(moon.get("vx").asDouble(),moon.get("vy").asDouble(),moon.get("vz").asDouble()), p);
                    if(FactoryProvider.getGameRepository().isGdx()) {
                        m.setTexture(foundMoon.getTexturePath());
                    }

                    p.addMoon(foundMoon.getName(), m);
                }
            }
        }

        for (JsonValue rocket: rockets) {
            SpaceObjectEnum found = SpaceObjectEnum.getByName(rocket.get("name").asString());
            float zoomLevel = (float) ((rocket.get("radius").asFloat() * 2) / 1e2);

            if (rocket.has("zoomLevel")) {
                zoomLevel = rocket.get("zoomLevel").asFloat();
            }

            Rocket r = new Rocket(found.getName(), rocket.get("mass").asFloat(), rocket.get("radius").asFloat(), new Vector3D(rocket.get("x").asDouble(),rocket.get("y").asDouble(),rocket.get("z").asDouble()), zoomLevel, new Vector3D(rocket.get("vx").asDouble(),rocket.get("vy").asDouble(),rocket.get("vz").asDouble()));
            if(FactoryProvider.getGameRepository().isGdx()) {
                r.setTexture(found.getTexturePath());
            }

            FactoryProvider.getSolarSystemRepository().addRocket(found.getName(), r);
        }
    }
}
