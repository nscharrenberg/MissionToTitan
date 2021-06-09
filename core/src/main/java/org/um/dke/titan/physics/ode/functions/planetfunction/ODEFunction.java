package org.um.dke.titan.physics.ode.functions.planetfunction;

import org.um.dke.titan.domain.MovingObject;
import org.um.dke.titan.domain.Vector3D;
import org.um.dke.titan.factory.FactoryProvider;
import org.um.dke.titan.interfaces.ODEFunctionInterface;
import org.um.dke.titan.interfaces.RateInterface;
import org.um.dke.titan.interfaces.StateInterface;
import org.um.dke.titan.interfaces.Vector3dInterface;

import java.util.HashMap;
import java.util.Map;

public class ODEFunction implements ODEFunctionInterface {
    private static final double G = 6.67408e-11; // Gravitational Constant

    @Override
    public RateInterface call(double t, StateInterface y) {
        if (t <= 0) {
            throw new IllegalArgumentException("The time should be higher than 0");
        }

        SystemState systemState = (SystemState) y;


        resetForces(systemState.getPlanets());

        for (Map.Entry<String, PlanetState> entry : systemState.getPlanets().entrySet()) {
            applyForces(systemState, entry.getValue(), entry.getKey());
        }



        Map<String, PlanetRate> rates = new HashMap<>();

        // creates planet rates out of the current state
        for (Map.Entry<String, PlanetState> entry : systemState.getPlanets().entrySet()) {

            PlanetState state = entry.getValue();
            String name = entry.getKey();
            double mass = FactoryProvider.getSolarSystemRepository().getPlanetByName(name).getMass();

            Vector3dInterface rateAcceleration = state.getForce().mul(1/mass);
            Vector3dInterface rateVelocity = state.getVelocity().add(rateAcceleration.mul(t));
            
            rates.put(name, new PlanetRate(rateVelocity, rateAcceleration));
        }
        return new SystemRate(rates);
    }

    /**
     * Applies to forces to the objects
     * @param universe - the state of the universe
     * @param state - the PlanetState you want to apply a force to
     * @param name - The name of the object
     */
    protected void applyForces(SystemState universe, PlanetState state, String name) {
        if (state == null) {
            throw new IllegalArgumentException("State can not be null");
        }

        if (universe == null) {
            throw new IllegalArgumentException("Planets must be passed, in order to apply forces");
        }

        for (Map.Entry<String, PlanetState> entry : universe.getPlanets().entrySet()) {

            if (entry.getKey().equals(name))
                break;

            Vector3dInterface force = newton(state, entry.getValue(), name, entry.getKey());

            state.setForce(state.getForce().add(force));
            universe.getPlanet(entry.getKey()).setForce(universe.getPlanet(entry.getKey()).getForce().add(force.mul(-1)));
        }
    }

    /**
     * Resets the force of all the objects in the state
     * @param universe - the state of the universe
     */
    protected void resetForces(Map<String, PlanetState> universe) {
        if (universe == null) {
            throw new IllegalArgumentException("Planets must be passed, in order to reset their forces");
        }

        for (Map.Entry<String, PlanetState> state : universe.entrySet()) {
            state.getValue().setForce(new Vector3D(0, 0, 0));
        }
    }

    /**
     * Calculates the gravitational Force of 2 moving objects
     * @param a - Planet State of first object
     * @param b - Planet state of second object
     * @param aName - NAme of first object
     * @param bName - NAme of second object
     * @return The force
     */
    protected Vector3dInterface newton(PlanetState a, PlanetState b, String aName, String bName) {
        if (a == null || b == null) {
            throw new IllegalArgumentException("Newton requires both planet states to be present, but at least one is NULL.");
        }

        if (aName == null || bName == null) {
            throw new IllegalArgumentException("planet names must be present.");
        }

        MovingObject planetA = FactoryProvider.getSolarSystemRepository().getPlanetByName(aName);
        MovingObject planetB = FactoryProvider.getSolarSystemRepository().getPlanetByName(bName);


        if(a.getPosition().sub(b.getPosition()).getX() == 0 && a.getPosition().sub(b.getPosition()).getY() == 0 && a.getPosition().sub(b.getPosition()).getZ() == 0) {
            throw new IllegalArgumentException("Invalid arguments; the distance between the 2 objects is zero");
        }

        if (planetA == null || planetB == null) {
            throw new NullPointerException("One of the objects could not be found");
        }

        if (planetA.getMass() == 0 || planetB.getMass() == 0) {
            if (planetA.getMass() == 0 && planetB.getMass() != 0) {
                throw new IllegalArgumentException("Object a has a zero mass");
            }

            if (planetB.getMass() == 0 && planetA.getMass() != 0) {
                throw new IllegalArgumentException("Object a has a zero mass");
            }

            throw new IllegalArgumentException("Objects a and b have a zero mass");
        }

        Vector3dInterface r = b.getPosition().sub(a.getPosition()); // xi - xj
        double gravConst = G * planetA.getMass() * planetB.getMass(); // G * Mi * Mj

        if (r.norm() == 0) {
            r = new Vector3D(1, 1, 1);
        }

        double modr3 = Math.pow(r.norm(), 3); // || xi - xj ||^3

        return r.mul(gravConst / modr3);
    }
}
