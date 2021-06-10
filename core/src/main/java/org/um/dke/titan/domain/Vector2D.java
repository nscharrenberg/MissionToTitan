package org.um.dke.titan.domain;

import com.badlogic.gdx.math.Vector2;
import org.um.dke.titan.interfaces.Vector3dInterface;

public class Vector2D extends Vector3D{


    public Vector2D(Vector2 position) {
        super(position.x, position.y, 0);
    }

    public Vector2D(double x, double y) {
        super(x,y,0);
    }

    @Override
    public void setZ(double z) {
        throw new RuntimeException("Cannot assign Z value to 2d vector");
    }

    @Override
    public Vector3dInterface addMul(double scalar, Vector3dInterface other) {
        if(!(other instanceof Vector2D)){
            throw new RuntimeException("Cannot add 3d vector to 2d vector");
        }
        Vector3dInterface resultant = new Vector2D(x, y);
        other = other.mul(scalar);

        return resultant.add(other);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2D vector2D = (Vector2D) o;
        return Double.compare(vector2D.x, x) == 0 &&
                Double.compare(vector2D.y, y) == 0;

    }

    @Override
    public Vector3D clone() {
        return new Vector2D(x, y);
    }
}
