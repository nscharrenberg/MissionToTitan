package physics.gravity;

import interfaces.ODEFunctionInterface;
import interfaces.ODESolverInterface;
import interfaces.StateInterface;

public class ODESolver implements ODESolverInterface {
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
            //TODO: step();
            stateArray[i] = step(f,i*h, stateArray[i-1],h);
            State state = (State) stateArray[i];
            state.getObject().setPosition(state.getPosition());
            state.getObject().setVelocity(state.getVelocity());
            System.out.println(i);
        }
        return stateArray;
    }

    @Override
    public StateInterface step(ODEFunctionInterface f, double t, StateInterface y, double h) {
        // y[i+1] = y[i] + h * f.call(t[i], y[i])
        return y.addMul(h,f.call(t,y));
    }
}
