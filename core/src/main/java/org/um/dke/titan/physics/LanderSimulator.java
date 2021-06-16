package org.um.dke.titan.physics;

import org.um.dke.titan.domain.Lander;
import org.um.dke.titan.domain.Planet;
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

                force = force.add(new Vector3D(0, dt*-g*landerMass, 0));// actual application of gravity

                //LANDING LOGIC
                //1. make lander land smoothly
                //System.out.println("y: "+ landerArray[i-1].getPosition().getY()+ "y velo: " +landerArray[i-1].getVelocity().getY()+" thrust: "+land(landerArray[i-1].getPosition().getY()));
                if(((landerArray[i-1].getVelocity().getY() < -100) && (landerArray[i-1].getPosition().getY() > 10000))) {
                    force = force.add(mainThruster(Math.abs(land(landerArray[i - 1].getPosition().getY()))));
                }
                if(((landerArray[i-1].getVelocity().getY() < -10) && (landerArray[i-1].getPosition().getY() < 11000))){
                    force = force.add(accelerateToVelocity(-10, landerArray[i-1]));
                }
                //----

                landerArray[i] = step(landerArray[i - 1], dt);
                //System.out.println("t: "+ts[i]+" x: " + landerArray[i].getPosition().getX() + " y: " + landerArray[i].getPosition().getY());
            }


        }
        System.out.println("MAXMIUM VELOCITY REACHED: " + maxVelocity());
    }

    private double land(double y) {
        double threshhold = 100000;
        double a, b, c;
        if(y > threshhold)
            return 0;
        if(y < 4000){
            a = 80;
            b = -0.0075;
            return a + b*y;
        }
        a = 64.44444444;
        b = -0.001555555556;
        c = 1.111111111E-8;
        return  a + b*y + c*Math.pow(y,2);
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
    public Vector3dInterface accelerateToVelocity(double desiredVelocity, PlanetState landerState){
        PlanetState newLanderState = step(landerState, dt);
        Vector3dInterface newForce = newLanderState.getForce();

        double velocity = landerState.getVelocity().getY();

        double desiredAcceleration = (desiredVelocity-velocity)/dt;

        Vector3dInterface desiredForce = new Vector3D(0,0,0);
        desiredForce.setY(dt*-g*landerMass + landerMass*desiredAcceleration);
        System.out.println(desiredForce);


        return desiredForce;
    }



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