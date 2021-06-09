package org.um.dke.titan.physics.ode;

import org.um.dke.titan.domain.Planet;
import org.um.dke.titan.interfaces.RateInterface;
import org.um.dke.titan.interfaces.StateInterface;
import org.um.dke.titan.utils.EmptyPlanetProvider;

import java.util.Map;

public class SystemState implements StateInterface {

    private Map<String, Planet> planets;

    public SystemState() {
        this.planets = EmptyPlanetProvider.get();
    }

    @Override
    public StateInterface addMul(double step, RateInterface rate) {
        return null;
    }

}
