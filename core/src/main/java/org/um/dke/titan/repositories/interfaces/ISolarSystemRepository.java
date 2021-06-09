package org.um.dke.titan.repositories.interfaces;

import org.um.dke.titan.domain.Moon;
import org.um.dke.titan.domain.MovingObject;
import org.um.dke.titan.domain.Planet;
import org.um.dke.titan.domain.Rocket;
import org.um.dke.titan.interfaces.ODESolverInterface;
import org.um.dke.titan.interfaces.StateInterface;

import java.util.Map;

public interface ISolarSystemRepository {

    void init();

    Map<String, Planet> getPlanets();

    Planet getPlanetByName(String name);

    Moon getMoonByName(String planetName, String moonName);

    Map<String, Rocket> getRockets();

    void setRockets(Map<String, Rocket> rockets);

    Rocket getRocketByName(String name);

    void addRocket(String name, Rocket object);

    void preprocessing();

    StateInterface[] getTimeLineArray(ODESolverInterface solver, double tf, double dt);
}
