package org.um.dke.titan.factory;

import org.junit.jupiter.api.Test;
import org.um.dke.titan.interfaces.ODESolverInterface;
import org.um.dke.titan.physics.ode.solvers.ODESolverR4;
import org.um.dke.titan.repositories.GameRepository;
import org.um.dke.titan.repositories.SolarSystemRepository;
import org.um.dke.titan.repositories.TestRepository;
import org.um.dke.titan.repositories.interfaces.IGameRepository;
import org.um.dke.titan.repositories.interfaces.ISolarSystemRepository;

public class FactoryProvider {
    private static IGameRepository gameRepository = new GameRepository();
    private static ISolarSystemRepository solarSystemRepository = new SolarSystemRepository();
    private static TestRepository testRepository = new TestRepository();
    private static ODESolverInterface solver = new ODESolverR4();

    public static IGameRepository getGameRepository() {
        return FactoryProvider.gameRepository;
    }

    public static ISolarSystemRepository getSolarSystemRepository() {
        return FactoryProvider.solarSystemRepository;
    }

    public static ODESolverInterface getSolver() {
        return solver;
    }

    public static TestRepository getTestRepository() {
        return testRepository;
    }
}
