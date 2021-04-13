package physics.gravity.ode;

import domain.Vector3D;
import factory.FactoryProvider;
import interfaces.ProbeSimulatorInterface;
import interfaces.StateInterface;
import interfaces.Vector3dInterface;
import physics.gravity.ode.function.ODEFunction;
import physics.gravity.ode.solver.ODESolver;

public class  ProbeSimulator implements ProbeSimulatorInterface {

    private static final double G = 6.67408e-11; // Gravitational Constant
    private Vector3dInterface force;
    private double probeMass = FactoryProvider.getSolarSystemFactory().getPlanets().get(6).getMass();

    /**
     * TODO: Rewrite this method
     */
    @Override
    public Vector3dInterface[] trajectory(Vector3dInterface p0, Vector3dInterface v0, double[] ts) {
        ODESolver solver = new ODESolver();

        ODEFunction f = new ODEFunction();
        StateInterface state = new State(p0, v0, FactoryProvider.getSolarSystemFactory().findPlanet("Probe"));
        StateInterface[] stateArray = solver.solve(f, state, ts);
        Vector3dInterface[] posArray = new Vector3dInterface[stateArray.length];

        for(int i = 0; i < posArray.length; i++) {
            posArray[i] = ((State)stateArray[i]).getPosition();
        }
        return posArray;
    }

    @Override
    public Vector3dInterface[] trajectory(Vector3dInterface p0, Vector3dInterface v0, double tf, double h) {
        StateInterface[][] timeLineArray = FactoryProvider.getSolarSystemFactory().getTimeLineArray(tf, h);
        StateInterface initialState = new State(p0, v0, FactoryProvider.getSolarSystemFactory().getPlanets().get(6));

        StateInterface[] probeStateArray = new StateInterface[timeLineArray[0].length];
        probeStateArray[0] = initialState;

        force = new Vector3D(0,0,0);

        for (int i = 1; i < timeLineArray[0].length; i++) {
            for (int j = 0; j < timeLineArray.length; j++) {
                if (j == 6) {
                    probeStateArray[i] = step(probeStateArray[i-1], h);
                    force = new Vector3D(0, 0, 0);
                } else {
                    force = force.add(newtonsLaw((State)probeStateArray[i-1], (State)timeLineArray[j][i-1]));
                }
            }
        }

        Vector3dInterface[] positionArray = new Vector3D[probeStateArray.length];
        for (int i = 0; i < positionArray.length; i++) {
            positionArray[i] = ((State)(probeStateArray[i])).getPosition();
        }
        return positionArray;
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
}
