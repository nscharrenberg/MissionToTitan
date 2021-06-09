package org.um.dke.titan.physics.ode.functions.planetfunction;

import org.um.dke.titan.interfaces.RateInterface;
import org.um.dke.titan.utils.EmptyMapProvider;

import java.util.Map;

public class SystemRate implements RateInterface {

    private Map<String, PlanetRate> rates = EmptyMapProvider.getRateMap();

    public SystemRate(Map<String, PlanetRate> rates) {
        this.rates = rates;
    }

    public SystemRate() {}


    public void setRate(String name, PlanetRate rate) {
        rates.put(name, rate);
    }

    public PlanetRate getRate(String name) {
        return rates.get(name);
    }
}
