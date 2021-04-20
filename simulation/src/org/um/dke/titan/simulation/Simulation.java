package org.um.dke.titan.simulation;

import org.um.dke.titan.domain.SpaceObjectEnum;
import org.um.dke.titan.factory.FactoryProvider;
import org.um.dke.titan.interfaces.StateInterface;
import org.um.dke.titan.interfaces.Vector3dInterface;
import org.um.dke.titan.physics.ode.ProbeSimulator;
import org.um.dke.titan.physics.ode.State;

public class Simulation {
    private static StateInterface[][] timeLineArray;
    private static Vector3dInterface[] probePositions;

    private static double daySec = 60*24*60;
    private static double dt = 1000;
    private static double tf = daySec*280;

    private static double titanRadius = 2574000;

    public static double run(Vector3dInterface unit, int velocity) {
        simulate(unit, velocity);
        return getMinDistance();
    }

    public static double getMinDistance() {
        double min = Double.MAX_VALUE;

        for (int i = 0; i < timeLineArray[0].length; i++) {
            State titan = (State) timeLineArray[SpaceObjectEnum.TITAN.getId()][i];
            Vector3dInterface probe = probePositions[i];

            double dist = probe.dist(titan.getPosition()) - titanRadius;

            if (min > dist && dist > 0)
                min = dist;
        }
        return min;
    }

    public static void simulate(Vector3dInterface unit, int velocity) {
        timeLineArray = FactoryProvider.getSolarSystemRepository().getTimeLineArray(tf, dt);

        Vector3dInterface earthVelocity = ((State)(timeLineArray[SpaceObjectEnum.EARTH.getId()][0])).getVelocity();

        ProbeSimulator probeSimulator = new ProbeSimulator();
        probePositions = probeSimulator.trajectory(((State)timeLineArray[SpaceObjectEnum.SHIP.getId()][0]).getPosition(),unit.mul(velocity).add(earthVelocity),tf, dt);
    }
}
