package physics.gravity;

import domain.Vector3D;
import interfaces.RateInterface;

public class Rate implements RateInterface {

    private Vector3D p;
    private Vector3D v;

    public Rate(Vector3D p, Vector3D v) {
        this.p = p;
        this.v = v;
    }

    public Vector3D<Rate>  newTown() {

    }
}
