package utils.gravitytest;

import domain.Planet;
import interfaces.StateInterface;
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

    protected static final double G = 6.67408e-11; // Gravitational Constant
    protected static double daySec = 60*24*60; // total seconds in a day
    protected static double t;

    protected static Planet mercury;
    protected static Planet venus;
    protected static Planet earth;
    protected static Planet mars;
    protected static Planet jupiter;
    protected static Planet sun;

    protected static List<Planet> planets;

    protected static SolarSystemRepository system;

    protected static double dt = 1*daySec;
    protected static double totalTime = 365*daySec;

    protected static void simulate() {
        ODESolver solve = new ODESolver(system);
        ODEFunction f = new ODEFunction(system);

        ArrayList<StateInterface[]> stateArrayList = new ArrayList<>();

        for (int i = 0; i < planets.size(); i++) {
            Planet planet = planets.get(i);
            State planetState = new State(planet.getPosition(), planet.getVelocity(), planet);
            StateInterface[] stateArray = solve.solve(f, planetState, totalTime, dt);
            stateArrayList.add(stateArray);
        }

        for (int i = 0; i < stateArrayList.get(0).length; i++) {
            System.out.println(i);
            State newMercuryState = (State) stateArrayList.get(0)[i];
            State newVenusState = (State) stateArrayList.get(1)[i];
            State newEarthState = (State) stateArrayList.get(2)[i];
            State newMarsState =  (State) stateArrayList.get(3)[i];
            State newJupiterState = (State) stateArrayList.get(4)[i];
            State newSunState = (State) stateArrayList.get(5)[i];

            Chart.addDataA(i*daySec, newMercuryState.getPosition().getY());
            Chart.addDataB(i*daySec, newVenusState.getPosition().getX());
            Chart.addDataC(i*daySec, newEarthState.getPosition().getX());
            Chart.addDataD(i*daySec, newMarsState.getPosition().getX());
            Chart.addDataE(i*daySec, newJupiterState.getPosition().getX());
            Chart.addDataF(i*daySec, newSunState.getPosition().getX());
        }
    }

    protected static void initSystem() {
        system = new SolarSystemRepository();
        system.init();
        planets = system.getPlanets();
        t = 0; // start time
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/sample.fxml"));
        stage.setTitle("Earth");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
