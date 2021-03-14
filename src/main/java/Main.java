package main.java;

import main.java.domain.Planet;
import main.java.factory.FactoryProvider;

public class Main {
    public static void main(String[] args) {
        FactoryProvider.getSolarSystemFactory().init();

        System.out.println("Planets: ");
        for (Planet planet : FactoryProvider.getSolarSystemFactory().getPlanets()) {
            System.out.println(planet);
        }
    }
}
