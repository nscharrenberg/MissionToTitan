package org.um.dke.titan.error;

import org.um.dke.titan.domain.Vector3D;
import org.um.dke.titan.interfaces.Vector3dInterface;

import java.util.Random;

public class Individual {

    int speed;
    Vector3D vector;
    double fitness;

    public Individual(Vector3dInterface vector, int speed) {
        this.vector = (Vector3D) vector;
        this.speed = speed;
    }

    public void mutate() {
        Random r = new Random();

    }

    public void determineFitness() {
        fitness = Simulation.run(vector, speed);
    }


    public double getFitness() {
        return fitness;
    }

    @Override
    public String toString() {
        return "fit: " + fitness;
    }
}
