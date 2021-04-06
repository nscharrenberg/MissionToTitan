package physics.gravity.ode;

import domain.MovingObject;
import interfaces.RateInterface;
import interfaces.StateInterface;
import interfaces.Vector3dInterface;

public class ODERungaFunction extends ODEFunction {

    public ODERungaFunction() {
        super();
    }

    @Override
    public RateInterface call(double t, StateInterface y) {
        State state = (State) y;
        Vector3dInterface velocity = state.getVelocity();
        MovingObject object = state.getMovingObject();

        applyForces(object);
        Vector3dInterface rateAcceleration = object.getForce().mul(1/object.getMass()); // a = F/m
        Vector3dInterface rateVelocity = velocity.add(rateAcceleration.mul(t));
        return new Rate(rateAcceleration, rateVelocity);
    }
}
