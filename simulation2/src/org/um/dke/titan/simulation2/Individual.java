package org.um.dke.titan.simulation2;

import org.um.dke.titan.interfaces.Vector3dInterface;

import java.util.HashMap;
import java.util.Random;

public class Individual {
    private static double MAX_THRUST = 3e7;

    private Vector3dInterface startPosition;
    private int velocity;
    private HashMap<String, Double> engineForce;
    private double fitness;
    private SimulationResults results = new SimulationResults();

    private double distanceFromTitan;
    private double distanceFromEarth;

    public Individual(Vector3dInterface startPosition, int velocity) {
        this.startPosition = startPosition;
        this.velocity = velocity;
    }

    public void mutate() {
        Random r = new Random();
        int rand = r.nextInt(100);

        // Mutation Threshold
        if (rand > 80) {
            if (rand < 85) {
                startPosition.setX(startPosition.getX() * r.nextDouble());
            } else if (rand < 90) {
                startPosition.setY(startPosition.getY() * r.nextDouble());
            } else if (rand < 95) {
                startPosition.setZ(startPosition.getZ() * r.nextDouble());
            } else {
                velocity = velocity * r.nextInt(20000);
            }
        }
    }

    public void determineFitness() {
        results = Simulation.run(startPosition, velocity);
        fitness = results.getMinDistanceToEarth() + results.getMinDistanceToTitan();
    }

    public double getFitness() {
        return this.fitness;
    }

    @Override
    public String toString() {
        return String.valueOf(getFitness());
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public static double getMaxThrust() {
        return MAX_THRUST;
    }

    public static void setMaxThrust(double maxThrust) {
        MAX_THRUST = maxThrust;
    }

    public Vector3dInterface getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(Vector3dInterface startPosition) {
        this.startPosition = startPosition;
    }

    public int getVelocity() {
        return velocity;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    public HashMap<String, Double> getEngineForce() {
        return engineForce;
    }

    public void setEngineForce(HashMap<String, Double> engineForce) {
        this.engineForce = engineForce;
    }

    public SimulationResults getResults() {
        return results;
    }

    public void setResults(SimulationResults results) {
        this.results = results;
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
