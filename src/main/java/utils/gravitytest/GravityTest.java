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

    protected static List<Planet> planets;
    protected static SolarSystemRepository system;

    protected static double daySec = 60*24*60; // total seconds in a day
    protected static double t;
    protected static double dt = 0.5*daySec;
    protected static double totalTime = 1*365*daySec;

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
            State newState1 = (State) stateArrayList.get(0)[i];
            State newState2 = (State) stateArrayList.get(1)[i];
            State newState3 = (State) stateArrayList.get(2)[i];
            State newState4 =  (State) stateArrayList.get(3)[i];
            State newState5 = (State) stateArrayList.get(4)[i];
            State newState6 = (State) stateArrayList.get(5)[i];

            // adding the data to the charts
            Chart.addDataA(i*daySec, newState1.getPosition().getX(), newState1.getPosition().getY());
            Chart.addDataB(i*daySec, newState2.getPosition().getX(), newState2.getPosition().getY());
            Chart.addDataC(i*daySec, newState3.getPosition().getX(), newState3.getPosition().getY());
            Chart.addDataD(i*daySec, newState4.getPosition().getX(), newState4.getPosition().getY());
            Chart.addDataE(i*daySec, newState5.getPosition().getX(), newState5.getPosition().getY());
            Chart.addDataF(i*daySec, newState6.getPosition().getX(), newState6.getPosition().getY());
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
