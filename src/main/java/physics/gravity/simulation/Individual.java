package physics.gravity.simulation;

import interfaces.Vector3dInterface;

import java.util.Random;

public class Individual {

    Vector3dInterface velocity;
    double fitness;

    public Individual(Vector3dInterface velocity) {
        this.velocity = velocity;
    }

    public void mutate() {
        Random r = new Random();
        int rand = r.nextInt(100);

        if (rand > 75)
            velocity.setX(velocity.getX() * r.nextDouble());
        else if (rand > 50)
            velocity.setY(velocity.getY() * r.nextDouble());
        else if (rand > 25)
            velocity.setZ(velocity.getZ() * r.nextDouble());
        determineFitness();
    }

    public void determineFitness() {
        fitness = Simulation.run(velocity);
    }

    public double getFitness() {
        return fitness;
    }

    public String toString() {
        return "" + getFitness();
    }
}
