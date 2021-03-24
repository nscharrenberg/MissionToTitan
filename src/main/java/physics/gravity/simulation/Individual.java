package physics.gravity.simulation;

import domain.Vector3D;
import interfaces.Vector3dInterface;

import java.util.Random;

public class Individual {
    Vector3dInterface vector;
    int fitness;
    int speed;
    private boolean mutated;

    public Individual(Vector3dInterface vector, int speed) {
        this.vector = vector;
        this.speed = speed;
        mutated = false;
    }

    public void mutate() {
        Random r = new Random();
        int rand = r.nextInt(100);

        if (rand > 75)
            vector.setX(vector.getX() * r.nextDouble());
        else if (rand > 50)
            vector.setY(vector.getY() *r.nextDouble());
        else if (rand > 25)
            vector.setZ(vector.getZ() *r.nextDouble());
        else if (rand >= 0) {
            this.speed = 40000 + r.nextInt(20000);
        }
        vector = vector.mul(1/vector.norm());
        mutated = true;
    }

    private void determineFitness() {
        fitness = (int)(Simulation.run(vector, speed)/1e5);
    }

    public int getFitness() {
        if (fitness == 0 || mutated) {
            determineFitness();
            mutated = false;
        }
        return fitness;
    }

    public String toString() {
        return "" + getFitness();
    }
}