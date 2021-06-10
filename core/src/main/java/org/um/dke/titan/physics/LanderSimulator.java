package org.um.dke.titan.physics;

import org.um.dke.titan.domain.SpaceObjectEnum;
import org.um.dke.titan.factory.FactoryProvider;
import org.um.dke.titan.interfaces.StateInterface;
import org.um.dke.titan.interfaces.Vector3dInterface;
import org.um.dke.titan.physics.ode.functions.planetfunction.PlanetState;
import org.um.dke.titan.repositories.interfaces.ISolarSystemRepository;

public class LanderSimulator {
    private static final double G = 6.67408e-11; // Gravitational Constant

    private StateInterface[] timeLineArray;
    private PlanetState[] probeStateArray;
    private double h;
    private int size;

    private String probeName = SpaceObjectEnum.SHIP.getName();
    private Vector3dInterface force;
    private ISolarSystemRepository system = FactoryProvider.getSolarSystemRepository();
}
