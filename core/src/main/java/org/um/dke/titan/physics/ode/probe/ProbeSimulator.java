package org.um.dke.titan.physics.ode.probe;

import org.um.dke.titan.domain.Planet;
import org.um.dke.titan.domain.SpaceObjectEnum;
import org.um.dke.titan.domain.Vector3D;
import org.um.dke.titan.factory.FactoryProvider;
import org.um.dke.titan.interfaces.ProbeSimulatorInterface;
import org.um.dke.titan.interfaces.StateInterface;
import org.um.dke.titan.interfaces.Vector3dInterface;
import org.um.dke.titan.physics.ode.functions.solarsystem.PlanetRate;
import org.um.dke.titan.physics.ode.functions.solarsystem.PlanetState;
import org.um.dke.titan.physics.ode.functions.solarsystem.SystemState;
import org.um.dke.titan.repositories.interfaces.ISolarSystemRepository;

import java.util.Map;

public class ProbeSimulator implements ProbeSimulatorInterface {
    private static final double G = 6.67408e-11; // Gravitational Constant

    private StateInterface[] timeLineArray;
    private PlanetState[] probeStateArray;
    private double h;
    private int size;

    private String probeName = SpaceObjectEnum.SHIP.getName();
    private ISolarSystemRepository system = FactoryProvider.getSolarSystemRepository();

    private double probeMass = 400000;
    private final double probeMassDry = 78000;
    private final double EXHAUST_VELOCITY = 2e4;
    private final double MAXIMUM_THRUST = 3e7;
    private final double AREA = 4.55;
    private final double PRESSURE = 100000;


    // --------------------- Trajectories  ---------------------

    @Override
    public Vector3dInterface[] trajectory(Vector3dInterface p0, Vector3dInterface v0, double[] ts) {
        timeLineArray = FactoryProvider.getSolarSystemRepository().getTimeLineArray(FactoryProvider.getSolver(), ts);
        init(p0, v0);

        for (int i = 1; i < size; i++) {
            h = ts[i] - ts[i-1];
            probeStateArray[i] = getNextProbeState(i, h);
        }

        // converting from probe state to probe position array
        Vector3dInterface[] probePositions = new Vector3D[size];
        for (int i = 0; i < size; i++)
            probePositions[i] = probeStateArray[i].getPosition();

        return probePositions;
    }

    @Override
    public Vector3dInterface[] trajectory(Vector3dInterface p0, Vector3dInterface v0, double tf, double h) {
        this.h = h;
        timeLineArray = FactoryProvider.getSolarSystemRepository().getTimeLineArray(FactoryProvider.getSolver(),tf, h);
        init(p0, v0);

        for (int i = 1; i < size; i++)
            probeStateArray[i] = getNextProbeState(i, h);

        // converting from probe state to probe position array
        Vector3dInterface[] probePositions = new Vector3D[size];
        for (int i = 0; i < size; i++)
            probePositions[i] = probeStateArray[i].getPosition();


        return probePositions;
    }

    public PlanetState[] stateTrajectory() {
        return probeStateArray;
    }





    // --------------------- ODE Handling  ---------------------


    private PlanetRate call(double t, PlanetState y) {
        Vector3dInterface rateAcceleration = y.getForce().mul(1 / probeMass); // a = F/m
        Vector3dInterface rateVelocity = y.getVelocity().add(rateAcceleration.mul(t));
        return new PlanetRate(rateVelocity, rateAcceleration);
    }

    private PlanetState step(PlanetState y, double h) {
        PlanetRate k1 = call(h, y).mul(h);
        PlanetRate k2 = call(0.5*h, y.addMul(0.5, k1)).mul(h);
        PlanetRate k3 = call(0.5*h, y.addMul(0.5, k2)).mul(h);
        PlanetRate k4 = call(h, y.addMul(1, k3)).mul(h);
        return y.addMul(1/6d, k1.addMul(2, k2).addMul(2, k3).addMul(1, k4));
    }

    private void init(Vector3dInterface p0, Vector3dInterface v0) {
        probeMass = 400000;
        size = timeLineArray.length;
        probeStateArray = new PlanetState[size];
        probeStateArray[0] = new PlanetState(p0, v0);
        probeStateArray[0].setForce(new Vector3D(0,0,0));
    }

    private PlanetState getNextProbeState(int i, double h) {
        probeStateArray[i-1].setForce(new Vector3D(0,0,0));


        for (Map.Entry<String, PlanetState> entry : ((SystemState)timeLineArray[i-1]).getPlanets().entrySet()) {
            String planetName = entry.getKey();
            Planet planet = system.getPlanetByName(planetName);
            double planetMass = planet.getMass();

            PlanetState planetState = entry.getValue();
            PlanetState probeState = probeStateArray[i-1];

            probeState.setForce(probeState.getForce().add(newtonsLaw(probeState, planetState, probeMass, planetMass)));
        }

        // optimal i = 74759

        PlanetState probe = probeStateArray[i-1];
        probeStateArray[i-1].setForce(probe.getForce().add(getEngineForce(i)));

        return step(probeStateArray[i-1], h);
    }

    /**
     * calculates the Gravitational force of 2 states.
     */
    private Vector3dInterface newtonsLaw(PlanetState a, PlanetState b, double massA, double massB) {
        Vector3D r = (Vector3D) b.getPosition().sub(a.getPosition()); // xi - xj
        double gravConst = G * massA * massB; // G * Mi * Mj
        double modr3 = Math.pow(r.norm(),3); // ||xi - xj||^3
        return r.mul(gravConst/modr3); // full formula together
    }

    // --------------------- New Engine Handling  ---------------------

    private Vector3dInterface getEngineForce(int i) {
        double firstStart = 70800;
        double firstEnd = firstStart + 6000;

        if (i > firstStart && i < firstEnd) { // 503309 dt50 closest point
            return useEngine(0.1, i, "Earth");
        }


        return new Vector3D(0,0,0);
    }

    private Vector3dInterface useEngine(double percentageOfPower, int index) {
        if(!calculateNewMass(percentageOfPower))
            return new Vector3D(0,0,0);
        Vector3dInterface thrustVector = findThrustVector(index);
        return engineForce(percentageOfPower, thrustVector);
    }

    private Vector3dInterface useEngine(double percentageOfPower, int index, String planetName) {
        if(!calculateNewMass(percentageOfPower))
            return new Vector3D(0,0,0);
        Vector3dInterface thrustVector = findThrustVector(index, planetName);
        return engineForce(percentageOfPower, thrustVector);
    }

    /**
     * returns the position we want to travel to
     * @return
     */
    private Vector3dInterface findThrustVector(int i) {
        return findThrustVector(i, SpaceObjectEnum.EARTH.getName());
    }

    private Vector3dInterface findThrustVector(int index, String planetName){
        SystemState systemState = (SystemState) timeLineArray[index];
        PlanetState aimPoint = systemState.getPlanet(planetName);
        PlanetState probe = probeStateArray[index-1];

        Vector3D vector = (Vector3D) aimPoint.getPosition().sub(probe.getPosition());
        vector.mul(1/vector.norm());
        return vector.getUnit();
    }

    private double calculateMassUsed(double percentageOfPower) {
        return (percentageOfPower/100.0)*((MAXIMUM_THRUST+PRESSURE*AREA)/EXHAUST_VELOCITY);
    }

    private Vector3dInterface engineForce(double percentageOfPower, Vector3dInterface thrustVector) {
        return thrustVector.mul(MAXIMUM_THRUST*(percentageOfPower/100));
    }

    private boolean calculateNewMass(double percentageOfPower) {
        if (probeMass-calculateMassUsed(percentageOfPower)>probeMassDry) {
            probeMass -= calculateMassUsed(percentageOfPower);
            return true;
        } else {
            return false;
        }
    }

}
