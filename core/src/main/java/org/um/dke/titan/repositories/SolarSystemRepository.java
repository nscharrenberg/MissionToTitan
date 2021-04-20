package org.um.dke.titan.repositories;

import com.badlogic.gdx.files.FileHandle;
import org.um.dke.titan.domain.Moon;
import org.um.dke.titan.domain.MovingObject;
import org.um.dke.titan.domain.Planet;
import org.um.dke.titan.domain.Rocket;
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
    private Map<String, Rocket> rockets;
    private StateInterface[][] timeLineArray;

    public SolarSystemRepository() {
        this.planets = new HashMap<>();
        this.rockets = new HashMap<>();
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
    public void initWithGdx() {
        FileImporter.load("data_20200401");
    }

    @Override
    public void init() {
        FileImporter.load("data_20200401");
    }

    @Override
    public void preprocessing() {
        Map<String, List<MovingObject>> timeline = new HashMap<>();
        double totalTime = 365 * 60 * 24 * 60;
        double dt = 100;

        timeLineArray = getTimeLineArray(totalTime, dt);

        StateInterface[] tmp2 = timeLineArray[0];
        int length = tmp2.length;

        for (int i = 0; i < length; i++) {
            Queue<MovingObject> tmp = new LinkedList<>();

            for (int j = 0; j < timeLineArray.length; j++) {
                State state = (State) timeLineArray[j][i];
                MovingObject sio = state.getMovingObject();
                String name = sio.getName();
                if (sio instanceof Planet) {
                    FactoryProvider.getSolarSystemRepository().getPlanetByName(name).add(new Planet(sio.getName(), sio.getMass(), sio.getRadius(), state.getPosition(), sio.getZoomLevel(), state.getVelocity()));
                } else if (sio instanceof Moon) {
                    Planet planet = ((Moon) sio).getPlanet();
                    String planetName = planet.getName();
                    FactoryProvider.getSolarSystemRepository().getMoonByName(planetName, name).add(new Moon(sio.getName(), sio.getMass(), sio.getRadius(), state.getPosition(), sio.getZoomLevel(), state.getVelocity(), planet));
                } else if (sio instanceof Rocket) {
                    FactoryProvider.getSolarSystemRepository().getRocketName(name).add(new Rocket(sio.getName(), sio.getMass(), sio.getRadius(), state.getPosition(), sio.getZoomLevel(), state.getVelocity()));
                }
            }
        }
    }

    @Override
    public StateInterface[][] getTimeLineArray(double totalTime, double dt) {
        if (timeLineArray == null) {
            computeTimeLineArray(totalTime, dt);
        } else if (timeLineArray[0].length != (int)(Math.round(totalTime/dt))+1) {
            computeTimeLineArray(totalTime, dt);
        }

        return timeLineArray;
    }

    @Override
    public void computeTimeLineArray(double totalTime, double dt) {
        ODESolver odes = new ODESolver();
        ODEFunctionInterface odef = new ODEFunction();
        timeLineArray =  odes.getData(odef, totalTime, dt);
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
    public Rocket getRocketName(String name) {
        return this.rockets.get(name);
    }

    @Override
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
