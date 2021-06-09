package org.um.dke.titan.physicsold.ode.functions;

import org.um.dke.titan.domain.MovingObject;
import org.um.dke.titan.interfaces.RateInterface;
import org.um.dke.titan.interfaces.StateInterface;
import org.um.dke.titan.interfaces.Vector3dInterface;
import org.um.dke.titan.physicsold.ode.Rate;
import org.um.dke.titan.physicsold.ode.State;

public class ODEVerletFunction extends ODEFunction {
    public ODEVerletFunction() {
        super();
    }

    @Override
    public RateInterface call(double t, StateInterface y) {
        State state = (State) y;
        Vector3dInterface velocity = state.getVelocity();
        Vector3dInterface acceleration = state.getAcceleration();
        MovingObject object = state.getMovingObject();

        applyForces(object);
        Vector3dInterface rateAcceleration = object.getForce().mul(1/object.getMass()); // a = F/m
        Vector3dInterface rateVelocity = velocity.add( (acceleration.add(rateAcceleration)).mul(t/2) );   // vel + (acc + rateAcc)*dt*1/2
        return new Rate(rateAcceleration, rateVelocity);
    }


}
