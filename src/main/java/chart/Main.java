package chart;

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

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/chart.fxml"));
        stage.setTitle("Graph");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void run() {
        System.out.println("GRAPH:");

        double dt = 10;
        double year = 1;

        System.out.println("Starting Runge-Kutta");

        FactoryProvider.getSolarSystemFactory().computeTimeLineArrayRunge(daySec*365.24*year,dt);
        StateInterface[][] timeLineArrayRunge = FactoryProvider.getSolarSystemFactory().getTimeLineArray(daySec*365.24*year, dt);

        ProbeSimulator simulator = new ProbeSimulator();
        Vector3dInterface[] probeArray = simulator.trajectory(((State)timeLineArrayRunge[6][0]).getPosition(), ((State)timeLineArrayRunge[6][0]).getVelocity(), daySec*365.24*year, dt);

        System.out.println("- Completed computing states");

        double startTime = 0*daySec;
        double endTime = year*365*daySec;

        for (int t = (int)Math.round(startTime/dt)+1; t < (int)Math.round(endTime/dt)+1; t+=100) {

            State probe = (State)timeLineArrayRunge[6][t];

            ChartLoader.addDataA(t,   probe.getPosition().dist(probeArray[t]));
        }

        System.out.println("- Completed inserting states into graph");
    }
}
