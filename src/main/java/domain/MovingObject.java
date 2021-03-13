package main.java.domain;

import main.java.interfaces.Vector3dInterface;

public class MovingObject extends SpaceObject {
    private Vector3dInterface force;
    private Vector3dInterface acceleration;
    private Vector3dInterface velocity;

    /**
     * @param mass   - the mass of the object in kilograms
     * @param position - the Vector Object with coordinates
     */
    public MovingObject(double mass, Vector3dInterface position, Vector3dInterface velocity) {
        super(mass, position);
        this.velocity = velocity;
    }

    public void setVelocity(Vector3dInterface velocity) {
        this.velocity = velocity;
    }

    public Vector3dInterface getVelocity() {
        return velocity;
    }

    public void setForce(Vector3dInterface force) {
        this.force = force;
    }

    public Vector3dInterface getForce() {
        return force;
    }

    public void setAcceleration(Vector3dInterface acceleration) {
        this.acceleration = acceleration;
    }

    public Vector3dInterface getAcceleration() {
        return acceleration;
    }
}
