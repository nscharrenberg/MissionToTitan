package physics.gravity.ode.function;

import domain.MovingObject;
import domain.Vector3D;
import factory.FactoryProvider;
import interfaces.ODEFunctionInterface;
import interfaces.RateInterface;
import interfaces.StateInterface;
import interfaces.Vector3dInterface;
import physics.gravity.ode.Rate;
import physics.gravity.ode.State;
import repositories.SolarSystemRepository;

import java.util.List;

public class ODEFunction implements ODEFunctionInterface {

    private static final double G = 6.67408e-11; // Gravitational Constant
    private SolarSystemRepository system;

    public ODEFunction(){
        this.system = FactoryProvider.getSolarSystemFactory();
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

    protected void applyForces(MovingObject a){
        List<MovingObject> list = system.getPlanets();
        resetForces(list);
        addForcesToPlanets(a, list);
    }

    /**
     * resetting forces of all planets for a new calculation
     */
    protected void resetForces(List<MovingObject> list) {
        for (int i = 0; i < list.size(); i++)
            list.get(i).setForce(new Vector3D(0,0,0));
    }

    /**
     * calculates and adds forces to all planets relative to object a.
     */
    protected void addForcesToPlanets(MovingObject a, List<MovingObject> list) {
        for (int i=0; i<list.size(); i++)
            if(!list.get(i).getName().equals(a.getName())) {
                Vector3dInterface force = newtonsLaw(a, list.get(i));
                a.setForce(a.getForce().add(force));
                list.get(i).setForce(list.get(i).getForce().add(force.mul(-1)));
            }
    }

    /**
     * calculates the Gravitational force of 2 moving objects.
     */
    protected Vector3dInterface newtonsLaw(MovingObject a, MovingObject b) {
        Vector3D r = (Vector3D) b.getPosition().sub(a.getPosition()); // xi - xj
        double gravConst = G * a.getMass() * b.getMass(); // G * Mi * Mj
        double modr3 = Math.pow(r.norm(),3); // ||xi - xj||^3

        return r.mul(gravConst/modr3); // full formula together
    }


}
