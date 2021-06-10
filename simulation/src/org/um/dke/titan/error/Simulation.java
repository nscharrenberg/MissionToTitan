package org.um.dke.titan.error;

import org.um.dke.titan.domain.SpaceObjectEnum;
import org.um.dke.titan.interfaces.StateInterface;
import org.um.dke.titan.interfaces.Vector3dInterface;
import org.um.dke.titan.physics.ProbeSimulator;
import org.um.dke.titan.physicsold.ode.State;

public class Simulation {
    private static StateInterface[][] timeLineArray;
    private static Vector3dInterface[] probePositions;

    private static double daySec = 60*24*60;
    private static double dt = 20;
    private static double tf = daySec*280;

    private static double titanRadius = 2574000;
    private static final double saturnRadius = 58232000;

    public static double run(Vector3dInterface unit, int velocity) {
        simulate(unit, velocity);
        return getMinDistance();
    }

    public static double getMinDistance() {
        double minTitan = Double.MAX_VALUE;
        double minSaturn = Double.MAX_VALUE;

        for (int i = 0; i < timeLineArray[0].length; i++) {
            State titan = (State) timeLineArray[SpaceObjectEnum.TITAN.getId()][i];
            State saturn = (State) timeLineArray[SpaceObjectEnum.SATURN.getId()][i];
            Vector3dInterface probe = probePositions[i];

            double distTitan = probe.dist(titan.getPosition()) - titanRadius;
            double distSaturn = probe.dist(saturn.getPosition()) - saturnRadius;
            if (minTitan > distTitan && distTitan > 0)
            {
                minTitan = distTitan;
            }
            if(minSaturn > distSaturn && distTitan > 0)
            {
                minSaturn = distSaturn;
            }
        }
        return (0.5*minSaturn) + minTitan;
    }

    public static void simulate(Vector3dInterface unit, int velocity) {
        //timeLineArray = FactoryProvider.getSolarSystemRepository().getTimeLineArray(tf, dt);

        Vector3dInterface earthVelocity = ((State)(timeLineArray[SpaceObjectEnum.EARTH.getId()][0])).getVelocity();

        ProbeSimulator probeSimulator = new ProbeSimulator();
        probePositions = probeSimulator.trajectory(((State)timeLineArray[SpaceObjectEnum.SHIP.getId()][0]).getPosition(),unit.mul(velocity).add(earthVelocity),tf, dt);
    }
}
