package physics.gravity;

import interfaces.RateInterface;
import interfaces.StateInterface;
import interfaces.Vector3dInterface;

public class State implements StateInterface {

    private Vector3dInterface position;
    private Vector3dInterface velocity;

    public State (Vector3dInterface position, Vector3dInterface velocity) {
        this.position = position;
        this.velocity = velocity;
    }

    @Override
    public StateInterface addMul(double step, RateInterface rate) {

        return null;
    }

    public void setPosition(Vector3dInterface position) {

        this.position = position;
    }

    public Vector3dInterface getPosition() {
        return position;
    }

    public void setVelocity(Vector3dInterface velocity) {
        this.velocity = velocity;
    }

    public Vector3dInterface getVelocity() {
        return velocity;
    }
}
