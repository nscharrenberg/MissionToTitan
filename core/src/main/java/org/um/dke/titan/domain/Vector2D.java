package org.um.dke.titan.domain;

import com.badlogic.gdx.math.Vector3;

public class Vector2D extends Vector3D{
    protected double x;
    protected double y;
    protected double z;

    public Vector2D(Vector2D position) {
        this.x = position.x;
        this.y = position.y;
        this.z = 0;
    }

    public Vector2D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = 0;
    }

    @Override
    public void setZ(double z) {
        throw new RuntimeException("Cannot assign Z value to 2d vector");
    }


}
