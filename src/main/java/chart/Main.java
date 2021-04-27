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

    private static StateInterface[][] timeLineArray;
    private static Vector3dInterface[] probeArray;


    private static int daySec = 60*60*24;
    private static int dataPointStepSize = 100;

    private static double dt = 20;
    private static double tf = 365*daySec;
    private static double startTime = 0*daySec;

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
        timeLineArray = FactoryProvider.getSolarSystemFactory().getTimeLineArray(tf, dt);
        System.out.println("|| Finished Solver");
    }

    private static void runProbeSimulator() {
        System.out.println("|| Computing Probe Simulator");
        ProbeSimulator simulator = new ProbeSimulator();
        Vector3dInterface velocityVector = ((State) timeLineArray[PlanetEnum.EARTH.getId()][0]).getVelocity().add(new Vector3D(40872.537001884506,-24780.003680313042,-930.5306010940334));
        //Vector3dInterface velocityVector = ((State) timeLineArray[PlanetEnum.EARTH.getId()][0]).getVelocity().add(new Vector3D(48887.51612513939,-19725.47474325983,-797.8491816542717)); // dt 10 6e6m
        System.out.println(velocityVector);
        probeArray = simulator.trajectory(((State) timeLineArray[PlanetEnum.SHIP.getId()][0]).getPosition(), velocityVector,tf, dt);
        System.out.println("finished");

        System.out.println("|| Finished Probe Simulator");

        System.out.println(getMinDistance());
    }

    private static void insertData() {
        System.out.println("|| Inserting data into chart");
        for (int t = (int)Math.round(startTime/dt); t < (int)Math.round(tf/dt)+1; t+=dataPointStepSize) {
            getData(t);
        }
        System.out.println("|| Completed chart");
    }

    private static void getData(int t) {
        State titan = (State) timeLineArray[titanId][t];
        ChartLoader.addDataA(t, probeArray[t].dist(titan.getPosition()));
    }

    private static double getMinDistance() {
        double min = Double.MAX_VALUE;
        double minI = 0;

        for (int i = 0; i < timeLineArray[0].length; i++) {
            State titan = (State) timeLineArray[PlanetEnum.TITANT.getId()][i];
            Vector3dInterface probe = probeArray[i];

            double dist = probe.dist(titan.getPosition()) - 2574000;

            if (min > dist && dist > 0) {
                min = dist;
                minI = i;
            }
        }
        System.out.println("minI = " + minI);
        return min;
    }



}
