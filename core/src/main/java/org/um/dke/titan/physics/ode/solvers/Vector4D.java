package org.um.dke.titan.physics.ode.solvers;

import org.um.dke.titan.domain.Vector3D;
import org.um.dke.titan.interfaces.Vector3dInterface;

public class Vector4D extends Vector3D{

    private double v; // velocity vector

    public Vector4D(double x, double y, double z, double v){
        super(x,y,z);
        this.v = v;
    }

    public Vector4D(Vector3dInterface vector, double v) {
        super(vector.getX(), vector.getY(), vector.getZ());
        this.v = v;
    }

    public double getV() {
        return v;
    }

    public void setV(double v) {
        this.v = v;
    }


    public Vector4D sub(Vector4D other) {
        return new Vector4D(this.getX()-other.getX(), this.getY()-other.getY(), this.getZ()-other.getZ(), this.getV()-other.getV());
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ", " + v + ")";
    }
}
