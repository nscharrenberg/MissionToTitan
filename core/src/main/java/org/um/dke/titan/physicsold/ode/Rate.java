package org.um.dke.titan.physicsold.ode;

import org.um.dke.titan.interfaces.RateInterface;
import org.um.dke.titan.interfaces.Vector3dInterface;

public class Rate implements RateInterface {
    private Vector3dInterface acceleration;
    private Vector3dInterface velocity;
    //doesn't make sense to pass the acceleration from here
    public Rate(Vector3dInterface acceleration, Vector3dInterface velocity){
        this.acceleration = acceleration;
        this.velocity = velocity;
    }

    public Rate addMull(double step, RateInterface rate) {
        Rate aRate = (Rate) rate;
        return new Rate(acceleration.add(aRate.getAcceleration().mul(step)), velocity.add(aRate.getVelocity().mul(step)));
    }

    public void setAcceleration(Vector3dInterface acceleration) {
        this.acceleration = acceleration;
    }

    public void setVelocity(Vector3dInterface velocity) {
        this.velocity = velocity;
    }

    public Vector3dInterface getAcceleration(){
        return acceleration;
    }

    public Vector3dInterface getVelocity() {
        return velocity;
    }

    public Rate mul(double scalar) {
        return new Rate(acceleration.mul(scalar), velocity.mul(scalar));
    }

    @Override
    public String toString() {
        return "Rate{" +
                "acceleration=" + acceleration +
                ", velocity=" + velocity +
                '}';
    }
}