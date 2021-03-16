package physics.gravity;

import domain.MovingObject;
import domain.Planet;
import domain.Vector3D;
import interfaces.ODEFunctionInterface;
import interfaces.RateInterface;
import interfaces.StateInterface;
import interfaces.Vector3dInterface;
import repositories.SolarSystemRepository;

import java.util.ArrayList;

public class ODEFunction implements ODEFunctionInterface {

    private static final double G = 6.67408e-11; // Gravitational Constant
    private SolarSystemRepository system;

    public ODEFunction(SolarSystemRepository system){
        this.system = system;
    }
    @Override
    public RateInterface call(double t, StateInterface y) {

        State state = (State) y;
        Vector3dInterface velocity = state.getVelocity();
        MovingObject object = state.getMovingObject();

        newtonsLaw(object);
        Vector3dInterface rateAcceleration = object.getForce().mul(1/object.getMass());
        Vector3dInterface rateVelocity = velocity.add(rateAcceleration.mul(t));

        return new Rate(rateAcceleration, rateVelocity);
    }

    private Vector3dInterface newtonsLaw(MovingObject a, MovingObject b) {
        Vector3D r = (Vector3D) b.getPosition().sub(a.getPosition()); // xi - xj
        double gravConst = G * a.getMass() * b.getMass(); // G * Mi * Mj
        double modr3 = Math.pow(r.norm(),3); // ||xi - xj||^3
        Vector3dInterface force = r.mul(gravConst/modr3); // full formula together

        return force;
    }

    private void newtonsLaw(MovingObject a){
        ArrayList<Planet> list = (ArrayList<Planet>) system.getPlanets();
        a.setForce(new Vector3D(0,0,0));


        for(int i=0; i<list.size(); i++)
            if(!list.get(i).getName().equals(a.getName())) {
                a.setForce(a.getForce().add(newtonsLaw(a, list.get(i))));
            }


    }

}
