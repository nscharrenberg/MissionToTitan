package physics.gravity;

import interfaces.ODEFunctionInterface;
import interfaces.ODESolverInterface;
import interfaces.StateInterface;
import repositories.SolarSystemRepository;

public class ODESolver implements ODESolverInterface {

    private SolarSystemRepository system;

    public ODESolver(SolarSystemRepository system){
        system = new SolarSystemRepository();
    }
    @Override
    public StateInterface[] solve(ODEFunctionInterface f, StateInterface y0, double[] ts) {
        return new StateInterface[0];
    }

    @Override
    public StateInterface[] solve(ODEFunctionInterface f, StateInterface y0, double tf, double h) {
        int size = (int)Math.round(tf/h)+1;
        StateInterface[] stateArray = new StateInterface[size];

        stateArray[0] = step(f,0, y0,h); // initial calculation of t=0

        for (int i = 1; i < size; i++) {
            // inserting step into the array
            stateArray[i] = step(f,i*h, stateArray[i-1],h);
            State state = (State) stateArray[i];

            // updating the MovingObject's state
            state.getMovingObject().setPosition(state.getPosition());
            state.getMovingObject().setVelocity(state.getVelocity());
        }
        return stateArray;
    }

    @Override
    public StateInterface step(ODEFunctionInterface f, double t, StateInterface y, double h) {
        return y.addMul(h,f.call(t,y)); // y[i+1] = y[i] + h * f.call(t[i], y[i])
    }

    private SolarSystemRepository getSolarSysem(){
        return system;
    }
}
