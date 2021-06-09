package org.um.dke.titan.factory;

import org.um.dke.titan.repositories.GameRepository;
import org.um.dke.titan.repositories.SolarSystemRepositoryNew;
import org.um.dke.titan.repositories.interfaces.IGameRepository;
import org.um.dke.titan.repositories.interfaces.ISolarSystemRepository;

public class FactoryProvider {
    private static IGameRepository gameRepository = new GameRepository();
    private static ISolarSystemRepository solarSystemRepository = new SolarSystemRepositoryNew();

    public static IGameRepository getGameRepository() {
        return FactoryProvider.gameRepository;
    }

    public static ISolarSystemRepository getSolarSystemRepository() {
        return FactoryProvider.solarSystemRepository;
    }
}
