package org.um.dke.titan.repositories;

import org.um.dke.titan.domain.Moon;
import org.um.dke.titan.domain.MovingObject;
import org.um.dke.titan.domain.Planet;
import org.um.dke.titan.domain.Rocket;
import org.um.dke.titan.factory.FactoryProvider;
import org.um.dke.titan.interfaces.ODESolverInterface;
import org.um.dke.titan.interfaces.StateInterface;
import org.um.dke.titan.physics.ode.functions.planetfunction.ODEFunction;
import org.um.dke.titan.physics.ode.functions.planetfunction.PlanetState;
import org.um.dke.titan.physics.ode.functions.planetfunction.SystemState;
import org.um.dke.titan.physics.ode.solvers.ODESolver;
import org.um.dke.titan.physics.ode.solvers.ODESolverR4;
import org.um.dke.titan.physicsold.ode.State;
import org.um.dke.titan.repositories.interfaces.ISolarSystemRepository;
import org.um.dke.titan.utils.FileImporter;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class SolarSystemRepositoryNew implements ISolarSystemRepository {

    private Map<String, Planet> planets;
    private Map<String, Rocket> rockets;
    private StateInterface[] timeLineArray;


    public void init() {
        rockets = new HashMap<>();
        planets = FileImporter.load();
    }

    public Map<String, Planet> getPlanets() {
        return planets;
    }

    public Planet getPlanetByName(String name) {
        return this.planets.get(name);
    }

    public Moon getMoonByName(String planetName, String moonName) {
        return this.planets.get(planetName).getMoons().get(moonName);
    }

    @Override
    public Map<String, Rocket> getRockets() {
        return rockets;
    }

    @Override
    public void setRockets(Map<String, Rocket> rockets) {
        this.rockets = rockets;
    }

    @Override
    public Rocket getRocketByName(String name) {
        return rockets.get(name);
    }

    @Override
    public void addRocket(String name, Rocket rocket) {
        rockets.put(name, rocket);
    }

    @Override
    public void preprocessing() {
        double tf = 60 * 60 * 25 * 365;
        double dt = 100;
        getTimeLineArray(new ODESolverR4(), tf, dt);

    }

    public StateInterface[] getTimeLineArray() {
        return timeLineArray;
    }

    @Override
    public StateInterface[] getTimeLineArray(ODESolverInterface solver, double tf, double dt) {
        int size = (int)(Math.round(tf/dt))+1;

        if (timeLineArray == null) {
            runSolver(solver, tf, dt);

        } else if  (timeLineArray.length == 0 ||  timeLineArray.length != size) {
            runSolver(solver, tf, dt);
        }
        return timeLineArray;
    }

    private void runSolver(ODESolverInterface solver, double tf, double dt) {
        SystemState y0 = getInitialSystemState();
        timeLineArray = solver.solve(new ODEFunction(), y0, tf, dt);
    }

    public SystemState getInitialSystemState() {
        Map<String, PlanetState> states = new HashMap<>();

        for (Planet planet : planets.values()) {

            for (Moon moon : planet.getMoons().values()) {
                states.put(moon.getName(), new PlanetState(moon.getPosition(), moon.getVelocity()));
            }

            states.put(planet.getName(), new PlanetState(planet.getPosition(), planet.getVelocity()));
        }
        return new SystemState(states);
    }


}
