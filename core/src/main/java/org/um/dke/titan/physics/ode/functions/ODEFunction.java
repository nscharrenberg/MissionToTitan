package org.um.dke.titan.physics.ode.functions;

import org.um.dke.titan.domain.MovingObject;
import org.um.dke.titan.domain.Planet;
import org.um.dke.titan.domain.Vector3D;
import org.um.dke.titan.factory.FactoryProvider;
import org.um.dke.titan.interfaces.ODEFunctionInterface;
import org.um.dke.titan.interfaces.RateInterface;
import org.um.dke.titan.interfaces.StateInterface;
import org.um.dke.titan.interfaces.Vector3dInterface;
import org.um.dke.titan.physics.ode.Rate;
import org.um.dke.titan.physics.ode.State;
import org.um.dke.titan.repositories.interfaces.ISolarSystemRepository;

import java.util.ArrayList;
import java.util.List;

public class ODEFunction implements ODEFunctionInterface {
    private static final double G = 6.67408e-11; // Gravitational Constant
    private ISolarSystemRepository system;

    public ODEFunction(){
        this.system = FactoryProvider.getSolarSystemRepository();
    }

    @Override
    public RateInterface call(double t, StateInterface y) {

        if(t <= 0){
            throw new IllegalArgumentException("The input passed is not valid");
        }


        State state = (State) y;
        Vector3dInterface velocity = state.getVelocity();
        MovingObject object = state.getMovingObject();

        applyForces(object);
        Vector3dInterface rateAcceleration = object.getForce().mul(1/object.getMass()); // a = F/m
        Vector3dInterface rateVelocity = velocity.add(rateAcceleration.mul(t)); //v + rateacc * h
        return new Rate(rateAcceleration, rateVelocity);
    }

    protected void applyForces(MovingObject a){
        List<MovingObject> list = new ArrayList<>();

        for (Planet planet : system.getPlanets().values()) {
            list.add(planet);
            list.addAll(planet.getMoons().values());
        }

        list.addAll(system.getRockets().values());

        resetForces(list);
        addForcesToPlanets(a, list);
    }

    /**
     * resetting forces of all planets for a new calculation
     */
    public void resetForces(List<MovingObject> list) {
        if(list == null)  {
            throw new NullPointerException("The list passed is null");
        }
        for (int i = 0; i < list.size(); i++)
            list.get(i).setForce(new Vector3D(0,0,0));
    }

    /**
     * calculates and adds forces to all planets relative to object a.
     */
    public void addForcesToPlanets(MovingObject a, List<MovingObject> list) {

        if(a == null) {
            throw new NullPointerException("Object a is null");
        }

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
    public Vector3dInterface newtonsLaw(MovingObject a, MovingObject b) {
        //exceptions
        if(a == null || b== null) {
            throw new NullPointerException("Invalid arguments; one of teh objects passed is null");
        }
//        TODO: Refactor this code for phase 3, as at the start earth and the probe will trigger this exception.
//        if(a.getPosition().sub(b.getPosition()).getX() == 0 && a.getPosition().sub(b.getPosition()).getY() == 0 && a.getPosition().sub(b.getPosition()).getZ() == 0) {
//            throw new IllegalArgumentException("Invalid arguments; the distance between the 2 objects is zero");
//        }
        if(a.getMass() == 0 || b.getMass() == 0){
            if(a.getMass() == 0 || b.getMass() != 0){
                throw new IllegalArgumentException("Invalid arguments; object a has zero mass");
            }
            if(a.getMass() != 0 || b.getMass() == 0){
                throw new IllegalArgumentException("Invalid arguments; object b has zero mass");
            }
            if(a.getMass() == 0 && b.getMass() == 0){
                throw new IllegalArgumentException("Invalid arguments; objects a and b have zero mass");
            }

        }

        Vector3D r = (Vector3D) b.getPosition().sub(a.getPosition()); // xi - xj
        double gravConst = G * a.getMass() * b.getMass(); // G * Mi * Mj

        if (r.norm() == 0) {
            r = new Vector3D(1,1,1);
        }

        double modr3 = Math.pow(r.norm(),3); // ||xi - xj||^3

        return r.mul(gravConst/modr3); // full formula together
    }
}