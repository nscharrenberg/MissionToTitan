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

    protected static SolarSystemRepository system;

    protected static double dt = 1*daySec;
    protected static double totalTime = 365*daySec;

    protected static void simulate() {
        ODESolver solve = new ODESolver(system);
        ODEFunction f = new ODEFunction(system);

       // State mercuryState = new State(mercury.getPosition(), mars.getVelocity(), mercury);
        //State venusState = new State(venus.getPosition(), venus.getVelocity(), venus);
        //State earthState = new State(earth.getPosition(), earth.getVelocity(), earth);
        //State marsState = new State(mars.getPosition(), mars.getVelocity(), mars);
        //State jupiterState = new State(jupiter.getPosition(), jupiter.getVelocity(), jupiter);
        State sunState = new State(sun.getPosition(), sun.getVelocity(), sun);

        //StateInterface[] mercuryArray = solve.solve(f, mercuryState, totalTime, dt);
        //StateInterface[] venusArray = solve.solve(f, venusState, totalSteps, dt);
        //StateInterface[] earthArray = solve.solve(f, earthState, totalSteps, dt);
      //  StateInterface[] marsArray  = solve.solve(f, marsState, totalSteps, dt);
        //StateInterface[] jupiterArray = solve.solve(f, jupiterState, totalSteps, dt);
        StateInterface[] sunArray = solve.solve(f, sunState, totalTime, dt);

        System.out.println(sunArray[0].toString());
        System.out.println(sunArray[30].toString());
        System.out.println(sunArray[60].toString());

        for (int i = 0; i < sunArray.length; i++) {
            //State newMercuryState = (State) mercuryArray[i];
           // State newVenusState = (State) venusArray[i];
           // State newEarthState = (State) earthArray[i];
           // State newMarsState =  (State) marsArray[i];
           // State newJupiterState = (State) jupiterArray[i];
            State newSunState = (State) sunArray[i];


            //Chart.addDataA(i*daySec, newMercuryState.getPosition().getY());
           // Chart.addDataB(i*daySec, newVenusState.getPosition().getX());
           // Chart.addDataC(i*daySec, newEarthState.getPosition().getX());
           // Chart.addDataD(i*daySec, newMarsState.getPosition().getX());
           // Chart.addDataE(i*daySec, newJupiterState.getPosition().getX());
            Chart.addDataF(i*daySec, newSunState.getPosition().getX());

        }
    }

    protected static void initSystem() {
        system = new SolarSystemRepository();
        system.init();
        mercury = system.findPlanet("Mercury");
        venus = system.findPlanet("Venus");
        earth = system.findPlanet("Earth");
        mars = system.findPlanet("Mars");
        jupiter = system.findPlanet("Jupiter");
        sun = system.findPlanet("Sun");
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
