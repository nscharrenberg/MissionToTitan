package org.um.dke.titan.domain;

import com.badlogic.gdx.math.Vector3;
import org.um.dke.titan.interfaces.Vector3dInterface;

import java.util.Queue;

public class Moon extends MovingObject {
    private Planet planet;

    public Moon(String name, float mass, float radius, Vector3dInterface position, float zoomLevel, Planet planet) {
        super(name, mass, radius, position, zoomLevel);
        this.planet = planet;
    }

    public Moon(String name, float mass, float radius, Vector3dInterface position, float zoomLevel, Queue<SpaceObject> timeline, Planet planet) {
        super(name, mass, radius, position, zoomLevel, timeline);
        this.planet = planet;
    }

    public Moon(String name, float mass, float radius, Vector3 position, float zoomLevel, Planet planet) {
        super(name, mass, radius, position, zoomLevel);
        this.planet = planet;
    }

    public Moon(String name, float mass, float radius, Vector3 position, float zoomLevel, Queue<SpaceObject> timeline, Planet planet) {
        super(name, mass, radius, position, zoomLevel, timeline);
        this.planet = planet;
    }

    public Planet getPlanet() {
        return planet;
    }

    public Moon clone() {
        return new Moon(name.getText().toString(), mass, radius, position, zoomLevel, planet);
    }
}
