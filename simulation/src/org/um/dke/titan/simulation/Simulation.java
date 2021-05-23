package org.um.dke.titan.simulation;

import org.um.dke.titan.domain.SpaceObjectEnum;
import org.um.dke.titan.domain.Vector3D;
import org.um.dke.titan.factory.FactoryProvider;
import org.um.dke.titan.interfaces.StateInterface;
import org.um.dke.titan.interfaces.Vector3dInterface;
import org.um.dke.titan.physics.ode.ProbeSimulator;
import org.um.dke.titan.physics.ode.State;

public class Simulation {
    private static StateInterface[][] timeLineArray;
    private static Vector3dInterface[] probePositions;

    private static double daySec = 60*24*60;
    private static double dt = 20;
    private static double tf = daySec*20;

    private static final double titanRadius = 2574000;
    private static final double MAX_DISTANCE = titanRadius + 300000;


    public static int run(int interval, int percentages) {
        simulate(interval, percentages);
        return getTotalTime();
    }

    public static int getTotalTime() {
        int totalTime = 0;

        for (int i = 0; i < timeLineArray[0].length; i++) {
            State titan = (State) timeLineArray[SpaceObjectEnum.TITAN.getId()][i];
            Vector3dInterface probe = probePositions[i];

            double dist = probe.dist(titan.getPosition()) - titanRadius;

            if (dist < MAX_DISTANCE) {
                totalTime++;
            }
        }
        return totalTime;
    }

    public static void simulate(int interval, int percentages) {
        timeLineArray = FactoryProvider.getSolarSystemRepository().getTimeLineArray(tf, dt);

        ProbeSimulator probeSimulator = new ProbeSimulator();
        probePositions = probeSimulator.trajectory(new Vector3D(7.909915359530085E11, -1.2509398179267585E12, -1.0093915704679705E10),new Vector3D(44544.311055095226, -55688.148030175595, -459.9299008790493),tf ,dt, interval, percentages);
    }
}
