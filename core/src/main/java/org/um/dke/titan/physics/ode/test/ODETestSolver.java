package org.um.dke.titan.physics.ode.test;

import org.um.dke.titan.interfaces.ODEFunctionInterface;
import org.um.dke.titan.interfaces.ODESolverInterface;
import org.um.dke.titan.interfaces.StateInterface;

public class ODETestSolver implements ODESolverInterface {
    @Override
    public StateInterface[] solve(ODEFunctionInterface f, StateInterface y0, double[] ts) {
        return new StateInterface[0];
    }

    @Override
    public StateInterface[] solve(ODEFunctionInterface f, StateInterface y0, double tf, double h) {

        int size = (int)Math.round(tf/h)+1;
        StateInterface[] array = new StateInterface[size];

        array[0] = y0;

        for (int i = 1; i < size; i++) {
            array[i] = step(f, i*h-h, array[i-1], h);
        }

        return array;
    }

    @Override
    public StateInterface step(ODEFunctionInterface f, double t, StateInterface y, double h) {
        return y.addMul(h,f.call(t,y));
    }
}
