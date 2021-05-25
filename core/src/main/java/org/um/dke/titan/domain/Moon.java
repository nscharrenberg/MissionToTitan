package org.um.dke.titan.domain;

import com.badlogic.gdx.math.Vector3;
import org.um.dke.titan.interfaces.Vector3dInterface;

import java.util.Queue;

public class Moon extends MovingObject {
    private transient Planet planet;

    public Moon(String name, float mass, float radius, Vector3dInterface position, float zoomLevel, Vector3dInterface velocity, Planet planet) {
        super(name, mass, radius, position, zoomLevel, velocity);
        this.planet = planet;
    }

    public Moon(String name, float mass, float radius, Vector3dInterface position, float zoomLevel, Vector3dInterface velocity, Queue<Vector3dInterface> timeline, Planet planet) {
        super(name, mass, radius, position, zoomLevel, velocity, timeline);
        this.planet = planet;
    }

    public Moon(String name, float mass, float radius, Vector3 position, float zoomLevel, Vector3 velocity, Planet planet) {
        super(name, mass, radius, position, zoomLevel, velocity);
        this.planet = planet;
    }

    public Moon(String name, float mass, float radius, Vector3 position, float zoomLevel, Vector3 velocity, Queue<Vector3dInterface> timeline, Planet planet) {
        super(name, mass, radius, position, zoomLevel, velocity, timeline);
        this.planet = planet;
    }

    public Planet getPlanet() {
        return planet;
    }

    public Moon clone() {
        return new Moon(name, mass, radius, position, zoomLevel, velocity, planet);
    }
}
