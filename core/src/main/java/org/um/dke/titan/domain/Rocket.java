package org.um.dke.titan.domain;

import com.badlogic.gdx.math.Vector3;
import org.um.dke.titan.interfaces.Vector3dInterface;

import java.util.Queue;

public class Rocket extends MovingObject {
    public Rocket(String name, float mass, float radius, Vector3dInterface position, float zoomLevel, Vector3dInterface velocity) {
        super(name, mass, radius, position, zoomLevel, velocity);
    }

    public Rocket(String name, float mass, float radius, Vector3dInterface position, float zoomLevel, Vector3dInterface velocity, Queue<Vector3dInterface> timeline) {
        super(name, mass, radius, position, zoomLevel, velocity, timeline);
    }

    public Rocket(String name, float mass, float radius, Vector3 position, float zoomLevel, Vector3 velocity) {
        super(name, mass, radius, position, zoomLevel, velocity);
    }

    public Rocket(String name, float mass, float radius, Vector3 position, float zoomLevel, Vector3 velocity, Queue<Vector3dInterface> timeline) {
        super(name, mass, radius, position, zoomLevel, velocity, timeline);
    }
}
