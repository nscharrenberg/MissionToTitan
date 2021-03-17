package utils.gravitytest;

import domain.MovingObject;
import domain.Planet;
import domain.Vector3D;
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

public class GravityTest extends Application {

    protected static final double G = 6.67408e-11; // Gravitational Constant
    protected static double daySec = 60*24*60; // total seconds in a day
    protected static double t;
    protected static Planet earth;
    protected static Planet moon;
    protected static Planet sun;
    protected static SolarSystemRepository system;

    protected static double dt = 0.5*daySec;
    protected static double totalSteps = 365*daySec/dt; // ensures that when changing dt, a full year will always be displayed

    /**
     *  this is where the logic of the whole simulation happens
     */
    protected static void simulate2() {
        System.out.println("starting calculation");
        while(t < dt*totalSteps) {
            determineForces();
            updateBodies();
            Chart.addDataPoints();
            t += dt;
        }
        System.out.println("done calculating");
    }

    protected static void simulate() {
        ODESolver solve = new ODESolver();
        ODEFunction f = new ODEFunction();
        State state = new State(earth.getPosition(), earth.getVelocity(), earth);

        StateInterface[] solveArray = solve.solve(f,state, 365*24.0*60*60, 0.05*24*60*60);

        for (int i = 0; i < solveArray.length; i++) {
            State temp = (State) solveArray[i];
            Chart.addData(i *daySec, temp.getPosition().getX());
        }
    }

    private static void determineForces() {
        resetForces();
        newtonsLaw(moon, sun);
        newtonsLaw(earth, sun);
        newtonsLaw(moon, earth);
    }

    private static void resetForces() {
        earth.setForce(new Vector3D(0,0,0));
        moon.setForce(new Vector3D(0,0,0));
        sun.setForce(new Vector3D(0,0,0));
    }

    private static void updateBodies() {
        updateAcceleration();
        updateVelocities();
        updatePositions();
    }

    // calculates the force on all x,y,z axes of a at its current position
    private static void newtonsLaw(MovingObject a, MovingObject b) {

        Vector3D r = (Vector3D) b.getPosition().sub(a.getPosition()); // xi - xj
        double gravConst = G * a.getMass() * b.getMass(); // G * Mi * Mj
        double modr3 = Math.pow(r.norm(),3); // ||xi - xj||^3
        Vector3dInterface force = r.mul(gravConst/modr3); // full formula together

        a.setForce(a.getForce().add(force));
        b.setForce(b.getForce().add(force.mul(-1)));
    }

    /**
     * updates the current acceleration of a and b determined by the gravitational force
     * each MovingObject is experiencing
     */
    private static void updateAcceleration () {
        earth.setAcceleration(earth.getForce().mul(1/earth.getMass()));
        sun.setAcceleration(sun.getForce().mul(1/sun.getMass()));
        moon.setAcceleration(moon.getForce().mul(1/moon.getMass()));
    }

    /**
     * updates the current Velocities of a and b determined by it's current
     * acceleration
     */
    private static void updateVelocities() {
        earth.setVelocity(earth.getVelocity().add(earth.getAcceleration().mul(dt)));
        sun.setVelocity(sun.getVelocity().add(sun.getAcceleration().mul(dt)));
        moon.setVelocity(moon.getVelocity().add(moon.getAcceleration().mul(dt)));
    }

    /**
     * updates the current position of a and b determined by it's
     * current velocity
     */
    private static void updatePositions() {
        earth.setPosition(earth.getPosition().add(earth.getVelocity().mul(dt)));
        sun.setPosition(sun.getPosition().add(sun.getVelocity().mul(dt)));
        moon.setPosition(moon.getPosition().add(moon.getVelocity().mul(dt)));
    }

    protected static void initSystem() {
        system = new SolarSystemRepository();
        system.init();
        earth = system.findPlanet("Earth");
        sun = system.findPlanet("Sun");
        moon = earth.getMoon("Moon");
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
