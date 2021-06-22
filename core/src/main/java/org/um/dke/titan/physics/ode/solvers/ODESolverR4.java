package org.um.dke.titan.physics.ode.solvers;

import org.um.dke.titan.interfaces.ODEFunctionInterface;
import org.um.dke.titan.interfaces.StateInterface;
import org.um.dke.titan.physics.ode.functions.solarsystem.SystemRate;

/**
 * Runge-kutta solver
 */

public class ODESolverR4 extends ODESolver{

    @Override
    public StateInterface step(ODEFunctionInterface f, double t, StateInterface y, double h) {
        if(y == null)
            throw new NullPointerException("The state passed is null");
        if(f == null)
            throw new NullPointerException("The function passed is null");


        SystemRate k1 = call(f, 1, y);
        SystemRate k2 = call(f, 0.5*h, y.addMul(0.5, k1));
        SystemRate k3 = call(f, 0.5*h, y.addMul(0.5, k2));
        SystemRate k4 = call(f, 1*h, y.addMul(1, k3));

        return y.addMul(h/6.0, k1.addMull(2.0, k2).addMull(2.0, k3).addMull(1.0, k4));
    }

    private SystemRate call(ODEFunctionInterface f, double t, StateInterface y) {
        return (SystemRate) f.call(t, y);
    }
}
