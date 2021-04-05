package chart;

import factory.FactoryProvider;
import interfaces.StateInterface;
import interfaces.Vector3dInterface;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
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

        double dt = 6;
        StateInterface[][] timeLineArray = FactoryProvider.getSolarSystemFactory().getTimeLineArray(daySec*365, dt);

        System.out.println("- Completed computing states");

        double startTime = 256*daySec;
        double endTime = 261*daySec;
        for (int t = (int)Math.round(startTime/dt)+1; t < (int)Math.round(endTime/dt)+1; t+=500) {
            State probe = (State)timeLineArray[6][t];
            State titan = (State)timeLineArray[5][t];
            Vector3dInterface difference = probe.getPosition().sub(titan.getPosition());
            ChartLoader.addDataA(t, difference.norm(), 0, 0);
        }

        System.out.println("- Completed inserting states into graph");
    }
}
