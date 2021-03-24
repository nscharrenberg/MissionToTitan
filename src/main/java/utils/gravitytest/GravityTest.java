package utils.gravitytest;

import domain.MovingObject;
import factory.FactoryProvider;
import interfaces.StateInterface;
import interfaces.Vector3dInterface;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import physics.gravity.ode.ODEFunction;
import physics.gravity.ode.ODESolver;
import physics.gravity.ode.State;
import repositories.SolarSystemRepository;

import java.util.ArrayList;
import java.util.List;

public class GravityTest extends Application {

    protected static List<MovingObject> planets;
    protected static SolarSystemRepository system;
    private static StateInterface[][] allStates;
    private static ArrayList<StateInterface[]> stateArrayList;


    protected static double daySec = 60*24*60; // total seconds in a day
    protected static double t;
    protected static double dt = 0.005*daySec;
    protected static double totalTime = 1*365*daySec;

    protected static void simulate() {
        ODESolver solve = new ODESolver();
        ODEFunction f = new ODEFunction();

        stateArrayList = new ArrayList<>();
        run(solve,f);

        for (int i = 0; i < planets.size(); i++) {
            StateInterface[] stateArray = allStates[i];
            stateArrayList.add(stateArray);
        }

//        SpaceCraft probe = system.getProbe();
//        State probeState = new State(probe.getPosition(), probe.getVelocity(), probe);
//        StateInterface[] probeStateArray = solve.solve(f, probeState, totalTime, dt);
//
//        System.out.println(system.getProbe().getVelocity().norm());

        for (int i = 0; i < stateArrayList.get(0).length; i++)
        {
            State newSunState = (State) stateArrayList.get(0)[i];
            State newMercuryState = (State) stateArrayList.get(1)[i];
            State newVenusState = (State) stateArrayList.get(2)[i];
            State newEarthState = (State) stateArrayList.get(3)[i];
            State newMoonState = (State) stateArrayList.get(4)[i];
            State newMarsState = (State) stateArrayList.get(5)[i];
            State newJupiterState =  (State) stateArrayList.get(6)[i];
            State newSaturnState = (State) stateArrayList.get(7)[i];
            State newTitanState = (State) stateArrayList.get(8)[i];
            State newProbeState = (State) stateArrayList.get(9)[i];
        }
    }
//            // adding the data to the charts
//            Chart.addDataA(i*daySec, newEarthState.getPosition().getX() , newEarthState.getPosition().getY(), newEarthState.getPosition().getZ());
//            Chart.addDataB(i*daySec, newMoonState.getPosition().getX() - newEarthState.getPosition().getX(), newMoonState.getPosition().getY() - newEarthState.getPosition().getY(), newMoonState.getPosition().getZ() - newEarthState.getPosition().getZ());
//            Chart.addDataC(i*daySec, newSaturnState.getPosition().getX(), newSaturnState.getPosition().getY(), newSaturnState.getPosition().getZ());
//            Chart.addDataD(i*daySec, newTitanState.getPosition().getX() - newSaturnState.getPosition().getX(), newTitanState.getPosition().getY() - newSaturnState.getPosition().getY(), newTitanState.getPosition().getZ() - newSaturnState.getPosition().getZ());
//            Chart.addDataE(i*daySec, newProbeState.getPosition().getX(), newProbeState.getPosition().getY(), newProbeState.getPosition().getZ());
//            Chart.addDataF(i*daySec, newSaturnState.getPosition().sub(newProbeState.getPosition()).norm());

    public static void run() {
        initSystem();
        //system = systemm;

        initProbe();

        simulate();

        State startEarthState = (State)stateArrayList.get(3)[22509];
        State startTitanState = (State)stateArrayList.get(8)[22509];

        system.init();
        Vector3dInterface unit = unitVecToGoal(startTitanState.getPosition());
        Vector3dInterface velocity = unit.mul(60000);
        //system.setProbe(new SpaceCraft(15000, startEarthState.getPosition().add(LaunchPosition(startTitanState.getPosition())), startEarthState.getVelocity().add(velocity), "Probe"));
        System.out.println(velocity.norm());
        simulate();
        System.out.println("Distance from titan: " + distanceFromTitan());
    }

    private static void run(ODESolver solve, ODEFunction f) {
        allStates = solve.getData(f,totalTime, dt);
    }

    private static void initProbe() {
        MovingObject earth = system.getPlanets().get(3);
        MovingObject titan = system.getPlanets().get(8);

       // system.setProbe(new SpaceCraft(1500, earth.getPosition().add(LaunchPosition(titan.getPosition())), earth.getVelocity(), "Probe"));
    }

    public static Vector3dInterface LaunchPosition(Vector3dInterface goal) {
        return unitVecToGoal(goal).mul(6371);
    }

    private static Vector3dInterface unitVecToGoal(Vector3dInterface goal) {
        MovingObject earth =system.getPlanets().get(3);
        Vector3dInterface aim = goal.sub(earth.getPosition()); // vector between earth and goal
        return aim.mul(1.0/aim.norm());
    }

    private static double distanceFromTitan() {

        double dist;
        double min = Double.MAX_VALUE;

        for(int i = 0; i < allStates[0].length; i++) {

            State probeState = (State) allStates[9][i];
            State titanState = (State) allStates[8][i];
            dist = probeState.getPosition().dist(titanState.getPosition());

            if (min > dist) {
                min = dist;
            }
        }
        return min;
    }

    public static void main(String[] args) {
        launch(args);
    }

    protected static void initSystem() {
        system = FactoryProvider.getSolarSystemFactory();
        system.init();
        planets = system.getPlanets();
        t = 0;
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/sample.fxml"));
        stage.setTitle("Earth");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
