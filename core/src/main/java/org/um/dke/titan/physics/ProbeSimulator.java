package org.um.dke.titan.physics;

import org.um.dke.titan.domain.Planet;
import org.um.dke.titan.domain.SpaceObjectEnum;
import org.um.dke.titan.domain.Vector3D;
import org.um.dke.titan.factory.FactoryProvider;
import org.um.dke.titan.interfaces.ProbeSimulatorInterface;
import org.um.dke.titan.interfaces.StateInterface;
import org.um.dke.titan.interfaces.Vector3dInterface;
import org.um.dke.titan.physics.ode.functions.mathfunctions.State;
import org.um.dke.titan.physics.ode.functions.solarsystemfunction.PlanetRate;
import org.um.dke.titan.physics.ode.functions.solarsystemfunction.PlanetState;
import org.um.dke.titan.physics.ode.functions.solarsystemfunction.SystemState;
import org.um.dke.titan.repositories.interfaces.ISolarSystemRepository;

import java.util.Arrays;
import java.util.Map;

public class ProbeSimulator implements ProbeSimulatorInterface {
    private static final double G = 6.67408e-11; // Gravitational Constant

    private StateInterface[] timeLineArray;
    private PlanetState[] probeStateArray;
    //private Vector3dInterface[] probeDirections;
    private double h;
    private int size;

    private String probeName = SpaceObjectEnum.SHIP.getName();
    private Vector3dInterface force;
    private ISolarSystemRepository system = FactoryProvider.getSolarSystemRepository();

    private double probeMass = system.getRocketByName(probeName).getMass();


    private final double probeMassDry = 78000;
    private final double EXHAUST_VELOCITY = 2e4;
    private final double MAXIMUM_THRUST = 3e7;
    private final double AREA = 4.55;
    private final double PRESSURE = 100000;
    private final double radiusTitan = 2575.5e3;
    private final double MASS_FLOW_RATE = 2000;




    // --------------------- Trajectories  ---------------------

    @Override
    public Vector3dInterface[] trajectory(Vector3dInterface p0, Vector3dInterface v0, double[] ts) {
        timeLineArray = FactoryProvider.getSolarSystemRepository().getTimeLineArray(FactoryProvider.getSolver(), ts);
        init(p0, v0);

        for (int i = 1; i < size; i++) {
            h = ts[i] - ts[i-1];
            probeStateArray[i] = getNextProbeState(i, h);
        }

        // converting from probe state to probe position array
        Vector3dInterface[] probePositions = new Vector3D[size];
        for (int i = 0; i < size; i++)
            probePositions[i] = probeStateArray[i].getPosition();

        return probePositions;
    }

    @Override
    public Vector3dInterface[] trajectory(Vector3dInterface p0, Vector3dInterface v0, double tf, double h) {
        this.h = h;
        timeLineArray = FactoryProvider.getSolarSystemRepository().getTimeLineArray(FactoryProvider.getSolver(),tf, h);
        init(p0, v0);
        for (int i = 1; i < size; i++)
            probeStateArray[i] = getNextProbeState(i, h);

        // converting from probe state to probe position array
        Vector3dInterface[] probePositions = new Vector3D[size];

        for (int i = 0; i < size; i++){
            probePositions[i] = probeStateArray[i].getPosition();
            //probeDirections[i] = probeStateArray[i].getVelocity();
        }
        System.out.println(probePositions[0]);
        return probePositions;
    }






    //--------------------Lander----------------------------










    // --------------------- ODE Handling  ---------------------


    private PlanetRate call(double t, PlanetState y) {
        Vector3dInterface rateAcceleration = force.mul(1 / probeMass); // a = F/m
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

    private void init(Vector3dInterface p0, Vector3dInterface v0) {
        size = timeLineArray.length;
        probeStateArray = new PlanetState[size];
        probeStateArray[0] = new PlanetState(p0, v0);
        force = new Vector3D(0,0,0);
    }

    private PlanetState getNextProbeState(int i, double h) {
        force = new Vector3D(0,0,0);

        for (Map.Entry<String , PlanetState> entry : ((SystemState)timeLineArray[i-1]).getPlanets().entrySet()) {

            String planetName = entry.getKey();
            Planet planet = system.getPlanetByName(planetName);
            double planetMass = planet.getMass();

            PlanetState planetState = entry.getValue();
            PlanetState probeState = probeStateArray[i-1];
            if(probeStateArray[i-1].getPosition().dist(planetState.getPosition())<radiusTitan){
                probeState.setVelocity(planetState.getVelocity());
                return probeState;}
            else
                force = force.add(newtonsLaw(probeState, planetState, probeMass, planetMass));

        }


        //force = force.add(getEngineForce(i-1, 50000).mul(1));
        return step(probeStateArray[i - 1], h);
    }

    /**
     * calculates the Gravitational force of 2 states.
     */
    private Vector3dInterface newtonsLaw(PlanetState a, PlanetState b, double massA, double massB) {
        Vector3D r = (Vector3D) b.getPosition().sub(a.getPosition()); // xi - xj
        double gravConst = G * massA * massB; // G * Mi * Mj
        double modr3 = Math.pow(r.norm(),3); // ||xi - xj||^3
        return r.mul(gravConst/modr3); // full formula together
    }







    // --------------------- New Engine Handling  ---------------------

    private Vector3dInterface getEngineForce(int i, double idealSpeed) {
        return getIdealEngineDirection(i, idealSpeed).mul(1);
    }


    private Vector3dInterface getIdealEngineDirection(int i,double idealSpeed) {
        return getIdealVector(i, idealSpeed).sub(probeStateArray[i].getVelocity());
    }


    private Vector3dInterface getIdealVector(int i, double idealSpeed) {
        Vector3D totalVector = (Vector3D) getDestination(i);
        return  totalVector.mul(idealSpeed/totalVector.norm());
    }

    /**
     * returns the position we want to travel to
     * @return
     */
    private Vector3dInterface getDestination(int t) {
        //double time = 256 * 60*60*24;
        //int t = (int) (time/h);
        SystemState systemState = (SystemState) timeLineArray[t];

        return systemState.getPlanet("Mars").getPosition();
    }



    // --------------------- Old Engine Handling  ---------------------


//    private boolean[] computeEngineInterval() {
//        boolean[] engineInterval = new boolean[probeStateArray.length];
//
//        for (int i = 0; i < probeStateArray.length; i++) {
//            if(i%2==0){
//                engineInterval[i] = true;}
//        }
//
//        return engineInterval;
//    }
//
//    /**
//     *  returns the unit vector of the desired thrust angle
//     *  TODO: fix this
//     */
//    private Vector3dInterface findThrustVector(int index, int planetID){
//        State probe = (State) probeStateArray[index];
//        State state = (State) timeLineArray[planetID][index];
//
//        Vector3dInterface thrustVector = state.getPosition().sub(probe.getPosition());
//        return thrustVector.mul(1/thrustVector.norm());
//        return new Vector3D(0,0,0);
//    }
//
//    /**
//     *  returns the force vector of the engine of the probe
//     */
//    private Vector3dInterface engineForce(double percentageOfPower, Vector3dInterface thrustVector) {
//        return thrustVector.mul(MAXIMUM_THRUST*(percentageOfPower/100));
//    }
//
//    private double calculateMassUsed(double percentageOfPower){
//
//        return h *(percentageOfPower/100)*((MAXIMUM_THRUST+PRESSURE*AREA)/EXHAUST_VELOCITY);
//
//    }
//
//    private Vector3dInterface useEngine(double percentageOfPower, int index, int targetPlanetID){
//        if(!calculateNewMass(percentageOfPower))
//            return new Vector3D(0,0,0);
//        Vector3dInterface thrustVector = findThrustVector(index, targetPlanetID);
//        return engineForce(percentageOfPower, thrustVector);
//    }
//
//    private Vector3dInterface useEngine(double percentageOfPower, int index, Vector3D v, StateInterface probe){
//        if(!calculateNewMass(percentageOfPower))
//            return new Vector3D(0,0,0);
//        v = (Vector3D) v.sub(((State)(probe)).getPosition());
//        Vector3dInterface thrustVector = v.mul(1/v.norm());
//        return engineForce(percentageOfPower, thrustVector);
//    }
//
//    private boolean calculateNewMass(double percentageOfPower) {
//        if (system.getRocketByName(probeName).getMass()-calculateMassUsed(percentageOfPower)>probeMassDry) {
//            system.getRocketByName(probeName).setMass((float) (system.getRocketByName(probeName).getMass() - calculateMassUsed(percentageOfPower)));
//            fuelUsed += calculateMassUsed(percentageOfPower);
//            return true;
//        }
//        else{
//            System.out.println("No fuel left!!");
//            return false;
//        }
//    }
//
//    private Vector3dInterface engine(int i){
//        if(i>minI && i<minI+31){
//            force = force.add(useEngine(3.7, i-1, SpaceObjectEnum.EARTH.getId()));
//        }
//        else if(i>1120550 && i<1120561){
//            force = force.add(useEngine(9.2, i-1, new Vector3D(-1.306472101663588e+12,
//                    -2.860995816266412e+10,
//                    153013631859.6080), probeStateArray[i-1]));
//        }
//        else if(i==1120562){
//            force = force.add(useEngine(0.5, i-1, new Vector3D(-1.471922101663588e+12,
//                    -2.860995816266412e+10,
//                    8.278183193596080e+06), probeStateArray[i-1]));
//        }
//        else if(i==3631390){
//            force = force.add(useEngine(0.069, i-1, SpaceObjectEnum.EARTH.getId()));
//        }
//        else
//            return null;
//        return force;
//        return new Vector3D(-1,-1,-1);
//    }

}