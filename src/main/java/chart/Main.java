package chart;

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
        StateInterface[][] timeLineArray = FactoryProvider.getSolarSystemFactory().getTimeLineArray(daySec*365, dt);
        System.out.println(((State)(timeLineArray[1][0])).getVelocity().add(new Vector3D(30044.15899234246,-40940.355140655956,-597.5749591228989)));

        System.out.println("- Completed computing states");

        double startTime = 255.278*daySec;
        double endTime = 255.28*daySec;
        for (int t = (int)Math.round(startTime/dt)+1; t < (int)Math.round(endTime/dt)+1; t+=1) {
            State probe = (State)timeLineArray[6][t];
            State titan = (State)timeLineArray[5][t];
            ChartLoader.addDataA(t, probe.getPosition().dist(titan.getPosition())-2574000, 0, 0);
        }

        System.out.println("- Completed inserting states into graph");
    }
}
