package repositories;

import domain.MovingObject;
import domain.Planet;
import factory.FactoryProvider;
import interfaces.StateInterface;
import physics.gravity.ODEFunction;
import physics.gravity.ODESolver;
import physics.gravity.State;
import domain.SpaceCraft;
import domain.Vector3D;
import physics.gravity.ProbeSimulator;
import repositories.interfaces.SolarSystemInterface;
import utils.PlanetReader;

import java.util.*;

public class SolarSystemRepository implements SolarSystemInterface {
    private List<Planet> planets = new ArrayList<>();
    private List<List<MovingObject>> timeLine = new ArrayList<>();

    protected static double daySec = 60*24*60; // total seconds in a day
    protected static double t;
    protected static double dt = 0.01*daySec;
    private SpaceCraft probe;
  
    @Override
    public void init() {
        ArrayList<Planet> planets = PlanetReader.getPlanets();
        setPlanets(planets);
        probe = new SpaceCraft(0, new Vector3D(0,0,0),new Vector3D(0,0,0),"Probe");
//        sampleSolarSystem();
    }

    private void sampleSolarSystem() {
        addPlanet(new Planet(50, 10, new Vector3D(100, 100, 10), new Vector3D(105, 105, 15), "earth"));
        addPlanet(new Planet(50, 20, new Vector3D(300, 350, 50), new Vector3D(310, 345, 10), "sun"));
    }

    @Override
    public List<Planet> getPlanets() {
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

    private void setPlanets(List<Planet> planets) {
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
            return this.planets.get(0).getName().equals(name) ? this.planets.get(0) : null;
        }

        return this.planets.stream().filter(p -> p.getName().equals(name)).findFirst().orElse(null);
    }
    /**
     * Something logical
     */
    @Override
    public void preprocessing() {
        this.timeLine = new ArrayList<>();
        this.planets = new ArrayList<>();
        double totalTime = FactoryProvider.getSettingRepository().getYearCount() * FactoryProvider.getSettingRepository().getDayCount() * daySec;
        ODESolver odes = new ODESolver(FactoryProvider.getSolarSystemFactory());
        ODEFunction odef = new ODEFunction(FactoryProvider.getSolarSystemFactory());
        StateInterface[][] timeLineArray = odes.getData(odef,totalTime, dt);
//
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

    @Override
    public List<List<MovingObject>> getTimeLine() {
        return timeLine;
    }

    public SpaceCraft getProbe() {
        return probe;
    }

    public void setProbe(SpaceCraft probe) {
        this.probe = probe;
    }
}
