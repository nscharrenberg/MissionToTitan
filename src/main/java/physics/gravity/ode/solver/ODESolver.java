package physics.gravity.ode.solver;

import domain.MovingObject;
import factory.FactoryProvider;
import interfaces.DataInterface;
import interfaces.ODEFunctionInterface;
import interfaces.ODESolverInterface;
import interfaces.StateInterface;
import physics.gravity.ode.State;
import repositories.SolarSystemRepository;

import java.util.List;

public class ODESolver implements ODESolverInterface, DataInterface {

    protected SolarSystemRepository system; // repository for all planets.
    protected List<MovingObject> planets;
    protected StateInterface[][] timelineArray; // 2d array containing all states of all planets.
    protected int currentPlanetIndex;
    protected int size;

    public ODESolver(){
        this.system = FactoryProvider.getSolarSystemFactory();
    }

    @Override
    public StateInterface[] solve(ODEFunctionInterface f, StateInterface y0, double[] ts) {
        size = ts.length;
        addInitialStates();
        for (int i = 1; i < size; i++) {
            for( int j = 0; j < planets.size(); j++)
            {
                int time = 0;
                for (int k = 0; k < i; k++) {
                    time += ts[k];
                }
                // inserting step into the array
                timelineArray[j][i] = step(f, time, timelineArray[j][i - 1], ts[i]-ts[i-1]);
                State state = (State) timelineArray[j][i];

                // updating the MovingObject's (Planet) state
                system.getPlanets().get(j).setPosition(state.getPosition());
                system.getPlanets().get(j).setVelocity(state.getVelocity());
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
        planets = system.getPlanets();
        timelineArray = new StateInterface[planets.size()][size];
        currentPlanetIndex = getIndexOfPlanet((State)y0);
    }

    protected void init(double tf, double h) {
        system.init();
        planets = system.getPlanets();
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
            StateInterface state = new State(planets.get(i).getPosition(), planets.get(i).getVelocity(), planets.get(i));
            timelineArray[i][0] = state;
        }
    }

    /**
     * computes all states of all planets for t[1]
     * and up and adds them in the allStates array
     */
    protected void computeStates(ODEFunctionInterface f, double h) {
        for (int i = 1; i < size; i++) {
            for( int j = 0; j < planets.size(); j++)
            {
                // inserting step into the array
                timelineArray[j][i] = step(f, h*i, timelineArray[j][i - 1], h);
                State state = (State) timelineArray[j][i];

                // updating the MovingObject's (Planet) state
                system.getPlanets().get(j).setPosition(state.getPosition());
                system.getPlanets().get(j).setVelocity(state.getVelocity());
            }
        }
    }

    @Override
    public StateInterface step(ODEFunctionInterface f, double t, StateInterface y, double h) {
        return y.addMul(h,f.call(h,y)); // y[i+h] = y[i] + h * f.call(t[i], y[i])
    }

    @Override
    public StateInterface[][] getData(ODEFunctionInterface f, double tf, double h) {
        init(tf, h);
        addInitialStates();
        computeStates(f, h);
        return timelineArray;
    }

}
