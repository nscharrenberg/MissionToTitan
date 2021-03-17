package utils.gravitytest;

import domain.Planet;
import domain.SpaceCraft;
import interfaces.StateInterface;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import physics.gravity.ODEFunction;
import physics.gravity.ODESolver;
import physics.gravity.Simulation;
import physics.gravity.State;
import repositories.SolarSystemRepository;

import java.util.ArrayList;
import java.util.List;

public class GravityTest extends Application {

    protected static List<Planet> planets;
    protected static SolarSystemRepository system;

    protected static double daySec = 60*24*60; // total seconds in a day
    protected static double t;
    protected static double dt = 0.01*daySec;
    protected static double totalTime = 1*30*daySec;

    protected static void simulate() {
        ODESolver solve = new ODESolver(system);
        ODEFunction f = new ODEFunction(system);

        Simulation simulation = new Simulation(system);
        simulation.simulate();

        StateInterface[][] array = solve.getData(f,totalTime, dt);
        ArrayList<StateInterface[]> stateArrayList = new ArrayList<>();

        for (int i = 0; i < planets.size(); i++) {
            Planet planet = planets.get(i);
            State planetState = new State(planet.getPosition(), planet.getVelocity(), planet);
            StateInterface[] stateArray = solve.solve(f, planetState, totalTime, dt);
            stateArrayList.add(stateArray);
        }
        SpaceCraft probe = system.getProbe();
        State probeState = new State(probe.getPosition(), probe.getVelocity(), probe);
        StateInterface[] probeStateArray = solve.solve(f, probeState, totalTime, dt);
        System.out.println(probeStateArray[0].toString());


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

            // adding the data to the charts
            Chart.addDataA(i*daySec, newEarthState.getPosition().getX() , newEarthState.getPosition().getY(), newEarthState.getPosition().getZ());
            Chart.addDataB(i*daySec, newMoonState.getPosition().getX() - newEarthState.getPosition().getX(), newMoonState.getPosition().getY() - newEarthState.getPosition().getY(), newMoonState.getPosition().getZ() - newEarthState.getPosition().getZ());
            Chart.addDataC(i*daySec, newSaturnState.getPosition().getX(), newSaturnState.getPosition().getY(), newSaturnState.getPosition().getZ());
            Chart.addDataD(i*daySec, newTitanState.getPosition().getX() - newSaturnState.getPosition().getX(), newTitanState.getPosition().getY() - newSaturnState.getPosition().getY(), newTitanState.getPosition().getZ() - newSaturnState.getPosition().getZ());
            Chart.addDataE(i*daySec, newProbeState.getPosition().getX(), newProbeState.getPosition().getY(), newProbeState.getPosition().getZ());
        }
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
