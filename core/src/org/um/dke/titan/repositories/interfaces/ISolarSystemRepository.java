package org.um.dke.titan.repositories.interfaces;

import org.um.dke.titan.domain.Moon;
import org.um.dke.titan.domain.MovingObject;
import org.um.dke.titan.domain.Planet;

import java.util.Map;

public interface ISolarSystemRepository {
    void addPlanet(String name, Planet planet);

    void removePlanet(String name);

    Map<String, Planet> getPlanets();

    void setPlanets(Map<String, Planet> planets);

    Planet getPlanetByName(String name);

    Moon getMoonByName(String planetName, String moonName);

    void init();

    void preprocessing();
}
