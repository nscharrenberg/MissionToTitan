package org.um.dke.titan.physics.ode.functions.math;


import org.um.dke.titan.interfaces.ODEFunctionInterface;
import org.um.dke.titan.interfaces.RateInterface;
import org.um.dke.titan.interfaces.StateInterface;

/**
 *  Function which determines the change of the state (rate)
 *  of the analytical function x^2.
 */

public class ODETestFunction implements ODEFunctionInterface {

    @Override
    public RateInterface call(double t, StateInterface y) {
        State state = (State) y;

        double rateVelocity = 2*t;
        double rateAcceleration = 1;

        return new Rate(rateVelocity, rateAcceleration);
    }
}
