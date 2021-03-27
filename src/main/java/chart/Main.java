package chart;

import interfaces.StateInterface;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import physics.gravity.ode.State;
import physics.gravity.simulation.Simulation;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/sample.fxml"));
        stage.setTitle("Earth");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void run() {
        StateInterface[][] timeLineArray = Simulation.simulate();

        for (int t = 0; t < timeLineArray[0].length; t++) {
            State earth = (State)timeLineArray[3][t];
            ChartLoader.addDataA(t, earth.getPosition().getX(), 0, 0);
        }
    }
}
