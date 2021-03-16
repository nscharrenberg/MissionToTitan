package domain;

import interfaces.Vector3dInterface;


public abstract class SpaceObject {
    private double mass;
    private Vector3dInterface position;

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

