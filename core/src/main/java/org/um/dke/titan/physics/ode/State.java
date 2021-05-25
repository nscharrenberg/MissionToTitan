package org.um.dke.titan.physics.ode;

import com.badlogic.gdx.utils.Null;
import org.um.dke.titan.domain.MovingObject;
import org.um.dke.titan.domain.Vector3D;
import org.um.dke.titan.interfaces.RateInterface;
import org.um.dke.titan.interfaces.StateInterface;
import org.um.dke.titan.interfaces.Vector3dInterface;

public class State implements StateInterface {
    private Vector3dInterface position;
    private Vector3dInterface velocity;
    private Vector3dInterface acceleration;
    private transient MovingObject object;

    public State (Vector3dInterface position, Vector3dInterface velocity, MovingObject object) {
        this.position = position;
        this.velocity = velocity;
        this.object = object;
    }

    public State (Vector3dInterface position, Vector3dInterface velocity, Vector3dInterface acceleration, MovingObject object) {
        this.position = position;
        this.velocity = velocity;
        this.acceleration = acceleration;
        this.object = object;
    }

    @Override
    public StateInterface addMul(double step, RateInterface rate) {
        if(step < 0) {
            throw new IllegalArgumentException();
        }
        if(rate == null) {
            throw new NullPointerException();
        }
        Rate r = (Rate) rate;

        Rate mul = new Rate(r.getAcceleration().mul(step), r.getVelocity().mul(step));
        State state = new State(position.add(mul.getVelocity()), velocity.add(mul.getAcceleration()),object);
        return state;
    }

    public StateInterface addMul(double step, StateInterface state) {
        if(step < 0) {
            throw new IllegalArgumentException("The step size passed is lower than zero");
        }
        State r = (State) state;
        State mul = new State(r.getPosition().mul(step), r.getVelocity().mul(step), object);
        return new State(position.add(mul.getPosition()), velocity.add(mul.getVelocity()),object);
    }

    public StateInterface add(StateInterface addedState) {
        State state = (State)addedState;

        return new State(position.add(state.getVelocity()), velocity.add(state.getVelocity()),object);
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

    public void setAcceleration(Vector3dInterface acceleration) {
        this.acceleration = acceleration;
    }

    public Vector3dInterface getAcceleration() {
        return acceleration;
    }

    public void setObject(MovingObject object) {
        this.object = object;
    }

    public MovingObject getMovingObject() {
        return object;
    }

    public String toString() {
        return "(p: "  + position + ", v: " + velocity + ", o: " + object.getName() + ")";
    }

    public State clone() {
        return new State(((Vector3D) this.position).clone(), ((Vector3D)this.velocity).clone(), this.object.clone());
    }
}
