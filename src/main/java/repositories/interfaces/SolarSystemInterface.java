package repositories.interfaces;

import domain.MovingObject;
import domain.Planet;

import java.util.List;

public interface SolarSystemInterface {
    List<MovingObject> getPlanets();
    void addPlanet(Planet planet);
    void addPlanets(Planet... planets);
    boolean removePlanet(String name);
    Planet findPlanet(String name);
    void init();
    void preprocessing();
    List<List<MovingObject>> getTimeLine();
}
