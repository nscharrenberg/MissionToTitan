package physics.gravity;

import interfaces.RateInterface;
import interfaces.Vector3dInterface;

public class Rate implements RateInterface {

    private Vector3dInterface acceleration;
    private Vector3dInterface velocity;
    //doesn't make sense to pass the accelleration from here
    public Rate(Vector3dInterface acceleration, Vector3dInterface velocity){
        this.acceleration = acceleration;
        this.velocity = velocity;
    }

    public void setAcceleration(Vector3dInterface acceleration) {
        this.acceleration = acceleration;
    }

    public void setVelocity(Vector3dInterface velocity) {
        this.velocity = velocity;
    }

    public Vector3dInterface getAcceleration(){
        return acceleration;
    }

    public Vector3dInterface getVelocity() {
        return velocity;
    }
}
