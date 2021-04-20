package org.um.dke.titan.domain;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import org.um.dke.titan.interfaces.Vector3dInterface;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class Planet extends MovingObject {
    private Map<String, Moon> moons;

    public Planet(String name, float mass, float radius, Vector3dInterface position, float zoomLevel, Vector3dInterface velocity) {
        super(name, mass, radius, position, zoomLevel, velocity);
        this.moons = new HashMap<>();
    }

    public Planet(String name, float mass, float radius, Vector3dInterface position, float zoomLevel, Vector3dInterface velocity, Map<String, Moon> moons) {
        super(name, mass, radius, position, zoomLevel, velocity);
        this.moons = moons;
    }

    public Planet(String name, float mass, float radius, Vector3dInterface position, float zoomLevel, Vector3dInterface velocity, Queue<MovingObject> timeline, Map<String, Moon> moons) {
        super(name, mass, radius, position, zoomLevel, velocity, timeline);
        this.moons = moons;
    }

    public Planet(String name, float mass, float radius, Vector3 position, float zoomLevel, Vector3 velocity) {
        super(name, mass, radius, position, zoomLevel, velocity);
        this.moons = new HashMap<>();
    }

    public Planet(String name, float mass, float radius, Vector3 position, float zoomLevel, Vector3 velocity, Map<String, Moon> moons) {
        super(name, mass, radius, position, zoomLevel, velocity);
        this.moons = moons;
    }

    public Planet(String name, float mass, float radius, Vector3 position, float zoomLevel, Vector3 velocity, Queue<MovingObject> timeline, Map<String, Moon> moons) {
        super(name, mass, radius, position, zoomLevel, velocity, timeline);
        this.moons = moons;
    }

    public Moon getMoonByName(String name) {
        return this.moons.get(name);
    }

    public void addMoon(String name, Moon moon) {
        this.moons.put(name, moon);
    }

    public void removeMoon(String name) {
        this.moons.remove(name);
    }

    public Map<String, Moon> getMoons() {
        return moons;
    }

    public void setMoons(Map<String, Moon> moons) {
        this.moons = moons;
    }

    @Override
    public void render(SpriteBatch batch, OrthographicCamera camera) {
        super.render(batch, camera);

        for (Moon moon : getMoons().values()) {
            moon.render(batch, camera);
            moon.next();
        }
    }

    @Override
    public void dispose() {
        super.dispose();

        for (Moon moon : getMoons().values()) {
            moon.dispose();
        }
    }

    @Override
    public void addActor(Stage stage) {
        super.addActor(stage);

        for (Moon moon : getMoons().values()) {
            moon.addActor(stage);
        }
    }

    public Planet clone() {
        return new Planet(name, mass, radius, position, zoomLevel, velocity, moons);
    }
}
