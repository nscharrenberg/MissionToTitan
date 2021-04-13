package org.um.dke.titan.physics.ode.solvers;

import org.um.dke.titan.domain.MovingObject;
import org.um.dke.titan.domain.Planet;
import org.um.dke.titan.domain.Vector3D;
import org.um.dke.titan.factory.FactoryProvider;
import org.um.dke.titan.interfaces.*;
import org.um.dke.titan.physics.ode.Rate;
import org.um.dke.titan.physics.ode.State;
import org.um.dke.titan.repositories.interfaces.ISolarSystemRepository;

import java.util.ArrayList;
import java.util.List;

public class ODERungeSolver implements ODESolverInterface,DataInterface {

    protected ISolarSystemRepository system; // repository for all planets.
    protected List<MovingObject> planets;
    protected StateInterface[][] timelineArray; // 2d array containing all states of all planets.
    protected int currentPlanetIndex;
    protected int size;

    public ODERungeSolver(){
        this.system = FactoryProvider.getSolarSystemRepository();
    }

    @Override
    public StateInterface[] solve(ODEFunctionInterface f, StateInterface y0, double[] ts) {
        size = ts.length;
        addInitialStates();
        for (int i = 1; i < size; i++) {
            int j = 0;
            for( MovingObject planet : planets)
            {
                int time = 0;
                for (int k = 0; k < i; k++) {
                    time += ts[k];
                }
                // inserting step into the array
                timelineArray[j][i] = step(f, time, timelineArray[j][i - 1], ts[i]-ts[i-1]);
                State state = (State) timelineArray[j][i];

                // updating the MovingObject's (Planet) state
                system.getPlanets().get(planet.getName().getText().toString()).setPosition(state.getPosition());
                system.getPlanets().get(planet.getName().getText().toString()).setVelocity(state.getVelocity());
                j++;
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
        system.init();
        planets = new ArrayList<>(system.getPlanets().values());
        timelineArray = new StateInterface[planets.size()][size];
        currentPlanetIndex = getIndexOfPlanet((State)y0);
    }

    protected void init(double tf, double h) {
        system.init();
        planets = new ArrayList<>(system.getPlanets().values());
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
            for( MovingObject planet : planets)
            {
                // inserting step into the array
                timelineArray[j][i] = step(f, h*i, timelineArray[j][i - 1], h);
                State state = (State) timelineArray[j][i];

                // updating the MovingObject's (Planet) state
                system.getPlanets().get(planet.getName().getText().toString()).setPosition(state.getPosition());
                system.getPlanets().get(planet.getName().getText().toString()).setVelocity(state.getVelocity());
                j++;
            }
        }
    }

    @Override
    public StateInterface step(ODEFunctionInterface f, double t, StateInterface y, double h) {
        Rate k1 = (Rate) f.call(h, y);
        Rate k2 = (Rate) f.call(0.5*h, y.addMul(0.5, k1));
        Rate k3 = (Rate) f.call(0.5*h, y.addMul(0.5, k2));
        Rate k4 = (Rate) f.call(h, y.addMul(1, k3));
        return y.addMul(h/6d, k1.addMull(2, k2).addMull(2, k3).addMull(1, k4));

    }

    @Override
    public StateInterface[][] getData(ODEFunctionInterface f, double tf, double h) {
        init(tf,h);
        addInitialStates();
        computeStates(f,h);
        return timelineArray;
    }
}
