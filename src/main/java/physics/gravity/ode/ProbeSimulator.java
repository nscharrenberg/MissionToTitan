package physics.gravity.ode;

import factory.FactoryProvider;
import interfaces.ProbeSimulatorInterface;
import interfaces.StateInterface;
import interfaces.Vector3dInterface;

public class ProbeSimulator implements ProbeSimulatorInterface {

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
        ODESolver solver = new ODESolver();

        ODEFunction f = new ODEFunction();
        StateInterface state = new State(p0, v0, FactoryProvider.getSolarSystemFactory().findPlanet("Probe"));
        StateInterface[] stateArray = solver.solve(f, state, tf, h);
        Vector3dInterface[] posArray = new Vector3dInterface[stateArray.length];

        for(int i = 0; i < posArray.length; i++) {
            posArray[i] = ((State)stateArray[i]).getPosition();
        }

        return posArray;
    }

}
