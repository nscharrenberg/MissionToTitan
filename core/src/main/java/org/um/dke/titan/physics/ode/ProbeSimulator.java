package org.um.dke.titan.physics.ode;

import org.um.dke.titan.domain.SpaceObjectEnum;
import org.um.dke.titan.domain.Vector3D;
import org.um.dke.titan.factory.FactoryProvider;
import org.um.dke.titan.interfaces.ProbeSimulatorInterface;
import org.um.dke.titan.interfaces.StateInterface;
import org.um.dke.titan.interfaces.Vector3dInterface;
import org.um.dke.titan.physics.ode.functions.ODEFunction;
import org.um.dke.titan.physics.ode.solvers.ODESolver;
import org.um.dke.titan.repositories.interfaces.ISolarSystemRepository;

import java.util.Vector;

public class ProbeSimulator implements ProbeSimulatorInterface {
    private static final double G = 6.67408e-11; // Gravitational Constant
    private int probeId = SpaceObjectEnum.SHIP.getId();
    private String probeName = SpaceObjectEnum.SHIP.getName();
    private Vector3dInterface force;
    private ISolarSystemRepository system = FactoryProvider.getSolarSystemRepository();
    private double probeMass = system.getRocketName(probeName).getMass();
    private final double probeMassDry = 78000;
    private double fuelUsed = 0;


    private StateInterface[][] timeLineArray;
    private StateInterface[] probeStateArray;
    private final double EXHAUST_VELOCITY = 2e4;
    private final double MAXIMUM_THRUST = 3e7;
    private final double MASS_FLOW_RATE = 2000;
    private final double AREA = 4.55;
    private final double PRESSURE = 100000;
    private final double minI = 8640;
    private int dt;

    /**
     * TODO: Rewrite this method
     */
    @Override
    public Vector3dInterface[] trajectory(Vector3dInterface p0, Vector3dInterface v0, double[] ts) {
        ODESolver solver = new ODESolver();

        ODEFunction f = new ODEFunction();
        StateInterface state = new State(p0, v0, system.getRocketName(probeName));
        StateInterface[] stateArray = solver.solve(f, state, ts);
        Vector3dInterface[] probePositions = new Vector3dInterface[stateArray.length];

        for(int i = 0; i < probePositions.length; i++) {
            probePositions[i] = ((State)stateArray[i]).getPosition();
        }
        return probePositions;
    }

    @Override
    public Vector3dInterface[] trajectory(Vector3dInterface p0, Vector3dInterface v0, double tf, double h) {
        dt = (int) h;
        timeLineArray = FactoryProvider.getSolarSystemRepository().getTimeLineArray(tf, h);
        StateInterface initialState = new State(p0, v0, system.getRocketName(probeName));

        probeStateArray = new StateInterface[timeLineArray[0].length];
        Vector3dInterface[] probePositions = new Vector3D[probeStateArray.length];
        probeStateArray[0] = initialState;
        force = new Vector3D(0,0,0);

        for (int i = 1; i < timeLineArray[0].length; i++) {
            for (int j = 0; j < timeLineArray.length; j++) {
                if (j == probeId) {
                    probeStateArray[i] = step(probeStateArray[i - 1], h);
                    if (i > minI + 1043053 && i < minI + 1043084) {
                        force = force.add(useEngine(3, i - 1, SpaceObjectEnum.EARTH.getId()));
                        System.out.printf("Fuel Left: %s at %s - 1%n", (probeMass - fuelUsed - probeMassDry), i);
                    } else if (i > 1126773 && i < 1126793) {
                        force = force.add(useEngine(6, i - 1, SpaceObjectEnum.SUN.getId()));
                        System.out.printf("Fuel Left: %s at %s - 2%n", (probeMass - fuelUsed - probeMassDry), i);
                    } else if (i > 2241053 && i < 2241058){
                        force = force.add(useEngine(4, i - 1, SpaceObjectEnum.MOON.getId()));
                        System.out.printf("Fuel Left: %s at %s - 3%n", (probeMass - fuelUsed - probeMassDry), i);
                    } else if(i > 2064730 && i < 2064750){
                        force = force.add(useEngine(1, i - 1, SpaceObjectEnum.SATURN.getId()).mul(-1));
                        System.out.printf("Fuel Left: %s at %s  - 4%n", (probeMass - fuelUsed - probeMassDry), i);
                    }
                    else{
                    force = new Vector3D(0, 0, 0);}
                }
            else {
                    force = force.add(newtonsLaw((State)probeStateArray[i-1], (State)timeLineArray[j][i]));
                }
            }
        }

        for (int i = 0; i < probePositions.length; i++) {
            probePositions[i] = ((State)(probeStateArray[i])).getPosition();
        }
        System.out.println("fuel left: " + (probeMass - fuelUsed - probeMassDry));
        return probePositions;
    }

    private boolean[] computeEngineInterval() {
        boolean[] engineInterval = new boolean[probeStateArray.length];

        for (int i = 0; i < probeStateArray.length; i++) {
            if(i%2==0){
                engineInterval[i] = true;}
        }

        return engineInterval;
    }

    private Rate call(double t, StateInterface y) {
        Vector3dInterface rateAcceleration = force.mul(1 / probeMass); // a = F/m
        Vector3dInterface rateVelocity = ((State)y).getVelocity().add(rateAcceleration.mul(t));
        return new Rate(rateAcceleration, rateVelocity);
    }

    private StateInterface step(StateInterface y, double h) {
        Rate k1 = call(h, y);
        Rate k2 = call(0.5*h, y.addMul(0.5, k1));
        Rate k3 = call(0.5*h, y.addMul(0.5, k2));
        Rate k4 = call(h, y.addMul(1, k3));
        return y.addMul(h/6d, k1.addMull(2, k2).addMull(2, k3).addMull(1, k4));
    }

    /**
     * calculates the Gravitational force of 2 states.
     */
    private Vector3dInterface newtonsLaw(State a, State b) {
        Vector3D r = (Vector3D) b.getPosition().sub(a.getPosition()); // xi - xj
        double gravConst = G * a.getMovingObject().getMass() * b.getMovingObject().getMass(); // G * Mi * Mj
        double modr3 = Math.pow(r.norm(),3); // ||xi - xj||^3
        return r.mul(gravConst/modr3); // full formula together
    }

    /**
     *  returns the unit vector of the desired thrust angle
     */
    private Vector3dInterface findThrustVector(int index, int planetID){
        State probe = (State) probeStateArray[index];
        State state = (State) timeLineArray[planetID][index];

        Vector3dInterface thrustVector = state.getPosition().sub(probe.getPosition());
        return thrustVector.mul(1/thrustVector.norm());
    }


    /**
     *  returns the force vector of the engine of the probe
     */
    private Vector3dInterface engineForce(double percentageOfPower, Vector3dInterface thrustVector) {
        return thrustVector.mul(MAXIMUM_THRUST*(percentageOfPower/100));
    }

    private double calculateMassUsed(double percentageOfPower){
        return dt*(percentageOfPower/100)*((MAXIMUM_THRUST+PRESSURE*AREA)/EXHAUST_VELOCITY);
    }

    private Vector3dInterface useEngine(double percentageOfPower, int index, int targetPlanetID){
        if(!calculateNewMass(percentageOfPower))
            return new Vector3D(0,0,0);
        Vector3dInterface thrustVector = findThrustVector(index, targetPlanetID);
        return engineForce(percentageOfPower, thrustVector);
    }

    private Vector3dInterface slowEngine(double percentageOfPower, int index){
        if(!calculateNewMass(percentageOfPower))
            return new Vector3D(0,0,0);

        State probe = ((State) probeStateArray[index]);

        Vector3dInterface thrustVector = probe.getVelocity().mul(-1);

        System.out.println(thrustVector);
        return  engineForce(percentageOfPower, thrustVector.mul(1/thrustVector.norm()));
    }

    private boolean calculateNewMass(double percentageOfPower) {
        if (system.getRocketName(probeName).getMass()-calculateMassUsed(percentageOfPower)>probeMassDry) {
            system.getRocketName(probeName).setMass((float) (system.getRocketName(probeName).getMass() - calculateMassUsed(percentageOfPower)));
            fuelUsed += calculateMassUsed(percentageOfPower);
            return true;
        }
        else{
            System.out.println("No fuel left!!");
            return false;
        }
    }

}
