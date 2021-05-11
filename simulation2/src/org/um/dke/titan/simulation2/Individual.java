package org.um.dke.titan.simulation2;

import org.um.dke.titan.interfaces.Vector3dInterface;

import java.util.HashMap;
import java.util.Random;

public class Individual {
    private static double MAX_THRUST = 3e7;

    private Vector3dInterface startPosition;
    private double velocity;
    private HashMap<String, Double> engineForce;
    private double fitness;

    private double distanceFromTitan;
    private double distanceFromEarth;

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
        fitness = Simulation.run(startPosition, velocity, engineForce);
    }

    public double getFitness() {
        return this.fitness;
    }

    @Override
    public String toString() {
        return String.valueOf(getFitness());
    }
}
