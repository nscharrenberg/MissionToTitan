package org.um.dke.titan.physics;

import org.um.dke.titan.domain.Lander;
import org.um.dke.titan.domain.Vector3D;
import org.um.dke.titan.interfaces.Vector3dInterface;
import org.um.dke.titan.physics.ode.functions.solarsystemfunction.PlanetRate;
import org.um.dke.titan.physics.ode.functions.solarsystemfunction.PlanetState;
import org.um.dke.titan.utils.SquareHandling;
import org.um.dke.titan.utils.WindGenerator;

public class LanderSimulator{
    private Lander lander;
    private double surfaceLevel = 0.1;
    private PlanetState[] landerArray;
    private double[] ts;
    private final double landerMass = 6000;
    private Vector3dInterface force;
    private final double g = 1.352;// m/s^2


    private final double probeSize = 15;
    private final double MOI = Math.pow(probeSize, 4)/12;
    private double tf, dt;

    private final double EXHAUST_VELOCITY = 2e4;
    private final double MAXIMUM_THRUST = 8e3;
    private final double MAXIMUM_SIDE_THRUST = 8e2;
    private final double AREA = 1;
    private final double PRESSURE = 100000;
    private final double MAXIMUM_WIND_FORCE = 10;
    private final double radiusTitan = 2575.5e3;
    private final double MASS_FLOW_RATE = 2000;
    private double massUsed = 0;
    private WindGenerator wg;
    private final double[] rotateThrust = {20.0,25.0,30.0,40.0};

    /**
     *
     * @param y0 starting state
     * @param tf timeframe
     * @param dt interval
     */
    public LanderSimulator(PlanetState y0, double tf, double dt){
        //init lander
        init(y0, tf, dt);

        for(int i = 1; i < landerArray.length; i++){
            force = new Vector3D(0,0,0);
            ts[i] = i*dt;
            if(landerArray[i-1].getPosition().getY() <= surfaceLevel) {
                landerArray[i] = landerArray[i-1]; //to stop the lander from falling into titan
                landerArray[i].getPosition().setY(0);
            }
            else {
                force = force.add(new Vector3D(0, dt*-g*landerMass, 0));// actual application of gravity
                //thrust(i);
            }

            if(landerArray[i-1].getPosition().getY() <= surfaceLevel) {
                landerArray[i] = landerArray[i-1]; //to stop the lander from falling into titan
                landerArray[i].getPosition().setY(0);
            }
            else {
                force = force.add(new Vector3D(0, dt*-g*landerMass, 0));// actual application of gravity



                controlVerticalVelocity(i);
                landerArray[i] = step(landerArray[i - 1]);
                landerArray[i].setAngle(generateWind(landerArray[i-1].getPosition(), i));

                //force = force.add(topLeftThruster(10, i));
                //System.out.println("t: "+ts[i]+" vy: " + landerArray[i-1].getVelocity().getY() + " y: " + landerArray[i-1].getPosition().getY());
            }


        }
        System.out.println("MAXIMUM VELOCITY REACHED: " + maxVelocity());
        System.out.println(massUsed);
    }

    public void thrust(int i){
        //0. create state i

        //1. generate the wind
        //we take position and angle of previous state, as a basis to compute the new state
        Vector3dInterface[] output = wg.getWind(landerArray[i - 1].getPosition(), landerArray[i - 1].getAngle());
        //2. apply force to lander basically angle step
        Vector3dInterface f = output[0], r = output[1];
        //--
        //torque, moment of inertia, get us angular acceleration
        double torque = crossProduct2D(f, r);
        double angularAccel = torque/MOI;
        //from ang acc calculate ang velo
        //ang acc * dt + prev ang velo
        double currAngVelo = angularAccel * dt + landerArray[i - 1].getAngularVelocity();//for i
        //compute change of angle
        double deltaTheta = currAngVelo * dt;
        double newTheta = WindGenerator.formatAngle(landerArray[i -1].getAngle() + deltaTheta);
        //--
        //3. step
        landerArray[i] = step(landerArray[i - 1]);
        landerArray[i].setAngle(newTheta);
        landerArray[i].setAngularVelocity(currAngVelo);

        //------REACT------
        Vector3dInterface[] rotation = rotationHandling(i);
        torque = crossProduct2D(rotation[0],rotation[1]);
        angularAccel = torque/MOI;
        currAngVelo = angularAccel * dt + landerArray[i].getAngularVelocity();
        newTheta = WindGenerator.formatAngle(landerArray[i].getAngle() + deltaTheta);


        landerArray[i].setAngle(newTheta);
        landerArray[i].setAngularVelocity(currAngVelo);

    }

    private Vector3dInterface[] rotationHandling(int i){
        Vector3dInterface[] output = new Vector3dInterface[2];
        //goals: - theta as close to zero as possible
        double steps = Math.PI/8;
        if(landerArray[i].getAngle() > 7*steps) {
            //take shortest path, but thrust differs based on prev angular velocity
            if(landerArray[i].getAngularVelocity() <= 0){// counterclockwise spin
                //apply thrust clockwise
                output = clockwiseThruster(rotateThrust[0], i);
            } else {
                output = counterclockwiseThruster(rotateThrust[0]/2.0, i);
            }
        } else if(landerArray[i].getAngle() > 6*steps) {//270 degrees
            if(landerArray[i].getAngularVelocity() <= 0){// counterclockwise spin
                output = clockwiseThruster(rotateThrust[1], i);
            } else {
                output = counterclockwiseThruster(rotateThrust[1]/2.0, i);
            }
        } else if(landerArray[i].getAngle() > 5*steps) {
            if(landerArray[i].getAngularVelocity() <= 0){// counterclockwise spin
                output = clockwiseThruster(rotateThrust[2], i);
            } else {
                output = counterclockwiseThruster(rotateThrust[2]/2.0, i);
            }
        } else if(landerArray[i].getAngle() > 4*steps) {//180 degrees
            if(landerArray[i].getAngularVelocity() <= 0){// counterclockwise spin
                output = clockwiseThruster(rotateThrust[3], i);
            } else {
                output = counterclockwiseThruster(rotateThrust[3]/2.0, i);
            }
        } else if(landerArray[i].getAngle() > 3*steps) {
            if(landerArray[i].getAngularVelocity() >= 0){// counterclockwise spin
                output = counterclockwiseThruster(rotateThrust[3], i);
            } else {
                output = clockwiseThruster(rotateThrust[3]/2.0, i);
            }
        }else if(landerArray[i].getAngle() > 2*steps) {//90 degrees
            if(landerArray[i].getAngularVelocity() >= 0){// counterclockwise spin
                output = counterclockwiseThruster(rotateThrust[2], i);
            } else {
                output = clockwiseThruster(rotateThrust[2]/2.0, i);
            }
        }else if(landerArray[i].getAngle() > 1*steps) {
            if(landerArray[i].getAngularVelocity() >= 0){// counterclockwise spin
                output = counterclockwiseThruster(rotateThrust[1], i);
            } else {
                output = clockwiseThruster(rotateThrust[1]/2.0, i);
            }
        }else if(landerArray[i].getAngle() > 0*steps) {
            if(landerArray[i].getAngularVelocity() >= 0){// counterclockwise spin
                output = counterclockwiseThruster(rotateThrust[0], i);
            } else {
                output = clockwiseThruster(rotateThrust[0]/2.0, i);
            }
        }
        output[0] = new Vector3D(0,0,0);
        output[1] = new Vector3D(0,0,0);
        return output;
    }



    private double land(double y) {
        double threshhold = 100000;
        double a, b, c;
        if(y > threshhold)
            return 0;
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

    private PlanetState step(PlanetState y) {
        PlanetRate k1 = call(dt, y).mul(dt);
        PlanetRate k2 = call(0.5*dt, y.addMul(0.5, k1)).mul(dt);
        PlanetRate k3 = call(0.5*dt, y.addMul(0.5, k2)).mul(dt);
        PlanetRate k4 = call(dt, y.addMul(1, k3)).mul(dt);
        return y.addMul(1/6d, k1.addMul(2, k2).addMul(2, k3).addMul(1, k4));
    }


    public PlanetState[] getLanderArray() {
        return landerArray;
    }

    public double[] getTs() {
        return ts;
    }

    private void init(PlanetState y0, double tf, double dt){
        this.tf = tf;
        this.dt = dt;
        force = new Vector3D(0,0,0);
        landerArray = new PlanetState[(int)Math.round(tf/dt) + 1];
        ts = new double[landerArray.length];
        //start landing
        landerArray[0] = y0;
        wg = new WindGenerator(MAXIMUM_WIND_FORCE, dt);
        ts[0] = 0;
    }

    //----------ENGINE HANDLING--------

    public Vector3dInterface mainThruster(double percentage, int i){
        PlanetState landerState = landerArray[i];
        massUsed += calculateMassUsed(percentage);

        Vector3dInterface c = landerState.getPosition();
        Vector3dInterface p = new Vector3D(c.getX(), c.getY() - probeSize*0.5, 0);
        Vector3dInterface f = new Vector3D(0, 1, 0).mul((percentage/100.0)*MAXIMUM_THRUST);
        Vector3dInterface r = SquareHandling.calculateDist(c, p.getX(), p.getY());

        f = SquareHandling.rotateAroundCenter(f, c, landerState.getAngle());
        handleRotation(f, r, i-1);
        return f;
    }

        private double calculateMassUsed(double percentageOfPower){
        return dt *(percentageOfPower/100)*((MAXIMUM_THRUST+PRESSURE*AREA)/EXHAUST_VELOCITY);
    }

    private double calculateMassUsedSide(double percentageOfPower){
        return dt *(percentageOfPower/100)*((MAXIMUM_SIDE_THRUST+PRESSURE*AREA)/EXHAUST_VELOCITY);
    }

    public Vector3dInterface[] clockwiseThruster(double percentage, int i){
        //some rotation handling
        //in 2 dimensions we can use torque = r*F*sin(theta)
        //where r is the distance to the center of gravity, F is the force, and theta is the angle between those (which i don't get rn)
        Vector3dInterface[] output = new Vector3dInterface[2];
        PlanetState landerState = landerArray[i];

        Vector3dInterface c = landerState.getPosition();
        Vector3dInterface p = new Vector3D(c.getX() - probeSize*0.5, c.getY() + probeSize*0.5, 0);
        Vector3dInterface f = new Vector3D(1, 0 , 0).mul((percentage/100.0)*MAXIMUM_SIDE_THRUST);
        Vector3dInterface r = SquareHandling.calculateDist(c, p.getX(), p.getY());

        f = SquareHandling.rotateAroundCenter(f, c, landerState.getAngle());

        massUsed += calculateMassUsedSide(percentage);
        output[0] = f;
        output[1] = r;
        return output;
    }

    public Vector3dInterface[] counterclockwiseThruster(double percentage, int i){
        Vector3dInterface[] output = new Vector3dInterface[2];
        massUsed += calculateMassUsedSide(percentage);
        PlanetState landerState = landerArray[i];

        Vector3dInterface c = landerState.getPosition();
        Vector3dInterface p = new Vector3D(c.getX() - probeSize*0.5, c.getY() - probeSize*0.5, 0);
        Vector3dInterface f = new Vector3D(1, 0 , 0).mul((percentage/100.0)*MAXIMUM_SIDE_THRUST);
        Vector3dInterface r = SquareHandling.calculateDist(c, p.getX(), p.getY());

        f = SquareHandling.rotateAroundCenter(f, c, landerState.getAngle());

        output[0] = f;
        output[1] = r;
        return output;
    }

    public void controlVerticalVelocity(int i){
        //LANDING LOGIC
        //1. make lander land smoothly
        if(landerArray[i-1].getPosition().getY() < 0.05){

        }
        //System.out.println("y: "+ landerArray[i-1].getPosition().getY()+ "y velo: " +landerArray[i-1].getVelocity().getY()+" thrust: "+land(landerArray[i-1].getPosition().getY()));
        else if(((landerArray[i-1].getVelocity().getY() < -40) && (landerArray[i-1].getPosition().getY() > 20000))) {
            force = force.add(mainThruster(Math.abs(land(landerArray[i - 1].getPosition().getY())), i-1));
        }
        else if(((landerArray[i-1].getVelocity().getY() < -10) && (landerArray[i-1].getPosition().getY() < 20000) && (landerArray[i-1].getPosition().getY() > 5000))) {
            force = force.add(mainThruster(Math.abs(land(landerArray[i - 1].getPosition().getY())), i-1));
        }
        else if(((landerArray[i-1].getVelocity().getY() < -2.5) && (landerArray[i-1].getPosition().getY() < 5000) && (landerArray[i-1].getPosition().getY() > 1250))) {
            force = force.add(mainThruster(Math.abs(land(landerArray[i - 1].getPosition().getY())), i-1));
        }
        else if(((landerArray[i-1].getVelocity().getY() < -0.625 && (landerArray[i-1].getPosition().getY() < 1250) && (landerArray[i-1].getPosition().getY() > 312.5)))) {
            force = force.add(mainThruster(Math.abs(land(landerArray[i - 1].getPosition().getY())), i-1));
        }
        else if(((landerArray[i-1].getVelocity().getY() < -0.15625 && (landerArray[i-1].getPosition().getY() < 312.5) && (landerArray[i-1].getPosition().getY() > 78.125)))) {
            force = force.add(mainThruster(Math.abs(land(landerArray[i - 1].getPosition().getY())), i-1));
        }
        else if(((landerArray[i-1].getVelocity().getY() < -0.09 && (landerArray[i-1].getPosition().getY() < 79)))) {
            force = force.add(mainThruster(2, i-1));
        }
    }

    /**
     * Outputs the angle out of the interval [0, 2* Pi]
     * at which the lander is being rotated from the wind
     * @param position
     * @return
     */
    public double generateWind(Vector3dInterface position, int i){
        double currentAngle = landerArray[i-1].getAngle();
        Vector3dInterface[] wind = wg.getWind(position, currentAngle);
        Vector3dInterface f = wind[0];//force of the wind
        Vector3dInterface r = wind[1];//distance of the wind to the center

        //torque, moment of inertia, get us angular acceleration
        return WindGenerator.formatAngle(handleRotation(f, r, i));
    }

    public double handleRotation(Vector3dInterface f, Vector3dInterface r, int i){
        //torque, moment of inertia, get us angular acceleration
        double torque = crossProduct2D(f, r);
        double angularAccel = torque/MOI;

        //current vel = prev vel + acceleration*dt
        double currentVelocity = landerArray[i-1].getAngularVelocity()+angularAccel*dt;
        landerArray[i].setAngularVelocity(currentVelocity);

        //displacement = currentVel*dt
        double deltaTheta = currentVelocity*dt;

        //prev angle + change in angle
        return landerArray[i-1].getAngle() + deltaTheta;
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

    public static double crossProduct2D(Vector3dInterface one, Vector3dInterface other){
        return (one.getX()*other.getY()) - (one.getY()*other.getX());
    }
}