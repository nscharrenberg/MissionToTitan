package org.um.dke.titan.physics.ode.solvers;

import org.um.dke.titan.domain.Vector3D;

public class PlanetRate {

    private Vector3D velocity;
    private Vector3D acceleration;

    public PlanetRate(Vector3D velocity, Vector3D acceleration) {
        this.velocity = velocity;
        this.acceleration = acceleration;
    }

    public Vector3D getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector3D velocity) {
        this.velocity = velocity;
    }

    public Vector3D getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(Vector3D acceleration) {
        this.acceleration = acceleration;
    }
}
