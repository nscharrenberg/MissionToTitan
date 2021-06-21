package org.um.dke.titan.simulation2;

import org.um.dke.titan.domain.SpaceObjectEnum;
import org.um.dke.titan.interfaces.StateInterface;
import org.um.dke.titan.interfaces.Vector3dInterface;
import org.um.dke.titan.physics.ode.probe.ProbeSimulator;
import org.um.dke.titan.physicsold.ode.State;

import java.util.HashMap;

public class Simulation {
    private static StateInterface[][] timelineArray;
    private static Vector3dInterface[] probePositions;
    private static HashMap<String, Double> engineUsageTimelineArray;
    private static SimulationResults results = new SimulationResults();

    private static double daySec = 60*24*60;
    private static double dt = 20;
    private static double tf = daySec*360;

    private static double titanRadius = 2575.5e3;
    private static double earthRadius = 6371e3;

    public static SimulationResults run(Vector3dInterface unit, int velocity) {
        simulate(unit, velocity);

        getMinDistanceToTitan();
        getMinDistanceToEarth();

        return results;
    }

    public static SimulationResults getMinDistanceToTitan() {
        double min = Double.MAX_VALUE;
        double time = Double.MAX_VALUE;

        for (int i = 0; i < timelineArray[0].length; i++) {
            State titan = (State) timelineArray[SpaceObjectEnum.TITAN.getId()][i];
            Vector3dInterface probe = probePositions[i];

            double dist = probe.dist(titan.getPosition()) - titanRadius;

            if (min > dist && dist > 0) {
                results.setMinDistanceToTitan(dist);
                results.setMinTimeToTitan(i);
            }
        }

        return results;
    }

    public static SimulationResults getMinDistanceToEarth() {
        double min = Double.MAX_VALUE;
        for (int i = results.getMinTimeToTitan(); i < timelineArray[0].length; i++) {
            State earth = (State) timelineArray[SpaceObjectEnum.EARTH.getId()][i];
            Vector3dInterface probe = probePositions[i];

            double dist = probe.dist(earth.getPosition()) - earthRadius;

            if (min > dist && dist > 0) {
                results.setMinDistanceToEarth(dist);
                results.setMinTimeToEarth(i);
            }
        }

        return results;
    }

    public static void simulate(Vector3dInterface unit, int velocity) {
        //timelineArray = FactoryProvider.getSolarSystemRepository().getTimeLineArray(tf, dt);

        Vector3dInterface earthVelocity = ((State)(timelineArray[SpaceObjectEnum.EARTH.getId()][0])).getVelocity();
        ProbeSimulator probeSimulator = new ProbeSimulator();

        probePositions = probeSimulator.trajectory(((State) timelineArray[SpaceObjectEnum.SHIP.getId()][0]).getPosition(), unit.mul(velocity).add(earthVelocity), tf, dt);
    }
}
