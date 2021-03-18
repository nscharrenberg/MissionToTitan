package physics.gravity;

import domain.MovingObject;
import domain.Planet;
import domain.SpaceCraft;
import interfaces.DataInterface;
import interfaces.ODEFunctionInterface;
import interfaces.ODESolverInterface;
import interfaces.StateInterface;
import repositories.SolarSystemRepository;

import java.util.List;

public class ODESolver implements ODESolverInterface, DataInterface {

    private SolarSystemRepository system; // repository for all planets.
    private StateInterface[][] allStates; // 2d array containing all states of all planets.
    private int currentPlanetIndex;
    private int size;
    private List<MovingObject> planets;

    public ODESolver(SolarSystemRepository system){
        this.system = system;

    }

    @Override
    public StateInterface[] solve(ODEFunctionInterface f, StateInterface y0, double[] ts) {
        return new StateInterface[0];
    }

    @Override
    public StateInterface[] solve(ODEFunctionInterface f, StateInterface y0, double tf, double h) {
        init(y0, tf,h);
        addInitialStates();
        computeStates(f,h);
        return allStates[currentPlanetIndex];
    }

    /**
     * determines and initializes variables needed for calculating new states.
     */
    private void init(StateInterface y0, double tf, double h) {
        planets = system.getPlanets();
        size = (int)Math.round(tf/h);
        allStates = new StateInterface[planets.size()][size];
        currentPlanetIndex = getIndexOfPlanet((State)y0);
    }

    private void init(double tf, double h) {
        //system.init();
        planets = system.getPlanets();
        size = (int)Math.round(tf/h)+1;
        allStates = new StateInterface[planets.size()][size];
    }

    /**
     * determine the index of the planet of y0 in the planets list.
     */
    private int getIndexOfPlanet(State y0) {
        for (int i = 0; i <planets.size(); i++)
            if (planets.get(i).getName().equals(y0.getMovingObject().getName()))
                return i;
        return allStates.length-1; // if it's not a planet, it returns the last index, which is the index of the probe
    }

    /**
     * setting t[0] in the allStates array
     * for all planets to their initial state
     */
    private void addInitialStates() {
        for (int i = 0; i < planets.size(); i++) {
            StateInterface state = new State(planets.get(i).getPosition(), planets.get(i).getVelocity(), planets.get(i));
            allStates[i][0] = state;
        }
    }

    /**
     * computes all states of all planets for t[1]
     * and up and adds them in the allStates array
     */
    private void computeStates(ODEFunctionInterface f, double h) {
        for (int i = 1; i < size; i++) {
            for( int j = 0; j < planets.size(); j++)
            {
                // inserting step into the array
                allStates[j][i] = step(f, h*i, allStates[j][i - 1], h);
                State state = (State) allStates[j][i];

                // updating the MovingObject's (Planet) state
                system.getPlanets().get(j).setPosition(state.getPosition());
                system.getPlanets().get(j).setVelocity(state.getVelocity());

            }
        }

    }

    @Override
    public StateInterface step(ODEFunctionInterface f, double t, StateInterface y, double h) {
        return y.addMul(h,f.call(h,y)); // y[i+1] = y[i] + h * f.call(t[i], y[i])
    }


    @Override
    public StateInterface[][] getData(ODEFunctionInterface f, double tf, double h) {
        init(tf,h);
        addInitialStates();
        computeStates(f,h);
        return allStates;
    }
}
