package org.um.dke.titan.physics.ode.functions.planetfunction;

import org.um.dke.titan.domain.Vector3D;
import org.um.dke.titan.interfaces.Vector3dInterface;

public class PlanetState {

    private Vector3dInterface position;
    private Vector3dInterface velocity;
    private Vector3dInterface force;

    public PlanetState(Vector3dInterface position, Vector3dInterface velocity) {
        this.position = position;
        this.velocity = velocity;
        force = new Vector3D(0,0,0);
    }

    public PlanetState() {
        force = new Vector3D(0,0,0);
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
}
