package org.um.dke.titan.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import org.um.dke.titan.domain.*;
import org.um.dke.titan.factory.FactoryProvider;
import org.um.dke.titan.interfaces.Vector3dInterface;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class FileImporter {
    public static String planetsFileName = "data_20200401";
    public static String horizonFileNAme = "horizonData_Sun";

    public static Map<String, Planet> load() {
        JsonReader jsonReader = new JsonReader();
        JsonValue base = jsonReader.parse(Gdx.files.internal(String.format("data/%s.json", planetsFileName)));

        JsonValue planets = base.get("planets");
        JsonValue rockets = base.get("rockets");

        Map<String, Planet> planetMap = new HashMap<>();

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

            planetMap.put(found.getName(), p);

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

        return planetMap;
    }

    public static HashMap<Integer, Vector3dInterface> importHorizon(String filename) throws ParseException {
        JsonReader jsonReader = new JsonReader();
        JsonValue base = jsonReader.parse(Gdx.files.internal(String.format("experimental/%s.json", filename)));

        int dtVal = base.get("stepSizeValue").asInt();
        String dtType = base.get("stepSizeType").asString();
        int dtInSeconds = convertToSeconds(dtVal, dtType);

        JsonValue data = base.get("data");

        HashMap<Integer, Vector3dInterface> timeline = new HashMap<>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH);
        Date startDate = sdf.parse("2020-04-01 00:00");

        for (JsonValue row : data) {
            String time = row.get("time").asString();
            double x = row.get("x").asDouble();
            double y = row.get("y").asDouble();
            double z = row.get("z").asDouble();

            Date currentDate = sdf.parse(time);
            long diffInMillies = Math.abs(currentDate.getTime() - startDate.getTime());
            int timeInSeconds = (int)TimeUnit.SECONDS.convert(diffInMillies, TimeUnit.MILLISECONDS);

            timeline.put(timeInSeconds, new Vector3D(x, y, z));
        }

        return timeline;
    }

    private static int convertToSeconds(int value, String type) {
        switch (type) {
            case "minute":
                return value * 60;
            case "hour": {
                return value * 60 * 60;
            }
            case "day": {
                return value * 60 * 60 * 24;
            }
            case "week": {
                return value * 60 * 60 * 24 * 7;
            }
            default:
                return value;
        }
    }
}
