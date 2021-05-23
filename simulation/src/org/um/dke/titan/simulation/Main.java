package org.um.dke.titan.simulation;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.um.dke.titan.domain.Vector3D;

import java.util.Arrays;
import java.util.Random;

public class Main {
    static Individual[] population;
    static final int MUTATION_RATE = 10;
    static final int POPULATION_SIZE = 150;
    static final int GENERATIONS = 50;
    static Individual max;
    static Random r;

    static final int TIME_CLOSEST_TO_TITAN = 8660;

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

    public static void Simulate() {
        r = new Random();
        max = new Individual(new Vector3D[] {new Vector3D(1,1,1), new Vector3D(-1,-1,-1), new Vector3D(1,1,1)}, new int[] {-1,-1,-1,-1,-1,-1}, new int[] {1,1,1});
        max.fitness = Integer.MIN_VALUE;

        for (int j = 0; j < 100; j++) {
            init();
            for (int i = 0; i < GENERATIONS; i++) {
                long startTime = System.currentTimeMillis();
                generation();

                if (max.getFitness() < population[0].getFitness())
                    max = copyOf(population[0]);

                System.out.println("| Generation " + (i + 1));
                print();
                System.out.println("| Max:          [" + max.fitness + "]");
                System.out.println("| Directions:   [" + Arrays.toString(max.directions) + "] ");
                System.out.println("| Interval:     [" + Arrays.toString(max.interval) + "]");
                System.out.println("| Percentages:  [" + Arrays.toString(max.percentage) + "]");
                long endTime = System.currentTimeMillis();
                System.out.println("| Compute Time: [" + ((endTime - startTime)/1000) + "s]");
                System.out.println();
            }
        }
    }

    public static void init() {
        initPopulation();
        System.out.println("| Generation start");
        population = Sort.quicksort(population);
        if (max != null) {
            population[10] = copyOf(max);
        }
        print();
        System.out.println();
    }

    public static Individual copyOf(Individual a) {
        Individual copy = new Individual(a.directions, a.interval, a.percentage);
        copy.fitness = a.getFitness();
        return copy;
    }

    public static void generation() {
        removeWeakest();
        for(int i =  population.length/2; i < population.length; i++) {
            int a = r.nextInt(population.length)/2;
            int b = r.nextInt(population.length)/2;

            if (a != b)
                population[i] = breed(population[r.nextInt(population.length) / 2], population[r.nextInt(population.length) / 2]);
            else if (a == b)
                i--;
        }

        for(int i = 0; i < MUTATION_RATE; i++)
            population[r.nextInt(population.length)].mutate();

        updateFitness();
        population = Sort.quicksort(population);
    }

    /**
     * removes the weakest half of the population
     */
    public static void removeWeakest() {
        for(int i = population.length/2; i < population.length; i++) {
            population[i] = null;
        }
    }

    /**
     * returns a new child from 2 parents a and b
     */
    public static Individual breed(Individual a, Individual b) {
        return new Individual(a.crossVectors(b), a.crossIntervals(b), a.crossPercentages(b));
    }

    private static void initPopulation() {
        population = new Individual[POPULATION_SIZE];

        for (int i = 0; i < POPULATION_SIZE; i++) {

            Vector3D[] directions = {randomUnitVector(), randomUnitVector(), randomUnitVector()};
            int[] percentages = {r.nextInt(30),r.nextInt(30), r.nextInt(30)};

            population[i] = new Individual(directions, randomInterval(), percentages);
        }
        updateFitness();
    }

    /**
     * creates a random unit vector
     */
    public static Vector3D randomUnitVector() {
        Vector3D vector = new Vector3D(-1 + r.nextDouble()*2, -1 + r.nextDouble()*2, -1 + r.nextDouble() * 2);
        double norm = vector.norm();
        return (Vector3D) vector.mul(1/norm);
    }

    public static int[] randomInterval() {
        int[] interval = new int[6];

        interval[0] = 8500 + r.nextInt(50);

        for (int i = 1; i < interval.length; i++) {
            interval[i] = interval[i-1] + r.nextInt(50);
        }

        return interval;
    }

    /**
     * prints out top 3 individuals
     */
    private static void print() {
        System.out.print("| Top 3:        ");
        for (int i = 0; i < 3; i++) {
            System.out.print("[" + population[i].fitness + "] ");
        }
        System.out.println();
    }

    private static void updateFitness() {
        for (int i = 0; i < POPULATION_SIZE; i++) {
            population[i].determineFitness();
        }
    }
}