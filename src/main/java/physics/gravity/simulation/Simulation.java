package physics.gravity.simulation;

import domain.PlanetEnum;
import factory.FactoryProvider;
import interfaces.StateInterface;
import interfaces.Vector3dInterface;
import physics.gravity.ode.ProbeSimulator;
import physics.gravity.ode.State;

public class Simulation{

    private static StateInterface[][] timeLineArray;
    private static Vector3dInterface[] probePositions;

    private static double daySec = 60*24*60;
    private static double dt = 10;
    private static double tf = daySec*280;

    private static double titanRadius = 2574000;

	public static double run(Vector3dInterface unit, int velocity) {
        simulate(unit, velocity);
        return getMinDistance();
    }

    public static double getMinDistance() {
        double min = Double.MAX_VALUE;

        for (int i = 0; i < timeLineArray[0].length; i++) {
            physics.gravity.ode.State titan = (physics.gravity.ode.State) timeLineArray[PlanetEnum.TITANT.getId()][i];
            Vector3dInterface probe = probePositions[i];

            double dist = probe.dist(titan.getPosition()) - titanRadius;

            if (min > dist && dist > 0)
                min = dist;
        }
        return min;
    }

    public static void simulate(Vector3dInterface unit, int velocity) {
        timeLineArray = FactoryProvider.getSolarSystemFactory().getTimeLineArray(tf, dt);

        Vector3dInterface earthVelocity = ((physics.gravity.ode.State)(timeLineArray[PlanetEnum.EARTH.getId()][0])).getVelocity();

        ProbeSimulator probeSimulator = new ProbeSimulator();
        probePositions = probeSimulator.trajectory(((State)timeLineArray[PlanetEnum.SHIP.getId()][0]).getPosition(),unit.mul(velocity).add(earthVelocity),tf, dt);
    }
}

