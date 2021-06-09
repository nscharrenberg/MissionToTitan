package org.um.dke.titan.physics.ode.functions.planetfunction;

import org.um.dke.titan.domain.Vector3D;
import org.um.dke.titan.interfaces.Vector3dInterface;

public class PlanetRate {

    private Vector3dInterface velocity;
    private Vector3dInterface acceleration;

    public PlanetRate(Vector3dInterface velocity, Vector3dInterface acceleration) {
        this.velocity = velocity;
        this.acceleration = acceleration;
    }

    public PlanetRate() { }


    public PlanetRate addMul(double step, PlanetRate rate) {
        return new PlanetRate(acceleration.add(rate.getAcceleration().mul(step)), velocity.add(rate.getVelocity().mul(step)));
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
