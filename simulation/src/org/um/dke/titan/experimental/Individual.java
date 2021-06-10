package org.um.dke.titan.experimental;

import org.um.dke.titan.domain.Vector3D;
import org.um.dke.titan.interfaces.Vector3dInterface;

import java.util.Arrays;
import java.util.Random;

public class Individual {

    int interval;
    int percentage;
    int fitness;

    public Individual(int interval, int percentage) {
        this.interval = interval;
        this.percentage = percentage;
    }

    public void mutate() {
        Random r = new Random();

    }

    public void determineFitness() {
        fitness = Simulation.run(interval, percentage);
    }


    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public int getFitness() {
        return fitness;
    }

}
