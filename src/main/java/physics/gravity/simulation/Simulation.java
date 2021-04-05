package physics.gravity.simulation;

import domain.Vector3D;
import factory.FactoryProvider;
import interfaces.StateInterface;
import interfaces.Vector3dInterface;
import physics.gravity.ode.ProbeSimulator;

public class Simulation extends Thread{

    private static double daySec = 60*24*60;
    private static double dt = 10;
    private static StateInterface[][] timeLineArray;
    private static Vector3dInterface[] probePositions;

	public static double run(Vector3dInterface unit, int velocity) {
        simulate(unit, velocity);
        return getMinDistance();
    }

    public static double getMinDistance() {
        double min = Double.MAX_VALUE;

        for (int i = 0; i < timeLineArray[0].length; i++) {
            physics.gravity.ode.State titan = (physics.gravity.ode.State) timeLineArray[5][i];
            Vector3dInterface probe = probePositions[i];

            double dist = probe.dist(titan.getPosition()) - 2574000;

            if (min > dist && dist > 0)
                min = dist;
        }
        return min;
    }

    public static void simulate(Vector3dInterface unit, int velocity) {
        timeLineArray = FactoryProvider.getSolarSystemFactory().getTimeLineArray(daySec*365, dt);

        Vector3dInterface earthVelocity = FactoryProvider.getSolarSystemFactory().getPlanets().get(1).getVelocity();

        ProbeSimulator probeSimulator = new ProbeSimulator();
        probePositions = probeSimulator.trajectory(new Vector3D(-1.4729367095433246E11, -2.92689818799422E10, 6581247.7920834115),unit.mul(velocity).add(earthVelocity),daySec*365, dt);
    }
}

