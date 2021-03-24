package physics.gravity.simulation;

import domain.Vector3D;
import factory.FactoryProvider;
import interfaces.Vector3dInterface;

import java.util.Arrays;
import java.util.Random;

public class Main {

    static Individual[] population;
    static final int MUTATION_RATE = 10;
    static final int POPULATION_SIZE = 90;
    static final int GENERATIONS = 50;
    static Individual min;
    static Random r;

    public static void main(String[] args) {
        r = new Random();
        init();
        min = copyOf(population[0]);


        for (int j = 0; j < 100; j++) {
            init();
            for (int i = 0; i < GENERATIONS; i++) {
                System.out.print("Generation " + (i + 1) + " | ");
                System.out.print("min: " + min + " | ");
                generation();
                if (min.fitness > population[0].fitness)
                    min = copyOf(population[0]);

                print(population);
                System.out.println("| Min vector: " + min.vector.mul(min.speed));
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
        print(population);
        System.out.println();
    }

    public static Individual copyOf(Individual a) {
        Individual copy = new Individual(a.vector, a.speed);
        copy.fitness = a.fitness;
        return copy;
    }

    public static void generation() {
        removeWeakest();
        for(int i =  population.length/2; i < population.length; i++) {
            population[i] = bread(population[r.nextInt(population.length)/2], population[r.nextInt(population.length)/2]);
        }

        population[population.length-1 ]= new Individual(randomUnitVector(), 40000 + r.nextInt(20000));

        for(int i = 0; i < MUTATION_RATE; i++) {
            population[r.nextInt(population.length)].mutate();
        }
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
    public static Individual bread(Individual a, Individual b) {
        return new Individual(new Vector3D((a.vector.getX() + b.vector.getX())/2, (a.vector.getY() + b.vector.getY())/2, (a.vector.getZ() + b.vector.getZ())/2), (a.speed+b.speed)/2);
    }



    private static void initPopulation() {
        population = new Individual[POPULATION_SIZE];

        for (int i = 0; i < POPULATION_SIZE; i++) {
            population[i] = new Individual(randomUnitVector(), 40000 + r.nextInt(20000));
        }
    }

    /**
     * creates a random unit vector
     */
    public static Vector3dInterface randomUnitVector() {
        Vector3dInterface vector = new Vector3D(r.nextDouble(), r.nextDouble()*-1, randomDouble()/10);
        double norm = vector.norm();
        return vector.mul(1/norm);
    }

    /**
     * returns a random double between -1 and 1
     * @return
     */
    public static double randomDouble() {
        double random = r.nextDouble();
        if (r.nextBoolean())
            return random * -1;
        else
            return random;
    }

    private static void print(Individual[] array) {
        System.out.print(Arrays.toString(array));
    }
}
