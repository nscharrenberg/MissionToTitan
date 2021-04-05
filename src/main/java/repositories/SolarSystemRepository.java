package repositories;

import domain.MovingObject;
import domain.Planet;
import factory.FactoryProvider;
import interfaces.ODEFunctionInterface;
import interfaces.StateInterface;
import physics.gravity.ode.ODEFunction;
import physics.gravity.ode.ODESolver;
import physics.gravity.ode.State;
import domain.Vector3D;
import repositories.interfaces.SolarSystemInterface;
import utils.PlanetReader;

import java.util.*;

public class SolarSystemRepository implements SolarSystemInterface {
    private List<MovingObject> planets = new ArrayList<>();
    private List<List<MovingObject>> timeLine = new ArrayList<>();

    private static double daySec = 60*24*60; // total seconds in a day
    private static double dt = 0.1*daySec;
    private StateInterface[][] timeLineArray;

    @Override
    public void init() {
        ArrayList<MovingObject> planets = PlanetReader.getPlanets();
        setPlanets(planets);
    }

    private void sampleSolarSystem() {
        addPlanet(new Planet(50, 10, new Vector3D(100, 100, 10), new Vector3D(105, 105, 15), "earth"));
        addPlanet(new Planet(50, 20, new Vector3D(300, 350, 50), new Vector3D(310, 345, 10), "sun"));
    }

    @Override
    public List<MovingObject> getPlanets() {
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

    private void setPlanets(List<MovingObject> planets) {
        this.planets = planets;
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
            MovingObject tmp = this.planets.get(0);
            if (tmp instanceof Planet) {
                return this.planets.get(0).getName().equals(name) ? (Planet)tmp : null;
            }
        }
        MovingObject tmp = this.planets.stream().filter(p -> p.getName().equals(name)).findFirst().orElse(null);
        if (tmp instanceof Planet) {
            return (Planet)tmp;
        }
       return null;
    }

    @Override
    public void preprocessing() {
        this.timeLine = new ArrayList<>();
        this.planets = new ArrayList<>();
        double totalTime = FactoryProvider.getSettingRepository().getYearCount() * FactoryProvider.getSettingRepository().getDayCount() * daySec;

        FactoryProvider.getSolarSystemFactory().init();
        timeLineArray = getTimeLineArray(totalTime, dt);

        StateInterface[] tmp2 = timeLineArray[0];
        int length = tmp2.length;
        for(int i = 0; i < length; i++) {
        	ArrayList<MovingObject> tmp = new ArrayList<MovingObject>();
        	for(int j = 0; j < timeLineArray.length; j++) {
        		State state = (State)timeLineArray[j][i];
        		MovingObject sio = state.getMovingObject();
        		tmp.add(new MovingObject(sio.getMass(), state.getPosition(), state.getVelocity(), sio.getName()));
        	}
        	timeLine.add(tmp);
        }
    }

    public StateInterface[][] getTimeLineArray(double totalTime, double dt) {
        if (this.timeLineArray == null) {
            computeTimeLineArray(totalTime, dt);
        }
        else if (this.timeLineArray[0].length != (int)(Math.round(totalTime/dt))+1) {
            computeTimeLineArray(totalTime, dt);
        }
        return timeLineArray;
    }

    private void computeTimeLineArray(double totalTime, double dt) {
        ODESolver odes = new ODESolver();
        ODEFunctionInterface odef = new ODEFunction();
        timeLineArray = odes.getData(odef, totalTime, dt);
    }

    @Override
    public List<List<MovingObject>> getTimeLine() {
        return timeLine;
    }
}
