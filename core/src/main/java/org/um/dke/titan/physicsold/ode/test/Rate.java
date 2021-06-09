package org.um.dke.titan.physicsold.ode.test;

import org.um.dke.titan.interfaces.RateInterface;

public class Rate implements RateInterface {

    private double velocity;
    private double acceleration;

    public Rate(double velocity, double acceleration) {
        this.velocity = velocity;
        this.acceleration = acceleration;
    }

    public Rate addMull(double step, RateInterface rate) {
        Rate aRate = (Rate) rate;
        return new Rate(velocity + (aRate.getVelocity()*(step)), acceleration + (aRate.getAcceleration() * (step)));
    }

    public Rate mul(double scalar) {
        return new Rate(velocity * scalar, acceleration * scalar);
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
        return "Rate{" +
                "velocity=" + velocity +
                ", acceleration=" + acceleration +
                '}';
    }
}
