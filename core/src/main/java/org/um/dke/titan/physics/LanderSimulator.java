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

    private final double tf, dt;

    private final double EXHAUST_VELOCITY = 2e4;
    private final double MAXIMUM_THRUST = 8e3;
    private final double AREA = 4.55;
    private final double PRESSURE = 100000;
    private final double radiusTitan = 2575.5e3;
    private final double MASS_FLOW_RATE = 2000;

    /**
     *
     * @param y0 starting state
     * @param tf timeframe
     * @param dt interval
     */
    public LanderSimulator(PlanetState y0, double tf, double dt){
        //init lander
        this.tf = tf;
        this.dt = dt;
        landerMass = 6000;//kg
        force = new Vector3D(0,0,0);
        landerArray = new PlanetState[(int)Math.round(tf/dt) + 1];
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

                force = force.add(new Vector3D(0, dt*-g*landerMass, 0));

                if(landerArray[i-1].getVelocity().getY()<-10){
                    force = force.add(mainThruster(30));
                }


                landerArray[i] = step(landerArray[i - 1], dt);
                System.out.println("t: "+ts[i]+" x: " + landerArray[i].getPosition().getX() + " y: " + landerArray[i].getPosition().getY());
            }


        }
        System.out.println("MAXMIUM VELOCITY REACHED: " + maxVelocity());
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

    //----------ENGINE HANDLING--------

    public Vector3dInterface mainThruster(double percentage){
        
        return new Vector3D(0, 1, 0).mul((percentage/100.0)*MAXIMUM_THRUST);
    }

    





    //issue with call method using the force field instead of the force provided by the parameter.
    //not sure if i can change that without messing things up
//    public Vector3dInterface decelerateToVelocity(double desiredVelocity, PlanetState landerState) {
//        //init values: force is already in landerstate, set it to a var
//        desiredVelocity*=-1;
//        double percentage = 1;
//        Vector3dInterface force = landerState.getForce();
//        Vector3dInterface engineForce = mainThruster(percentage);
//
//        //the y velocity in the next step with no engines
//        double actualVelocity = step(landerState, dt).getVelocity().getY();
//
//        //increase the percentage of power until the velocity on the next step will be smaller than the desired velocity
//        while(actualVelocity<desiredVelocity){
//            //increment the percentage; make sure it doesnt go above 100
//            if(percentage>100){
//                throw new RuntimeException("Percentage higher than 100");
//                }
//            percentage++;
//            //calculate force by engine
//            engineForce = mainThruster(percentage);
//            landerState.setForce(force.add(engineForce));
//            this.force = landerState.getForce();
//            actualVelocity = step(landerState, dt).getVelocity().getY();;
//
//        }
//       // System.out.println("ACTUAL VELOCITY: " + actualVelocity + ", DESIRED VELOCITY: " + desiredVelocity +  ", ENGINE FORCE: " + engineForce);
//        return force;
//    }

    //--------UTILS-------------
    public double maxVelocity(){
        double max = Double.MIN_VALUE;
        for(int i=0; i<landerArray.length; i++){
            double current = landerArray[i].getVelocity().norm();
            if(landerArray[i].getVelocity().norm()>max){
                max = current;
            }
        }
        return max;
    }
}