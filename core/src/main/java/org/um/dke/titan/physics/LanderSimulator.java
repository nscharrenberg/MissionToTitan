package org.um.dke.titan.physics;

import org.um.dke.titan.domain.Planet;
import org.um.dke.titan.domain.SpaceObjectEnum;
import org.um.dke.titan.domain.Vector3D;
import org.um.dke.titan.factory.FactoryProvider;
import org.um.dke.titan.interfaces.StateInterface;
import org.um.dke.titan.interfaces.Vector3dInterface;
import org.um.dke.titan.physics.ode.functions.mathfunctions.State;
import org.um.dke.titan.physics.ode.functions.planetfunction.PlanetRate;
import org.um.dke.titan.physics.ode.functions.planetfunction.PlanetState;
import org.um.dke.titan.physics.ode.functions.planetfunction.SystemState;
import org.um.dke.titan.repositories.interfaces.ISolarSystemRepository;

import java.util.Map;

public class LanderSimulator {
    private static final double G = 6.67408e-11; // Gravitational Constant

    private StateInterface[] timeLineArray;
    private PlanetState[] landerStateArray;
    private double h;
    private int size;

    private String landerName = SpaceObjectEnum.LANDER.getName();
    private Vector3dInterface force  = new Vector3D(0, 0,0);
    private ISolarSystemRepository system = FactoryProvider.getSolarSystemRepository();

    private double landerMass = system.getRocketByName(landerName).getMass();




    public Vector3dInterface[] trajectory(Vector3dInterface p0, Vector3dInterface v0, double[] ts) throws Exception {


        timeLineArray = FactoryProvider.getSolarSystemRepository().getTimeLineArray(FactoryProvider.getSolver(), ts);
        init(p0, v0);

        for (int i = 1; i < size; i++) {
            h = ts[i] - ts[i-1];
            landerStateArray[i] = getNextLanderState(i, h);
        }

        // converting from probe state to probe position array
        Vector3dInterface[] landerPositions = new Vector3D[size];
        for (int i = 0; i < size; i++){

            landerPositions[i] = landerStateArray[i].getPosition();
            PlanetState currentState = (PlanetState) landerPositions[i];
            Vector3dInterface currentPosition = currentState.getPosition();

            for (Map.Entry<String , PlanetState> entry : ((SystemState)timeLineArray[i-1]).getPlanets().entrySet()) {

                String planetName = entry.getKey();
                Planet planet = system.getPlanetByName(planetName);
                    if(planet.getPosition().dist(currentPosition) <= planet.getRadius()){
                        throw new Exception("The lander collided with " + planetName);
                }

            }
        }

        return landerPositions;
    }

   


    public Vector3dInterface[] trajectory(Vector3dInterface p0, Vector3dInterface v0, double tf, double h) throws Exception {
        this.h = h;
        timeLineArray = FactoryProvider.getSolarSystemRepository().getTimeLineArray(FactoryProvider.getSolver(),tf, h);
        init(p0, v0);

        for (int i = 1; i < size; i++)
            landerStateArray[i] = getNextLanderState(i, h);

        // converting from probe state to probe position array
        Vector3dInterface[] landerPositions = new Vector3D[size];
        for (int i = 0; i < size; i++) {

            landerPositions[i] = landerStateArray[i].getPosition();
            PlanetState currentState = (PlanetState) landerPositions[i];
            Vector3dInterface currentPosition = currentState.getPosition();

            for (Map.Entry<String , PlanetState> entry : ((SystemState)timeLineArray[i-1]).getPlanets().entrySet()) {

                String planetName = entry.getKey();
                Planet planet = system.getPlanetByName(planetName);
                if(planet.getPosition().dist(currentPosition) <= planet.getRadius()){
                    throw new Exception("The lander collided with " + planetName);
                }

            }
        }
        return landerPositions;
    }


    // --------------------- ODE Handling  ---------------------


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

    private void init(Vector3dInterface p0, Vector3dInterface v0) {
        size = timeLineArray.length;
        landerStateArray = new PlanetState[size];
        landerStateArray[0] = new PlanetState(p0, v0);
        force = new Vector3D(0,0,0);
    }

    private PlanetState getNextLanderState(int i, double h) {
        force = new Vector3D(0,0,0);


        for (Map.Entry<String , PlanetState> entry : ((SystemState)timeLineArray[i-1]).getPlanets().entrySet()) {

            String planetName = entry.getKey();
            Planet planet = system.getPlanetByName(planetName);
            double planetMass = planet.getMass();

            PlanetState planetState = entry.getValue();
            PlanetState probeState = landerStateArray[i-1];

            force = force.add(newtonsLaw(probeState, planetState, landerMass, planetMass));
        }
        return step(landerStateArray[i - 1], h);
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


}
