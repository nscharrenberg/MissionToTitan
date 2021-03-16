package repositories;

import domain.Planet;
import domain.Vector3D;
import repositories.interfaces.SolarSystemInterface;
import utils.PositionConverter;

import java.util.*;

public class SolarSystemRepository implements SolarSystemInterface {
    private List<Planet> planets = new ArrayList<>();

    @Override
    public void init() {
        double width = 500;
        double height = 500;
//        // TODO: Maybe we should import the data file to initialize planets?
        Planet sun = new Planet(1.988500e30, PositionConverter.convertToPixel(new Vector3D(-6.806783239281648e+08, 1.080005533878725e+09, 6.564012751690170e+06), width, height), PositionConverter.convertToPixel(new Vector3D(-1.420511669610689e+01, -4.954714716629277e+00, 3.994237625449041e-01), width, height), "Sun");
        Planet earth = new Planet(5.97219e24, new Vector3D(-1.471922101663588e+11, -2.860995816266412e+10, 8.278183193596080e+06), new Vector3D(5.427193405797901e+03, -2.931056622265021e+04, 6.575428158157592e-01), "Earth");
        earth.addMoon(7.349e22, new Vector3D(-1.472343904597218e+11, -2.822578361503422e+10, 1.052790970065631e+07), new Vector3D(4.433121605215677e+03, -2.948453614110320e+04, 8.896598225322805e+01), "Luna");

        addPlanets(sun, earth);
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
