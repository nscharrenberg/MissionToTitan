package org.um.dke.titan.domain;

import com.badlogic.gdx.math.Vector3;
import org.um.dke.titan.interfaces.Vector3dInterface;

import java.util.LinkedList;
import java.util.Queue;

public class MovingObject extends SpaceObject {
    protected Queue<MovingObject> timeline;
    protected Vector3dInterface force;
    protected Vector3dInterface acceleration;
    protected Vector3dInterface velocity;

    public MovingObject(String name, float mass, float radius, Vector3dInterface position, float zoomLevel, Vector3dInterface velocity) {
        super(name, mass, radius, position, zoomLevel);
        this.timeline = new LinkedList<>();
        this.velocity = velocity;
    }

    public MovingObject(String name, float mass, float radius, Vector3dInterface position, float zoomLevel, Vector3dInterface velocity, Queue<MovingObject> timeline) {
        super(name, mass, radius, position, zoomLevel);
        this.timeline = timeline;
        this.velocity = velocity;
    }

    public MovingObject(String name, float mass, float radius, Vector3 position, float zoomLevel, Vector3 velocity) {
        super(name, mass, radius, position, zoomLevel);
        this.timeline = new LinkedList<>();
        this.velocity = new Vector3D(velocity);
    }

    public MovingObject(String name, float mass, float radius, Vector3 position, float zoomLevel, Vector3 velocity ,Queue<MovingObject> timeline) {
        super(name, mass, radius, position, zoomLevel);
        this.timeline = timeline;
        this.velocity = new Vector3D(velocity);
    }

    public void add(MovingObject object) {
        this.timeline.offer(object);
    }

    public void remove() {
        this.timeline.poll();
    }

    public Queue<MovingObject> getTimeline() {
        return timeline;
    }

    public void setTimeline(Queue<MovingObject> timeline) {
        this.timeline = timeline;
    }

    public MovingObject clone() {
        return new MovingObject(name.getText().toString(), mass, radius, position, zoomLevel, velocity);
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

    public void next() {
        if (this.timeline.size() <= 0) {
            return;
        }

        MovingObject object = this.timeline.remove();

        // TODO: Speed up drawing by relatively removing items.
        for (int i = 0; i < 50; i++) {
            if (this.timeline.size() > 0) {
                timeline.remove();
            }
        }

        this.position = object.getPosition();
    }
}
