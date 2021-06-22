package org.um.dke.titan.physics.ode.functions.math;


import org.um.dke.titan.interfaces.RateInterface;
import org.um.dke.titan.interfaces.StateInterface;

public class State implements StateInterface {

    private double position;
    private double velocity;
    private double acceleration;


    public State(double position, double velocity, double acceleration) {
        this.position = position;
        this.velocity = velocity;
        this.acceleration = acceleration;
    }

    public State(double position, double velocity) {
        this.position = position;
        this.velocity = velocity;
        this.acceleration = 0;
    }

    @Override
    public StateInterface addMul(double step, RateInterface rate) {
        Rate r = (Rate) rate;

        Rate mul = new Rate(r.getVelocity() * step, r.getAcceleration() * step);
        State state = new State(position + mul.getVelocity(), velocity + mul.getAcceleration());
        return state;
    }


    public double getPosition() {
        return position;
    }

    public void setPosition(double position) {
        this.position = position;
    }

    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public double getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(double acceleration) {
        this.acceleration = acceleration;
    }

    @Override
    public String toString() {
        return "State{" +
                "position=" + position +
                ", velocity=" + velocity +
                ", acceleration=" + acceleration +
                '}';
    }
}
