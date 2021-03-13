package domain;

import interfaces.Vector3dInterface;

public class MovingObject extends SpaceObject {
    private double velocity;
    private Vector3dInterface newVectorState;

    /**
     * @param mass   - the mass of the object in kilograms
     * @param vector - the Vector Object with coordinates
     */
    public MovingObject(int mass, Vector3dInterface vector, Vector3dInterface newVectorState) {
        super(mass, vector);
        this.newVectorState = newVectorState;
    }

    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public Vector3dInterface getNewVectorState() {
        return newVectorState;
    }

    public void setNewVectorState(Vector3dInterface newVectorState) {
        this.newVectorState = newVectorState;
    }
}
