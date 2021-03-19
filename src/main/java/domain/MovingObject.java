package domain;

import interfaces.Vector3dInterface;
import interfaces.gui.IUpdate;

import java.util.Random;

public class MovingObject extends SpaceObject implements IUpdate {
    private Vector3dInterface force;
    private Vector3dInterface acceleration;
    private Vector3dInterface velocity;
    private String name;

    /**
     * @param mass   - the mass of the object in kilograms
     * @param position - the Vector Object with coordinates
     */
    public MovingObject(double mass, Vector3dInterface position, Vector3dInterface velocity, String name) {
        super(mass, position);
        this.velocity = velocity;
        this.name = name;
    }

    public void setVelocity(Vector3dInterface velocity) {
        this.velocity = velocity;
    }

    public Vector3dInterface getVelocity() {
        return velocity;
    }

    public void setForce(Vector3dInterface force) {
        this.force = force;
    }

    public Vector3dInterface getForce() {
        return force;
    }

    public void setAcceleration(Vector3dInterface acceleration) {
        this.acceleration = acceleration;
    }

    public Vector3dInterface getAcceleration() {
        return acceleration;
    }

    @Override
    public void update() {
        Random r = new Random();

        boolean xFlip = r.nextBoolean();
        boolean yFlip = r.nextBoolean();

        if (xFlip) {
            getPosition().setX(getPosition().getX() - (r.nextInt(5)*1E11));
        } else {
            getPosition().setX(getPosition().getX() + r.nextInt(5)*1E11);
        }

        if (yFlip) {
            getPosition().setY(getPosition().getY() - r.nextInt(5)*1E11);
        } else {
            getPosition().setY(getPosition().getY() + r.nextInt(5)*1E11);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "MovingObject{" +
                "force=" + force +
                ", acceleration=" + acceleration +
                ", velocity=" + velocity +
                ", name='" + name + '\'' +
                '}';
    }
    
    public MovingObject clone() {
    	return new MovingObject(this.getMass(), ((Vector3D) this.getPosition()).clone(), ((Vector3D) this.velocity).clone(), this.name);
    }
}
