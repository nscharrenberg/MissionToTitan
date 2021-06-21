package org.um.dke.titan.repositories;

import org.um.dke.titan.domain.*;
import org.um.dke.titan.factory.FactoryProvider;
import org.um.dke.titan.interfaces.ODESolverInterface;
import org.um.dke.titan.interfaces.StateInterface;
import org.um.dke.titan.interfaces.Vector3dInterface;
import org.um.dke.titan.physics.ode.functions.solarsystemfunction.ODEFunction;
import org.um.dke.titan.physics.ode.functions.solarsystemfunction.PlanetState;
import org.um.dke.titan.physics.ProbeSimulator;
import org.um.dke.titan.physics.ode.functions.solarsystemfunction.SystemState;
import org.um.dke.titan.repositories.interfaces.ISolarSystemRepository;
import org.um.dke.titan.utils.FileImporter;


import java.util.HashMap;
import java.util.Map;

public class SolarSystemRepository implements ISolarSystemRepository {

    private Map<String, Planet> planets;
    private Map<String, Rocket> rockets;


    private StateInterface[] timeLineArray;
    private ODESolverInterface solver;
    private double dt;
    private double tf;
    double[] ts;


    public void preprocessing() {
        tf = 60 * 60 * 24 * 450;
        dt = 50;
        getTimeLineArray(FactoryProvider.getSolver(), tf, dt);
        deployRockets(tf, dt);
//        deployLander(tf, dt);
    }

    public void init() {
        rockets = new HashMap<>();
        planets = FileImporter.load();
    }

    public double getDt() {
        return dt;
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
        org.um.dke.titan.physics.ode.functions.solarsystemfunction.SystemState y0 = getInitialSystemState();
        timeLineArray = solver.solve(new ODEFunction(), y0, tf, dt);
        this.solver = solver;
        this.tf = tf;
        this.dt = dt;
    }

    private void runSolver(ODESolverInterface solver, double ts[]) {
        org.um.dke.titan.physics.ode.functions.solarsystemfunction.SystemState y0 = getInitialSystemState();
        timeLineArray = solver.solve(new ODEFunction(), y0, ts);
        this.solver = solver;
        this.ts = ts;
    }

    private void deployRockets(double tf, double dt) {


        for (Map.Entry<String, Rocket> entry: this.rockets.entrySet()) {
            ProbeSimulator probeSimulator = new ProbeSimulator();
            Vector3D destination = (Vector3D) ((SystemState)timeLineArray[0]).getPlanet("Titan").getPosition();//.add(new Vector3D(-0.3, 0.7, 0).getUnit().mul(2574700 + 300000));

            Vector3D velocity = (Vector3D) entry.getValue().getVelocity();

            Vector3D earthVelocity = new Vector3D(5.427193405797901e+03, -2.931056622265021e+04, 6.575428158157592e-01);
            //velocity = new Vector3D(0.4257580681316204,-0.1255899174838238,0.6790064383709731); working on 500dt
            velocity = new Vector3D(0.5979983770225301,-0.41522775759194325,0.05921359817599511);
            velocity = (Vector3D) velocity.mul(1/velocity.norm()).mul(40020);
            velocity = (Vector3D) velocity.add(earthVelocity);
            System.out.println(velocity);


            Vector3D probeStart = (Vector3D) entry.getValue().getPosition();

            probeSimulator.trajectory(probeStart,velocity, tf, dt);
            PlanetState[] probeArray = probeSimulator.stateTrajectory();

            Vector3D min = (Vector3D) destination.sub(probeArray[0].getPosition());
            int minI = 0;

            for (int i = 0; i < probeArray.length; i++) {
                Vector3D probePos = (Vector3D) probeArray[i].getPosition();
                Vector3D planetPos = (Vector3D) ((SystemState)timeLineArray[i]).getPlanet("Titan").getPosition();

                if (min.norm() > probePos.dist(planetPos)) {
                    min = (Vector3D) planetPos.sub(probePos);
                    minI = i;
                }
            }

            System.out.println("MIN: " + (min.norm()-2574700) + " ::: " + minI);

            //System.out.println(NewtonRaphson.get(probeStart, destination));

            // adding the rockets to the system state
            for (int i = 0; i < timeLineArray.length; i++) {
                PlanetState state = new PlanetState();
                state.setPosition(probeArray[i].getPosition());
                state.setVelocity(probeArray[i].getVelocity());
                ((SystemState)timeLineArray[i]).setPlanet(entry.getKey(), state);
            }
        }
    }

    private void deployLander(double tf, double dt) {
        for (Map.Entry<String, Rocket> entry: this.rockets.entrySet()) {
            ProbeSimulator probeSimulator = new ProbeSimulator();
            Vector3dInterface[] probeArray = probeSimulator.trajectory(entry.getValue().getPosition(), entry.getValue().getVelocity(), tf, dt);

            // adding the rockets to the system state
            for (int i = 0; i < timeLineArray.length; i++) {
                PlanetState state = new PlanetState();
                state.setPosition(probeArray[i]);
                state.setVelocity(new Vector3D(0,0,0));
                ((SystemState)timeLineArray[i]).setPlanet(entry.getKey(), state);
            }

            System.out.println(((SystemState)timeLineArray[458941]).getPlanets());

        }
    }

    public SystemState getInitialSystemState() {
        Map<String, PlanetState> states = new HashMap<>();

        for (Planet planet : planets.values()) {

            states.put(planet.getName(), new PlanetState(planet.getPosition(), planet.getVelocity()));
        }
        return new org.um.dke.titan.physics.ode.functions.solarsystemfunction.SystemState(states);
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
