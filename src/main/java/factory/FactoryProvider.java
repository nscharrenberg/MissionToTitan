package main.java.factory;

import main.java.repositories.SolarSystemRepository;

public class FactoryProvider {
    private static SolarSystemRepository solarSystemRepository = new SolarSystemRepository();

    public static SolarSystemRepository getSolarSystemFactory() {
        return FactoryProvider.solarSystemRepository;
    }
}
