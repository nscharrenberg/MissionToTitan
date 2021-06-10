package org.um.dke.titan.physics.ode.solvers;

import org.um.dke.titan.interfaces.ODEFunctionInterface;
import org.um.dke.titan.interfaces.ODESolverInterface;
import org.um.dke.titan.interfaces.StateInterface;


public class ODESolver implements ODESolverInterface {

    private StateInterface[] timeLineArray;
    private int size;

    @Override
    public StateInterface[] solve(ODEFunctionInterface f, StateInterface y0, double[] ts) {
        if(y0 == null)
            throw new NullPointerException();

        size = ts.length;

        init(y0);
        computeTimeLineArray(f, ts);

        return timeLineArray;
    }

    @Override
    public StateInterface[] solve(ODEFunctionInterface f, StateInterface y0, double tf, double h) {
        if(y0 == null)
            throw new NullPointerException();

        size = (int)Math.round(tf/h)+1;

        init(y0);
        computeTimeLineArray(f,h);

        return timeLineArray;
    }

    @Override
    public StateInterface step(ODEFunctionInterface f, double t, StateInterface y, double h) {
        return y.addMul(h,f.call(1,y)); // y[i+h] = y[i] + h * f.call(t[i], y[i])
    }

    private void init(StateInterface y0) {
        timeLineArray = new StateInterface[size];
        timeLineArray[0] = y0;
    }

    private void computeTimeLineArray(ODEFunctionInterface f, double h) {
        for (int i = 1; i < timeLineArray.length; i++) {
            timeLineArray[i] = step(f, h*i, timeLineArray[i-1], h);
        }
    }

    private void computeTimeLineArray(ODEFunctionInterface f, double[] ts) {
        for (int i = 1; i < timeLineArray.length; i++) {
            double h = ts[i] - ts[i-1];
            double t = ts[i];

            timeLineArray[i] = step(f, t, timeLineArray[i-1], h);
        }
    }

}
