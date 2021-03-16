package physics.gravity;

import domain.Planet;
import interfaces.ODEFunctionInterface;
import interfaces.ODESolverInterface;
import interfaces.StateInterface;
import repositories.SolarSystemRepository;

import java.util.List;

public class ODESolver implements ODESolverInterface {

    private SolarSystemRepository system;
    private StateInterface[][] allStates;
    private int currentPlanetIndex;
    List<Planet> planets;

    public ODESolver(SolarSystemRepository system){
        this.system = system;
        planets = system.getPlanets();
    }

    @Override
    public StateInterface[] solve(ODEFunctionInterface f, StateInterface y0, double[] ts) {
        return new StateInterface[0];
    }

    @Override
    public StateInterface[] solve(ODEFunctionInterface f, StateInterface y0, double tf, double h) {
        system.init();
        int size = (int)Math.round(tf/h)+1;
        allStates = new StateInterface[planets.size()][size];

        // determine the index of the planet of y0 in the planets list
        for (int i = 0; i <planets.size(); i++) {
            State state = (State) y0;
            if (planets.get(i).getName().equals(state.getMovingObject().getName()))
                currentPlanetIndex = i;
        }

        // initial calculation of t=0 of all planets
        for (int i = 0; i < planets.size(); i++) {
            StateInterface state = new State(planets.get(i).getPosition(), planets.get(i).getVelocity(), planets.get(i));
            allStates[i][0] = step(f, 0, state , h);
        }

        // continuing all other steps for all planets
        for (int i = 1; i < size; i++) {
            for( int j = 0; j < planets.size(); j++)
            {
                // inserting step into the array
                allStates[j][i] = step(f, h, allStates[j][i - 1], h);
                State state = (State) allStates[j][i];

                // updating the MovingObject's (Planet) state
                state.getMovingObject().setPosition(state.getPosition());
                state.getMovingObject().setVelocity(state.getVelocity());
            }
        }
        return allStates[currentPlanetIndex];
    }

    @Override
    public StateInterface step(ODEFunctionInterface f, double t, StateInterface y, double h) {
        return y.addMul(h,f.call(t,y)); // y[i+1] = y[i] + h * f.call(t[i], y[i])
    }


}
