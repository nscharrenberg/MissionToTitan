package chart;

import domain.PlanetEnum;
import domain.Vector3D;
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

    private static int probeId = PlanetEnum.SHIP.getId();
    private static int titanId = PlanetEnum.TITANT.getId();

    private static StateInterface[][] timeLineArrayRunge;
    private static Vector3dInterface[] probeArray;

    private static int daySec = 60*60*24;
    private static int dataPointStepSize = 1;

    private static double dt = 10;
    private static double tf = 1*daySec*222.39;
    private static double startTime = daySec*222.37;

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/chart.fxml"));
        stage.setTitle("Graph");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void run() {
        System.out.println("|| Chart:");
        runSolver();
        runProbeSimulator();
        insertData();
    }

    private static void runSolver() {
        System.out.println("|| Computing Solver");
        FactoryProvider.getSolarSystemFactory().computeTimeLineArrayRunge(tf,dt);
        timeLineArrayRunge = FactoryProvider.getSolarSystemFactory().getTimeLineArray(tf, dt);
        System.out.println("|| Finished Solver");
    }

    private static void runProbeSimulator() {
        System.out.println("|| Computing Probe Simulator");
        ProbeSimulator simulator = new ProbeSimulator();
        probeArray = simulator.trajectory(((State)timeLineArrayRunge[probeId][0]).getPosition(), ((State)timeLineArrayRunge[1][0]).getVelocity().add(new Vector3D(47630.51324475152,-14136.648010470884,-970.3424308897135)), tf, dt);
        System.out.println("|| Finished Probe Simulator");
    }

    private static void insertData() {
        System.out.println("|| Inserting data into chart");
        for (int t = (int)Math.round(startTime/dt); t < (int)Math.round(tf/dt)+1; t+=dataPointStepSize) {
            getData(t);
        }
        System.out.println("|| Completed chart");
    }

    private static void getData(int t) {
        State titan = (State)timeLineArrayRunge[titanId][t];
        State probe = (State)timeLineArrayRunge[6][t];
        ChartLoader.addDataA(t, probeArray[t].dist(titan.getPosition()), probe.getPosition().dist(titan.getPosition()));
    }
}
