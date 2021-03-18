package utils.gravitytest;

import domain.Planet;
import domain.SpaceCraft;
import interfaces.StateInterface;
import interfaces.Vector3dInterface;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import physics.gravity.ODEFunction;
import physics.gravity.ODESolver;
import physics.gravity.State;
import repositories.SolarSystemRepository;

import java.util.ArrayList;
import java.util.List;

public class GravityTest extends Application {

    protected static List<Planet> planets;
    protected static SolarSystemRepository system;
    private static StateInterface[][] allStates;
    private static ArrayList<StateInterface[]> stateArrayList;


    protected static double daySec = 60*24*60; // total seconds in a day
    protected static double t;
    protected static double dt = 0.01*daySec;
    protected static double totalTime = 1*200*daySec;

    protected static void simulate() {
        ODESolver solve = new ODESolver(system);
        ODEFunction f = new ODEFunction(system);

        stateArrayList = new ArrayList<>();
        run(solve,f);

        for (int i = 0; i < planets.size(); i++) {
            StateInterface[] stateArray = allStates[i];
            stateArrayList.add(stateArray);
        }

        SpaceCraft probe = system.getProbe();
        State probeState = new State(probe.getPosition(), probe.getVelocity(), probe);
        StateInterface[] probeStateArray = solve.solve(f, probeState, totalTime, dt);

        for (int i = 0; i < stateArrayList.get(0).length; i++) {
            State newSunState = (State) stateArrayList.get(0)[i];
            State newMercuryState = (State) stateArrayList.get(1)[i];
            State newVenusState = (State) stateArrayList.get(2)[i];
            State newEarthState = (State) stateArrayList.get(3)[i];
            State newMoonState = (State) stateArrayList.get(4)[i];
            State newMarsState = (State) stateArrayList.get(5)[i];
            State newJupiterState =  (State) stateArrayList.get(6)[i];
            State newSaturnState = (State) stateArrayList.get(7)[i];
            State newTitanState = (State) stateArrayList.get(8)[i];
            State newProbeState = (State) probeStateArray[i];
            System.out.println("adding " + i);

            // adding the data to the charts
            Chart.addDataA(i*daySec, newEarthState.getPosition().getX() , newEarthState.getPosition().getY(), newEarthState.getPosition().getZ());
            Chart.addDataB(i*daySec, newMoonState.getPosition().getX() - newEarthState.getPosition().getX(), newMoonState.getPosition().getY() - newEarthState.getPosition().getY(), newMoonState.getPosition().getZ() - newEarthState.getPosition().getZ());
            Chart.addDataC(i*daySec, newSaturnState.getPosition().getX(), newSaturnState.getPosition().getY(), newSaturnState.getPosition().getZ());
            Chart.addDataD(i*daySec, newTitanState.getPosition().getX() - newSaturnState.getPosition().getX(), newTitanState.getPosition().getY() - newSaturnState.getPosition().getY(), newTitanState.getPosition().getZ() - newSaturnState.getPosition().getZ());
            Chart.addDataE(i*daySec, newProbeState.getPosition().getX(), newProbeState.getPosition().getY(), newProbeState.getPosition().getZ());
            Chart.addDataF(i*daySec, newSaturnState.getPosition().sub(newProbeState.getPosition()).norm());
        }
    }


    private static void run(ODESolver solve, ODEFunction f) {
        randomizeProbeSpeed();
       // System.out.println(system.getProbe().getVelocity().norm());
        allStates = solve.getData(f,totalTime, dt);
        System.out.println(probeIsCloseTooSaturn());
    }

    private static void randomizeProbeSpeed() {
        Planet earth = system.getPlanets().get(4);
        Planet titan = system.getPlanets().get(8);
        Vector3dInterface velocity = unitVecToGoal(titan.getPosition()).mul(1);
        system.setProbe(new SpaceCraft(1000, earth.getPosition().add(LaunchPosition(titan.getPosition())), earth.getVelocity().add(velocity), "Probe"));
        System.out.println(system.getProbe().getVelocity().norm() - earth.getVelocity().norm());

    }

    private static Vector3dInterface LaunchPosition(Vector3dInterface goal) {
        return unitVecToGoal(goal).mul(6371);
    }

    private static Vector3dInterface unitVecToGoal(Vector3dInterface goal) {
        Planet earth = planets.get(4);
        Vector3dInterface aim = goal.sub(earth.getPosition()); // vector between earth and goal
        return aim.mul(1/aim.norm());
    }

    private static boolean probeIsCloseTooSaturn() {
        for (int i = 0; i < allStates[0].length; i++) {
            State saturnState = (State) allStates[7][i];
            State probeState = (State) allStates[9][i];

            if ( saturnState.getPosition().sub(probeState.getPosition()).norm() < 10000000 ) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        launch(args);
    }

    protected static void initSystem() {
        system = new SolarSystemRepository();
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
