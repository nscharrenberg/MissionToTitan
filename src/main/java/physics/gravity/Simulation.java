package physics.gravity;

import domain.Planet;
import domain.SpaceCraft;
import repositories.SolarSystemRepository;

import java.util.ArrayList;

public class Simulation {

    private SolarSystemRepository system;
    private ArrayList<Planet> planets;

    public Simulation(SolarSystemRepository system) {
        this.system = system;
        planets = (ArrayList<Planet>) system.getPlanets();
    }

    public void simulate() {
        SpaceCraft probe = new SpaceCraft(1000,planets.get(4).getPosition(), planets.get(4).getVelocity().add(planets.get(4).getPosition().sub(planets.get(5).getPosition()).mul(1.0e-6)),"Probe");
        system.setProbe(probe);
    }

}
