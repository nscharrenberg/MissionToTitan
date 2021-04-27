package physics.gravity.simulation;

import domain.Vector3D;
import interfaces.Vector3dInterface;

import java.util.Random;

public class Main {

    static Individual[] population;
    static final int MUTATION_RATE = 10;
    static final int POPULATION_SIZE = 90;
    static final int GENERATIONS = 1000;
    static Individual min;
    static Random r;

    public static void main(String[] args) {
        System.out.println("Starting Genetic Simulation");
        Simulate();
    }

    public static void Simulate() {
        r = new Random();
        min = new Individual(new Vector3D(1,1,1));
        min.fitness = Double.MAX_VALUE;

        for (int j = 0; j < 100; j++) {
            init();
            for (int i = 0; i < GENERATIONS; i++) {
                long startTime = System.currentTimeMillis();
                generation();

                if (min.getFitness() > population[0].getFitness())
                    min = copyOf(population[0]);

                System.out.print("Generation " + (i + 1) + " | ");
                print();
                System.out.print("| Min: " + min);
                System.out.print("| Min vector: [" + min.velocity.getX() + "," + min.velocity.getY() + "," + min.velocity.getZ() + "] ");
                System.out.print("| Speed: [" + min.velocity.norm() + " m/s] ");
                long endTime = System.currentTimeMillis();
                System.out.println("| Compute Time: [" + ((endTime - startTime)/1000) + "s]");
            }
        }
    }

    public static void init() {
        initPopulation();
        System.out.print("Generation start | ");
        population = Sort.quicksort(population);
        if (min != null) {
            population[10] = copyOf(min);
        }
        print();
        System.out.println();
    }

    public static Individual copyOf(Individual a) {
        Individual copy = new Individual(a.velocity);
        copy.determineFitness();
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

        population[population.length-1 ]= new Individual(randomVector());

        for(int i = 0; i < MUTATION_RATE; i++) {
            population[r.nextInt(population.length-1)+1].mutate();
        }
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
        return new Individual(new Vector3D((a.velocity.getX() + b.velocity.getX())/2, (a.velocity.getY() + b.velocity.getY())/2, (a.velocity.getZ() + b.velocity.getZ())/2));
    }

    private static void initPopulation() {
        population = new Individual[POPULATION_SIZE];

        for (int i = 0; i < POPULATION_SIZE; i++) {
            population[i] = new Individual(randomVector());
        }
        updateFitness();
    }

    /**
     * creates a random unit vector
     */
    public static Vector3dInterface randomVector() {
        Vector3D randomVector = new Vector3D(r.nextDouble(), r.nextDouble()*-1.5, r.nextDouble()/-10);
        randomVector = (Vector3D)randomVector.mul(1/randomVector.norm());
        return randomVector.mul(40000 + r.nextDouble()*20000.0);
    }

    /**
     * prints out top 3 individuals
     */
    private static void print() {
        System.out.print("Top 3: ");
        for (int i = 0; i < 3; i++) {
            System.out.print("[" + population[i] + "] ");
        }
    }

    private static void updateFitness() {
        for (int i = 0; i < POPULATION_SIZE; i++) {
             population[i].determineFitness();
        }
    }
}
