package org.um.dke.titan.domain;

import com.badlogic.gdx.math.Vector3;
import org.um.dke.titan.interfaces.Vector3dInterface;

import java.util.LinkedList;
import java.util.Queue;

public class MovingObject extends SpaceObject {
    protected Queue<SpaceObject> timeline;
    protected Vector3dInterface force;
    protected Vector3dInterface acceleration;
    protected Vector3dInterface velocity;

    public MovingObject(String name, float mass, float radius, Vector3dInterface position, float zoomLevel) {
        super(name, mass, radius, position, zoomLevel);
        this.timeline = new LinkedList<>();
    }

    public MovingObject(String name, float mass, float radius, Vector3dInterface position, float zoomLevel, Queue<SpaceObject> timeline) {
        super(name, mass, radius, position, zoomLevel);
        this.timeline = timeline;
    }

    public MovingObject(String name, float mass, float radius, Vector3 position, float zoomLevel) {
        super(name, mass, radius, position, zoomLevel);
        this.timeline = new LinkedList<>();
    }

    public MovingObject(String name, float mass, float radius, Vector3 position, float zoomLevel, Queue<SpaceObject> timeline) {
        super(name, mass, radius, position, zoomLevel);
        this.timeline = timeline;
    }

    public void add(SpaceObject object) {
        this.timeline.offer(object);
    }

    public void remove() {
        this.timeline.poll();
    }

    public Queue<SpaceObject> getTimeline() {
        return timeline;
    }

    public void setTimeline(Queue<SpaceObject> timeline) {
        this.timeline = timeline;
    }

    public MovingObject clone() {
        return new MovingObject(name.getText().toString(), mass, radius, position, zoomLevel);
    }

    public Vector3dInterface getForce() {
        return force;
    }

    public void setForce(Vector3 force) {
        this.force = new Vector3D(force);
    }

    public void setForce(Vector3dInterface force) {
        this.force = force;
    }

    public Vector3dInterface getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(Vector3 acceleration) {
        this.acceleration = new Vector3D(acceleration);
    }

    public void setAcceleration(Vector3dInterface acceleration) {
        this.acceleration = acceleration;
    }

    public Vector3dInterface getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector3 velocity) {
        this.velocity = new Vector3D(velocity);
    }

    public void setVelocity(Vector3dInterface velocity) {
        this.velocity = velocity;
    }
}
