package org.um.dke.titan.physics.ode.functions.solarsystem;

import org.um.dke.titan.interfaces.Vector3dInterface;

/**
 * Encapsulation of the rate of change (velocity, acceleration) of a singular planet
 */

public class PlanetRate {

    private Vector3dInterface velocity;
    private Vector3dInterface acceleration;

    public PlanetRate(Vector3dInterface velocity, Vector3dInterface acceleration) {
        this.velocity = velocity;
        this.acceleration = acceleration;
    }

    public PlanetRate() { }


    public PlanetRate addMul(double step, PlanetRate rate) {
        return new PlanetRate(velocity.add(rate.getVelocity().mul(step)), acceleration.add(rate.getAcceleration().mul(step)));
    }

    public PlanetRate mul(double scalar) {
        return new PlanetRate(this.velocity.mul(scalar), this.acceleration.mul(scalar));
    }

    public Vector3dInterface getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector3dInterface velocity) {
        this.velocity = velocity;
    }

    public Vector3dInterface getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(Vector3dInterface acceleration) {
        this.acceleration = acceleration;
    }
}
