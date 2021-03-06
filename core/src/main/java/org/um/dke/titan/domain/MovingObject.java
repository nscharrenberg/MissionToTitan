package org.um.dke.titan.domain;

import com.badlogic.gdx.math.Vector3;
import org.um.dke.titan.factory.FactoryProvider;
import org.um.dke.titan.interfaces.Vector3dInterface;

import java.util.LinkedList;
import java.util.Queue;

public class MovingObject extends SpaceObject {
    protected Queue<Vector3dInterface> timeline;
    protected Vector3dInterface force;
    protected Vector3dInterface acceleration;
    protected Vector3dInterface velocity;

    public MovingObject(String name, float mass, float radius, Vector3dInterface position, float zoomLevel, Vector3dInterface velocity) {
        super(name, mass, radius, position, zoomLevel);
        this.timeline = new LinkedList<>();
        this.velocity = velocity;
    }

    public MovingObject(String name, float mass, float radius, Vector3dInterface position, float zoomLevel, Vector3dInterface velocity, Queue<Vector3dInterface> timeline) {
        super(name, mass, radius, position, zoomLevel);
        this.timeline = timeline;
        this.velocity = velocity;
    }

    public void add(Vector3dInterface object) {
        this.timeline.offer(object);
    }

    public void remove() {
        this.timeline.poll();
    }

    public Queue<Vector3dInterface> getTimeline() {
        return timeline;
    }

    public void setTimeline(Queue<Vector3dInterface> timeline) {
        this.timeline = timeline;
    }

    public MovingObject clone() {
        return new MovingObject(name, mass, radius, position, zoomLevel, velocity);
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

    private int timeOnTimeLine = 0;

    public void next() {
        if (this.timeline.size() <= 0) {
            return;
        }

        Vector3dInterface object = this.timeline.remove();

        for (int i = 0; i < FactoryProvider.getGameRepository().getTimeToSkip(); i++) {

            if (this.timeline.size() > 0) {
                timeline.remove();
                //timeOnTimeLine +=  FactoryProvider.getGameRepository().getTimeToSkip();
            }
            timeOnTimeLine++;


        }

        this.position = object;
    }

    public int getTimeOnTimeLine() {
        return timeOnTimeLine;
    }
}
