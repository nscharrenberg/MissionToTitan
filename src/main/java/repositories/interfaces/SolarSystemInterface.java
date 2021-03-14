package main.java.repositories.interfaces;

import main.java.domain.Planet;

import java.util.List;

public interface SolarSystemInterface {
    List<Planet> getPlanets();
    void addPlanet(Planet planet);
    void addPlanets(Planet... planets);
    boolean removePlanet(String name);
    Planet findPlanet(String name);
    void init();
}
