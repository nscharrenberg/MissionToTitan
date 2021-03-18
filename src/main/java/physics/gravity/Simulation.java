package physics.gravity;

import domain.Planet;
import domain.SpaceCraft;
import interfaces.ODEFunctionInterface;
import repositories.SolarSystemRepository;

import java.util.ArrayList;

public class Simulation {

    private SolarSystemRepository system;
    private ArrayList<Planet> planets;

    public Simulation(SolarSystemRepository system) {
        this.system = system;
        planets = (ArrayList<Planet>) system.getPlanets();
    }

}
