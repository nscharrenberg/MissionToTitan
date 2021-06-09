package org.um.dke.titan.physicsold.ode.solvers;

import org.um.dke.titan.interfaces.ODEFunctionInterface;
import org.um.dke.titan.interfaces.StateInterface;
import org.um.dke.titan.physicsold.ode.Rate;

public class ODESolverR4 extends ODESolver {

    @Override
    public StateInterface step(ODEFunctionInterface f, double t, StateInterface y, double h) {
        if(y == null) {
            throw new NullPointerException("The state passed is null");
        }
        if(f == null) {
            throw new NullPointerException("The function passed is null");
        }
        Rate k1 = call(f, h, y).mul(h);
        Rate k2 = call(f, h/2.0, y.addMul(0.5, k1)).mul(h);
        Rate k3 = call(f, h/2.0, y.addMul(0.5, k2)).mul(h);
        Rate k4 = call(f, 2.0*h, y.addMul(1.0, k3)).mul(h);

        return y.addMul(1/6.0, k1.addMull(2.0,k2).addMull(2.0, k3).addMull(1.0, k4));
    }

    private Rate call(ODEFunctionInterface f, double t, StateInterface y) {
        Rate rate = (Rate) f.call(t,y);
        return rate;
    }

}