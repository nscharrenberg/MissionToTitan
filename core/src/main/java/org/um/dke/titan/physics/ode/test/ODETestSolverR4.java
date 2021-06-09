package org.um.dke.titan.physics.ode.test;

import org.um.dke.titan.interfaces.ODEFunctionInterface;
import org.um.dke.titan.interfaces.ODESolverInterface;
import org.um.dke.titan.interfaces.StateInterface;

public class ODETestSolverR4 extends ODETestSolver{

    @Override
    public StateInterface step(ODEFunctionInterface f, double t, StateInterface y, double h) {
        Rate k1 = call(f, t, y).mul(h);
        Rate k2 = call(f, t + 0.5*h, y.addMul(0.5, k1)).mul(h);
        Rate k3 = call(f, t + 0.5*h, y.addMul(0.5, k2)).mul(h);
        Rate k4 = call(f, t+h, y.addMul(1, k3)).mul(h);

        return y.addMul(1/6.0, k1.addMull(2, k2).addMull(2, k3).addMull(1, k4));
    }

    private Rate call(ODEFunctionInterface f, double t, StateInterface y) {
        Rate rate = (Rate) f.call(t,y);
        return rate;
    }
}
