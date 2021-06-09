package org.um.dke.titan.physics.ode.test;


import org.um.dke.titan.interfaces.ODEFunctionInterface;
import org.um.dke.titan.interfaces.RateInterface;
import org.um.dke.titan.interfaces.StateInterface;
import org.um.dke.titan.interfaces.Vector3dInterface;

public class ODETestFunction implements ODEFunctionInterface {

    @Override
    public RateInterface call(double t, StateInterface y) {
        State state = (State) y;

        double rateVelocity = 2*t;
        double rateAcceleration = 1;

        return new Rate(rateVelocity, rateAcceleration);
    }
}
