package org.um.dke.titan.simulation2;

import org.um.dke.titan.interfaces.Vector3dInterface;

import java.util.HashMap;
import java.util.Random;

public class Individual {
    private Vector3dInterface startPosition;
    private double velocity;
    private HashMap<String, Double> engineForce;
    private double fitness;

    private double distanceFromTitan;
    private double distanceFromEarth;

    public void mutate() {
        // TODO: Mutation Logic here
    }

    public void determineFitness() {
        // TODO: Fitness Determination logic here
    }

    public Vector3dInterface getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(Vector3dInterface startPosition) {
        this.startPosition = startPosition;
    }

    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public HashMap<String, Double> getEngineForce() {
        return engineForce;
    }

    public void setEngineForce(HashMap<String, Double> engineForce) {
        this.engineForce = engineForce;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public double getDistanceFromTitan() {
        return distanceFromTitan;
    }

    public void setDistanceFromTitan(double distanceFromTitan) {
        this.distanceFromTitan = distanceFromTitan;
    }

    public double getDistanceFromEarth() {
        return distanceFromEarth;
    }

    public void setDistanceFromEarth(double distanceFromEarth) {
        this.distanceFromEarth = distanceFromEarth;
    }
}
