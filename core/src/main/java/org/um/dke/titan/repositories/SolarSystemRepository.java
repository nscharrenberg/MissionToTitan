package org.um.dke.titan.repositories;

import org.um.dke.titan.domain.*;
import org.um.dke.titan.factory.FactoryProvider;
import org.um.dke.titan.interfaces.ODEFunctionInterface;
import org.um.dke.titan.interfaces.StateInterface;
import org.um.dke.titan.interfaces.Vector3dInterface;
import org.um.dke.titan.physicsold.ode.ProbeSimulator;
import org.um.dke.titan.physicsold.ode.State;
import org.um.dke.titan.physicsold.ode.functions.ODEFunction;
import org.um.dke.titan.physicsold.ode.functions.ODEVerletFunction;
import org.um.dke.titan.physicsold.ode.solvers.ODESolverR4;
import org.um.dke.titan.physicsold.ode.solvers.ODESolver;
import org.um.dke.titan.physicsold.ode.solvers.ODESolverVerlet;
import org.um.dke.titan.repositories.interfaces.ISolarSystemRepository;
import org.um.dke.titan.utils.FileImporter;

import java.util.*;

public class SolarSystemRepository{
    private Map<String, Planet> planets;
    private Map<String, Rocket> rockets;
    private StateInterface[][] timeLineArray;


    public SolarSystemRepository() {
        this.planets = new HashMap<>();
        this.rockets = new HashMap<>();
    }

    public void addPlanet(String name, Planet planet) {
        this.planets.put(name, planet);
    }

    public void removePlanet(String name) {
        this.planets.remove(name);
    }

    public Map<String, Planet> getPlanets() {
        return planets;
    }

    public void setPlanets(Map<String, Planet> planets) {
        this.planets = planets;
    }

    public Planet getPlanetByName(String name) {
        return this.planets.get(name);
    }

    public Moon getMoonByName(String planetName, String moonName) {
        return this.planets.get(planetName).getMoons().get(moonName);
    }

    public void initWithGdx() {
        FileImporter.load();
    }

    public void init() {
        FileImporter.load();
    }

    public void preprocessing() {
        Map<String, List<MovingObject>> timeline = new HashMap<>();
        double totalTime = 365 * 60 * 24 * 60;
        double dt = 20;

        timeLineArray = getTimeLineArray(totalTime, dt);

        timeLineArray = getTimeLineArray(totalTime ,dt);

        ProbeSimulator simulator = new ProbeSimulator();


        // initial state for the probe
        Vector3dInterface[] probeArray = simulator.trajectory(new Vector3D(-1.471922101663588e+11, -2.860995816266412e+10, 8.278183193596080e+06),((State)timeLineArray[0][0]).getVelocity().add(new Vector3D(41878.56337407961,-28602.250664987056,-885.8769882128352)),totalTime, dt);
        StateInterface[] tmp2 = timeLineArray[0];

        int length = tmp2.length;

        //TODO: remove this method/print
        double min = Double.MAX_VALUE;
        int minI = 0;
        for (int i = 0; i < timeLineArray[0].length; i++) {
            State titan = (State) timeLineArray[SpaceObjectEnum.TITAN.getId()][i];
            Vector3dInterface probe = probeArray[i];
            double dist = probe.dist(titan.getPosition()) - 6371000;

            if (i > 1000000 && min > dist && dist > 0) {
                min = dist;
                minI = i;
            }

        }
        System.out.println(min);
        System.out.println("minI = " + minI);

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < timeLineArray.length; j++) {
                State state = (State) timeLineArray[j][i];
                MovingObject sio = state.getMovingObject();
                String name = sio.getName();
                if (sio instanceof Planet) {
                    FactoryProvider.getSolarSystemRepository().getPlanetByName(name).add(state.getPosition());
                } else if (sio instanceof Moon) {
                    Planet planet = ((Moon) sio).getPlanet();
                    String planetName = planet.getName();
                    FactoryProvider.getSolarSystemRepository().getMoonByName(planetName, name).add(state.getPosition());
                } else if (sio instanceof Rocket) {
                    FactoryProvider.getSolarSystemRepository().getRocketByName(name).add(probeArray[i]);
                }
            }
        }
    }

    public StateInterface[][] getTimeLineArray(double totalTime, double dt) {
        if (timeLineArray == null) {
            computeTimeLineArrayR(totalTime, dt);
        } else if (timeLineArray[0].length != (int)(Math.round(totalTime/dt))+1) {
            computeTimeLineArrayR(totalTime, dt);
        }
        return timeLineArray;
    }

    public void computeTimeLineArray(double totalTime, double dt) {
        ODESolver odes = new ODESolver();
        ODEFunctionInterface odef = new ODEFunction();
        timeLineArray =  odes.getData(odef, totalTime, dt);
    }

    public void computeTimeLineArrayV(double totalTime, double dt) {
        ODESolverVerlet odes = new ODESolverVerlet();
        ODEFunctionInterface odef = new ODEVerletFunction();
        timeLineArray =  odes.getData(odef, totalTime, dt);
    }

    public void computeTimeLineArrayR(double totalTime, double dt) {
        ODESolverR4 odes = new ODESolverR4();
        ODEFunctionInterface odef = new ODEFunction();
        timeLineArray =  odes.getData(odef, totalTime, dt);
    }

    public Map<String, Rocket> getRockets() {
        return rockets;
    }

    public void setRockets(Map<String, Rocket> rockets) {
        this.rockets = rockets;
    }

    public Rocket getRocketByName(String name) {
        return this.rockets.get(name);
    }

    public void addRocket(String name, Rocket object) {
        this.rockets.put(name, object);
    }

    public StateInterface[][] getTimeLineArray() {
        return timeLineArray;
    }

    public void setTimeLineArray(StateInterface[][] timeLineArray) {
        this.timeLineArray = timeLineArray;
    }

}
