package org.um.dke.titan.physics.ode.functions.solarsystemfunction;

import org.um.dke.titan.domain.Vector3D;
import org.um.dke.titan.interfaces.Vector3dInterface;

public class PlanetState {

    private Vector3dInterface position;
    private Vector3dInterface velocity;
    private Vector3dInterface force;
    private double angle;
    private double angularVelocity;



    public PlanetState(Vector3dInterface position, Vector3dInterface velocity) {
        this.position = position;
        this.velocity = velocity;
        force = new Vector3D(0,0,0);
        angle = 0;
    }

    public PlanetState(Vector3dInterface position, Vector3dInterface velocity, double angle) {
        this.position = position;
        this.velocity = velocity;
        force = new Vector3D(0,0,0);
        this.angle = angle;
    }

    public PlanetState() {
        force = new Vector3D(0,0,0);
    }

    public PlanetState addMul(double step, PlanetRate rate) {
        if(step < 0) {
            throw new IllegalArgumentException();
        }
        if(rate == null) {
            throw new NullPointerException();
        }
        PlanetRate mul = new PlanetRate(rate.getVelocity().mul(step), rate.getAcceleration().mul(step));
        PlanetState state = new PlanetState(position.add(mul.getVelocity()), velocity.add(mul.getAcceleration()));
        return state;
    }


    public Vector3dInterface getPosition() {
        return position;
    }

    public void setPosition(Vector3dInterface position) {
        this.position = position;
    }

    public Vector3dInterface getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector3dInterface velocity) {
        this.velocity = velocity;
    }

    public Vector3dInterface getForce() {
        return force;
    }

    public void setForce(Vector3dInterface force) {
        this.force = force;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public double getAngularVelocity() {
        return angularVelocity;
    }

    public void setAngularVelocity(double angularVelocity) {
        this.angularVelocity = angularVelocity;
    }

    @Override
    public String toString() {
        return "\n\"x\": "   + position.getX() + ",\n" +
                "\"y\": "  + position.getY() + ",\n" +
                "\"z\": "  + position.getZ() + ",\n" +
                "\"vx\": " + velocity.getX() + ",\n" +
                "\"vy\": " + velocity.getY() + ",\n" +
                "\"vz\": " + velocity.getZ() + "\n";
    }
}
