package org.um.dke.titan.simulation2;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.sun.org.apache.bcel.internal.generic.POP;
import org.um.dke.titan.domain.Vector3D;
import org.um.dke.titan.interfaces.Vector3dInterface;

import java.util.Random;

public class Main {
    static Individual[] population;
    static final int MUTATION_RATE = 10;
    static final int POPULATION_SIZE = 50;
    static final int GENERATIONS = 50;
    static Individual min;
    static Random r;

    public static void main(String[] args) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Mission To  Titan";
        config.resizable = false;
        config.vSyncEnabled = true;
        config.width = 1260;
        config.height = 720;
        config.foregroundFPS = 120;
        new LwjglApplication(new Game(false), config);
    }

    public static void simulate() {
        r = new Random();
        min = new Individual(new Vector3D(1, -1, 0.1), 1);
        min.setFitness(Double.MAX_VALUE);

        for (int j = 0; j < 100; j++) {
            init();

            for (int i = 0; i < GENERATIONS; i++) {
                long startTime = System.currentTimeMillis();

                generation();

                if (min.getFitness() > population[0].getFitness()) {
                    min = copyOf(population[0]);
                }

                System.out.printf("Generation %s |%n", i+1);
                print();
                System.out.printf("| Min: %s", min);
                System.out.printf("| Min Start: [ %s, %s, %s ]\n", min.getStartPosition().mul(min.getVelocity()).getX(), min.getStartPosition().mul(min.getVelocity()).getY(), min.getStartPosition().mul(min.getVelocity()).getZ());
                System.out.printf("| Speed: [ %s m/s ] \n", min.getVelocity());
                System.out.printf("| Min Distance Titan: %s at time %s", min.getResults().getMinDistanceToTitan(), min.getResults().getMinTimeToTitan());
                System.out.printf("| Min Distance Earth: %s at time %s", min.getResults().getMinDistanceToEarth(), min.getResults().getMinTimeToEarth());
                long endTime = System.currentTimeMillis();
                System.out.printf("| Computation time: [ %s s ]", ((endTime - startTime) / 1000));
            }
        }
    }

    private static void init() {
        initPopulation();
        System.out.println("Starting Generation | ");

        if (min != null) {
            population[10] = copyOf(min);
        }

        print();
        System.out.println();
    }

    private static void initPopulation() {
        population = new Individual[POPULATION_SIZE];

        for (int i = 0; i < POPULATION_SIZE; i++) {
            population[i] = new Individual(randomUnitVector(), 40000 + r.nextInt(20000));
        }

        updateFitness();
    }

    private static void generation() {
        removeWeakest();
        int start = population.length / 2;

        for (int i = start; i < population.length; i++) {
            int male = r.nextInt(start);
            int female = r.nextInt(start);

            if (male != female) {
                population[i] = breed(population[r.nextInt(start)], population[r.nextInt(start)]);
            } else {
                i--;
            }
        }

        population[population.length-1] = new Individual(randomUnitVector(), 40000 + r.nextInt(20000));

        for (int i = 0; i < MUTATION_RATE; i++) {
            population[r.nextInt(population.length)].mutate();
        }

        updateFitness();

        population = Sort.quicksort(population);
    }

    private static void updateFitness() {
        for (int i = 0; i < POPULATION_SIZE; i++) {
            population[i].determineFitness();
        }
    }

    private static void removeWeakest() {
        for (int i = population.length / 2; i < population.length; i++) {
            population[i] = null;
        }
    }

    private static Individual breed(Individual male, Individual female) {
        return new Individual(new Vector3D((male.getStartPosition().getX() + female.getStartPosition().getX())/2, (male.getStartPosition().getY() + female.getStartPosition().getY())/2, (male.getStartPosition().getZ() + female.getStartPosition().getZ())/2), (male.getVelocity() + female.getVelocity())/2);
    }

    private static Vector3dInterface randomUnitVector() {
        Vector3dInterface vector = new Vector3D(r.nextDouble(), r.nextDouble() * -1, randomDouble() / 10);

        return vector.mul(1 / vector.norm());
    }

    private static double randomDouble() {
        double rand = r.nextDouble();

        if (r.nextBoolean()) {
            return rand * -1;
        }

        return rand;
    }

    private static Individual copyOf(Individual a) {
        Individual copy = new Individual(a.getStartPosition(), a.getVelocity());
        copy.setFitness(a.getFitness());
        copy.setResults(a.getResults());

        return copy;
    }

    private static void print() {
        StringBuilder sb = new StringBuilder();
        sb.append("Top 3: \n");
        for (int i = 0; i < 3; i++) {
            sb.append(String.format("[ %s ] \n", population[i]));
        }

        System.out.println(sb.toString());
    }
}