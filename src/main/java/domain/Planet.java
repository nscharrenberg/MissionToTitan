package domain;

import interfaces.Vector3dInterface;

import java.util.ArrayList;
import java.util.List;

public class Planet extends MovingObject {
    private String name;
    private double radius;
    private List<Moon> moons;

    /**
     * @param mass - the mass of the object in kilograms
     * @param vector - the vector containing the position of the object
     * @param newVectorState - the vector containing the changes of position of the object
     * @param name - the name of the planet
     */
    public Planet(double mass, double radius, Vector3dInterface vector, Vector3dInterface newVectorState, String name) {
        super(mass, vector, newVectorState, name);
        this.name = name;
        this.radius = radius;
    }

    public List<Moon> getMoons() {
    	if(moons != null)
    		return moons;
    	else
    		return new ArrayList<Moon>();
    }

    public void setMoons(List<Moon> moons) {
        this.moons = moons;
    }

    public void addMoon(double mass, double radius, Vector3dInterface vector, Vector3dInterface newVectorState, String name) {
        if (this.moons == null) {
            this.moons = new ArrayList<>();
        }

        this.moons.add(new Moon(mass, radius, vector, newVectorState, name, this));
    }

    public boolean removeMoon(Moon moon) {
        if (this.moons == null) {
            return false;
        }

        return this.moons.remove(moon);
    }

    public Moon getMoon(String name) {


        if (this.moons.size() <= 0) {
            return null;
        }

        if (this.moons.size() == 1) {
            return this.moons.get(0).getName().equals(name) ? this.moons.get(0) : null;
        }

        return this.moons.stream().filter(m -> m.getName().equals(name)).findFirst()
                .orElse(null);
    }



    @Override
    public String toString() {
        return "Name: "+this.name+", Moons: " + getMoons().toString();
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
}
