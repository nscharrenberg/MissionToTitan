package repositories;

import domain.Planet;
import domain.Vector3D;
import repositories.interfaces.SolarSystemInterface;
import utils.PlanetReader;
import utils.converter.PositionConverter;

import java.util.*;

public class SolarSystemRepository implements SolarSystemInterface {
    private List<Planet> planets = new ArrayList<>();

    @Override
    public void init() {
        double width = 500;
        double height = 500;
//        // TODO: Maybe we should import the data file to initialize planets?
        ArrayList<Planet> planets = PlanetReader.getPlanets();
        setPlanets(planets);
    }

    @Override
    public List<Planet> getPlanets() {
        return this.planets;
    }

    @Override
    public void addPlanet(Planet planet) {
        this.planets.add(planet);
    }

    @Override
    public void addPlanets(Planet... planets) {
        for (Planet planet : planets) {
            addPlanet(planet);
        }
    }

    private void setPlanets(List<Planet> planets) {
        this.planets = planets;
    }

    @Override
    public boolean removePlanet(String name) {
        if (this.planets.size() <= 0) {
            return false;
        }

        Planet planet = findPlanet(name);

        if (planet == null) {
            return false;
        }

        return this.planets.remove(planet);
    }

    @Override
    public Planet findPlanet(String name) {
        if (this.planets.size() <= 0) {
            return null;
        }

        if (this.planets.size() == 1) {
            return this.planets.get(0).getName().equals(name) ? this.planets.get(0) : null;
        }

        return this.planets.stream().filter(p -> p.getName().equals(name)).findFirst().orElse(null);
    }
}
