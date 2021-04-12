package org.um.dke.titan.repositories;

import org.um.dke.titan.domain.Moon;
import org.um.dke.titan.domain.Planet;

import java.util.HashMap;
import java.util.Map;

public class SolarSystemRepository implements org.um.dke.titan.repositories.interfaces.ISolarSystemRepository {
    private Map<String, Planet> planets;

    public SolarSystemRepository() {
        this.planets = new HashMap<>();
    }

    @Override
    public void addPlanet(String name, Planet planet) {
        this.planets.put(name, planet);
    }

    @Override
    public void removePlanet(String name) {
        this.planets.remove(name);
    }

    @Override
    public Map<String, Planet> getPlanets() {
        return planets;
    }

    @Override
    public void setPlanets(Map<String, Planet> planets) {
        this.planets = planets;
    }

    @Override
    public Planet getPlanetByName(String name) {
        return this.planets.get(name);
    }

    @Override
    public Moon getMoonByName(String planetName, String moonName) {
        return this.planets.get(planetName).getMoons().get(moonName);
    }
}
