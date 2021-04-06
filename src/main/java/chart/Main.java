package chart;

import factory.FactoryProvider;
import interfaces.StateInterface;
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

        double dt = 5;
        double year = 260.0/365.0;
        FactoryProvider.getSolarSystemFactory().computeTimeLineArray(daySec*365*year,dt);
        StateInterface[][] timeLineArray = FactoryProvider.getSolarSystemFactory().getTimeLineArray(daySec*365*year, dt);

        //FactoryProvider.getSolarSystemFactory().computeTimeLineArrayVerlet(daySec*365*year,dt);
       // StateInterface[][] timeLineArrayVerlet = FactoryProvider.getSolarSystemFactory().getTimeLineArray(daySec*365*year, dt);

        System.out.println("- Completed computing states");

        double startTime = 255.2*daySec;
        double endTime = 255.5*daySec;

        for (int t = (int)Math.round(startTime/dt)+1; t < (int)Math.round(endTime/dt)+1; t+=1) {
            State probe = (State)timeLineArray[6][t];
            State titan = (State)timeLineArray[5][t];
            //State earth = (State)timeLineArray[5][t];
            //State earthVerlet = (State)timeLineArrayVerlet[5][t];
            ChartLoader.addDataA(t, probe.getPosition().dist(titan.getPosition()));
           // ChartLoader.addDataA(t,probe.getPosition().dist(titan.getPosition()));
        }

        System.out.println("- Completed inserting states into graph");
    }
}
