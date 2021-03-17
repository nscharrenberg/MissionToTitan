
package repositories;

import domain.Planet;
import domain.Vector3D;
import repositories.interfaces.SolarSystemInterface;
import utils.PlanetReader;
import utils.converter.PositionConverter;

import java.util.*;

public class SolarSystemRepository implements SolarSystemInterface {
    private List<Planet> planets = new ArrayList<>();
    private double width = 700;
    private double height = 700;

    @Override
    public void init() {
        ArrayList<Planet> planets = PlanetReader.getPlanets();
        setPlanets(planets);
//        sampleSolarSystem();
    }

    private void sampleSolarSystem() {
        addPlanet(new Planet(50, 10, new Vector3D(100, 100, 10), new Vector3D(105, 105, 15), "earth"));
        addPlanet(new Planet(50, 20, new Vector3D(300, 350, 50), new Vector3D(310, 345, 10), "sun"));
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
