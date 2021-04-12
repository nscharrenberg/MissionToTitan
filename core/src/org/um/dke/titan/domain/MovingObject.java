package org.um.dke.titan.domain;

import com.badlogic.gdx.math.Vector3;

import java.util.LinkedList;
import java.util.Queue;

public class MovingObject extends SpaceObject {
    protected Queue<SpaceObject> timeline;

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
}
