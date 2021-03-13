package domain;

import interfaces.Vector3dInterface;

public class SpaceObject {
    private double mass;
    /**
     * TODO: vector field did not have any assigned type, I've added it to the Vector3DInterface, however, this should be looked at.
     */
    private Vector3dInterface vector;

    /**
     * @param mass - the mass of the object in kilograms
     * @param vector - the Vector Object with coordinates
     */
    public SpaceObject(double mass, Vector3dInterface vector){
        this.mass = mass;
        this.vector = vector;
    }

    public double getMass() {
        return mass;
    }
}
