package physics.gravity;

import domain.MovingObject;
import domain.Planet;
import domain.SpaceCraft;
import interfaces.ODEFunctionInterface;
import repositories.SolarSystemRepository;

import java.util.ArrayList;
import java.util.List;

public class Simulation {

    private SolarSystemRepository system;
    private List<MovingObject> planets;

    public Simulation(SolarSystemRepository system) {
        this.system = system;
        planets = system.getPlanets();
    }

}
