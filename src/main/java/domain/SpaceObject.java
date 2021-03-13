package main.java.domain;

import main.java.interfaces.Vector3dInterface;

public class SpaceObject {
    private double mass;
    private Vector3dInterface position;

    /**
     * @param mass - the mass of the object in kilograms
     * @param position - the Vector Object with coordinates
     */
    public SpaceObject(double mass, Vector3dInterface position){
        this.mass = mass;
        this.position = position;
    }

    public double getMass() {
        return mass;
    }

    public Vector3dInterface getPosition() {
        return position;
    }

    public void setPosition(Vector3dInterface position) {
        this.position = position;
    }
}

