package org.um.dke.titan.physics;

import org.um.dke.titan.domain.Lander;
import org.um.dke.titan.domain.Vector3D;
import org.um.dke.titan.interfaces.Vector3dInterface;
import org.um.dke.titan.physics.ode.functions.solarsystemfunction.PlanetRate;
import org.um.dke.titan.physics.ode.functions.solarsystemfunction.PlanetState;

public class LanderSimulator{
    private Lander lander;
    private double surfaceLevel = 0.0;
    private PlanetState[] landerArray;
    private double[] ts;
    private final double landerMass;
    private Vector3dInterface force;
    private final double g = 1.352;// m/s^2

    /**
     *
     * @param y0 starting state
     * @param tf timeframe
     * @param dt interval
     */
    public LanderSimulator(PlanetState y0, double tf, double dt){
        //init lander
        landerMass = 6000;//kg
        force = new Vector3D(0,0,0);
        landerArray = new PlanetState[(int)Math.round(tf/dt)];
        ts = new double[landerArray.length];
        //start landing
        landerArray[0] = y0;
        ts[0] = 0;
        for(int i = 1; i < landerArray.length; i++){
            ts[i] = i*dt;

            if(landerArray[i-1].getPosition().getY() <= surfaceLevel){
                landerArray[i] = landerArray[i-1]; //to stop the lander from falling into titan
                landerArray[i].getPosition().setY(0);
            } else {
                force = force.add(new Vector3D(0, -g*landerMass, 0));
                landerArray[i] = step(landerArray[i - 1], dt);
            }

            System.out.println("x: " + landerArray[i].getPosition().getX() + " - y: " + landerArray[i].getPosition().getY());
        }
    }

    private PlanetRate call(double t, PlanetState y) {
        Vector3dInterface rateAcceleration = force.mul(1 / landerMass); // a = F/m
        Vector3dInterface rateVelocity = y.getVelocity().add(rateAcceleration.mul(t));
        return new PlanetRate(rateVelocity, rateAcceleration);
    }

    private PlanetState step(PlanetState y, double h) {
        PlanetRate k1 = call(h, y).mul(h);
        PlanetRate k2 = call(0.5*h, y.addMul(0.5, k1)).mul(h);
        PlanetRate k3 = call(0.5*h, y.addMul(0.5, k2)).mul(h);
        PlanetRate k4 = call(h, y.addMul(1, k3)).mul(h);
        return y.addMul(1/6d, k1.addMul(2, k2).addMul(2, k3).addMul(1, k4));
    }


    public PlanetState[] getLanderArray() {
        return landerArray;
    }

    public double[] getTs() {
        return ts;
    }
}