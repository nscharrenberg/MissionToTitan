package org.um.dke.titan.repositories.interfaces;

import org.um.dke.titan.domain.Moon;
import org.um.dke.titan.domain.MovingObject;
import org.um.dke.titan.domain.Planet;
import org.um.dke.titan.domain.Rocket;
import org.um.dke.titan.interfaces.StateInterface;

import java.util.Map;

public interface ISolarSystemRepository {
    void addPlanet(String name, Planet planet);

    void removePlanet(String name);

    Map<String, Planet> getPlanets();

    void setPlanets(Map<String, Planet> planets);

    Planet getPlanetByName(String name);

    Moon getMoonByName(String planetName, String moonName);

    Map<String, Rocket> getRockets();

    void setRockets(Map<String, Rocket> rockets);

    Rocket getRocketName(String name);

    void addRocket(String name, Rocket object);

    void initWithGdx();

    void preprocessing();

    StateInterface[][] getTimeLineArray(double totalTime, double dt);

    void setTimeLineArray(StateInterface[][] timeLineArray);

    StateInterface[][] getTimeLineArray();

    void computeTimeLineArray(double totalTime, double dt);

    void init();

    boolean isImportSolarSystem();
    void setImportSolarSystem(boolean importSolarSystem);
}
