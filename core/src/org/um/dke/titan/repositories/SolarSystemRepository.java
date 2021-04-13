package org.um.dke.titan.repositories;

import org.um.dke.titan.domain.Moon;
import org.um.dke.titan.domain.MovingObject;
import org.um.dke.titan.domain.Planet;
import org.um.dke.titan.factory.FactoryProvider;
import org.um.dke.titan.interfaces.ODEFunctionInterface;
import org.um.dke.titan.interfaces.StateInterface;
import org.um.dke.titan.physics.ode.State;
import org.um.dke.titan.physics.ode.functions.ODEFunction;
import org.um.dke.titan.physics.ode.solvers.ODESolver;
import org.um.dke.titan.utils.FileImporter;

import java.util.*;

public class SolarSystemRepository implements org.um.dke.titan.repositories.interfaces.ISolarSystemRepository {
    private Map<String, Planet> planets;
    private StateInterface[][] timeLineArray;

    public SolarSystemRepository() {
        this.planets = new HashMap<>();
    }

    @Override
    public void addPlanet(String name, Planet planet) {
        this.planets.put(name, planet);
    }

    @Override
    public void removePlanet(String name) {
        this.planets.remove(name);
    }

    @Override
    public Map<String, Planet> getPlanets() {
        return planets;
    }

    @Override
    public void setPlanets(Map<String, Planet> planets) {
        this.planets = planets;
    }

    @Override
    public Planet getPlanetByName(String name) {
        return this.planets.get(name);
    }

    @Override
    public Moon getMoonByName(String planetName, String moonName) {
        return this.planets.get(planetName).getMoons().get(moonName);
    }

    @Override
    public void init() {
        FileImporter.load("data_20200401");
    }

    @Override
    public void preprocessing() {
        Map<String, List<MovingObject>> timeline = new HashMap<>();
        this.planets = new HashMap<>();
        double totalTime = 365 * 60 * 24 * 60;
        double dt = 100;

        FactoryProvider.getSolarSystemRepository().init();

        timeLineArray = getTimeLineArray(totalTime, dt);

        StateInterface[] tmp2 = timeLineArray[0];
        int length = tmp2.length;

        for (int i = 0; i < length; i++) {
            Queue<MovingObject> tmp = new LinkedList<>();

            for (int j = 0; j < timeLineArray.length; j++) {
                State state = (State) timeLineArray[j][i];
                MovingObject sio = state.getMovingObject();
                String name = sio.getName().getText().toString();
                if (sio instanceof Planet) {
                    FactoryProvider.getSolarSystemRepository().getPlanetByName(name).add(new Planet(sio.getName().getText().toString(), sio.getMass(), sio.getRadius(), state.getPosition(), sio.getZoomLevel(), state.getVelocity()));
                } else if (sio instanceof Moon) {
                    Planet planet = ((Moon) sio).getPlanet();
                    String planetName = planet.getName().getText().toString();
                    FactoryProvider.getSolarSystemRepository().getMoonByName(planetName, name).add(new Moon(sio.getName().getText().toString(), sio.getMass(), sio.getRadius(), state.getPosition(), sio.getZoomLevel(), state.getVelocity(), planet));
                }
            }
        }
    }

    private StateInterface[][] getTimeLineArray(double totalTime, double dt) {
        if (timeLineArray == null) {
            computeTimeLineArray(totalTime, dt);
        } else if (timeLineArray[0].length != (int)(Math.round(totalTime/dt))+1) {
            computeTimeLineArray(totalTime, dt);
        }

        return timeLineArray;
    }

    private void computeTimeLineArray(double totalTime, double dt) {
        ODESolver odes = new ODESolver();
        ODEFunctionInterface odef = new ODEFunction();
        timeLineArray =  odes.getData(odef, totalTime, dt);
    }
}
