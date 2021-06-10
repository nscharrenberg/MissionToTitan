package org.um.dke.titan.physics.ode.functions.planetfunction;

import org.um.dke.titan.interfaces.RateInterface;
import org.um.dke.titan.interfaces.StateInterface;
import org.um.dke.titan.utils.EmptyMapProvider;

import java.util.Map;

public class SystemState implements StateInterface {
    private Map<String, PlanetState> planets = EmptyMapProvider.getStateMap();

    public SystemState() {}

    public SystemState(Map<String, PlanetState> planets) {
        this.planets = planets;
    }

    @Override
    public StateInterface addMul(double step, RateInterface rate) {
        if (step < 0) {
            throw new IllegalArgumentException("Step must be higher or equal to 0");
        }

        if (rate == null) {
            throw new IllegalArgumentException("A rate must be provided");
        }


        SystemRate r = (SystemRate)rate;
        SystemRate nextRate = new SystemRate();
        SystemState nextState = new SystemState();

        for (Map.Entry<String, PlanetState> entry : this.planets.entrySet()) {
            PlanetRate foundRate = r.getRate(entry.getKey());
            PlanetRate nextTempRate = updateLocalRate(foundRate, step);

            nextRate.setRate(entry.getKey(), nextTempRate);
            nextState.setPlanet(entry.getKey(), updateLocalState(entry.getValue(), nextTempRate));
        }

        return nextState;
    }

    /**
     * Updates the Local Rate for a single planet
     * @param current - the planet to update the Rate for
     * @param step - the step to take
     * @return - the next rate for the planet
     */
    private PlanetRate updateLocalRate(PlanetRate current, double step) {
        PlanetRate nextTempRate = new PlanetRate();
        nextTempRate.setVelocity(current.getVelocity().mul(step));
        nextTempRate.setAcceleration(current.getAcceleration().mul(step));

        return nextTempRate;
    }

    /**
     * Updates the local State for a single planet
     * @param current - the planet to update the state for
     * @param rate - the rate for the planet
     * @return - the next state for the planet
     */
    private PlanetState updateLocalState(PlanetState current, PlanetRate rate) {
        PlanetState nextTempState = new PlanetState();
        nextTempState.setPosition(current.getPosition().add(rate.getVelocity()));
        nextTempState.setVelocity(current.getVelocity().add(rate.getAcceleration()));
        return nextTempState;
    }

    public void setPlanets(Map<String, PlanetState> planets) {
        this.planets = planets;
    }

    public void setPlanet(String name, PlanetState planet) {
        this.planets.put(name, planet);
    }

//    private void updatePlanet(String name, PlanetState newState) {
//        System.out.println(name);
//        PlanetState planetState = planets.get(name);
//        planetState.setPosition(newState.getPosition());
//        planetState.setVelocity(newState.getVelocity());
//        planetState.setForce(newState.getForce());
//    }

    public PlanetState getPlanet(String name) {
        return this.planets.get(name);
    }

    public Map<String, PlanetState> getPlanets() {
        return this.planets;
    }
}
