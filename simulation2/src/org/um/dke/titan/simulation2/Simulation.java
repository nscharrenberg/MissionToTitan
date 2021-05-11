package org.um.dke.titan.simulation2;

import org.um.dke.titan.interfaces.StateInterface;
import org.um.dke.titan.interfaces.Vector3dInterface;

public class Simulation {
    private static StateInterface[][] timelineArray;
    private static Vector3dInterface[] probePositions;

    private static double daySec = 60*24*60;
    private static double dt = 20;
    private static double tf = daySec*280;

    private static double titanRadius = 2575.5e3;
    private static double earthRadius = 6371e3;

    public static double run(Vector3dInterface unit, double velocity, double)
}
