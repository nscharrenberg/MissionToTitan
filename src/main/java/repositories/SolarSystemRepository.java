package repositories;

import domain.MovingObject;
import domain.Planet;
import factory.FactoryProvider;
import interfaces.StateInterface;
import interfaces.Vector3dInterface;
import physics.gravity.ODEFunction;
import physics.gravity.ODESolver;
import physics.gravity.State;
import domain.SpaceCraft;
import domain.Vector3D;
import repositories.interfaces.SolarSystemInterface;
import utils.PlanetReader;
import utils.gravitytest.GravityTest;

import java.util.*;

public class SolarSystemRepository implements SolarSystemInterface {
    private List<MovingObject> planets = new ArrayList<>();
    private List<List<MovingObject>> timeLine = new ArrayList<>();

    protected static double daySec = 60*24*60; // total seconds in a day
    protected static double t;
    protected static double dt = 0.01*daySec;

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

        State start = (State) timeLineArray[3][0];
        State goal = (State)  timeLineArray[8][0];

//        for (int i = 0 ;i < timeLineArray[0].length; i++) {
//            if ( i*dt/daySec < 300) {
//                State probe = (State) timeLineArray[9][i];
//                State jupiter = (State) timeLineArray[7][i];
//                System.out.println( "Distance : " + probe.getPosition().sub(jupiter.getPosition()).norm());
//            }
//        }

        Vector3dInterface unit = unitVecToGoal(start.getPosition(), goal.getPosition());
        System.out.println(planets.get(3).getPosition().add(unit.mul(6371000)));
        System.out.println(planets.get(3).getVelocity().add(unit.mul(6000)));

        FactoryProvider.getSolarSystemFactory().init();
        odes = new ODESolver(FactoryProvider.getSolarSystemFactory());
        odef = new ODEFunction(FactoryProvider.getSolarSystemFactory());
        timeLineArray = odes.getData(odef,totalTime, dt);

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

    private Vector3dInterface unitVecToGoal(Vector3dInterface start, Vector3dInterface goal) {
        Vector3dInterface aim = goal.sub(start); // vector between earth and goal
        return aim.mul(1.0/aim.norm());
    }


    @Override
    public List<List<MovingObject>> getTimeLine() {
        return timeLine;
    }
}
