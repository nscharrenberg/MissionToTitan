package domain;

import interfaces.Vector3dInterface;

// TODO: To further implement
public class SpaceCraft extends MovingObject {
    /**
     * @param mass           - the mass of the object in kilograms
     * @param vector         - the Vector Object with coordinates
     * @param newVectorState
     */
    public SpaceCraft(double mass, Vector3dInterface vector, Vector3dInterface newVectorState) {
        super(mass, vector, newVectorState);
    }
}
