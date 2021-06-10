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

    public SystemRate mul(double scalar) {
        SystemRate nextRate = new SystemRate();

        for (Map.Entry<String, PlanetRate> entry : rates.entrySet()) {
            PlanetRate planetRate = entry.getValue();
            PlanetRate nextPlanetRate = new PlanetRate();
            nextPlanetRate.setAcceleration(planetRate.getAcceleration().mul(scalar));
            nextPlanetRate.setVelocity(planetRate.getVelocity().mul(scalar));
            nextRate.rates.put(entry.getKey(), nextPlanetRate);
        }

        return nextRate;
    }

    public SystemRate addMull(double step, RateInterface rate) {
        SystemRate nextRate = new SystemRate();
        SystemRate r = (SystemRate) rate;

        for (Map.Entry<String, PlanetRate> entry : rates.entrySet()) {
            PlanetRate planetRate = entry.getValue();
            PlanetRate nextPlanetRate = new PlanetRate();
            nextPlanetRate.setAcceleration(planetRate.getAcceleration().add(r.getRate(entry.getKey()).getAcceleration().mul(step)));
            nextPlanetRate.setVelocity(planetRate.getVelocity().add(r.getRate(entry.getKey()).getVelocity().mul(step)));
            nextRate.rates.put(entry.getKey(), nextPlanetRate);
        }

        return nextRate;
    }
}

