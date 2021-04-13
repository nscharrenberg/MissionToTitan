package domain;

import interfaces.Vector3dInterface;

public class Moon extends Planet {
    private Planet parent;

    /**
     * @param mass           - the mass of the object in kilograms
     * @param vector         - the vector containing the position of the object
     * @param newVectorState - the vector containing the changes of position of the object
     * @param name           - the name of the planet
     * @param radius 
     */
    public Moon(double mass, double radius, Vector3dInterface vector, Vector3dInterface newVectorState, String name, Planet parent) {
        super(mass, radius, vector, newVectorState, name);

        this.parent = parent;
    }

    public Planet getParent() {
        return parent;
    }

    public void setParent(Planet parent) {
        this.parent = parent;
    }
    
    public String toString() {
    	return "Name: "+this.getName();
    }
}