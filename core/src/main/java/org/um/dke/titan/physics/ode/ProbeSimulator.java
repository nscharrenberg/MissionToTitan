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

public class ProbeSimulator implements ProbeSimulatorInterface {
    private static final double G = 6.67408e-11; // Gravitational Constant
    private int probeId = SpaceObjectEnum.SHIP.getId();
    private String probeName = SpaceObjectEnum.SHIP.getName();
    private Vector3dInterface force;
    private ISolarSystemRepository system = FactoryProvider.getSolarSystemRepository();
    private double probeMass = system.getRocketName(probeName).getMass();
    private StateInterface[][] timeLineArray;
    private StateInterface[] probeStateArray;
    private final double EXHAUST_VELOCITY = 2e4;
    private final double MAXIMUM_THRUST = 3e7;
    private final double MASS_FLOW_RATE = 2000;
    private final double AREA = 4.55;
    private final double PRESSURE = 100000;

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
        timeLineArray = FactoryProvider.getSolarSystemRepository().getTimeLineArray(tf, h);
        StateInterface initialState = new State(p0, v0, system.getRocketName(probeName));

        probeStateArray = new StateInterface[timeLineArray[0].length];
        Vector3dInterface[] probePositions = new Vector3D[probeStateArray.length];
        probeStateArray[0] = initialState;
        force = new Vector3D(0,0,0);
        System.out.println("MASS USED FOR THRUST: " + calculateMassUsed(100));
        for (int i = 1; i < timeLineArray[0].length; i++) {
            for (int j = 0; j < timeLineArray.length; j++) {
                if (j == probeId) {
                    probeStateArray[i] = step(probeStateArray[i-1], h);
                    force = new Vector3D(0, 0, 0);
                } else {
                    force = force.add(newtonsLaw((State)probeStateArray[i-1], (State)timeLineArray[j][i]));
                }
            }
        }

        for (int i = 0; i < probePositions.length; i++) {
            probePositions[i] = ((State)(probeStateArray[i])).getPosition();
        }
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
    private Vector3dInterface findThrustVectorTitan(int index){
        State probe = ((State) probeStateArray[index]);
        State titan = (State) timeLineArray[SpaceObjectEnum.TITAN.getId()][index];

        Vector3dInterface thrustVector = titan.getPosition().sub(probe.getPosition());
        thrustVector = thrustVector.sub(probe.getVelocity());
        return thrustVector.mul(1/thrustVector.norm());
    }

    private Vector3dInterface findThrustVectorEarth(int index){
        State probe = ((State) probeStateArray[index]);
        State earth = (State) timeLineArray[SpaceObjectEnum.EARTH.getId()][index];

        Vector3dInterface thrustVector = earth.getPosition().sub(probe.getPosition());
        thrustVector = thrustVector.sub(probe.getVelocity());
        return thrustVector.mul(1/thrustVector.norm());
    }

    /**
     *  returns the force vector of the engine of the probe
     */
    private Vector3dInterface engineForce(int percentageOfPower, Vector3dInterface thrustVector) {
        return thrustVector.mul(MAXIMUM_THRUST*(percentageOfPower/100));
    }

    private double calculateMassUsed(int percentageOfPower){

        double massUsed = -1*((percentageOfPower/100)*-1*MAXIMUM_THRUST+PRESSURE*AREA)/EXHAUST_VELOCITY;
        return massUsed;
    }

}
