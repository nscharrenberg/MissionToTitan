package physics.gravity.ode.function;

import domain.MovingObject;
import interfaces.RateInterface;
import interfaces.StateInterface;
import interfaces.Vector3dInterface;
import physics.gravity.ode.Rate;
import physics.gravity.ode.State;

public class ODERungeFunction extends ODEFunction {

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
