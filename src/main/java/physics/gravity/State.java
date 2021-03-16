package physics.gravity;

import domain.MovingObject;
import domain.Planet;
import interfaces.RateInterface;
import interfaces.StateInterface;
import interfaces.Vector3dInterface;

public class State implements StateInterface {

    private Vector3dInterface position;
    private Vector3dInterface velocity;
    private MovingObject object;

    public State (Vector3dInterface position, Vector3dInterface velocity, MovingObject object) {
        this.position = position;
        this.velocity = velocity;
        this.object = object;
    }

    @Override
    public StateInterface addMul(double step, RateInterface rate) {
        Rate r = (Rate) rate;
        Rate mul = new Rate(r.getAcceleration().mul(step), r.getVelocity().mul(step));
        State state = new State(position.add(mul.getVelocity()), velocity.add(mul.getAcceleration()),object);
        return state;
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

    public void setObject(MovingObject object) {
        this.object = object;
    }

    public MovingObject getMovingObject() {
        return object;
    }

    public String toString() {
        return "this is a state, cool right?";
    }
}
