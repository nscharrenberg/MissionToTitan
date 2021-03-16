package physics.gravity;

import domain.MovingObject;
import domain.Planet;
import domain.Vector3D;
import interfaces.ODEFunctionInterface;
import interfaces.RateInterface;
import interfaces.StateInterface;
import interfaces.Vector3dInterface;

public class ODEFunction implements ODEFunctionInterface {

    private static final double G = 6.67408e-11; // Gravitational Constant
    private static Planet sun = new Planet(1.988500e30, new Vector3D(-6.806783239281648e+08, 1.080005533878725e+09, 6.564012751690170e+06), new Vector3D(-1.420511669610689e+01, -4.954714716629277e+00, 3.994237625449041e-01), "Sun", 0);

    @Override
    public RateInterface call(double t, StateInterface y) {
        State state = (State) y;
        Vector3dInterface velocity = state.getVelocity();
        MovingObject object = state.getMovingObject();

        newtonsLaw(object, sun);
        Vector3dInterface rateAcceleration = object.getForce().mul(1/object.getMass());
        Vector3dInterface rateVelocity = velocity.add(rateAcceleration);

        return new Rate(rateAcceleration, rateVelocity);
    }

    private static void newtonsLaw(MovingObject a, MovingObject b) {

        Vector3D r = (Vector3D) b.getPosition().sub(a.getPosition()); // xi - xj
        double gravConst = G * a.getMass() * b.getMass(); // G * Mi * Mj
        double modr3 = Math.pow(r.norm(),3); // ||xi - xj||^3
        Vector3dInterface force = r.mul(gravConst/modr3); // full formula together

        //a.setForce(a.getForce().add(force));
       // b.setForce(b.getForce().add(force.mul(-1)));
        a.setForce(force);
        b.setForce(force.mul(-1));
    }

}
