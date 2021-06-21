package org.um.dke.titan.error;

import org.um.dke.titan.domain.Vector3D;
import org.um.dke.titan.factory.FactoryProvider;
import org.um.dke.titan.interfaces.StateInterface;
import org.um.dke.titan.interfaces.Vector3dInterface;
import org.um.dke.titan.physics.ode.probe.ProbeSimulator;
import org.um.dke.titan.physics.ode.functions.solarsystemfunction.PlanetState;
import org.um.dke.titan.physics.ode.functions.solarsystemfunction.SystemState;

public class Simulation {
    private static StateInterface[] timeLineArray;
    private static Vector3dInterface[] probePositions;

    private static double daySec = 60*24*60;
    private static double dt = 200;
    private static double tf = daySec*280;

    public static double run(Vector3dInterface unit, int velocity) {
        simulate(unit, velocity);
        return getMinDistance();
    }

    public static double getMinDistance() {

        double min = Double.MAX_VALUE;

        for (int i = 0; i < probePositions.length; i++) {
            SystemState systemState = (SystemState) timeLineArray[i];
            PlanetState titan = systemState.getPlanet("Titan");
            Vector3dInterface probe = probePositions[i];

            double dist = probe.dist(titan.getPosition());

            if (min > dist) {
                min = dist;
            }
        }
        return min;
    }

    public static void simulate(Vector3dInterface unit, int velocity) {
        timeLineArray = FactoryProvider.getSolarSystemRepository().getTimeLineArray(FactoryProvider.getSolver(),tf, dt);

        Vector3dInterface earthVelocity = ((SystemState)timeLineArray[0]).getPlanet("Earth").getVelocity();

        ProbeSimulator probeSimulator = new ProbeSimulator();
        probePositions = probeSimulator.trajectory(new Vector3D(-1.471922101663588e+11, -2.860995816266412e+10, 9.278183193596080e+06),unit.mul(velocity).add(earthVelocity),tf, dt);
    }
}
