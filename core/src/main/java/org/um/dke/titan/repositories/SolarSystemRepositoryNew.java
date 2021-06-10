package org.um.dke.titan.repositories;

import org.um.dke.titan.domain.*;
import org.um.dke.titan.interfaces.ODESolverInterface;
import org.um.dke.titan.interfaces.StateInterface;
import org.um.dke.titan.interfaces.Vector3dInterface;
import org.um.dke.titan.physics.ode.functions.planetfunction.ODEFunction;
import org.um.dke.titan.physics.ode.functions.planetfunction.PlanetState;
import org.um.dke.titan.physics.ode.functions.planetfunction.SystemState;
import org.um.dke.titan.physics.ode.solvers.ODESolver;
import org.um.dke.titan.physics.ProbeSimulator;
import org.um.dke.titan.repositories.interfaces.ISolarSystemRepository;
import org.um.dke.titan.utils.FileImporter;


import java.util.HashMap;
import java.util.Map;

public class SolarSystemRepositoryNew implements ISolarSystemRepository {

    private Map<String, Planet> planets;
    private Map<String, Rocket> rockets;

    private StateInterface[] timeLineArray;
    private ODESolverInterface solver;
    private double dt;
    private double tf;
    double[] ts;


    /**
     * TODO: add function have multiple rockets be added at once.
     * right now, it wont work with more than 1 rocket.
     */


    public void preprocessing() {
        double tf = 60 * 60 * 25 * 365;
        double dt = 100;
        getTimeLineArray(new ODESolver(), tf, dt);
        deployRockets(tf, dt);
    }

    public void init() {
        rockets = new HashMap<>();
        planets = FileImporter.load();
    }






    // --------------------- ODE Handling  ---------------------

    public StateInterface[] getTimeLineArray(ODESolverInterface solver, double tf, double dt) {
        int size = (int)(Math.round(tf/dt))+1;

        if (timeLineArray == null)
            runSolver(solver, tf, dt);
        else if  (timeLineArray.length == 0 ||  timeLineArray.length != size)
            runSolver(solver, tf, dt);

        return timeLineArray;
    }

    public StateInterface[] getTimeLineArray(ODESolverInterface solver, double ts[]) {
        int size = ts.length;

        if (timeLineArray == null)
            runSolver(solver, ts);
        else if  (timeLineArray.length == 0 ||  timeLineArray.length != size)
            runSolver(solver, ts);

        return timeLineArray;
    }

    public StateInterface[] getTimeLineArray() {
        return timeLineArray;
    }

    private void runSolver(ODESolverInterface solver, double tf, double dt) {
        SystemState y0 = getInitialSystemState();
        timeLineArray = solver.solve(new ODEFunction(), y0, tf, dt);
        this.solver = solver;
        this.tf = tf;
        this.dt = dt;
    }

    private void runSolver(ODESolverInterface solver, double ts[]) {
        SystemState y0 = getInitialSystemState();
        timeLineArray = solver.solve(new ODEFunction(), y0, ts);
        this.solver = solver;
        this.ts = ts;
    }

    private void deployRockets(double tf, double dt) {
        for (Map.Entry<String, Rocket> entry: this.rockets.entrySet()) {
            ProbeSimulator probeSimulator = new ProbeSimulator();
            Vector3dInterface[] probeArray = probeSimulator.trajectory(entry.getValue().getPosition(), entry.getValue().getVelocity(), tf, dt);

            // adding the rockets to the system state
            for (int i = 0; i < timeLineArray.length; i++) {
                PlanetState state = new PlanetState();
                state.setPosition(probeArray[i]);
                ((SystemState)timeLineArray[i]).setPlanet(entry.getKey(), state);
            }
        }
    }

    public SystemState getInitialSystemState() {
        Map<String, PlanetState> states = new HashMap<>();

        for (Planet planet : planets.values()) {

            states.put(planet.getName(), new PlanetState(planet.getPosition(), planet.getVelocity()));
        }
        return new SystemState(states);
    }

    public void refresh() {
        planets = FileImporter.load();

        if (ts == null) {
            runSolver(solver, tf, dt);
        } else if (tf == 0 && dt == 0) {
            runSolver(solver, ts);
        }

    }




    // --------------------- getters / setters  ---------------------

    public Map<String, Planet> getPlanets() {
        return planets;
    }

    public Planet getPlanetByName(String name) {
        return this.planets.get(name);
    }

    public Moon getMoonByName(String planetName, String moonName) {
        return this.planets.get(planetName).getMoons().get(moonName);
    }

    public Map<String, Rocket> getRockets() {
        return rockets;
    }

    public void setRockets(Map<String, Rocket> rockets) {
        this.rockets = rockets;
    }

    public Rocket getRocketByName(String name) {
        return rockets.get(name);
    }

    public void addRocket(String name, Rocket rocket) {
        rockets.put(name, rocket);
    }


}
