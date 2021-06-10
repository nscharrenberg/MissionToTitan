package org.um.dke.titan.physicsold.ode.solvers;

import org.um.dke.titan.domain.*;
import org.um.dke.titan.factory.FactoryProvider;
import org.um.dke.titan.interfaces.*;
import org.um.dke.titan.physicsold.ode.Rate;
import org.um.dke.titan.physicsold.ode.State;
import org.um.dke.titan.repositories.interfaces.ISolarSystemRepository;

import java.util.ArrayList;
import java.util.List;

public class ODESolverVerlet implements ODESolverInterface, DataInterface {
    protected ISolarSystemRepository system; // repository for all planets.
    protected List<MovingObject> planets;
    protected StateInterface[][] timelineArray; // 2d array containing all states of all planets.
    protected int currentPlanetIndex;
    protected int size;

    public ODESolverVerlet(){
        this.system = FactoryProvider.getSolarSystemRepository();
    }

    @Override
    public StateInterface[] solve(ODEFunctionInterface f, StateInterface y0, double[] ts) {
        size = ts.length;
        if(size == 0) {
            throw new IllegalArgumentException();
        }
        addInitialStates();
        for (int i = 1; i < size; i++) {
            int j = 0;
            for(MovingObject planet : planets)
            {
                int time = 0;
                for (int k = 0; k < i; k++) {
                    time += ts[k];
                }
                // inserting step into the array
                timelineArray[j][i] = step(f, time, timelineArray[j][i - 1], ts[i]-ts[i-1]);
                State state = (State) timelineArray[j][i];

                if (planet instanceof Rocket) {
                    // updating the MovingObject's (Planet) state
                    system.getRocketByName(planet.getName()).setPosition(state.getPosition());
                    system.getRocketByName(planet.getName()).setVelocity(state.getVelocity());

                    j++;
                } else {
                    // updating the MovingObject's (Planet) state
                    system.getPlanetByName(planet.getName()).setPosition(state.getPosition());
                    system.getPlanetByName(planet.getName()).setVelocity(state.getVelocity());
                    j++;

                    if (planet instanceof Planet) {
                        Planet p = (Planet) planet;

                        for (Moon moon : p.getMoons().values()) {
                            // inserting step into the array
                            timelineArray[j][i] = step(f, time, timelineArray[j][i - 1], ts[i]-ts[i-1]);
                            State stateMoon = (State) timelineArray[j][i];

                            system.getMoonByName(planet.getName(), moon.getName()).setPosition(stateMoon.getPosition());
                            system.getMoonByName(planet.getName(), moon.getName()).setVelocity(stateMoon.getVelocity());
                            j++;
                        }
                    }
                }
            }
        }
        return timelineArray[currentPlanetIndex];
    }

    @Override
    public StateInterface[] solve(ODEFunctionInterface f, StateInterface y0, double tf, double h) {
        size = (int)Math.round(tf/h)+1;
        init(y0);
        addInitialStates();
        computeStates(f,h);
        return timelineArray[currentPlanetIndex];
    }

    /**
     * determines and initializes variables needed for calculating new states.
     */
    protected void init(StateInterface y0) {
        for (Planet planet : system.getPlanets().values()) {
            planets.add(planet);
            planets.addAll(planet.getMoons().values());
        }

        planets.addAll(system.getRockets().values());

        timelineArray = new StateInterface[planets.size()][size];
        currentPlanetIndex = getIndexOfPlanet((State)y0);
    }

    protected void init(double tf, double h) {

        this.planets = new ArrayList<>();

        for (Planet planet : system.getPlanets().values()) {
            planets.add(planet);
            planets.addAll(planet.getMoons().values());
        }

        planets.addAll(system.getRockets().values());

        size = (int)Math.round(tf/h)+1;
        timelineArray = new StateInterface[planets.size()][size];
    }

    /**
     * determine the index of the planet of y0 in the planets list.
     */
    protected int getIndexOfPlanet(State y0) {
        for (int i = 0; i <planets.size(); i++)
            if (planets.get(i).getName().equals(y0.getMovingObject().getName()))
                return i;
        return timelineArray.length-1; // if it's not a planet, it returns the last index, which is the index of the probe
    }

    /**
     * setting t[0] in the allStates array
     * for all planets to their initial state
     */
    protected void addInitialStates() {
        for (int i = 0; i < planets.size(); i++) {
            StateInterface state = new State(planets.get(i).getPosition(), planets.get(i).getVelocity(), new Vector3D(0,0,0), planets.get(i));
            timelineArray[i][0] = state;
        }
    }

    /**
     * computes all states of all planets for t[1]
     * and up and adds them in the allStates array
     */
    protected void computeStates(ODEFunctionInterface f, double h) {
        for (int i = 1; i < size; i++) {
            int j = 0;
            for(MovingObject planet : system.getPlanets().values())
            {
                // inserting step into the array
                timelineArray[j][i] = step(f, h*i, timelineArray[j][i - 1], h);
                State state = (State) timelineArray[j][i];

                // updating the MovingObject's (Planet) state
                system.getPlanetByName(planet.getName()).setPosition(state.getPosition());
                system.getPlanetByName(planet.getName()).setVelocity(state.getVelocity());
                j++;

                if (planet instanceof Planet) {
                    Planet p = (Planet) planet;
                    for (Moon moon : p.getMoons().values()) {
                        // inserting step into the array
                        timelineArray[j][i] = step(f, h*i, timelineArray[j][i - 1], h);
                        State stateMoon = (State) timelineArray[j][i];

                        system.getMoonByName(planet.getName(), moon.getName()).setPosition(stateMoon.getPosition());
                        system.getMoonByName(planet.getName(), moon.getName()).setVelocity(stateMoon.getVelocity());
                        j++;
                    }
                }
            }

            for (Rocket rocket : system.getRockets().values()) {
                // inserting step into the array
                timelineArray[j][i] = step(f, h*i, timelineArray[j][i - 1], h);
                State state = (State) timelineArray[j][i];

                // updating the MovingObject's (Planet) state
                system.getRocketByName(rocket.getName()).setPosition(state.getPosition());
                system.getRocketByName(rocket.getName()).setVelocity(state.getVelocity());
                j++;
            }
        }
    }

    @Override
    /** t>0, t < 0; t = 0
     *  h >0; h < 0; h = 0
     *  y = null
     *
     */
    public StateInterface step(ODEFunctionInterface f, double t, StateInterface y, double h) {
        if(y == null) {
            throw new NullPointerException();
        }
        Rate rate = (Rate)f.call(h, y);
        State state = (State)y;
        Vector3dInterface newPosition = state.getPosition().add(state.getVelocity().mul(h)).add(state.getAcceleration().mul(h*h/2));
        Vector3dInterface newVelocity = rate.getVelocity();
        Vector3dInterface newAcceleration = rate.getAcceleration();
        return new State(newPosition, newVelocity, newAcceleration, state.getMovingObject());
    }

    @Override
    public StateInterface[][] getData(ODEFunctionInterface f, double tf, double h) {
        init(tf, h);
        addInitialStates();
        computeStates(f, h);
        return timelineArray;
    }

    public int getCurrentPlanetIndex(){
        return currentPlanetIndex;
    }
}
