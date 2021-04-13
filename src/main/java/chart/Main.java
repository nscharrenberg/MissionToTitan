package chart;

import domain.Planet;
import domain.PlanetEnum;
import factory.FactoryProvider;
import interfaces.StateInterface;
import interfaces.Vector3dInterface;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import physics.gravity.ode.ProbeSimulator;
import physics.gravity.ode.State;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    private static int daySec = 60*60*24;
    private static int probeId = PlanetEnum.SHIP.getId();
    private static int titanId = PlanetEnum.TITANT.getId();


    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/chart.fxml"));
        stage.setTitle("Graph");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void run() {
        System.out.println("GRAPH:");


        double dt = 100;
        double tf = daySec*365*10;
        double year = 1;

        System.out.println("Starting Runge-Kutta");

        FactoryProvider.getSolarSystemFactory().computeTimeLineArrayRunge(tf,dt);
        StateInterface[][] timeLineArrayRunge = FactoryProvider.getSolarSystemFactory().getTimeLineArray(tf, dt);

        ProbeSimulator simulator = new ProbeSimulator();
        Vector3dInterface[] probeArray = simulator.trajectory(((State)timeLineArrayRunge[probeId][0]).getPosition(), ((State)timeLineArrayRunge[probeId][0]).getVelocity(), tf, dt);

        System.out.println("- Completed computing states");

        double startTime = 0;
        double endTime = tf;

        for (int t = (int)Math.round(startTime/dt); t < (int)Math.round(endTime/dt)+1; t+=1000) {

            State probe = (State)timeLineArrayRunge[probeId][t];
            State titan = (State)timeLineArrayRunge[titanId][t];

            ChartLoader.addDataA(t,   probeArray[t].getX(), titan.getPosition().getX());
        }

        System.out.println("- Completed inserting states into graph");
    }
}
